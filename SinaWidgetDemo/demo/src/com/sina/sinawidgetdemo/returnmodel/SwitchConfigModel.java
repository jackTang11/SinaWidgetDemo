package com.sina.sinawidgetdemo.returnmodel;


import com.sina.engine.base.db4o.Db4oInterface;

/**
 * 配置开关
 * 
 * @author zouyulong
 * 
 */
public class SwitchConfigModel extends BaseModel implements
		Db4oInterface<SwitchConfigModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int download_button; 

	private int gift_show_tag;
	
	private int app_recommend;
	
	
	public int getDownload_button() {
		return download_button;
	}


	public void setDownload_button(int download_button) {
		this.download_button = download_button;
	}


	public int getGift_show_tag() {
		return gift_show_tag;
	}


	public void setGift_show_tag(int gift_show_tag) {
		this.gift_show_tag = gift_show_tag;
	}


	public int getApp_recommend() {
		return app_recommend;
	}


	public void setApp_recommend(int app_recommend) {
		this.app_recommend = app_recommend;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sina.engine.base.db4o.Db4oInterface#objectUpdate(java.lang.Object)
	 */
	@Override
	public void objectUpdate(SwitchConfigModel object) {
		if (object == null) {
			return;
		}
	    this.setApp_recommend(object.getApp_recommend());
	    this.setDownload_button(object.getDownload_button());
	    this.setGift_show_tag(object.getGift_show_tag());
	}
}
