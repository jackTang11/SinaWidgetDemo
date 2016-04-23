package com.sina.sinawidgetdemo.receiver;

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
				EngineManager.getInstance().getImageDownLoadManager()
						.setDenyDownLoadImg(false);
			} else {
				EngineManager.getInstance().getImageDownLoadManager()
						.setDenyDownLoadImg(true);
			}
		}
	}

}
