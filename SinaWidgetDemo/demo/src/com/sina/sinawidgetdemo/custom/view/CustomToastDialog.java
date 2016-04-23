package com.sina.sinawidgetdemo.custom.view;

import com.sina.sinawidgetdemo.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义toast
 * @author kangshaozhe
 *
 */
public class CustomToastDialog{
    private Context context;
    private View view;
    private LayoutInflater inflater;
    private Toast toast;
    
	public CustomToastDialog(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(this.context);
		// TODO Auto-generated constructor stub
		 view = inflater.inflate(R.layout.toast_dialog, null);
		 toast = Toast.makeText(context,"", Toast.LENGTH_SHORT);
		 toast.setGravity(Gravity.CENTER, 0, 0);;
	     toast.setView(view);
		 
	}
	
	 
	 public CustomToastDialog setWaitTitle(int titleId,int iconId){
		 if(view == null){
			 return this;
		 }
		 if(titleId > 0){
			 TextView titleView = (TextView)view.findViewById(R.id.wait_dialog_toast_title);
		     titleView.setText(titleId);
		 }
	     if(iconId > 0){
		     ImageView iconView = (ImageView)view.findViewById(R.id.wait_dialog_toast_icon);
		     iconView.setImageResource(iconId);
	     }
	     return this;
	 }
	 
	public CustomToastDialog setWaitTitle(int titleId) {
		return setWaitTitle(titleId, -1);
	}
	
	public CustomToastDialog setWaitTitle(String title){
		 if(view == null){
			 return this;
		 }
		 if(title != null && title.length() > 0){
			 TextView titleView = (TextView)view.findViewById(R.id.wait_dialog_toast_title);
		     titleView.setText(title);
		 }
	     return this;
	 }
	 
	 /**
	  * 显示
	  */
	 public void showMe(){
		if (toast != null) {
			toast.show();
		}
	 }
}
