package com.sina.sinawidgetdemo.sharesdk;

import android.app.Activity;

import com.sina.sinagame.share.entity.ShareParams;
import com.sina.sinagame.share.platforms.QQDefaultPlatform;
import com.sina.sinagame.sharesdk.AuthorizeAdapter;

/**
 * 
 * @author liu_chonghui
 * 
 */
class Sina973QQPlatform extends QQDefaultPlatform {

	public Sina973QQPlatform(Activity activity) {
		super(activity);
	}

	public Sina973QQPlatform(Activity activity, AuthorizeAdapter adapter) {
		super(activity, adapter);
	}

	@Override
	public void share(ShareParams params) {
		if (params != null && params.getText() != null) {
			String text = params.getText();
			String tail = params.getWeb_url();
			params.setText(text + tail);
		}
		super.share(params);
	}
}
