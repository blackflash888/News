package com.feicui.news.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.feicui.news.adapter.LoginLogAdapter;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.ParserUser;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.UserManager;
import com.feicui.news.model.entity.Entity;
import com.feicui.news.model.entity.LoginLog;
import com.feicui.news.model.entity.Register;
import com.feicui.news.model.entity.User;

import edu.feicui.newshome.model.httpclient.TextHttpResponseHandler;

public class UserActivity extends Activity implements
		ImageLoaderUtil.ImageLoadListener, OnClickListener {

	private ListView logListView;
	private LoginLogAdapter loginLogAdapter; 
	private ImageView imageViewBack;
	private Button button;
	private SystemUtil systemUtil;
	private UserManager userManager;
	private Register register;
	private ImageView icon;
	private TextView loginName, integral, commentCount;
	private User<LoginLog> logs;
	private ImageLoaderUtil imageLoader;
	private PopupWindow popupWindow;
	private LinearLayout linearLayout;
	private Bitmap bitmap;
	private File photo;// ����ͷ��·��
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		initView();
		setListener();
		// ��������ȡ�û�����
		userManager = UserManager.getInstance();
		String imei = systemUtil.imei();
		register = SharedPreferencesUtil.getRegister(this);
		token = register.getToken();
		// �����û�����
		userManager.getUserData(this, new MyTextResponseHandler(),
				new MyTextError(), imei, token);
	}

	protected void initView() {
		logListView = (ListView) findViewById(R.id.logListView);
		loginLogAdapter = new LoginLogAdapter(this);
		logListView.setAdapter(loginLogAdapter);
		imageViewBack = (ImageView) findViewById(R.id.user_iv);
		button = (Button) findViewById(R.id.btn_exit);
		icon = (ImageView) findViewById(R.id.icon);
		loginName = (TextView) findViewById(R.id.login_name);
		integral = (TextView) findViewById(R.id.integral);
		commentCount = (TextView) findViewById(R.id.comment_count);
		systemUtil = new SystemUtil(this);
		imageLoader = new ImageLoaderUtil(this, this);
		linearLayout = (LinearLayout) findViewById(R.id.layout);
		// ����PopupWindow
		View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.item_pop_selectpic, null);
		// ��������LinearLayout���õ��
		LinearLayout photoTake = (LinearLayout) view
				.findViewById(R.id.photo_take);
		LinearLayout photoSel = (LinearLayout) view
				.findViewById(R.id.photo_sel);
		photoTake.setOnClickListener(this);
		photoSel.setOnClickListener(this);

		popupWindow = new PopupWindow(view,
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// ��������
		popupWindow.setBackgroundDrawable(new BitmapDrawable());// ����Ҫ����,�����޷��ر�
	}

	public class MyTextResponseHandler implements Listener<String> {
		@Override
		public void onResponse(java.lang.String response) {
			// ������Ӧ
			Entity<User> entity = ParserUser.parserUser(response);
			User user = entity.getData();
			// ���������ص�ͷ���ַ
			String portrait = user.getPortrait();
			String name = user.getUid();
			int commentCount = user.getComnum();
			int score = user.getIntegration();
			// ListView������Դ
			ArrayList<LoginLog> logins =(ArrayList<LoginLog>) user.getLoginlog();
			//�������Դ��������
			loginLogAdapter.addDataToAdapter(logins);
			
			// ��ȡͷ��,�ȴӻ�������,û��������,�����걣�浽����
			String localPath = SharedPreferencesUtil
					.getPhoto(UserActivity.this);
			//�����û���Ϣ
			SharedPreferencesUtil.saveIconLink(UserActivity.this, portrait);
			if (TextUtils.isEmpty(localPath)) {
				Bitmap bitmap = imageLoader.getBitmap(portrait);
				if (bitmap != null) {
					icon.setImageBitmap(bitmap);
				}
			} else {
				// ��SD���л�ȡͷ��
				Bitmap bitmap = BitmapFactory.decodeFile(Environment
						.getExternalStorageDirectory() + "newsphoto/photo.jpg");
				icon.setImageBitmap(bitmap);
			}
			// ��������
			UserActivity.this.loginName.setText(name);
			UserActivity.this.commentCount.setText(commentCount + "");
			UserActivity.this.integral.setText("����: "+score + "");
		}
	}

	public class MyTextError implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			ToastUtil.show(UserActivity.this, "��������ʧ��", Toast.LENGTH_SHORT);
		}
	}

	protected void setListener() {
		// �������popupWindow�Ի���
		icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0);

			}
		});
		// ���ؽ�����
		imageViewBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserActivity.this.finish();

			}
		});
		// �˳���¼����
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UserActivity.this.finish();
				SharedPreferencesUtil.clearUserInfo(UserActivity.this);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// �ر�PopupWindow
		popupWindow.dismiss();
		switch (v.getId()) {

		// �������
		case R.id.photo_take:
			Intent take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(take, 100);
			break;
		// �����ѡ��
		case R.id.photo_sel:
			Intent sel = new Intent(Intent.ACTION_PICK);
			sel.setType("image/*");
			sel.putExtra("crop", "true");// ���òü�����
			sel.putExtra("aspectX", 1); // ��߱���
			sel.putExtra("aspectY", 1);
			sel.putExtra("outputX", 80); // ���ֵ
			sel.putExtra("outputY", 80);
			sel.putExtra("return-data", true); // ���زü����
			startActivityForResult(sel, 3);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			// ��ȡ��Ƭ
			bitmap = data.getParcelableExtra("data");
			// �浽SD��
			File file = new File(Environment.getExternalStorageDirectory(),
					"newsphoto");
			if (!file.exists()) {
				file.mkdirs();
			}
			photo = new File(file, "photo.jpg");
			OutputStream os = null;
			try {
				os = new FileOutputStream(photo);
				// ����ͷ�񵽱���
				bitmap.compress(CompressFormat.JPEG, 100, os);
				// ����������������ϴ�ͷ��
				userManager.uploadIcon(this, token, photo,
						new MyResponseHanlder());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class MyResponseHanlder extends TextHttpResponseHandler {

		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			Log.i("onFailure", "ͼƬ�ϴ�ʧ��" + responseString);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			// ����
			Entity<Register> entity = ParserUser.parserRegist(responseString);
			Register register = entity.getData();
			Log.i("onSuccess",
					register.getResult() + "," + register.getExplain());
			int result = register.getResult();
			if (result == 0) {// �ϴ��ɹ�
				// ������ͷ��·���������ļ���
				SharedPreferencesUtil.savePhoto(UserActivity.this,
						photo.getAbsolutePath());
				icon.setImageBitmap(bitmap);
				userManager.userIcon(UserActivity.this, new MyTextResponseHandler1(),
						new MyTextError(),SystemUtil.getIMEI(UserActivity.this),token);
			}
		}
	}

	// ��ͼƬ�������������ȥ
	@Override
	public void imageLoadOk(Bitmap bitmap, String url) {
		if (bitmap != null) {
			icon.setImageBitmap(bitmap);

		}
	}
	public class MyTextResponseHandler1 implements Listener<String> {

		@Override
		public void onResponse(String response) {
			// ����
			logs = ParserUser.getUserParser(response);
			String iconLink = logs.getPortrait();
			SharedPreferencesUtil.saveIconLink(UserActivity.this, iconLink);
	}
	}
}
