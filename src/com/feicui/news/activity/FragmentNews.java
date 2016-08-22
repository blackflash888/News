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
 * ��ʾ������Ƭ
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
			// ��������ص�,��ôҲҪ����ͼƬ
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
				// �ý�������ʧ
				xProgressBar.setVisibility(View.INVISIBLE);
				// ��ʾListView
				xlistview.setVisibility(View.VISIBLE);
				// �ý�������ʧ
				hProgressBar.setVisibility(View.INVISIBLE);
				// ��ʾListView
				hListView.setVisibility(View.VISIBLE);
				// ��Ӹ����б�
				// for (int i = 0; i < 20; i++) {
				// }
				mNewsAdapter.addDataToAdapter(list);
//				LogUtil.d("list", list.toString());
				xlistview.setAdapter(mNewsAdapter);
				// ֪ͨ���������½���
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
		
		// ���������������Ҫ��������������
		NewsManager.loadNewsType(new MyErrorHandler(), new MyResponseHandler(),
				getActivity());

		// �Ȳ鿴���ݿ����Ƿ��������,�������������ݿ��л�ȡ,�������������
		mNewsDBManager = new NewsDBManager(homeActivity);
		if (mNewsDBManager.getCount() > 0) {
//			Log.i("getCount", "getCount");
			new Thread() {
				public void run() {
					// ��ѯ���ݿ��ȡ����
					list = mNewsDBManager.getAllNews();
					handler.sendEmptyMessage(1);
				}
			}.start();
		} else {

			new Thread() {

				public void run() {
					// ���ػ�ȡ����
					try {
						String result = HttpUtil.httpGetString(CommonUtil.PATH1
								+ CommonUtil.PATH2);
						list = ParserJsonUtil.parser(result);
						// �浽���ݿ�
						mNewsDBManager.insertNews(list);
						handler.sendEmptyMessage(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
		}
		// ��������������
		xlistview.setPullLoadEnable(true);
		xlistview.setPullRefreshEnable(true);
		setListener();
		return view;
	}

	/** ------- ��ҳ���� ------- **/
	protected void setListener() {

		// �������غ�����ˢ�µļ���
		xlistview.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				ToastUtil.show(homeActivity, "ˢ�����", 0);
				// ֹͣˢ��
				xlistview.stopRefresh();
				// ����getTime()�������ص�����ˢ�µĸ���ʱ��
				xlistview.setRefreshTime(CommonUtil.getTime());
			}

			@Override
			public void onLoadMore() {
				ToastUtil.show(homeActivity, "�������", 0);
				// ֹͣ����
				xlistview.stopLoadMore();
			}
		});
		xlistview.setAdapter(mNewsAdapter);

		// ���listview�б�����������ݽ���
		xlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(homeActivity, WebpageActivity.class);
				News news = mNewsAdapter.getInfos().get(position - 1);
				Log.i("position", position + "");
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", news);
				// �������Ŷ���
				it.putExtras(bundle);
				startActivity(it);
			}
		});

		/** ------- ListView���Ż�------- **/
		// ����ListView�Ĺ���״̬,������ʱ������ͼƬ,ֹͣ����ʱ����ͼƬ
		xlistview.setOnScrollListener(new OnScrollListener() {
			// ��������״̬�����仯ʱ�����
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mNewsAdapter.setFlag(false);
				switch (scrollState) {
				// ����
				case OnScrollListener.SCROLL_STATE_IDLE:
//					Log.i("Log", "0");
					// ����ʱ����
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
				// ����
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					Log.i("Log", "1");
					break;
				// ���ٹ���
				case OnScrollListener.SCROLL_STATE_FLING:
					Log.i("Log", "2");
					break;
				}
			}

			// ���б��ڹ���ʱ�᲻ͣ�ĵ���
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// ��¼��ʼ�����ͽ�������
				startIndex = Math.max(firstVisibleItem, 1);
				endIndex = Math.min(firstVisibleItem + visibleItemCount,
						totalItemCount - 1);
			}
		});
	}

	public class MyErrorHandler implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.i("error", "��������ʧ��");
		}
	}

	public class MyResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {// �������Ļص�
			// ����
			ArrayList<SubType> newsType = NewsParserUtil
					.parserNewsType(response);
			mNewsTypeAdapter.addDataToAdapter(newsType);
			mNewsTypeAdapter.notifyDataSetChanged();
			LogUtil.d("json", response);
		}
	}
}
