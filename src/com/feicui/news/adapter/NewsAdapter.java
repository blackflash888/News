package com.feicui.news.adapter;


import com.feicui.news.R;
import com.feicui.news.activity.FragmentNews;
import com.feicui.news.activity.HomeActivity;
import com.feicui.news.base.MyBaseAdapter;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.model.entity.News;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ����������������
 * @author Administrator
 */
public class NewsAdapter extends MyBaseAdapter<News> {
	private Bitmap decodeResource;
	private ImageLoaderUtil mImageLoaderUtil;
	private ViewHolder vh;
	private Context context;
	private boolean flag = true;

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public NewsAdapter(Context context) {
		super(context);
		decodeResource = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
		this.context = context;
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_list_news, null);
			vh.iv = (ImageView) convertView.findViewById(R.id.list_item_news_iv);
			vh.tv1 = (TextView) convertView.findViewById(R.id.list_item_news_tv1);
			vh.tv2 = (TextView) convertView.findViewById(R.id.list_item_news_tv2);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		//��ÿ��ImageView��������һ��Tag,�����ҵ�
		vh.iv.setTag(infos.get(position).iconPath);
		//����Ĭ��ͼƬ
		vh.iv.setImageResource(R.drawable.ic_launcher);
		if(flag){
			mImageLoaderUtil = new 	ImageLoaderUtil(FragmentNews.getImageLoadListener(), context);
			Bitmap bitmap = mImageLoaderUtil.getBitmap(infos.get(position).iconPath);
			Log.i("bitmap", bitmap+"");
			if(bitmap != null){
				vh.iv.setImageBitmap(bitmap);
			}
		}
		vh.tv1.setText(infos.get(position).title);
		vh.tv2.setText(infos.get(position).content);
		return convertView;
	}

	class ViewHolder {
		ImageView iv;
		TextView tv1, tv2;
	}
}
	
