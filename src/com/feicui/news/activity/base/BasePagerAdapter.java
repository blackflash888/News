package com.feicui.news.activity.base;

import java.util.ArrayList;
import java.util.zip.Inflater;

import org.apache.http.util.LangUtils;

import com.feicui.news.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BasePagerAdapter extends PagerAdapter {
	// ViewPagerһ��ֻ����������Դ 1.View 2.Fragment
	private ArrayList<View> views = new ArrayList<View>();
	private LayoutInflater inflater;
	private Context context;
	private int[] pics = {R.drawable.leadleft,
					R.drawable.leadmiddleleft,R.drawable.leadmiddleright,R.drawable.leadright};
	
	public BasePagerAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		//��������ͼ���س�View����,��������ͼƬ
		for (int x = 0; x < pics.length; x++) {
			View v = inflater.inflate(R.layout.activity_lead_viewpager_item, null);
			ImageView iv = (ImageView) v.findViewById(R.id.iv_lead_viewpager_item);
			iv.setImageResource(pics[x]);
			//����ͼ���ؽ�������
			addViewToAdapter(v);
		}
	}

	/**
	 * �����ͼ��������
	 * 
	 * @param v
	 *            ��ͼ
	 */
	public void addViewToAdapter(View v) {
		views.add(v);
	}

	/**
	 * ��������Դ����
	 * 
	 * @return ����Դ
	 */
	public ArrayList<View> getViews() {
		return views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	// ����ͼ��ӽ���ͼ��
	/*
	 * ����
	 * 1.��ͼ����
	 * 2.����
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View v= views.get(position);
		container.addView(v);
		return v;
	}

	// ����ͼ�Ƴ���ͼ��
	//removeView������ͼ
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));//��views��������ȥ��ȡ		
	}
}
