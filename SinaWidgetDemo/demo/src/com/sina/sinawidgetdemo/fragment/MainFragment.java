package com.sina.sinawidgetdemo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.engine.base.manager.EngineManager;
import com.sina.engine.base.request.listener.RequestDataListener;
import com.sina.engine.base.request.model.TaskModel;
import com.sina.engine.base.utils.LogUtils;
import com.sina.sinawidgetdemo.activity.WebBrowserActivity;
import com.sina.sinawidgetdemo.constant.IntentConstant;
import com.sina.sinawidgetdemo.constant.PreferencesConstant;
import com.sina.sinawidgetdemo.constant.StatisticsConstant;
import com.sina.sinawidgetdemo.fragment.GuideFragment.GuideOnClickListener;
import com.sina.sinawidgetdemo.request.process.RequestConfigProcess;
import com.sina.sinawidgetdemo.returnmodel.SwitchConfigModel;
import com.sina.sinawidgetdemo.sharesdk.AccountInfoManager;
import com.sina.sinawidgetdemo.sharesdk.SyncReason;
import com.sina.sinawidgetdemo.statistics.StatisticsManager;
import com.sina.sinawidgetdemo.switchconfig.SwitchConfigManager;
import com.sina.sinawidgetdemo.usercredit.CheckStateButtonAgent;
import com.sina.sinawidgetdemo.usercredit.ConfigurationManager;
import com.sina.sinawidgetdemo.utils.CommonUtils;
import com.sina.sinawidgetdemo.utils.PreferencesUtils;
import com.sina.sinawidgetdemo.versionupdate.VersionUpdateManager;
import com.sina.sinawidgetdemo.R;
import com.sina.sinaadsdk.constant.RequestConstant;
import com.sina.sinaadsdk.returnmodel.AdModel;
import com.sina.sinaadsdk.sdk.AdSDK;
import com.sina.sinaadsdk.sdk.AdSDK.AdSDKListener;

/**
 * 主架构页面
 * 
 * @author kangshaozhe
 * 
 */
public class MainFragment extends BaseFragment implements OnClickListener,
		GuideOnClickListener {

	private AdSDK mAdSDK;
	private StartFragment startFragment;
	private AdFragment adFragment;
	private MyMainFragment myMainFragment;
	private GuideFragment guideFragment;
	private long mExitTime;
	public boolean startAppLogic;
	private int guide_version;
	private static final int REMOVE_START_FRAGMENT = 10120;
	private static final int CHANGE_NUMBER_ANIMATION = 10121;
	private static final int REMOVE_AD_FRAGMENT = 10122;
	private Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			if (msg.what == REMOVE_START_FRAGMENT) {
				if (myActivity != null && !myActivity.isFinishing()) {
					removeStartFragment();
					turnGuideFragmentLogic();
				}
			} else if (msg.what == REMOVE_AD_FRAGMENT) {
				if (myActivity != null && !myActivity.isFinishing()) {
					long totalTime = AdFragment.DEFAULT_SHOWTIME_DURATION;
					if (adFragment != null)
						totalTime = adFragment.getShowTime();
					try {
						Thread.sleep(totalTime * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					turnGuideFragmentLogic();
				}
			}
			// else if (msg.what == CHANGE_NUMBER_ANIMATION) {
			// int number = msg.arg1;
			// if (myActivity != null && !myActivity.isFinishing()) {
			// if (startFragment != null && startFragment.isAdded()) {
			// startFragment.setNumber(number);
			// }
			// }
			// }
			super.dispatchMessage(msg);
		}
	};

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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initFragment();
		passStartFragment();
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
		mView = inflater.inflate(R.layout.main_fragment, container, false);
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
		// TODO Auto-generated method stub
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

	private void initAdSDK() {
		mAdSDK = new AdSDK(getActivity(), true);
		mAdSDK.setAdSDKListener(new AdSDKListener() {

			@Override
			public void onShowTimeOver() {
				// myActivity.finish();
			}

			@Override
			public void onClickAdAction(AdModel model) {
				if (model == null)
					return;
				// 链接
				if (model.getType() == RequestConstant.WEB_CLICK_ACTION) {
					// Uri uri = Uri.parse(model.getParam());
					// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					// myActivity.startActivity(intent);
					Intent intent = new Intent(getActivity(),
							WebBrowserActivity.class);
					intent.putExtra("url", model.getParam());
					startActivity(intent);
				}
			}
		});
	}

	/**
	 * 跳转逻辑
	 */
	public void intView(View view) {

	}

	/**
	 * 初始化fragment
	 */
	public void initFragment() {
		startFragment = new StartFragment();
		adFragment = new AdFragment();
		myMainFragment = new MyMainFragment();
		guideFragment = new GuideFragment().setGuideOnClickListener(this);
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.add(R.id.main_fragment_content, startFragment);
		fragTransaction.commitAllowingStateLoss();
	}

	public void passGuideFragment(boolean isSave) {
		removeGuideFragment();
		if (myMainFragment == null) {
			return;
		}
		if (isSave)
			PreferencesUtils.writeInt(myActivity,
					PreferencesConstant.GUIDE_VERSION_FILE,
					PreferencesConstant.GUIDE_VERSION_KEY,
					CommonUtils.getVersionCode(myActivity));
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.replace(R.id.main_fragment_content, myMainFragment);
		fragTransaction.commitAllowingStateLoss();

	}

	/**
	 * 跳过启动页
	 */
	public void passStartFragment() {
		if (guideFragment == null) {
			return;
		}
		// try {
		// Thread.sleep(sleepTime);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				startAppLogic();
				startAppLogic = true;

				long endTime = System.currentTimeMillis();
				long sleepTime = 1000 - (endTime - startTime);
				if (sleepTime < 0) {
					sleepTime = 0;
				}
				try {
					Thread.sleep(sleepTime);
					LogUtils.d("ADLOG", "start fragment sleeptime:"+sleepTime);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated method stub
				if (!mAdSDK.checkHasValidAd(com.sina.sinawidgetdemo.constant.RequestConstant.DOMAIN_NAME)) {
					turnGuideFragmentLogic();
				} else {
					startAdFragment();
					// Message m = Message.obtain();
					// m.what = REMOVE_AD_FRAGMENT;
					// mHandler.sendMessage(m);
					long totalTime = AdFragment.DEFAULT_SHOWTIME_DURATION;
					if (adFragment != null)
						totalTime = adFragment.getShowTime();
					LogUtils.d("ADLOG", "ad fragment sleeptime:"+totalTime * 1000);
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							turnGuideFragmentLogic();
						}
					}, totalTime* 1000);
				}

			}
		}, 1000);

	}

	/**
	 * 跳过广告页
	 */
	// public void passAdFragment() {
	// if (adFragment == null) {
	// return;
	// }
	//
	// new Handler().post(new Runnable() {
	//
	// @Override
	// public void run() {
	// long totalTime = AdFragment.DEFAULT_SHOWTIME_DURATION;
	// if (adFragment != null)
	// totalTime = adFragment.getShowTime();
	// while (true) {
	// Message m = Message.obtain();
	// m.arg1 = (int) totalTime;
	// // m.what = CHANGE_NUMBER_ANIMATION;
	// // mHandler.sendMessage(m);
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// totalTime -= 1;
	// if (totalTime <= 0)
	// break;
	// }
	// mHandler.sendEmptyMessage(REMOVE_AD_FRAGMENT);
	// }
	// });
	//
	// }

	private void turnGuideFragmentLogic() {
		if (guideFragment != null && guideFragment.isPass()) {
			passGuideFragment(false);
			return;
		}
		SwitchConfigModel model = SwitchConfigManager
				.getSwitchConfigModel(myActivity);
		if (model.getGift_show_tag() == SwitchConfigManager.SWITCH_CLOSE) {
			passGuideFragment(false);
			return;
		}
		guide_version = PreferencesUtils.getInt(myActivity,
				PreferencesConstant.GUIDE_VERSION_FILE,
				PreferencesConstant.GUIDE_VERSION_KEY, 0);
		// 屏蔽跳过引导页
		if (versionCompare()) {
			passGuideFragment(true);
		} else {
			FragmentManager fm = getChildFragmentManager();
			FragmentTransaction fragTransaction = fm.beginTransaction();
			fragTransaction.replace(R.id.main_fragment_content, guideFragment);
			fragTransaction.commitAllowingStateLoss();
		}
	}

	/**
	 * 如果当前版本等于储存版本 返回true
	 * 
	 * @return
	 */
	private boolean versionCompare() {
		try {
			// //这个版本在覆盖安装上个版本后不再出现引导界面
			// if(version > 0){
			// return true;
			// }
			if (guide_version == CommonUtils.getVersionCode(myActivity)) {

				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	private void removeStartFragment() {
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.remove(startFragment);
		fragTransaction.commitAllowingStateLoss();
	}

	private void removeGuideFragment() {
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.remove(guideFragment);
		fragTransaction.commitAllowingStateLoss();
	}

	private void removeAdFragment() {
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.remove(adFragment);
		fragTransaction.commitAllowingStateLoss();
	}

	// TODO
	private void startAdFragment() {
		adFragment.setAdData(mAdSDK);
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.add(R.id.main_fragment_content, adFragment);
		fragTransaction.commitAllowingStateLoss();
	}

	/**
	 * 启动程序逻辑
	 */
	private void startAppLogic() {
		SwitchConfigManager.requestSwitchConfig(myActivity);
		StatisticsManager.sendEvent(myActivity.getApplicationContext(),
				StatisticsConstant.STARTAPP, "", null);
		LogUtils.d("ENV", "startAppLogic()");
		ConfigurationManager.getInstance().requestConfigurations();
		AccountInfoManager.getInstance().requestCurrentAccountInfoForReason(
				SyncReason.ALL);
		requestVersionUpdate();
		new CheckStateButtonAgent(getActivity()).checkNetworkStatus();
		initAdSDK();
	}

	/**
	 * 请求config数据
	 */
	public void requestConfig() {
		RequestConfigProcess.startrRequestConfig(new RequestDataListener() {

			@Override
			public void resultCallBack(TaskModel taskModel) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 版本更新逻辑
	 */
	private void requestVersionUpdate() {
		VersionUpdateManager updateDialog = VersionUpdateManager
				.getInstance(myActivity);
		updateDialog.request();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

	}

	/**
	 * 判断按两次返回键是否能够退出
	 * 
	 * @return
	 */
	private boolean isExit() {
		if ((System.currentTimeMillis() - mExitTime) > 800) {
			mExitTime = System.currentTimeMillis();
			return false;
		}
		return true;
	}

	/**
	 * 程序推出逻辑
	 */
	private void exitLogic() {
		startFragment = null;
		myMainFragment = null;
		guideFragment = null;
		mExitTime = 0;
		EngineManager.getInstance().destory();
		ImageLoader.getInstance().clearMemoryCache();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit()) {
				// 推出并释放
				exitLogic();
				myActivity.finish();
			} else {
				Toast.makeText(myActivity, R.string.exit_app_twice,
						Toast.LENGTH_SHORT).show();
			}
			return true;
		}
		return false;
	}

	@Override
	public void finishGuide() {
		// TODO Auto-generated method stub
		passGuideFragment(true);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (myMainFragment != null) {
			myMainFragment.onActivityResult(requestCode, resultCode, data);
		}
	}

	public void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		String backType = intent
				.getStringExtra(IntentConstant.INTENT_BACK_HOME_KEY);
		if (IntentConstant.INTENT_BACK_HOME_VALUE.equals(backType)) {
			// 返回主页
			if (myMainFragment != null) {
				myMainFragment
						.clickReplaceFragment(R.id.main_fragment_title_home);
			}
		}
	}

}
