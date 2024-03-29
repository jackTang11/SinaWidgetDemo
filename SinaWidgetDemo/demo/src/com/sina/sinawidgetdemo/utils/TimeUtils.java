package com.sina.sinawidgetdemo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.util.Log;
/**
 * 跟时间相关的工具类
 */
public class TimeUtils {
	/**
	 * 获取现在时间
	 * @return
	 */
	public static String getDate() {
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(Calendar.getInstance().getTimeZone());   
        String date = formatter.format(new Date()); 
        return date;
	}
	/**
	 * 获取现在时间
	 * @return
	 */
	public static String getAllDate() {
	    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(Calendar.getInstance().getTimeZone());   
        String date = formatter.format(new Date()); 
        return date;
	}
	
	/**
	 * 时间戳转换成时间
	 * @param time
	 * @return
	 */
	public static String allTimeTransDate(Long time){
		long curr_time = System.currentTimeMillis();
		Date date = new Date(time);
		Date curr_date = new Date(curr_time);
		
		int year = date.getYear();
		int curr_year = curr_date.getYear();
		DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String sd = sdf.format(time);  
		return sd;
	}
	
	/**
	 * 时间戳转换成时间
	 * @param time
	 * @return
	 */
	public static String timeTransDate(Long time){
		long curr_time = System.currentTimeMillis();
		Date date = new Date(time);
		Date curr_date = new Date(curr_time);
		
		int year = date.getYear();
		int curr_year = curr_date.getYear();
		DateFormat sdf=new SimpleDateFormat("MM-dd");
		if(year < curr_year){
			sdf=new SimpleDateFormat("yyyy-MM-dd");  
		}
		String sd = sdf.format(time);  
		return sd;
	}
	/**
	 * 时间戳转换成时间:带时分
	 * @param time
	 * @return
	 */
	public static String timeTransDate2(Long time){
		long curr_time = System.currentTimeMillis();
		Date date = new Date(time);
		Date curr_date = new Date(curr_time);
		
		int year = date.getYear();
		int curr_year = curr_date.getYear();
		DateFormat sdf=new SimpleDateFormat("MM月dd日 HH:mm");
		if(year < curr_year){
			sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");  
		}
		String sd = sdf.format(time);  
		return sd;
	}
	
	/**
	 * 日期转换成时间戳
	 * @param date
	 * @return
	 */
	public static long DateTransTime(String date){
		DateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 
		Date time = null;
		try {
			time = format.parse(date);
			return time.getTime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	/**
	 * 新闻列表日期转换方法
	 * @return
	 */
	public static String getNewsDate(long date) {
		String turnResult = "";
		long subTime = System.currentTimeMillis() - date;
		if(subTime<0){
			turnResult = timeTransDate(date);
		}
		else {
			if(subTime < 1000*60){//一分钟内
				turnResult=subTime/(1000)+"秒前";
			}
			else if(subTime < 1000*60*60){//一小时内
				turnResult=subTime/(1000*60)+"分钟前";
			}
			else if(subTime <1000*60*60*24){//一天内
				turnResult=subTime/(1000*60*60)+"小时前";
			}
			else{
				turnResult = timeTransDate(date);
			}
		}
		return turnResult;
	}
	
	public static String getNewsDate(String date){
		return getNewsDate(DateTransTime(date));
	}
	
	/**
	 *  转换成视频的时长
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String timeTransVideoTimes(Long time, boolean showZeroHour){
		DateFormat sdf = null;
		long hourTime = 1000*60*60;
		if(time >= hourTime){
			sdf=new SimpleDateFormat("hh:mm:ss");  
		}
		else{
			if(!showZeroHour){
			    sdf=new SimpleDateFormat("mm:ss");  
			}
			else{
				sdf=new SimpleDateFormat("HH:mm:ss");  
			}
		}
		TimeZone tz = TimeZone.getTimeZone("Asia/Beijing");  
		sdf.setTimeZone(tz); 
		String sd = sdf.format(time);  
		
		return sd;
	}
	
	public static String timeParseVideoTimes(Long time) {
		boolean hasHour = time / 3600000L  > 0;
		String hour = String.valueOf(time / 3600000);
		hour = hour.length() == 1 ? "0" + hour : hour;
		time = time % 3600000;
		String minute = String.valueOf(time / 60000);
		minute = minute.length() == 1 ? "0" + minute : minute;
		time = time % 60000;
		String second = String.valueOf(time / 1000);
		second = second.length() == 1 ? "0" + second : second;
		if (hasHour) {
			return hour + ":" + minute + ":" + second;
		}
		return minute + ":" + second;
	}
	
	public static String generateTime(long time) {
		int totalSeconds = (int) (time / 1000);
		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;

		return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
	}

	/**
	 * 获取刷新时间
	 * @param date
	 * @return
	 */
	public static String getRrefreshTime(long date) {
		String turnResult = "";
		long subTime = System.currentTimeMillis() - date;
		if(date<=0){
			turnResult="从未更新";
		}
		else {
			if(subTime <= 0){
				turnResult = timeTransDate(date);
			}
			else if(subTime <  10*1000 ){
				turnResult = "刚刚";
			}
			else if(subTime < 1000*60){//一分钟内
				turnResult=subTime/(1000)+"秒前";
			}
			else if(subTime < 1000*60*60){//一小时内
				turnResult=subTime/(1000*60)+"分钟前";
			}
			else if(subTime <1000*60*60*24){//一天内
				turnResult=subTime/(1000*60*60)+"小时前";
			}
			else{
				turnResult = timeTransDate(subTime);
			}
		}
		return turnResult;
	}
	
	public static Date getDate(String dateString) {
		SimpleDateFormat df = null;
		Date date = null;
		try {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = df.parse(dateString);
		} catch (Exception e) {
			df = null;
			date = null;
		}
		if (df == null || date == null) {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = df.parse(dateString);
			} catch (Exception e) {
				df = null;
				date = null;
			}
		}
		return date;
	}
	
	public static String parseMonthAndDay(String time) {
		SimpleDateFormat df = null;
		Date date = null;
		try {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = df.parse(time);
		} catch (Exception e) {
			df = null;
			date = null;
		}
		if (date == null) {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = df.parse(time);
			} catch (Exception e) {
				df = null;
				date = null;
			}
		}
		String result = "";
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);

			result = new String(month + "月" + day + "日");
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}
	
	public static boolean sameDayWith(Date toBeCompare, String timeToParse) {
		SimpleDateFormat df = null;
		Date date = null;
		try {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = df.parse(timeToParse);
		} catch (Exception e) {
			df = null;
			date = null;
		}
		if (date == null) {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = df.parse(timeToParse);
			} catch (Exception e) {
				df = null;
				date = null;
			}
		}
		boolean result = false;
		int year;
		int month;
		int day;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			day = cal.get(Calendar.DAY_OF_MONTH);

			cal.setTime(toBeCompare);
			if (year == cal.get(Calendar.YEAR)) {
				if (month == cal.get(Calendar.MONTH) + 1) {
					if (day == cal.get(Calendar.DAY_OF_MONTH)) {
						result = true;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public static Date getYesterDayOf(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	public static String getElipseTime(String input) {
		Date date = getDate(input);
		if (date == null) {
			return null;
		}
		String result = null;
		try {
			Date current = new Date();
			long interval = date.getTime() - current.getTime();
			if (interval <= 0) {
				return null;
			}
			long howmanymonth = interval / (30 * 24 * 60 * 60 * 1000);
			long howmanydays = interval / (24 * 60 * 60 * 1000);
			long howmanyhours = interval / (60 * 60 * 1000);
			long howmanyminutes = interval / (60 * 1000);
			if (howmanymonth > 0) {
				return null;
			}
			if (howmanyhours > 72) {
				result = howmanydays + "天";
			} else if (howmanyhours > 0) {
				result = howmanyhours + "小时";
			}
			if (howmanyminutes > 0) {
				result = howmanyminutes + "分钟";
			}
			
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	
	public static String getTimeLimit(String time) {
		SimpleDateFormat df = null;
		Date date = null;
		try {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = df.parse(time);
		} catch (Exception e) {
			df = null;
			date = null;
		}
		if (date == null) {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = df.parse(time);
			} catch (Exception e) {
				df = null;
				date = null;
			}
		}
		String result = null;
		StringBuilder sb;
		int year;
		int month;
		int day;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			day = cal.get(Calendar.DAY_OF_MONTH);
			sb = new StringBuilder().append(year).append("-").append(month)
					.append("-").append(day);
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}
	
	public static String getTimeLimit(String from, String to) {
		SimpleDateFormat df = null;
		Date date = null;
		try {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			date = df.parse(from);
		} catch (Exception e) {
			df = null;
			date = null;
		}
		if (date == null) {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				date = df.parse(from);
			} catch (Exception e) {
				df = null;
				date = null;
			}
		}
		String result = null;
		StringBuilder sbFrom;
		StringBuilder sbTo;
		int year;
		int month;
		int day;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			day = cal.get(Calendar.DAY_OF_MONTH);
			sbFrom = new StringBuilder().append(year).append("-").append(month)
					.append("-").append(day);
			
			date = df.parse(to);
			cal.setTime(date);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			day = cal.get(Calendar.DAY_OF_MONTH);
			sbTo = new StringBuilder().append(year).append("-").append(month)
					.append("-").append(day);
			if (sbFrom != null && sbTo != null && sbFrom.length() > 0
					&& sbTo.length() > 0) {
				result = sbFrom.toString() + "至" + sbTo.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}
	
	/**评论时间*/
	public static String getCommentDate(String date){
		return getCommentDate(DateTransTime(date));
		
		
	}
	public static String getCommentDate(long date){
		
		String turnResult = "";
		long subTime = System.currentTimeMillis() - date;
		if(subTime<0){
			turnResult = timeTransDate2(date);
		}
		else {
			if(subTime <  10*1000 ){
				turnResult = "刚刚";
			}else if(subTime < 1000*60){//一分钟内
				turnResult=subTime/(1000)+"秒前";
			}
			else if(subTime < 1000*60*60){//一小时内
				turnResult=subTime/(1000*60)+"分钟前";
			}
			else if(subTime <1000*60*60*24){//一天内
				turnResult=subTime/(1000*60*60)+"小时前";
			}
			else{
				turnResult = timeTransDate2(date);
			}
		}
		return turnResult;
	}
	
	
}
