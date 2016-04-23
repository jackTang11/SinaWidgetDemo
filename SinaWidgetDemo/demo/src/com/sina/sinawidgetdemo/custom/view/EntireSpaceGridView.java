package com.sina.sinawidgetdemo.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class EntireSpaceGridView extends GridView {

	public EntireSpaceGridView(Context context) {
		super(context);
	}

	public EntireSpaceGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
