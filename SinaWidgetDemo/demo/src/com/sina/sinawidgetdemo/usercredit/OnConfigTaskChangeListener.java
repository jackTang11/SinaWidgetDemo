package com.sina.sinawidgetdemo.usercredit;

import java.util.List;

import com.android.overlay.BaseManagerInterface;
import com.sina.sinawidgetdemo.returnmodel.ConfigModel;
import com.sina.sinawidgetdemo.sharesdk.Task;

public interface OnConfigTaskChangeListener extends BaseManagerInterface {

	void onConfigTaskChanged(ConfigModel config, List<Task> tasks);
}
