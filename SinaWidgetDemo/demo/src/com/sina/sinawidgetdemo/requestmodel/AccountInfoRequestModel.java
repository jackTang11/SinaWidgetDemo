package com.sina.sinawidgetdemo.requestmodel;

import com.sina.engine.base.request.model.RequestModel;

public class AccountInfoRequestModel extends RequestModel {

	private static final long serialVersionUID = 1L;

	public AccountInfoRequestModel(String domainName, String phpName) {
		super(domainName, phpName);
	}

	private String action;
	private String uid;
	private String taskId;
	private String syncReseaon;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSyncReseaon() {
		return syncReseaon;
	}

	public void setSyncReseaon(String syncReseaon) {
		this.syncReseaon = syncReseaon;
	}

}