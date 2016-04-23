package com.sina.sinawidgetdemo.returnmodel;

import java.util.ArrayList;
import java.util.List;

import com.sina.engine.base.db4o.Db4oInterface;
import com.sina.sinawidgetdemo.sharesdk.Task;

/**
 * 配置信息数据模型
 * 
 * @author kangshaozhe
 * 
 */
public class ConfigModel extends BaseModel implements
		Db4oInterface<ConfigModel> {
	private static final long serialVersionUID = 1L;

	private String recodeUrl;
	private int version;
	private List<Task> taskList = new ArrayList<Task>();
	private List<ClassifyModel> classifyList = new ArrayList<ClassifyModel>();
	private List<EvidenceChannelModel> evidenceList = new ArrayList<EvidenceChannelModel>();
	private String forumUrl;

	public void setRecodeUrl(String recodeUrl) {
		this.recodeUrl = recodeUrl;
	}

	public String getRecodeUrl() {
		return recodeUrl;
	}

	public void setForumUrl(String forumUrl) {
		this.forumUrl = forumUrl;
	}

	public String getForumUrl() {
		return forumUrl;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList.clear();
		this.taskList.addAll(taskList);
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setClassifyList(List<ClassifyModel> classifyList) {
		this.classifyList.clear();
		if (classifyList != null)
			this.classifyList.addAll(classifyList);
	}

	public List<ClassifyModel> getClassifyList() {
		return classifyList;
	}

	public void setEvidenceList(List<EvidenceChannelModel> evidenceList) {
		this.evidenceList.clear();
		this.evidenceList.addAll(evidenceList);
	}

	public List<EvidenceChannelModel> getEvidenceList() {
		return evidenceList;
	}

	@Override
	public void objectUpdate(ConfigModel object) {
		if (object == null) {
			return;
		}
		setForumUrl(object.getForumUrl());
		setRecodeUrl(object.getRecodeUrl());
		setTaskList(object.getTaskList());
		setVersion(object.getVersion());
		setClassifyList(object.getClassifyList());
	}

}
