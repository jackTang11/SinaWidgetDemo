package com.sina.sinawidgetdemo.applcation;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.android.overlay.KeepAliveService;
import com.android.overlay.RunningEnvironment;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.engine.base.config.EngineConfig;
import com.sina.engine.base.manager.EngineManager;
import com.sina.sinawidgetdemo.constant.RequestConstant;
import com.sina.sinawidgetdemo.receiver.NetChangeReceiver;
import com.sina.sinagame.share.platforms.PlatformManager;

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
		super.onCreate();
		EngineManager.init(this);
		EngineManager.getInstance().initConfig(new EngineConfig().setIsEncrypt(false)
				.setSignKey(RequestConstant.SIGN_KEY_VALUE)
				.setPartner_id(RequestConstant.PARTNER_ID_VALUE));
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).denyCacheImageMultipleSizesInMemory()
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheExtraOptions(480, 800)
//				.discCacheExtraOptions(480, 800, CompressFormat.PNG, 75, null)
				.discCacheSize(1024 * 1024 * 50)
				.memoryCacheSize(1024 * 1024 * 2)
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024)).build();
		ImageLoader.getInstance().init(config);
		
		
		if (subSystem == null) {
			subSystem = new RunningEnvironment("R.array.managers",
					"R.array.tables");
		} else {
			subSystem = RunningEnvironment.getInstance();
		}
		subSystem.onCreate(this);
		PlatformManager.getInstance().onloadPlatformInfos();

		registerNetChangeReceiver();
		
		startService(KeepAliveService.createIntent(this));
	}

	private NetChangeReceiver netChangeReceiver;

	public void registerNetChangeReceiver(){
		netChangeReceiver = new NetChangeReceiver();
		IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netChangeReceiver, mFilter);
	}
	
	public void unRegisterNetChangeReceiver(){
		if(netChangeReceiver != null){
			unregisterReceiver(netChangeReceiver);
			netChangeReceiver = null;
		}
	}
}
