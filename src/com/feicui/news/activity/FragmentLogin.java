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
 * 登录界面
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
	private String isMemory = "";// isMemory变量用来判断SharedPreferences有没有数据，包括上面的YES和NO
	private String FILE = "saveUserNamePwd";// 用于保存SharedPreferences的文件

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
		// 进入界面时，这个if用来判断SharedPreferences里面name和password有没有数据，有的话则直接打在EditText上面
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

		// 保存ChexkBox的选中状态
		boolean isProtecting = sharedPreferences.getBoolean("isProtected",
				false);// 每次进来的时候读取保存的数据
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
					editor.commit();// 提交数据保存
				} else {
					Editor editor = sharedPreferences.edit();
					editor.putBoolean("isProtected", false);
					passwordCb.setChecked(false);
					editor.commit();// 提交数据保存
				}
			}
		});
		// 显示密码
		showPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断是否显示明文或秘文密码
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
		// 获得对应输入框中的用户名和密码信息
		username = usernameEd.getText().toString();
		password = passwordEd.getText().toString();

		switch (v.getId()) {
		case R.id.button_login:// 登录
			progressDialog = new ProgressDialog(getActivity());
			progressDialog.setMessage("加载中...");
			progressDialog.show();
			remenber();
			// 判断用户名和密码是否为空，密码长度是否符合规范
			if (TextUtils.isEmpty(username)) {
				ToastUtil.show(getActivity(), "用户名输入错误", Toast.LENGTH_LONG);
				progressDialog.dismiss();
				return;
			}
			if (TextUtils.isEmpty(password)) {
				ToastUtil.show(getActivity(), "密码输入错误", Toast.LENGTH_LONG);
				progressDialog.dismiss();
				return;
			}
			if (password.length() < 5 || password.length() > 16) {
				ToastUtil.show(getActivity(), "密码长度错误", Toast.LENGTH_LONG);
				progressDialog.dismiss();
				return;
			}

			// 使用UserManager获得登录信息
			if (userManager == null) {
				userManager = UserManager.getInstance();
			}
			userManager.login(getActivity(), new MyTextResponseHandler(),
					new MyTextError(), username, password);
			break;
		case R.id.button_forgetPass:// 忘记密码
			homeActivity.ShowForgetPass();
			break;
		case R.id.button_register:// 注册
			homeActivity.ShowRegister();
			break;
		}
	}

	// 用于Volley获取数据后回调,结果
	public class MyTextResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {// 服务器的回调
			// 解析
			Log.i("response", response);
			Entity<Register> entity = ParserUser.parserRegist(response);
			if (entity.getStatus() == 0) {
				Register register = entity.getData();
				int result = register.getResult();
				if (result == 0) {
					// 保存用户信息
					SharedPreferencesUtil.saveRegister(getActivity(), register);
					ToastUtil.show(getActivity(), "登录成功", 0);
					progressDialog.dismiss();
					sharedPreferences = getActivity().getSharedPreferences(
							"user", Context.MODE_PRIVATE);
					Editor edit = sharedPreferences.edit();
					edit.putString("username", username);
					edit.putString("password", password);
					edit.putString("token", register.getToken());
					edit.commit();
					// 跳转到用户中心
					startActivity(new Intent(getActivity(), UserActivity.class));
				} else if (result == -3) {
					ToastUtil.show(getActivity(), "用户名或密码错误", 0);
					progressDialog.dismiss();
				} else {
					ToastUtil.show(getActivity(), "登录失败", 0);
					progressDialog.dismiss();
				}
			} else {
				ToastUtil.show(getActivity(), entity.getMessage(),
						Toast.LENGTH_SHORT);
				progressDialog.dismiss();
			}
		}
	}

	// 用于Volley获取数据后回调,错误
	public class MyTextError implements ErrorListener {
		@SuppressLint("ShowToast")
		@Override
		public void onErrorResponse(VolleyError error) {
			LogUtil.d("onErrorResponse", "网络连接失败");
			ToastUtil.show(getActivity(), "网络连接失败", 0);
			progressDialog.dismiss();
		}
	}

}
