package com.feicui.news.activity;

import java.util.ArrayList;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.feicui.news.adapter.NewsAdapter;
import com.feicui.news.adapter.NewsTypeAdapter;
import com.feicui.news.common.CommonUtil;
import com.feicui.news.common.HttpUtil;
import com.feicui.news.common.ImageLoaderUtil;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.NewsParserUtil;
import com.feicui.news.common.ParserJsonUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.NewsDBManager;
import com.feicui.news.model.biz.NewsManager;
import com.feicui.news.model.entity.News;
import com.feicui.news.model.entity.SubType;
import com.feicui.news.view.HorizontalListView;
import com.feicui.news.view.XListView;
import com.feicui.news.view.XListView.IXListViewListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;

/**
 * 显示新闻碎片
 * 
 * @author Administrator
 * 
 */
public class FragmentNews extends Fragment {

	private ArrayList<News> list = new ArrayList<News>();
	private HomeActivity homeActivity;
	private HorizontalListView hListView;
	private NewsTypeAdapter mNewsTypeAdapter;
	private static XListView xlistview;
	private NewsAdapter mNewsAdapter;
	private NewsDBManager mNewsDBManager;
	private ImageLoaderUtil mImageLoaderUtil;
	private int startIndex, endIndex;
	private ProgressBar xProgressBar,hProgressBar;

	private static ImageLoaderUtil.ImageLoadListener imageLoadListener = new ImageLoaderUtil.ImageLoadListener() {

		@Override
		public void imageLoadOk(Bitmap bitmap, String url) {
			// 如果是下载的,那么也要设置图片
//			Log.i("Log", bitmap + "");
			ImageView iv = (ImageView) xlistview.findViewWithTag(url);
			iv.setImageBitmap(bitmap);
		}
	};

	public static ImageLoaderUtil.ImageLoadListener getImageLoadListener() {
		return imageLoadListener;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				// 让进度条消失
				xProgressBar.setVisibility(View.INVISIBLE);
				// 显示ListView
				xlistview.setVisibility(View.VISIBLE);
				// 让进度条消失
				hProgressBar.setVisibility(View.INVISIBLE);
				// 显示ListView
				hListView.setVisibility(View.VISIBLE);
				// 添加更多列表
				// for (int i = 0; i < 20; i++) {
				// }
				mNewsAdapter.addDataToAdapter(list);
//				LogUtil.d("list", list.toString());
				xlistview.setAdapter(mNewsAdapter);
				// 通知适配器更新界面
				mNewsAdapter.notifyDataSetChanged();
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_news, null);
		hListView = (HorizontalListView) view.findViewById(R.id.hlistview);
		mNewsTypeAdapter = new NewsTypeAdapter(getActivity());
		hListView.setAdapter(mNewsTypeAdapter);
		homeActivity = (HomeActivity) getActivity();
		xlistview = (XListView) view.findViewById(R.id.xlv);
		mNewsDBManager = NewsDBManager.getNewsDBManager(homeActivity);
		mNewsAdapter = new NewsAdapter(homeActivity);
		xProgressBar = (ProgressBar) view.findViewById(R.id.pb);
		hProgressBar = (ProgressBar) view.findViewById(R.id.hlistview_pb);
		
		// 发送请求给服务器要求下载新闻类型
		NewsManager.loadNewsType(new MyErrorHandler(), new MyResponseHandler(),
				getActivity());

		// 先查看数据库中是否存在数据,如果存在则从数据库中获取,否则从网上下载
		mNewsDBManager = new NewsDBManager(homeActivity);
		if (mNewsDBManager.getCount() > 0) {
//			Log.i("getCount", "getCount");
			new Thread() {
				public void run() {
					// 查询数据库获取数据
					list = mNewsDBManager.getAllNews();
					handler.sendEmptyMessage(1);
				}
			}.start();
		} else {

			new Thread() {

				public void run() {
					// 下载获取数据
					try {
						String result = HttpUtil.httpGetString(CommonUtil.PATH1
								+ CommonUtil.PATH2);
						list = ParserJsonUtil.parser(result);
						// 存到数据库
						mNewsDBManager.insertNews(list);
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
		}
		// 设置上拉和下拉
		xlistview.setPullLoadEnable(true);
		xlistview.setPullRefreshEnable(true);
		setListener();
		return view;
	}

	/** ------- 分页加载 ------- **/
	protected void setListener() {

		// 上拉加载和下拉刷新的监听
		xlistview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				ToastUtil.show(homeActivity, "刷新完成", 0);
				// 停止刷新
				xlistview.stopRefresh();
				// 设置getTime()函数返回的下拉刷新的更新时间
				xlistview.setRefreshTime(CommonUtil.getTime());
			}

			@Override
			public void onLoadMore() {
				ToastUtil.show(homeActivity, "加载完成", 0);
				// 停止加载
				xlistview.stopLoadMore();
			}
		});
		xlistview.setAdapter(mNewsAdapter);

		// 点击listview列表进入新闻内容界面
		xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(homeActivity, WebpageActivity.class);
				News news = mNewsAdapter.getInfos().get(position - 1);
				Log.i("position", position + "");
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", news);
				// 传递新闻对象
				it.putExtras(bundle);
				startActivity(it);
			}
		});

		/** ------- ListView的优化------- **/
		// 监听ListView的滚动状态,当滑动时不下载图片,停止滑动时下载图片
		xlistview.setOnScrollListener(new OnScrollListener() {
			// 当滚动的状态发生变化时会调用
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mNewsAdapter.setFlag(false);
				switch (scrollState) {
				// 空闲
				case OnScrollListener.SCROLL_STATE_IDLE:
//					Log.i("Log", "0");
					// 空闲时下载
					for (int x = startIndex; x < endIndex; x++) {
						ImageView imageview = (ImageView) xlistview
								.findViewWithTag(mNewsAdapter.getItem(x - 1).iconPath);
						mImageLoaderUtil = new ImageLoaderUtil(
								imageLoadListener, homeActivity);
						Bitmap bitmap = mImageLoaderUtil.getBitmap(mNewsAdapter
								.getItem(x - 1).iconPath);
						if (bitmap != null) {
							imageview.setImageBitmap(bitmap);
						}
					}
					break;
				// 滚动
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					Log.i("Log", "1");
					break;
				// 快速滚动
				case OnScrollListener.SCROLL_STATE_FLING:
					Log.i("Log", "2");
					break;
				}
			}

			// 当列表在滚动时会不停的调用
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// 记录开始索引和结束索引
				startIndex = Math.max(firstVisibleItem, 1);
				endIndex = Math.min(firstVisibleItem + visibleItemCount,
						totalItemCount - 1);
			}
		});
	}

	public class MyErrorHandler implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.i("error", "网络连接失败");
		}
	}

	public class MyResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {// 服务器的回调
			// 解析
			ArrayList<SubType> newsType = NewsParserUtil
					.parserNewsType(response);
			mNewsTypeAdapter.addDataToAdapter(newsType);
			mNewsTypeAdapter.notifyDataSetChanged();
			LogUtil.d("json", response);
		}
	}
}
