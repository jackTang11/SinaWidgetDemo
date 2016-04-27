package com.sina.sinawidgetdemo.custom.view.livelistview;

import com.android.overlay.BaseUIListener;

public interface OnRequestReceivedListener extends BaseUIListener {
    void onRequestReceiveSuccess(CommonLiveResp data);
    void onRequestReceiveFailure();
}
