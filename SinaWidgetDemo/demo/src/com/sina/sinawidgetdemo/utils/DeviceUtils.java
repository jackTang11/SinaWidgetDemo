package com.sina.sinawidgetdemo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 跟手机硬件参数相关的工具类
 */
public class DeviceUtils {
	/**
	 * 描述build的标签
	 */
	public static String getDeviceTagS() {
		return Build.TAGS;
	}

	/**
	 * 修订版本列表
	 */
	public static String getDeviceID() {
		return Build.ID;
	}

	/**
	 * 获取手机型号
	 */
	public static String getDeviceModel() {
		return Build.MODEL;
	}

	/**
	 * 获取手机品牌
	 */
	public static String getDeviceBrand() {
		return Build.BRAND;
	}

	/**
	 * 获取手机名
	 */
	public static String getDeviceName() {
		return Build.DEVICE;
	}

	/**
	 * 获取手机制造商
	 */
	public static String getDeviceProduct() {
		return Build.PRODUCT;
	}

	/**
	 * 获取手机主板
	 */
	public static String getDeviceBoard() {
		return Build.BOARD;
	}

	/**
	 * 获取手机显示屏参数
	 */
	public static String getDeviceDisplay() {
		return Build.DISPLAY;
	}

	/**
	 * 获取手机硬件名称
	 */
	public static String getDeviceFingerprint() {
		return Build.FINGERPRINT;
	}

	/**
	 * 获取手机硬件制造商
	 */
	public static String getDeviceManufacturer() {
		return Build.MANUFACTURER;
	}

	/**
	 * 获取手机cpu指令集
	 */
	public static String getDeviceCpu_abi() {
		return Build.CPU_ABI;
	}

	/**
	 * 获取手机当前开发代号
	 */
	public static String getVersionCodename() {
		return Build.VERSION.CODENAME;
	}

	/**
	 * 获取手机源码控制版本号
	 */
	public static String getVersionInc() {
		return Build.VERSION.INCREMENTAL;
	}

	/**
	 * 获取手机版本字符串
	 */
	public static String getVersionRelease() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取手机版本号(android_sdk版本) 配合：android.os.Build.VERSION_CODES.BASE使用
	 */
	public static int getVersionSDK() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * 获取IMEI号:手机串号
	 */
	public static String getIMEI(Activity context) {
		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		return mTm.getDeviceId();
	}

	/**
	 * 获取IESI号 ,唯一识别SIM卡
	 */
	public static String getIESI(Activity context) {
		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		return mTm.getSubscriberId();
	}

	/**
	 * 获取手机号码 手机号码 运营商没有下发的话获取不到
	 */
	public static String getPhoneNum(Activity context) {
		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		String numer = mTm.getLine1Number();
		return "手机号码：" + numer;
	}

	/**
	 * 获取手机MAC地址 只有手机开启wifi才能获取到mac地址
	 */
	public static String getMacAddress(Context context) {
		String result = "";
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		result = wifiInfo.getMacAddress();
		return "手机macAdd:" + result;
	}

	/**
	 * 手机CPU信息
	 */
	public static String getCpuInfo(Context context) {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" }; // 1-cpu型号 //2-cpu频率
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return "cpu型号:" + cpuInfo[0] + "\ncpu频率:" + cpuInfo[1];
	}

	/**
	 * 获取android当前可用内存大小
	 */
	public static String getAvailMemory(Context context) {// 获取android当前可用内存大小

		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		// mi.availMem; 当前系统的可用内存(b)
		return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
	}

	/**
	 * 获得系统总内存(在SUNSANG上有问题？？？)
	 */
	public static String getTotalMemory(Context context) {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;

		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString) {
				Log.i(str2, num + "\t");
			}
			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
		}
		return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}

	/**
	 * 屏幕像素宽高
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenWH(Context context) {
		int[] wh = new int[2];
		DisplayMetrics metrics = context.getApplicationContext().getResources()
				.getDisplayMetrics();
		wh[0] = metrics.widthPixels;
		wh[1] = metrics.heightPixels;
		return wh;
	}

	public static String getLocale() {
		Locale locale = Locale.getDefault();
		if (locale != null) {
			String lo = locale.getLanguage();
			if (lo != null) {
				return lo.toLowerCase();
			}
		}
		return "en";
	}



	@SuppressLint("NewApi")
	public static String getIdentifiers(Context ctx) {
		StringBuilder sb = new StringBuilder();
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO)
			sb.append(getPair("serial", Build.SERIAL));
		else
			sb.append(getPair("serial", "No Serial"));
		sb.append(getPair("android_id", Settings.Secure.getString(
				ctx.getContentResolver(), Settings.Secure.ANDROID_ID)));
		TelephonyManager tel = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		sb.append(getPair("sim_country_iso", tel.getSimCountryIso()));
		sb.append(getPair("network_operator_name", tel.getNetworkOperatorName()));
		sb.append(getPair("unique_id", EncryptUtils.md5(sb.toString())));
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		sb.append(getPair(
				"network_type",
				cm.getActiveNetworkInfo() == null ? "-1" : String.valueOf(cm
						.getActiveNetworkInfo().getType())));
		return sb.toString();
	}



	private static String getPair(String key, String value) {
		key = key == null ? "" : key.trim();
		value = value == null ? "" : value.trim();
		return "&" + key + "=" + value;
	}

	/**
	 * 获得SD卡总大小
	 * 
	 * @return
	 */
	public static long getSDTotalSize(Context con) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		// return Formatter.formatFileSize(con, blockSize * totalBlocks);
		return blockSize * totalBlocks;
	}

	/**
	 * 获得sd卡剩余容量，即可用大小
	 * 
	 * @return
	 */
	public static long getSDAvailableSize(Context con) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		// return Formatter.formatFileSize(con, blockSize * availableBlocks);
		return blockSize * availableBlocks;
	}

	/**
	 * 获得机身内存总大小
	 * 
	 * @return
	 */
	public static String getRomTotalSize(Context con) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return Formatter.formatFileSize(con, blockSize * totalBlocks);
	}

	/**
	 * 获得机身可用内存
	 * 
	 * @return
	 */
	public static String getRomAvailableSize(Context con) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(con, blockSize * availableBlocks);
	}

	/**
	 * 检查SDCard是否存在
	 * 
	 * @return
	 */
	public static boolean isSdcardMounted() {
		String info = Environment.getExternalStorageState();
		if (info != null) {
			if (info.equals("mounted")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测SIM卡
	 */
	public static boolean readSIMCard2(Context ctx) {
		TelephonyManager mTelephonyManager = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		switch (mTelephonyManager.getSimState()) {
		case TelephonyManager.SIM_STATE_ABSENT:
			return false;
		case TelephonyManager.SIM_STATE_READY:
			return true;
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			return false;
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			return false;
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			return false;
		case TelephonyManager.SIM_STATE_UNKNOWN:
			return false;
		}
		return false;
	}

}
