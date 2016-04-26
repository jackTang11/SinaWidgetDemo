package com.sina.sinawidgetdemo.custom.timergallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class BannerGallery extends Gallery {

    public BannerGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAnimationDuration(1000);
    }

    BannerGalleryContainer container;

    public void setMyGalleryContainer(BannerGalleryContainer container) {
        this.container = container;
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
        boolean flag = false;

        onKeyDown(keyCode, null);

        return flag;
    }

    boolean toRight = false;
    boolean toLeft = false;

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {
        if(e2.getX() > e1.getX()){
            toLeft = true;
        } else {
            toRight = true;
        }

        if ((disableDpadLeft && toLeft) || (disableDpadRight && toRight)) {
            if (container != null) {
                container.setInterceptEvent(true);
            }
            return true;
        } else {
            if (container != null) {
                container.setInterceptEvent(false);
            }
        }

        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    boolean disableDpadLeft, disableDpadRight;

    public void setDisableDpadMotion(boolean disableDpadLeft,
                                     boolean disableDpadRight) {
        this.disableDpadLeft = disableDpadLeft;
        this.disableDpadRight = disableDpadRight;
        toLeft = false;
        toRight = false;
        if (container != null) {
            container.setInterceptEvent(false);
        }
    }

    public void cleanState() {
        this.disableDpadLeft = false;
        this.disableDpadRight = false;
        toLeft = false;
        toRight = false;
        if (container != null) {
            container.setInterceptEvent(false);
        }
    }

}