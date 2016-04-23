package com.sina.sinawidgetdemo.statistics;

import java.util.Map;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.sina.engine.base.manager.EngineManager;
import com.sina.engine.base.request.utils.Utils;
import com.tendcloud.tenddata.TCAgent;

public class TalkingDataManager {
	 public static void init(Context context){
		Properties configPro = Utils.getConfigProperties(context);
		String appid = configPro.getProperty("td_appid","");
		String cid = configPro.getProperty("cid","");
		if(TextUtils.isEmpty(appid)||TextUtils.isEmpty(cid)){
			TCAgent.init(context);
		}
		else {
			TCAgent.init(context,appid,cid);
		}
		TCAgent.setReportUncaughtExceptions(true);
	 }
	 
	 public static void onPause(Activity context){
		 TCAgent.onPause(context);
	 }
	 
	 public static void onResume(Activity context){
		 TCAgent.onResume(context);
	 }
	 
     public static void TDEvent(Context context,String id,String lable,Map<String,String> map){
    	 if(EngineManager.DEBUG){
    		 return;
    	 }
    	if(map == null){
    		if(TextUtils.isEmpty(lable)){
    			TCAgent.onEvent(context, id);
    		}
    		else {
    			TCAgent.onEvent(context, id,lable);
    		}
    	}
    	else {
    		String mLable = lable;
    		if(TextUtils.isEmpty(mLable)){
    			mLable = id;
    		}
		   TCAgent.onEvent(context, id,mLable,map);
    	}
     }
     
     public static String getDeviceId(Context context){
    	 return TCAgent.getDeviceId(context);
     }
}
