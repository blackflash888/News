package com.feicui.news.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * ����Activity�ĸ���
 * ������ת�ĺ���
 */
public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected abstract void initView();
	protected abstract void setListener();
	
	/**
	 * �����������ת
	 * @param act	���ĸ�������ת
	 * @param cls	Ҫ��ת�Ľ���
	 */
	protected void startActivity(Activity act,Class<?> cls){
		// ��ת��������
		startActivity(new Intent(act, cls));
//		// ���ٵ�ǰ����
//		act.finish();
	}
	/**
	 * ���Ž������ת
	 * ��һ��������ת��ϵͳ����
	 * @param action	��ת���ĸ�ϵͳ����
	 */
	protected void startActivity(String action){
		Intent it = new Intent(action);
		startActivity(it);
	}
	/**
	 * TelActivity������ת��TelListActivity����
	 * ��һ��������ת����һ�����沢��Я������
	 * @param act	���ĸ�������ת
	 * @param cls	Ҫ��ת�Ľ���
	 */
	protected void startActivity(Class<?> cls,Bundle bundle){
		// ��ת��TelListActivity
		Intent it = new Intent(this, cls);
		// ���ڵ�TelListActivity����Ҫ��ѯ��Ӧ����е����ݣ�������Ҫ��position���ݹ�ȥ
		it.putExtra("bundle", bundle);
		startActivity(it);
	}
	/**
	 * ��һ��������ת����һ�����沢��Я������Ч��
	 * @param cls	Ҫ��ת�Ľ���
	 * @param in	��ȥ�Ķ���
	 * @param out	��ȥ�Ķ���
	 */
	protected void startActivity(Class<?> cls,int in,int out){
		Intent it = new Intent(this, cls);
		startActivity(it);
		this.finish();
		//�������ת����,�������startActivity����finish����
		overridePendingTransition(in, out);
	}
	
	/**
	 * ��һ��������ת����һ�����沢��Я������Ч��
	 * @param cls	Ҫ��ת�Ľ���
	 * @param in	��ȥ�Ķ���
	 * @param out	��ȥ�Ķ���
	 */
	protected void startActivity(Class<?> cls,int in,int out, Bundle bundle){
		Intent it = new Intent(this, cls);
		startActivity(it);
		it.putExtra("bundle", bundle);
		//�������ת����,�������startActivity����finish����
		overridePendingTransition(in, out);
	}
}
