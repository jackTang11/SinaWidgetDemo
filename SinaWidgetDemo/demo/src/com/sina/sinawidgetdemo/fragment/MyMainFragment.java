package com.sina.sinawidgetdemo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sina.sinawidgetdemo.constant.StatisticsConstant;
import com.sina.sinawidgetdemo.returnmodel.SwitchConfigModel;
import com.sina.sinawidgetdemo.sharesdk.AccountInfoManager;
import com.sina.sinawidgetdemo.sharesdk.SyncReason;
import com.sina.sinawidgetdemo.statistics.StatisticsManager;
import com.sina.sinawidgetdemo.switchconfig.SwitchConfigManager;
import com.sina.sinawidgetdemo.R;

/**
 * 业务主页面
 * 
 * @author kangshaozhe
 * 
 */
public class MyMainFragment extends BaseFragment implements OnClickListener{

	 private HomeFragment homeFragment;
	 private AdvanceWListFragment giftFragment;
	 private MoreFragment moreFragment;
	 private TextView homeTagView,giftTagView,moreTagView;
	 public int clickId;
	 private View giftLayout;
	/**
	 * 当fragment和activity关联之后，调用这个方法
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initFragment();
	}
	
	/**
	 * 创建fragment中的视图的时候，调用这个方法。
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.my_main_fragment, container,
				false);
		intView(mView);
		return mView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * 当activity的onCreate()方法被返回之后，调用这个方法。
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	/**
	 * 当fragment中的视图被移除的时候，调用这个方法。
	 */

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	/**
	 * 当fragment和activity分离的时候，调用这个方法。
	 */
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		AccountInfoManager.getInstance().requestCurrentAccountInfoForReason(
				SyncReason.TOTAL_SCORE_AND_USER_TASKS);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/**
	 * 跳转逻辑
	 */
	public void intView(View view) {
		homeTagView = (TextView)view.findViewById(R.id.main_fragment_title_home);
		homeTagView.setOnClickListener(this);
		giftTagView = (TextView)view.findViewById(R.id.main_fragment_title_gift);
		giftTagView.setOnClickListener(this);
		moreTagView = (TextView)view.findViewById(R.id.main_fragment_title_more);
		moreTagView.setOnClickListener(this);
		giftLayout = view.findViewById(R.id.main_fragment_title_gift_layout);
		clickChangeColor();
		
		SwitchConfigModel model = SwitchConfigManager.getSwitchConfigModel(myActivity);
		if(model.getGift_show_tag()==SwitchConfigManager.SWITCH_CLOSE){
			giftLayout.setVisibility(View.GONE);
		}
	}

	/**
	 * 初始化fragment
	 */
	private void initFragment(){
		
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		homeFragment = new HomeFragment();
		giftFragment = new AdvanceWListFragment();
		moreFragment = new MoreFragment();
		fragTransaction.add(R.id.my_main_fragment_content, homeFragment);
	    fragTransaction.commitAllowingStateLoss();
	    clickId = R.id.main_fragment_title_home;

	}
	
	/**
	 * 点击切换中间的fragment
	 * @param id
	 */
	public void clickReplaceFragment(int id){
		if(clickId == id){
			return;
		}
		clickId = id;
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction fragTransaction = fm.beginTransaction();
		switch(id){
		   case R.id.main_fragment_title_home:
			   if(homeFragment == null){
				   homeFragment = new HomeFragment();
			   }
			   fragTransaction.replace(R.id.my_main_fragment_content, homeFragment);
			   StatisticsManager.sendEvent(myActivity.getApplicationContext(),
						StatisticsConstant.HOME_TAB, "", null);
		   break;
		   case R.id.main_fragment_title_gift:
			   if (giftFragment == null) {
				   giftFragment = new AdvanceWListFragment();
			   }
			   fragTransaction.replace(R.id.my_main_fragment_content, giftFragment);
			   StatisticsManager.sendEvent(myActivity.getApplicationContext(),
						StatisticsConstant.GIFT_TAB, "", null);
		   break;
		   case R.id.main_fragment_title_more:
			if (moreFragment == null) {
				moreFragment = new MoreFragment();
			}
			fragTransaction.replace(R.id.my_main_fragment_content, moreFragment);
			StatisticsManager.sendEvent(myActivity.getApplicationContext(),
						StatisticsConstant.MORE_TAB, "", null);
		   break;
		  	   
		   
		}
		
		fragTransaction.commitAllowingStateLoss();
		
		clickChangeColor();
	}
	
	private void clickChangeColor(){
		homeTagView.setTextColor(myActivity.getResources().getColor(R.color.main_tab_title_unselect));
		giftTagView.setTextColor(myActivity.getResources().getColor(R.color.main_tab_title_unselect));
		moreTagView.setTextColor(myActivity.getResources().getColor(R.color.main_tab_title_unselect));
		homeTagView.setBackgroundColor(myActivity.getResources().getColor(R.color.transparent));
		giftTagView.setBackgroundColor(myActivity.getResources().getColor(R.color.transparent));
		moreTagView.setBackgroundColor(myActivity.getResources().getColor(R.color.transparent));
		
		Drawable homeDrawable= getTextDrawable(R.drawable.tab_home);    
		homeTagView.setCompoundDrawables(null,homeDrawable,null,null);  
		
		Drawable giftDrawable= getTextDrawable(R.drawable.tab_gift);    
		giftTagView.setCompoundDrawables(null,giftDrawable,null,null);
		
		Drawable moreDrawable= getTextDrawable(R.drawable.tab_more);    
		moreTagView.setCompoundDrawables(null,moreDrawable,null,null);
		
		
		switch(clickId){
		   case R.id.main_fragment_title_home:
			   homeTagView.setTextColor(myActivity.getResources().getColor(R.color.main_tab_title_select));
			   homeTagView.setBackgroundColor(myActivity.getResources().getColor(R.color.main_tab_select_back));
			   Drawable homeDrawable_down= getTextDrawable(R.drawable.tab_home_down);    
			   homeTagView.setCompoundDrawables(null,homeDrawable_down,null,null); 
		   break;
		   case R.id.main_fragment_title_gift:
			   giftTagView.setTextColor(myActivity.getResources().getColor(R.color.main_tab_title_select));
			   giftTagView.setBackgroundColor(myActivity.getResources().getColor(R.color.main_tab_select_back));
			   Drawable giftDrawable_down= getTextDrawable(R.drawable.tab_gift_down_m);    
			   giftTagView.setCompoundDrawables(null,giftDrawable_down,null,null);
		   break;
		   case R.id.main_fragment_title_more:
			   moreTagView.setTextColor(myActivity.getResources().getColor(R.color.main_tab_title_select));
			   moreTagView.setBackgroundColor(myActivity.getResources().getColor(R.color.main_tab_select_back));
			   Drawable moreDrawable_down= getTextDrawable(R.drawable.tab_more_down);    
			   moreTagView.setCompoundDrawables(null,moreDrawable_down,null,null);
		   break;
		   
		}
	}

	

	private Drawable getTextDrawable(int drawableId) {
		// TODO Auto-generated method stub
		Drawable drawable= getResources().getDrawable(drawableId);    
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		return drawable;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id = view.getId();
		clickReplaceFragment(id);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(homeFragment!=null){
			homeFragment.onActivityResult(requestCode, resultCode, data);
		}
		
	}
	
	
}
