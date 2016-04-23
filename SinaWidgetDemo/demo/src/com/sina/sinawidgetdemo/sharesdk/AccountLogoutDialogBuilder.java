package com.sina.sinawidgetdemo.sharesdk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.sina.sinawidgetdemo.R;
import com.sina.sinagame.share.platforms.PlatformType;
import com.sina.sinagame.sharesdk.AuthorizeActionAdapter;
import com.sina.sinagame.sharesdk.AuthorizeManager;

/**
 * 
 * @author liu_chonghui
 * 
 */
public class AccountLogoutDialogBuilder extends AlertDialog.Builder implements
		DialogInterface.OnDismissListener {
	public static int ACCOUNT_LOGOUT_DIALOG_DEFAULT_ID = 0xe7d0313f;
	protected final Activity activity;
	protected final int dialogId;

	protected AuthorizeActionAdapter adapter;

	public AccountLogoutDialogBuilder(Activity activity) {
		this(activity, ACCOUNT_LOGOUT_DIALOG_DEFAULT_ID, null);
	}

	public AccountLogoutDialogBuilder(Activity activity,
			AuthorizeActionAdapter listener) {
		this(activity, ACCOUNT_LOGOUT_DIALOG_DEFAULT_ID, listener);
	}

	/**
	 * @param activity
	 *            Parent activity.
	 * @param dialogId
	 *            Dialog ID to be removed.
	 */
	public AccountLogoutDialogBuilder(Activity activity, int dialogId,
			final AuthorizeActionAdapter adapter) {
		super(activity);
		this.activity = activity;
		this.dialogId = dialogId;
		this.adapter = adapter;

		setTitle(R.string.setting_logout_comfirm);
		setIcon(android.R.drawable.ic_dialog_info);

		setPositiveButton(R.string.tip_comfirm,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
						// new CheckStateButtonAgent(getActivity(),
						// new Runnable() {
						// @Override
						// public void run() {
						AuthorizeManager.getInstance().unauthorize(
								getActivity(), PlatformType.SinaWeibo, adapter);
						// }
						// });
					}
				});

		setNegativeButton(R.string.tip_cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (adapter != null) {
							adapter.onLogoutCanceled(PlatformType.SinaWeibo);
						}
					}
				});
	}

	@Override
	public AlertDialog create() {
		AlertDialog alertDialog = super.create();
		alertDialog.setOnDismissListener(this);
		return alertDialog;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDismiss(DialogInterface dialog) {
		activity.removeDialog(dialogId);
	}

	public int getDialogId() {
		return dialogId;
	}

	protected Activity getActivity() {
		return this.activity;
	}
}
