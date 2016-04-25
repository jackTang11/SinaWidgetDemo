package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.adapter.FocusImagePagerAdapter;
import com.sina.sinawidgetdemo.custom.view.AutoScrollViewPager;
import com.sina.sinawidgetdemo.utils.ViewUtils;

/**
 * 自动滑动ViewPager
 * 
 * @author rongrong5
 *
 */
public class AutoScrollViewPagerFragment extends BaseFragment {
	private AutoScrollViewPager focusadsView;
	private TextView tv_focus;
	private LinearLayout dot;
	private List<ImageView> dotList;
	private FocusImagePagerAdapter myFocusadAdapter;
	private List resArray = Arrays.asList(R.drawable.banner1,
			R.drawable.banner2, R.drawable.banner3, R.drawable.banner4);
	private TextView tv_desc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.auto_scroll_viewpager_fragment,
				container, false);
		initView(mView);
		return mView;
	}

	private void initData() {
	}

	private void initView(View view) {
		tv_desc = (TextView) view.findViewById(R.id.tv_desc);
		tv_desc.setText(getResources().getString(
				R.string.auto_scroll_viewpager_desc));
		myFocusadAdapter = new FocusImagePagerAdapter(getActivity());
		myFocusadAdapter.setInfiniteLoop(true);
		focusadsView = (AutoScrollViewPager) view.findViewById(R.id.item_focus);
		focusadsView.setAdapter(myFocusadAdapter);
		focusadsView
				.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
		focusadsView.setBorderAnimation(false);
		focusadsView.setStopScrollWhenTouch(true);
		focusadsView.startAutoScroll();// 开启自动滑动
		focusadsView.setInterval(5000);// 设置时间
		focusadsView.setCycle(false);// 设置是否循环
		focusadsView.setDirection(AutoScrollViewPager.RIGHT);// 设置方向

		focusadsView.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (getActivity() == null || getActivity().isFinishing()
						|| isDetached() || !isAdded()) {
					return;
				}
				for (int i = 0; i < dotList.size(); i++) {
					ImageView m = dotList.get(i);
					LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) m
							.getLayoutParams();
					params.setMargins(8, 0, 8, 0);
					if (i == arg0 % dotList.size()) {
						params.width = ViewUtils.dp2px(getActivity(), 5);
						params.height = ViewUtils.dp2px(getActivity(), 5);
						dotList.get(i).setBackgroundResource(
								R.drawable.focus_dot_select);
					} else {
						params.width = ViewUtils.dp2px(getActivity(), 4);
						params.height = ViewUtils.dp2px(getActivity(), 4);
						dotList.get(i).setBackgroundResource(
								R.drawable.focus_dot_unselect);
					}
					m.setLayoutParams(params);
				}

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

		// focusadViewList = creatViewPager(focusadList);
		myFocusadAdapter.setmDataList(resArray);
		dot = (LinearLayout) view.findViewById(R.id.dot);
		dotList = new ArrayList<ImageView>();
		// focusadViewList(=size+2)
		dot.removeAllViews();
		for (int i = 0; i < resArray.size(); i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(8, 0, 8, 0);

			ImageView m = new ImageView(getActivity());
			// if (i == 0) {
			// m.setVisibility(View.GONE);
			if (i == 0) {
				 m.setVisibility(View.VISIBLE);
				 params.width = ViewUtils.dp2px(getActivity(), 5);
				 params.height = ViewUtils.dp2px(getActivity(), 5);
				 m.setBackgroundResource(R.drawable.focus_dot_select);
			} else if (i > 1 && i < resArray.size() - 1) {
				m.setVisibility(View.VISIBLE);
				params.width = ViewUtils.dp2px(getActivity(), 4);
				params.height = ViewUtils.dp2px(getActivity(), 4);
				m.setBackgroundResource(R.drawable.focus_dot_unselect);
			}
			// } else {
			// m.setVisibility(View.GONE);
			// }
			m.setLayoutParams(params);
			dot.addView(m);
			dotList.add(m);
		}

		focusadsView.setCurrentItem(1, false);
		myFocusadAdapter.notifyDataSetChanged();
	}
}
