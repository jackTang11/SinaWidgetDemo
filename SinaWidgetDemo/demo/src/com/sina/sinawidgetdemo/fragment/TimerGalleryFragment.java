package com.sina.sinawidgetdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;


public class TimerGalleryFragment extends BaseFragment {
	
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
		mView = inflater.inflate(R.layout.timergallery_fragment, container,
				false);
		initView(mView);
		return mView;
		
	}
	private void initData(){
	}
	
	private void initView(View view){
		LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
	}

	
	
}
