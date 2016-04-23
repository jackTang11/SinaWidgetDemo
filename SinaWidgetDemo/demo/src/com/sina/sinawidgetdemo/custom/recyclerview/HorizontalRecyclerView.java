package com.sina.sinawidgetdemo.custom.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class HorizontalRecyclerView extends RecyclerView {

	private CustomLinearLayoutManager mLinearLayoutManager;
	private int measuredByIndex = 0;
	private Context context;
	private boolean hasSetIndex = false;

	public HorizontalRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public HorizontalRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public HorizontalRecyclerView(Context context) {
		super(context);
		init(context);
	}

	@SuppressLint("NewApi")
	private void init(Context context) {
		this.context = context;
		if (Build.VERSION.SDK_INT > 9) {
			this.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
		}

		initLayoutManager();
	}

	public CustomLinearLayoutManager getmLinearLayoutManager() {
		return mLinearLayoutManager;
	}

	public void setmLinearLayoutManager(CustomLinearLayoutManager mLinearLayoutManager) {
		this.mLinearLayoutManager = mLinearLayoutManager;
	}

	

	private void initLayoutManager() {
		if (hasSetIndex) {
			//更新（不要重新初始化mLinearLayoutManager，否则计算高度会出错）
			mLinearLayoutManager.setMeasuredByIndex(measuredByIndex);
			
		} else {
			//初始化
			mLinearLayoutManager = new CustomLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
			mLinearLayoutManager.setMeasuredByIndex(measuredByIndex);
			HorizontalRecyclerView.this.setLayoutManager(mLinearLayoutManager);
		}

	}
	
	public int getMeasuredByIndex() {
		return measuredByIndex;
	}

	/**  
	 * 设置以第几个位置上的子view来测量高度
	 * @param measuredByIndex 子view的位置
	 */
	public void setMeasuredByIndex(int measuredByIndex) {

		if (context == null) {
			return;
		}
		this.measuredByIndex = measuredByIndex;
		initLayoutManager();
		hasSetIndex = true;
	}

}
