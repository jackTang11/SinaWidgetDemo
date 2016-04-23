package com.sina.sinawidgetdemo.usercredit;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * 
 * @author liu_chonghui
 * 
 */
public class SyncHeightLayoutScheduler implements OnGlobalLayoutListener {
	final int repeat = 20;
	boolean waitingForExactViewHeight = true;
	View master;
	View[] slaves;
	int oldHeight = -1;
	int viewHeight;
	int count;

	public SyncHeightLayoutScheduler(View master) {
		this.master = master;
		this.master.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	public SyncHeightLayoutScheduler(View master, View[] slaves) {
		this(master);
		this.slaves = slaves;
	}

	@Override
	public void onGlobalLayout() {
		int height = master == null ? -1 : master.getHeight();
		if (waitingForExactViewHeight && height > 0) {
			viewHeight = master.getHeight();
			if (oldHeight != viewHeight) {
				count = 0;
				if (slaves != null) {
					for (View slave : slaves) {
						if (slave == null || View.GONE == slave.getVisibility()) {
							continue;
						}
						LayoutParams broParams = slave.getLayoutParams();
						if (broParams != null) {
							broParams.height = viewHeight;
							slave.setLayoutParams(broParams);
						}
					}
				}
				updateHeight(viewHeight);
				oldHeight = viewHeight;
			} else {
				count++;
			}
			if (count > repeat) {
				updateHeight(viewHeight);
			}
		}
	}

	protected void updateHeight(int viewHeight) {
	}

	@SuppressLint("NewApi")
	public void onDestroy() {
		if (master != null) {
			if (Build.VERSION.SDK_INT >= 16) {
				master.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
			master = null;
		}
		if (slaves != null) {
			for (int i = 0; i < slaves.length; i++) {
				slaves[i] = null;
			}
		}
	}
}