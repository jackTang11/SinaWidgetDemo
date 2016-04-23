package com.sina.sinawidgetdemo.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角图
 * 
 * @author liu_chonghui
 * 
 */
public class RoundImageView extends ImageView {

	protected RoundedShader pathHelper;

	public RoundImageView(Context context) {
		super(context);
		setup(context, null, 0);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(context, attrs, 0);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup(context, attrs, defStyle);
	}

	private void setup(Context context, AttributeSet attrs, int defStyle) {
		getPathHelper().init(context, attrs, defStyle);
	}

	protected RoundedShader getPathHelper() {
		if (pathHelper == null) {
			pathHelper = new RoundedShader();
		}
		return pathHelper;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (getPathHelper().isSquare()) {
			int width = getMeasuredWidth();
			int height = getMeasuredHeight();
			int dimen = Math.min(width, height);
			setMeasuredDimension(dimen, dimen);
		}
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		getPathHelper().onImageDrawableReset(getDrawable());
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		getPathHelper().onImageDrawableReset(getDrawable());
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		getPathHelper().onImageDrawableReset(getDrawable());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		getPathHelper().onSizeChanged(w, h);
	}

	@SuppressLint("WrongCall")
	@Override
	public void onDraw(Canvas canvas) {
		if (!getPathHelper().onDraw(canvas)) {
			super.onDraw(canvas);
		}
	}

	private class RoundedShader {
		private int ALPHA_MAX = 255;

		protected int viewWidth;
		protected int viewHeight;

		protected int borderColor = Color.WHITE;
		protected int borderWidth = 0;
		protected float borderAlpha = 1f;
		protected boolean square = false;

		protected Paint borderPaint;
		protected Paint imagePaint;
		protected BitmapShader shader;
		protected Drawable drawable;
		protected Matrix matrix = new Matrix();

		private RectF borderRect = new RectF();
		private RectF imageRect = new RectF();

		private int radius = 0;
		private int bitmapRadius;

		public RoundedShader() {
			borderPaint = new Paint();
			borderPaint.setStyle(Paint.Style.STROKE);
			borderPaint.setAntiAlias(true);

			imagePaint = new Paint();
			imagePaint.setAntiAlias(true);
		}

		public void init(Context context, AttributeSet attrs, int defStyle) {
			if (attrs != null) {
				borderWidth = 0;
				square = true;
			}

			borderPaint.setColor(borderColor);
			borderPaint.setAlpha(Float.valueOf(borderAlpha * ALPHA_MAX)
					.intValue());
			borderPaint.setStrokeWidth(borderWidth);
			if (attrs != null) {
				radius = 25;
			}
		}

		public void draw(Canvas canvas, Paint imagePaint, Paint borderPaint) {
			if (borderWidth > 0) {
				canvas.drawRoundRect(borderRect, radius, radius, borderPaint);
			}
			canvas.save();
			canvas.concat(matrix);
			canvas.drawRoundRect(imageRect, bitmapRadius, bitmapRadius,
					imagePaint);
			canvas.restore();
		}

		public void onSizeChanged(int width, int height) {
			viewWidth = width;
			viewHeight = height;
			if (isSquare()) {
				viewWidth = viewHeight = Math.min(width, height);
			}
			if (shader != null) {
				calculateDrawableSizes();
			}

			borderRect.set(borderWidth, borderWidth, viewWidth - borderWidth,
					viewHeight - borderWidth);
		}

		public Bitmap calculateDrawableSizes() {
			Bitmap bitmap = getBitmap();
			if (bitmap != null) {
				int bitmapWidth = bitmap.getWidth();
				int bitmapHeight = bitmap.getHeight();

				if (bitmapWidth > 0 && bitmapHeight > 0) {
					float width = Math.round(viewWidth - 2f * borderWidth);
					float height = Math.round(viewHeight - 2f * borderWidth);

					float scale = 1f;
					float translateX = 0;
					float translateY = 0;

					if (bitmapWidth * height > width * bitmapHeight) {
						scale = height / bitmapHeight;
						translateX = Math
								.round((width / scale - bitmapWidth) / 2f);
					} else {
						scale = width / (float) bitmapWidth;
						translateY = Math
								.round((height / scale - bitmapHeight) / 2f);
					}

					matrix.setScale(scale, scale);
					matrix.preTranslate(translateX, translateY);
					matrix.postTranslate(borderWidth, borderWidth);

					imageRect.set(-translateX, -translateY, bitmapWidth
							+ translateX, bitmapHeight + translateY);
					bitmapRadius = Math.round(radius / scale);
					return bitmap;
				}
			}
			return null;
		}

		public boolean isSquare() {
			return square;
		}

		public boolean onDraw(Canvas canvas) {
			if (shader == null) {
				createShader();
			}
			if (shader != null && viewWidth > 0 && viewHeight > 0) {
				draw(canvas, imagePaint, borderPaint);
				return true;
			}

			return false;
		}

		public final void onImageDrawableReset(Drawable drawable) {
			this.drawable = drawable;
			shader = null;
			imagePaint.setShader(null);
		}

		protected void createShader() {
			Bitmap bitmap = calculateDrawableSizes();
			if (bitmap != null && bitmap.getWidth() > 0
					&& bitmap.getHeight() > 0) {
				shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
						Shader.TileMode.CLAMP);
				imagePaint.setShader(shader);
			}
		}

		protected Bitmap getBitmap() {
			Bitmap bitmap = null;
			if (drawable != null) {
				if (drawable instanceof BitmapDrawable) {
					bitmap = ((BitmapDrawable) drawable).getBitmap();
				}
			}

			return bitmap;
		}
	}

}
