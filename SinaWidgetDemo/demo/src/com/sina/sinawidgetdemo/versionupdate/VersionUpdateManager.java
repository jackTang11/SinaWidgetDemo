package com.sina.sinawidgetdemo.versionupdate;

import com.sina.engine.base.download.DownLoadConstant;
import com.sina.engine.base.download.DownLoadListener;
import com.sina.engine.base.download.DownloadItem;
import com.sina.engine.base.enums.HttpTypeEnum;
import com.sina.engine.base.enums.ReturnDataClassTypeEnum;
import com.sina.engine.base.manager.EngineManager;
import com.sina.engine.base.request.listener.RequestDataListener;
import com.sina.engine.base.request.model.TaskModel;
import com.sina.engine.base.request.options.RequestOptions;
import com.sina.sinawidgetdemo.constant.RequestConstant;
import com.sina.sinawidgetdemo.custom.view.VersionUpdateDialog;
import com.sina.sinawidgetdemo.request.process.PublicModelUtils;
import com.sina.sinawidgetdemo.request.process.ReuqestDataProcess;
import com.sina.sinawidgetdemo.requestmodel.NoParameRequestModel;
import com.sina.sinawidgetdemo.returnmodel.VersionUpdateModel;
import com.sina.sinawidgetdemo.utils.CommonUtils;
import com.sina.sinawidgetdemo.R;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

public class VersionUpdateManager implements RequestDataListener {
	public Activity myActivity;
	private VersionUpdateModel mVersionUpdateModel;
	private VersionUpdateDialog.Builder mDialog;
	private VersionUpdateListener mVersionUpdateListener;
	private Context mContext;
	private boolean isNew = true;
	private static VersionUpdateManager mInstance;

	public static VersionUpdateManager getInstance(Activity myActivity) {
		if (mInstance == null) {
			synchronized (VersionUpdateManager.class) {
				if (mInstance == null) {
					mInstance = new VersionUpdateManager();
				}
			}
		}
		mInstance.myActivity = myActivity;
		mInstance.mContext = myActivity.getApplicationContext();
		return mInstance;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

//	public VersionUpdateManager(Activity myActivity) {
//		this.myActivity = myActivity;
//		mContext = myActivity.getApplicationContext();
//	}

	public void setVersionUpdateListener(
			VersionUpdateListener versionUpdateListener) {
		this.mVersionUpdateListener = versionUpdateListener;
	}

	public void request() {
		requestData(true);
	}

	public VersionUpdateModel getVersionUpdateModel() {
		return mVersionUpdateModel;
	}

	public void show() {
		if (mVersionUpdateModel == null) {
			return;
		}
		if (myActivity == null || myActivity.isFinishing()) {
			return;
		}
		String confirmStr = myActivity.getResources().getString(
				R.string.version_update_confirm);
		String cancelStr = myActivity.getResources().getString(
				R.string.version_update_cancel);
		String titleStr = myActivity.getResources().getString(
				R.string.version_update_title);

		String info = mVersionUpdateModel.getVersion_info();
		if (TextUtils.isEmpty(info)) {
			info = "";
		}
		String newVersion = mVersionUpdateModel.getVersion();
		mDialog = new VersionUpdateDialog.Builder(myActivity);
		mDialog.setMessage(info);
		mDialog.setTitle(titleStr);
		mDialog.setNewVersion(newVersion);
		mDialog.setPositiveButton(confirmStr,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 点击“确认”后的操作
						if (mVersionUpdateModel != null
								&& !TextUtils.isEmpty(mVersionUpdateModel
										.getUrl())) {
							if (myActivity != null
									&& !myActivity.isFinishing())
								// Utils.goToBrowser(myActivity,
								// versionUpdate.getUrl());
								downLoadApk();
						}
					}
				});

		mDialog.setNegativeButton(cancelStr,
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		mDialog.create().show();
	}

	/**
	 * 请求版本信息
	 */
	private void requestData(boolean isForceRefesh) {

		RequestOptions requestOptions = new RequestOptions()
				.setHttpRequestType(HttpTypeEnum.get).setIsMainThread(true)
				.setIsSaveMemory(true).setIsSaveDb(false)
				.setMemoryLifeTime(RequestConstant.MEMORY_DETAIL_EVER)
				.setReturnDataClassTypeEnum(ReturnDataClassTypeEnum.object)
				.setReturnModelClass(VersionUpdateModel.class);

		NoParameRequestModel requestModel = PublicModelUtils
				.getVerionUpdateRequestModel();
		ReuqestDataProcess.requestData(isForceRefesh, 1, requestModel,
				requestOptions, this, null);
	}

	public interface VersionUpdateListener {
		public void notifyNew();
	}

	private void downLoadApk() {
		String version_code = String.valueOf(mVersionUpdateModel
				.getVersion_id());
		String appName = mContext.getResources().getString(R.string.app_name);
		EngineManager
				.getInstance()
				.getApkDownLoadManager()
				.addMyApkDownLoadTask(mVersionUpdateModel.getUrl(),
						version_code,appName,R.drawable.sina973_icon);
		EngineManager.getInstance().getApkDownLoadManager()
				.setDownLoadListener(new DownLoadListener() {

					@Override
					public void downLoadCallBack(DownloadItem item,
							int downLoadStatus) {
						// TODO Auto-generated method stub
						if (downLoadStatus == DownLoadConstant.DOWNLOAD_STATUS_START) {
							Toast.makeText(
									mContext,
									mContext.getString(R.string.version_start_download),
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sina.engine.base.request.listener.RequestDataListener#resultCallBack
	 * (com.sina.engine.base.request.model.TaskModel)
	 */
	@Override
	public void resultCallBack(TaskModel taskModel) {
		if (myActivity == null || myActivity.isFinishing()) {
			return;
		}
		if (taskModel.getReturnModel() != null) {
			mVersionUpdateModel = (VersionUpdateModel) taskModel
					.getReturnModel();
			// 测试弹出框
			// mVersionUpdateModel.setVersion_id(2);
			if (mVersionUpdateModel.getVersion_id() > CommonUtils
					.getVersionCode(myActivity)) {
				show();
				isNew = false;
			} else {
				if (mVersionUpdateListener != null) {
					mVersionUpdateListener.notifyNew();
				}
				isNew = true;
			}
			// if(result.equals("update")){
			// if(data != null){
			// versionUpdate =data;
			// show();
			// }
			// }
			// else if(result.equals("no")){
			// if(versionUpdateListener != null){
			// versionUpdateListener.notifyNew();
			// }
		}
	}
}
