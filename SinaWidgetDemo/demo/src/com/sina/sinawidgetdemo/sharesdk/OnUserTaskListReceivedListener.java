package com.sina.sinawidgetdemo.sharesdk;

import java.util.List;

import com.android.overlay.BaseManagerInterface;

public interface OnUserTaskListReceivedListener extends BaseManagerInterface {
	void onUserTaskListReceived(String user, List<Task> taskList);
}
