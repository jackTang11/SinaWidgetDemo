package com.sina.sinawidgetdemo.request.process;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.db4o.query.Predicate;
import com.sina.engine.base.db4o.Db4oImpl;
import com.sina.engine.base.enums.HttpTypeEnum;
import com.sina.engine.base.enums.ReturnDataClassTypeEnum;
import com.sina.engine.base.manager.EngineManager;
import com.sina.engine.base.request.interfaces.DbLogicInterface;
import com.sina.engine.base.request.listener.RequestDataListener;
import com.sina.engine.base.request.manager.MemoryTaskManager;
import com.sina.engine.base.request.model.TaskModel;
import com.sina.engine.base.request.options.RequestOptions;
import com.sina.engine.base.request.utils.DebugHttpUtils;
import com.sina.engine.base.request.utils.Utils;
import com.sina.sinawidgetdemo.constant.DBConstant;
import com.sina.sinawidgetdemo.constant.RequestConstant;
import com.sina.sinawidgetdemo.requestmodel.ConfigRequestModel;
import com.sina.sinawidgetdemo.returnmodel.ConfigModel;

/**
 * 具体业务获取数据流程
 * @author kangshaozhe
 *
 */
public class RequestConfigProcess {
	
	/**
	 * 启动请求config数据流程
	 * @param requestDataListener
	 */
   public static void startrRequestConfig(RequestDataListener requestDataListener){
	   ConfigRequestModel configRequestModel = PublicModelUtils.getConfigtRequestModel();
	   RequestOptions requestOptions = new RequestOptions().setHttpRequestType(HttpTypeEnum.get)
				.setIsMainThread(true)
				.setIsSaveMemory(true)
				.setIsSaveDb(true)
				.setMemoryLifeTime(RequestConstant.MEMORY_DETAIL_EVER)
				.setReturnDataClassTypeEnum(ReturnDataClassTypeEnum.object)
				.setReturnModelClass(ConfigModel.class);
	   DbLogicInterface dbLogicObserver = new DbLogicInterface() {

			@Override
			public void saveDb(TaskModel taskModel) {
				// TODO Auto-generated method stub
				//先删除
				Db4oImpl deleteImpl = new Db4oImpl(DBConstant.CONFIG_NAME);
				deleteImpl.deleteDbFile();
				
				//再存储
				saveConfigData(taskModel.getReturnModel());
			}
	
			@Override
			public void getDb(TaskModel taskModel) {
				// TODO Auto-generated method stub
			}
		
	   };
	   ReuqestDataProcess.requestData(true, 2,configRequestModel,requestOptions, requestDataListener,dbLogicObserver);
   }
   /**
    * 其他类用到的地方获取config数据
    * @param requestDataListener
    * @return
    */
   public static ConfigModel getConfigData(){
	   ConfigModel returnModel = null;
	   try{
		   ConfigRequestModel configRequestModel = PublicModelUtils.getConfigtRequestModel();
		   String mapKey = Utils.creatMapKey(configRequestModel);
		   
		   MemoryTaskManager memoryTaskManager = 
					EngineManager.getInstance().getRequestDataTaskManager().memoryTaskManager;
		    if(memoryTaskManager.getMemoryData(mapKey) != null){//如果缓存有数据
		    	returnModel = (ConfigModel)memoryTaskManager.getMemoryData(mapKey).getMemoryModel();
		    }
	   }
	   catch(Exception e){
		   
	   }
	   try{
		    if(returnModel == null) {
		    	//获取数据库数据
		    	returnModel = getDbConfigModel();
		    }
	   }
	   catch(Exception e){
		   
	   }
	   try{
		    if(returnModel == null) {
		    	//如果数据库获取失败 获取默认数据
		    	returnModel = getLocalConfigData();
		    }
		   
	   }
	   catch(Exception e){
		   
	   }
	   
	   return returnModel;
   }
   
   /**
    * 
    */
   public static void saveConfigData(Object model){
	   try{
		    ConfigModel saveModel = (ConfigModel)model;
			Db4oImpl saveImpl = new Db4oImpl(DBConstant.CONFIG_NAME).open();
			
			saveImpl.save(saveModel, new Predicate<ConfigModel>() {
				private static final long serialVersionUID = 1L;
	
				@Override
				public boolean match(ConfigModel model) {
					return false;
				}
			}, ConfigModel.class.getName());
			saveImpl.close();
		   }
	   catch (Exception e){
		   
	   }
   }
   
   public static ConfigModel getDbConfigModel(){
	   ConfigModel dbModel = null;
	   try{
			//获取数据库数据
			Db4oImpl getImpl = new Db4oImpl(DBConstant.CONFIG_NAME).open();
			List<ConfigModel> getList = getImpl.getListNoComparator(new Predicate<ConfigModel>() {
				private static final long serialVersionUID = 1L;
	
				@Override
				public boolean match(ConfigModel model) {
					
					return true;
				}
			});
			if(getList.size() > 0){
				dbModel = getList.get(0);		
			}
			getImpl.close();
	    }
	   catch (Exception e){
		   
	   }
		return dbModel;
   }
   
   /**
    * 获取本地config初始数据
    * @return
    */
   public static ConfigModel getLocalConfigData(){
	   ConfigModel localModel = null;
	   String jsonStr = DebugHttpUtils.getHttpJsonStr("file:///android_asset/config_loacl.txt");
	   try {
			JSONObject js = new JSONObject(jsonStr);
			if(!js.isNull("data")){
				JSONObject dataObject= js.getJSONObject("data");
				localModel = JSON.parseObject(dataObject.toString(), ConfigModel.class);
			}
	   } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   }
	   
	   return localModel;
   }
}
