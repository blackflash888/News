package com.feicui.news.activity;

import com.feicui.news.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FragmentLeft extends Fragment implements OnClickListener {

	private RelativeLayout[] mRelativeLayout = new RelativeLayout[5];
	private HomeActivity homeActivity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu_left, null);
		// 设置监听
		mRelativeLayout[0] = (RelativeLayout) view.findViewById(R.id.rl1);
		mRelativeLayout[1] = (RelativeLayout) view.findViewById(R.id.rl2);
		mRelativeLayout[2] = (RelativeLayout) view.findViewById(R.id.rl3);
		mRelativeLayout[3] = (RelativeLayout) view.findViewById(R.id.rl4);
		mRelativeLayout[4] = (RelativeLayout) view.findViewById(R.id.rl5);
		homeActivity = (HomeActivity) getActivity();
		for (RelativeLayout rl : mRelativeLayout) {
			rl.setOnClickListener(this);

		}
		return view;
	}

	@Override
	public void onClick(View v) {
		for (RelativeLayout rl : mRelativeLayout) {
			// 将所有的背景颜色更改成统一
			rl.setBackgroundColor(Color.TRANSPARENT);
		}
		v.setBackgroundColor(getResources().getColor(R.color.qqlan));
		switch (v.getId()) {
		case R.id.rl1:
			// 当点击新闻显示主界面
			homeActivity.showNewsContent();
			break;
		case R.id.rl2:
			homeActivity.showLoveNews();
			break;
		case R.id.rl3:
			homeActivity.showlocal();
			break;
		case R.id.rl4:

			break;
		case R.id.rl5:

			break;

		}
	}
}
