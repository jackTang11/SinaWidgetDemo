package com.sina.sinawidgetdemo.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class EntireSpaceListView extends ListView {

	public EntireSpaceListView(Context context) {
		super(context);
	}

	public EntireSpaceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
