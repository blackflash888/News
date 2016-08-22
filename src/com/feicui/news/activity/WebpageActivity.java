package com.feicui.news.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.feicui.news.R;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.NewsDBManager;
import com.feicui.news.model.entity.News;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 新闻内容界面
 * 
 * @author chenaihua
 */
public class WebpageActivity extends Activity {

	private ImageView iv;
	private ImageButton ib;
	private News news;
	private ProgressBar progressBar;
	private WebView webview;
	private PopupWindow popupWindow;
	private TextView saveLocalTv, shareTv;
	private TextView commentTv;// 跟帖
	private ImageView weixin, qq, fiends, sinaweibo;
	private boolean flag = false;
	private WebViewClient viewclient = new WebViewClient() {
		// 在点击请求的是链接时才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，
		// 不跳到浏览器那边。
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			webview.loadUrl(url);
			return true;
		};
	};

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		initPopupWindow();
		setContentView(R.layout.activity_webpage);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		webview = (WebView) findViewById(R.id.webview);
		iv = (ImageView) findViewById(R.id.iv1);
		ib = (ImageButton) findViewById(R.id.ib);
		commentTv = (TextView) findViewById(R.id.comment);
		/** ----------设置跟贴的点击事件------------ **/
		commentTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WebpageActivity.this,
						CommentActivity.class);
				startActivity(intent);
				flag = !flag;
				// 增加点击效果
				if (!flag) {
					commentTv.setTextColor(getResources().getColor(
							R.color.white));
				} else {
					commentTv.setTextColor(Color.BLACK);
				}
			}
		});
		/** ---------------------------------- **/
		/* 设置点击返回上一级页面 */
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (webview.canGoBack()) {
					webview.goBack();
					return;
				} else {
					WebpageActivity.this.finish();
				}
			}
		});
		/** ----------设置收藏的点击事件------------ **/
		ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LogUtil.d("ib.setOnClickListener", "ib.setOnClickListener");
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
				} else if (popupWindow != null) {
					popupWindow.showAsDropDown(ib, 0, -5);
				}
			}
		});
		/** ---------------------- **/
		news = ((News) getIntent().getSerializableExtra("news"));
		/* 默认显示进度条 */
		progressBar.setVisibility(View.VISIBLE);
		// 设置网页打开时缓存模式
		webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webview.setWebChromeClient(new WebChromeClient() {
			// newProgress就是打开网页的进度值
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress >= 100) {
					progressBar.setVisibility(view.GONE);
				}
				progressBar.setProgress(newProgress);
			}
		});

		/* 网页加载完毕要做的事 */
		webview.setWebViewClient(new WebViewClient() {
			/* 如果此函数返回值是true,那么点击链接后网页还是在本身控件中执行,而不会打开一个新的浏览器 */
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 加载网址
				webview.loadUrl(url);
				return true;
			}
		});
		// 加载网页
		webview.loadUrl(news.link);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/* 设置网页返回上一级页面 */
		if ((keyCode == 4) && (this.webview.canGoBack())) {
			webview.goBack();
			return true;
		}
		/* 显示右侧下拉菜单 */
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (popupWindow != null && popupWindow.isShowing()) {
				Log.d("popupWindow", "isShowing");
				popupWindow.dismiss();
			} else if (popupWindow != null) {
				popupWindow.showAsDropDown(ib, 0, -5);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 加入收藏
	public void initPopupWindow() {
		// 显示item_pop_save界面
		View popview = getLayoutInflater()
				.inflate(R.layout.item_pop_save, null);
		// 对弹出窗口进行设置
		popupWindow = new PopupWindow(popview, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.popupwindow));
		saveLocalTv = (TextView) popview.findViewById(R.id.saveLocal);
		saveLocalTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				NewsDBManager manager = new NewsDBManager(WebpageActivity.this);
				// 添加到数据库
				if (manager.saveLoveNews(news)) {
					ToastUtil.show(WebpageActivity.this, "收藏成功！\n在主界面侧滑菜单中查看",
							0);
				} else {
					ToastUtil.show(WebpageActivity.this,
							"已经收藏过这条新闻了！\n在主界面侧滑菜单中查看", 0);
				}
			}
		});
		// 分享的监听
		shareTv = (TextView) popview.findViewById(R.id.share);
		shareTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showShare();
			}
		});
	}

	// 分享函数
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		getString(R.string.app_name);
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(news.title);
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(news.link);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(news.getContent());
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath(news.iconPath);// 确保SDcard下面存在此张图片
		// oks.setImageUrl(news.iconPath);
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(news.content);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");
		// 启动分享GUI
		oks.show(this);
	}
}