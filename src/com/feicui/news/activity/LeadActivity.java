package com.feicui.news.activity;

import com.feicui.news.R;
import com.feicui.news.activity.base.BaseActivity;
import com.feicui.news.activity.base.BasePagerAdapter;
import com.feicui.news.common.LogUtil;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LeadActivity extends BaseActivity {

	private ViewPager mViewPager;
	private BasePagerAdapter mBasePagerAdapter;
	private ImageView[] points = new ImageView[4];
	private String className;
	private final long DISPLAY = 2000;
	private TextView mTextView;

//	private MyCountDownTimer mc;
//	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lead);
		initView();
		setListener();
//		mc = new MyCountDownTimer(DISPLAY, 1000);
//		mc.start();
		class MyCountDownTimer extends CountDownTimer {
			public MyCountDownTimer(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);
			}

			public void onFinish() {
				mTextView.setText("正在跳转");
			}

			public void onTick(long millisUntilFinished) {
				mTextView.setText("倒计时(" + millisUntilFinished / 1000 + ")");
			}
		}
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// 当页面被切换的时候调用
			@Override
			public void onPageSelected(int position) {
				LogUtil.d("onPageScrolled", position + "");
				// 图片标记
				for (int i = 0; i < points.length; i++) {
					points[i].setImageResource(R.drawable.bg_myitem_like_new);
				}
				points[position].setImageResource(R.drawable.bg_myitem_islike_new);
				if (position == points.length - 1) {
//					startActivity(LeadActivity.this,LogoActivity.class);
//					LeadActivity.this.finish();
					new Handler().postDelayed(new Runnable() {
						public void run() {
							Intent intent = new Intent(LeadActivity.this,
									LogoActivity.class);
							startActivity(intent);
							LeadActivity.this.finish();
						}
					}, DISPLAY); // 延迟多少秒跳转
				}
			}

			// 当页面正在滚动的时候会多次调用
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				LogUtil.d("onPageScrolled", arg0 + "," + arg1 + "," + arg2 + "");
			}

			// 当滚动状态切换的时候调用
			@Override
			public void onPageScrollStateChanged(int position) {
				LogUtil.d("onPageScrollStateChanged", position + "");
			}
		});
		mViewPager.setAdapter(mBasePagerAdapter);
		
		// 接收界面传递过来的数据
		Bundle bundle = getIntent().getBundleExtra("bundle");
		if (bundle != null) {
			// 如果没有获取到数据则返回null
			className = bundle.getString("className");
			Log.i("className", className);
		}
		// 判断是否是第一次打开应用程序
		SharedPreferences shared = getSharedPreferences("myfile",
				Context.MODE_PRIVATE);
		boolean ismyfile = shared.getBoolean("myfile", true);
		Log.i("ismyfile", "ismyfile");
		if (!ismyfile) {
			startActivity(this, LogoActivity.class);
			// 销毁
			finish();
		} else {// 第一次登录
			Editor edit = shared.edit();
			edit.putBoolean("myfile", false);
			// 提交
			edit.commit();
		}
	}

	@Override
	protected void initView() {
		mViewPager = (ViewPager) findViewById(R.id.vp_lead);
		mBasePagerAdapter = new BasePagerAdapter(this);
		// 获取4个ImageView点的对象
		points[0] = (ImageView) findViewById(R.id.iv_lead_left);
		points[1] = (ImageView) findViewById(R.id.iv_lead_middle_left);
		points[2] = (ImageView) findViewById(R.id.iv_lead_middle_right);
		points[3] = (ImageView) findViewById(R.id.iv_lead_right);
		// 文字对象
		mTextView = (TextView) findViewById(R.id.txt_lead);
	}

	@Override
	protected void setListener() {
		mTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				startActivity(LeadActivity.this,LogoActivity.class);
//				LeadActivity.this.finish();
			}
		});
	}
}