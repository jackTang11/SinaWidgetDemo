package com.sina.sinawidgetdemo.custom.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author sunislee 可以宽高能够自适应的linearlayoutmanager，有待日后优化
 *
 */
public class CustomLinearLayoutManager extends LinearLayoutManager {

	private static final int CHILD_WIDTH = 0;
	private static final int CHILD_HEIGHT = 1;
	private static final int DEFAULT_CHILD_SIZE = 100;

	private final int[] childDimensions = new int[2];

	private int childSize = DEFAULT_CHILD_SIZE;

	private boolean hasChildSize;

	/** 默认以第几个位置上得子view来测量宽高 */
	private int measuredByIndex = 0;

	@SuppressWarnings("UnusedDeclaration")
	public CustomLinearLayoutManager(Context context) {
		super(context);
	}

	@SuppressWarnings("UnusedDeclaration")
	public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
		super(context, orientation, reverseLayout);
	}

	private int[] mMeasuredDimension = new int[2];

	public int getMeasuredByIndex() {
		return measuredByIndex;
	}

	public void setMeasuredByIndex(int measuredByIndex) {
		this.measuredByIndex = measuredByIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v7.widget.RecyclerView.LayoutManager#onMeasure(android.
	 * support.v7.widget.RecyclerView.Recycler,
	 * android.support.v7.widget.RecyclerView.State, int, int)
	 */
	@Override
	public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
		final int widthMode = View.MeasureSpec.getMode(widthSpec);
		final int heightMode = View.MeasureSpec.getMode(heightSpec);
		final int widthSize = View.MeasureSpec.getSize(widthSpec);
		final int heightSize = View.MeasureSpec.getSize(heightSpec);
		int width = 0;
		int height = 0;

		int j = measuredByIndex;
		try {
			measureScrapChild(recycler, j, widthSpec, View.MeasureSpec.makeMeasureSpec(j, View.MeasureSpec.UNSPECIFIED),
					mMeasuredDimension);
		} catch (IndexOutOfBoundsException e) {

			e.printStackTrace();
		}

		if (getOrientation() == HORIZONTAL) {
			width = width + mMeasuredDimension[0]+getPaddingLeft()+getPaddingRight();
			height = mMeasuredDimension[1] + getPaddingTop() + getPaddingBottom();
		} else {
			height = height + mMeasuredDimension[1] + getPaddingTop() + getPaddingBottom();
			width = mMeasuredDimension[0]+getPaddingLeft()+getPaddingRight();
		}

		switch (widthMode) {
		case View.MeasureSpec.EXACTLY:
			// width = widthSize;
		case View.MeasureSpec.AT_MOST:
		case View.MeasureSpec.UNSPECIFIED:
		}

		switch (heightMode) {
		case View.MeasureSpec.EXACTLY:
			height = heightSize;
		case View.MeasureSpec.AT_MOST:
		case View.MeasureSpec.UNSPECIFIED:
		}
		setMeasuredDimension(widthSpec, height);

	}

	private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec, int heightSpec,
			int[] measuredDimension) {
		View view = recycler.getViewForPosition(position);

		if (view != null) {
			RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
			int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, 0, p.height);
			view.measure(widthSpec, childHeightSpec);
			measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
			measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
			recycler.recycleView(view);
		}
	}

	@Override
	public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
		super.onLayoutChildren(recycler, state);
	}
}
