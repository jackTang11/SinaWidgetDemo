package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshWebView.OnScrollChangedListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sina.engine.base.utils.LogUtils;
import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.activity.BaseFragmentActivity;
import com.sina.sinawidgetdemo.custom.view.CustomLoadView;
import com.sina.sinawidgetdemo.custom.view.OnPullEventListenerTimer;
import com.sina.sinawidgetdemo.custom.view.ScrolledScrollView;
import com.sina.sinawidgetdemo.custom.view.ScrolledWebView;
import com.sina.sinawidgetdemo.usercredit.ListViewScrollObserver;
import com.sina.sinawidgetdemo.usercredit.ListViewScrollObserver.OnListViewScrollListener;
import com.sina.sinawidgetdemo.usercredit.SyncHeightLayoutScheduler;

public class WListFragment extends BaseFragment implements OnClickListener {

	private ListView listView;
	protected PullToRefreshListView refreshListView;
	private OnPullEventListenerTimer<ListView> onPullEventListenerTimer;
	private MyListAdapter listAdapter;
	private List<String> imageList = new ArrayList<String>();
	private List<String> page1List = new ArrayList<String>();
	private List<String> page2List = new ArrayList<String>();
	private DisplayImageOptions imageOptions;
	private int mPage = 1;
	private CustomLoadView loadLayout;
	private FrameLayout mainLayout;

	private static final int LIST_LOAD_NUM = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Fresco.initialize(getActivity());
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
		mView = inflater.inflate(R.layout.wlist_fragment, container, false);
		initView(mView);
		return mView;
	}

	private void initImageLoader() {
		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		// imageOptions = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.start_img) // 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.start_img) // 设置图片加载或解码过程中发生错误显示的图片
		// .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		// .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		// .bitmapConfig(Bitmap.Config.RGB_565)
		// .imageScaleType(ImageScaleType.EXACTLY)
		// .build();
		imageOptions = new DisplayImageOptions.Builder() // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.gift_item_default) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.gift_item_default) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build();
	}

	private void initData() {
		page1List
				.add("http://n.sinaimg.cn/transform/20150708/pCcs-fxesssr5496632.png");
		page1List
				.add("http://n.sinaimg.cn/transform/20150708/zut5-fxesfty0669894.jpg");
		page1List
				.add("http://n.sinaimg.cn/default/20150630/nOBz-fxemzey4941340.jpg");
		page1List
				.add("http://n.sinaimg.cn/transform/20150708/qww3-fxesfty0666835.png");
		page1List.add("http://www.sinaimg.cn/gm/cr/2015/0707/3843767675.jpg");
		page1List
				.add("http://n.sinaimg.cn/transform/20150706/6g4D-fxesftz6782576.jpg");
		page1List
				.add("http://n.sinaimg.cn/transform/20150707/IbpT-fxesfuc3573002.jpg");
		page1List
				.add("http://n.sinaimg.cn/transform/20150706/g3Y_-fxesftz6765356.png");
		page1List
				.add("http://n.sinaimg.cn/crawl/20150701/xcd--fxemzey4967573.jpeg");
		page1List
				.add("http://n.sinaimg.cn/default/20150703/ZkZE-fxesssr5401638.jpg");
		page2List
				.add("http://n.sinaimg.cn/default/20150707/5CJt-fxesftz6800147.jpg");
		page2List
				.add("http://n.sinaimg.cn/default/20150702/FZCO-fxenncn6853276.png");
		page2List
				.add("http://n.sinaimg.cn/default/20150702/LLzB-fxesfuc3481996.png");
		page2List
				.add("http://n.sinaimg.cn/default/20150706/78S8-fxesfuc3555015.png");
		page2List
				.add("http://n.sinaimg.cn/crawl/20150707/vIo8-fxesftz6798991.jpeg");
		page2List
				.add("http://n.sinaimg.cn/default/20150702/QKTy-fxesfuc3485818.png");
	}

	private void initRequestData() {
		// imageList.clear();
		// if (imageList.size() <= 0) {
		// requestData(false);
		// }
	}

	ScrolledScrollView mScrollView;
	LinearLayout container;
	int containerheight;
	WebView mWebView;
	View Footer;
	ListView mListView;
	Button mButton;
	
	int updateheight;
	int webmeasuredheight;
	
	int lastwebscrolly;
	int scrolly;
	int footermeasuredheight;
	boolean allowtoappendlist = true;
	
	View topbar;
	View bottombar;
	

	private void initView(View view) {
		mScrollView = (ScrolledScrollView) view.findViewById(R.id.scroll);
		mScrollView.setOnScrollChangedListener(new OnScrollChangedListener() {
			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				scrolly = t;
				if (allowtoappendlist) {
					if (scrolly > (footermeasuredheight + webmeasuredheight) - 40) {
						allowtoappendlist = false;
						fakeSync(new Runnable() {
							@Override
							public void run() {
								imageList.addAll(page2List);
								listAdapter.setData(imageList);
								setListViewHeightBasedOnChildren(mListView);
								listAdapter.notifyDataSetChanged();
								footermeasuredheight = Footer.getMeasuredHeight();
								Log.d("WLF", "append: footermeasuredheight=" + footermeasuredheight);
								allowtoappendlist = true;
							}
						});
					}
				}
			}
		});
		topbar = view.findViewById(R.id.topbar);
		bottombar = view.findViewById(R.id.bottombar);
		container = (LinearLayout) view.findViewById(R.id.linear);
		mWebView = (WebView) view.findViewById(R.id.web);
		mWebView.getSettings().setTextSize(TextSize.SMALLEST);
		mWebView.loadUrl("file:///android_res/raw/user_rules.html");
		mWebView.setWebViewClient(new MyWebViewClient());
		mScheduler = new SyncHeightLayoutScheduler(mWebView) {
			@Override
			protected void updateHeight(int viewHeight) {
				updateheight = viewHeight;
				fakeSync(new Runnable() {
					@Override
					public void run() {
						Log.d("WLF", "updateheight=" + updateheight);
						container.removeView(Footer);
						initFlush();
						container.addView(Footer);
						footermeasuredheight = Footer.getMeasuredHeight();
						Log.d("WLF", "footermeasuredheight=" + footermeasuredheight);
					}
				});
			}
		};
		
		Footer = LayoutInflater.from(getActivity()).inflate(R.layout.wlist_list, null);
		mListView = (ListView) Footer.findViewById(R.id.list);
		listAdapter = new MyListAdapter(getActivity());
		mListView.setAdapter(listAdapter);

		mButton = (Button) view.findViewById(R.id.btn1);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				webmeasuredheight = mWebView.getMeasuredHeight();
				int top = topbar.getBottom();
				int bottom = bottombar.getTop();
				containerheight = bottom - top;
				Log.d("WLF", "scrolly=" + scrolly + ", webmeasuredheight=" + webmeasuredheight);
				Log.d("WLF", "footermeasuredheight=" + footermeasuredheight + ", containerheight" + containerheight);
				if (footermeasuredheight <= containerheight) {
					// listview不到一屏幕高，webview无法滑出屏幕总是可见
					if (scrolly >= (webmeasuredheight - containerheight)) {
						// 如果点击前的位置在webview之下，webview已不可见，向上滑
						Log.d("WLF", "<<<go up");
						mScrollView.smoothScrollTo(0, lastwebscrolly);
					} else {
						// 如果点击前的位置在webview之内，webview可见，向下滑
						Log.d("WLF", ">>>go down");
						lastwebscrolly = scrolly;
						mScrollView.smoothScrollTo(0, webmeasuredheight);
					}
					
				} else {
					// listview比一屏幕高，理想状态
					if (scrolly >= webmeasuredheight) {
						// 如果点击前的位置在webview之下，webview已不可见，向上滑
						Log.d("WLF", "<<<go up");
						mScrollView.smoothScrollTo(0, lastwebscrolly);
					} else {
						// 如果点击前的位置在webview之内，webview可见，向下滑
						Log.d("WLF", ">>>go down");
						lastwebscrolly = scrolly;
						mScrollView.smoothScrollTo(0, webmeasuredheight);
					}
				}
			}
		});
		// refreshListView = (PullToRefreshListView) view
		// .findViewById(R.id.round_image_list);
		// refreshListView
		// .setOnRefreshListener(new OnRefreshListener2<ListView>() {
		//
		// @Override
		// public void onPullDownToRefresh(
		// PullToRefreshBase<ListView> refreshView) {
		// requestData(true);
		// }
		//
		// @Override
		// public void onPullUpToRefresh(
		// PullToRefreshBase<ListView> refreshView) {
		// requestData(false);
		// }
		//
		// });
		// onPullEventListenerTimer = new OnPullEventListenerTimer<ListView>(
		// refreshListView.getLoadingLayoutProxy());
		// refreshListView.setOnPullEventListener(onPullEventListenerTimer);
		//
		// listView = refreshListView.getRefreshableView();
		//
		// listAdapter = new MyListAdapter(getActivity());
		// listView.setAdapter(listAdapter);
		//
		// mainLayout = (FrameLayout) view.findViewById(R.id.main_layout);
		// loadLayout = new CustomLoadView(myActivity);
		// loadLayout.creatView(mainLayout, this);
		// if (imageList.size() <= 0) {
		// loadLayout.flushLoadView(CustomLoadView.LOAD_ING);
		// }
	}
	
	protected void fakeSync(final Runnable runnable) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Handler(Looper.getMainLooper()).post(runnable);
			}
			
		}).start();
	}

	SyncHeightLayoutScheduler mScheduler;

	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			LogUtils.d("WEB", "onPageFinished[" + url + "]");
			// try {
			// Thread.sleep(200L);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
//			initFlush();
			// mScheduler = new SyncHeightLayoutScheduler(mWebView) {
			// @Override
			// protected void updateHeight(int viewHeight) {
			// }
			// };
		}
	}

	private void initFlush() {
		imageList.addAll(page1List);
		listAdapter.setData(imageList);
		setListViewHeightBasedOnChildren(mListView);
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mScheduler != null) {
			mScheduler.onDestroy();
		}
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
						LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	private void requestData(boolean isForceRefresh) {
		mPage = imageList.size() / LIST_LOAD_NUM + 1;
		if (isForceRefresh) {
			mPage = 1;
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				resultCallBack();
			}
		}, 3000);
	}

	private class MyListAdapter extends BaseAdapter {
		List<String> listData = new ArrayList<String>();

		public MyListAdapter(Context context) {
		}

		public void setData(List<String> list) {
			listData = list;
		}

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {

			return listData.size() > 0 ? listData.get(position) : null;
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
						R.layout.image_list_item, null, false);
				holder = new ViewHolder();
				holder.roundImagview = (ImageView) convertView
						.findViewById(R.id.round_image);
				holder.ovalImagview = (SimpleDraweeView) convertView
						.findViewById(R.id.oval_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (!TextUtils.isEmpty(imgurl)) {
				if (holder.roundImagview != null) {
					holder.roundImagview
							.setImageResource(R.drawable.gift_item_default);
					ImageLoader.getInstance().displayImage(imgurl,
							holder.roundImagview, imageOptions,
							new AnimateFirstDisplayListener());
				}
				if (holder.ovalImagview != null) {
					holder.ovalImagview.setImageURI(Uri.parse(imgurl));
					// holder.ovalImagview
					// .setImageResource(R.drawable.gift_item_default);
					// ImageLoader.getInstance().displayImage(imgurl,
					// holder.ovalImagview, imageOptions,
					// new AnimateFirstDisplayListener());
				}
			}

			return convertView;
		}

		private class ViewHolder {
			public ImageView roundImagview;
			public SimpleDraweeView ovalImagview;
		}
	}

	/**
	 * 图片加载第一次显示监听器
	 */
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// 是否第一次显示
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// 图片淡入效果
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public void resultCallBack() {
		if (isDetached() || getActivity() == null || getActivity().isFinishing()) {
			return;
		}
		if (mPage == 1) {
			imageList.clear();
			imageList.addAll(page1List);
			onPullEventListenerTimer.flushLastRefreshTime();
		} else {
			imageList.addAll(page2List);
		}

		flushListView();
		if (imageList.size() > 0) {
			loadLayout.flushLoadView(CustomLoadView.LOAD_SUCESS);
		} else {
			loadLayout.flushLoadView(CustomLoadView.LOAD_FAIL);
		}
		refreshListView.onRefreshComplete();
	}

	private void flushListView() {
		listAdapter.setData(imageList);
		listAdapter.notifyDataSetChanged();
		refreshListView.setHideFooterView(imageList.size() % LIST_LOAD_NUM > 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_load_fail_button:
			if (imageList.size() <= 0) {
				loadLayout.flushLoadView(CustomLoadView.LOAD_ING);
				requestData(false);
			}
			break;
		}
	}

}