package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.recyclerview.CustomLinearLayoutManager;
import com.sina.sinawidgetdemo.custom.recyclerview.CustomOnItemClickListener;
import com.sina.sinawidgetdemo.custom.recyclerview.HorizontalRecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Li Shang
 * 
 */
public final class HorizontalScrollFragmentByRecyclerView extends BaseFragment implements OnClickListener {

	protected HorizontalRecyclerView mRecyclerView1;
	protected RecyclerView mRecyclerView2;
	protected MyAdapter mAdapter1, mAdapter2;
	private CustomLinearLayoutManager mLayoutManager1, mLayoutManager2;
	private Button update1, update2;

	protected int getPageLayout() {
		return R.layout.horizontalscroll_fragment_recyclerview;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initDataset();
	}

	protected List<String> mDataset;
	private static final int DATASET_COUNT = 10;

	/**
	 * Generates Strings for RecyclerView's adapter. This data would usually
	 * come from a local content provider or remote server.
	 */
	private void initDataset() {
		mDataset = new ArrayList<String>();
		for (int i = 0; i < DATASET_COUNT; i++) {
			String s = "This is element #" + i;
			mDataset.add(s);
		}
	}

	private void addLargeDataset() {
		// if (mDataset != null) {
		// mDataset.clear();
		// } else {
		// return;
		// }

		for (int i = 0; i < DATASET_COUNT; i++) {
			String s = "This is added element #" + i + " after update!";
			mDataset.add(s);
		}
	}

	private void resetShortDataset() {
		if (mDataset != null) {
			mDataset.clear();
		} else {
			return;
		}

		for (int i = 0; i < DATASET_COUNT; i++) {
			String s = "This is element #" + i;
			mDataset.add(s);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if (!isViewNull()) {
			return mView;
		}
		mView = inflater.inflate(getPageLayout(), container, false);
		initView(mView, savedInstanceState);

		return mView;
	}

	@SuppressLint({ "InflateParams", "NewApi" })
	protected void initView(View view, Bundle savedInstanceState) {

		update1 = (Button) view.findViewById(R.id.update_long);
		update2 = (Button) view.findViewById(R.id.update_short);
		update1.setOnClickListener(this);
		update2.setOnClickListener(this);

		mRecyclerView1 = (HorizontalRecyclerView) view.findViewById(R.id.hListView1);
		mRecyclerView2 = (RecyclerView) view.findViewById(R.id.hListView2);
		if (Build.VERSION.SDK_INT > 9) {
			mRecyclerView2.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
		}
		// mRecyclerView1已经封装好了横向recyclerview的初始化
		mRecyclerView1.setMeasuredByIndex(mDataset.size() - 1);

		// mRecyclerView2为普通recyclerview的初始化过程
		mLayoutManager2 = new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		mLayoutManager2.setMeasuredByIndex(mDataset.size() - 1);// 设置以最后一个view为测量标准
		mRecyclerView2.setLayoutManager(mLayoutManager2);

		mAdapter1 = new MyAdapter(mDataset);
		mAdapter2 = new MyAdapter(mDataset);
		mRecyclerView1.setAdapter(mAdapter1);
		mRecyclerView2.setAdapter(mAdapter2);

	}

	public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

		private List<String> mDataSet;

		public MyAdapter(List<String> dataSet) {
			mDataSet = dataSet;
		}

		public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

			private CustomOnItemClickListener onClickListener;
			private View rootView;
			public TextView textView;
			public ImageView image;

			public ViewHolder(View view) {
				super(view);
				Log.e("ViewHolder", "ViewHolder");
				rootView = view;
				textView = (TextView) view.findViewById(R.id.text);
				image = (ImageView) view.findViewById(R.id.image);

			}

			@Override
			public void onClick(View v) {
				if (onClickListener != null) {
					onClickListener.onClick(v, getPosition());
				}

			}

			public void setOnclickListener(CustomOnItemClickListener onClickListener) {
				this.onClickListener = onClickListener;
				rootView.setOnClickListener(this);

			}

		}

		// Create new views (invoked by the layout manager)
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			// create a new view
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.normal_recyclerview_item, parent, false);
			// set the view's size, margins, paddings and layout parameters
			Log.e("onCreateViewHolder", "onCreateViewHolder" + viewType);
			ViewHolder holder = new ViewHolder(v);

			holder.setOnclickListener(new CustomOnItemClickListener() {

				@Override
				public void onClick(View v, int position) {
					Toast.makeText(getActivity(), "element " + position + " is clicked!", Toast.LENGTH_SHORT).show();

				}
			});
			
			
			
			return holder;
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, final int position) {
			holder.image.setLayoutParams(new LayoutParams(300, 200));
			
			
			
			
			holder.textView.setText(mDataSet.get(position));
			
			Log.e("onBindViewHolder", "onBindViewHolder" + position);

		}

		@Override
		public int getItemCount() {
			return mDataSet.size();
		}
	}

	/**
	 * 
	 */
	private void updateRecyclerView(boolean longString) {
		if (longString) {
			addLargeDataset();
		} else {
			resetShortDataset();
		}

		mRecyclerView1.setMeasuredByIndex(mDataset.size() - 1);
		// （不要重新初始化mLayoutManager2，否则计算高度会出错）
		mLayoutManager2.setMeasuredByIndex(mDataset.size() - 1);// 设置以最后一个view为测量标准

		mAdapter1.notifyDataSetChanged();
		mAdapter2.notifyDataSetChanged();

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.update_long) {
			updateRecyclerView(true);
		} else if (id == R.id.update_short) {
			updateRecyclerView(false);
		}

	}

}