package com.sina.sinawidgetdemo.sharesdk;

import com.android.overlay.BaseManagerInterface;

public interface OnGiftStatisticsReceivedListener extends BaseManagerInterface {
	void OnGiftStatisticsReceived(String user, String fetchCount,
			String alarmCount, String giftCount);
}
