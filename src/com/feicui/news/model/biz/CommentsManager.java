package com.feicui.news.model.biz;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.feicui.news.activity.CommentActivity;
import com.feicui.news.common.CommonUtil;

import edu.feicui.news.volley.VolleySingleton;
import android.content.Context;

public class CommentsManager {

	/*
	 * 请求评论
	 * 
	 * @paramver 版本
	 * 
	 * @paramargs 顺序要求如下。包含nid (新闻id) dir ( 刷新方向，1 表示：上拉刷新，即加载更多的xx条， 2
	 * 表示下拉刷新，请求最新的数据) cid评论id
	 * cmt?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容发送评论
	 */
	// 加载评论请求
	public static void loadComments(Context context, String ver,
			Listener<String> listener, ErrorListener errorListener, int... args) {
		String url = CommonUtil.PATH1 + "cmt_list?ver=" + ver + "&nid="
				+ args[0] + "&dir=" + args[1] + "&cid=" + args[2] + "&type="
				+ 1 + "&stamp=" + "20140707";
		// 下载
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue rq = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, url, listener,
				errorListener);
		rq.add(sr);
	}

	// 发送评论请求
	public static void sendCommnet(Context context, int nid,
			CommentActivity.MyTextResponseHandler handler,
			ErrorListener errorListener, String... args) {
		String url = CommonUtil.PATH1 + "cmt_commit?nid=" + nid + "&ver=1"
				+ "&token=" + args[0] + "&imei=" + args[1] + "&ctx=" + args[2];
		// 下载
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, url, handler,
				errorListener);
		queue.add(sr);
	}

}
