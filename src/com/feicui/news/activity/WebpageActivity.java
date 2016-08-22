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
 * �������ݽ���
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
	private TextView commentTv;// ����
	private ImageView weixin, qq, fiends, sinaweibo;
	private boolean flag = false;
	private WebViewClient viewclient = new WebViewClient() {
		// �ڵ�������������ʱ�Ż���ã���д�˷�������true���������ҳ��������ӻ����ڵ�ǰ��webview����ת��
		// ������������Ǳߡ�
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
		/** ----------���ø����ĵ���¼�------------ **/
		commentTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WebpageActivity.this,
						CommentActivity.class);
				startActivity(intent);
				flag = !flag;
				// ���ӵ��Ч��
				if (!flag) {
					commentTv.setTextColor(getResources().getColor(
							R.color.white));
				} else {
					commentTv.setTextColor(Color.BLACK);
				}
			}
		});
		/** ---------------------------------- **/
		/* ���õ��������һ��ҳ�� */
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
		/** ----------�����ղصĵ���¼�------------ **/
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
		/* Ĭ����ʾ������ */
		progressBar.setVisibility(View.VISIBLE);
		// ������ҳ��ʱ����ģʽ
		webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webview.setWebChromeClient(new WebChromeClient() {
			// newProgress���Ǵ���ҳ�Ľ���ֵ
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress >= 100) {
					progressBar.setVisibility(view.GONE);
				}
				progressBar.setProgress(newProgress);
			}
		});

		/* ��ҳ�������Ҫ������ */
		webview.setWebViewClient(new WebViewClient() {
			/* ����˺�������ֵ��true,��ô������Ӻ���ҳ�����ڱ���ؼ���ִ��,�������һ���µ������ */
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// ������ַ
				webview.loadUrl(url);
				return true;
			}
		});
		// ������ҳ
		webview.loadUrl(news.link);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/* ������ҳ������һ��ҳ�� */
		if ((keyCode == 4) && (this.webview.canGoBack())) {
			webview.goBack();
			return true;
		}
		/* ��ʾ�Ҳ������˵� */
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

	// �����ղ�
	public void initPopupWindow() {
		// ��ʾitem_pop_save����
		View popview = getLayoutInflater()
				.inflate(R.layout.item_pop_save, null);
		// �Ե������ڽ�������
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
				// ��ӵ����ݿ�
				if (manager.saveLoveNews(news)) {
					ToastUtil.show(WebpageActivity.this, "�ղسɹ���\n��������໬�˵��в鿴",
							0);
				} else {
					ToastUtil.show(WebpageActivity.this,
							"�Ѿ��ղع����������ˣ�\n��������໬�˵��в鿴", 0);
				}
			}
		});
		// ����ļ���
		shareTv = (TextView) popview.findViewById(R.id.share);
		shareTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showShare();
			}
		});
	}

	// ������
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();
		// ����ʱNotification��ͼ������� 2.5.9�Ժ�İ汾�����ô˷���
		// oks.setNotification(R.drawable.ic_launcher,
		getString(R.string.app_name);
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle(news.title);
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl(news.link);
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText(news.getContent());
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		// oks.setImagePath(news.iconPath);// ȷ��SDcard������ڴ���ͼƬ
		// oks.setImageUrl(news.iconPath);
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://sharesdk.cn");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		oks.setComment(news.content);
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite(getString(R.string.app_name));
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl("http://sharesdk.cn");
		// ��������GUI
		oks.show(this);
	}
}