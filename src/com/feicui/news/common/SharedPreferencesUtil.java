package com.feicui.news.common;

import com.feicui.news.model.entity.Register;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	public static void saveRegister(Context context, Register register) {
		SharedPreferences sp = context.getSharedPreferences("register",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		String token = register.getToken();
		String explain = register.getExplain();
		int result = register.getResult();
		editor.putInt("result", result);
		editor.putString("token", token);
		editor.putString("explain", explain);
		editor.commit();
	}

	public static Register getRegister(Context context) {
		SharedPreferences sp = context.getSharedPreferences("register",
				Context.MODE_PRIVATE);
		int result = sp.getInt("result", 0);
		String token = sp.getString("token", "");
		String explain = sp.getString("explain", "");
		Register res = new Register();
		res.setExplain(explain);
		res.setResult(result);
		res.setToken(token);
		return res;
	}

	// 保存本地头像路径
	public static void savePhoto(Context context, String path) {
		SharedPreferences sp = context.getSharedPreferences("photo",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("path", path);
		editor.commit();
	}

	// 获取本地头像路径
	public static String getPhoto(Context context) {
		SharedPreferences sp = context.getSharedPreferences("photo",
				Context.MODE_PRIVATE);
		String path = sp.getString("path", "");
		return path;
	}

	// 获取头像链接
	public static String getIconLink(Context context) {
		SharedPreferences sp = context.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		return sp.getString("icon", null);
	}

	// 保存头像链接
	public static void saveIconLink(Context context, String link) {
		SharedPreferences sp = context.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("icon", link);
		editor.commit();
	}

	// 退出登录清空用户信息
	public static void clearUserInfo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("username", null);
		editor.putString("token", null);
		editor.putString("password", null);
		editor.putString("icon", null);
		editor.commit();
	}

}
