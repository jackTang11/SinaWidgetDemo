package com.sina.sinawidgetdemo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class PreferencesUtils {
	
	/**
	 * 写入long数据
	 * @param con
	 * @param fileName
	 * @param dataName
	 * @param data
	 */
	public static void writeLong(Context con,String fileName,String dataName,long data){
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		Editor editor=sp.edit();
 		editor.putLong(dataName, data);
     	editor.commit();
 	}
	/**
	 * 写入string 数据
	 * @param con
	 * @param fileName
	 * @param dataName
	 * @param data
	 */
	public static void writeString(Context con,String fileName,String dataName,String data){
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		Editor editor=sp.edit();
 		editor.putString(dataName, data);
     	editor.commit();
 	}
	/**
	 * 写入int数据
	 * @param con
	 * @param fileName
	 * @param dataName
	 * @param data
	 */
	public static void writeInt(Context con,String fileName,String dataName,int data){
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		Editor editor=sp.edit();
 		editor.putInt(dataName, data);
     	editor.commit();
 	}
	
	/**
	 * 写入boolean数据
	 * @param con
	 * @param fileName
	 * @param dataName
	 * @param data
	 */
	public static void writeBoolean(Context con,String fileName,String dataName,Boolean data){
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		Editor editor=sp.edit();
 		editor.putBoolean(dataName, data);
     	editor.commit();
	}
	
	/**
	 * 得到boolean数据
	 * @param con
	 * @param fileName
	 * @param dataName
	 * @param defaultData
	 * @return
	 */
	public static Boolean getBoolean(Context con,String fileName,String dataName,Boolean defaultData){
 		Boolean data = defaultData;
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		data = sp.getBoolean(dataName, defaultData);
 		return data;
 	}
	
	/**
	 * 得到int数据
	 * @param con
	 * @param fileName
	 * @param dataName
	 * @param defaultData
	 * @return
	 */
	public static int getInt(Context con,String fileName,String dataName,int defaultData){
 		int data = defaultData;
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		data = sp.getInt(dataName, defaultData);
 		return data;
 	}
	/**
	 * 得到String数据
	 * @param con
	 * @param fileName
	 * @param dataName
	 * @param defaultData
	 * @return
	 */
	public static String getString(Context con,String fileName,String dataName,String defaultData){
		String data = defaultData;
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		data = sp.getString(dataName, defaultData);
 		return data;
 	}
	/**
	 * 得到long数据
	 * @param con
	 * @param fileName
	 * @param dataName
	 * @param defaultData
	 * @return
	 */
	public static long getLong(Context con,String fileName,String dataName,long defaultData){
		long data = defaultData;
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		data = sp.getLong(dataName, defaultData);
 		return data;
 	}
	
	/**
	 * 得到全部数据
	 * @param con
	 * @param fileName
	 * @return
	 */
	public static Map getAll(Context con,String fileName){
		Map data = null;
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		data = sp.getAll();
 		return data;
 	}
	
	/**
	 * 删除指定key数据
	 * @param con
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static void deleteDataForKey(Context con,String fileName,String key){
 		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		SharedPreferences.Editor editor = sp.edit();
 		editor.remove(key);
 		editor.commit();
 	}
	
	/**
	 * 删除所有数据
	 * @param con
	 * @param fileName
	 */
	public static void deleteAll(Context con,String fileName){
		SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
 		SharedPreferences.Editor editor = sp.edit();
 		editor.clear();
 		editor.commit();
	}
	/**
	 * 储存对象
	 * @param con
	 * @param fileName
	 * @param name
	 * @param object
	 */
	 public static void saveObject(Context con,String fileName, String key,Object object) {  
	        SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE);
        try {  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            ObjectOutputStream oos = new ObjectOutputStream(baos);  
            oos.writeObject(object);
  
            String personBase64 = new String(Base64.encode(baos.toByteArray(),Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();  
            editor.putString(key, personBase64);  
            editor.commit();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
	/**
	 * 获得对象
	 * @param con
	 * @param fileName
	 * @param name
	 * @return
	 */
	 public static Object getObjectInfo(Context con,String fileName, String key) {  
		Object object = null;
        try {  
        	SharedPreferences sp = con.getSharedPreferences(fileName,Context.MODE_PRIVATE); 
            String personBase64 = sp.getString(key, "");  
            byte[] base64Bytes = Base64.decode(personBase64.getBytes(),Base64.DEFAULT);  
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);  
            ObjectInputStream ois = new ObjectInputStream(bais);  
            object = ois.readObject();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	      
        return object;
	 }  
}
