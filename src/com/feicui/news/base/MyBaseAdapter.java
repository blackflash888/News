package com.feicui.news.base;

import java.util.ArrayList;

import com.feicui.news.common.LogUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 所有适配器的父类
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
	 * 添加数据到适配器最后的方法
	 * 
	 * @param info
	 *            要添加的数据
	 */
	public void addDataToAdapter(T info) {
		infos.add(info);
	}

	/**
	 * 添加数据到适配器指定位置的方法
	 * 
	 * @param info
	 *            要添加的数据
	 * @param index
	 *            要添加的数据的位置
	 */
	public void addDataToAdapter(T info, int index) {
		infos.add(index, info);
	}

	/**
	 * 添加集合中的数据到适配器的方法
	 * 
	 * @param info
	 *            要添加的数据
	 */
	public void addDataToAdapter(ArrayList<T> infos) {
		this.infos.addAll(infos);
	}
	
	/**
	 * 清空所有数据
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
