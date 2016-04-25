package com.sina.sinawidgetdemo.custom.timergallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;

import com.sina.sinawidgetdemo.R;

import java.util.List;

public class TimerGallery extends LinearLayout {

	private LayoutInflater inflater;
	/**
	 * 正常状态的点
	 */
	private Bitmap normal;
	/**
	 * 选中的点
	 */
	private Bitmap select;
	private MyGallery gallery;
	private ImageView point;
	private List<JumpableImage> mFocusImageList;
	private int positionIndex = 0;
	private static final int BANNER_AUTO_PLAY_INTERVAL = 4000;

	private boolean mIsActive = true;//当前定时器是否处于活动状态

//	public TimeGallery(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		// initConfig(context);
//	}

	public TimerGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// initConfig(context);
	}

	public TimerGallery(Context context) {
		super(context);
		// initConfig(context);
	}
	private CustomerTimer timer;

	public void init(Context context, List<JumpableImage> mFocusImageList) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.specaltime_card_banner_layout,
				null);
		gallery = (MyGallery) view.findViewById(R.id.big_gallery);
		point = (ImageView) view.findViewById(R.id.point);
		normal = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.point_normal)).getBitmap();
        select = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.point_selected)).getBitmap();
		this.mFocusImageList = mFocusImageList;
		int imageSize = mFocusImageList.size();
		if (imageSize > 1) {
			gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);//增加滑动效果
			point.setVisibility(View.VISIBLE);
			point.setImageBitmap(drawPoint(mFocusImageList.size(), 0));
			positionIndex = 0;		
			if (timer == null) {
				int millisInFuture = 0;
				if (!isCycle) {
					millisInFuture = Math.min(imageSize * BANNER_AUTO_PLAY_INTERVAL,
							Integer.MAX_VALUE);
				} else {
					millisInFuture = Integer.MAX_VALUE;
				}
				timer = new CustomerTimer(millisInFuture, BANNER_AUTO_PLAY_INTERVAL);
			}
			timer.start();
		}else{
			point.setVisibility(View.GONE);
			if (timer != null) {
				timer.cancel();
			}
			positionIndex = 0;
		}
		addView(view);

		setOnItemSelectedListener(new TimerGalleryItemSelector());
	}

	class TimerGalleryItemSelector implements AdapterView.OnItemSelectedListener {
		protected boolean flag = false;

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
			if (flag) {
				onTimerGalleryItemSelected(position);
			}
			flag = true;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	}

	protected void onTimerGalleryItemSelected(int position) {
		onItemSelected(position);
	}

	public void onHiddenChanged(boolean hidden) {
		if (hidden) {
			suspendBanner();
		} else {
			restartBanner();
		}
	}

	/**
	 * 当banner不可见时,暂停定时器
	 */
	public void suspendBanner() {
		if(this.mFocusImageList != null && this.mFocusImageList.size() > 0) {
			this.mIsActive = false;
		}
	}

	/**
	 * 当banner可见时,重新开启定时器
	 */
	public void restartBanner() {
		if(this.mFocusImageList != null && this.mFocusImageList.size() > 0) {
			this.mIsActive = true;
		}
	}

	boolean isCycle = true;

	public boolean isCycle() {
		return isCycle;
	}

	public void setCycle(boolean isCycle) {
		this.isCycle = isCycle;
	}

	public void setImageList(List<JumpableImage> mFocusImageList) {
		this.mFocusImageList = mFocusImageList;
	}

	public Bitmap drawPoint(int num, int position) {
		if (num <= 0) {
			num = 1;
		}
		Bitmap bitmap = Bitmap.createBitmap(dip2px(getContext(), num * 15), dip2px(getContext(), 10),
				Bitmap.Config.ARGB_4444);
		if (bitmap == null) {
			return null;
		}
		Canvas canvas = new Canvas(bitmap);
		int x = 0;
		for (int i = 0; i < num; i++) {
			if (i == position) {
				canvas.drawBitmap(select, x, 0, null);
			} else {
				canvas.drawBitmap(normal, x, 0, null);
			}
			x += dip2px(getContext(), 15);
		}
		return bitmap;
	}

	class CustomerTimer extends CountDownTimer {

		public CustomerTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// timeHandler.post();
//			gallery.setSelection(positionIndex % mFocusImageList.size()
//					+ (Integer.MAX_VALUE / 2 / (mFocusImageList.size()))
//					* mFocusImageList.size());
			if(!mIsActive) {
				return;
			}
			if(gallery != null) {
				gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);//增加滑动效果
			}
			point.setImageBitmap(drawPoint(mFocusImageList.size(),
					positionIndex % mFocusImageList.size()));// 绘制点

			if (mFocusImageList != null && mFocusImageList.size() != 0) {
				if (positionIndex < mFocusImageList.size() - 1) {
					positionIndex++;
				} else {
					positionIndex = 0;
				}
			}

		}

		@Override
		public void onFinish() {

		}
	}

	public void setOnItemSelectedListener(OnItemSelectedListener listener) {
		gallery.setOnItemSelectedListener(listener);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		gallery.setOnItemClickListener(listener);
	}
	public void onItemSelected(int position){
		positionIndex = position % mFocusImageList.size();
		point.setImageBitmap(drawPoint(mFocusImageList.size(), position % mFocusImageList.size()));// 绘制点
	}

	/*
	** 在start之后必须成对调用，否则会导致创建timer的对象无法被GC
	 */
	public void cancelTimer(){
		if(timer!=null){
			timer.cancel();
		}
	}
	public void startTimer(){
		if(timer==null){
			timer = new CustomerTimer(Long.MAX_VALUE, BANNER_AUTO_PLAY_INTERVAL);
		}
		timer.start();
	}
	 public void setAdapter(SpinnerAdapter adapter) {
		 gallery.setAdapter(adapter); 
	 }

	protected int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
