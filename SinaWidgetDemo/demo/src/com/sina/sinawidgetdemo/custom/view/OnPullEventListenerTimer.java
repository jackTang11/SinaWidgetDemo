package com.sina.sinawidgetdemo.custom.view;

import android.view.View;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.sina.sinawidgetdemo.utils.TimeUtils;
/**
 * 实现下拉刷新时间的监听接口
 * @author kangshaozhe
 *
 * @param <V>
 */
public class  OnPullEventListenerTimer<V extends View> implements OnPullEventListener<V> {
	private boolean refreshTimeIsValue;
	private long lastRefreshTime;
	private ILoadingLayout iLoadingLayout;
	
	public OnPullEventListenerTimer(ILoadingLayout iLoadingLayout){
		this.iLoadingLayout = iLoadingLayout;
	}
	
	public void flushLastRefreshTime(){
		this.lastRefreshTime = System.currentTimeMillis();
	}
	@Override
	public void onPullEvent(PullToRefreshBase<V> refreshView, State state,
			Mode direction) {
		// TODO Auto-generated method stub
		if(iLoadingLayout == null){
			return;
		}
		switch(direction){
		case PULL_FROM_START:
			if(!refreshTimeIsValue){
				String refreshTime = TimeUtils.getRrefreshTime(lastRefreshTime);
				iLoadingLayout.setLastUpdatedLabel("最后更新时间:"+refreshTime);
				refreshTimeIsValue = true;
			}
			switch(state){
			case RESET:
				refreshTimeIsValue = false;
			break;
			 default:
				break;
			  
			}
		break;
		case PULL_FROM_END:
			iLoadingLayout.setLastUpdatedLabel("");
		break;
		default:
			break;
	}
	}

}
