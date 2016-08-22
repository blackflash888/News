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
				mTextView.setText("������ת");
			}

			public void onTick(long millisUntilFinished) {
				mTextView.setText("����ʱ(" + millisUntilFinished / 1000 + ")");
			}
		}
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// ��ҳ�汻�л���ʱ�����
			@Override
			public void onPageSelected(int position) {
				LogUtil.d("onPageScrolled", position + "");
				// ͼƬ���
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
					}, DISPLAY); // �ӳٶ�������ת
				}
			}

			// ��ҳ�����ڹ�����ʱ����ε���
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				LogUtil.d("onPageScrolled", arg0 + "," + arg1 + "," + arg2 + "");
			}

			// ������״̬�л���ʱ�����
			@Override
			public void onPageScrollStateChanged(int position) {
				LogUtil.d("onPageScrollStateChanged", position + "");
			}
		});
		mViewPager.setAdapter(mBasePagerAdapter);
		
		// ���ս��洫�ݹ���������
		Bundle bundle = getIntent().getBundleExtra("bundle");
		if (bundle != null) {
			// ���û�л�ȡ�������򷵻�null
			className = bundle.getString("className");
			Log.i("className", className);
		}
		// �ж��Ƿ��ǵ�һ�δ�Ӧ�ó���
		SharedPreferences shared = getSharedPreferences("myfile",
				Context.MODE_PRIVATE);
		boolean ismyfile = shared.getBoolean("myfile", true);
		Log.i("ismyfile", "ismyfile");
		if (!ismyfile) {
			startActivity(this, LogoActivity.class);
			// ����
			finish();
		} else {// ��һ�ε�¼
			Editor edit = shared.edit();
			edit.putBoolean("myfile", false);
			// �ύ
			edit.commit();
		}
	}

	@Override
	protected void initView() {
		mViewPager = (ViewPager) findViewById(R.id.vp_lead);
		mBasePagerAdapter = new BasePagerAdapter(this);
		// ��ȡ4��ImageView��Ķ���
		points[0] = (ImageView) findViewById(R.id.iv_lead_left);
		points[1] = (ImageView) findViewById(R.id.iv_lead_middle_left);
		points[2] = (ImageView) findViewById(R.id.iv_lead_middle_right);
		points[3] = (ImageView) findViewById(R.id.iv_lead_right);
		// ���ֶ���
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