package com.sina.sinawidgetdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.sina.sinawidgetdemo.R;

public class DrawerLayoutContentFragment extends BaseFragment {
	private ListView mContentListView;
	private TextView mHeaderView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.drawerlayout_content_fragment, container,
				false);
		initView();
		return mView;
	}

	private void initView() {
		mContentListView = (ListView) mView.findViewById(R.id.content_listview);
		mHeaderView = new TextView(myActivity);
		mHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mHeaderView.setText("这是一个关于抽屉的demo");
		mHeaderView.setTextSize(20);
		mContentListView.addHeaderView(mHeaderView);
		String str[] = new String[] { "item1", "item2", "item3"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(myActivity,
				android.R.layout.simple_list_item_1, str);
		mContentListView.setAdapter(adapter);
		mContentListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DrawerLayoutFragment fragment = (DrawerLayoutFragment) getParentFragment();
				fragment.drawerAction(String.valueOf(position));
			}
		});
	}
	
	/**
	 * 显示隐藏listview header 供drawer中调用交互
	 */
	public void toggleHeader(){
		if(mHeaderView.getVisibility()==View.VISIBLE)
			mHeaderView.setVisibility(View.GONE);
		else
			mHeaderView.setVisibility(View.VISIBLE);
	}
}