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
 * �Զ���ؼ�,����������ʽ,������ͼƬ�м�������
 * @author MR.CHEN
 */

public class ActionBar extends LinearLayout {
	
	//������ݵ���-1��ô��ʾ����ҪͼƬ
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
		//��������ͼ
//		inflater = LayoutInflater.from(context);
//		inflater.inflate(R.layout.activity_home_actionbar,null);
		inflate(context,R.layout.activity_home_actionbar,this);
		leftImageView = (ImageView) this.findViewById(R.id.left);
		middleTextView = (TextView) this.findViewById(R.id.middle);
		rightImageView = (ImageView) this.findViewById(R.id.right);
	}
	
	/**
	 * ��ʼ��ActionBar,����ͼƬ������
	 * @param left ��ͼ
	 * @param text ����
	 * @param right	��ͼ
	 */
	public void initActionBar(int left,String text,int right,OnClickListener on) {
		if(left == ID_NULL){
			leftImageView.setVisibility(View.INVISIBLE);//����ͼƬ����ʾ״̬,�˱�ʾ����
		}else{
			leftImageView.setImageResource(left);//��Ҫ��ʾͼƬ
			//���õ���¼�
			leftImageView.setOnClickListener(on);
		}
		if(right == ID_NULL){
			rightImageView.setVisibility(View.INVISIBLE);//����ͼƬ����ʾ״̬,�˱�ʾ����
		}else{
			rightImageView.setImageResource(right);
			//���õ���¼�
			rightImageView.setOnClickListener(on);
		}
		middleTextView.setText(text);
			
	}
}
