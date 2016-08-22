package com.feicui.news.activity;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.ParserUser;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.UserManager;
import com.feicui.news.model.entity.Entity;
import com.feicui.news.model.entity.Register;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 注册界面
 * 
 * @author Administrator
 * 
 */
public class FragmentRegist extends Fragment {

	private EditText email, nickName, password;
	private Button regist;
	private UserManager userManager;
	private CheckBox checkBox;
	private SharedPreferences sharedPreferences;
	private String NickName,Password;
	private ProgressDialog progressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_register, null);

		email = (EditText) view.findViewById(R.id.editText_email);
		nickName = (EditText) view.findViewById(R.id.editText_name);
		password = (EditText) view.findViewById(R.id.editText_pwd);
		regist = (Button) view.findViewById(R.id.button_register);
		checkBox = (CheckBox) view.findViewById(R.id.checkBox);
		checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断是否同意协议条款
				if (!checkBox.isChecked()) {
					ToastUtil.show(getActivity(), "须同意协议条款才能注册",
							Toast.LENGTH_LONG);

				}
			}
		});
		// 注册按钮
		regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progressDialog = new ProgressDialog(getActivity());
				progressDialog.setMessage("注册中...");
				progressDialog.show();
				
				String Email = email.getText().toString();
				 NickName = nickName.getText().toString();
				 Password = password.getText().toString();

				// 判断是否同意协议条款
				if (!checkBox.isChecked()) {
					ToastUtil.show(getActivity(), "没有同意协议条款",
							Toast.LENGTH_SHORT);
					progressDialog.dismiss();
				}
				// 判断用户名和密码是否为空，密码长度是否符合规范
				if (!CommonUtil.isEmail(Email) && TextUtils.isEmpty(Email)) {
					ToastUtil.show(getActivity(), "请求输入正确的邮箱格式",
							Toast.LENGTH_SHORT);
					progressDialog.dismiss();
				}
				if (TextUtils.isEmpty(NickName)) {
					ToastUtil.show(getActivity(), "呢称不能为空", Toast.LENGTH_SHORT);
					progressDialog.dismiss();
					return;
				}
				if (TextUtils.isEmpty(Password)) {
					ToastUtil.show(getActivity(), "密码不能为空", Toast.LENGTH_SHORT);
					progressDialog.dismiss();
					return;
				}
				if (Password.length() < 5 || Password.length() > 16) {
					ToastUtil.show(getActivity(), "密码长度错误", Toast.LENGTH_SHORT);
					progressDialog.dismiss();
					return;
				}
				// 要判断输入内容是否合法
				if (userManager == null) {
					userManager = UserManager.getInstance();
					// 使用用户管理器帮助注册
					userManager.regist(getActivity(),
							new MyTextResponseHandler(), new MyTextError(),
							Email, NickName, Password);
				}
			}
		});

		return view;
	}

	public class MyTextResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {
			// 解析
			Log.i("response", response);
			Entity<Register> entity = ParserUser.parserRegist(response);
			Register register = entity.getData();
			int result = register.getResult();
			if (result == 0) {
				// 保存用户信息
				SharedPreferencesUtil.saveRegister(getActivity(), register);
				sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
				Editor edit = sharedPreferences.edit();
				edit.putString("username", NickName);
				edit.putString("password", Password);
				edit.putString("token", register.getToken());
				edit.commit();
				// 跳转到用户中心
				startActivity(new Intent(getActivity(), UserActivity.class));
				ToastUtil.show(getActivity(), "注册成功", Toast.LENGTH_SHORT);
				progressDialog.dismiss();
			} else if (result == -2) {
				ToastUtil.show(getActivity(), "注册失败", Toast.LENGTH_SHORT);
				progressDialog.dismiss();
			} else {
				ToastUtil.show(getActivity(), register.getExplain(),Toast.LENGTH_SHORT);
				progressDialog.dismiss();
			}
		}
	}

	public class MyTextError implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
			progressDialog.dismiss();
		}

	}

}
