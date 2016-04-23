package com.sina.sinawidgetdemo.fragment;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.view.pulltozoom.PullToZoomScrollViewEx;

public class ZoomFragment extends BaseFragment {
	private PullToZoomScrollViewEx scrollView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.scrollzoomin_fragment, container,
				false);
		initView();
		return mView;
	}

	private void initView() {
		scrollView = (PullToZoomScrollViewEx) mView
				.findViewById(R.id.scroll_view);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		int mScreenHeight = localDisplayMetrics.heightPixels;
		int mScreenWidth = localDisplayMetrics.widthPixels;
		LayoutInflater lay = LayoutInflater.from(getActivity());
		View userHeaderView = lay
				.inflate(R.layout.scrollzoomin_head_view, null);
		LinearLayout.LayoutParams headerLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		userHeaderView.setLayoutParams(headerLayoutParams);
		userHeaderView.measure(0, 0);
		RelativeLayout.LayoutParams localObject = new RelativeLayout.LayoutParams(
				mScreenWidth, userHeaderView.getMeasuredHeight());
		scrollView.setHeaderLayoutParams(localObject);
	}
}