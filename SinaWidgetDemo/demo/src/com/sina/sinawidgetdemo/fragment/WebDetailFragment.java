package com.sina.sinawidgetdemo.fragment;

import java.util.LinkedList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.android.overlay.ApplicationUncaughtHandler;
import com.android.overlay.utils.LogUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.sina.sinawidgetdemo.custom.view.CustomLoadView;
import com.sina.sinawidgetdemo.R;

/**
 * @author liu_chonghui 设置标题：intent.putExtra("title", title);
 *         设置链接：intent.putExtra("url", url);
 */
public class WebDetailFragment extends BaseFragment implements
		View.OnClickListener {

	protected WebView content;
	protected String hostUrl;
	protected String url;
	protected String titleStr;
	protected LinkedList<String> queue = new LinkedList<String>();
//	protected TextView title;
//	protected Recommendation recommendation;
	protected String mHtmlTitleStr;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new ApplicationUncaughtHandler(
				getActivity()));
		initController();
	}

	protected int getLayoutResId() {
		return R.layout.web_detail;
	}

	protected WebDetailFragment getWebDetailFragment() {
		return this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		view = inflater.inflate(getLayoutResId(), null);
		prepareView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();

		initContent();
	}

	protected void initController() {
		getActivity().overridePendingTransition(R.anim.push_left_in,
				R.anim.push_still);
	}

	protected void prepareView(View view) {

	}

	protected void initView() {
		View btnRet = getView().findViewById(R.id.btn_return);
		if (btnRet != null) {
			btnRet.setOnClickListener(this);
		}
//		title = (TextView) getView().findViewById(R.id.text_title);
//		if (title != null) {
//			titleStr = getIntentTitle(getActivity().getIntent());
//			title.setText(titleStr);
//		}
		setUrl(getIntentUrl(getActivity().getIntent()));
		hostUrl = url;
		initWebView();
	}

	protected void initContent() {
		if (checkWebRecommendation(getActivity().getIntent())) {
			refreshWebRecommendation();
		}
		requestWebDetail();
	}

	@Override
	public void onClick(View view) {
		final int id = view.getId();
		if (R.id.btn_return == id) {
			returnLogic();
		} else if (R.id.custom_load_fail_button == id) {
			refeshLogic();
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (checkWebRecommendation(intent)) {
			refreshWebRecommendation();
		} else {
			setUrl(getIntentUrl(intent));
			hostUrl = url;
//			if (title != null) {
//				title.setText(getIntentTitle(intent));
//			}
		}
		refeshLogic();
	}

	@Override
	public void onResume() {
		super.onResume();
//		if (recommendation != null) {
//			PushManager.getInstance().removeRecommendation(recommendation);
//		}
	}

	protected void initWebView() {
		mPullRefreshWebView = (PullToRefreshWebView) getView().findViewById(
				R.id.web_detail_webview);
		mPullRefreshWebView.setMode(Mode.DISABLED);
		mPullRefreshWebView.getLoadingLayoutProxy().setLastUpdatedLabel("");
		mPullRefreshWebView.getLoadingLayoutProxy().setPullLabel("");
		mPullRefreshWebView.getLoadingLayoutProxy().setRefreshingLabel("");
		mPullRefreshWebView.getLoadingLayoutProxy().setReleaseLabel("");
		mPullRefreshWebView.setOnPullEventListener(mPullEventListener);
		mPullRefreshWebView.setOnRefreshListener(mRefreshListener2);
		contentWebView = mPullRefreshWebView.getRefreshableView();
		contentWebView.requestFocus();
		// contentWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		webSettings = contentWebView.getSettings();
		// settings.setSupportZoom(true);
		contentWebView.getSettings().setJavaScriptEnabled(true);
		contentWebView.getSettings().setDefaultTextEncodingName("UTF-8");
		String strPadding = getIntentPadding(getActivity().getIntent());
		int padding = 0;
		try {
			padding = Integer.valueOf(strPadding);
		} catch (Exception e) {
			padding = 0;
		}
		if (padding > 0) {
			mPullRefreshWebView.setPadding(padding, 0, padding, 0);
			mPullRefreshWebView.setVerticalScrollBarEnabled(true);
		}
		// settings.setDefaultTextEncodingName("GBK");
		// contentWebView.getSettings().setLayoutAlgorithm(
		// LayoutAlgorithm.SINGLE_COLUMN);
		// settings.setUseWideViewPort(true);
		// settings.setLoadWithOverviewMode(true);
		// settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		// webSettings.setBlockNetworkImage(true);

		// int screenDensity = getResources().getDisplayMetrics().densityDpi;
		// WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
		// if (screenDensity == DisplayMetrics.DENSITY_LOW) {
		// zoomDensity = WebSettings.ZoomDensity.CLOSE;
		// } else if (screenDensity == DisplayMetrics.DENSITY_MEDIUM) {
		// zoomDensity = WebSettings.ZoomDensity.MEDIUM;
		// } else if (screenDensity >= DisplayMetrics.DENSITY_HIGH) {
		// zoomDensity = WebSettings.ZoomDensity.FAR;
		// }
		// webSettings.setSupportZoom(true);
		// webSettings.setDefaultZoom(zoomDensity);
		// setLocalTextSizeData();
		// contentWebView.setInitialScale(39);
		contentWebView.getSettings().setBuiltInZoomControls(true);
		contentWebView.getSettings().setUseWideViewPort(true);
		contentWebView.getSettings().setLoadWithOverviewMode(true);
		// contentWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		// contentWebView.getSettings().setBlockNetworkImage(true);
		contentWebView.getSettings().setCacheMode(WebSettings.LOAD_NORMAL);

		contentWebView.setWebViewClient(new MyWebViewClient());

		contentWebView.setDownloadListener(new MyWebViewDownLoadListener());

		contentWebView.setWebChromeClient(new MyChromeClient());

		mainLayout = (RelativeLayout) getView().findViewById(
				R.id.detail_main_layout);

		loadLayout = new CustomLoadView(getActivity());

		loadLayout.creatView(mainLayout, this);

		loadLayout.flushLoadView(CustomLoadView.LOAD_ING);
	}

	private class MyWebViewDownLoadListener implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String userAgent,
				String contentDisposition, String mimetype, long contentLength) {
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
			if (loadLayout != null) {
				loadLayout.flushLoadView(CustomLoadView.LOAD_SUCESS);
			}
		}

	}

//	public Recommendation getRecommendation() {
//		return recommendation;
//	}

	protected String getIntentTitle(Intent intent) {
		String title = getActivity().getString(R.string.app_name);
		String intentContent = intent.getStringExtra("title");
		if (intentContent != null) {
			title = intentContent;
		}
		return title;
	}

	protected String getIntentUrl(Intent intent) {
		String url = "";
		String intentContent = intent.getStringExtra("url");
		if (intentContent != null && intentContent.length() > 0) {
			url = intentContent;
		}
		return url;
	}

	protected String getIntentPadding(Intent intent) {
		String padding = "0";
		String intentContent = intent.getStringExtra("padding");
		if (intentContent != null && intentContent.length() > 0) {
			padding = intentContent;
		}
		return padding;
	}

	protected boolean checkWebRecommendation(Intent intent) {
//		String action = intent.getAction();
//		String royalTypeName = RoyaltyIntentBuilder.getRoyalTypeName(intent);
//		String royalIdentify = RoyaltyIntentBuilder.getRoyalIdentify(intent);
//		if (action == null || action.length() == 0) {
//			recommendation = null;
//		} else if (action.contains(PushAssistant.ACTION_WEBRECOMMENDATION)) {
//			recommendation = PushManager.getInstance().getRecommendation(
//					royalTypeName, royalIdentify);
//			if (recommendation == null) {
//				LogUtils.d("PUSHLOG", "NewsDetail->WebRecommendation == null");
//				getActivity().finish();
//				// 在二层界面发生崩溃
//				return false;
//			} else {
//				LogUtils.d("PUSHLOG", "NewsDetail->WebRecommendation:"
//						+ recommendation.getUuid() + ", " + recommendation.url);
//			}
//			return true;
//		} else {
//			recommendation = null;
//		}

		return false;
	}

	protected void refreshWebRecommendation() {
//		if (recommendation == null || recommendation.url == null
//				|| recommendation.url.length() == 0) {
//			return;
//		}
//
//		setUrl(recommendation.url);
//		hostUrl = url;
	}

	protected PullToRefreshWebView mPullRefreshWebView;
	protected CustomLoadView loadLayout;
	protected RelativeLayout mainLayout;
	protected WebView contentWebView;
	protected WebSettings webSettings;

	protected void refeshLogic() {
		alreadyFailed = false;

		mPullRefreshWebView.onRefreshComplete();

		loadLayout.flushLoadView(CustomLoadView.LOAD_ING);

		contentWebView.loadDataWithBaseURL(null, null, "text/html", "utf-8",
				null);
		contentWebView.setVisibility(View.GONE);

		requestWebDetail();
	}

	private void requestWebDetail() {
		// TODO
		alreadyFailed = false;
		LogUtils.d("WEB", "WebView.loadUrl:" + url);
		contentWebView.loadUrl(url);
	}

	OnRefreshListener2<WebView> mRefreshListener2 = new OnRefreshListener2<WebView>() {

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {
			mPullRefreshWebView.onRefreshComplete();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {
			mPullRefreshWebView.onRefreshComplete();
		}

	};

	OnPullEventListener<WebView> mPullEventListener = new OnPullEventListener<WebView>() {

		@Override
		public void onPullEvent(PullToRefreshBase<WebView> refreshView,
				State state, Mode direction) {

			if (direction == Mode.PULL_FROM_START) {
				mPullRefreshWebView.getLoadingLayoutProxy().setPullLabel("");
				mPullRefreshWebView.getLoadingLayoutProxy().setReleaseLabel("");
				mPullRefreshWebView.getLoadingLayoutProxy()
						.setLastUpdatedLabel("");
			} else if (direction == Mode.PULL_FROM_END) {
				// String downTurn = getActivity().getResources().getString(
				// R.string.pull_to_refresh_news_detail_down);
				mPullRefreshWebView.getLoadingLayoutProxy().setPullLabel("");
				mPullRefreshWebView.getLoadingLayoutProxy().setReleaseLabel("");
				mPullRefreshWebView.getLoadingLayoutProxy()
						.setLastUpdatedLabel("");
			}

		}
	};

	void setUrl(String url) {
		this.url = url;
		queue.addLast(url);
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			LogUtils.d("WEB", "errorCode:" + errorCode + ", desc:" + description
					+ ", url:" + failingUrl);
			alreadyFailed = true;
			contentWebView.setVisibility(View.GONE);
			loadLayout.flushLoadView(CustomLoadView.LOAD_FAIL);
			mPullRefreshWebView.onRefreshComplete();
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			LogUtils.d("WEB", "shouldOverrideUrlLoading:" + url);
			setUrl(url);
			refeshLogic();
			return true;
		}

		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			overrideOnPageStarted(view, url, favicon);
		}

		@Override
		public void doUpdateVisitedHistory(WebView view, String url,
				boolean isReload) {
			super.doUpdateVisitedHistory(view, url, isReload);
			overrideDoUpdateVisitedHistory(view, url, isReload);
		}
	}

	protected void overrideOnPageStarted(WebView view, String url,
			Bitmap favicon) {

	}

	protected void overrideDoUpdateVisitedHistory(WebView view, String url,
			boolean isReload) {

	}

	boolean alreadyFailed = false;

	private class MyChromeClient extends WebChromeClient {

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			LogUtils.d("WEB", "Progress:" + newProgress);
			if (alreadyFailed) {
				return;
			}

			if (newProgress <= 15) {
				// setWebViewLoading(view);
			}
			if (newProgress > 90) {
				LogUtils.d("WEB", "Progress> 90:" + newProgress);
				loadLayout.flushLoadView(CustomLoadView.LOAD_SUCESS);
				contentWebView.setVisibility(View.VISIBLE);
				mPullRefreshWebView.onRefreshComplete();
			}
		}
		
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			mHtmlTitleStr = title;
		}
	}

	// public void setLocalTextSizeData() {
	// int text_size_value = PreferencesUtils.getInt(getActivity(),
	// Constant.SETTING_TEXT_SIZE, Constant.WEBVIEW_TEXT_SIZE_KEY,
	// Constant.WEBVIEW_TEXT_SIZE_VALUE);
	// if (text_size_value <= Constant.WEBVIEW_TEXT_SIZE_LIST.length - 1) {
	// webSettings
	// .setTextSize(Constant.WEBVIEW_TEXT_SIZE_LIST[text_size_value]);
	// }
	// }

	public boolean holdGoBack() {
		// if (contentWebView != null) {
		// return contentWebView.canGoBack();
		// }
		return false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = false;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (holdGoBack()) {
				// contentWebView.goBack();
				if (queue.size() > 0) {
					queue.removeLast();
				}
				flag = true;
			}
		}
		return flag;
	}

	protected void returnLogic() {
//		if (getRecommendation() != null) {
//			LogUtils.d("PUSHLOG", "from WebActivity to MainActivity");
//			Intent intent = new Intent(myActivity, MainActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			intent.setAction(PushAssistant.ACTION_WEBRECOMMENDATION);
//			startActivity(intent);
//			getActivity().finish();
//		} else {
			getActivity().finish();
			getActivity().overridePendingTransition(R.anim.push_still,
					R.anim.push_right_out);
//		}
	}
}
