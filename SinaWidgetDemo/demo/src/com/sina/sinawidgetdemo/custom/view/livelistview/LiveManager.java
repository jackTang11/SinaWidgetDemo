package com.sina.sinawidgetdemo.custom.view.livelistview;

import com.android.overlay.RunningEnvironment;

import java.util.ArrayList;
import java.util.List;

public class LiveManager {

    protected static LiveManager instance;

    protected LiveManager() {
    }

    public static LiveManager getInstance() {
        if (instance == null) {
            synchronized (LiveManager.class) {
                if (instance == null) {
                    instance = new LiveManager();
                }
            }
        }
        return instance;
    }

    public void reqeustData(final OnRequestReceivedListener l) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean succss = false;
                CommonLiveResp data = null;
                try {
                    Thread.sleep(2000L);
                    data = new CommonLiveResp();
                    CommonLiveResp.CommonLive live = new CommonLiveResp.CommonLive();
                    data.setLive(live);
                    live.setStatus("1");
                    live.setTitle("测试live");
                    List<CommonLiveResp.CommonLiveItem> list = new ArrayList<CommonLiveResp.CommonLiveItem>();
                    live.setList(list);
                    CommonLiveResp.CommonLiveItem item1 = new CommonLiveResp.CommonLiveItem();
                    item1.setTitle("title1");
                    CommonLiveResp.CommonLiveItem item2 = new CommonLiveResp.CommonLiveItem();
                    item2.setTitle("title2");
                    list.add(item1);
                    list.add(item2);
                    succss = true;
                } catch (Exception e) {
                    succss = false;
                } finally {
                    notifyRequestReceived(succss, data, l);
                }
            }
        }).start();
    }

    protected void notifyRequestReceived(final boolean success,
                                         final CommonLiveResp data,
                                         final OnRequestReceivedListener l) {
        if (null != l) {
            RunningEnvironment.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (success) {
                        l.onRequestReceiveSuccess(data);
                    } else {
                        l.onRequestReceiveFailure();
                    }
                }
            });
        }
    }
}
