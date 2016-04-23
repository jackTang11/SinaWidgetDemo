package com.sina.sinawidgetdemo.requestmodel;

import com.sina.engine.base.request.model.RequestModel;
/**
 * 应用配置信息model
 * @author kangshaozhe
 *
 */
public class ConfigRequestModel extends RequestModel {

	public ConfigRequestModel(String domainName, String phpName) {
		super(domainName, phpName);
	}

	private static final long serialVersionUID = 1L;
	
	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
