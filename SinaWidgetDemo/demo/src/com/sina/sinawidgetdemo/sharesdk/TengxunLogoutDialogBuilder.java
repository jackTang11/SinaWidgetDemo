package com.sina.sinawidgetdemo.sharesdk;

import android.app.Activity;
import android.content.DialogInterface;

import com.sina.sinawidgetdemo.usercredit.CheckStateButtonAgent;
import com.sina.sinawidgetdemo.R;
import com.sina.sinagame.share.platforms.PlatformType;
import com.sina.sinagame.sharesdk.AuthorizeActionAdapter;
import com.sina.sinagame.sharesdk.AuthorizeManager;

/**
 * Abandoned by Ver2
 *
 * @author liu_chonghui
 */
@Deprecated
public class TengxunLogoutDialogBuilder extends AccountLogoutDialogBuilder {

	public static int TENGXUN_LOGOUT_DIALOG_DEFAULT_ID = 0xe7d03140;

	public TengxunLogoutDialogBuilder(Activity activity) {
		this(activity, TENGXUN_LOGOUT_DIALOG_DEFAULT_ID, null);
	}

	public TengxunLogoutDialogBuilder(Activity activity,
			AuthorizeActionAdapter listener) {
		this(activity, TENGXUN_LOGOUT_DIALOG_DEFAULT_ID, listener);
	}

	public TengxunLogoutDialogBuilder(Activity activity, int dialogId,
			final AuthorizeActionAdapter adapter) {
		super(activity, dialogId, adapter);
		setPositiveButton(R.string.tip_comfirm,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
						new CheckStateButtonAgent(getActivity(),
								new Runnable() {
									@Override
									public void run() {
										AuthorizeManager
												.getInstance()
												.unauthorize(
														getActivity(),
														PlatformType.TencentWeibo,
														adapter);
									}
								});
					}
				});

	}

}
