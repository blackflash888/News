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
	// ViewPager一般只有两种数据源 1.View 2.Fragment
	private ArrayList<View> views = new ArrayList<View>();
	private LayoutInflater inflater;
	private Context context;
	private int[] pics = {R.drawable.leadleft,
					R.drawable.leadmiddleleft,R.drawable.leadmiddleright,R.drawable.leadright};
	
	public BasePagerAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		//将三个视图加载成View对象,并且设置图片
		for (int x = 0; x < pics.length; x++) {
			View v = inflater.inflate(R.layout.activity_lead_viewpager_item, null);
			ImageView iv = (ImageView) v.findViewById(R.id.iv_lead_viewpager_item);
			iv.setImageResource(pics[x]);
			//将视图加载进适配器
			addViewToAdapter(v);
		}
	}

	/**
	 * 添加视图到适配器
	 * 
	 * @param v
	 *            视图
	 */
	public void addViewToAdapter(View v) {
		views.add(v);
	}

	/**
	 * 返回数据源集合
	 * 
	 * @return 数据源
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

	// 将视图添加进视图组
	/*
	 * 参数
	 * 1.视图对象
	 * 2.索引
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View v= views.get(position);
		container.addView(v);
		return v;
	}

	// 将视图移除视图组
	//removeView移走视图
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));//从views对象里面去获取		
	}
}
