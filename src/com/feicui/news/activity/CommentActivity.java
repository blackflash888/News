package com.feicui.news.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.feicui.news.activity.base.BaseActivity;
import com.feicui.news.adapter.CommentsAdapter;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.NewsParserUtil;
import com.feicui.news.common.ParserUser;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.CommentsManager;
import com.feicui.news.model.entity.Comment;
import com.feicui.news.model.entity.Entity;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.Register;
import com.feicui.news.view.XListView;
import com.feicui.news.view.XListView.IXListViewListener;

public class CommentActivity extends BaseActivity {

	private ImageView imageView, send;
	private static XListView xlistview;
	private CommentsAdapter commentsAdapter;
	private EditText editText;
	private News news;
	private int curId;
	private SharedPreferences sharedPreferences;
	private SystemUtil systemUtil;
	private Comment comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		news = (News) getIntent().getSerializableExtra("news");
		initView();
		setListener();
	}

	// 加载下面的XX条数据
	protected void loadPreComment() {
		comment = commentsAdapter.getItem(xlistview
				.getLastVisiblePosition()-1);
		if (SystemUtil.getInstance(this).isNetConn()) {
			CommentsManager.loadComments(CommentActivity.this,
					CommonUtil.VERSION_CODE + "", listener, errorListener,
					news.nid, 2, comment.getCid());
		}
	}

	// 请求最新的评论
	protected void loadNextComment() {
		if (SystemUtil.getInstance(this).isNetConn()) {
			CommentsManager.loadComments(this, CommonUtil.VERSION_CODE + "",
					listener, errorListener, news.nid, 1, curId);
		}
	}

	private Listener<String> listener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			LogUtil.d("Commentresponse", response);
			ArrayList<Comment> comments = NewsParserUtil.getComments(response);
			if (comments == null || comments.size() < 1) {
				return;
			}
			commentsAdapter.addDataToAdapter(comments);
			commentsAdapter.notifyDataSetChanged();
		}
	};

	private ErrorListener errorListener = new ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(CommentActivity.this, "服务器连接错误！", Toast.LENGTH_SHORT)
					.show();
		}
	};

	public class MyTextError implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			ToastUtil.show(CommentActivity.this, "发送评论失败！", Toast.LENGTH_SHORT);
		}
	}
	
	public class MyTextResponseHandler implements Listener<String>{

		@Override
		public void onResponse(String response) {
			//解析
			Entity<Register> entity = ParserUser.parserRegist(response);
			if(entity.getStatus() == 0){
				Register register =  entity.getData();
				String info = "";
				int result = register.getResult();
				if(result == 0){
					info = "评论成功";
					editText.setText(null); 
					editText.clearFocus();
				}else{
					info = "评论失败";
					}
				ToastUtil.show(CommentActivity.this, info, Toast.LENGTH_SHORT);
				}
			}
		}
	
	@Override
	protected void initView() {
		imageView = (ImageView) findViewById(R.id.comment_iv);
		xlistview = (XListView) findViewById(R.id.xlistview);
		commentsAdapter = new CommentsAdapter(this, xlistview);
		xlistview.setAdapter(commentsAdapter);
		systemUtil = SystemUtil.getInstance(this);
		new Thread() {
			public void run() {

				CommentsManager.loadComments(CommentActivity.this, "1",
						listener, errorListener, 1, 1, 10);
			};
		}.start();
		xlistview.setPullLoadEnable(true);
		xlistview.setPullRefreshEnable(true);

		editText = (EditText) findViewById(R.id.et);
		send = (ImageView) findViewById(R.id.send);
		curId = commentsAdapter.getCount() <= 0 ? 0 : commentsAdapter
				.getItem(0).getCid();
	}

	@Override
	protected void setListener() {
		// 监听返回按钮
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommentActivity.this.finish();
			}
		});
		// 发送评论按钮的监听
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String ccontent = editText.getText().toString();
				if (ccontent.length() == 0&&ccontent == null || ccontent.equals("")) {
					ToastUtil.show(CommentActivity.this, "要先写评论内容",
							Toast.LENGTH_SHORT);
				} else {
					Register register = SharedPreferencesUtil
							.getRegister(CommentActivity.this);
					String token = register.getToken();
					String imei = SystemUtil.getIMEI(CommentActivity.this);
					sharedPreferences = getSharedPreferences("user",
							Context.MODE_PRIVATE);
					String username = sharedPreferences.getString("username", null);
					LogUtil.d("username", username);
					String password = sharedPreferences.getString("password",null);
//					send.setImageDrawable(getResources().getDrawable(
//							R.drawable.bg_talk_send_2));
					if (username != null && password != null) {//发送成功
						CommentsManager.sendCommnet(CommentActivity.this,
								1, new MyTextResponseHandler(),
								new MyTextError(), token, imei, ccontent);
					} else {
						ToastUtil.show(CommentActivity.this, "请先登录", 0);
					}
				}
			}
		});

		// 上拉加载和下拉刷新的监听
		xlistview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				ToastUtil.show(CommentActivity.this, "刷新完成", Toast.LENGTH_LONG);
				// 停止刷新
				xlistview.stopRefresh();
				// 设置getTime()函数返回的下拉刷新的更新时间
				xlistview.setRefreshTime(CommonUtil.getTime());
//				commentsAdapter.clearAllData();
//				loadNextComment();
			}

			@Override
			public void onLoadMore() {
//				loadPreComment();
				ToastUtil.show(CommentActivity.this, "加载完成", Toast.LENGTH_LONG);
				// 停止加载
				xlistview.stopLoadMore();

			}
		});

	}
}
