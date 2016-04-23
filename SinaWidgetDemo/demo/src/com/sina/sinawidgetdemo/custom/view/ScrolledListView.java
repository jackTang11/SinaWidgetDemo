package com.sina.sinawidgetdemo.custom.view;

import com.handmark.pulltorefresh.library.PullToRefreshWebView.OnScrollChangedListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ScrolledListView extends ListView {
	
	boolean touchEnable = true;
	
	public ScrolledListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ScrolledListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ScrolledListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	private OnScrollChangedListener mScrollListener;
	
	public void setOnScrollChangedListener(OnScrollChangedListener l) {
		mScrollListener = l;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mScrollListener != null) {
			mScrollListener.onScrollChanged(l, t, oldl, oldt);
		}
	}

	public void setTouchEnable(boolean enable) {
		touchEnable = enable;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return touchEnable ? super.onInterceptTouchEvent(ev) : false;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return touchEnable ? super.onTouchEvent(ev) : false;
	}
}