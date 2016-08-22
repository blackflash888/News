package com.feicui.news.base;

import java.util.ArrayList;

import com.feicui.news.common.LogUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * �����������ĸ���
 * 
 * @author Administrator
 * 
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected LayoutInflater mLayoutInflater;
	public ArrayList<T> infos = new ArrayList<T>();
	public Context mContext;
	public MyBaseAdapter(Context context) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}

	public ArrayList<T> getInfos() {
		return infos;
	}
	
	/**
	 * ������ݵ����������ķ���
	 * 
	 * @param info
	 *            Ҫ��ӵ�����
	 */
	public void addDataToAdapter(T info) {
		infos.add(info);
	}

	/**
	 * ������ݵ�������ָ��λ�õķ���
	 * 
	 * @param info
	 *            Ҫ��ӵ�����
	 * @param index
	 *            Ҫ��ӵ����ݵ�λ��
	 */
	public void addDataToAdapter(T info, int index) {
		infos.add(index, info);
	}

	/**
	 * ��Ӽ����е����ݵ��������ķ���
	 * 
	 * @param info
	 *            Ҫ��ӵ�����
	 */
	public void addDataToAdapter(ArrayList<T> infos) {
		this.infos.addAll(infos);
	}
	
	/**
	 * �����������
	 */
	public void clearAllData() {
		infos.clear();

	}
	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public T getItem(int position) {
		LogUtil.d("position",position+"");
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getMyView(position, convertView, parent);
	}

	public abstract View getMyView(int position, View convertView,
			ViewGroup parent);
}
