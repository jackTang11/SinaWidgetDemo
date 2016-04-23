package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.activity.BaseFragmentActivity;
import com.sina.sinawidgetdemo.custom.view.CustomLoadView;
import com.sina.sinawidgetdemo.custom.view.OnPullEventListenerTimer;


/**
 * 圆角图
 * 
 * @author haorongrong
 * 
 */
public class RoundImageViewFragment extends BaseFragment implements OnClickListener{
	
	private ListView listView;
	protected PullToRefreshListView refreshListView;
	private OnPullEventListenerTimer<ListView> onPullEventListenerTimer;
	private MyListAdapter listAdapter;
	private List<String> imageList = new ArrayList<String>();
	private List<String> page1List = new ArrayList<String>();
	private List<String> page2List = new ArrayList<String>();
	private int mPage = 1;
	private CustomLoadView loadLayout;
	private FrameLayout mainLayout;
	
	private static final int LIST_LOAD_NUM = 10;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initImageLoader();
		initData();
		initRequestData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.round_imageview_fragment, container,
				false);
		initView(mView);
		return mView;
	}
	
	private void initImageLoader() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
//		imageOptions = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
//				.showImageForEmptyUri(R.drawable.start_img) // 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.start_img) // 设置图片加载或解码过程中发生错误显示的图片
//				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//				.bitmapConfig(Bitmap.Config.RGB_565)
//				.imageScaleType(ImageScaleType.EXACTLY)
//				.build();
	}
	private void initData(){
		page1List.add("http://n.sinaimg.cn/transform/20150708/pCcs-fxesssr5496632.png");
		page1List.add("http://n.sinaimg.cn/transform/20150708/zut5-fxesfty0669894.jpg");
		page1List.add("http://n.sinaimg.cn/default/20150630/nOBz-fxemzey4941340.jpg");
		page1List.add("http://n.sinaimg.cn/transform/20150708/qww3-fxesfty0666835.png");
		page1List.add("http://www.sinaimg.cn/gm/cr/2015/0707/3843767675.jpg");
		page1List.add("http://n.sinaimg.cn/transform/20150706/6g4D-fxesftz6782576.jpg");
		page1List.add("http://n.sinaimg.cn/transform/20150707/IbpT-fxesfuc3573002.jpg");
		page1List.add("http://n.sinaimg.cn/transform/20150706/g3Y_-fxesftz6765356.png");
		page1List.add("http://n.sinaimg.cn/crawl/20150701/xcd--fxemzey4967573.jpeg");
		page1List.add("http://n.sinaimg.cn/default/20150703/ZkZE-fxesssr5401638.jpg");
		page2List.add("http://n.sinaimg.cn/default/20150707/5CJt-fxesftz6800147.jpg");
		page2List.add("http://n.sinaimg.cn/default/20150702/FZCO-fxenncn6853276.png");
		page2List.add("http://n.sinaimg.cn/default/20150702/LLzB-fxesfuc3481996.png");
		page2List.add("http://n.sinaimg.cn/default/20150706/78S8-fxesfuc3555015.png");
		page2List.add("http://n.sinaimg.cn/crawl/20150707/vIo8-fxesftz6798991.jpeg");
		page2List.add("http://n.sinaimg.cn/default/20150702/QKTy-fxesfuc3485818.png");
	}
	private void initRequestData() {
		imageList.clear();
		if (imageList.size()<=0) {
			requestData(false);
		}
	}
	private void initView(View view){
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.round_image_list);
		refreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				requestData(true);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				requestData(false);
			}
			
		});
		onPullEventListenerTimer = new OnPullEventListenerTimer<ListView>(
				refreshListView.getLoadingLayoutProxy());
		refreshListView.setOnPullEventListener(onPullEventListenerTimer);
		

		listView = refreshListView.getRefreshableView();

		listAdapter = new MyListAdapter();
		listView.setAdapter(listAdapter);
		
		mainLayout = (FrameLayout) view.findViewById(R.id.main_layout);
		loadLayout = new CustomLoadView(getActivity());
		loadLayout.creatView(mainLayout, this);
		if (imageList.size()<=0) {
			loadLayout.flushLoadView(CustomLoadView.LOAD_ING);
		}
	}
	
	private void requestData(boolean isForceRefresh){
		mPage = imageList.size()/LIST_LOAD_NUM+1;
		if(isForceRefresh){
			mPage=1;
		}
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

			@Override
			public void run() {
				resultCallBack();
			}
		}, 3000);
	}
	
	private class MyListAdapter extends BaseAdapter{
		List<String> listData = new ArrayList<String>();

		public void setData(List<String> list){
			listData = list;
		}
		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			
			return listData.size()>0?listData.get(position):null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String imgurl = (String) getItem(position);
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.round_imageview_list_item, null, false);
				holder = new ViewHolder();
				holder.roundImagview = (SimpleDraweeView) convertView
						.findViewById(R.id.round_image);
				holder.ovalImagview = (SimpleDraweeView) convertView
						.findViewById(R.id.oval_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (!TextUtils.isEmpty(imgurl)) {
				if (holder.roundImagview != null) {
					holder.roundImagview.setImageURI(Uri.parse(imgurl));
				}
				if (holder.ovalImagview != null) {
					holder.ovalImagview.setImageURI(Uri.parse(imgurl));
				}
			}

			return convertView;
		}

		private class ViewHolder {
			public SimpleDraweeView roundImagview;
			public SimpleDraweeView ovalImagview;
		}
	}
	public void resultCallBack() {
		if(isDetached() || getActivity() == null || getActivity().isFinishing()){
			return;
		}
		if (mPage == 1) {
			imageList.clear();
			imageList.addAll(page1List);
			onPullEventListenerTimer.flushLastRefreshTime();
		}else{
			imageList.addAll(page2List);
		}
		
		flushListView();
		if(imageList.size() > 0){
			loadLayout.flushLoadView(CustomLoadView.LOAD_SUCESS);
		}else{
		    loadLayout.flushLoadView(CustomLoadView.LOAD_FAIL);
		}
		refreshListView.onRefreshComplete();
	}
	
	private void flushListView(){
		listAdapter.setData(imageList);
		listAdapter.notifyDataSetChanged();
		refreshListView.setHideFooterView(imageList.size()
				% LIST_LOAD_NUM > 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_load_fail_button:
			if (imageList.size()<=0) {
				loadLayout.flushLoadView(CustomLoadView.LOAD_ING);
				requestData(false);
			}
			break;
		}
	}
	
}
