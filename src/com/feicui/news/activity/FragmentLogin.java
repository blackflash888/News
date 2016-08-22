package com.feicui.news.activity;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.R;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.ParserUser;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.UserManager;
import com.feicui.news.model.entity.Entity;
import com.feicui.news.model.entity.Register;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * ��¼����
 * 
 * @author Administrator
 * 
 */
public class FragmentLogin extends Fragment implements OnClickListener {

	private View view;
	private AutoCompleteTextView usernameEd;
	private EditText passwordEd;
	private Button loginBt, registBt;
	private TextView forgetPwd;
	private HomeActivity homeActivity;
	private UserManager userManager;
	private ImageView showPassword;
	private boolean flag = false;
	private String[] saveUser = { "admin", "chenyi", "android" };
	private SharedPreferences sharedPreferences = null;
	private CheckBox passwordCb;
	private String username, password;
	private ProgressDialog progressDialog;
	static String YES = "yes";
	static String NO = "no";
	private String isMemory = "";// isMemory���������ж�SharedPreferences��û�����ݣ����������YES��NO
	private String FILE = "saveUserNamePwd";// ���ڱ���SharedPreferences���ļ�

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.fragment_right_login, container, false);
		usernameEd = (AutoCompleteTextView) view
				.findViewById(R.id.editText_username);
		passwordEd = (EditText) view.findViewById(R.id.editText_password);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, saveUser);
		usernameEd.setAdapter(adapter);
		loginBt = (Button) view.findViewById(R.id.button_login);
		forgetPwd = (TextView) view.findViewById(R.id.button_forgetPass);
		registBt = (Button) view.findViewById(R.id.button_register);
		homeActivity = (HomeActivity) getActivity();
		showPassword = (ImageView) view.findViewById(R.id.show_password);
		passwordCb = (CheckBox) view.findViewById(R.id.password_CheckBox);
		sharedPreferences = getActivity().getSharedPreferences(FILE,
				Activity.MODE_PRIVATE);
		isMemory = sharedPreferences.getString("isMemory", NO);
		// �������ʱ�����if�����ж�SharedPreferences����name��password��û�����ݣ��еĻ���ֱ�Ӵ���EditText����
		if (isMemory.equals(YES)) {
			username = sharedPreferences.getString("username", "");
			password = sharedPreferences.getString("password", "");
			usernameEd.setText(username);
			passwordEd.setText(password);
		}
		Editor editor = sharedPreferences.edit();
		editor.putString(username, usernameEd.toString());
		editor.putString(password, passwordEd.toString());
		editor.commit();

		// ����ChexkBox��ѡ��״̬
		boolean isProtecting = sharedPreferences.getBoolean("isProtected",
				false);// ÿ�ν�����ʱ���ȡ���������
		if (isProtecting) {
			passwordCb.setChecked(true);
		}
		passwordCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					passwordCb.setChecked(true);
					Editor editor = sharedPreferences.edit();
					editor.putBoolean("isProtected", true);
					editor.commit();// �ύ���ݱ���
				} else {
					Editor editor = sharedPreferences.edit();
					editor.putBoolean("isProtected", false);
					passwordCb.setChecked(false);
					editor.commit();// �ύ���ݱ���
				}
			}
		});
		// ��ʾ����
		showPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �ж��Ƿ���ʾ���Ļ���������
				if (!flag) {
					showPassword.setImageDrawable(getResources().getDrawable(
							R.drawable.show_password_normal));
					passwordEd
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					showPassword.setImageDrawable(getResources().getDrawable(
							R.drawable.show_password_pressed));
					passwordEd
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}
				flag = !flag;
				passwordEd.postInvalidate();
			}
		});

		loginBt.setOnClickListener(this);
		forgetPwd.setOnClickListener(this);
		registBt.setOnClickListener(this);

		return view;

	}

	public void remenber() {
		if (passwordCb.isChecked()) {
			if (sharedPreferences == null) {
				sharedPreferences = getActivity().getSharedPreferences(FILE,
						Activity.MODE_PRIVATE);
			}
			Editor edit = sharedPreferences.edit();
			edit.putString("username", usernameEd.getText().toString());
			edit.putString("password", passwordEd.getText().toString());
			edit.putString("isMemory", YES);
			edit.commit();
		} else if (!passwordCb.isChecked()) {
			if (sharedPreferences == null) {
				sharedPreferences = getActivity().getSharedPreferences(FILE,
						Activity.MODE_PRIVATE);
			}
			Editor edit = sharedPreferences.edit();
			edit.putString("isMemory", NO);
			edit.commit();
		}
	}

	@Override
	public void onClick(View v) {
		// ��ö�Ӧ������е��û�����������Ϣ
		username = usernameEd.getText().toString();
		password = passwordEd.getText().toString();

		switch (v.getId()) {
		case R.id.button_login:// ��¼
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("������...");
			progressDialog.show();
			remenber();
			// �ж��û����������Ƿ�Ϊ�գ����볤���Ƿ���Ϲ淶
			if (TextUtils.isEmpty(username)) {
				ToastUtil.show(getActivity(), "�û����������", Toast.LENGTH_LONG);
				progressDialog.dismiss();
				return;
			}
			if (TextUtils.isEmpty(password)) {
				ToastUtil.show(getActivity(), "�����������", Toast.LENGTH_LONG);
				progressDialog.dismiss();
				return;
			}
			if (password.length() < 5 || password.length() > 16) {
				ToastUtil.show(getActivity(), "���볤�ȴ���", Toast.LENGTH_LONG);
				progressDialog.dismiss();
				return;
			}

			// ʹ��UserManager��õ�¼��Ϣ
			if (userManager == null) {
				userManager = UserManager.getInstance();
			}
			userManager.login(getActivity(), new MyTextResponseHandler(),
					new MyTextError(), username, password);
			break;
		case R.id.button_forgetPass:// ��������
			homeActivity.ShowForgetPass();
			break;
		case R.id.button_register:// ע��
			homeActivity.ShowRegister();
			break;
		}
	}

	// ����Volley��ȡ���ݺ�ص�,���
	public class MyTextResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {// �������Ļص�
			// ����
			Log.i("response", response);
			Entity<Register> entity = ParserUser.parserRegist(response);
			if (entity.getStatus() == 0) {
				Register register = entity.getData();
				int result = register.getResult();
				if (result == 0) {
					// �����û���Ϣ
					SharedPreferencesUtil.saveRegister(getActivity(), register);
					ToastUtil.show(getActivity(), "��¼�ɹ�", 0);
					progressDialog.dismiss();
					sharedPreferences = getActivity().getSharedPreferences(
							"user", Context.MODE_PRIVATE);
					Editor edit = sharedPreferences.edit();
					edit.putString("username", username);
					edit.putString("password", password);
					edit.putString("token", register.getToken());
					edit.commit();
					// ��ת���û�����
					startActivity(new Intent(getActivity(), UserActivity.class));
				} else if (result == -3) {
					ToastUtil.show(getActivity(), "�û������������", 0);
					progressDialog.dismiss();
				} else {
					ToastUtil.show(getActivity(), "��¼ʧ��", 0);
					progressDialog.dismiss();
				}
			} else {
				ToastUtil.show(getActivity(), entity.getMessage(),
						Toast.LENGTH_SHORT);
				progressDialog.dismiss();
			}
		}
	}

	// ����Volley��ȡ���ݺ�ص�,����
	public class MyTextError implements ErrorListener {
		@SuppressLint("ShowToast")
		@Override
		public void onErrorResponse(VolleyError error) {
			LogUtil.d("onErrorResponse", "��������ʧ��");
			ToastUtil.show(getActivity(), "��������ʧ��", 0);
			progressDialog.dismiss();
		}
	}

}
