package com.feicui.news.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.widget.EditText;

/**
 * 公共类，存储常用的访问路径
 */
public final class CommonUtil {
	public static boolean isEmail(String email) {
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher("dffdfdf@qq.com");
		return matcher.matches();
	}

	// 记录下拉刷新的更新时间
	@SuppressLint("SimpleDateFormat")
	public static String getTime() {
		long timeNumber = System.currentTimeMillis();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(timeNumber);
		String time = format.format(date);
		return time;
	}
	
	/** 服务器访问地址 */
	public static final String PATH1 = "http://118.244.212.82:9092/newsClient/";
	public static final String PATH2 = "news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
	/** 联网的ip */
	public static String NETIP = "118.244.212.82";
	/** 版本号 */
	public static final int VERSION_CODE = 1;
	/** 联网的路径 */
	public static String NETPATH = "http://" + NETIP + ":9092/newsClient";
	/** SharedPreferences保存用户名键 */
	public static final String SHARE_USER_NAME = "userName";
	/** SharedPreferences保存用户名密码 */
	public static final String SHARE_USER_PWD = "userPwd";
	/** SharedPreferences保存是否第一次登陆 */
	public static final String SHARE_IS_FIRST_RUN = "isFirstRun";
	/** SharedPreferences存储路径 */
	public static final String SHAREPATH = "news_share";
}
