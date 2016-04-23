package com.sina.sinawidgetdemo.custom.horizontalvariablelistview;

import android.view.View;

public class ViewHelper16 extends ViewHelper14 {
	public ViewHelper16( View view ) {
		super( view );
	}

	@Override
	public void postOnAnimation( Runnable action ) {
		view.postOnAnimation(action);
	}
}