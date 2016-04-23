package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshDragSortListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.extras.dragsort.DragSortController;
import com.handmark.pulltorefresh.library.extras.dragsort.DragSortListView;
import com.sina.sinawidgetdemo.R;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Li Shang
 *
 */
public class DragSortListFragment extends BaseFragment {

	private PullToRefreshDragSortListView pullToRefreshDragSortListView;
	private DragSortListView dragSortListView;
	private DragSortController dragSortController;
	private List<String> mData = new ArrayList<String>();
	private boolean dragStatus = false;
	private Button switcher, delete;
	private DragSortListAdapter adapter;

	/** 拖动手松开之后的事件监听，主要用来给数据排序，更新界面 */
	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			if (from != to) {
				String item = adapter.getItem(from);
				mData.remove(from);

				mData.add(to, item);
				adapter.notifyDataSetChanged();
			}
		}
	};

	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.drag_sort_list_fragment, container, false);

		initView();

		return mView;
	}

	@SuppressWarnings("unchecked")
	private void initView() {
		pullToRefreshDragSortListView = (PullToRefreshDragSortListView) mView.findViewById(R.id.list);
		dragSortListView = (DragSortListView) pullToRefreshDragSortListView.getRefreshableView();
		switcher = (Button) mView.findViewById(R.id.switcher);
		delete = (Button) mView.findViewById(R.id.delete);

		pullToRefreshDragSortListView.setDropListener(onDrop);// 设置拖动手松开之后的事件监听
		pullToRefreshDragSortListView.setFloatAlpha(0.5f);// 设置拖动时的背景颜色的透明度
		dragSortController = buildController(pullToRefreshDragSortListView);// 新建一个controller，并将它与pullToRefreshDragSortListView关联
		pullToRefreshDragSortListView.setOnRefreshListener(new OnRefreshListener2() {
			// 设置下拉刷新的事件
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				initData();
				adapter.notifyDataSetChanged();
				pullToRefreshDragSortListView.onRefreshComplete();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mData.add("This is added item!");
				adapter.notifyDataSetChanged();
				pullToRefreshDragSortListView.onRefreshComplete();

			}
		});

		switcher.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dragStatus) {
					dragStatus = false;
				} else {
					dragStatus = true;
				}

				switchDragStatus();
			}
		});

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (adapter != null && adapter.getCount() > 0 && mData != null && mData.size() > 0) {
					mData.remove(mData.size() - 1);
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(getActivity(), "There is no data now!", Toast.LENGTH_SHORT).show();
				}
			}
		});

		adapter = new DragSortListAdapter();
		pullToRefreshDragSortListView.setAdapter(adapter);
		switchDragStatus();
	}

	private void switchDragStatus() {
		if (dragStatus) {
			switcher.setText("disable drag!");

		} else {
			switcher.setText("enable drag!");
		}

		// list.setDragEnabled(dragStatus);
		pullToRefreshDragSortListView.setDragSortEnable(dragStatus);
		adapter.notifyDataSetChanged();
	}

	private DragSortController buildController(PullToRefreshDragSortListView listView) {
		DragSortController dragSortController = listView.getDragSortController();
		dragSortController.setDragHandleId(R.id.handler);// 设置可拖动的view，该id需要和adapter中对应得view相对应
		dragSortController.setBackgroundColor(Color.RED);// 设置拖动时的背景色

		return dragSortController;

	}

	private void initData() {
		mData.clear();
		for (int i = 0; i < 40; i++) {
			mData.add("This is test data " + i);
		}

	}

	class DragSortListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public String getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.drag_sort_list_item, null);
			}

			TextView textView = (TextView) convertView.findViewById(R.id.text);
			TextView handler = (TextView) convertView.findViewById(R.id.handler);
			textView.setText(mData.get(position));

			if (dragStatus) {
				handler.setVisibility(View.VISIBLE);
			} else {
				handler.setVisibility(View.GONE);
			}

			return convertView;
		}

	}

}
