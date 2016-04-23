package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sina.sinawidgetdemo.R;

public class DrawerLayoutDrawerFragment extends BaseFragment {
	private ListView mDrawerListView;
	private ArrayList<String> mArrayList = new ArrayList<String>();
	ArrayAdapter<String> mAdapter;

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
		mView = inflater.inflate(R.layout.drawerlayout_drawer_fragment,
				container, false);
		initView();
		return mView;
	}

	private void initView() {
		mDrawerListView = (ListView) mView.findViewById(R.id.drawer_listview);
		mArrayList.add("item1");
		mArrayList.add("item2");
		mArrayList.add("item3");
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mArrayList);
		mDrawerListView.setAdapter(mAdapter);
		mDrawerListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				DrawerLayoutFragment fragment = (DrawerLayoutFragment) getParentFragment();
				if (fragment != null && fragment.isAdded())
					fragment.contentAction();
			}
		});
	}

	public void insertData(String data) {
		if (!TextUtils.isEmpty(data))
			mArrayList.add(data);
		mAdapter.notifyDataSetChanged();
		mDrawerListView.setSelection(mAdapter.getCount());
	}
}