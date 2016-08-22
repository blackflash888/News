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
		// 创建动画对象
				Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
				// 设置动画监听
						anim.setAnimationListener(new AnimationListener() {

							// 当动画开始的时候调用此函数
							@Override
							public void onAnimationStart(Animation animation) {
								LogUtil.d("Log", "onAnimationStart");
							}
							// 当动画重复播放的时候调用此函数
							@Override
							public void onAnimationRepeat(Animation animation) {
								LogUtil.d("Log", "onAnimationRepeat");
							}
							// 当动画结束的时候调用此函数
							@Override
							public void onAnimationEnd(Animation animation) {
								LogoActivity.this.startActivity(HomeActivity.class,
										R.anim.in,R.anim.exit);
							}
						});
						// 设置动画
						imageview.setAnimation(anim);
			}
}
