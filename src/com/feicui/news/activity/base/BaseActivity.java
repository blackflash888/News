package com.feicui.news.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 所有Activity的父类
 * 包含跳转的函数
 */
public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected abstract void initView();
	protected abstract void setListener();
	
	/**
	 * 启动界面的跳转
	 * @param act	从哪个界面跳转
	 * @param cls	要跳转的界面
	 */
	protected void startActivity(Activity act,Class<?> cls){
		// 跳转到主界面
		startActivity(new Intent(act, cls));
//		// 销毁当前界面
//		act.finish();
	}
	/**
	 * 拨号界面的跳转
	 * 从一个界面跳转到系统界面
	 * @param action	跳转到哪个系统界面
	 */
	protected void startActivity(String action){
		Intent it = new Intent(action);
		startActivity(it);
	}
	/**
	 * TelActivity界面跳转到TelListActivity界面
	 * 从一个界面跳转到另一个界面并且携带数据
	 * @param act	从哪个界面跳转
	 * @param cls	要跳转的界面
	 */
	protected void startActivity(Class<?> cls,Bundle bundle){
		// 跳转到TelListActivity
		Intent it = new Intent(this, cls);
		// 由于到TelListActivity中需要查询对应表格中的数据，所以需要将position传递过去
		it.putExtra("bundle", bundle);
		startActivity(it);
	}
	/**
	 * 从一个界面跳转到另一个界面并且携带动画效果
	 * @param cls	要跳转的界面
	 * @param in	进去的动画
	 * @param out	出去的动画
	 */
	protected void startActivity(Class<?> cls,int in,int out){
		Intent it = new Intent(this, cls);
		startActivity(it);
		this.finish();
		//界面的跳转动画,必须加在startActivity或者finish后面
		overridePendingTransition(in, out);
	}
	
	/**
	 * 从一个界面跳转到另一个界面并且携带动画效果
	 * @param cls	要跳转的界面
	 * @param in	进去的动画
	 * @param out	出去的动画
	 */
	protected void startActivity(Class<?> cls,int in,int out, Bundle bundle){
		Intent it = new Intent(this, cls);
		startActivity(it);
		it.putExtra("bundle", bundle);
		//界面的跳转动画,必须加在startActivity或者finish后面
		overridePendingTransition(in, out);
	}
}
