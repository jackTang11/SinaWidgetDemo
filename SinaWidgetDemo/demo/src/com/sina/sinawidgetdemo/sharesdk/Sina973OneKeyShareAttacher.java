package com.sina.sinawidgetdemo.sharesdk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.overlay.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiscCacheUtils;
import com.sina.engine.base.manager.EngineManager;
import com.sina.sinawidgetdemo.custom.view.CustomToastDialog;
import com.sina.sinawidgetdemo.usercredit.CheckStateButtonAgent;
import com.sina.sinawidgetdemo.R;
import com.sina.sinagame.share.ShareMethod;
import com.sina.sinagame.share.entity.ShareParams;
import com.sina.sinagame.share.entity.ShareSelectModel;
import com.sina.sinagame.share.impl.OneKeyShare;
import com.sina.sinagame.share.impl.Platform;
import com.sina.sinagame.share.impl.PlatformObserver;
import com.sina.sinagame.share.platforms.PlatformManager;
import com.sina.sinagame.share.platforms.PlatformType;
import com.sina.sinagame.sharesdk.AuthorizeActionAdapter;
import com.sina.sinagame.sharesdk.AuthorizeManager;
import com.sina.sinagame.sharesdk.ShareActionAdapter;
import com.sina.sinagame.windowattacher.FooterPopupAttacher;

/**
 * OneKeyShareAttacher for 97973.
 * 
 * @author liu_chonghui
 * 
 */
@SuppressWarnings("deprecation")
class Sina973OneKeyShareAttacher extends FooterPopupAttacher implements
		OneKeyShare {

	protected List<ShareSelectModel> modelList = new ArrayList<ShareSelectModel>();
	protected PlatformObserver platformObserver;
	protected GridView adapterView;
	protected GridAdapter mAdapter;

	public Sina973OneKeyShareAttacher(Activity attachedActivity,
			List<ShareSelectModel> modelList, PlatformObserver PlatformObserver) {
		this(attachedActivity);
		this.setAnimation(R.anim.mysharesdk_select_in,
				R.anim.mysharesdk_select_out);
		this.modelList = modelList;
		mAdapter = new GridAdapter(modelList);
		this.platformObserver = PlatformObserver;
	}

	protected Sina973OneKeyShareAttacher(Activity attachedActivity,
			int layoutResId, int animationViewGroupId) {
		super(attachedActivity, R.layout.sina_game_onekeyshare_popupwindow,
				R.id.popup_animation_layout);
	}

	public Sina973OneKeyShareAttacher(Activity attachedActivity) {
		this(attachedActivity, R.layout.sina_game_onekeyshare_popupwindow,
				R.id.popup_animation_layout);
	}

	@Override
	public void findViewByContentView(View contentView) {
		adapterView = (GridView) contentView.findViewById(R.id.adapter_view);
		contentView.findViewById(R.id.cancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View view) {
						close();
					}
				});
	}

	@Override
	public void adjustContentView(View contentView) {
		super.adjustContentView(contentView);
		adapterView.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
	}

	protected OnClickListener collectListener, textSizeListener;
	protected OnClickListener activateListener, launchListener,
			downloadListener;
	protected ShareActionAdapter actionListener;

	class GridAdapter extends BaseAdapter {
		List<ShareSelectModel> mModelList = new ArrayList<ShareSelectModel>();

		public GridAdapter(List<ShareSelectModel> list) {
			setData(list);
		}

		public void setData(List<ShareSelectModel> mModelList) {
			this.mModelList.clear();
			this.mModelList.addAll(mModelList);
		}

		public int getCount() {
			return mModelList.size();
		}

		@Override
		public Object getItem(int index) {
			return mModelList.get(index);
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		protected int getItemLayout() {
			return R.layout.one_key_share_item;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ShareSelectModel model = mModelList.get(position);
			final String name = model.getIcon_name();

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						getItemLayout(), null);
				holder.title = (TextView) convertView
						.findViewById(R.id.myshare_select_item_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText(name);

			Drawable drawable = getActivity().getResources().getDrawable(
					model.getIconId());
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.title.setCompoundDrawables(null, drawable, null, null);
			if (ShareMethod.COLLECT == model.getMethod()) {
				if (isCollect) {
					String text = "";
					if (isCollect) {
						drawable = getActivity().getResources().getDrawable(
								R.drawable.func_do_collect_cancel);
						text = getActivity().getResources().getString(
								R.string.function_do_collect_cancel);
					} else {
						drawable = getActivity().getResources().getDrawable(
								R.drawable.func_do_collect);
						text = getActivity().getResources().getString(
								R.string.function_do_collect);
					}
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					holder.title.setCompoundDrawables(null, drawable, null,
							null);
					holder.title.setText(text);
				}
			}

			final ShareSelectModel dialogModel = model;

			if (ShareMethod.COLLECT == dialogModel.getMethod()) {
				convertView.setOnClickListener(collectListener);
			} else if (ShareMethod.FONT == dialogModel.getMethod()) {
				convertView.setOnClickListener(textSizeListener);
			} else if (ShareMethod.ACTIVATE == dialogModel.getMethod()) {
				convertView.setOnClickListener(activateListener);
			} else if (ShareMethod.LAUNCH == dialogModel.getMethod()) {
				convertView.setOnClickListener(launchListener);
			} else if (ShareMethod.DOWNLOAD == dialogModel.getMethod()) {
				convertView.setOnClickListener(downloadListener);
			} else if (ShareMethod.COPY_URL == dialogModel.getMethod()) {
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						// 复制到粘贴板
						if (getActivity() == null
								|| dialogModel.getWeb_url() == null) {
							return;
						}
						((ClipboardManager) getActivity().getSystemService(
								Context.CLIPBOARD_SERVICE)).setText(dialogModel
								.getWeb_url());
						new CustomToastDialog(getActivity()).setWaitTitle(
								"已将当前链接复制到剪贴板").showMe();
					}
				});
			} else if (ShareMethod.OPEN_URL == dialogModel.getMethod()) {
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						if (getActivity() == null
								|| dialogModel.getWeb_url() == null) {
							return;
						}
						if (StringUtils.isWebUrl(dialogModel.getWeb_url())) {
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							Uri uri = Uri.parse(dialogModel.getWeb_url());
							intent.setData(uri);
							getActivity().startActivity(intent);
						} else {
							new CustomToastDialog(getActivity()).setWaitTitle(
									"操作失败").showMe();
						}
					}
				});
			} else {
				convertView
						.setOnClickListener(new OnPlatformButtonClickListener(
								model));
			}
			return convertView;
		}

		class ViewHolder {
			TextView title;
		}
	}

	@Override
	public void setShareContent(ShareSelectModel content) {
		for (ShareSelectModel model : modelList) {
			model.cloneAttribute(content);
		}
		flushList();
	}

	@Override
	public void setShareContent(String title, String content, Bitmap img,
			String webUrl) {
		for (ShareSelectModel model : modelList) {
			model.setContent(content);
			model.setImage(img);
			model.setTitle(title);
			model.setWeb_url(webUrl);
		}
		flushList();
	}

	@Override
	public void setShareContent(String title, String content, String imgUrl,
			String webUrl) {
		for (ShareSelectModel model : modelList) {
			model.setContent(content);
			model.setImgUrl(imgUrl);
			model.setTitle(title);
			model.setWeb_url(webUrl);
		}
		flushList();
	}

	protected void flushList() {
		mAdapter.setData(modelList);
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void setCollectState(boolean isCollect) {
		this.isCollect = isCollect;
		if (mAdapter != null && isShowing()) {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void show(Activity context, View parensView, String channelId,
			String newsId, String sid) {
		// isCollect = EngineFactory.getInstance().dbManager.getCollectDaoImpl()
		// .isCollect(channelId, newsId, sid);
		show(context, newsId);
	}

	@Override
	public void show(Activity context, View parensView, String newsId) {
		show(context, newsId);
	}

	protected boolean isCollect = false;
	protected String newsId;

	@Override
	public void show(Activity context, String newsId) {
		if (EngineManager.DEBUG) {
			if (newsId == null) {
				throw new IllegalArgumentException("newsid should not be null!");
			}
		}
		this.newsId = newsId;
		show();
	}

	@Override
	public void setOnClickListener(ShareMethod type, OnClickListener listener) {
		if (ShareMethod.COLLECT == type) {
			this.collectListener = listener;
		} else if (ShareMethod.FONT == type) {
			this.textSizeListener = listener;
		} else if (ShareMethod.ACTIVATE == type) {
			this.activateListener = listener;
		} else if (ShareMethod.LAUNCH == type) {
			this.launchListener = listener;
		} else if (ShareMethod.DOWNLOAD == type) {
			this.downloadListener = listener;
		}
	}

	public void setOnShareActionListener(ShareActionAdapter listener) {
		this.actionListener = listener;
	}

	@Override
	public boolean isShow() {
		return isShowing();
	}

	@Override
	public void close() {
		closePop();
	}

	protected void onPlatformBtnClick(final ShareSelectModel model) {
		PlatformType type = null;
		ShareMethod method = model.getMethod();
		if (method != null) {
			for (PlatformType value : PlatformType.values()) {
				if (value.name().equalsIgnoreCase(method.name())) {
					type = value;
					break;
				}
			}
		}
		if (PlatformType.SinaWeibo == type) {
			AuthorizeManager.getInstance().doInAuthorized(getActivity(),
					new ShareLogicRunnable(model));
		} else {
			new ShareLogicRunnable(model).run();
		}
	}

	class ShareLogicRunnable implements Runnable {
		ShareSelectModel model;

		public ShareLogicRunnable(ShareSelectModel model) {
			this.model = model;
		}

		@Override
		public void run() {
			shareLogic(model, new AuthorizeActionAdapter() {
				@Override
				public void onShareSuccess(PlatformType type) {
					if (actionListener != null) {
						actionListener.onShareSuccess(type);
					}
					close();
				}

				@Override
				public void onShareFailure(PlatformType type) {
					if (actionListener != null) {
						actionListener.onShareFailure(type);
					}
				}

				@Override
				public void onShareCanceled(PlatformType type) {
					if (actionListener != null) {
						actionListener.onShareCanceled(type);
					}
				}
			});
		}
	}

	class OnPlatformButtonClickListener implements View.OnClickListener {
		ShareSelectModel model;

		public OnPlatformButtonClickListener(ShareSelectModel model) {
			this.model = model;
		}

		@Override
		public void onClick(View view) {
			new CheckStateButtonAgent(getActivity(), new Runnable() {
				@Override
				public void run() {
					onPlatformBtnClick(model);
				}
			});
		}
	}

	protected void shareLogic(ShareSelectModel dialogModel,
			AuthorizeActionAdapter shareListener) {
		PlatformType type = null;
		ShareMethod method = dialogModel.getMethod();
		if (method != null) {
			for (PlatformType value : PlatformType.values()) {
				if (value.name().equalsIgnoreCase(method.name())) {
					type = value;
					break;
				}
			}
		}
		Platform sharePlatform = PlatformManager.getInstance().getPlatform(
				getActivity(),
				type,
				AuthorizeManager.getInstance().getAuthorizeAdapter(
						shareListener));

		ShareParams params = new ShareParams();
		params.title = dialogModel.getTitle();
		params.text = dialogModel.getContent();
		params.web_url = dialogModel.getWeb_url();
		params.img = dialogModel.getImage();
		params.imgUrl = dialogModel.getImgUrl();
		if (params.img == null && params.imgUrl != null) {
			File imageFile = DiscCacheUtils.findInCache(params.imgUrl,
					ImageLoader.getInstance().getDiscCache());
			if (imageFile != null) {
				params.img = com.sina.sinawidgetdemo.utils.CommonUtils
						.createBitmapByWH(imageFile.getPath(), 100, 100);
			}
			if (params.img == null) {
				params.img = BitmapFactory.decodeResource(getActivity()
						.getResources(), R.drawable.sina973_icon);
			}
		}
		params.appname = dialogModel.getAppname();
		if (params.appname == null || params.appname.length() == 0) {
			params.appname = getActivity().getString(R.string.app_name);
		}

		if (sharePlatform != null) {
			sharePlatform.share(params);
		}
		if (platformObserver != null) {
			platformObserver.returnPlatform(sharePlatform);
		}
	}

	@Override
	public boolean isCollect() {
		return this.isCollect;
	}

	@Override
	public void setMethodVisible(ShareMethod method, boolean visible) {
		
	}

}