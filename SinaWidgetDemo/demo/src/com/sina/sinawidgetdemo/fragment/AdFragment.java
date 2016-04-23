package com.sina.sinawidgetdemo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.sina.engine.base.manager.EngineManager;
import com.sina.sinawidgetdemo.activity.WebBrowserActivity;
import com.sina.sinawidgetdemo.constant.IntentConstant;
import com.sina.sinawidgetdemo.R;
import com.sina.sinaadsdk.constant.RequestConstant;
import com.sina.sinaadsdk.returnmodel.AdModel;
import com.sina.sinaadsdk.sdk.AdSDK;
import com.sina.sinaadsdk.sdk.AdSDK.AdSDKListener;

/**
 * 启动页
 * 
 * @author kangshaozhe
 * 
 */
public class AdFragment extends BaseFragment implements OnClickListener {
	private AdSDK mAdSDK;
	public static long DEFAULT_SHOWTIME_DURATION = 2;

	/**
	 * 当fragment和activity关联之后，调用这个方法
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void setAdData(AdSDK mAdSDK){
		this.mAdSDK = mAdSDK;
	} 

	/**
	 * 创建fragment中的视图的时候，调用这个方法。
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.ad_fragment, container, false);
		intView(mView);
		return mView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * 当activity的onCreate()方法被返回之后，调用这个方法。
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * 当fragment中的视图被移除的时候，调用这个方法。
	 */

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	/**
	 * 当fragment和activity分离的时候，调用这个方法。
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/**
	 * 跳转逻辑
	 */
	public void intView(View view) {
		final ViewGroup parentView = (ViewGroup) mView
				.findViewById(R.id.ad_full_layout);
		final ViewGroup bottomGroup = (ViewGroup) mView
				.findViewById(R.id.ad_bottom_layout);
		final ViewGroup aboveGroup = (ViewGroup) mView
				.findViewById(R.id.ad_above_layout);
		if(mAdSDK!=null)
		mAdSDK.loadAd(parentView, aboveGroup, bottomGroup);
	}

	public long getShowTime() {
		long animationDuration = DEFAULT_SHOWTIME_DURATION;
		if (mAdSDK == null) {
			return animationDuration;
		}
		try {
			long seconds = mAdSDK.getShowTime();
			Log.d("ADLOG", "seconds"+seconds * 1000);
			if (seconds > 0 && seconds < 10) {
				animationDuration = seconds;
			} else {
				animationDuration = DEFAULT_SHOWTIME_DURATION;
			}
		} catch (Exception e) {
			e.printStackTrace();
			animationDuration = DEFAULT_SHOWTIME_DURATION;
		}
		return animationDuration;
	}

//	public void setNumber(int number) {
//		int resId = 0;
//		switch (number) {
//		case 0:
//			break;
//		case 1:
//			resId = R.drawable.one;
//			break;
//		case 2:
//			resId = R.drawable.two;
//			break;
//		case 3:
//			resId = R.drawable.three;
//			break;
//		case 4:
//			resId = R.drawable.four;
//			break;
//		}
//		mAdSDK.showNumberResId(resId);
//	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
	}

}
