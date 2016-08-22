package com.feicui.news.activity;

import java.util.ArrayList;

import com.feicui.news.R;
import com.feicui.news.adapter.NewsAdapter;
import com.feicui.news.common.LogUtil;
import com.feicui.news.model.biz.NewsDBManager;
import com.feicui.news.model.db.DBOpenHelper;
import com.feicui.news.model.entity.News;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentFavorite extends Fragment {

	private View view;
	private ListView favoritelv;
	private NewsAdapter newsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_favorite, container, false);
		favoritelv = (ListView) view.findViewById(R.id.favoritelv);
		newsAdapter = new NewsAdapter(getActivity());
		loadLoveNews();
		favoritelv.setAdapter(newsAdapter);
		favoritelv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				News news = newsAdapter.getInfos().get(position);
				Intent intent = new Intent(getActivity(), WebpageActivity.class);
				intent.putExtra("news", news);
				getActivity().startActivity(intent);
			}
		});
		return view;
	}

	/** 从数据库中加载保存的新闻 */
	public void loadLoveNews() {
		ArrayList<News> data = new NewsDBManager(getActivity()).queryLoveNews();
		LogUtil.d("loadLoveNews",data.toString());
		newsAdapter.addDataToAdapter(data);
	}
}
