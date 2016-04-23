package com.sina.sinawidgetdemo.constant;

import com.sina.engine.base.manager.EngineManager;


/**
 * 请求数据常量类
 * @author kangshaozhe
 *
 */
public class RequestConstant {
	/**域名**/
	public static String DOMAIN_NAME = EngineManager.DEBUG?
			"http://10.210.74.210/97973_api/":"http://gameapi.g.sina.com.cn/97973_api/";
	
	public static final String PARTNER_ID_VALUE = "10001";
    public static final String SIGN_KEY_VALUE = "2b3bf29404920195d43fcd665733a3cd";
	
	/** 缓存生存时间 **/
	public static int MEMORY_LIST_LIFETIME = 120;
	public static int MEMORY_DETAIL_LIFETIME = 60;
	public static int MEMORY_DETAIL_EVER = -1;
	
	/** 列表加载每页数量 **/
	public static int listLoadNum = 20;
	/** 评论列表加载每页数量 **/
	public static int listCommentLoadNum = 20;
	
	public static String NEWS_PHPNAME = "newsApi.php";
	public static String RANK_PHPNAME = "rankApi.php";
	public static String GIFT_PHPNAME = "giftApi.php";
	public static String USER_PHPNAME = "userApi.php";
	public static String CONFIG_PHPNAME = "configApi.php";
	/**推送**/
	public static String PUSH_PHPNAME = "pushApi.php";
	/**新闻详情请求变量**/
	public static String NEWS_DETAIL_ACTION = "newsInfo";
	/**游戏列表请求变量**/
	public static String GAME_LIST_ACTION = "rankList";
	public static String GAME_RESULT_ACTION = "gameClassList";
	/**游戏关注请求变量**/
	public static String GAME_ATTEND_ACTION = "bookGame";
	/**游戏详情请求变量**/
	public static String GAME_DETAIL_ACTION = "gameDetail";
	/**礼包列表请求变量**/
	public static String GIFT_DETAIL_ACTION = "cardDetail";
	/**游戏搜索请求变量**/
	public static String SEARCH_GAME_ACTION = "searchGame";
	/**礼包搜索请求变量**/
	public static String SEARCH_GIFT_ACTION = "searchCard";
	public static String SEARCH_RECOMMEND_ACTION = "recommendGames";
	public static String PUSH_SENDTOKEN_ACTION = "sendToken";
	public static String PUSH_UPDATESTATE_ACTION = "updatePushState";
	/**专题列表请求变量**/
	public static String THEMATIC_LIST_ACTION = "specialList";
	/**推荐app请求变量**/
	public static String RECOMMEND_APP_ACTION = "recommendList";
	/**评论个数**/
	public static String COMMENT_COUNT_ACTION = "commentCount";
	/**检测版本**/
	public static String GET_APP_VERSION_ACTION = "getAppVersion";
	/**检测版本**/
	public static String GET_SWITCH_ACTION = "getSwitch";
	
	public static String GIFT_ACTION = "mainList";
	public static String GIFT_RELATED_ACTION = "relateCard";
	public static String GIFT_TIXING_ACTION = "noticeGameList";
	public static String GIFT_YILING_ACTION = "myCard";
	public static String GIFT_FETCH_ACTION = "fetchCard";
	public static String GIFT_FIND_ACTION = "findCard";
	public static String GIFT_ATTENTION_ACTION = "bookGame";
	public static String GIFT_STATIS_ACTION = "statisCard";
	public static String GIFT_DELETE_ACTION = "deleteCard";
	/**配置**/
	public static String GET_CONFIGS_ACTION = "config";
	/**用户**/
	public static String ACCOUNTINFO_ACTION = "userInfo";
	/**任务积分**/
	public static String ADDRECODE_ACTION = "addRecode";
	/**我的游戏请求变量*/
	public static String BOOK_GAME_LIST_ACTION = "bookGameList";
	/**我的游戏删除关注游戏请求变量*/
	public static String CANCEL_BOOK_GAME_ACTION = "cancelBookGame";
	/**攻略新闻列表请求变量*/
	public static String GAME_RAIDERS_ACTION = "gameRaiders";
	/**首页model请求变量*/
	public static String MAIN_LIST_ACTION = "newMainList";
	
	/**专题详情**/
	public static String THEMATIC_DETAIL_ACTION = "specialInfo";
	
	/**应用配置**/
	public static String CONFIG_ACTION = "getAppConfig";
	/**评论列表请求变量*/
	public static String COMMENT_LIST_ACTION = "commentList";
	/**发表评论*/
	public static String ADD_COMMENT_ACTION = "addComment";
	/**评论点赞*/
	public static String ADD_AGREE_ACTION = "addAgree";
	/**点赞**/
	public static String ADD_PRAISE_ACTION = "agreeSpecial";
	
	/**点赞校验**/
	public static String CHECK_PRAISE_ACTION = "checkAgreeSpecial";
	
	/**找游戏*/
	public static String FIND_GAME_LIST_ACTION = "findGameList";
	/**新闻评测*/
	public static String NEWS_EVALUATE_LIST_ACTION = "newsList";
	
	/**收藏**/
	public static String COLLECT_ACTION = "addCollect";
	/**取消收藏**/
	public static String CANCEL_COLLECT_ACTION = "deleteCollect";
	/**收藏校验**/
	public static String COLLECT_STATUS_ACTION = "checkCollect";
	/**获取收藏列表**/
	public static String GET_COLLECT_LIST_ACTION = "collectList";
}
