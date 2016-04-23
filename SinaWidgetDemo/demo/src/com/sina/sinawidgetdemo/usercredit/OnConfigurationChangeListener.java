package com.sina.sinawidgetdemo.usercredit;

import com.android.overlay.BaseManagerInterface;
import com.sina.sinawidgetdemo.returnmodel.ConfigModel;

public interface OnConfigurationChangeListener extends BaseManagerInterface {

	void onConfigurationChanged(ConfigModel config);
}
