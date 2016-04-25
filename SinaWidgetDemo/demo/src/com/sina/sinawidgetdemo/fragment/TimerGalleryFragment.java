package com.sina.sinawidgetdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.timergallery.JumpableImage;
import com.sina.sinawidgetdemo.custom.timergallery.TimerGallery;
import com.sina.sinawidgetdemo.custom.view.FlowLayout;
import com.sina.sinawidgetdemo.custom.viewpagerindicator.SpecialSellingGalleryAdapter;

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

	List<JumpableImage> focusImages = new ArrayList<JumpableImage>();

	private void initData(){
		JumpableImage image1 = new JumpableImage();
		image1.img="http://github.com/liuchonghui/SinaWidgetDemo/blob/master/SinaWidgetDemo/demo/res/drawable-hdpi/banner1.jpg";
		JumpableImage image2 = new JumpableImage();
		image2.img="https://github.com/liuchonghui/SinaWidgetDemo/blob/master/SinaWidgetDemo/demo/res/drawable-hdpi/banner2.jpg";
		JumpableImage image3 = new JumpableImage();
		image3.img="https://github.com/liuchonghui/SinaWidgetDemo/blob/master/SinaWidgetDemo/demo/res/drawable-hdpi/banner3.jpg";
		JumpableImage image4 = new JumpableImage();
		image4.img="https://github.com/liuchonghui/SinaWidgetDemo/blob/master/SinaWidgetDemo/demo/res/drawable-hdpi/banner4.jpg";
		focusImages.add(image1);
		focusImages.add(image2);
		focusImages.add(image3);
		focusImages.add(image4);
	}
	
	private void initView(View view){
		LinearLayout bannerLayout = (LinearLayout) view.findViewById(R.id.main_layout);
		if (focusImages != null && focusImages.size() != 0) {
			final TimerGallery gallery = new TimerGallery(getActivity());
			gallery.init(getActivity(), focusImages);
			SpecialSellingGalleryAdapter bigAdapter;
			bigAdapter = new SpecialSellingGalleryAdapter(getActivity(), focusImages);
			gallery.setAdapter(bigAdapter);
			gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				}
			});
			gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				protected boolean flag = false;

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
					if (flag) {
						gallery.onItemSelected(position);
					}
					flag = true;
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
			bannerLayout.addView(gallery);
		}
		bannerLayout.invalidate();
	}

	
	
}
