package com.feicui.news.model.biz;

import java.util.ArrayList;

import android.R.array;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.feicui.news.common.LogUtil;
import com.feicui.news.model.db.DBOpenHelper;
import com.feicui.news.model.entity.News;

/**
 * 数据库管理类
 * 
 * @author Administrator
 */
public class NewsDBManager {
	/* 单例模式 */
	private static NewsDBManager dbManager;
	private DBOpenHelper dbHelper;

	public NewsDBManager(Context context) {
		dbHelper = new DBOpenHelper(context, null, null, 0);
	}

	public static NewsDBManager getNewsDBManager(Context context) {
		if (dbManager == null) {
			synchronized (NewsDBManager.class) {
				if (dbManager == null) {
					dbManager = new NewsDBManager(context);
				}
			}
		}
		return dbManager;
	}

	/* 添加新闻数据 */
	public void insertNews(ArrayList<News> list) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		for (int i = 0; i < list.size(); i++) {
			ContentValues values = new ContentValues();
			News news = list.get(i);
			values.put("nid", news.nid);
			values.put("title", news.title);
			values.put("content", news.content);
			values.put("icon", news.iconPath);
			values.put("link", news.link);
			values.put("type", news.type);
			db.insert("news", null, values);
//			LogUtil.d("news", news.toString());
		}
		db.close();
	}

	/* 查询数量,获取数据库有没有数据 */
	public long getCount() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from news", null);
		long len = 0;
		if (cursor.moveToFirst()) {
			len = cursor.getLong(0);
		}
		cursor.close();
		db.close();
		return len;
	}

	// 获取数据库中所有的新闻
	public ArrayList<News> getAllNews() {
		ArrayList<News> list = new ArrayList<News>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from news",null);
		cursor.moveToFirst();
		do{
			News news = new News();
			int nid = cursor.getInt(cursor.getColumnIndex("nid"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
			String link = cursor.getString(cursor.getColumnIndex("link"));
			String iconPath = cursor.getString(cursor
					.getColumnIndex("icon"));
			int type = cursor.getShort(cursor.getColumnIndex("type"));
			news.content = content;
			news.iconPath = iconPath;
			news.link = link;
			news.nid = nid;
			news.stamp = stamp;
			news.title = title;
			news.type = type;
			Log.i("news",news.toString());
			list.add(news);
		}while (cursor.moveToNext()) ;
		cursor.close();
		db.close();
		return list;
	}
	
	/*
	 * 获取收藏新闻的列表
	 */
	public ArrayList<News> queryLoveNews(){
		ArrayList<News> newsList = new ArrayList<News>(); 
		SQLiteDatabase db = dbHelper.getReadableDatabase(); 
		String sql= "select * from lovenews order by _id desc"; 
		Cursor cursor=db.rawQuery(sql, null);
		if (cursor.moveToFirst()) { 
			do { 
				News news = new News();
				int type = cursor.getInt(cursor.getColumnIndex("type")); 
				int nid = cursor.getInt(cursor.getColumnIndex("nid")); 
				String stamp = cursor.getString(cursor.getColumnIndex("stamp")); 
				String icon = cursor.getString(cursor.getColumnIndex("icon")); 
				String title = cursor.getString(cursor.getColumnIndex("title")); 
				String content = cursor.getString(cursor.getColumnIndex("content")); 
				String link = cursor.getString(cursor.getColumnIndex("link")); 
				news.nid = nid;
				news.title = title;
				news.content = content;
				news.iconPath = icon;
				news.stamp = stamp;
				news.link = link;
				news.type = type;
				newsList.add(news); 
				} while (cursor.moveToNext()); 
			cursor.close(); 
			db.close(); 
			} 
		return newsList;
	}
	
	// 收藏新闻
	public boolean saveLoveNews(News news) {
		try {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from lovenews where nid="
					+ news.getNid(), null);
			if (cursor.moveToFirst()) {
				cursor.close();
				return false;
			}
			cursor.close();
			ContentValues values = new ContentValues();
			values.put("type", news.getType());
			values.put("nid", news.getNid());
			values.put("stamp", news.getStamp());
			values.put("icon", news.getIconPath());
			values.put("title", news.getTitle());
			values.put("content", news.getContent());
			values.put("link", news.getLink());
			db.insert("lovenews", null, values);
			db.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
