package com.feicui.news.common;

import android.content.Context;
import android.widget.Toast;

/**
 * 打印短消息的工具类,Toast对象是单列
 * @author Administrator
 */

public final class ToastUtil {

	// 为了调用show函数的时候Toast对象不要每次都创建,只需要一个对象即可
	private static Toast toast;

	/**
	 * 打印短消息
	 * @param context	上下文
	 * @param text	文字内容
	 * @param duration	选择消息持续时间
	 */
	
	public static void show(Context context, String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		}
		// 修改内容
		toast.setText(text);
		toast.setDuration(duration);
		// 显示内容
		toast.show();
	}
}
