package com.sina.sinawidgetdemo.custom.timergallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.Gallery;

public class MyGallery extends Gallery {

    public MyGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setAnimationDuration(500);
        setAnimationDuration(1000);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        int keyCode;
        if(e2.getX()>e1.getX()){
            keyCode= KeyEvent.KEYCODE_DPAD_LEFT;
        }else{
            keyCode=KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode, null);
        return false;//super.onFling(e1, e2, velocityX, velocityY);
    }

//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
//			float distanceY) {
//		if(e2.getAction() ==  MotionEvent.ACTION_UP) {
//			if(distanceX>0) {
//				distanceX = distanceX+15;
//			} else {
//				distanceX = distanceX-15;
//			}
//		}
//
//		return super.onScroll(e1, e2, distanceX, distanceY);
//	}

    boolean touchEnable = true;

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