package com.sina.sinawidgetdemo.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sina.sinawidgetdemo.constant.PreferencesConstant;
import com.sina.sinawidgetdemo.custom.view.SegmentedRadioGroup;
import com.sina.sinawidgetdemo.utils.PreferencesUtils;
import com.sina.sinawidgetdemo.utils.TitleViewUtils;
import com.sina.sinawidgetdemo.utils.ViewUtils;
import com.sina.sinawidgetdemo.R;

/**
 * @author liu_chonghui
 * 
 */
public class WebBrowserFragment extends WebDetailFragment {

	private ImageView moreView;
	private TextView mAdvanceTv, mBackTv, mRefreshTv;
//	private OneKeyShare myOneKeyShare;
	private static final int REFRESH_TOOLS = 100100;
	private View mTitleView;
	private boolean isCollected;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REFRESH_TOOLS:
				refreshWebTool();
				break;
			}
		}
	};

	@Override
	protected int getLayoutResId() {
		return R.layout.web_browser;
	}

	@Override
	protected void initView() {
		super.initView();
		mAdvanceTv = (TextView) getView().findViewById(R.id.web_advance);
		mAdvanceTv.setOnClickListener(this);
		mBackTv = (TextView) getView().findViewById(R.id.web_back);
		mBackTv.setOnClickListener(this);
		mRefreshTv = (TextView) getView().findViewById(R.id.web_refresh);
		mRefreshTv.setOnClickListener(this);

		mTitleView = getView().findViewById(R.id.main_title_layout);
		// title = (TextView) getView().findViewById(R.id.title_content);
		TitleViewUtils.addRightTitleLayout(getActivity(), mTitleView,
				R.layout.web_title_right);
		TitleViewUtils.setBackGround(mTitleView, R.color.white);
		moreView = (ImageView) getView().findViewById(R.id.more_imageview);
		moreView.setOnClickListener(this);
		TitleViewUtils.setReturnListener(mTitleView, this);
		initShare();
		titleStr = getIntentTitle(getActivity().getIntent());
		// if (title != null) {
		// titleStr = getIntentTitle(getActivity().getIntent());
		// title.setText(titleStr);
		// }
		getCollectStatus();
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		final int id = view.getId();
		if (R.id.title_turn_return == id) {
			returnLogic();
		} else if (R.id.more_imageview == id) {
			// 弹出分享窗口
			shareAction();
		} else if (R.id.web_advance == id) {
			// 前进
			if (contentWebView.canGoForward()) {
				contentWebView.goForward();
			}
		} else if (R.id.web_back == id) {
			// 后退
			if (contentWebView.canGoBack()) {
				contentWebView.goBack();
			}
		} else if (R.id.web_refresh == id) {
			// 刷新
			refeshLogic();
		}
	}

	/**
	 * 刷新web控件
	 */
	private void refreshWebTool() {
		if (mBackTv != null) {
			if (contentWebView.canGoBack()) {
				mBackTv.setTextColor(getResources().getColor(R.color.black));
			} else {
				mBackTv.setTextColor(Color.GRAY);
			}
			mBackTv.setEnabled(contentWebView.canGoBack());
		}

		if (mAdvanceTv != null) {
			if (contentWebView.canGoForward()) {
				mAdvanceTv.setTextColor(getResources().getColor(R.color.black));
			} else {
				mAdvanceTv.setTextColor(Color.GRAY);
			}
			mAdvanceTv.setEnabled(contentWebView.canGoForward());
		}
	}

	@Override
	protected void refeshLogic() {
		// TODO Auto-generated method stub
		super.refeshLogic();

	}

	public boolean holdGoBack() {
//		if (myOneKeyShare != null && myOneKeyShare.isShow()) {
//			return true;
//		}
		return super.holdGoBack();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = false;
		flag = super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (holdGoBack()) {
//				if (myOneKeyShare != null && myOneKeyShare.isShow()) {
//					myOneKeyShare.close();
//				}
				flag = true;
			}
		}
		return flag;
	}

	@Override
	protected void overrideOnPageStarted(WebView view, String url,
			Bitmap favicon) {
		// TODO Auto-generated method stub
		super.overrideOnPageStarted(view, url, favicon);
		mHandler.sendEmptyMessage(REFRESH_TOOLS);
	}

	@Override
	protected void overrideDoUpdateVisitedHistory(WebView view, String url,
			boolean isReload) {
		// TODO Auto-generated method stub
		super.overrideDoUpdateVisitedHistory(view, url, isReload);
		mHandler.sendEmptyMessage(REFRESH_TOOLS);
	}

	protected void shareLogic() {
		String sAgeFormat1 = getResources().getString(
				R.string.web_browser_share_content);
		String content = String.format(sAgeFormat1, titleStr);
		Bitmap img = ViewUtils.getScreenShot(myActivity);
		// if(img!=null)
		// img = ViewUtils.scaleBitmapImg(img, 300, 300);

//		ShareSelectModel item = new ShareSelectModel();
//		String appname = getActivity().getString(R.string.app_name);
//		item.setTitle(titleStr == null || titleStr.length() == 0 ? appname
//				: titleStr);
//		item.setContent(content);
//		item.setImgUrl(url);
//		item.setWeb_url(url);
//		myOneKeyShare.setShareContent(item);
//		myOneKeyShare.show(getActivity(), getClass().getName());
	}

	protected PopupWindow popupWindow_textsize;
	protected LinearLayout textsizeAnLayout;
	protected int current_textszie;
	protected Animation textsizeAnClose;
	protected Animation textsizeAnOpen;

	protected void initTextsizeAn() {
		if (textsizeAnOpen == null) {
			textsizeAnOpen = AnimationUtils.loadAnimation(getActivity(),
					R.anim.menushow);
		}
		if (textsizeAnClose == null) {
			textsizeAnClose = AnimationUtils.loadAnimation(getActivity(),
					R.anim.menuhide);
		}
	}

	protected void showPopTextSize(View view) {
		View textsizeView = LayoutInflater.from(getActivity()).inflate(
				R.layout.textsize_popsetting, null);
		textsizeView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				closeTextsizePop();
			}
		});
		textsizeAnLayout = (LinearLayout) textsizeView
				.findViewById(R.id.textsize_pop_an_layout);
		Button btn_textsize_canncl = (Button) textsizeView
				.findViewById(R.id.btn_textsize_canncl);
		btn_textsize_canncl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				closeTextsizePop();
			}
		});
		SegmentedRadioGroup segmentImg = (SegmentedRadioGroup) textsizeView
				.findViewById(R.id.segment_img);
		int text_size_value = PreferencesUtils.getInt(getActivity(),
				PreferencesConstant.SETTING_TEXT_SIZE,
				PreferencesConstant.WEBVIEW_TEXT_SIZE_KEY,
				PreferencesConstant.WEBVIEW_TEXT_SIZE_VALUE);
		int checkId = R.id.button_font_medium;
		if (text_size_value == 0) {
			checkId = R.id.button_font_low;
		} else if (text_size_value == 1) {
			checkId = R.id.button_font_medium;
		} else if (text_size_value == 2) {
			checkId = R.id.button_font_high;
		} else if (text_size_value == 3) {
			checkId = R.id.button_font_exhigh;
		}
		segmentImg.check(checkId);
		segmentImg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.button_font_low) {
					current_textszie = 0;
				} else if (checkedId == R.id.button_font_medium) {
					current_textszie = 1;
				} else if (checkedId == R.id.button_font_high) {
					current_textszie = 2;
				} else if (checkedId == R.id.button_font_exhigh) {
					current_textszie = 3;
				} else {
					return;
				}
				PreferencesUtils.writeInt(getActivity(),
						PreferencesConstant.SETTING_TEXT_SIZE,
						PreferencesConstant.WEBVIEW_TEXT_SIZE_KEY,
						current_textszie);
			}
		});

		popupWindow_textsize = new PopupWindow(textsizeView,
				RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		popupWindow_textsize.setBackgroundDrawable(new BitmapDrawable());

		showTextsizePop();
	}

	public void showTextsizePop() {
		if (popupWindow_textsize != null && !popupWindow_textsize.isShowing()) {
			popupWindow_textsize.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
			textsizeAnLayout.startAnimation(textsizeAnOpen);
		}
	}

	public void closeTextsizePop() {
		if (popupWindow_textsize != null && popupWindow_textsize.isShowing()) {
			textsizeAnClose.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					new Handler().post(new Runnable() {
						@Override
						public void run() {
							if (popupWindow_textsize != null
									&& popupWindow_textsize.isShowing()) {
								popupWindow_textsize.dismiss();
							}
						}
					});
				}
			});
			textsizeAnLayout.startAnimation(textsizeAnClose);
		}
	}

	/**
	 * 分享操作
	 */
	public void shareAction() {
		// TODO 点击分享
		if (TextUtils.isEmpty(titleStr) || TextUtils.isEmpty(url)) {
			return;
		}
		shareLogic();
	}

	/**
	 * 获取收藏状态
	 */
	private void getCollectStatus() {
//		String account = AccountManager.getInstance().getCurrentAccount();
//		if (account == null || TextUtils.isEmpty(account) || url == null
//				|| TextUtils.isEmpty(url)) {
//			return;
//		}
		// List<CollectListModel> data = CollectDbProccess.getDbListForId(url,
		// CollectListModel.COLLECT_WEB_TYPE);
		// if (data != null && data.size() > 0) {
		// // 已经收藏
		// isCollected = true;
		// } else {
		// // 未收藏
		// isCollected = false;
		// }
//		myOneKeyShare.setCollectState(isCollected);
	}

	protected void initShare() {
		initTextsizeAn();
//		myOneKeyShare = ShareManager.getInstance()
//				.getWebBrowserPageOneKeyShare(getActivity(), null);
//		myOneKeyShare.setOnClickListener(ShareMethod.FONT,
//				new OnClickListener() {
//					@Override
//					public void onClick(View view) {
//						showPopTextSize(mView);
//						if (myOneKeyShare != null) {
//							myOneKeyShare.close();
//						}
//					}
//				});
//
//		myOneKeyShare.setOnClickListener(ShareMethod.COLLECT,
//				new OnClickListener() {
//					@Override
//					public void onClick(View view) {
//						if (!AuthorizeManager.getInstance().isAuthorized()) {
//							AuthorizeManager.getInstance().doAuthorize(
//									getActivity());
//							return;
//						}
//						if (url == null || TextUtils.isEmpty(url))
//							return;
//						// collect or cancel collect isCollected ? 0 : 1
//						requestCancelOrCollect(!isCollected);
//						if (myOneKeyShare != null) {
//							myOneKeyShare.close();
//						}
//					}
//				});
	}

	/**
	 * 关注OR取消关注 param 是否为关注
	 * 
	 * @param isCollect
	 */
	private void requestCancelOrCollect(boolean isCollect) {
//		String account = AccountManager.getInstance().getCurrentAccount();
//		if (account == null || TextUtils.isEmpty(account) || url == null
//				|| TextUtils.isEmpty(url)) {
//			return;
//		}
		isCollect = true; // v1.1要求web页中的收藏按钮只有收藏操作无取消操作
	}
}
