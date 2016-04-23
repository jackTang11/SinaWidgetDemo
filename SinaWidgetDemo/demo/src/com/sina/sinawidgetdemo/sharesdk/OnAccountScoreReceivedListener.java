package com.sina.sinawidgetdemo.sharesdk;

import com.android.overlay.BaseManagerInterface;

public interface OnAccountScoreReceivedListener extends BaseManagerInterface {
	void onAccountScoreReceived(String user, String score);
}
