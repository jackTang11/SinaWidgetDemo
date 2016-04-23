package com.sina.sinawidgetdemo.requestmodel;

import com.sina.engine.base.request.model.RequestModel;

/**
 * 新闻详情请求模型
 * @author kangshaozhe
 *
 */
public class NoParameRequestModel extends RequestModel{
	public NoParameRequestModel(String domainName, String phpName) {
		super(domainName, phpName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String action;
 	 
	 
	public void setAction(String action){
		this.action = action;
	}
	public String getAction() {
		return action;
	}
}
