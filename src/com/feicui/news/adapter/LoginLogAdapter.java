package com.feicui.news.adapter;

import com.feicui.news.R;
import com.feicui.news.base.MyBaseAdapter;
import com.feicui.news.model.entity.LoginLog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoginLogAdapter extends MyBaseAdapter<LoginLog>{

	private TextView textViewTime,textViewAddress,textViewType;
	
	public LoginLogAdapter(Context context) {
		super(context);
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		convertView = mLayoutInflater.inflate(R.layout.item_login_log, null);
		textViewTime= (TextView) convertView.findViewById(R.id.login_time);
		textViewAddress= (TextView) convertView.findViewById(R.id.login_address);
		textViewType= (TextView) convertView.findViewById(R.id.login_type);
		
		textViewTime.setText(infos.get(position).getTime());
		textViewAddress.setText(infos.get(position).getAddress());
		textViewType.setText(infos.get(position).getDevice()+"");
		return convertView;
	}
	
	
}
