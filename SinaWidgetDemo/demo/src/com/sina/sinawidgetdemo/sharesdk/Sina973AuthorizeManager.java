package com.sina.sinawidgetdemo.sharesdk;

import com.android.overlay.RunningEnvironment;
import com.sina.sinagame.sharesdk.AuthorizeAdapter;
import com.sina.sinagame.sharesdk.AuthorizeManager;

@SuppressWarnings("serial")
public class Sina973AuthorizeManager extends AuthorizeManager {

	static {
		RunningEnvironment.getInstance().removeManager(instance);
		instance = new Sina973AuthorizeManager();
		RunningEnvironment.getInstance().addManager(instance);
	}

	public Sina973AuthorizeManager() {
		super();
	}

	@Override
	public AuthorizeAdapter getAuthorizeAdapter() {
		return new Sina973AuthorizeAdapter();
	}
}
