package com.sina.sinawidgetdemo.receiver;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.engine.base.manager.EngineManager;
import com.sina.sinawidgetdemo.utils.CommonUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class NetChangeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			if (CommonUtils.isWifiDownload(context)) {
				ImageLoader.getInstance().denyNetworkDownloads(false);
				EngineManager.getInstance().getImageDownLoadManager()
						.setDenyDownLoadImg(false);
			} else {
				ImageLoader.getInstance().denyNetworkDownloads(true);
				EngineManager.getInstance().getImageDownLoadManager()
						.setDenyDownLoadImg(true);
			}
		}
	}

}
