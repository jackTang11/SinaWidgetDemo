package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sina.sinawidgetdemo.custom.viewpagerindicator.IconPageIndicator;
import com.sina.sinawidgetdemo.custom.viewpagerindicator.IconPagerAdapter;
import com.sina.sinawidgetdemo.utils.DeviceUtils;
import com.sina.sinawidgetdemo.utils.ViewUtils;
import com.sina.sinawidgetdemo.R;

/**
 * 引导页
 * 
 * @author kangshaozhe
 * 
 */
public class GuideFragment extends BaseFragment implements OnClickListener{
	
	private GuideOnClickListener guideOnClickListener;
	private ViewPager guidePager;
	private ViewPagerAdapter myAdapter;
	private List<View> views = new ArrayList<View>();
	private int[] viewResouseId = new int[]{R.layout.guide_1,R.layout.guide_2,R.layout.guide_3};
//	private int[] viewResouseId = null;
	private int pagerCount;
	private TextView exitButton;
	
	private IconPageIndicator guideIndicator;
	private List<Integer> focusad_icon_ids = new ArrayList<Integer>();
	
	public boolean isPass(){
		if(viewResouseId == null || viewResouseId.length == 0){
			return true;
		}
		return false;
	}

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
		initData();
		
	}
	
	
	public GuideFragment setGuideOnClickListener(GuideOnClickListener guideOnClickListener){
		this.guideOnClickListener = guideOnClickListener;
		return this;
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
		mView = inflater.inflate(R.layout.guide_fragment, container,
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
		// TODO Auto-generated method stub
		super.onResume();
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
	 * 初始化数据
	 */
	private void initData(){
		creatPager();
	}
	
	
	/**
	 * 初始化view
	 * @param view
	 */
	private void intView(View view) {
		guidePager = (ViewPager)view.findViewById(R.id.guide_viewpager);
		
		myAdapter = new ViewPagerAdapter(views);
		
		guidePager.setAdapter(myAdapter);
		
		guideIndicator = (IconPageIndicator) view
				.findViewById(R.id.guide_indicator);
		int screen_w = 0;
		try{
		  screen_w = DeviceUtils.getScreenWH(getActivity())[0];
		}
		catch(Exception e){
			
		}
		
		if(screen_w < 700){
			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) guideIndicator.getLayoutParams();
			lp.bottomMargin=ViewUtils.dp2px(getActivity(), 30);
			guideIndicator.setLayoutParams(lp);
		}
			
		guideIndicator.setOnPageChangeListener(new OnPageChangeListener() {
			float positionOffsetPixels = 0;
			@Override
			public void onPageSelected(int postion) {
				// TODO Auto-generated method stub
					pagerCount = postion;
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub
				if(this.positionOffsetPixels == 0){
					   this.positionOffsetPixels = positionOffsetPixels;
					}
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				if (state == 0) {
					if (pagerCount >= views.size()-1&&positionOffsetPixels == 0){
						//跳转
						exitLogic();
	                }
					positionOffsetPixels = 0;
				}
			}
		});
		
		guideIndicator.setViewPager(guidePager);
//		guideIndicator.setIconStyles(R.attr.focusCricleStyle);
		
		guidePager.setPageTransformer(true, new ParallaxTransformer(0.6f, 0.3f));
	}


	

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
	}
	
	public interface GuideOnClickListener {
        public void finishGuide();
    }
	
	private class ViewPagerAdapter extends PagerAdapter implements IconPagerAdapter {

		// 界面列表
		private List<View> views;
		  
		public ViewPagerAdapter(List<View> views) {
			this.views = views;
		}

		// 销毁arg1位置的界面
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		// 获得当前界面数
		@Override
		public int getCount() {
			if (views != null) {
				return views.size();
			}

			return 0;
		}

		// 初始化arg1位置的界面
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		// 判断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		 @Override
	     public int getItemPosition(Object object)   {          
	          return POSITION_NONE;
	     }

		@Override
		public int getIconResId(int index) {
			// TODO Auto-generated method stub
			return focusad_icon_ids.get(index % focusad_icon_ids.size());
		}
	}
	
	private void creatPager(){
		focusad_icon_ids.clear();
		views.clear();
		for(int i=0;i<viewResouseId.length;i++){
			View itemView =  LayoutInflater.from(getActivity()).inflate(
					viewResouseId[i], null, false);
			views.add(itemView);
			focusad_icon_ids.add(R.drawable.focus_guide_icon);
			
			if(viewResouseId[i] == R.layout.guide_3){
				exitButton = (TextView)itemView.findViewById(R.id.guide3_exit_button);
				int screen_w = 0;
				try{
				  screen_w = DeviceUtils.getScreenWH(getActivity())[0];
				}
				catch(Exception e){
					
				}
				if(screen_w < 700){
					exitButton.setVisibility(View.GONE);
				}
				exitButton.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);  
				exitButton.getPaint().setAntiAlias(true);  
				exitButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						//跳转
						exitLogic();
					}
				});
			}
		}
	}
	
	
	private void exitLogic(){
		//跳转
		if(guideOnClickListener != null){
			guideOnClickListener.finishGuide();
		}
	}
	
	/**
	 * 视差动画相关
	 */
	private int[] shPageId = new int[]{R.id.guide1_image_1,R.id.guide1_image_2,R.id.guide1_image_3,R.id.guide1_image_4
			,R.id.guide1_image_5,//第一页
			R.id.guide2_image_1,R.id.guide2_image_2,R.id.guide2_image_3,R.id.guide2_image_4,//第二页
			R.id.guide3_image_1,R.id.guide3_image_2,R.id.guide3_image_3,R.id.guide3_image_4};//第三页
	@SuppressLint("NewApi")
	class ParallaxTransformer implements ViewPager.PageTransformer {

	    float parallaxCoefficient;
	    float distanceCoefficient;

	    public ParallaxTransformer(float parallaxCoefficient, float distanceCoefficient) {
	        this.parallaxCoefficient = parallaxCoefficient;
	        this.distanceCoefficient = distanceCoefficient;
	    }

	    @Override
	    public void transformPage(View page, float position) {
	        float scrollXOffset = page.getWidth() * parallaxCoefficient;
	        ViewGroup pageViewWrapper = (ViewGroup) page;
	        for(int i=0;i<shPageId.length;i++){
	        	 View view = pageViewWrapper.findViewById(shPageId[i]);
		            if (view != null) {
		                view.setTranslationX(scrollXOffset * position);
		                scrollXOffset *= distanceCoefficient;
		            }
		            
	        }
	    }
	}
	
}
