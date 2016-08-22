package com.feicui.news.model.biz;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.feicui.news.activity.FragmentRegist.MyTextError;
import com.feicui.news.activity.FragmentRegist.MyTextResponseHandler;
import com.feicui.news.activity.FragmentRight;
import com.feicui.news.activity.UserActivity;
import com.feicui.news.common.CommonUtil;

import edu.feicui.news.volley.VolleySingleton;
import edu.feicui.newshome.model.httpclient.AsyncHttpClient;
import edu.feicui.newshome.model.httpclient.RequestParams;
import edu.feicui.newshome.model.httpclient.ResponseHandlerInterface;

/**
 * chenaihau
 * 
 * @author Administrator
 * 
 */
// �����û�ע��,��¼,��������Ȳ���
public class UserManager {

	private static UserManager userManager;

	private UserManager() {
	}

	public static UserManager getInstance() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		return userManager;
	}

	// ����ע������
	public void regist(Context context, MyTextResponseHandler handler,
			MyTextError error, String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		// StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
		// + "user_register?ver=1&uid=" + s[0] + "&email=" + s[1]
		// + "&pwd=" + s[2], handler, error);
		// vs.addToRequestQueue(sr);
		StringRequest sr = new StringRequest(Method.GET, CommonUtil.PATH1
				+ "user_register?ver=1&uid=" + s[1] + "&email=" + s[0]
				+ "&pwd=" + s[2], handler, error);
		vs.addToRequestQueue(sr);
	}

	// ���͵�¼����
	public void login(
			Context context,
			com.feicui.news.activity.FragmentLogin.MyTextResponseHandler handler,
			com.feicui.news.activity.FragmentLogin.MyTextError error,
			String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET,
				CommonUtil.PATH1 + "user_login?ver=1&uid=" + s[0] + "&pwd="
						+ s[1] + "&device=0", handler, error);
		vs.addToRequestQueue(sr);
	}

	// ��ȡ�û�����
	// user_home?ver=�汾��&imei=�ֻ���ʶ��& token =�û�����
	public void getUserData(Context context,
			UserActivity.MyTextResponseHandler handler,
			UserActivity.MyTextError error, String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, CommonUtil.PATH1
				+ "user_home?ver=1&imei=" + s[0] + "&token=" + s[1], handler,
				error);
		vs.addToRequestQueue(sr);
	}

	// �ϴ�ͷ�񵽷�����
	public void uploadIcon(Context context, String token, File portrait,
			ResponseHandlerInterface in) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams param = new RequestParams();
		param.put("token", token);
		try {
			param.put("portrait", portrait);
			client.post(context, CommonUtil.PATH1 + "user_image", param, in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// �Ҳ˵����͵�¼����
	public void loginRight(Context context,
			FragmentRight.MyTextResponseHandler1 handler,
			FragmentRight.MyTextError1 error, String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET,
				CommonUtil.PATH1 + "user_login?ver=1&uid=" + s[0] + "&pwd="
						+ s[1] + "&device=0", handler, error);
		vs.addToRequestQueue(sr);
	}

	// �����û���Ϣ����
	public void userIcon(Context context,
			UserActivity.MyTextResponseHandler1 handler, ErrorListener error,
			String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, CommonUtil.PATH1
				+ "user_home?ver=1&imei=" + s[0] + "&token=" + s[1], handler,
				error);
		vs.addToRequestQueue(sr);
	}

	// �����û���Ϣ����
	public void userRight(Context context,
			FragmentRight.MyTextResponseHandler handler, ErrorListener error,
			String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
				+ "user_home?ver=1&imei=" + s[0] + "&token=" + s[1], handler,
				error);
		vs.addToRequestQueue(sr);
	}

}
