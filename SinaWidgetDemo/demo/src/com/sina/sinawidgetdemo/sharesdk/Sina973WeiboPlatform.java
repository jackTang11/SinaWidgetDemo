package com.sina.sinawidgetdemo.sharesdk;

import java.util.Date;

import android.app.Activity;

import com.sina.sinagame.share.entity.ShareParams;
import com.sina.sinagame.share.platforms.SinaWeiboDefaultPlatform;
import com.sina.sinagame.sharesdk.AuthorizeAdapter;
import com.sina.sinagame.usercredit.AccountItem;
import com.sina.sinagame.usercredit.AccountManager;

/**
 * 
 * @author liu_chonghui
 * 
 */
class Sina973WeiboPlatform extends SinaWeiboDefaultPlatform {

	public Sina973WeiboPlatform(Activity activity) {
		super(activity);
	}

	public Sina973WeiboPlatform(Activity activity, AuthorizeAdapter adapter) {
		super(activity, adapter);
	}

	@Override
	public void unauthorize() {
		// super.removeAccount();
		// 选择不发送登出请求
		new Thread(new Runnable() {
			@Override
			public void run() {
				onUnauthorizeSuccess(getActivity(), getPlatform(),
						getAuthorizeAdatper());
			}
		}).start();
	}

	@Override
	public void share(ShareParams params) {
		if (!isAuthorized()) {
			authorize();
			return;
		}

		AccountItem item = AccountManager.getInstance().getCurrentAccountItem();
		if (item != null && item.getExpiresin() != null
				&& new Date().getTime() >= item.getExpiresin().getTime()) {
			new AccountExpiresDialogBuilder(getActivity()).create().show();
			return;
		}

		if (params != null && params.getText() != null) {
			String text = params.getText();
			String tail = params.getWeb_url();
			params.setText(text + tail);
		}
		super.share(params);
	}
}
