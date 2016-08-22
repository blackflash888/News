package com.feicui.news.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.feicui.news.R;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.slingmenu.SlidingMenu;

/**
 * ����������
 * 
 * @author chenaihau
 */
public class HomeActivity extends FragmentActivity {

	private long waitTime = 1000;// �ȴ�ʱ��
	private long time = 0;// ��¼ʱ��
	public static SlidingMenu menu;
	private FragmentNews fragmentNews;
	private FragmentFavorite fragmentFavorite;
	private FragmentLogin fragmentLogin;
	private FragmentRegist fragmentRegist;
	private FragmentForget fragmentForgetPass;
	private FragmentRight right;
	private TextView hometv;
	private TextView hTextView;//ˮƽ�б�
	// ���õ����������������ImageView��ʾ���Ҳ໬�˵�
	private OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.left:
				menu.showMenu();
				break;
			case R.id.right:
				menu.showSecondaryMenu();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		hometv = (TextView) findViewById(R.id.home_tv);
		hTextView = (TextView) findViewById(R.id.horizontal_tv);
//		hTextView.setTextColor(getResources().getColor(R.color.anred));
		/** ------- ���ò����˵�------- **/
		menu = new SlidingMenu(this);
		// ���ò����˵�ģʽ
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		// ���ò˵����������
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// ���ò˵��ϳ���ʣ�����Ļ���
		menu.setBehindOffsetRes(R.dimen.slidingmenu);
		// ��SlingMenu�˵�����Activity��
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// ����SlidingMenu�����˵�
		menu.setMenu(R.layout.activity_menu_left);
		// ��������SlidingMenu�Ķ����˵�
		menu.setSecondaryMenu(R.layout.activity_menu_right);
		// �滻Fragment
		FragmentLeft left = new FragmentLeft();
		right = new FragmentRight();
		fragmentNews = new FragmentNews();
		// ����ʵ������滻,������������ύ
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_left, left).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_right, right).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentNews).commit();
	}

	protected void initView() {
		// initActionBar(R.drawable.leftimg, "�ֻ��ܼ�", R.drawable.rightimg, on);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ˫���˳�Ӧ�ó���
		switch (keyCode) {
		// ���������back��
		case KeyEvent.KEYCODE_BACK:
			// ��ȡ��ǰʱ��ĺ���ֵ
			long curTime = System.currentTimeMillis();
			if (curTime - time <= waitTime) {
				// ˫���˳�
				HomeActivity.this.finish();
			} else {
				// ��ʾ
				ToastUtil.show(this, "�ٰ�һ���˳�~", 0);
			}
			time = curTime;
			return true;
			// �����˵���
		case KeyEvent.KEYCODE_MENU:
			menu.showMenu();
			return true;
		}
		// super.onKeyDown(keyCode, event)��ʾ���ø�����Ĭ�ϵ��豸����������Ϊ
		return super.onKeyDown(keyCode, event);
	}
	/** ------- menuleft------- **/
	// ��������˵�������ʾ����
	public void showNewsContent() {
		// �رղ˵�������ʾ�˵�����
		menu.showContent();
		// ����activity�Ϸ��ı���
		// setTitle("����");
		hometv.setText("����");
		// ����Fragment����
		if (fragmentNews == null) {
			fragmentNews = new FragmentNews();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentNews).commit();
	}

	// ��������˵��ղ���ʾ�ղ�
	public void showLoveNews() {
		// �رղ˵�������ʾ�˵�����
		menu.showContent();
		// ����activity�Ϸ��ı���
		hometv.setText("�ղ�");
		// ����Fragment����
		if (fragmentFavorite == null) {
			fragmentFavorite = new FragmentFavorite();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentFavorite).commit();
	}

	// ��������˵�������ʾ����
	public void showlocal() {
		// �رղ˵�������ʾ�˵�����
		menu.showContent();
		// ����activity�Ϸ��ı���
		hometv.setText("����");
		// ����Fragment����
		if (fragmentFavorite == null) {
			fragmentFavorite = new FragmentFavorite();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentFavorite).commit();
	}
	/** ------- menuright------- **/
	//��ʾ��¼����
	public void ShowLogin() {
		// �رղ˵�������ʾ�˵�����
		menu.showContent();
		// ����activity�Ϸ��ı���
		hometv.setText("�û���¼");
		// ����Fragment����
		if (fragmentLogin == null) {
			fragmentLogin = new FragmentLogin();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentLogin).commit();
	}

	//��ʾע�����
		public void ShowRegister() {
			// �رղ˵�������ʾ�˵�����
			menu.showContent();
			// ����activity�Ϸ��ı���
			hometv.setText("ע��");
			// ����Fragment����
			if (fragmentRegist == null) {
				fragmentRegist = new FragmentRegist();
			}
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.home_fragment, fragmentRegist).commit();
		}
		
		//��ʾ�����������
		public void ShowForgetPass() {
			// �رղ˵�������ʾ�˵�����
			menu.showContent();
			// ����activity�Ϸ��ı���
			hometv.setText("��������");
			// ����Fragment����
			if (fragmentForgetPass == null) {
				fragmentForgetPass = new FragmentForget();
			}
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.home_fragment, fragmentForgetPass).commit();
		}
		
	// ���õ����������������ImageView��ʾ���Ҳ໬�˵�
	public void doClick(View v) {
		switch (v.getId()) {
		case R.id.leftiv:
			menu.showMenu();
			break;
		case R.id.rightiv:
			menu.showSecondaryMenu();
			break;
		}
	}
	@Override
	protected void onRestart() {
		super.onRestart();
		right.login();
		LogUtil.d("onRestart","onRestart");
	}

}
