package com.feicui.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.activity.CommentActivity;
import com.feicui.news.base.MyBaseAdapter;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.model.entity.Comment;
import com.feicui.news.view.XListView;


public class CommentsAdapter extends MyBaseAdapter<Comment> implements ImageLoaderUtil.ImageLoadListener{

	private XListView xlistview;
	private ImageView imageView;
	private TextView textView1, textView2, textView3;
	private ImageLoaderUtil mImageLoaderUtil;

	public CommentsAdapter(Context context, XListView xlistview) {
		super(context);
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		convertView = mLayoutInflater.inflate(R.layout.item_list_comment, null);
		imageView = (ImageView) convertView.findViewById(R.id.imageview);
		textView1 = (TextView) convertView.findViewById(R.id.textview1);
		textView2 = (TextView) convertView.findViewById(R.id.textview2);
		textView3 = (TextView) convertView.findViewById(R.id.textview3);
//		imageView.setImageResource(R.drawable.photo);
		imageView.setTag(infos.get(position).getPortrait());
		mImageLoaderUtil = new ImageLoaderUtil(this, mContext);
		Bitmap bitmap = mImageLoaderUtil.getBitmap(infos.get(position).getPortrait());
		if(bitmap != null){
			LogUtil.d("bitmap","bitmap");
			imageView.setImageBitmap(bitmap);
		}
		textView1.setText(infos.get(position).getUid());
		textView2.setText(infos.get(position).getStamp());
		textView3.setText(infos.get(position).getContent());
		return convertView;
	}

	@Override
	public void imageLoadOk(Bitmap bitmap, String url) {
		if(bitmap != null){
			LogUtil.d("imageLoadOk","imageLoadOk");
			imageView.setImageBitmap(bitmap);
		}
		
	}
}
