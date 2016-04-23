package com.sina.sinawidgetdemo.sharesdk;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.android.overlay.RunningEnvironment;
import com.sina.sinawidgetdemo.R;
import com.sina.sinagame.share.ShareManager;
import com.sina.sinagame.share.ShareMethod;
import com.sina.sinagame.share.entity.ShareSelectModel;
import com.sina.sinagame.share.impl.OneKeyShare;
import com.sina.sinagame.share.impl.PlatformObserver;

@SuppressWarnings("serial")
class Sina973ShareManager extends ShareManager {

	static {
		RunningEnvironment.getInstance().removeManager(instance);
		instance = new Sina973ShareManager();
		RunningEnvironment.getInstance().addManager(instance);
	}

	public Sina973ShareManager() {
		super();
	}

	public OneKeyShare getOneKeyShare(Activity context,
			List<ShareSelectModel> modelList, PlatformObserver platformObserver) {
		this.oneKeyShare = new Sina973OneKeyShareAttacher(context, modelList,
				platformObserver == null ? this : platformObserver);
		return this.oneKeyShare;
	}

	@Override
	public OneKeyShare getDetailPageOneKeyShare(Activity context,
			PlatformObserver platformObserver) {
		List<ShareSelectModel> modelList = new ArrayList<ShareSelectModel>();
		ShareSelectModel model1 = new ShareSelectModel(ShareMethod.SinaWeibo,
				context.getResources().getString(R.string.share_sina), null,
				null, R.drawable.share_sinaweibo);
		ShareSelectModel model2 = new ShareSelectModel(ShareMethod.Wechat,
				context.getResources().getString(R.string.share_wx_friend),
				null, null, R.drawable.share_weixin);
		ShareSelectModel model3 = new ShareSelectModel(
				ShareMethod.WechatMoment, context.getResources().getString(
						R.string.share_wx_circle), null, null,
				R.drawable.share_weixin_friend);
		ShareSelectModel model4 = new ShareSelectModel(ShareMethod.FONT,
				context.getResources().getString(
						R.string.function_textsize), null, null,
				R.drawable.func_textsize);
		modelList.add(model1);
		modelList.add(model2);
		modelList.add(model3);
		modelList.add(model4);
		return getOneKeyShare(context, modelList, platformObserver);
	}

	public OneKeyShare getDetailPageWithoutFontOneKeyShare(Activity context,
			PlatformObserver platformObserver) {
		List<ShareSelectModel> modelList = new ArrayList<ShareSelectModel>();
		ShareSelectModel model1 = new ShareSelectModel(ShareMethod.SinaWeibo,
				context.getResources().getString(R.string.share_sina), null,
				null, R.drawable.share_sinaweibo);
		ShareSelectModel model2 = new ShareSelectModel(ShareMethod.Wechat,
				context.getResources().getString(R.string.share_wx_friend),
				null, null, R.drawable.share_weixin);
		ShareSelectModel model3 = new ShareSelectModel(
				ShareMethod.WechatMoment, context.getResources().getString(
						R.string.share_wx_circle), null, null,
				R.drawable.share_weixin_friend);
		modelList.add(model1);
		modelList.add(model2);
		modelList.add(model3);
		return getOneKeyShare(context, modelList, platformObserver);
	}

	@Override
	public OneKeyShare getWebBrowserPageOneKeyShare(Activity context,
			PlatformObserver platformObserver) {
		List<ShareSelectModel> modelList = new ArrayList<ShareSelectModel>();
		ShareSelectModel model1 = new ShareSelectModel(ShareMethod.SinaWeibo,
				context.getResources().getString(R.string.share_sina), null,
				null, R.drawable.share_sinaweibo);
		ShareSelectModel model2 = new ShareSelectModel(ShareMethod.Wechat,
				context.getResources().getString(R.string.share_wx_friend),
				null, null, R.drawable.share_weixin);
		ShareSelectModel model3 = new ShareSelectModel(
				ShareMethod.WechatMoment, context.getResources().getString(
						R.string.share_wx_circle), null, null,
				R.drawable.share_weixin_friend);
		ShareSelectModel model4 = new ShareSelectModel(ShareMethod.OPEN_URL,
				context.getResources().getString(R.string.function_open_browser),
				null, null, R.drawable.open_safari);
		ShareSelectModel model5 = new ShareSelectModel(ShareMethod.COPY_URL,
				context.getResources().getString(R.string.function_do_copy), null,
				null, R.drawable.share_link);
		ShareSelectModel model6 = new ShareSelectModel(ShareMethod.COLLECT,
				context.getResources().getString(
						R.string.function_do_collect), null, null,
				R.drawable.func_do_collect);
		modelList.add(model1);
		modelList.add(model2);
		modelList.add(model3);
		modelList.add(model4);
		modelList.add(model5);
		modelList.add(model6);
		return getOneKeyShare(context, modelList, platformObserver);
	}

	@Override
	public OneKeyShare getGiftPageOneKeyShare(Activity context,
			PlatformObserver platformObserver) {
		List<ShareSelectModel> modelList = new ArrayList<ShareSelectModel>();
		ShareSelectModel model1 = new ShareSelectModel(ShareMethod.SinaWeibo,
				context.getResources().getString(R.string.share_sina), null,
				null, R.drawable.share_sinaweibo);
		ShareSelectModel model2 = new ShareSelectModel(ShareMethod.Wechat,
				context.getResources().getString(R.string.share_wx_friend),
				null, null, R.drawable.share_weixin);
		ShareSelectModel model3 = new ShareSelectModel(
				ShareMethod.WechatMoment, context.getResources().getString(
						R.string.share_wx_circle), null, null,
				R.drawable.share_weixin_friend);
		modelList.add(model1);
		modelList.add(model2);
		modelList.add(model3);
		return getOneKeyShare(context, modelList, platformObserver);
	}

}
