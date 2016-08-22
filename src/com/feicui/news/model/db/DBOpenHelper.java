package com.feicui.news.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "news";

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, "sql", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table news(_id integer primary key autoincrement,"
				+ "nid integer,type integer,title text,content text,"
				+ "stamp text,link text,icon text)");
		db.execSQL("create table newstype(_id integer primary key autoincrement,subgroup text,subid integer)");
		db.execSQL("create table lovenews(_id integer primary key autoincrement,nid integer,title text,"
				+ "content text,icon text,link text,type integer,stamp text)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
