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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.overlay.utils.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshWebView.OnScrollChangedListener;
import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.activity.BaseFragmentActivity;
import com.sina.sinawidgetdemo.custom.view.CustomLoadView;
import com.sina.sinawidgetdemo.custom.view.OnPullEventListenerTimer;
import com.sina.sinawidgetdemo.custom.view.ScrolledListView;
import com.sina.sinawidgetdemo.custom.view.ScrolledScrollView;
import com.sina.sinawidgetdemo.custom.view.ScrolledWebView;
import com.sina.sinawidgetdemo.usercredit.SyncHeightLayoutScheduler;

public class AdvanceWListFragment extends BaseFragment implements OnClickListener {

	private ListView listView;
	protected PullToRefreshListView refreshListView;
	private OnPullEventListenerTimer<ListView> onPullEventListenerTimer;

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
		mView = inflater.inflate(R.layout.advance_wlist_fragment, container, false);
		initView(mView);
		return mView;
	}

	private void initImageLoader() {
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
	ScrolledWebView mWebView;
//	View Footer;
	ScrolledListView mListView;
	private MyListAdapter listAdapter;
	Button mButton;
	
	int maxheight;
	int updateheight;
	int scrollviewheight;
	int webmeasuredheight;
	
	int lastwebscrolly;
	int scrolly;
	int footermeasuredheight;
	boolean allowtoappendlist = true;
	View webLayout;
	View listLayout;
	View topbar;
	View bottombar;
	
	boolean webviewupdate = false;
	
	boolean alreadynearby = false;
	boolean nearby(int a, int b) {
		int topa = a + 5;
		int bota = a - 5;
		if (topa <= b || b >= bota) {
			return true;
		}
		return false;
	}
	
	boolean alreadynearby0 = false;

	private void initView(View view) {
		mScrollView = (ScrolledScrollView) view.findViewById(R.id.scroll);
		mScrollView.setOnScrollChangedListener(new OnScrollChangedListener() {
			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				Log.d("OOO", "t=" + t);
				if (!alreadynearby0 && nearby(scrollviewheight, t)) {
					alreadynearby0 = true;
					mScrollView.setTouchEnable(false);
					mWebView.setTouchEnable(false);
					mListView.setTouchEnable(true);
				}
//				scrolly = t;
//				if (allowtoappendlist) {
//					if (scrolly > (footermeasuredheight + webmeasuredheight) - 40) {
//						allowtoappendlist = false;
//						fakeSync(new Runnable() {
//							@Override
//							public void run() {
//								imageList.addAll(page2List);
//								listAdapter.setData(imageList);
//								setListViewHeightBasedOnChildren(mListView);
//								listAdapter.notifyDataSetChanged();
//								footermeasuredheight = Footer.getMeasuredHeight();
//								Log.d("WLF", "append: footermeasuredheight=" + footermeasuredheight);
//								allowtoappendlist = true;
//							}
//						});
//					}
//				}
			}
		});
//        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.MATCH_PARENT, MeasureSpec.UNSPECIFIED);
//        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY);
////        realHeader.measure(widthMeasureSpec, heightMeasureSpec);
//        mScrollView.measure(widthMeasureSpec, heightMeasureSpec);
//        int w2w = mScrollView.getMeasuredWidth();
//        int h2h = mScrollView.getMeasuredHeight();
        
        webLayout = LayoutInflater.from(getActivity()).inflate(R.layout.webview, null);
        mWebView = (ScrolledWebView) webLayout.findViewById(R.id.web);
        mWebView.getSettings().setTextSize(TextSize.SMALLEST);
		mWebView.setOnScrollChangedListener(new OnScrollChangedListener() {
			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				Log.d("AAA", "t=" + t);
				if (!alreadynearby && nearby(maxheight - t, scrollviewheight)) {
					alreadynearby = true;
					mScrollView.setTouchEnable(true);
					mWebView.setTouchEnable(false);
					mListView.setTouchEnable(false);
				}
			}
		});
		
		listLayout = LayoutInflater.from(getActivity()).inflate(R.layout.listview, null);
		mListView = (ScrolledListView) listLayout.findViewById(R.id.list);
		mListView.setOnScrollChangedListener(new OnScrollChangedListener() {
			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				Log.d("AAA", "T=" + t);
			}
		});
		listAdapter = new MyListAdapter(getActivity());
		mListView.setAdapter(listAdapter);
		
		topbar = view.findViewById(R.id.topbar);
//		topbar.measure(widthMeasureSpec, heightMeasureSpec);
		int hhh = topbar.getMeasuredHeight();
		bottombar = view.findViewById(R.id.bottombar);
		container = (LinearLayout) view.findViewById(R.id.linear);
		container.addView(webLayout);
		container.addView(listLayout);
//		mWebView.loadUrl("file:///android_res/raw/user_rules_brief.html");
//		mWebView.setWebViewClient(new MyWebViewClient());
		
		mScheduler = new SyncHeightLayoutScheduler(mScrollView) {
			@Override
			protected void updateHeight(final int viewHeight) {
				updateheight = viewHeight;
				scrollviewheight = viewHeight;
				fakeSync(new Runnable() {
					@Override
					public void run() {
						mWebView.setWebViewClient(new WebViewClient() {
							@Override
							public void onPageFinished(WebView view, String url) {
								super.onPageFinished(view, url);
								webviewupdate = true;
							}
						});
						mWebView.loadUrl("file:///android_res/raw/user_rules_brief.html");
						mScheduler2 = new SyncHeightLayoutScheduler(mWebView) {
							@Override
							protected void updateHeight(final int viewHeight) {
								if (webviewupdate) {
									webviewupdate = false;
									int aheight = mWebView.getMeasuredHeight();
									maxheight = aheight;
									LayoutParams lp = mWebView.getLayoutParams();
									lp.height = scrollviewheight;
									mWebView.setLayoutParams(lp);
									mWebView.setTouchEnable(true);
									mScrollView.setTouchEnable(false);
									mListView.setTouchEnable(false);
									
								} else {
									if (nearby(scrollviewheight, viewHeight)) {
																				
									}
								}
							}
						};
						
//						mWebView.setLayoutParams(lp);
//						mScheduler2 = new SyncHeightLayoutScheduler(mWebView) {
//							@Override
//							protected void updateHeight(final int viewHeight) {
//								updateheight = viewHeight;
//								mScrollView.setTouchEnable(false);
//								mWebView.loadUrl("file:///android_res/raw/user_rules_brief.html");
//								mWebView.setWebViewClient(new MyWebViewClient());
//							}
//						};
					}
				});
				
				fakeSync(new Runnable() {
					@Override
					public void run() {
						LayoutParams lp = listLayout.getLayoutParams();
						lp.height = scrollviewheight;
						listLayout.setLayoutParams(lp);
						
						imageList.addAll(page1List);
						listAdapter.setData(imageList);
						listAdapter.notifyDataSetChanged();
					}
				});	
				
			}
		};
		
		
//		mScheduler = new SyncHeightLayoutScheduler(mWebView) {
//			@Override
//			protected void updateHeight(int viewHeight) {
//				updateheight = viewHeight;
//				fakeSync(new Runnable() {
//					@Override
//					public void run() {
//						Log.d("WLF", "updateheight=" + updateheight);
//						container.removeView(Footer);
//						initFlush();
//						container.addView(Footer);
//						footermeasuredheight = Footer.getMeasuredHeight();
//						Log.d("WLF", "footermeasuredheight=" + footermeasuredheight);
//					}
//				});
//			}
//		};
		
//		Footer = LayoutInflater.from(getActivity()).inflate(R.layout.wlist_list, null);
//		mListView = (ListView) Footer.findViewById(R.id.list);
//		listAdapter = new MyListAdapter(getActivity());
//		mListView.setAdapter(listAdapter);

		mButton = (Button) view.findViewById(R.id.btn1);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				webmeasuredheight = mWebView.getMeasuredHeight();
				int top = topbar.getBottom();
				int topmh = topbar.getMeasuredHeight();
				int bottom = bottombar.getTop();
				int bottomh = bottombar.getMeasuredHeight();
				int webviewh = mWebView.getMeasuredHeight();
				int containerh = container.getMeasuredHeight();
				int scrollviewh = mScrollView.getMeasuredHeight();
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
	SyncHeightLayoutScheduler mScheduler2;

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
//		listAdapter.setData(imageList);
//		setListViewHeightBasedOnChildren(mListView);
//		listAdapter.notifyDataSetChanged();
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
//		listAdapter.setData(imageList);
//		listAdapter.notifyDataSetChanged();
//		refreshListView.setHideFooterView(imageList.size() % LIST_LOAD_NUM > 0);
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