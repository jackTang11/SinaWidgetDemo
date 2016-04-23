package com.sina.sinawidgetdemo.usercredit;

import android.app.Activity;

import com.android.overlay.RunningEnvironment;
import com.android.overlay.connection.ConnectionType;
import com.android.overlay.connection.NetworkState;
import com.android.overlay.manager.NetworkManager;
import com.android.overlay.utils.NetUtils;
import com.sina.sinawidgetdemo.custom.view.NoNetToastDialog;
import com.sina.sinawidgetdemo.R;

/**
 * Button Listener check illegal state before user click.
 * 
 * @author liu_chonghui
 * 
 */
public class CheckStateButtonAgent {

	private Runnable runnable;
	private Activity activity;

	protected Activity getActivity() {
		return activity;
	}

	public CheckStateButtonAgent(Activity activity) {
		this(activity, null);
	}

	/**
	 * Button Agent of check illegal state before user click.
	 * 
	 * @param activity
	 *            attached Activity.
	 * @param todo
	 *            Runnable of available state.
	 */
	public CheckStateButtonAgent(Activity activity, Runnable todo) {
		super();
		this.activity = activity;
		runnable = todo;
		if (runnable != null && activity != null && !activity.isFinishing()) {
			if (stateIllegal()) {
				return;
			}
			runnable.run();
		}
	}

	/**
	 * Can be Override.
	 * 
	 * @return
	 */
	protected boolean stateIllegal() {
		// Default: check network state.
		boolean legal = NetUtils.checkNetworkState(getActivity());
		if (!legal) {
			onConnectionClosed();
		}
		return !legal;
	}

	public void onConnectionClosed() {
		RunningEnvironment.getInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (activity != null && !activity.isFinishing()) {
					new NoNetToastDialog(activity).setWaitTitle(
							R.string.device_network_unavailable).showMe();
				}
			}
		});
	}

	public void onConnectionChanged(ConnectionType type) {
		int toast = -1;
		if (ConnectionType.WIFI == type) {
			toast = R.string.device_network_wifi_available;
		} else if (ConnectionType.MOBILE == type) {
			toast = R.string.device_network_mobile_available;
		}

		if (toast > 0) {
			final int toastId = toast;
			RunningEnvironment.getInstance().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (activity != null && !activity.isFinishing()) {
						new NoNetToastDialog(activity).setWaitTitle(toastId)
								.showMe();
					}
				}
			});
		}
	}

	public void checkNetworkStatus() {
		NetworkState state = NetworkManager.getInstance().getState();
		if (NetworkState.available == state) {
			if (NetUtils.checkWifiState(activity)) {
				// new CustomToastDialog(getActivity())
				// .setWaitTitle("您当前使用wifi网络").showMe();
			} else if (NetUtils.checkMobileState(activity)) {
				RunningEnvironment.getInstance().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (activity != null && !activity.isFinishing()) {
							new NoNetToastDialog(activity).setWaitTitle(
									"您当前使用2G/3G/4G网络").showMe();
						}
					}
				});
			}
		} else if (NetworkState.unavailable == state) {
			RunningEnvironment.getInstance().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (activity != null && !activity.isFinishing()) {
						new NoNetToastDialog(activity).setWaitTitle(
								R.string.device_network_unavailable).showMe();
					}
				}
			});
		}
	}
}
