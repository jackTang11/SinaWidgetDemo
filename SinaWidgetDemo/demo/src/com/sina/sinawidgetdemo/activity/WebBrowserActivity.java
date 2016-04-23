package com.sina.sinawidgetdemo.activity;

import com.sina.sinawidgetdemo.fragment.WebBrowserFragment;

/**
 * Web Browser Page support user control.
 * 
 * @author liu_chonghui
 * 
 */
public class WebBrowserActivity extends WebDetailActivity {

	@Override
	public void initFragment() {
		webBrowser = new WebBrowserFragment();
		super.initFragment();
	}

}