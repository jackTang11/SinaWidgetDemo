package com.sina.sinawidgetdemo.constant;

import android.webkit.WebSettings;

public class PreferencesConstant {
	// 本地网络存储文件
	public static final String SETTING_WIFI = "wifiSetting";
	// 推送使用
	public static final String SETTING_PUSH = "pushSetting";
	public static final String SETTING_PUSH_VIDEO = "pushSettingVideo";
	public static final String SETTING_PUSH_GIFT = "pushSettingGift";
	// 字体大小值
	public static int WEBVIEW_TEXT_SIZE_VALUE = 1;
	// 字体大小key
	public static String WEBVIEW_TEXT_SIZE_KEY = "text_size";
	// 本地字体大小文件
	public static final String SETTING_TEXT_SIZE = "textSizeSetting";
	public static WebSettings.TextSize[] WEBVIEW_TEXT_SIZE_LIST = {
			WebSettings.TextSize.SMALLER, WebSettings.TextSize.NORMAL,
			WebSettings.TextSize.LARGER, WebSettings.TextSize.LARGEST };
	
	
	/**判断是否进入引导页的版本号**/
    public static String GUIDE_VERSION_FILE = "guide_version_file";
    public static String GUIDE_VERSION_KEY = "guide_version_key";
    
    /** 审核开关配置 **/
    public static String SWITCH_DOWNLOAD_BUTTON = "switch_download_button";
    public static String SWITCH_GIFT_SHOW = "switch_gift_show";
    public static String SWITCH_APP_RECOMMEND = "switch_app_recommend";
    
    /**弹幕开关**/
    public static String DAN_OPTION_FILE = "dan_option_file";
    public static String DAN_OPTION_KEY = "dan_option_key";
	
}
