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
 * 新闻主界面
 * 
 * @author chenaihau
 */
public class HomeActivity extends FragmentActivity {

	private long waitTime = 1000;// 等待时间
	private long time = 0;// 记录时间
	public static SlidingMenu menu;
	private FragmentNews fragmentNews;
	private FragmentFavorite fragmentFavorite;
	private FragmentLogin fragmentLogin;
	private FragmentRegist fragmentRegist;
	private FragmentForget fragmentForgetPass;
	private FragmentRight right;
	private TextView hometv;
	private TextView hTextView;//水平列表
	// 设置点击主界面左右两个ImageView显示左右侧滑菜单
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
		/** ------- 设置侧拉菜单------- **/
		menu = new SlidingMenu(this);
		// 设置侧拉菜单模式
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		// 设置菜单点击的区域
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置菜单拖出后剩余的屏幕宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu);
		// 将SlingMenu菜单加入Activity中
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 设置SlidingMenu的主菜单
		menu.setMenu(R.layout.activity_menu_left);
		// 设置设置SlidingMenu的二级菜单
		menu.setSecondaryMenu(R.layout.activity_menu_right);
		// 替换Fragment
		FragmentLeft left = new FragmentLeft();
		right = new FragmentRight();
		fragmentNews = new FragmentNews();
		// 开启实物才能替换,开启事物必须提交
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_left, left).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_right, right).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentNews).commit();
	}

	protected void initView() {
		// initActionBar(R.drawable.leftimg, "手机管家", R.drawable.rightimg, on);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 双击退出应用程序
		switch (keyCode) {
		// 如果按的是back键
		case KeyEvent.KEYCODE_BACK:
			// 获取当前时间的毫秒值
			long curTime = System.currentTimeMillis();
			if (curTime - time <= waitTime) {
				// 双击退出
				HomeActivity.this.finish();
			} else {
				// 提示
				ToastUtil.show(this, "再按一次退出~", 0);
			}
			time = curTime;
			return true;
			// 监听菜单键
		case KeyEvent.KEYCODE_MENU:
			menu.showMenu();
			return true;
		}
		// super.onKeyDown(keyCode, event)表示调用父类中默认的设备按键处理行为
		return super.onKeyDown(keyCode, event);
	}
	/** ------- menuleft------- **/
	// 点击侧拉菜单新闻显示新闻
	public void showNewsContent() {
		// 关闭菜单并且显示菜单内容
		menu.showContent();
		// 设置activity上方的标题
		// setTitle("新闻");
		hometv.setText("新闻");
		// 创建Fragment对象
		if (fragmentNews == null) {
			fragmentNews = new FragmentNews();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentNews).commit();
	}

	// 点击侧拉菜单收藏显示收藏
	public void showLoveNews() {
		// 关闭菜单并且显示菜单内容
		menu.showContent();
		// 设置activity上方的标题
		hometv.setText("收藏");
		// 创建Fragment对象
		if (fragmentFavorite == null) {
			fragmentFavorite = new FragmentFavorite();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentFavorite).commit();
	}

	// 点击侧拉菜单本地显示本地
	public void showlocal() {
		// 关闭菜单并且显示菜单内容
		menu.showContent();
		// 设置activity上方的标题
		hometv.setText("本地");
		// 创建Fragment对象
		if (fragmentFavorite == null) {
			fragmentFavorite = new FragmentFavorite();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentFavorite).commit();
	}
	/** ------- menuright------- **/
	//显示登录界面
	public void ShowLogin() {
		// 关闭菜单并且显示菜单内容
		menu.showContent();
		// 设置activity上方的标题
		hometv.setText("用户登录");
		// 创建Fragment对象
		if (fragmentLogin == null) {
			fragmentLogin = new FragmentLogin();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentLogin).commit();
	}

	//显示注册界面
		public void ShowRegister() {
			// 关闭菜单并且显示菜单内容
			menu.showContent();
			// 设置activity上方的标题
			hometv.setText("注册");
			// 创建Fragment对象
			if (fragmentRegist == null) {
				fragmentRegist = new FragmentRegist();
			}
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.home_fragment, fragmentRegist).commit();
		}
		
		//显示忘记密码界面
		public void ShowForgetPass() {
			// 关闭菜单并且显示菜单内容
			menu.showContent();
			// 设置activity上方的标题
			hometv.setText("忘记密码");
			// 创建Fragment对象
			if (fragmentForgetPass == null) {
				fragmentForgetPass = new FragmentForget();
			}
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.home_fragment, fragmentForgetPass).commit();
		}
		
	// 设置点击主界面左右两个ImageView显示左右侧滑菜单
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
