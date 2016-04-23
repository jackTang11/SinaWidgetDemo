package com.sina.sinawidgetdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.android.overlay.OnConnectionChangedListener;
import com.android.overlay.RunningEnvironment;
import com.android.overlay.connection.ConnectionType;
import com.sina.sinawidgetdemo.usercredit.CheckStateButtonAgent;

/**
 * activity基类
 * 
 * @author kangshaozhe
 * 
 */
public class BaseFragmentActivity extends FragmentActivity implements OnConnectionChangedListener {
	public LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
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
	}

	@Override
	protected void onPause() {
		RunningEnvironment.getInstance().removeUIListener(
				OnConnectionChangedListener.class, this);
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		RunningEnvironment.getInstance().addUIListener(
				OnConnectionChangedListener.class, this);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onConnectionChanged(ConnectionType type) {
		new CheckStateButtonAgent(this).onConnectionChanged(type);
	}

	@Override
	public void onConnectionClosed() {
		new CheckStateButtonAgent(this).onConnectionClosed();
	}

}
