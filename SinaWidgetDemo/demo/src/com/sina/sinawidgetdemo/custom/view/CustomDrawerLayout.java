package com.sina.sinawidgetdemo.custom.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomDrawerLayout extends DrawerLayout {

	public CustomDrawerLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomDrawerLayout(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public CustomDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(super.onInterceptTouchEvent(ev)&&ev.getAction()==MotionEvent.ACTION_MOVE)
			return true;
		return false;
	}

}
