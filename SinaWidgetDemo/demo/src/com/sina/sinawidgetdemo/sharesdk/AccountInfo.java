package com.sina.sinawidgetdemo.sharesdk;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.sina.engine.base.db4o.Db4oInterface;
import com.sina.sinawidgetdemo.returnmodel.BaseModel;

public class AccountInfo extends BaseModel implements
		Db4oInterface<AccountInfo> {

	private static final long serialVersionUID = 1L;

	public AccountInfo() {
		super();
	}

	private String account;

	private int integral;
	private int gameDynamicCount;
	private int fetchCount;
	private int alarmCount;
	private int myGameCount;

	private int giftCount;
	private int collectCount;

	private List<Task> taskList;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("account:").append(account).append(", integral:")
				.append(integral).append(", gameDynamicCount:")
				.append(gameDynamicCount).append(", fetchCount:")
				.append(fetchCount).append(", alarmCount:").append(alarmCount)
				.append(", myGameCount:").append(myGameCount)
				.append(", giftCount:").append(giftCount)
				.append(", collectCount:").append(collectCount);
		if (taskList != null) {
			sb.append("<");
			String str = "";
			try {
				str = JSONArray.toJSONString(taskList);
			} catch (Exception e) {
				str = "";
			}
			sb.append(str);
			sb.append(">");
		}
		return sb.toString();
	}

	@Override
	public void objectUpdate(AccountInfo o) {
		if (o == null) {
			return;
		}

		setAccount(o.getAccount());
		setIntegral(o.getIntegral());
		setGameDynamicCount(o.getGameDynamicCount());
		setFetchCount(o.getFetchCount());
		setAlarmCount(o.getAlarmCount());
		setMyGameCount(o.getMyGameCount());
		setGiftCount(o.getGiftCount());
		setCollectCount(o.getCollectCount());

		if (this.taskList == null) {
			this.taskList = new ArrayList<Task>();
		} else {
			this.taskList.clear();
		}
		if (o.getTaskList() != null) {
			for (Task item : o.getTaskList()) {
				Task obj = new Task();
				obj.objectUpdate(item);
				this.taskList.add(obj);
			}
		}
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public int getGameDynamicCount() {
		return gameDynamicCount;
	}

	public void setGameDynamicCount(int gameDynamicCount) {
		this.gameDynamicCount = gameDynamicCount;
	}

	public int getFetchCount() {
		return fetchCount;
	}

	public void setFetchCount(int fetchCount) {
		this.fetchCount = fetchCount;
	}

	public int getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(int alarmCount) {
		this.alarmCount = alarmCount;
	}

	public int getMyGameCount() {
		return myGameCount;
	}

	public void setMyGameCount(int myGameCount) {
		this.myGameCount = myGameCount;
	}

	public int getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(int giftCount) {
		this.giftCount = giftCount;
	}

	public int getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public boolean isLegal() {
		return this.taskList != null;
	}
}
