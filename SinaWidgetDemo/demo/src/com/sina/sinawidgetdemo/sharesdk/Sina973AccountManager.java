package com.sina.sinawidgetdemo.sharesdk;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.overlay.RunningEnvironment;
import com.db4o.query.Predicate;
import com.sina.engine.base.db4o.Db4oImpl;
import com.sina.engine.base.utils.LogUtils;
import com.sina.sinawidgetdemo.constant.DBConstant;
import com.sina.sinagame.share.impl.OnAccountRemovedListener;
import com.sina.sinagame.usercredit.AccountItem;
import com.sina.sinagame.usercredit.AccountManager;

@SuppressWarnings("serial")
class Sina973AccountManager extends AccountManager {

	static {
		RunningEnvironment.getInstance().removeManager(instance);
		instance = new Sina973AccountManager();
		RunningEnvironment.getInstance().addManager(instance);
	}

	public Sina973AccountManager() {
		super();
	}

	protected String getDbName() {
		return DBConstant.ACCOUNTS_DB_NAME;
	}

	@Override
	public void onLoad() {
		final Map<String, AccountItem> accounts = new HashMap<String, AccountItem>();
		Db4oImpl cursor = new Db4oImpl(getDbName());
		try {
			cursor.open();
			List<AccountModel> list = cursor.getList(AccountModel.class);
			if (list != null) {
				for (AccountModel item : list) {
					if (item == null) {
						continue;
					}
					accounts.put(item.getAccount(), item);
				}
			}
		} finally {
			cursor.close();
		}
		RunningEnvironment.getInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				onLoaded(accounts);
			}
		});
	}

	public AccountItem addAccount(String userName, String nickName,
			String avatar, String gender, String protocol, String unionName, String resource,
			String password, int priority, Date lastSync, Date expiresin) {
		LogUtils.d("ACCOUNT", "#1 requestAddAccount:" + userName
				+ ", nickname:" + nickName + ", avatar:" + avatar + ", gender:"
				+ gender + ", protocol:" + protocol + ", resource:" + resource
				+ ", password:" + password + ", priority:" + priority
				+ ", lastSync:" + lastSync + ", expiresin:" + expiresin);
		boolean accountExist = false;
		AccountItem accountItem = accountItems.get(userName);
		if (accountItem != null) {
			accountExist = true;
			boolean changed = accountItem.checkChanged(userName, nickName,
					avatar, gender, password, resource, priority, protocol,
					unionName, expiresin);
			if (!changed) {
				LogUtils.d("ACCOUNT", "#2 CheckAccount:" + userName
						+ ", Check NoChanges.");
				return accountItem;
			} else {
				accountItem.update(userName, nickName, avatar, gender,
						password, resource, priority, protocol, unionName, lastSync,
						expiresin);
				LogUtils.d("ACCOUNT", "#2 CheckAccount:" + userName
						+ ", Check Changed:" + userName + ", nickname:"
						+ nickName + ", avatar:" + avatar + ", gender:"
						+ gender + ", protocol:" + protocol + ", resource:"
						+ resource + ", password:" + password + ", priority:"
						+ priority + ", lastSync:" + lastSync + ", expiresin:"
						+ expiresin);
			}
		} else {
			LogUtils.d("ACCOUNT", "#2 CheckAccount:" + userName
					+ ", Not Exist, Create.");
			accountItem = new AccountModel(userName, nickName, avatar, gender,
					protocol, unionName, resource, password, priority, lastSync, expiresin);
		}
		if (ACTION_ACCOUNT_PRIORITY == accountItem.getPriority()) {
			String account = accountItem.getAccount();
			for (AccountItem item : accountItems.values()) {
				if (item != null && item.getAccount() != null
						&& !item.getAccount().equalsIgnoreCase(account)
						&& ACTION_ACCOUNT_PRIORITY == item.getPriority()) {
					item.setPriority(INACTION_ACCOUNT_PRIORITY);
					requestToWriteAccount(item);
				}
			}
		}
		LogUtils.d(
				"ACCOUNT",
				"#3 WriteAccount:" + accountItem.getAccount() + ", write:"
						+ accountItem.getAccount() + ", "
						+ accountItem.getNickName() + ", "
						+ accountItem.getAvatar() + ", "
						+ accountItem.getGender() + ", "
						+ accountItem.getProtocol() + ", "
						+ accountItem.getResource() + ", "
						+ accountItem.getPassword() + ", "
						+ accountItem.getPriority() + ", "
						+ accountItem.getLastSync() + ", "
						+ accountItem.getExpiresin());
		accountItems.put(accountItem.getAccount(), accountItem);
		requestToWriteAccount(accountItem);

		if (!accountExist) {
			LogUtils.d("ACCOUNT", "#4 PostAccount:" + accountItem.getAccount()
					+ ", Post Add & Changed.");
			addAccount(accountItem);
			onAccountChanged(accountItem.getAccount());
			onAccountStateAdded(accountItem);
		} else {
			LogUtils.d("ACCOUNT", "#4 PostAccount:" + accountItem.getAccount()
					+ ", Post Changed.");
			onAccountChanged(accountItem.getAccount());
		}
		return accountItem;
	}

	protected void requestToWriteAccount(final AccountItem accountItem) {
		final String uesrName = accountItem.getUserName();
		final String nickName = accountItem.getNickName();
		final String avatar = accountItem.getAvatar();
		final String gender = accountItem.getGender();
		final String protocol = accountItem.getProtocol();
		final String unionName = accountItem.getUnionName();
		final String resource = accountItem.getResource();
		final String password = accountItem.getPassword();
		final int priority = accountItem.getPriority();
		final Date lastSync = accountItem.getLastSync();
		final Date expiresin = accountItem.getExpiresin();
		final AccountModel model = new AccountModel(uesrName, nickName, avatar,
				gender, protocol, unionName, resource, password, priority, lastSync,
				expiresin);
		RunningEnvironment.getInstance().runInBackground(new Runnable() {
			@Override
			public void run() {
				Db4oImpl cursor = new Db4oImpl(getDbName()).open();
				try {
					cursor.save(model, new Predicate<AccountModel>() {
						@Override
						public boolean match(AccountModel querModel) {
							if (querModel == null) {
								return false;
							}
							if (querModel.getAccount().equalsIgnoreCase(
									model.getAccount())) {
								return true;
							}
							return false;
						}
					}, AccountModel.class.getName());
				} finally {
					cursor.close();
				}
			}
		});
	}

	protected void removeAccountWithoutCallback(final String account) {
		final AccountItem accountItem = getAccount(account);
		if (accountItem == null) {
			return;
		}
		RunningEnvironment.getInstance().runInBackground(new Runnable() {
			@Override
			public void run() {
				Db4oImpl cursor = new Db4oImpl(getDbName()).open();
				try {
					cursor.delete(new Predicate<AccountModel>() {
						@Override
						public boolean match(AccountModel querModel) {
							if (querModel == null) {
								return true;
							}
							String querId = querModel.getAccount();
							String deleId = accountItem.getAccount();
							if (querId.equalsIgnoreCase(deleId)) {
								return true;
							}
							return false;
						}
					}, AccountModel.class.getName());
				} finally {
					cursor.close();
				}
			}
		});
		accountItems.remove(account);
		for (OnAccountRemovedListener listener : RunningEnvironment
				.getInstance().getManagers(OnAccountRemovedListener.class)) {
			listener.onAccountRemoved(accountItem);
		}
	}

}
