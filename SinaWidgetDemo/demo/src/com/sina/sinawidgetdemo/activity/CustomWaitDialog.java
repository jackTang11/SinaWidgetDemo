package com.sina.sinawidgetdemo.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sina.sinawidgetdemo.R;

/**
 * 自定义加载 请求等等待dialog
 * @author kangshaozhe
 *
 */
public class CustomWaitDialog extends Dialog{
    private int titleId;
    private Context context;
    private View view;
    private LayoutInflater inflater;
	public CustomWaitDialog(Context context) {
		super(context, R.style.waitDialog);
		this.context = context;
		inflater = LayoutInflater.from(this.context);
		// TODO Auto-generated constructor stub
	}
	
	 public CustomWaitDialog(Context context, int theme){
	    super(context, theme);
        this.context = context;
        inflater = LayoutInflater.from(this.context);
     }
	 
	 public void setWaitTitle(int titleId){
		 
		 if(view == null){
			 view = inflater.inflate(R.layout.wait_dialog, null);
		 }
		 this.titleId = titleId;
		 TextView titleView = (TextView)view.findViewById(R.id.wait_dialog_title);
	     titleView.setText(this.titleId);
	 }
	 
	 public void setWaitTitle(CharSequence title){
		 
		 if(view == null){
			 view = inflater.inflate(R.layout.wait_dialog, null);
		 }
		 TextView titleView = (TextView)view.findViewById(R.id.wait_dialog_title);
	     titleView.setText(title);
	 }

	 @Override
     protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(view);
     }
	 
	 /**
	  * 显示
	  */
	 public void showMe(){
		 if(!isShowing()){
			 this.show();
		 }
	 }
	 /**
	  * 关闭
	  */
	 public void close(){
		 if(isShowing()){
			 this.dismiss();
		 }
	 }
}
