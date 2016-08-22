package com.feicui.news.activity;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.R;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.ParserUser;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.UserManager;
import com.feicui.news.model.entity.Entity;
import com.feicui.news.model.entity.LoginLog;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.Register;
import com.feicui.news.model.entity.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentRight extends Fragment implements OnClickListener {

	private ImageView imageView;// ��¼ͷ��
	private TextView textView;// ��¼����
	private HomeActivity homeActivity;
	private ImageView weixin, qq, fiends, sinaweibo;
	private UserManager userManager;
	private SharedPreferences sharedPreferences;
	private String iconLink, iconLinkOld;
	private String username, password, token;
	private boolean isLogin;
	private ImageLoaderUtil imageLoaderUtil;
	private User<LoginLog> logs;
	private News news;
	private TextView updateVersion;

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	private ImageLoaderUtil.ImageLoadListener imageLoadListener = new ImageLoaderUtil.ImageLoadListener() {

		@Override
		public void imageLoadOk(Bitmap bitmap, String url) {
			// ��������ص�,��ôҲҪ����ͼƬ
//			Log.i("Log", bitmap + "");
			if(bitmap != null){
				//���õ�¼ͼƬ
				imageView.setImageBitmap(bitmap);
			}
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu_right, null);
		imageView = (ImageView) view.findViewById(R.id.login_iv);
		textView = (TextView) view.findViewById(R.id.login_tv);
		homeActivity = (HomeActivity) getActivity();
		weixin = (ImageView) view.findViewById(R.id.weixin);
		qq = (ImageView) view.findViewById(R.id.qq);
		fiends = (ImageView) view.findViewById(R.id.fiends);
		sinaweibo = (ImageView) view.findViewById(R.id.sinaweibo);
		updateVersion = (TextView) view.findViewById(R.id.update_version);
		userManager = UserManager.getInstance();
		imageLoaderUtil = new ImageLoaderUtil(imageLoadListener, getActivity());
		weixin.setOnClickListener(this);
		qq.setOnClickListener(this);
		fiends.setOnClickListener(this);
		sinaweibo.setOnClickListener(this);
		imageView.setOnClickListener(this);
		textView.setOnClickListener(this);
		updateVersion.setOnClickListener(this);
		login();
		return view;
	}

	public void login() {
		sharedPreferences = getActivity().getSharedPreferences("user",
				Context.MODE_PRIVATE);
		username = sharedPreferences.getString("username", null);
		password = sharedPreferences.getString("password", null);
		token = sharedPreferences.getString("token", null);
		iconLink = SharedPreferencesUtil.getIconLink(getActivity());
		LogUtil.d("login","login");
		if (username != null && password != null && token != null) {
			LogUtil.d("1", "1");
			if (isLogin) {
				LogUtil.d("2", "2");
				if (iconLink != null) {
					LogUtil.d("3", "3");
					if (iconLink.equals(iconLinkOld)) {// �ж�ͷ�������Ƿ�ı�
					} else {
						LogUtil.d("4", "4");
						LogUtil.d("mIconLink", iconLink);
						Bitmap bitmap = imageLoaderUtil.getBitmap(iconLink);
						if (bitmap != null) {
							imageView.setImageBitmap(bitmap);
						}
					}

				}
			} else {// ����Ӧ���Զ���½
				LogUtil.d("7", "7");
				userManager.loginRight(getActivity(),
						new MyTextResponseHandler1(), new MyTextError1(),
						username, password);
			}
			iconLinkOld = iconLink;// �����µ�ͷ������
		} else {
			LogUtil.d("6", "6");
			isLogin = false;
			imageView.setImageResource(R.drawable.photo);
			textView.setText("���̵�½");
		}
	}

	public class MyTextResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {
			// ����
			Log.i("response", response);
			logs = ParserUser.getUserParser(response);

		}
	}

	public class MyTextError1 implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(homeActivity, "��¼�쳣��", Toast.LENGTH_SHORT).show();
		}
	}

	public class MyTextResponseHandler1 implements Listener<String> {

		@Override
		public void onResponse(String response) {
			// ����
			Entity<Register> entity = ParserUser.parserRegist(response);
			if (entity.getStatus() == 0) {
				Register register = entity.getData();
				int result = register.getResult();
				if (result == 0) {
					LogUtil.d("11", "11");
					textView.setText(username);
					iconLink = SharedPreferencesUtil.getIconLink(getActivity());
					if (iconLink != null) {
						Bitmap bitmap = imageLoaderUtil.getBitmap(iconLink);
						LogUtil.d("12", "12");
						if (bitmap != null) {
							LogUtil.d("13", "13");
							imageView.setImageBitmap(bitmap);
						}
					}
					isLogin = true;
				}
			} else {
				ToastUtil.show(homeActivity, entity.getMessage(),
						Toast.LENGTH_SHORT);
			}
		}

	}

	// �Ҳ໬�˵������м���
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_iv:
			if (isLogin) {
				getActivity().startActivity(
						new Intent(getActivity(), UserActivity.class));
				homeActivity.showNewsContent();
			} else {
				homeActivity.ShowLogin();
			}
			break;
		case R.id.login_tv:
			if (isLogin) {
				getActivity().startActivity(
						new Intent(getActivity(), UserActivity.class));
				homeActivity.showNewsContent();
			} else {
				homeActivity.ShowLogin();
			}
			break;
		case R.id.update_version:
			ToastUtil.show(getActivity(), "�������°汾", Toast.LENGTH_LONG);
			break;
		case R.id.weixin:
			
			break;
		case R.id.qq:

			break;
		case R.id.fiends:

			break;
		case R.id.sinaweibo:

			break;
		}
	}
}
