package com.sina.sinawidgetdemo.statistics;

import java.util.Map;

import com.sina.engine.base.manager.EngineManager;
import com.tendcloud.tenddata.TCAgent;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

/**
 * 统计管理器
 * @author kangshaozhe
 *
 */
public class StatisticsManager {
	public static void init(Context context){
		TalkingDataManager.init(context);
		UmengManager.init(context);
	}
	
	 public static void onPause(Activity context){
		 TalkingDataManager.onPause(context);
		 UmengManager.onPause(context);
	 }
	 
	 public static void onResume(Activity context){
		 TalkingDataManager.onResume(context);
		 UmengManager.onResume(context);
	 }
	 
	 public static void sendEvent(Context context,String id,String lable,Map<String,String> map){
		 TalkingDataManager.TDEvent(context, id, lable, map);
		 UmengManager.umengEvent(context, id, lable, map);
     }
     
}
