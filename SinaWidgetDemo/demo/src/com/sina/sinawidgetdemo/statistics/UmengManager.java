package com.sina.sinawidgetdemo.statistics;

import java.util.Map;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.sina.engine.base.manager.EngineManager;
import com.sina.engine.base.request.utils.Utils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * 友盟统计管理器
 * @author kangshaozhe
 *
 */
public class UmengManager {
   public static void init(Context context){
	   Properties configPro = Utils.getConfigProperties(context);
//	   String appid = configPro.getProperty("umeng_appid","");
	   String cid = configPro.getProperty("cid","");
	   String debug = configPro.getProperty("umeng_debug","");
	   //调试模式打开后数据实时发送 不受策略影响
	   MobclickAgent.setDebugMode("true".equals(debug)?true:false);
	   //禁止默认的页面统计
	   MobclickAgent.openActivityDurationTrack(false);
	   //在程序的入口 Activity 中添加
	   MobclickAgent.updateOnlineConfig(context);
	   if(!TextUtils.isEmpty(cid)){
		   AnalyticsConfig.setChannel(cid);
	   }
//	   AnalyticsConfig.setAppkey(appid);用manifest.xml里面的APPKey
	   
   }
   
     public static void onPause(Activity context){
    	 MobclickAgent.onPause(context);
	 }
	 
	 public static void onResume(Activity context){
		 MobclickAgent.onResume(context);
	 }
	 
	 public static void umengEvent(Context context,String id,String lable,Map<String,String> map){
    	 if(EngineManager.DEBUG){
    		 return;
    	 }
    	if(map == null){
    		if(TextUtils.isEmpty(lable)){
    			MobclickAgent.onEvent(context, id);
    		}
    		else {
    			MobclickAgent.onEvent(context, id,lable);
    		}
    	}
    	else {
    		String mId = id;
    		if(!TextUtils.isEmpty(lable)){
    			mId = mId +"_"+ lable;
    		}
    		MobclickAgent.onEvent(context,mId,map);
    	}
     }
	 
}
