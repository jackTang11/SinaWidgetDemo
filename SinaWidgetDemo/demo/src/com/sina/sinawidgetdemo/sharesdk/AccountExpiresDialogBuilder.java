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
public class AccountExpiresDialogBuilder extends AlertDialog.Builder implements
		DialogInterface.OnDismissListener {
	public static int ACCOUNT_EXPIRES_DIALOG_DEFAULT_ID = 0xe7d0323e;
	protected final Activity activity;
	protected final int dialogId;

	protected AuthorizeActionAdapter adapter;

	public AccountExpiresDialogBuilder(Activity activity) {
		this(activity, ACCOUNT_EXPIRES_DIALOG_DEFAULT_ID, null);
	}

	public AccountExpiresDialogBuilder(Activity activity,
			AuthorizeActionAdapter adapter) {
		this(activity, ACCOUNT_EXPIRES_DIALOG_DEFAULT_ID, adapter);
	}

	public AccountExpiresDialogBuilder(Activity activity,
			AuthorizeActionAdapter adapter, String message) {
		this(activity, adapter);
	}

	/**
	 * @param activity
	 *            Parent activity.
	 * @param dialogId
	 *            Dialog ID to be removed.
	 */
	public AccountExpiresDialogBuilder(Activity activity, int dialogId,
			final AuthorizeActionAdapter adapter) {
		super(activity);
		this.activity = activity;
		this.dialogId = dialogId;
		this.adapter = adapter;

		setIcon(android.R.drawable.ic_dialog_info);
		setTitle(R.string.setting_expires_comfirm);

		setPositiveButton(R.string.expires_cancel_button,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“取消”后的操作
						dialog.cancel();
					}
				});

		setNegativeButton(R.string.expires_comfirm_button,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“确认”后的操作
						AuthorizeManager.getInstance().runInAuthorized(
								getActivity(), PlatformType.SinaWeibo, adapter,
								new DialogDismissRunnable());
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

	class DialogDismissRunnable implements Runnable {

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			if (getActivity() != null && !getActivity().isFinishing()) {
				getActivity().removeDialog(dialogId);
			}
		}
	}
}