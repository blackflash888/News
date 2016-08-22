package com.feicui.news.common;

import java.lang.reflect.Type;

import com.feicui.news.model.entity.Entity;
import com.feicui.news.model.entity.LoginLog;
import com.feicui.news.model.entity.Register;
import com.feicui.news.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ParserUser {

	// ���������ص�ע����Ϣ����
	public static Entity<Register> parserRegist(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<Entity<Register>>() {
		}.getType();
		Entity<Register> entity = gson.fromJson(json, type);
		return entity;
	}
	// ���������ص��û���Ϣ����
	public static Entity<User> parserUser(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<Entity<User>>() {
		}.getType();
		Entity<User> entity = gson.fromJson(json, type);
		return entity;
	}

	//�����û����ķ��ص�����
	public static User<LoginLog> getUserParser(String data){
		Gson gson = new Gson();
		Type type = new TypeToken<Entity<User<LoginLog>>>(){}.getType();
		Entity<User<LoginLog>> login = gson.fromJson(data, type);
		User<LoginLog> user = (User<LoginLog>)login.getData();
		return user;
	}
	
}


