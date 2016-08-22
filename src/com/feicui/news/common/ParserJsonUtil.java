package com.feicui.news.common;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.feicui.news.model.entity.News;

public class ParserJsonUtil {

	public static ArrayList<News> parser(String s) throws Exception{
		ArrayList<News> list = new ArrayList<News>();
		String data = HttpUtil.httpGetString(CommonUtil.PATH1
				+ CommonUtil.PATH2);
		Log.d("news", data);
		//������������
		JSONObject json = new JSONObject(s);
		JSONArray jsonArray = json.getJSONArray("data");
		// ��ȡJSONArray�е�����
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String content = jsonObject.getString("summary");
			String iconPath = jsonObject.getString("icon");
			String stamp = jsonObject.getString("stamp");
			String title = jsonObject.getString("title");
			String link = jsonObject.getString("link");
			int nid = jsonObject.getInt("nid");
			int type = jsonObject.getInt("type");
			News news = new News();
			news.content = content;
			news.iconPath = iconPath;
			news.stamp = stamp;
			news.title = title;
			news.link = link;
			news.nid = nid;
			news.type = type;
			list.add(news);
		}
		return list;
	}
	
}
