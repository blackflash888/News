package com.feicui.news.activity;

import com.feicui.news.R;
import com.feicui.news.activity.base.BaseActivity;
import com.feicui.news.common.LogUtil;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends BaseActivity {
	
	private ImageView imageview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		initView();
		setListener();
	}

	@Override
	protected void initView() {
		imageview = (ImageView) findViewById(R.id.iv_logo);
		
	}

	@Override
	protected void setListener() {
		// ������������
				Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
				// ���ö�������
						anim.setAnimationListener(new AnimationListener() {

							// ��������ʼ��ʱ����ô˺���
							@Override
							public void onAnimationStart(Animation animation) {
								LogUtil.d("Log", "onAnimationStart");
							}
							// �������ظ����ŵ�ʱ����ô˺���
							@Override
							public void onAnimationRepeat(Animation animation) {
								LogUtil.d("Log", "onAnimationRepeat");
							}
							// ������������ʱ����ô˺���
							@Override
							public void onAnimationEnd(Animation animation) {
								LogoActivity.this.startActivity(HomeActivity.class,
										R.anim.in,R.anim.exit);
							}
						});
						// ���ö���
						imageview.setAnimation(anim);
			}
}
