package com.feicui.news;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//��ʼ���������� 
		JPushInterface.setDebugMode(true); 
		JPushInterface.init(this);
	}
}
