package com.sina.sinawidgetdemo.sharesdk;

import android.app.Activity;

import com.android.overlay.RunningEnvironment;
import com.sina.sinagame.share.impl.Platform;
import com.sina.sinagame.share.platforms.PlatformManager;
import com.sina.sinagame.share.platforms.PlatformType;
import com.sina.sinagame.sharesdk.AuthorizeAdapter;

@SuppressWarnings("serial")
public class Sina973PlatformManager extends PlatformManager {

	static {
		RunningEnvironment.getInstance().removeManager(instance);
		instance = new Sina973PlatformManager();
		RunningEnvironment.getInstance().addManager(instance);
	}

	public Sina973PlatformManager() {
		super();
	}
	
	@Override
	public Platform getPlatform(Activity activity, PlatformType type,
			AuthorizeAdapter adapter) {
		Platform platform = super.getPlatform(activity, type, adapter);
		if (PlatformType.SinaWeibo == type) {
			platform = new Sina973WeiboPlatform(activity, adapter);
		} else if (PlatformType.QQ == type) {
			platform = new Sina973QQPlatform(activity, adapter);
		}
		return platform;
	}

}