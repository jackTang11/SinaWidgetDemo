package com.sina.sinawidgetdemo.usercredit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.android.overlay.OnInitializedListener;
import com.android.overlay.OnLoadListener;
import com.android.overlay.RunningEnvironment;
import com.android.overlay.connection.ConnectionType;
import com.android.overlay.connection.OnConnectedListener;
import com.android.overlay.manager.ConnectionManager;
import com.android.overlay.utils.StringUtils;
import com.sina.engine.base.enums.TaskTypeEnum;
import com.sina.engine.base.request.listener.RequestDataListener;
import com.sina.engine.base.request.model.TaskModel;
import com.sina.engine.base.utils.LogUtils;
import com.sina.sinawidgetdemo.request.process.RequestConfigProcess;
import com.sina.sinawidgetdemo.returnmodel.ClassifyModel;
import com.sina.sinawidgetdemo.returnmodel.ConfigModel;
import com.sina.sinawidgetdemo.sharesdk.Task;
import com.sina.sinawidgetdemo.utils.CommonUtils;
import com.sina.sinagame.share.impl.OnAccountAddedListener;
import com.sina.sinagame.usercredit.AccountItem;

@SuppressWarnings("serial")
public class ConfigurationManager implements OnLoadListener,
		OnInitializedListener, OnConnectedListener, OnAccountAddedListener,
		Serializable {

	protected static ConfigurationManager instance;
	protected ConfigModel savedConfig;
	protected boolean initialized = false;

	static {
		instance = new ConfigurationManager();
		RunningEnvironment.getInstance().addManager(instance);
	}

	public static ConfigurationManager getInstance() {
		return instance;
	}

	public ConfigurationManager() {
	}

	@Override
	public void onLoad() {
		if (savedConfig == null) {
			savedConfig = new ConfigModel();
			savedConfig.objectUpdate(RequestConfigProcess.getConfigData());
		}
	}

	@Override
	public void onInitialized() {
		LogUtils.d("CONFIGTASK", "onInitialized");
		initialized = true;
		if (!initialized) {
			LogUtils.d("CONFIGTASK",
					"onInitialized:initialized=false, no requestConfigurations");
		} else {
			LogUtils.d("CONFIGTASK",
					"onInitialized:initialized=true, call requestConfigurations");
		}
		requestConfigurations();
	}

	@Override
	public void onAccountAdded(AccountItem accountItem) {
		LogUtils.d("CONFIGTASK", "onAccountAdded");
		if (!initialized) {
			LogUtils.d("CONFIGTASK",
					"onAccountAdded:initialized=false, no requestConfigurations");
		} else {
			LogUtils.d("CONFIGTASK",
					"onAccountAdded:initialized=true, call requestConfigurations");
		}
		requestConfigurations();
	}

	@Override
	public void onConnected(ConnectionType type) {
		LogUtils.d("CONFIGTASK", "onConnected:type=" + type.name());
		if (initialized) {
			LogUtils.d("CONFIGTASK",
					"onConnected:initialized=false, no requestConfigurations");
		} else {
			LogUtils.d("CONFIGTASK",
					"onConnected:initialized=true, call requestConfigurations");
		}
		requestConfigurations();
	}

	public void requestConfigurations() {
		if (!initialized) {
			return;
		}
		LogUtils.d("CONFIGTASK", "requestConfigurations");
		RequestConfigProcess
				.startrRequestConfig(new RequestConfigurationListener());
	}

	/**
	 * Reserved.
	 * 
	 * @param taskId
	 * @return
	 */
	protected Task getTaskInfo(String taskId) {
		Task info = null;
		if (this.savedConfig != null) {
			List<Task> tasks = this.savedConfig.getTaskList();
			if (tasks != null) {
				for (Task task : tasks) {
					if (task == null || task.getAbsId() == null) {
						continue;
					}
					if (taskId.equalsIgnoreCase(task.getAbsId())) {
						info = new Task();
						info.objectUpdate(task);
						break;
					}
				}
			}
		}
		return info;
	}

	public ConfigModel getCurrentConfig() {
		if (savedConfig == null) {
			savedConfig = new ConfigModel();
			ConfigModel configData = RequestConfigProcess.getConfigData();
			savedConfig.objectUpdate(configData);
		}
		return savedConfig;
	}

	public List<Task> getConfigTasks() {
		ConfigModel config = getCurrentConfig();
		if (config != null && config.getTaskList() != null
				&& config.getTaskList().size() > 0) {
			return config.getTaskList();
		}
		return new ArrayList<Task>();
	}

	public String getOfficalForumUrl() {
		String officalUrl = null;
		ConfigModel model = getCurrentConfig();
		if (model != null && StringUtils.isWebUrl(model.getForumUrl())) {
			officalUrl = model.getRecodeUrl();
		}
		return officalUrl;
	}

	public String getOfficalRecodeUrl() {
		String officalUrl = null;
		ConfigModel model = getCurrentConfig();
		if (model != null && StringUtils.isWebUrl(model.getRecodeUrl())) {
			officalUrl = model.getRecodeUrl();
		}
		return officalUrl;
	}

	protected void dispatchConfigurationChanged(final ConfigModel config) {
		LogUtils.d("CONFIGTASK", "dispatchConfigurationChanged");
		for (OnConfigurationChangeListener listener : RunningEnvironment
				.getInstance().getManagers(OnConfigurationChangeListener.class)) {
			listener.onConfigurationChanged(config);
		}
	}

	protected void dispatchConfigurationTasksChanged(final ConfigModel config,
			final List<Task> tasks) {
		LogUtils.d("CONFIGTASK", "dispatchConfigurationTasksChanged");
		for (OnConfigTaskChangeListener listener : RunningEnvironment
				.getInstance().getManagers(OnConfigTaskChangeListener.class)) {
			listener.onConfigTaskChanged(config, tasks);
		}
	}

	class RequestConfigurationsRunnable implements Runnable {
		String reason;

		public RequestConfigurationsRunnable(String reason) {
			this.reason = reason;
		}

		@Override
		public void run() {
			LogUtils.d("CONFIGTASK", "Run RequestConfigurationsRunnable for("
					+ this.reason + ")");
			requestConfigurations();
		}
	}

	class RequestConfigurationListener implements RequestDataListener {

		public RequestConfigurationListener() {
		}

		@Override
		public void resultCallBack(TaskModel taskModel) {
			LogUtils.d("CONFIGTASK", "resultCallBack[" + taskModel.getResult()
					+ "][" + taskModel.getMessage() + "]");
			boolean iscatch = false;
			ConfigModel returnModel = null;
			if (taskModel.getReturnModel() != null
					&& TaskTypeEnum.getNet == taskModel.getReturnInfo()
							.getTaskTypeEnum()) {
				try {
					returnModel = (ConfigModel) taskModel.getReturnModel();
					if (returnModel != null
							&& String.valueOf(HttpStatus.SC_OK)
									.equalsIgnoreCase(taskModel.getResult())) {
						iscatch = true;
					}
				} catch (Exception e) {
					returnModel = null;
					iscatch = false;
				}
			}

			if (!iscatch) {
				String message = taskModel.getMessage();
				if (message == null || message.length() == 0) {
					message = "请求失败(未知原因)";
				}
				onRequestFailure(message);
			} else {
				onRequestSuccess(returnModel);
			}
		}
	}

	int retry = 0;

	protected void onRequestFailure(final String message) {
		RunningEnvironment.getInstance().runOnUiThreadDelay(new Runnable() {
			@Override
			public void run() {
				if (retry < 50) {
					ConnectionManager.getInstance().executeWhenConnected(
							new RequestConfigurationsRunnable(message));
					retry++;
				}
			}
		}, 2000L);
	}

	@SuppressWarnings("deprecation")
	protected void onRequestSuccess(ConfigModel newConfig) {
		retry = 0;
		boolean modelChanged = false;
		boolean tasksChanged = false;
		String objString = null;
		if (savedConfig != null) {
			objString = CommonUtils.getObjectString(savedConfig);
			LogUtils.d("CONFIGTASK", "resultCallBack savedModel<" + objString
					+ ">");

		}
		objString = CommonUtils.getObjectString(newConfig);
		LogUtils.d("CONFIGTASK", "resultCallBack returnModel<" + objString
				+ ">");

		if (!CommonUtils.compare(newConfig, savedConfig, ConfigModel.class)) {
			LogUtils.d("CONFIGTASK", "model changed");
			modelChanged = true;
		} else {
			LogUtils.d("CONFIGTASK", "model no change, ignore it.");
		}
		if (!CommonUtils.compare(newConfig.getTaskList(),
				savedConfig.getTaskList(), Task.class)) {
			LogUtils.d("CONFIGTASK", "tasks changed");
			tasksChanged = true;
		} else {
			LogUtils.d("CONFIGTASK", "tasks no change, ignore it.");
		}

		if (modelChanged || tasksChanged) {
			savedConfig = new ConfigModel();
			savedConfig.objectUpdate(newConfig);
		}

		if (modelChanged) {
			dispatchConfigurationChanged(newConfig);
		}
		if (tasksChanged) {
			dispatchConfigurationTasksChanged(newConfig,
					newConfig.getTaskList());
		}
	}

	public List<ClassifyModel> getConfigClassifies() {
		ConfigModel config = getCurrentConfig();
		if (config != null && config.getClassifyList() != null
				&& config.getClassifyList().size() > 0) {
			return config.getClassifyList();
		}
		return new ArrayList<ClassifyModel>();
	}

}
