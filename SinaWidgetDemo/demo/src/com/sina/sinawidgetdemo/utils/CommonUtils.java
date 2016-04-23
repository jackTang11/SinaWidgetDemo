package com.sina.sinawidgetdemo.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.http.util.EncodingUtils;
import org.xmlpull.v1.XmlPullParser;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.telephony.SmsManager;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sina.sinawidgetdemo.constant.PreferencesConstant;
/**
 * 其他工具类
 */
public class CommonUtils {
	/**
	 * 跳转到浏览器
	 * 
	 * @param url
	 */
	public static void goToBrowser(Context con, String url) {
		Intent in = new Intent();
		in.setAction("android.intent.action.VIEW");
		Uri url_content = Uri.parse(url);
		in.setData(url_content);
		// in.setClassName("com.android.browser",
		// "com.android.browser.BrowserActivity");
		con.startActivity(in);

	}

	/**
	 * 获取微博的appkey
	 * 
	 * @return
	 */
	public static String getAppKey(Context con) {
		String app_key = null;
		try {
			InputStream open = con.getResources().getAssets()
					.open("MyShareSDK.xml");
			XmlPullParser mXmlPull = Xml.newPullParser();
			mXmlPull.setInput(open, "UTF-8");
			int eventCode = mXmlPull.getEventType();

			while (eventCode != XmlPullParser.END_DOCUMENT) {

				switch (eventCode) {
				case XmlPullParser.START_DOCUMENT: // 文档开始事件
					break;

				case XmlPullParser.START_TAG:// 元素开始.
					String name = mXmlPull.getName();
					if (name.equalsIgnoreCase("SinaWeibo")) {
						app_key = mXmlPull.getAttributeValue(null, "AppKey");
					}
					break;

				case XmlPullParser.END_TAG: // 元素结束,

					break;
				default:
					break;
				}
				eventCode = mXmlPull.next();// 进入到一下一个元素.
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return app_key;
	}

	/**
	 * 获取当前版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context)// 获取版本号(内部识别号)
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取当前版本名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersion(Context context)// 获取版本号
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 安装某个apk
	 * 
	 * @param con
	 * @param filePath
	 */
	public static void installApkForPath(Context con, String filePath) {
		if (!TextUtils.isEmpty(filePath) && con != null) {
			Intent intent1 = new Intent(Intent.ACTION_VIEW);
			File file = new File(filePath);
			if (file != null) {
				intent1.setDataAndType(Uri.fromFile(file),
						"application/vnd.android.package-archive");
				intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				con.startActivity(intent1);
			}
		}
	}

	/**
	 * 获取手机安装的应用名称（排除系统自带）
	 */
	public static List<String> getAllAppName(Context context) {
		List<String> packageList = new ArrayList<String>();
		PackageManager mPm = context.getPackageManager();
		List<PackageInfo> packages = mPm.getInstalledPackages(0);
		for (PackageInfo i : packages) {
			ApplicationInfo appInfo = i.applicationInfo;
			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				packageList.add(appInfo.loadLabel(mPm).toString());
			}
		}
		return packageList;
	}

	/**
	 * 获取手机安装的应用包名（排除系统自带）
	 */
	public static List<String> getAllAppPackage(Context context) {
		List<String> packageList = new ArrayList<String>();
		PackageManager mPm = context.getPackageManager();
		List<PackageInfo> packages = mPm.getInstalledPackages(0);
		for (PackageInfo i : packages) {
			ApplicationInfo appInfo = i.applicationInfo;
			if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				packageList.add(appInfo.packageName);
			}
		}
		return packageList;
	}

	/**
	 * 当前API等级是否比指定api等级大
	 * 
	 * @return
	 */
	public static boolean isMoreSdkLv(int lv) {
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);
		if (version > lv) {
			return true;
		}
		return false;
	}

	/**
	 * 获得application数据标签的值
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getAppLicationMetaData(Context context, String name) {
		ApplicationInfo appInfo = null;
		String metaDataStr = null;
		Context appContext = context.getApplicationContext();
		try {
			appInfo = appContext.getPackageManager().getApplicationInfo(
					appContext.getPackageName(), PackageManager.GET_META_DATA);
			if (appInfo != null && appInfo.metaData != null) {
				metaDataStr = String.valueOf(appInfo.metaData.get(name));
			}

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metaDataStr;
	}

	

	/**
	 * @param context
	 *            (MyApplication.getInstance())
	 * @param name
	 * @return
	 */
	public static String getHtmlMouldFromAssets(Context context, String name) {
		String result = "";
		try {
			InputStream in = context.getResources().getAssets().open(name);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 赋值到剪贴板
	 * 
	 * @param context
	 * @param clipText
	 */
	public static void clipText(Context context, String clipText) {
		// 得到剪贴板管理器
		ClipboardManager cmb = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setText(clipText);
	}

	/**
	 * 包名
	 * 
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.packageName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getWebViewBaseUrl(Context con) {
		String baseUrl = "";
		String packageName = getPackageName(con);
		if (packageName == null || packageName.length() == 0) {
			packageName = "com.sina.sinawidgetdemo";
		}
		baseUrl = "sina97973://" + packageName + "/";
		return baseUrl;
	}

	public static Properties getConfigProperties(Context con) {
		Properties properties = new Properties();
		try {
			InputStream is = con.getAssets().open("config.properties");
			properties.load(is);
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * webview执行js方法
	 * 
	 * @param webview
	 * @param content
	 */
	@SuppressLint("NewApi")
	public static void webViewJs(WebView webview, String content) {
		try {
			if (Build.VERSION.SDK_INT < 19) {// 4.4版本之前
				content = "javascript:" + content;
				webview.loadUrl(content);
			} else {
				webview.evaluateJavascript(content, null);
			}
		} catch (Exception e) {

		}
	}

	

	/**
	 * 发短信,尚没验证
	 */
	public static void sendMessage(Context ctx, String address, String text) {
		try {
			SmsManager smsManager = SmsManager.getDefault();
			PendingIntent mPI = PendingIntent.getBroadcast(ctx, 0,
					new Intent(), 0);
			smsManager.sendTextMessage(address, null, text, mPI, null);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	/**
	 * 发短信
	 */
	public static  void sendSMS(Context context,String address, String smsBody){

		Uri smsToUri = Uri.parse("smsto:"+address);
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		intent.putExtra("sms_body", smsBody);
		context.startActivity(intent);
	}

	

	/**
	 * 安装app
	 * 
	 * @param path
	 * @param context
	 * @throws Exception
	 */
	public static void install(String path, Context context) throws Exception {
		if (!path.endsWith(".apk")) {
			return;
		}
		try {
			File file = new File(path);
			if (!file.exists() || file.isDirectory()) {
				return;
			}
			try {
				Process p = Runtime.getRuntime().exec("chmod 705 " + file);
				p.waitFor();
				p.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(android.content.Intent.ACTION_VIEW);

			/* 设置intent的file与MimeType */
			String type = "application/vnd.android.package-archive";
			intent.setDataAndType(Uri.fromFile(file), type);
			context.startActivity(intent);
		} catch (Exception e) {
		}
	}

	/**
	 * 卸载app
	 * 
	 * @param packageName
	 * @param context
	 */
	public static void uninstall(Context context, String packageName) {
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		context.startActivity(uninstallIntent);
	}

	/**
	 * 打开app
	 * 
	 * @param packageName
	 * @param context
	 */
	public static void openApp(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		Intent in = pm.getLaunchIntentForPackage(packageName);
		if (in != null) {
			context.startActivity(in);
		}
	}
	/**
     * 创建目录
     * @return
     */
    public static String getFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
        	file.mkdirs();
            Process p;
            try {
                p = Runtime.getRuntime().exec("chmod 705 " + file);
                p.waitFor();
                p.destroy();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath() + File.separator;
    }
    /**
	 * 清理缓存
	 */
	public static void clearCache(String pPath) {
		File folder = new File(pPath);
		if (folder.exists() && folder.isDirectory()) {
			File[] folders = folder.listFiles();
			for (File file : folders) {
				if (file == null)
					continue;
				if (!file.exists())
					continue;
				if (file.isDirectory()) {
					clearCache(file.getPath());
				} else if (file.isFile()) {
					file.delete();
				}
			}
		}
	}
	private static final String FILE_NAME_RESERVED = "|\\?*<\":>+[]/'";
	public static String getUniqueFileName(String name, String id) {
		StringBuilder sb = new StringBuilder();
		for (Character c : name.toCharArray()) {
			if (FILE_NAME_RESERVED.indexOf(c) == -1) {
				sb.append(c);
			}
		}
		name = sb.toString();
		if (name.length() > 16) {
			name = name.substring(0, 16);
		}
		id = EncryptUtils.md5(id);
		name += id;
		try {
			File f = File.createTempFile(name, null);
			if (f.exists()) {
				f.delete();
				return name;
			}
		} catch (IOException e) {
		}
		return id;
	}

	public static String getCanonical(File f) {
		if (f == null)
			return null;

		try {
			return f.getCanonicalPath();
		} catch (IOException e) {
			return f.getAbsolutePath();
		}
	}

	/**
	 * Get the path for the file:/// only
	 * 
	 * @param uri
	 * @return
	 */
	public static String getPath(String uri) {
		Log.i("FileUtils#getPath(%s)", uri);
		if (TextUtils.isEmpty(uri))
			return null;
		if (uri.startsWith("file://") && uri.length() > 7)
			return Uri.decode(uri.substring(7));
		return Uri.decode(uri);
	}

	public static String getName(String uri) {
		String path = getPath(uri);
		if (path != null)
			return new File(path).getName();
		return null;
	}
	
private static final String TAG = "IOUtils";
	
	public static void closeSilently(Closeable c) {
		if (c == null)
			return;
		try {
			c.close();
		} catch (Throwable t) {
			Log.w(TAG, "fail to close", t);
		}
	}

	public static void closeSilently(ParcelFileDescriptor c) {
		if (c == null)
			return;
		try {
			c.close();
		} catch (Throwable t) {
			Log.w(TAG, "fail to close", t);
		}
	}
	
	public static void closeSilently(Cursor cursor) {
    try {
    	if (cursor != null) cursor.close();
     } catch (Throwable t) {
    	 Log.w(TAG, "fail to close", t);
     }
	 }
	/*public static void deleteDir(File f) {
		if (f.exists() && f.isDirectory()) {
			for (File file : f.listFiles()) {
				if (file.isDirectory())
					deleteDir(file);
				file.delete();
			}
			f.delete();
		}
	}*/
	
	public static String join(Object[] elements, CharSequence separator) {
		return join(Arrays.asList(elements), separator);
	}

	public static String join(Iterable<? extends Object> elements, CharSequence separator) {
		StringBuilder builder = new StringBuilder();

		if (elements != null) {
			Iterator<? extends Object> iter = elements.iterator();
			if (iter.hasNext()) {
				builder.append(String.valueOf(iter.next()));
				while (iter.hasNext()) {
					builder.append(separator).append(String.valueOf(iter.next()));
				}
			}
		}

		return builder.toString();
	}

	public static String fixLastSlash(String str) {
		String res = str == null ? "/" : str.trim() + "/";
		if (res.length() > 2 && res.charAt(res.length() - 2) == '/')
			res = res.substring(0, res.length() - 1);
		return res;
	}

	public static int convertToInt(String str) throws NumberFormatException {
		int s, e;
		for (s = 0; s < str.length(); s++)
			if (Character.isDigit(str.charAt(s)))
				break;
		for (e = str.length(); e > 0; e--)
			if (Character.isDigit(str.charAt(e - 1)))
				break;
		if (e > s) {
			try {
				return Integer.parseInt(str.substring(s, e));
			} catch (NumberFormatException ex) {
				throw new NumberFormatException();
			}
		} else {
			throw new NumberFormatException();
		}
	}

	 /**
	  * 判断设置界面wifi下图片
	  * @param context
	  * @return
	  */
	public static boolean isWifiDownload(Context context) {
		boolean wifi_onof = PreferencesUtils.getBoolean(context,
				PreferencesConstant.SETTING_WIFI,
				PreferencesConstant.SETTING_WIFI, false);
		if (!wifi_onof) {
			return true;
		}
		NetworkInfo info = getActiveNetwork(context);
		if (info != null && info.isAvailable()) {
			int type = info.getType();
			if (type == ConnectivityManager.TYPE_WIFI) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得网络状态信息
	 * @param context
	 * @return
	 */
	public static NetworkInfo getActiveNetwork(Context context) {
		if (context == null)
			return null;
		ConnectivityManager mConnMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnMgr == null)
			return null;
		NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo();
		return aActiveInfo;
	}
	
	/**
	 * 图片模糊
	 * 
	 * @param sentBitmap
	 * @param radius
	 * @return
	 */
	public static Bitmap fastblur(Bitmap sentBitmap, int radius) {

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
						| (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		bitmap.setPixels(pix, 0, w, 0, 0, w, h);

		return (bitmap);
	}
	
	/** 获取图像的宽高**/
	public static int[] getImageWH(String path) {
     int[] wh = {-1, -1};
	   if (path == null) {
	   	   return wh;
	   }
		File file = new File(path);
		if (file.exists() && !file.isDirectory()) {
			try {
	           BitmapFactory.Options options = new BitmapFactory.Options();
	           options.inJustDecodeBounds = true;
	           InputStream is = new FileInputStream(path);
	           BitmapFactory.decodeStream(is, null, options);
	           wh[0] = options.outWidth;
	           wh[1] = options.outHeight;
             }
               catch (Exception e) {

			 }
		  }
		  return wh;
	}
  /**
   * 读取指定大小的图片
   * @param path
   * @param width
   * @param height
   * @return
   */
	public static Bitmap createBitmapByWH(String path, int width,int height) {
	    Bitmap bm = null;
		try {
			if(TextUtils.isEmpty(path)){
				return null;
			}
	       // 获取宽高
		   int[] wh = getImageWH(path);
		   if (wh[0] == -1 || wh[1] == -1) {
		       return null;
		   }
	
	 //读取图片
	       BitmapFactory.Options options = new BitmapFactory.Options();
	       options.inSampleSize = Math.max(wh[0]/width, wh[1]/height);
		   options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		   options.inJustDecodeBounds = false;
		   InputStream is = new FileInputStream(path);
		       	bm = BitmapFactory.decodeStream(is, null, options);
		 }
		 catch(Exception e){
			 
		 }
         
		return bm;
	}
	
	/**
	 * WARNNING: Log only for Class Type no check!
	 * @param obj
	 * @return obj.toString()
	 */
	@Deprecated
	public static String getObjectString(Object obj) {
		if (obj == null) {
			return null;
		}
		String objString = "";
		try {
			objString = JSON.toJSONString(obj);
		} catch (Exception e) {
			objString = "";
		}
		return objString;
	}
	
	/**
	 * WARNNING: Log only for Class Type no check!
	 * @param obj
	 * @return obj.toString()
	 */
	@Deprecated
	public static String getObjectString(Collection<Object> objlist) {
		if (objlist == null) {
			return null;
		}
		String objString = "";
		try {
			objString = JSONArray.toJSONString(objlist);
		} catch (Exception e) {
			objString = "";
		}
		return objString;
	}
	
	public static <T extends Object> boolean compare(T left, T right,
			Class<T> classOfT) {
		if (left == null && right == null) {
			return true;
		}
		if (left == null) {
			return false;
		}
		if (right == null) {
			return false;
		}
		String leftString = "";
		String rightString = null;
		try {
			leftString = JSON.toJSONString(left);
			rightString = JSON.toJSONString(right);
		} catch (Exception e) {
			leftString = "";
			rightString = null;
		}
		if (leftString.equalsIgnoreCase(rightString)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static <T extends Object> boolean compare(Collection<T> left, Collection<T> right,
			Class<T> classOfT) {
		if (left == null && right == null) {
			return true;
		}
		if (left == null) {
			return false;
		}
		if (right == null) {
			return false;
		}
		String leftString = "";
		String rightString = null;
		try {
			leftString = JSONArray.toJSONString(left);
			rightString = JSONArray.toJSONString(right);
		} catch (Exception e) {
			leftString = "";
			rightString = null;
		}
		if (leftString.equalsIgnoreCase(rightString)) {
			return true;
		} else {
			return false;
		}
	}
	
}
