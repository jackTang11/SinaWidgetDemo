package com.sina.sinawidgetdemo.sharesdk;

import com.android.overlay.BaseManagerInterface;

public interface OnGameStatisticsReceivedListener extends BaseManagerInterface {
	void OnGameStatisticsReceived(String user, String gameDynamicCount,
			String myGameCount);
}
