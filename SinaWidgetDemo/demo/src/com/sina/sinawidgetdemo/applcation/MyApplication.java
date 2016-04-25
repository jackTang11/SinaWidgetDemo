package com.sina.sinawidgetdemo.applcation;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.imageloadercompact.ImageLoaderCompact;
import com.android.overlay.KeepAliveService;
import com.android.overlay.RunningEnvironment;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * application类
 * 
 * @author administrator
 * 
 */
public class MyApplication extends Application {
	protected RunningEnvironment subSystem;

	@Override
	public void onCreate() {
		MultiDex.install(this);

		super.onCreate();

		if (subSystem == null) {
			subSystem = new RunningEnvironment("R.array.managers",
					"R.array.tables");
		} else {
			subSystem = RunningEnvironment.getInstance();
		}
		subSystem.onCreate(this);

		ImageLoaderCompact.getInstance().init(this);
		startService(KeepAliveService.createIntent(this));
	}


	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
