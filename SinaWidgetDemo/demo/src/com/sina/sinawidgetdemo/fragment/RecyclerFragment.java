package com.sina.sinawidgetdemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sina.sinawidgetdemo.R;

public class RecyclerFragment extends BaseFragment implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(R.layout.activity_my, container, false);
		initView(mView);
		return mView;
	}

	protected void initView(View view) {
		initHorizaontal(view);
		initVertical(view);
	}

	private void initHorizaontal(View view) {
		RecyclerView recyclerView = (RecyclerView) view
				.findViewById(R.id.recyclerview_horizontal);

		// 创建一个线性布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity());
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		// 设置布局管理器
		recyclerView.setLayoutManager(layoutManager);

		// 创建数据集
		String[] dataset = new String[100];
		for (int i = 0; i < dataset.length; i++) {
			dataset[i] = "item" + i;
		}
		// 创建Adapter，并指定数据集
		MyAdapter adapter = new MyAdapter(dataset);
		// 设置Adapter
		recyclerView.setAdapter(adapter);
	}

	public void initVertical(View view) {
		RecyclerView recyclerView = (RecyclerView) view
				.findViewById(R.id.recyclerview_vertical);

		// 创建一个线性布局管理器
		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity());
		// 默认是Vertical，可以不写
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// 设置布局管理器
		recyclerView.setLayoutManager(layoutManager);

		// 创建数据集
		String[] dataset = new String[100];
		for (int i = 0; i < dataset.length; i++) {
			dataset[i] = "item" + i;
		}
		// 创建Adapter，并指定数据集
		MyAdapter adapter = new MyAdapter(dataset);
		// 设置Adapter
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {

	}

	class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

		// 数据集
		private String[] mDataset;

		public MyAdapter(String[] dataset) {
			super();
			mDataset = dataset;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			// 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
			View view = View.inflate(viewGroup.getContext(),
					android.R.layout.simple_list_item_1, null);
			// 创建一个ViewHolder
			ViewHolder holder = new ViewHolder(view);
			return holder;
		}

		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int i) {
			// 绑定数据到ViewHolder上
			viewHolder.mTextView.setText(mDataset[i]);
		}

		@Override
		public int getItemCount() {
			return mDataset.length;
		}

		class ViewHolder extends RecyclerView.ViewHolder {

			public TextView mTextView;

			public ViewHolder(View itemView) {
				super(itemView);
				mTextView = (TextView) itemView;
			}
		}
	}
}