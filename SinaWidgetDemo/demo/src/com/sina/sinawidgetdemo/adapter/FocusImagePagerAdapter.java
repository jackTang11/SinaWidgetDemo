/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.sina.sinawidgetdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.activity.ImageBrowserActivity;
import com.sina.sinawidgetdemo.activity.WebBrowserActivity;
import com.sina.sinawidgetdemo.activity.WebDetailActivity;
import com.sina.sinawidgetdemo.custom.autoscrollviewpager.RecyclingPagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * ImagePagerAdapter
 * 
 * @author zyl
 */
public class FocusImagePagerAdapter extends RecyclingPagerAdapter {

	private Context context;
	private List<Integer> mDataList = new ArrayList<Integer>();
	private boolean isInfiniteLoop;
	LayoutInflater inflater;

	public FocusImagePagerAdapter(Context context) {
		this.context = context;
		isInfiniteLoop = false;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// Infinite loop
		return isInfiniteLoop ? Integer.MAX_VALUE : mDataList == null ? 0
				: mDataList.size();
	}

	/**
	 * get really position
	 * 
	 * @param position
	 * @return
	 */
	private int getPosition(int position) {
		return isInfiniteLoop ? position % mDataList.size() : position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup container) {
		ViewHolder holder;
		int o = getPosition(position);
		int res = mDataList.get(o);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.news_item_focusads_item,
					null);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.news_focus_item_image);
//			try {
//				// imageView按比例动态设置宽高
//				LayoutParams lp = holder.imageView.getLayoutParams();
//				int wh[] = ViewUtils.setImageWH(context, 360, 118, 1, 0, 0, 0);
//				lp.width = wh[0];
//				lp.height = wh[1];
//				holder.imageView.setLayoutParams(lp);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, WebBrowserActivity.class);
				intent.putExtra("url", "www.sina.com");
				intent.putExtra("title", "www.sina.com");
				context.startActivity(intent);
			}
		});
		// holder.imageView.setImageResource(imageIdList
		// .get(getPosition(position)));
//		ImageLoader.getInstance().displayImage(color, holder.imageView,
//				mHeaderImageOptions, new AnimateFirstDisplayListener());
		holder.imageView.setImageResource(res);
		return convertView;
	}

	private static class ViewHolder {

		ImageView imageView;
	}

	/**
	 * @return the isInfiniteLoop
	 */
	public boolean isInfiniteLoop() {
		return isInfiniteLoop;
	}

	/**
	 * @param isInfiniteLoop
	 *            the isInfiniteLoop to set
	 */
	public FocusImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
		this.isInfiniteLoop = isInfiniteLoop;
		return this;
	}

	public List<Integer> getmDataList() {
		return mDataList;
	}

	public void setmDataList(List<Integer> mDataList) {
		this.mDataList = mDataList;
	}

}
