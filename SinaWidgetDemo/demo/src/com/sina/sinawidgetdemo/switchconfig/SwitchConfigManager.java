/**
 * @author yulong
 *
 *  
 */
package com.sina.sinawidgetdemo.switchconfig;

import java.util.Properties;

import android.content.Context;
import android.text.TextUtils;

import com.sina.engine.base.enums.HttpTypeEnum;
import com.sina.engine.base.enums.ReturnDataClassTypeEnum;
import com.sina.engine.base.request.listener.RequestDataListener;
import com.sina.engine.base.request.model.TaskModel;
import com.sina.engine.base.request.options.RequestOptions;
import com.sina.engine.base.request.utils.Utils;
import com.sina.sinawidgetdemo.constant.PreferencesConstant;
import com.sina.sinawidgetdemo.constant.RequestConstant;
import com.sina.sinawidgetdemo.request.process.ReuqestDataProcess;
import com.sina.sinawidgetdemo.requestmodel.NoParameRequestModel;
import com.sina.sinawidgetdemo.returnmodel.SwitchConfigModel;
import com.sina.sinawidgetdemo.utils.PreferencesUtils;

/**
 * @author yulong
 * 
 */
public class SwitchConfigManager {
	public static final int SWITCH_OPEN = 1;
	public static final int SWITCH_CLOSE = 0;
	
	/**
	 * 请求审核开关配置
	 */
	public static void requestSwitchConfig(final Context context) {
		RequestOptions requestOptions = new RequestOptions()
				.setHttpRequestType(HttpTypeEnum.get).setIsMainThread(true)
				.setIsSaveMemory(true).setIsSaveDb(false)
				.setMemoryLifeTime(RequestConstant.MEMORY_DETAIL_EVER)
				.setReturnDataClassTypeEnum(ReturnDataClassTypeEnum.object)
				.setReturnModelClass(SwitchConfigModel.class);

		NoParameRequestModel requestModel = getSwitchRequestModel();
		ReuqestDataProcess.requestData(true, 1, requestModel, requestOptions,
				new RequestDataListener() {

					@Override
					public void resultCallBack(TaskModel taskModel) {
						if (taskModel.getReturnModel() != null) {
							SwitchConfigModel model = (SwitchConfigModel) taskModel
									.getReturnModel();
							saveSwitchConfig(model, context);
						}
					}
				}, null);
	}

	/**
	 * 保存开关配置信息
	 * 
	 * @param model
	 */
	public static void saveSwitchConfig(SwitchConfigModel model,
			Context context) {
		if (model != null) {
			PreferencesUtils.writeInt(context,
					PreferencesConstant.SWITCH_GIFT_SHOW,
					PreferencesConstant.SWITCH_GIFT_SHOW,
					model.getGift_show_tag());
			PreferencesUtils.writeInt(context,
					PreferencesConstant.SWITCH_APP_RECOMMEND,
					PreferencesConstant.SWITCH_APP_RECOMMEND,
					model.getGift_show_tag());
			PreferencesUtils.writeInt(context,
					PreferencesConstant.SWITCH_DOWNLOAD_BUTTON,
					PreferencesConstant.SWITCH_DOWNLOAD_BUTTON,
					model.getGift_show_tag());
		}
		// testSwitchConfig();
	}

	/**
	 * 获取开关配置信息
	 */
	public static SwitchConfigModel getSwitchConfigModel(Context context) {
		SwitchConfigModel mSwitchModel = ReuqestDataProcess
				.getMemoryData(getSwitchRequestModel());
		if (mSwitchModel == null) {
			mSwitchModel = new SwitchConfigModel();
			int giftswitch = PreferencesUtils.getInt(
					context.getApplicationContext(),
					PreferencesConstant.SWITCH_GIFT_SHOW,
					PreferencesConstant.SWITCH_GIFT_SHOW, SWITCH_CLOSE);
			int appswitch = PreferencesUtils.getInt(
					context.getApplicationContext(),
					PreferencesConstant.SWITCH_APP_RECOMMEND,
					PreferencesConstant.SWITCH_APP_RECOMMEND, SWITCH_CLOSE);
			int downloadswitch = PreferencesUtils.getInt(
					context.getApplicationContext(),
					PreferencesConstant.SWITCH_DOWNLOAD_BUTTON,
					PreferencesConstant.SWITCH_DOWNLOAD_BUTTON, SWITCH_CLOSE);
			mSwitchModel.setApp_recommend(appswitch);
			mSwitchModel.setDownload_button(downloadswitch);
			mSwitchModel.setGift_show_tag(giftswitch);
		}
		if(!isSwitchChannel(context)){
			mSwitchModel.setApp_recommend(SWITCH_OPEN);
			mSwitchModel.setDownload_button(SWITCH_OPEN);
			mSwitchModel.setGift_show_tag(SWITCH_OPEN);
		}
		return mSwitchModel;
	}
	
	/**
	 * 改渠道是否需要开关
	 * @param context
	 * @return
	 */
	private static boolean isSwitchChannel(Context context){
		try{
			Properties switchChannelPro = Utils.getSwitchChannelProperties(context);
			String channel_id = switchChannelPro.getProperty("channelid","");
			
			Properties configPro = Utils.getConfigProperties(context);
			String cid = configPro.getProperty("cid","");
			if(!TextUtils.isEmpty(channel_id)){
				String[] channel_ids = channel_id.split(",");
				if(channel_ids != null){
					for(String id:channel_ids){
						if(id.equalsIgnoreCase(cid)){
							return true;
						}
					}
				}
			}
		}
		catch(Exception e){
			return true;
		}
		return false;
	}

	/**
	 * 创建版本信息请求模型
	 */
	private static NoParameRequestModel getSwitchRequestModel() {
		NoParameRequestModel requestModel = new NoParameRequestModel(
				RequestConstant.DOMAIN_NAME, RequestConstant.CONFIG_PHPNAME);
		requestModel.setAction(RequestConstant.GET_SWITCH_ACTION);
		return requestModel;
	}

}
