package com.sina.sinawidgetdemo.custom.view;

import java.lang.ref.WeakReference;

import com.sina.sinawidgetdemo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形图
 * 
 * @author kangshaozhe
 * 
 */
public class OvalImageView extends ImageView {

	private int borderWidth = 0;
	private int viewWidth;
	private int viewHeight;
	private Paint paint;
	private Paint paintBorder;
	private BitmapShader shader;
	private WeakReference<Bitmap> mWeakBitmap;

	public OvalImageView(Context context) {
		super(context);
		setup();
	}

	public OvalImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
		initAttrs(context, attrs);
	}

	public OvalImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup();
		initAttrs(context, attrs);
	}

	private void initAttrs(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ovalimageThemes, 0, 0);
		int c = a.getColor(R.styleable.ovalimageThemes_bordercolor, 0);
		if (c != 0)
			setBorderColor(c);
		int width = (int) a.getDimension(
				R.styleable.ovalimageThemes_borderwidth, 0);
		if (width > 0)
			setBorderWidth(width);
		a.recycle();
	}

	private void setup() {
		paint = new Paint();
		paint.setAntiAlias(true);

		paintBorder = new Paint();
		setBorderColor(Color.TRANSPARENT);
		paintBorder.setAntiAlias(true);
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
		invalidate();
	}

	public void setBorderColor(int borderColor) {
		if (paintBorder != null) {
			paintBorder.setColor(borderColor);
		}

		invalidate();
	}

	public void invalidate() {
		if (mWeakBitmap != null) {
			mWeakBitmap.clear();
			mWeakBitmap = null;
		}
		super.invalidate();
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		if (mWeakBitmap != null) {
			mWeakBitmap.clear();
			mWeakBitmap = null;
		}
		super.onDetachedFromWindow();
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas) {
		if (!isInEditMode()) {
			int saveCount = canvas.saveLayer(0.0f, 0.0f, getWidth(),
					getHeight(), null, Canvas.ALL_SAVE_FLAG);
			try {
				Bitmap image = mWeakBitmap != null ? mWeakBitmap.get() : null;
				if (image == null || image.isRecycled()) {
					BitmapDrawable drawable = (BitmapDrawable) getDrawable();
					if (drawable != null) {
						image = scaleCenterCrop(drawable.getBitmap(),
								getWidth(), getHeight());
						mWeakBitmap = new WeakReference<Bitmap>(image);
					}
				}

				if (image != null) {
					shader = new BitmapShader(Bitmap.createScaledBitmap(image,
							getWidth(), getHeight(), false),
							Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
					paint.setShader(shader);
					int circleCenter = viewWidth / 2;

					canvas.drawCircle(circleCenter + borderWidth, circleCenter
							+ borderWidth, circleCenter + borderWidth,
							paintBorder);
					canvas.drawCircle(circleCenter + borderWidth, circleCenter
							+ borderWidth, circleCenter, paint);
					return;
				}
			} catch (Exception e) {
				System.gc();

			} finally {
				canvas.restoreToCount(saveCount);
			}
		} else {
			super.onDraw(canvas);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec, widthMeasureSpec);

		viewWidth = width - (borderWidth * 2);
		viewHeight = height - (borderWidth * 2);

		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = viewWidth;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = viewHeight;
		}
		return result;
	}

	Bitmap scaleCenterCrop(Bitmap source, int newWidth, int newHeight) {
		if (source == null || source.isRecycled()) {
			return null;
		}
		int sourceWidth = source.getWidth();
		int sourceHeight = source.getHeight();

		float xScale = (float) newWidth / sourceWidth;
		float yScale = (float) newHeight / sourceHeight;
		float scale = Math.max(xScale, yScale);

		float scaledWidth = scale * sourceWidth;
		float scaledHeight = scale * sourceHeight;

		float left = (newWidth - scaledWidth) / 2;
		float top = (newHeight - scaledHeight) / 2;

		RectF targetRect = new RectF(left, top, left + scaledWidth, top
				+ scaledHeight);

		Bitmap dest = Bitmap.createBitmap(newWidth, newHeight,
				source.getConfig());
		Canvas canvas = new Canvas(dest);
		canvas.drawBitmap(source, null, targetRect, null);

		return dest;
	}
}
