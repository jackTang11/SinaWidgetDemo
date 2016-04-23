package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.horizontalvariablelistview.HListView;

/**
 * 
 * @author liu_chonghui
 * 
 */
public final class HorizontalScrollFragment extends BaseFragment {

	protected int getPageLayout() {
		return R.layout.horizontalscroll_fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initImageLoader();
	}

	protected void initData() {
		contentList1.clear();
		contentList1.add(new TestItem("0"));
		contentList1.add(new TestItem("1"));
		contentList1.add(new TestItem("2"));
		contentList1.add(new TestItem("3"));
		contentList1.add(new TestItem("4"));
		contentList1.add(new TestItem("5"));
		contentList1.add(new TestItem("6"));
		contentList1.add(new TestItem("7"));
		contentList1.add(new TestItem("8"));
		contentList1.add(new TestItem("9"));
		contentList2.clear();
		contentList2.add(new TestItem("字段0"));
		contentList2.add(new TestItem("字段1"));
		contentList2.add(new TestItem("字段2"));
		contentList2.add(new TestItem("字段3"));
		contentList2.add(new TestItem("字段4"));
		contentList2.add(new TestItem("字段5"));
		contentList2.add(new TestItem("字段6"));
		contentList2.add(new TestItem("字段7"));
		contentList2.add(new TestItem("字段8"));
		contentList2.add(new TestItem("字段9"));

	}

	protected void initImageLoader() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(getPageLayout(), container, false);
		initView(mView);
		return mView;
	}

	protected HListView mListView1;
	protected View listHeader1;
	protected NewsListAdapter mAdapter1;
	protected List<TestItem> contentList1 = new ArrayList<TestItem>();

	protected HListView mListView2;
	protected NewsListAdapter mAdapter2;
	protected List<TestItem> contentList2 = new ArrayList<TestItem>();

	@SuppressLint("InflateParams")
	protected void initView(View view) {
		mListView1 = (HListView) view.findViewById(R.id.hListView1);

		listHeader1 = LayoutInflater.from(getActivity()).inflate(
				R.layout.horizontalscroll_fragment_listheader, null);
		mListView1.addHeaderView(listHeader1);

		mAdapter1 = new NewsListAdapter(getActivity());
		mListView1.setAdapter(mAdapter1);
		flushData1();

		mListView2 = (HListView) view.findViewById(R.id.hListView2);
		mAdapter2 = new NewsListAdapter(getActivity()) {
			@Override
			protected int getItemLayout() {
				return R.layout.horizontalscroll_simple_item;
			}
		};
		mListView2.setAdapter(mAdapter2);

		flushData2();
	}

	protected void flushData1() {
		mAdapter1.setData(contentList1);
		mAdapter1.notifyDataSetChanged();
	}

	protected void flushData2() {
		mAdapter2.setData(contentList2);
		mAdapter2.notifyDataSetChanged();
	}

	class TestItem {
		String content;

		public TestItem(String content) {
			this.content = content;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}

	class NewsListAdapter extends BaseAdapter {
		List<TestItem> myNewsList = new ArrayList<TestItem>();

		public NewsListAdapter(Context ctx) {
		}

		public void setData(List<TestItem> myNewsList) {
			this.myNewsList = myNewsList;
		}

		@Override
		public int getCount() {
			if (myNewsList != null) {
				return myNewsList.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return myNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		protected int getItemLayout() {
			return R.layout.horizontalscroll_item;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TestItem item = myNewsList.get(position);
			String content = item.getContent();
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getActivity()).inflate(
						getItemLayout(), parent, false);
				holder.itemLayout = convertView.findViewById(R.id.item_layout);
				holder.text = (TextView) convertView.findViewById(R.id.text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (holder.text != null) {
				holder.text.setText(content);
			}
			if (holder.itemLayout != null) {
				holder.clickListener.setData(content);
				holder.itemLayout.setOnClickListener(holder.clickListener);
			}

			return convertView;
		}

		class ViewHolder {
			TextView text;
			View itemLayout;
			ItemClickListener clickListener = new ItemClickListener();
		}

		class ItemClickListener implements OnClickListener {
			String content;

			@Override
			public void onClick(View view) {
				if (getActivity() != null && !getActivity().isFinishing()) {
					Toast.makeText(getActivity(), "click:" + content,
							Toast.LENGTH_LONG).show();
				}
			}

			public void setData(String content) {
				this.content = content;
			}
		}
	}
}