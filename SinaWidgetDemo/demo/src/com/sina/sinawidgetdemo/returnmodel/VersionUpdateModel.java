package com.sina.sinawidgetdemo.returnmodel;


import com.sina.engine.base.db4o.Db4oInterface;

/**
 * 新闻详情模型
 * 
 * @author kangshaozhe
 * 
 */
public class VersionUpdateModel extends BaseModel implements
		Db4oInterface<VersionUpdateModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String version;
	private int version_id;
	private String url;
	private String version_info;
	private String os;

	
	 
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getVersion_id() {
		return version_id;
	}

	public void setVersion_id(int version_id) {
		this.version_id = version_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion_info() {
		return version_info;
	}

	public void setVersion_info(String version_info) {
		this.version_info = version_info;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sina.engine.base.db4o.Db4oInterface#objectUpdate(java.lang.Object)
	 */
	@Override
	public void objectUpdate(VersionUpdateModel object) {
		if (object == null) {
			return;
		}
		this.setOs(object.getOs());
		this.setUrl(object.getUrl());
		this.setVersion(object.getVersion());
		this.setVersion_id(object.getVersion_id());
		this.setVersion_info(object.getVersion_info());
	}
}
