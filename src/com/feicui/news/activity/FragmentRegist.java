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
 * ע�����
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
				// �ж��Ƿ�ͬ��Э������
				if (!checkBox.isChecked()) {
					ToastUtil.show(getActivity(), "��ͬ��Э���������ע��",
							Toast.LENGTH_LONG);

				}
			}
		});
		// ע�ᰴť
		regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progressDialog = new ProgressDialog(getActivity());
				progressDialog.setMessage("ע����...");
				progressDialog.show();
				
				String Email = email.getText().toString();
				 NickName = nickName.getText().toString();
				 Password = password.getText().toString();

				// �ж��Ƿ�ͬ��Э������
				if (!checkBox.isChecked()) {
					ToastUtil.show(getActivity(), "û��ͬ��Э������",
							Toast.LENGTH_SHORT);
					progressDialog.dismiss();
				}
				// �ж��û����������Ƿ�Ϊ�գ����볤���Ƿ���Ϲ淶
				if (!CommonUtil.isEmail(Email) && TextUtils.isEmpty(Email)) {
					ToastUtil.show(getActivity(), "����������ȷ�������ʽ",
							Toast.LENGTH_SHORT);
					progressDialog.dismiss();
				}
				if (TextUtils.isEmpty(NickName)) {
					ToastUtil.show(getActivity(), "�سƲ���Ϊ��", Toast.LENGTH_SHORT);
					progressDialog.dismiss();
					return;
				}
				if (TextUtils.isEmpty(Password)) {
					ToastUtil.show(getActivity(), "���벻��Ϊ��", Toast.LENGTH_SHORT);
					progressDialog.dismiss();
					return;
				}
				if (Password.length() < 5 || Password.length() > 16) {
					ToastUtil.show(getActivity(), "���볤�ȴ���", Toast.LENGTH_SHORT);
					progressDialog.dismiss();
					return;
				}
				// Ҫ�ж����������Ƿ�Ϸ�
				if (userManager == null) {
					userManager = UserManager.getInstance();
					// ʹ���û�����������ע��
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
			// ����
			Log.i("response", response);
			Entity<Register> entity = ParserUser.parserRegist(response);
			Register register = entity.getData();
			int result = register.getResult();
			if (result == 0) {
				// �����û���Ϣ
				SharedPreferencesUtil.saveRegister(getActivity(), register);
				sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
				Editor edit = sharedPreferences.edit();
				edit.putString("username", NickName);
				edit.putString("password", Password);
				edit.putString("token", register.getToken());
				edit.commit();
				// ��ת���û�����
				startActivity(new Intent(getActivity(), UserActivity.class));
				ToastUtil.show(getActivity(), "ע��ɹ�", Toast.LENGTH_SHORT);
				progressDialog.dismiss();
			} else if (result == -2) {
				ToastUtil.show(getActivity(), "ע��ʧ��", Toast.LENGTH_SHORT);
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
			Toast.makeText(getActivity(), "��������ʧ��", Toast.LENGTH_SHORT).show();
			progressDialog.dismiss();
		}

	}

}
