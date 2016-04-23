package com.sina.sinawidgetdemo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.overlay.utils.LogUtils;
import com.sina.sinawidgetdemo.constant.PreferencesConstant;
import com.sina.sinawidgetdemo.fragment.GuideFragment.GuideOnClickListener;
import com.sina.sinawidgetdemo.usercredit.CheckStateButtonAgent;
import com.sina.sinawidgetdemo.utils.CommonUtils;
import com.sina.sinawidgetdemo.utils.PreferencesUtils;
import com.sina.sinawidgetdemo.R;

/**
 * 主架构页面
 * 
 * @author kangshaozhe
 * 
 */
public class MainFragment extends BaseFragment implements OnClickListener,
		GuideOnClickListener {

	private StartFragment startFragment;
	private MyMainFragment myMainFragment;
	private GuideFragment guideFragment;
	private long mExitTime;
	public boolean startAppLogic;
	private int guide_version;

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
			PreferencesUtils.writeInt(getActivity(),
					PreferencesConstant.GUIDE_VERSION_FILE,
					PreferencesConstant.GUIDE_VERSION_KEY,
					CommonUtils.getVersionCode(getActivity()));
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
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

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
					LogUtils.d("ADLOG", "start fragment sleeptime:" + sleepTime);
				} catch (Exception e) {
					e.printStackTrace();
				}

				turnGuideFragmentLogic();

			}
		}, 1000);

	}

	private void turnGuideFragmentLogic() {
		if (guideFragment != null && guideFragment.isPass()) {
			passGuideFragment(false);
			return;
		}
		guide_version = PreferencesUtils.getInt(getActivity(),
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
			if (guide_version == CommonUtils.getVersionCode(getActivity())) {

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

	/**
	 * 启动程序逻辑
	 */
	private void startAppLogic() {
		LogUtils.d("ENV", "startAppLogic()");
		new CheckStateButtonAgent(getActivity()).checkNetworkStatus();
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
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit()) {
				// 推出并释放
				exitLogic();
				getActivity().finish();
			} else {
				Toast.makeText(getActivity(), R.string.exit_app_twice,
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
	}

}
