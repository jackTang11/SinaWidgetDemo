package com.sina.sinawidgetdemo.sharesdk;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.http.HttpStatus;

import com.android.overlay.RunningEnvironment;
import com.android.overlay.connection.ConnectionType;
import com.android.overlay.connection.OnConnectedListener;
import com.android.overlay.manager.ConnectionManager;
import com.android.overlay.utils.StringUtils;
import com.sina.engine.base.enums.HttpTypeEnum;
import com.sina.engine.base.enums.ReturnDataClassTypeEnum;
import com.sina.engine.base.request.listener.RequestDataListener;
import com.sina.engine.base.request.model.TaskModel;
import com.sina.engine.base.request.options.RequestOptions;
import com.sina.engine.base.utils.LogUtils;
import com.sina.sinawidgetdemo.constant.DBConstant;
import com.sina.sinawidgetdemo.constant.RequestConstant;
import com.sina.sinawidgetdemo.request.process.ReuqestDataProcess;
import com.sina.sinawidgetdemo.requestmodel.AccountInfoRequestModel;
import com.sina.sinawidgetdemo.utils.CommonUtils;
import com.sina.sinagame.share.impl.OnAccountAddedListener;
import com.sina.sinagame.usercredit.AccountItem;
import com.sina.sinagame.usercredit.AccountManager;

@SuppressWarnings("serial")
public class AccountInfoManager implements OnConnectedListener,
		OnAccountAddedListener, Serializable {

	protected static final String dbName = DBConstant.ACCOUNTINFO_DB_NAME;
	protected ExecutorService dispatchExecutor;
	protected static AccountInfoManager instance;

	static {
		instance = new AccountInfoManager();
		RunningEnvironment.getInstance().addManager(instance);
	}

	public static AccountInfoManager getInstance() {
		return instance;
	}

	public AccountInfoManager() {
		dispatchExecutor = Executors.newFixedThreadPool(
				SyncReason.values().length, new ThreadFactory() {
					public Thread newThread(Runnable runnable) {
						Thread thread = new Thread(runnable,
								"AIM dispatch-worker");
						thread.setPriority(Thread.MAX_PRIORITY - 1);
						return thread;
					}
				});
	}

	public void doInBackground(Runnable task) {
		dispatchExecutor.submit(task);
	}

	@Override
	public void onAccountAdded(AccountItem accountItem) {
		final String account = accountItem.getAccount();
		LogUtils.d("ACCOUNTINFO", "[0]onAccountAdded: requestAccountInfo("
				+ account + ")");
		ConnectionManager.getInstance().executeWhenConnected(
				new AccountInfoRequestRunnable(account, SyncReason.ALL));
	}

	@Override
	public void onConnected(ConnectionType type) {
		final String account = AccountManager.getInstance().getCurrentAccount();
		LogUtils.d("ACCOUNTINFO", "[0]onConnected[" + type
				+ "]: requestAccountInfo(" + account + ")");
		ConnectionManager.getInstance().executeWhenConnected(
				new AccountInfoRequestRunnable(account, SyncReason.ALL));
	}

	/**
	 * Use requestCurrentAccountInfoForReason for instead.
	 */
	@Deprecated
	protected void requestCurrentAccountInfo() {
		requestCurrentAccountInfoForReason(SyncReason.ALL);
	}

	public void requestCurrentAccountInfoForReason(SyncReason reason) {
		requestAccountInfo(AccountManager.getInstance().getCurrentAccount(),
				reason);
	}

	AccountInfoRequestModel accountInfoRequestModel;

	protected void requestAccountInfo(String user, SyncReason reason) {
		if (user == null || user.length() == 0) {
			return;
		}
		if (reason == null) {
			return;
		}
		String requestId = nextID();
		LogUtils.d("ACCOUNTINFO", "[1]requestAccountInfo[" + requestId
				+ "]: user=" + user + ", reason=" + reason);
		String requestDomainName = RequestConstant.DOMAIN_NAME;
		String requestPhpName = RequestConstant.USER_PHPNAME;
		String requestAction = RequestConstant.ACCOUNTINFO_ACTION;
		// requestDomainName =
		// "file:///android_asset/credit_test/creditAccountInfo.txt";
		// requestPhpName = "";
		// requestAction = null;
		accountInfoRequestModel = new AccountInfoRequestModel(
				requestDomainName, requestPhpName);
		accountInfoRequestModel.setAction(requestAction);
		accountInfoRequestModel.setUid(user);
		accountInfoRequestModel.setSyncReseaon(reason.name());

		RequestOptions requestOptions = new RequestOptions()
				.setHttpRequestType(HttpTypeEnum.get).setIsMainThread(false)
				.setIsSaveMemory(false).setIsSaveDb(false)
				.setMemoryLifeTime(120)
				.setReturnDataClassTypeEnum(ReturnDataClassTypeEnum.object)
				.setReturnModelClass(AccountInfo.class);

		ReuqestDataProcess.requestData(true, accountInfoRequestModel,
				requestOptions, new AccountInfoRequestResult(user, requestId,
						reason), null);
	}

	class AccountInfoRequestResult implements RequestDataListener {
		String user;
		String requestId;
		SyncReason reason;

		public AccountInfoRequestResult(String user, String requestId,
				SyncReason reason) {
			this.user = user;
			this.requestId = requestId;
			this.reason = reason;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void resultCallBack(TaskModel taskModel) {
			LogUtils.d(
					"ACCOUNTINFO",
					"[2]resultCallBack[" + requestId + "]:"
							+ taskModel.getResult() + ", " + this.reason + ", "
							+ taskModel.getMessage() + ", url="
							+ taskModel.getRequestUrl());
			AccountInfo retModel = null;
			boolean success = false;
			if (taskModel.getReturnModel() != null) {
				retModel = (AccountInfo) taskModel.getReturnModel();
				if (retModel != null) {
					if (String.valueOf(HttpStatus.SC_OK).equalsIgnoreCase(
							taskModel.getResult())) {
						retModel.setAccount(user);
						String objString = CommonUtils
								.getObjectString(retModel);
						LogUtils.d("ACCOUNTINFO", "[2]resultCallBack["
								+ requestId + "]" + "[" + this.reason + "]:<"
								+ objString + ">");

						success = true;
					}
				}
			}
			if (!success) {
				onReceiveAccountInfoFailure(user, requestId, reason);
			} else {
				onReceiveAccountInfoSuccess(user, requestId, reason, retModel);
			}
		}
	}

	int retry = 0;

	protected void onReceiveAccountInfoFailure(final String user,
			final String requestId, final SyncReason reason) {
		LogUtils.d("ACCOUNTINFO", "[3]onReceiveAccountInfoFailure[请求失败]["
				+ requestId + "]:" + user + ", " + reason);
		String account = AccountManager.getInstance().getCurrentAccount();
		if (account == null || user == null) {
			return;
		}
		RunningEnvironment.getInstance().runOnUiThreadDelay(new Runnable() {
			@Override
			public void run() {
				if (retry < 50) {
					ConnectionManager.getInstance().executeWhenConnected(
							new AccountInfoRequestRunnable(user, reason));
					retry++;
				}
			}
		}, 200L);
	}

	protected void onReceiveAccountInfoSuccess(String user, String requestId,
			SyncReason reason, AccountInfo info) {
		retry = 0;
		LogUtils.d("ACCOUNTINFO", "[3]onReceiveAccountInfoSuccess[" + requestId
				+ "]:" + user + ", " + reason + ", " + info.toString());

		if (info != null) {
			if (SyncReason.ALL == reason || SyncReason.TOTAL_SCORE == reason) {
				doInBackground(new DispatchAccountScoreRunnable(user,
						requestId, String.valueOf(info.getIntegral())));
			}
			if (SyncReason.ALL == reason
					|| SyncReason.GIFT_STATISTICS == reason) {
				doInBackground(new DispatchGiftStatisticsRunnable(user,
						requestId, String.valueOf(info.getFetchCount()),
						String.valueOf(info.getAlarmCount()),
						String.valueOf(info.getGiftCount())));
			}
			if (SyncReason.ALL == reason
					|| SyncReason.GAME_STATISTICS == reason) {
				doInBackground(new DispatchGameStatisticsRunnable(user,
						requestId, String.valueOf(info.getGameDynamicCount()),
						String.valueOf(info.getMyGameCount())));
			}
			if (SyncReason.ALL == reason
					|| SyncReason.COLLECT_STATISTICS == reason) {
				doInBackground(new DispatchCollectStatisticsRunnable(user,
						requestId, String.valueOf(info.getCollectCount())));
			}
			if (SyncReason.ALL == reason || SyncReason.USER_TASKS == reason) {
				doInBackground(new DispatchUserTaskListRunnable(user,
						requestId, info.getTaskList()));
			}
			if (SyncReason.TOTAL_SCORE_AND_USER_TASKS == reason) {
				doInBackground(new DispatchAccountScoreRunnable(user,
						requestId, String.valueOf(info.getIntegral())));
				doInBackground(new DispatchUserTaskListRunnable(user,
						requestId, info.getTaskList()));
			}
		}
	}

	class DispatchAccountScoreRunnable implements Runnable {
		String user;
		String requestId;
		String score;

		public DispatchAccountScoreRunnable(String user, String requestId,
				String score) {
			this.user = user;
			this.requestId = requestId;
			this.score = score;
		}

		@Override
		public void run() {
			dispatchAccountScoreReceived(user, requestId, score);
		}
	}

	class DispatchGiftStatisticsRunnable implements Runnable {
		String user;
		String requestId;
		String fetchCount;
		String alarmCount;
		String giftCount;

		public DispatchGiftStatisticsRunnable(String user, String requestId,
				String fetchCount, String alarmCount, String giftCount) {
			this.user = user;
			this.requestId = requestId;
			this.fetchCount = fetchCount;
			this.alarmCount = alarmCount;
			this.giftCount = giftCount;
		}

		@Override
		public void run() {
			dispatchGiftStatisticsReceived(user, requestId, fetchCount,
					alarmCount, giftCount);
		}
	}

	class DispatchGameStatisticsRunnable implements Runnable {
		String user;
		String requestId;
		String gameDynamicCount;
		String myGameCount;

		public DispatchGameStatisticsRunnable(String user, String requestId,
				String gameDynamicCount, String myGameCount) {
			this.user = user;
			this.requestId = requestId;
			this.gameDynamicCount = gameDynamicCount;
			this.myGameCount = myGameCount;
		}

		@Override
		public void run() {
			dispatchGameStatisticsReceived(user, requestId, gameDynamicCount,
					myGameCount);
		}
	}

	class DispatchCollectStatisticsRunnable implements Runnable {
		String user;
		String requestId;
		String collectCount;

		public DispatchCollectStatisticsRunnable(String user, String requestId,
				String collectCount) {
			this.user = user;
			this.requestId = requestId;
			this.collectCount = collectCount;
		}

		@Override
		public void run() {
			dispatchCollectStatisticsReceived(user, requestId, collectCount);
		}
	}

	class DispatchUserTaskListRunnable implements Runnable {
		String user;
		String requestId;
		List<Task> taskList;

		public DispatchUserTaskListRunnable(String user, String requestId,
				List<Task> taskList) {
			this.user = user;
			this.requestId = requestId;
			this.taskList = taskList;
		}

		@Override
		public void run() {
			if (this.taskList != null) {
				for (Task task : this.taskList) {
					if (task == null) {
						continue;
					}
					task.setAccount(this.user);
				}
			}
			dispatchUserTaskListReceived(user, requestId, taskList);
		}
	}

	protected void dispatchAccountScoreReceived(final String user,
			final String requestId, final String score) {
		LogUtils.d("ACCOUNTINFO", "dispatchAccountScoreReceived[" + requestId
				+ "]");
		for (OnAccountScoreReceivedListener listener : RunningEnvironment
				.getInstance()
				.getManagers(OnAccountScoreReceivedListener.class)) {
			listener.onAccountScoreReceived(user, score);
		}
	}

	protected void dispatchGiftStatisticsReceived(final String user,
			final String requestId, final String fetchCount,
			final String alarmCount, final String giftCount) {
		LogUtils.d("ACCOUNTINFO", "dispatchGiftStatisticsReceived[" + requestId
				+ "]");
		for (OnGiftStatisticsReceivedListener listener : RunningEnvironment
				.getInstance().getManagers(
						OnGiftStatisticsReceivedListener.class)) {
			listener.OnGiftStatisticsReceived(user, fetchCount, alarmCount,
					giftCount);
		}
	}

	protected void dispatchGameStatisticsReceived(final String user,
			final String requestId, final String gameDynamicCount,
			final String myGameCount) {
		LogUtils.d("ACCOUNTINFO", "dispatchGameStatisticsReceived[" + requestId
				+ "]");
		for (OnGameStatisticsReceivedListener listener : RunningEnvironment
				.getInstance().getManagers(
						OnGameStatisticsReceivedListener.class)) {
			listener.OnGameStatisticsReceived(user, gameDynamicCount,
					myGameCount);
		}
	}

	protected void dispatchCollectStatisticsReceived(final String user,
			final String requestId, final String collectCount) {
		LogUtils.d("ACCOUNTINFO", "dispatchCollectStatisticsReceived["
				+ requestId + "]");
		for (OnCollectStatisticsReceivedListener listener : RunningEnvironment
				.getInstance().getManagers(
						OnCollectStatisticsReceivedListener.class)) {
			listener.OnCollectStatisticsReceived(user, collectCount);
		}
	}

	protected void dispatchUserTaskListReceived(final String user,
			final String requestId, final List<Task> taskList) {
		LogUtils.d("ACCOUNTINFO", "dispatchUserTaskListReceived[" + requestId
				+ "]");
		for (OnUserTaskListReceivedListener listener : RunningEnvironment
				.getInstance()
				.getManagers(OnUserTaskListReceivedListener.class)) {
			listener.onUserTaskListReceived(user, taskList);
		}
	}

	class AccountInfoRequestRunnable implements Runnable {
		String user;
		SyncReason reason;

		public AccountInfoRequestRunnable(String user, SyncReason reason) {
			this.user = user;
			this.reason = reason;
		}

		@Override
		public void run() {
			requestAccountInfo(user, reason);
		}
	}

	private static String prefix = StringUtils.randomString(5) + "-";

	private static long id = 0;

	protected static synchronized String nextID() {
		return prefix + Long.toString(id++);
	}
}
