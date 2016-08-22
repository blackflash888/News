package com.feicui.news;

import android.os.Bundle;
import android.view.View.OnClickListener;

import com.feicui.news.activity.base.BaseActivity;
import com.feicui.news.view.ActionBar;

public class MyActionBarActivity extends BaseActivity {
				
	protected ActionBar bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	protected void initActionBar(int left,String text,int right,OnClickListener on) {
//		bar = (ActionBar) this.findViewById(R.id.actionbar);
		bar.initActionBar(left, text,right,on);
	}
	
	@Override
	protected void initView() {
		
	}

	@Override
	protected void setListener() {
		
	}

}
