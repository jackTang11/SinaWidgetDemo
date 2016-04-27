package com.sina.sinawidgetdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.timergallery.BannerLayout;
import com.sina.sinawidgetdemo.custom.timergallery.JumpableImage;
import com.sina.sinawidgetdemo.custom.timergallery.SpecialSellingGalleryAdapter;
import com.sina.sinawidgetdemo.custom.view.livelistview.CommonLiveResp;
import com.sina.sinawidgetdemo.custom.view.livelistview.LiveListView;

import java.util.ArrayList;
import java.util.List;


public class LiveListViewFragment extends BaseFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.livelistview_fragment, container,
				false);
		initView(mView);
		return mView;
		
	}

	private void initData(){
	}

	private void initView(View view){
		RelativeLayout cardLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
		LiveListView liveListView = new LiveListView(getActivity(), cardLayout) {
			@Override
			protected void onMoreLayoutOnClick(View view) {
				Toast.makeText(getActivity(), "点击了更多", Toast.LENGTH_SHORT).show();
			}

			@Override
			protected void onLiveListItemClick(CommonLiveResp.CommonLiveItem item) {
				Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
			}
		};
		liveListView.loadData();
	}


	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}
}
