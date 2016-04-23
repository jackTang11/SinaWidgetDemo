package com.sina.sinawidgetdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.android.overlay.connection.ConnectionType;
import com.sina.sinawidgetdemo.fragment.MainFragment;
import com.sina.sinawidgetdemo.statistics.StatisticsManager;
import com.sina.sinawidgetdemo.R;

/**
 * 主页
 * @author kangshaozhe
 *
 */
public class MainActivity extends BaseFragmentActivity{
	public MainFragment mainFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StatisticsManager.init(getApplicationContext());
		setContentView(R.layout.main_activity);
		initFragment();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		if(mainFragment != null){
			mainFragment.onNewIntent(intent);
			return;
		}
		super.onNewIntent(intent);
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mainFragment = null;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	/**
	 * 初始化fragment
	 */
	public void initFragment(){
		mainFragment = new MainFragment();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.add(R.id.main_content, mainFragment);
		fragTransaction.commit(); 
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mainFragment != null){
        	return mainFragment.onKeyDown(keyCode, event);
        }
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConnectionChanged(ConnectionType type) {
		if (mainFragment == null || !mainFragment.startAppLogic) {
			return;
		}
		super.onConnectionChanged(type);
	}

	@Override
	public void onConnectionClosed() {
		if (mainFragment == null || !mainFragment.startAppLogic) {
			return;
		}
		super.onConnectionClosed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(mainFragment != null){
			mainFragment.onActivityResult(requestCode, resultCode, data);
		}
	}
	
	
}
