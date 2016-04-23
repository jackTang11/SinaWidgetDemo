package com.sina.sinawidgetdemo.sharesdk;

import com.android.overlay.BaseManagerInterface;

public interface OnCollectStatisticsReceivedListener extends
		BaseManagerInterface {
	void OnCollectStatisticsReceived(String user, String collectCount);
}
