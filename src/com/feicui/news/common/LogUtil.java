package com.feicui.news.common;

import android.util.Log;

/**
 * 统一管理Log日志
 * 设置属性为True,则表示打开日志，否则关闭日志
 * @author Administrator
 */
public final class LogUtil {
	public static final String TAG = "新闻随意看";
	//调试日志的开关
	public static boolean isDebug=true;
	/**
	 * 打印Log日志，级别为debug
	 * @param tag
	 * 			打印日志的签名
	 * @param msg
	 * 			打印日志的内容
	 */			
	public static void d(String tag,String msg){
		if(isDebug)
			Log.i(tag, msg);
	}
}
