package com.feicui.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui.news.R;

/**
 * 自定义控件,导航条的样式,左右有图片中间有文字
 * @author MR.CHEN
 */

public class ActionBar extends LinearLayout {
	
	//如果传递的是-1那么表示不需要图片
	public static final int ID_NULL = -1;
	private ImageView leftImageView;
	private TextView middleTextView;
	private ImageView rightImageView;

	public ActionBar(Context context) {
		super(context);
		Log.d("code", "code");
	}

	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("xml", "xml");
		//加载子视图
//		inflater = LayoutInflater.from(context);
//		inflater.inflate(R.layout.activity_home_actionbar,null);
		inflate(context,R.layout.activity_home_actionbar,this);
		leftImageView = (ImageView) this.findViewById(R.id.left);
		middleTextView = (TextView) this.findViewById(R.id.middle);
		rightImageView = (ImageView) this.findViewById(R.id.right);
	}
	
	/**
	 * 初始化ActionBar,设置图片与文字
	 * @param left 左图
	 * @param text 文字
	 * @param right	右图
	 */
	public void initActionBar(int left,String text,int right,OnClickListener on) {
		if(left == ID_NULL){
			leftImageView.setVisibility(View.INVISIBLE);//设置图片的显示状态,此表示隐藏
		}else{
			leftImageView.setImageResource(left);//需要显示图片
			//设置点击事件
			leftImageView.setOnClickListener(on);
		}
		if(right == ID_NULL){
			rightImageView.setVisibility(View.INVISIBLE);//设置图片的显示状态,此表示隐藏
		}else{
			rightImageView.setImageResource(right);
			//设置点击事件
			rightImageView.setOnClickListener(on);
		}
		middleTextView.setText(text);
			
	}
}
