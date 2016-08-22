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
		//Ӧ�÷��빤����
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		
		//�ж����ݿ�����û��
		if(false){
			//�����ݿ��м���
			
		}else{
			//����
			VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
			RequestQueue queue = vs.getRequestQueue();
			StringRequest sr = new StringRequest(Method.GET, CommonUtil.PATH1+"news_sort?ver=1&imei="+imei, myResponseHandler, myErrorHandler);
			queue.add(sr);
		}
		
	}
	
}

