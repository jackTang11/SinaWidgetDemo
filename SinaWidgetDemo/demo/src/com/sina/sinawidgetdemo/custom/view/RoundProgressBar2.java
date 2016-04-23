
package com.sina.sinawidgetdemo.custom.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.sina.sinawidgetdemo.utils.ViewUtils;
import com.sina.sinawidgetdemo.R;
  
/** 
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度 
 * haorongrong
 */  
public class RoundProgressBar2 extends View {  
    /** 
     * 画笔对象的引用 
     */  
    private Paint paint,paint1;  
      
    /** 
     * 圆环的颜色 
     */  
    private int roundColor;  
      
    /** 
     * 圆环进度的颜色 
     */  
    private int roundProgressColor; 
    
    /**
     * 圆环进度底色
     */
     private int roundProgressBgColor;
      
    /** 
     * 中间进度百分比的字符串的颜色 
     */  
    private int textColor;  
      
    /** 
     * 中间进度百分比的字符串的字体 
     */  
    private float textIntegerSize,textDecimalSize;  
    private String integer = null;
    private String decimal = null;
    private float textIntegerWidth;
      
    /** 
     * 圆环的宽度 
     */  
    private float roundWidth;  
      
    /** 
     * 最大进度 
     */  
    private float max;  
      
    /** 
     * 当前进度 
     */  
    private float progress;  
    /** 
     * 是否显示中间的进度 
     */  
    private boolean textIsDisplayable;  
      
    /** 
     * 进度的风格，实心或者空心 
     */  
    private int style;  
      
    public static final int STROKE = 0;  //空心
    public static final int FILL = 1;  //实心
    
    /**
     * 内部显示的文字
     */
    private String content = null;
      
    public RoundProgressBar2(Context context) {  
        this(context, null);  
    }  
  
    public RoundProgressBar2(Context context, AttributeSet attrs) {  
        this(context, attrs, 0);  
    }  
      
    public RoundProgressBar2(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
          
        paint = new Paint(); 
        paint1 = new Paint();
  
          
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,  
                R.styleable.RoundProgressBar);  
          
        //获取自定义属性和默认值  
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.GRAY);  
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.RED);
        roundProgressBgColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressBgColor, Color.TRANSPARENT);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.BLACK);  
        textIntegerSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textIntegerSize, ViewUtils.sp2px(getContext(), 20));
        textDecimalSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textDecimalSize, ViewUtils.sp2px(getContext(), 15));
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, ViewUtils.dp2px(getContext(), 10));  
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);  
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);  
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);  
          
        mTypedArray.recycle();  
    }  
      
  
    @SuppressLint("DrawAllocation")
	@Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);
        //内圆
        int centre = getWidth()/2;
        int radius = (int) (centre - roundWidth);
        paint.setColor(roundColor);  
        paint.setStyle(Paint.Style.FILL);  
        paint.setStrokeWidth(roundWidth); 
        paint.setAntiAlias(true);
        canvas.drawCircle(centre, centre, radius, paint); 
       
       //进度 
        paint.setStrokeWidth(0);   
        paint.setColor(textColor);  
        paint.setTextSize(textIntegerSize);  
//        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC );
//        paint.setTypeface(font);
        
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        
        int percent = (int)(((float)progress / (float)max) * 100);
        float textWidth = paint.measureText(percent + "%");
        if(textIsDisplayable && percent != 0 && style == STROKE){  
            canvas.drawText(percent + "%", centre - textWidth / 2, centre + textIntegerSize/2, paint); //画出进度百分比  
        }
//	    String proStr = String.valueOf(progress);
//	    if("10.0".equals(proStr)){
//	    	proStr = "10";
//	    }
//	    int pos = 0;
//	    if(proStr.contains(".")){
//	    	pos = proStr.lastIndexOf(".");
//	    	integer = proStr.substring(0, pos);
//	    	textIntegerWidth = paint.measureText(integer);
//	        decimal = proStr.substring(pos, proStr.length());
//	    }else{
//	    	integer = proStr;
//	    	textIntegerWidth = paint.measureText(integer);
//	    }
//        if(textIsDisplayable &&style == STROKE){
//        	if(decimal!=null){
//        		canvas.drawText(integer , centre - textIntegerWidth, centre + textIntegerSize/2-ViewUtils.dp2px(getContext(), 2), paint); 
//            	paint.setTextSize(textDecimalSize); 
//                canvas.drawText(decimal , centre, centre + textIntegerSize/2-ViewUtils.dp2px(getContext(), 2), paint);  
//        	}else{
//        		canvas.drawText(integer , centre - textIntegerWidth/2, centre + textIntegerSize/2-ViewUtils.dp2px(getContext(), 2), paint); 
//        	}
//        	
//        }
        
        
                  
        //设置进度是实心还是空心  外环
        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundProgressColor);
        paint1.setStrokeWidth(roundWidth); 
        paint1.setColor(roundProgressBgColor);
        RectF oval = new RectF(centre - radius-roundWidth/2, centre - radius-roundWidth/2, centre  
                + radius+roundWidth/2, centre + radius+roundWidth/2);
        //画光波
//        RectF quan = new RectF(centre - radius -roundWidth -2, centre - radius -roundWidth -2, centre  
//                + radius+roundWidth+2, centre + radius+roundWidth+2);
//        paint.setStyle(Paint.Style.STROKE);  
//    	canvas.drawArc(quan, 270, 360, false, paint); 
          
        switch (style) {  
        case STROKE:{
        	paint.setStyle(Paint.Style.STROKE); 
        	paint1.setStyle(Paint.Style.STROKE);  
        	canvas.drawArc(oval, 270, 360, false, paint1); 
            canvas.drawArc(oval, 270, 360 * progress / max, false, paint); 
            break;  
        }  
        case FILL:{  
            paint.setStyle(Paint.Style.FILL_AND_STROKE);  
            if(progress !=0)  
                canvas.drawArc(oval, 270, 360 * progress / max, true, paint); 
            break;  
        	}  
        }  
          
    }  
      
      
    public synchronized float getMax() {  
        return max;  
    }  
  
    /** 
     * 设置进度的最大值 
     * @param max 
     */  
    public synchronized void setMax(int max) {  
        if(max < 0){  
            throw new IllegalArgumentException("max not less than 0");  
        }  
        this.max = max;  
    }  
  
    /** 
     * 获取进度.需要同步 
     * @return 
     */  
    public synchronized float getProgress() {  
        return progress;  
    }  
  
    /** 
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步 
     * 刷新界面调用postInvalidate()能在非UI线程刷新 
     * @param progress 
     */  
//    public synchronized void setProgress(String progress) {
//    	float pro = Float.parseFloat(progress);
//    	
//        if(pro < 0){  
//            throw new IllegalArgumentException("progress not less than 0");  
//        }  
//        if(pro > max){  
//        	pro = max;  
//        }  
//        if(pro <= max){  
//            this.progress = pro;  
//            postInvalidate();  
//        } 
//        
//        
//          
//    }  
    
    public synchronized void setProgress(int progress) {
    	float pro = progress;
    	
        if(pro < 0){  
            throw new IllegalArgumentException("progress not less than 0");  
        }  
        if(pro > max){  
        	pro = max;  
        }  
        if(pro <= max){  
            this.progress = pro;  
            postInvalidate();  
        }  
          
    }  
      
      
    public int getCricleColor() {  
        return roundColor;  
    }  
  
    public void setCricleColor(int cricleColor) {  
        this.roundColor = cricleColor;  
    }  
  
    public int getCricleProgressColor() {  
        return roundProgressColor;  
    }  
  
    public void setCricleProgressColor(int cricleProgressColor) {  
        this.roundProgressColor = cricleProgressColor;  
    }  
  
    public int getTextColor() {  
        return textColor;  
    }  
  
    public void setTextColor(int textColor) {  
        this.textColor = textColor;  
    }  
  
    public float getTextIntegerSize() {
		return textIntegerSize;
	}

	public void setTextIntegerSize(float textIntegerSize) {
		this.textIntegerSize = textIntegerSize;
	}

	public float getTextDecimalSize() {
		return textDecimalSize;
	}

	public void setTextDecimalSize(float textDecimalSize) {
		this.textDecimalSize = textDecimalSize;
	}

	public float getRoundWidth() {  
        return roundWidth;  
    }  
  
    public void setRoundWidth(float roundWidth) {  
        this.roundWidth = roundWidth;  
    }  
  
  
  
}  


