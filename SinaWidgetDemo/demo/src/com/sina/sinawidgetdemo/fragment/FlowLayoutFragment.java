package com.sina.sinawidgetdemo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.view.FlowLayout;


/**
 * 流式布局
 * @author rongrong5
 *
 */
public class FlowLayoutFragment extends BaseFragment {
	
	protected FlowLayout labelLayout;
	private List<String> lable_list = new ArrayList<String>();

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
		mView = inflater.inflate(R.layout.flow_layout_fragment, container,
				false);
		initView(mView);
		return mView;
		
	}
	private void initData(){
		lable_list.add("无冬Online");
		lable_list.add("天涯明月刀");
		lable_list.add("剑灵OL");
		lable_list.add("天谕");
		lable_list.add("奇迹MU");
		lable_list.add("魔域");
		lable_list.add("天之禁");
		lable_list.add("天龙八部3D");
		lable_list.add("倩女幽魂2");
		lable_list.add("魔兽世界");
		lable_list.add("反恐精英OL2");
		lable_list.add("笑傲江湖OL");
		lable_list.add("自由篮球");
		lable_list.add("魔幻西游2");
		lable_list.add("坦克世界");
		lable_list.add("画皮世界2");
	}
	
	private void initView(View view){
		labelLayout = (FlowLayout) view.findViewById(R.id.flow_layout);
		if(labelLayout.getChildCount()>0){
			labelLayout.removeAllViews();
		}
		for (final String value : lable_list) {
			TextView textView = new TextView(getActivity());
			textView.setLayoutParams(new FlowLayout.LayoutParams(
					FlowLayout.LayoutParams.WRAP_CONTENT,
					FlowLayout.LayoutParams.WRAP_CONTENT));
			textView.setText(value);
			textView.setTextColor(Color.parseColor("#333333"));
			textView.setTextSize(15);
			textView.setBackgroundResource(R.drawable.kan_search_lable_back);
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(myActivity, value, Toast.LENGTH_SHORT).show();
				}
			});
			labelLayout.addView(textView);
		}
	}

	
	
}
