package com.feicui.news.common;

import android.util.Log;

/**
 * ͳһ����Log��־
 * ��������ΪTrue,���ʾ����־������ر���־
 * @author Administrator
 */
public final class LogUtil {
	public static final String TAG = "�������⿴";
	//������־�Ŀ���
	public static boolean isDebug=true;
	/**
	 * ��ӡLog��־������Ϊdebug
	 * @param tag
	 * 			��ӡ��־��ǩ��
	 * @param msg
	 * 			��ӡ��־������
	 */			
	public static void d(String tag,String msg){
		if(isDebug)
			Log.i(tag, msg);
	}
}
