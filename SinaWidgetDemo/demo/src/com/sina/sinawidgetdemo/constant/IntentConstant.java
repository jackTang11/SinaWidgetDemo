package com.sina.sinawidgetdemo.constant;

/**
 * 传递数据常量类
 * 
 * @author kangshaozhe
 * 
 */
public class IntentConstant {
	/** 游戏合集传递ID **/
	public static final String GSET_ID = "gSetId";
	public static final String GAME_TITLE = "gameTitle";

	/** 游戏详情页面 **/
	public static final String GAME_LABEL_ID = "gameLabelId";
	public static final String GAME_ID = "gameId";

	/** 游戏列表类型 **/
	public static final int NEW_TYPE = 0;
	public static final int HOT_TYPE = 1;
	public static final String INTENT_GAME_DETAIL_LABLE_ID = "game_detail_label_id";
	public static final String INTENT_GAME_DETAIL_LABLE_NAME = "game_detail_label_name";

	/** 图片浏览页 **/
	public static final String IMAGE_BROWSE_OBJECT = "image_browse_object";

	/** 新闻详情 **/
	public static final String NEWS_DETAIL_NEWSID = "news_detail_newsid";
	public static final String INTENT_NEWS_DETAIL_LIST = "news_detail_newslist";
	public static final String INTENT_NEWS_DETAIL_SHAREIMG_URL = "news_detail_shareimage_url";

	/** 搜索页面 **/
	public static final String INTENT_SEARCH_NAME = "search_name";
	public static final String INTENT_SEARCH_TYPE = "search_type";

	public static final int SEARCH_GAME_TYPE = 0;
	public static final int SEARCH_GIFT_TYPE = 1;
	public static final int SEARCH_ATTENDGAME_TYPE = 2;
	/** 礼包详情 **/
	public static final String INTENT_GIFT_DETAIL_ID = "giftdetailid";
	public static final String INTENT_GIFT_DETAIL_OT = "giftdetailot";

	/** 攻略新闻 */
	public static final String INTENT_NEWS_DETAIL_READNEWS_KEY = "newsdetail_readnews";
	public static final int INTENT_NEWS_DETAIL_READ_REQUESTCODE = 1010;
	public static final int INTENT_NEWS_DETAIL_READ_RESULTCODE = 2020;
	public static final String INTENT_RAIDERS_NEWS_GAME_NAME = "raiders_news_game_name";
	public static final String INTENT_RAIDERS_NEWS_GAME_ID = "raiders_news_game_id";
	/** 礼包提醒列表页 */
	public static final String INTENT_GIFT_FRAGMENT_CLASS_NAME = "fragmentclassname";
	public static final String INTENT_GIFT_RELETIVE_GAME_ID = "gameId";
	/** 首页跳活动web页 */
	public static final String INTENT_ACTIVITIES_WEB_URL = "url";

	/** 推送开关key **/
	public static final String SETTING_PUSH = "pushSetting";
	public static final String SETTING_PUSH_VIDEO = "pushSettingVideo";
	public static final String SETTING_PUSH_GIFT = "pushSettingGift";
	
	/**获取评论数量*/
	public static final int INTENT_COMMENT_COUNT_REQUESTCODE = 1100;
	public static final int INTENT_COMMENT_COUNT_RESULTCODE = 1101;
	public static final String INTENT_COMMENT_COUNT = "comment_count";
 
	/**跳评论页面传递相关id*/
	public static final String INTENT_COMMENT_RELETIVE_ABSID = "comment_reletive_absid";//除图集意外的id
	public static final String INTENT_COMMENT_RELETIVE_IMAGEID = "comment_reletive_imageid";//图片id
	public static final String INTENT_COMMENT_RELETIVE_SETID = "comment_reletive_setid";//图集id
	public static final String INTENT_COMMENT_RELETIVE_TYPE = "comment_reletive_type";//评论类型
	/***
	 * 评论列表，发表评论，获取评论数量请求相关type
	 */
	public static final int TYPE_NEWS = 0;
	public static final int TYPE_GAME = 1;
	public static final int TYPE_THEMATIC = 2;
	public static final int TYPE_IMAGE_SET = 3;
	public static final String INTENT_SEND_COMMENT_IMAGE_ID = "send_comment_image_id";
	public static final String INTENT_SEND_COMMENT_SID = "send_comment_image_sid";
	public static final String INTENT_SEND_COMMENT_TYPE = "send_comment_type";
	public static final String INTENT_SEND_COMMENT_ABSID = "send_comment_absid";
	public static final int INTENT_SEND_COMMENT_REQUESTCODE = 11201;
	public static final int INTENT_SEND_COMMENT_RESULTCODE = 11202;
	public static final String INTENT_SEND_COMMENT_CONTENT = "send_comment_content";
	public static final String INTENT_SEND_COMMENT_STATE = "send_comment_state";
//	public static final String INTENT_SEND_COMMENT_PARENTID = "send_comment_parendid";
	public static final String INTENT_SEND_COMMENT_MODEL = "send_comment_model";
	/***
	 * 跳积分详情参数
	 */
	public static final String INTENT_TASK_TYPE = "task_detail_type";
	public static final String INTENT_TASK_SIGN = "1001";//签到
	public static final String INTENT_TASK_SHARE = "1002";//分享
	public static final String INTENT_TASK_COMMENT = "1003";//评论
	public static final String INTENT_TASK_ATTENTION = "1004";//关注游戏
	public static final String INTENT_TASK_GETGIFT = "1005";//领取礼包
	public static final String INTENT_TASK_LUNTAN = "1006";//查看论坛、
	public static final String INTENT_TASK_PINGCE = "1007";//查看新闻评测
	public static final String INTENT_TASK_OPEN_APP = "1008";//启动客户端

	
	/**评论详情**/
	public static final String INTENT_COMMENT_DETAIL_MODEL = "comment_detail_model";
	public static final String INTENT_COMMENT_DETAIL_NUM = "comment_detail_num";
	
	/**首页快速导航跳转 //跳转的类型( /0：找游戏/1：新闻评测/2：web)*/
	public static final int TYPE_QUICK_NAV_FIND_GAME = 0;
	public static final int TYPE_QUICK_NAV_NEWS_EVALUATE = 1;
	public static final int TYPE_QUICK_NAV_WEB = 2;
	public static final int TYPE_QUICK_NAV_LUNTAN = 3;
	
	/**返回首页**/
	public static final String INTENT_BACK_HOME_KEY = "backmain";
	public static final String INTENT_BACK_HOME_VALUE = "home";
}
