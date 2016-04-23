package com.sina.sinawidgetdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.view.CustomDrawerLayout;

public class DrawerLayoutFragment extends BaseFragment {
	private CustomDrawerLayout mDrawerLayout;
	private DrawerLayoutDrawerFragment mDrawerFragment;
	private DrawerLayoutContentFragment mContentFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.drawerlayout_fragment, container,
				false);
		initView();
		initFragment();
		return mView;
	}

	private void initView() {
		mDrawerLayout = (CustomDrawerLayout) mView
				.findViewById(R.id.drawerlayout);
		mDrawerLayout
				.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
					@Override
					public void onDrawerClosed(View drawerView) {
						super.onDrawerClosed(drawerView);
						mDrawerLayout
								.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
					}

					@Override
					public void onDrawerOpened(View drawerView) {
						super.onDrawerOpened(drawerView);
						mDrawerLayout
								.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
					}
				});
		mDrawerLayout.setScrimColor(0xffffff);
	}

	private void initFragment() {
		mDrawerFragment = new DrawerLayoutDrawerFragment();
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.add(R.id.drawer_framelayout, mDrawerFragment);
		mContentFragment = new DrawerLayoutContentFragment();
		fragTransaction.add(R.id.content_framelayout, mContentFragment);
		fragTransaction.commit();
	}
	
	/**
	 * 作为conetent中listview 事件调用方法
	 * @param data
	 */
	public void drawerAction(String data){
		mDrawerLayout.openDrawer(Gravity.RIGHT);
		mDrawerFragment.insertData(data); 
	}
	
	/**
	 * 作为drawer中listview 事件调用方法
	 * @param data
	 */
	public void contentAction(){
		mContentFragment.toggleHeader();
	}
}