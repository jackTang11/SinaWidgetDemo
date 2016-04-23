package com.sina.sinawidgetdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.sina.sinawidgetdemo.R;
import com.sina.sinawidgetdemo.custom.view.RoundProgressBar2;


/**
 * 进度条
 * @author rongrong5
 *
 */
public class ProgressBarFragment extends BaseFragment {
	
	private RoundProgressBar2 roundProgressBar;
	private Button btn_start;
	private int progress=0;
	private ProgressCallback mCallback = null;
	
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
		mView = inflater.inflate(R.layout.progress_bar_fragment, container,
				false);
		
		initView(mView);
		return mView;
		
	}
	private void initData(){
		if(mCallback == null){
			mCallback = new ProgressCallback();
		}
		
	}
	
	private void initView(View view){
		roundProgressBar = (RoundProgressBar2) view.findViewById(R.id.roundProgressBar);
		roundProgressBar.setProgress(0);
		
		btn_start = (Button) view.findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(mCallback).start();
			}
		});
	}

	class ProgressCallback implements Runnable{

		@Override
		public void run() {
			
			while(progress<100){
				try {
					progress++;
					Message msg = new Message();
					msg.what = 0;
					msg.arg1 = progress;
					mHandler.sendMessage(msg);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
			
		}
		
	}
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				roundProgressBar.setProgress(progress);
				roundProgressBar.invalidate();
				break;
			
			default:
				break;
			}
			
		}
		
	};
	
	
}
