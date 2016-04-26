package com.sina.sinawidgetdemo.custom.timergallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyGalleryContainer extends LinearLayout {
    public MyGalleryContainer(Context context) {
        super(context);
    }

    public MyGalleryContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGalleryContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    boolean interceptEvent = false;

    public void setInterceptEvent(boolean interceptEvent) {
        this.interceptEvent = interceptEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (interceptEvent) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
