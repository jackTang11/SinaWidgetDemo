package com.sina.sinawidgetdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.sina.sinawidgetdemo.fragment.WebDetailFragment;
import com.sina.sinawidgetdemo.R;

/**
 * Simple Web Browser Page.
 * 
 * @author liu_chonghui
 * 
 */
public class WebDetailActivity extends BaseFragmentActivity {
	protected WebDetailFragment webBrowser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_frame);
		initFragment();
	}

	public void initFragment() {
		if (webBrowser == null) {
			webBrowser = new WebDetailFragment();
		}
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		fragTransaction.add(R.id.content_frame, webBrowser);
		fragTransaction.commit();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (webBrowser != null) {
			webBrowser.onNewIntent(intent);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webBrowser != null && webBrowser.holdGoBack()) {
				return webBrowser.onKeyDown(keyCode, event);
			}
//			if (webBrowser != null && webBrowser.getRecommendation() != null) {
//				LogUtils.d("PUSHLOG", "from WebActivity to MainActivity");
//				Intent intent = new Intent(this, MainActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				intent.setAction(PushAssistant.ACTION_WEBRECOMMENDATION);
//				startActivity(intent);
//				finish();
//			} else {
				this.finish();
//			}
			overridePendingTransition(R.anim.push_still,
					R.anim.push_right_out);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
