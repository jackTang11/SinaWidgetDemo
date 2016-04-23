package com.sina.sinawidgetdemo.constant;
/**
 * 统计相关常量
 * @author kangshaozhe
 *
 */
public class StatisticsConstant {
	
	/**首页**/
	//启动App
	public static String STARTAPP= "start_app";
	
	//切换到首页
	public static String HOME_TAB = "home_tab";
	//切换到礼包大厅
	public static String GIFT_TAB = "gift_tab";
	//切换到更多（我的页）
	public static String MORE_TAB = "more_tab";
	
	//首页反馈点击（设置中的吐槽）
	public static String HOME_FACEBACK_CLICK = "home_faceback_click";
//	//首页签到点击
//	public static String HOME_SIGN_CLICK = "home_sign_click";
//	//首页未登录签到点击
//	public static String HOME_SIGN_UNLOGIN_CLICK = "home_sign_unlogin_click";
	
	//首页焦点图点击
	public static String HOME_FOCUS_CLICK = "home_focus_click";
	//首页焦点图点击上传数据id
	public static String HOME_FOCUS_CLICK_ID = "focus_id";
	//首页焦点图点击上传数据标题
	public static String HOME_FOCUS_CLICK_TITLE = "focus_title";
	
	//首页赚韭菜花点击 (首页任务按钮)
	public static String HOME_JIUCAIHUA_CLICK = "home_jiucaihua_click";
	//首页我的游戏点击（更改到我的页面中）
	public static String HOME_MYGAME_CLICK = "home_mygame_click";
	//首页未登录下我的游戏点击
	public static String HOME_MYGAME_UNLOGIN_CLICK = "home_mygame_unlogin_click";
	
//	//首页专题焦点图点击
//	public static String HOME_THEMATIC_FOCUS_CLICK = "home_thematic_focus_click";
//	//首页专题焦点图点击上传数据id
//	public static String HOME_THEMATIC_FOCUS_CLICK_ID = "focus_id";
//	//首页专题焦点图点击上传数据标题
//	public static String HOME_THEMATIC_FOCUS_CLICK_TITLE = "focus_title";
	
//	//首页专题更多点击
//	public static String HOME_THEMATIC_MORE_CLICK = "home_thematic_more_click";
	
	//首页特色合集点击
	public static String HOME_FEATURE_CLICK = "home_feature_click";
	//首页分类排行点击
	public static String HOME_CLASSIFY_CLICK = "home_classify_click";
	
//	//首页特色合集展开
//	public static String HOME_FEATURE_OPEN = "home_feature_open";
//	//首页分类排行展开
//	public static String HOME_CLASSIFY_OPEN = "home_classify_open";
	
	/**更多**/
	//更多登陆点击
	public static String MORE_LOGIN_CLICK = "more_login_click";
	//更多应用推荐点击
	public static String MORE_RECOMMEND_CLICK = "more_recommend_click";
	//更多论坛点击
	public static String MORE_BBS_CLICK = "more_bbs_click";
	//更多设置中心点击
	public static String MORE_SETTING_CLICK = "more_setting_click";
	//更多版本查看点击
	public static String MORE_VERSION_CLICK = "more_version_click";
	
	/**搜索**/
	//搜索大家都在搜
	public static String SEARCH_EVERYONE_CLICK = "search_everyone_click";
	//搜索大家都在搜上传数据名称
	public static String SEARCH_EVERYONE_CLICK_NAME = "name";
	
	//搜索按钮点击
	public static String SEARCH_BUTTON_CLICK = "search_button_click";
	
	//搜索索引点击
	public static String SEARCH_INDEX_CLICK = "search_index_click";
	
	//搜索游戏为空
	public static String SEARCH_EMPTY_GAME = "search_empty_game";
	//搜索礼包为空
	public static String SEARCH_EMPTY_GIFT = "search_empty_gift";
	//搜索为空的字段名称
	public static String SEARCH_EMPTY_NAME = "name";
	
	
	/**攻略列表**/
	//攻略列表来自游戏详情
	public static String NEWSLIST_FROM_GAMEDETAIL = "newslist_from_gamedetail";
	//攻略列表来自我的游戏
	public static String NEWSLIST_FROM_MYAGAME = "newslist_from_mygame";
	
	/**获取韭菜花弹窗**/
	
	public static String GET_JIUCAIHUA_POP_SHOW = "get_jiucaihua_pop_show";
	//获取韭菜花弹窗的类型
	public static String GET_JIUCAIHUA_POP_COMMENT = "comment";//评论
//	public static String GET_JIUCAIHUA_POP_SIGN = "sign";//签到
//	public static String GET_JIUCAIHUA_POP_ATTENTION = "attention";//关注游戏
	public static String GET_JIUCAIHUA_POP_SHARE = "share";//分享
//	public static String GET_JIUCAIHUA_POP_GETGIFT = "get_gift";//领取礼包
	
	/**我的游戏**/
	public static String MYGAME_ADDGAME_CLICK = "game_AddClick";//我的游戏列表  点击添加游戏
	public static String MYGAME_MANAGER_CLICK = "game_ManagerClick";//我的游戏列表  点击管理
	
	/**游戏详情**/
	public static String GAMEDETAIL_FROM = "game_DetailFrom";//显示游戏 详情来自于搜索
	public static String GAMEDETAIL_FROM_SEARCH = "search";//显示游戏 详情来自于搜索
	public static String GAMEDETAIL_FROM_LIST = "list";//显示游戏 详情来自于列表
	public static String GAMEDETAIL_FROM_HOME_FOCUS = "home_focus";//显示游戏 详情来自于首页推荐焦点
	public static String GAMEDETAIL_FROM_HOME_PUSH = "push";//显示游戏 详情来自于系统推荐
	public static String GAMEDETAIL_FROM_THEMATIC_DETAIL= "thematic_detail";//显示游戏 详情来自于专题详情
//	public static String GAMEDETAIL_FROM_OTHER = "other";//显示游戏 详情来自于其他
	
	
	
	/**礼包详情**/
	public static String kAppAnalyticsEvent_Gift_ShowDetail = "gift_DetailFrom";//显示礼包详情

	public static String kAppAnalyticsEventLabel_Gift_ShowDetailFromSearch = "search";//显示礼包详情来自于搜索
	public static String kAppAnalyticsEventLabel_Gift_ShowDetailFromRemind  = "remind";//显示礼包详情来自于提醒列表
	public static String kAppAnalyticsEventLabel_Gift_ShowDetailFromHomeAds  = "homeFocus";//显示礼包详情来自于首页推荐焦点
	public static String kAppAnalyticsEventLabel_Gift_ShowDetailFromSystemRec = "push";//显示礼包详情来自于系统推荐
	public static String kAppAnalyticsEventLabel_Gift_ShowDetailFromHomeList  = "homeList";//显示礼包详情来自于礼包大厅
	public static String kAppAnalyticsEventLabel_Gift_ShowDetailFromOther  = "other";//显示礼包详情来自于其他
	
	public static String kAppAnalyticsEvent_Gift_ShowDetailFetch = "gift_FetchShow";//显示领号类型的礼包详情
	public static String kAppAnalyticsEvent_Gift_ShowDetailFind  = "gift_FindShow";//显示淘号类型的礼包详情
	public static String kAppAnalyticsEvent_Gift_ShowDetailBook  = "gift_BookShow";//显示预定类型的礼包详情
	
	/**礼包大厅**/
	public static String kAppAnalyticsEvent_Gift_ClickSearch = "gift_SearchClick";//点击礼包大厅搜索
	public static String kAppAnalyticsEvent_Gift_ClickFetchedList= "gift_FetchedListClick";//点击礼包大厅已领礼包列表入口
	public static String kAppAnalyticsEvent_Gift_ClickFetchedList_Unlogin = "gift_FetchedList_UnloginClick";//点击礼包大厅已领礼包列表入口 未登陆情况下
	public static String kAppAnalyticsEvent_Gift_ClickRemindList = "gift_RemindListClick";//点击礼包大厅礼包提醒列表入口
	public static String kAppAnalyticsEvent_Gift_ClickRemindList_Unlogin = "gift_RemindList_UnloginClick";//点击礼包大厅礼包提醒列表入口 未登陆情况下

	public static String kAppAnalyticsEvent_Gift_ClickHotData = "gift_HotDataClick";//点击礼包大厅热门礼包
	public static String kAppAnalyticsEventLabel_GiftHotData_Id  = "gift_id";//点击礼包大厅热门礼包  礼包id
	public static String kAppAnalyticsEventLabel_GiftHotData_Title = "gift_title";//点击礼包大厅热门礼包  礼包名称
	
	public static String kAppAnalyticsEvent_Gift_ClickFetchActionBtn = "gift_FetchClick";//在详情触发领号按钮
	public static String kAppAnalyticsEvent_Gift_ClickFindActionBtn = "gift_FindClick";//在详情触发淘号按钮
	public static String kAppAnalyticsEvent_Gift_ClickBookActionBtn = "gift_BookClick";//在详情触发预定按钮
	public static String kAppAnalyticsEvent_Gift_ShowPop_Fetch_F_Getter = "gift_GetterFetchPopShow";//在已领礼包列表弹窗领号结果
	public static String kAppAnalyticsEvent_Gift_ShowPop_Fetch_F_Detail = "gift_DetailFetchPopShow";//在详情弹窗领号结果
	public static String kAppAnalyticsEvent_Gift_ShowPop_SeeMore = "gift_MorePopShow";//在弹窗中显示兑换详情
	public static String kAppAnalyticsEvent_Gift_ShowPop_Find = "gift_FindPopShow";//在详情弹窗淘号结果
	public static String kAppAnalyticsEvent_Gift_ShowPop_ChangeAgain = "gift_PopShowAgain";//在详情弹窗淘号弹窗中触发换一组

	
	/**1.1.0版本新增加和改变的事件**/
	
	//**做任务赚取积分（韭菜花）弹窗  新增加三个类型的弹窗 去掉关注游戏、领礼包和签到
	public static String GET_JIUCAIHUA_POP_BBS = "bbs";//查看论坛
	public static String GET_JIUCAIHUA_POP_EVALUATE= "evaluate";//新闻评测
	public static String GET_JIUCAIHUA_POP_STARTAPP= "startApp";//启动应用
	
	//新闻资讯详情 展示事件
	public static String NEWS_DETAIL_SHOW = "news_DetailShow";
	//新闻资讯详情上传数据id
	public static String NEWS_DETAIL_SHOW_ID = "id";
	
	//礼包详情上传数据id
	public static String GIFT_DETAIL_SHOW_ID = "id";
	
	//游戏详情进入 去掉关注游戏列表进入(之前版本是放到 "other"子事件中的 所以实际去掉的是 "other"事件)
	
	//首页 活动和找游戏点击  论坛点击用上个版本设置里面的论坛点击事件
	public static String HOME_ACTIVITY_CLICK = "home_activity_click";//首页活动
	public static String HOME_FINDGAME_CLICK = "home_FindGame_click";//首页找游戏
	
	//进入新闻资讯详情的几个入口点击事件命名规则（头条除外） 前缀名称_id_title  
	//头条命名规则：前缀名称_头条后缀
	public static String INDETAIL_CLICK = "inNewsDetail";
	//头条后缀
	public static String INDETAIL_TOPNEWS = "头条";
	//进入新闻资讯详情的几个入口点击事件上传数据id
	public static String INDETAIL_CLICK_ID = "id";
	
	
}
