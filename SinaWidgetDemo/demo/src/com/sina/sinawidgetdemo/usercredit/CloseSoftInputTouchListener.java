package com.sina.sinawidgetdemo.usercredit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 
 * @author liu_chonghui
 * 
 */
@SuppressLint("ClickableViewAccessibility")
public class CloseSoftInputTouchListener implements
		android.view.View.OnTouchListener, Runnable {
	Activity activity;

	public CloseSoftInputTouchListener(Activity activity) {
		this.activity = activity;
	}

	protected Activity getActivity() {
		return activity;
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		return onTouch();
	}

	protected boolean onTouch() {
		InputMethodManager manager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		return manager.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), 0);
	}

	@Override
	public void run() {
		onTouch();
	}
}
