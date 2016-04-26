package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.viewpagerindicator.TabPageIndicator;
import com.sina.sinawidgetdemo.custom.viewpagerindicator.TabPageIndicator.OnTabCollListener;
import com.sina.sinawidgetdemo.custom.viewpagerindicator.TabTextChangeIndicator;

public class HomeFragment extends BaseFragment {
	private ViewPager mHomePager;
	private TabTextChangeIndicator mHomeIndicator;
	private MyPagerAdapter myPagerAdapter;

	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private List<String> mTitleList = new ArrayList<String>();
	private int mCurrentItem;
	private final String SAVED_CURRENT_ITEM = "saved_current_item";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// if (savedInstanceState != null) {
		// mCurrentItem = savedInstanceState.getInt(SAVED_CURRENT_ITEM);
		// }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		if (!isViewNull()) {
			return mView;
		}
		initData();
		mView = inflater.inflate(R.layout.home_fragment, container, false);
		initView(mView);
		initListener();
		return mView;
	}

	private void initData() {
		mTitleList.add("自动滑动ViewPager");
		mFragmentList.add(new AutoScrollViewPagerFragment());
		mTitleList.add("自动滑动Gallery");
		mFragmentList.add(new TimerGalleryFragment());
		mTitleList.add("横向滑动新");
		mFragmentList.add(new HorizontalScrollFragmentByRecyclerView());// 横向滑动
		mTitleList.add("下拉放大回弹ScrollView");
		mFragmentList.add(new ZoomFragment());
		mTitleList.add("圆角图");
		mFragmentList.add(new RoundImageViewFragment());
		mTitleList.add("流式布局");
		mFragmentList.add(new FlowLayoutFragment());
		mTitleList.add("进度条");
		mFragmentList.add(new ProgressBarFragment());
		mTitleList.add("抽屉布局");
		mFragmentList.add(new DrawerLayoutFragment());
		mTitleList.add("拖动排序listview");
		mFragmentList.add(new DragSortListFragment());
		mTitleList.add("测试9");
		mFragmentList.add(new MoreFragment());
		mTitleList.add("测试10");
		mFragmentList.add(new MoreFragment());
	}

	private void initView(View view) {
		mHomePager = (ViewPager) view.findViewById(R.id.home_pager);

		myPagerAdapter = new MyPagerAdapter(getFragmentManager());
		myPagerAdapter.setData(mFragmentList, mTitleList);
		mHomePager.setAdapter(myPagerAdapter);

		mHomeIndicator = (TabTextChangeIndicator) view.findViewById(R.id.home_indicator);
		mHomeIndicator.setViewPager(mHomePager);
	}

	private void initListener() {
		mHomeIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int page) {
				mCurrentItem = page;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
//		mHomeIndicator.setOnTabCollListener(new OnTabCollListener() {
//
//			@Override
//			public void OnTabColl(int coll_dir) {
//				// TODO Auto-generated method stub
//				switch (coll_dir) {
//				// none
//				case 0:
//					break;
//				// left
//				case 1:
//					break;
//				// rigth
//				case 2:
//					break;
//				}
//			}
//		});
	}

	public void flushPage() {
		myPagerAdapter.setData(mFragmentList, mTitleList);
		myPagerAdapter.notifyDataSetChanged();
		mHomeIndicator.notifyDataSetChanged();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		int page = mHomePager.getCurrentItem();
		if (page < mFragmentList.size()) {
			mFragmentList.get(page).onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * viewpager adapter
	 * 
	 * @author kangshaozhe
	 *
	 */
	class MyPagerAdapter extends FragmentStatePagerAdapter {
		private List<Fragment> myFragmentList;
		private List<String> mNewsListTitle;
		FragmentManager fm;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
			// TODO Auto-generated constructor stub
		}

		public void setData(List<Fragment> fragmentList, List<String> mNewsListTitle) {
			myFragmentList = fragmentList;
			this.mNewsListTitle = mNewsListTitle;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = myFragmentList.get(position);
			return fragment;
		}

		/**
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return (mNewsListTitle.size() > position) ? mNewsListTitle.get(position) : "";
		}

		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return myFragmentList == null ? 0 : myFragmentList.size();
		}

		// @Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			Fragment ff = (Fragment) super.instantiateItem(container, position);
			return ff;
		}
	}

	// @Override
	// public void onSaveInstanceState(Bundle outState) {
	// outState.putInt(SAVED_CURRENT_ITEM, mCurrentItem);
	// super.onSaveInstanceState(outState);
	// }

	@Override
	public void onPause() {
		SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt(SAVED_CURRENT_ITEM, mCurrentItem);
		editor.commit();
		super.onPause();
	}

	@Override
	public void onResume() {
		SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
		mCurrentItem = sharedPreferences.getInt(SAVED_CURRENT_ITEM, 0);
		if (mHomeIndicator != null && mFragmentList.size() > 0 && mCurrentItem < mFragmentList.size()
				&& mCurrentItem >= 0) {
			mHomeIndicator.setCurrentItem(mCurrentItem);
		}
		super.onResume();
	}

}
