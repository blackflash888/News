package com.feicui.news.model.biz;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.feicui.news.activity.FragmentNews.MyErrorHandler;
import com.feicui.news.activity.FragmentNews.MyResponseHandler;
import com.feicui.news.common.CommonUtil;

import edu.feicui.news.volley.VolleySingleton;

public class NewsManager {
	
	private static TelephonyManager tm; 
	
	public static void loadNewsType(MyErrorHandler myErrorHandler, MyResponseHandler myResponseHandler,Context context){
		//应该放入工具类
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		
		//判断数据库中有没有
		if(false){
			//从数据库中加载
			
		}else{
			//下载
			VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
			RequestQueue queue = vs.getRequestQueue();
			StringRequest sr = new StringRequest(Method.GET, CommonUtil.PATH1+"news_sort?ver=1&imei="+imei, myResponseHandler, myErrorHandler);
			queue.add(sr);
		}
		
	}
	
}

