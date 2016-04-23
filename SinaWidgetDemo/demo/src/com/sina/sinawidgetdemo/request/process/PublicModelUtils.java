/**
 * @author yulong
 *
 *  
 */
package com.sina.sinawidgetdemo.request.process;

import com.sina.sinawidgetdemo.constant.RequestConstant;
import com.sina.sinawidgetdemo.requestmodel.ConfigRequestModel;
import com.sina.sinawidgetdemo.requestmodel.NoParameRequestModel;

/**
 * @author  yulong
 *
 */
public class PublicModelUtils {
	
	/**
	 * 创建配置信息请求模型
	 * @return
	 */
	public static ConfigRequestModel getConfigtRequestModel(){
		String requestDomainName = RequestConstant.DOMAIN_NAME;
		String requestPhpName = RequestConstant.CONFIG_PHPNAME;
		String requestAction = RequestConstant.CONFIG_ACTION;
//		requestDomainName = "file:///android_asset/credit_test/creditConfig.txt";
//		requestPhpName = "";
//		requestAction = null;
		ConfigRequestModel requestModel = new ConfigRequestModel(requestDomainName, requestPhpName);
		requestModel.setAction(requestAction);
		return requestModel;
	}
	
	/**
	 *  创建版本信息请求模型
	 */
	public static NoParameRequestModel getVerionUpdateRequestModel(){
		NoParameRequestModel requestModel = new NoParameRequestModel(
				RequestConstant.DOMAIN_NAME, RequestConstant.CONFIG_PHPNAME);
		requestModel.setAction(RequestConstant.GET_APP_VERSION_ACTION);
		return requestModel;
	}
}
