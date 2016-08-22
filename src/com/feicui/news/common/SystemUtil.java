package com.feicui.news.common;

import com.feicui.news.activity.CommentActivity;
import com.feicui.news.activity.UserActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class SystemUtil {

	private static SystemUtil systemUtil;
	private Context context;
	private static TelephonyManager telManager;
	private ConnectivityManager connManager;
	private LocationManager locationManager;
	private String position;

	public SystemUtil(Context context) {
		this.context = context;
		telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public String imei() {
		return telManager.getDeviceId();
	}

	/**
	 * * 获取手机IMEI 号码 * @return IMEI
	 * @param commentActivity 
	 * 
	 * @param commentActivity
	 */
	public static String getIMEI(Context context) {
		return telManager.getDeviceId();
	}

	public static String getIMEI(UserActivity userActivity) {
		return telManager.getDeviceId();
	}
	
	public static SystemUtil getInstance(Context context) {
		if (systemUtil == null) {
			systemUtil = new SystemUtil(context);
		}
		return systemUtil;
	}

	/** 判断网络是否连接 */
	public boolean isNetConn() {
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}

	/** 获取sim卡的类型 */
	public String simType() {
		String simOperator = telManager.getSimOperator();
		String type = "";
		if ("46000".equals(simOperator)) {
			type = "移动";
		} else if ("46002".equals(simOperator)) {
			type = "移动";
		} else if ("46001".equals(simOperator)) {
			type = "联通";
		} else if ("46003".equals(simOperator)) {
			type = "电信";
		}
		return type;
	}

	public String getPosition() {
		return this.position;
	}
}
