package com.feicui.news.adapter;

import com.feicui.news.R;
import com.feicui.news.base.MyBaseAdapter;
import com.feicui.news.model.entity.SubType;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsTypeAdapter extends MyBaseAdapter<SubType>{
	
	private TextView tv;

	public NewsTypeAdapter(Context context) {
		super(context);
	}


	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		convertView = mLayoutInflater.inflate(R.layout.activity_horizontallistview_item, null);
		tv = (TextView) convertView.findViewById(R.id.horizontal_tv);
		tv.setText(infos.get(position).getSubgroup());
		
		return convertView;
	}
}
