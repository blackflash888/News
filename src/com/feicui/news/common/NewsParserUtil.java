package com.feicui.news.common;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.feicui.news.model.entity.Comment;
import com.feicui.news.model.entity.Entity;
import com.feicui.news.model.entity.SubEntity;
import com.feicui.news.model.entity.SubGroup;
import com.feicui.news.model.entity.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NewsParserUtil {

	public static ArrayList<SubType> parserNewsType(String result) {
		ArrayList<SubType> subTypes = new ArrayList<SubType>();
		Gson gson = new Gson();
		Type type = new TypeToken<SubEntity<SubGroup<SubType>>>() {
		}.getType();
		SubEntity<SubGroup<SubType>> entity = gson.fromJson(result, type);
		ArrayList<SubGroup<SubType>> list = (ArrayList<SubGroup<SubType>>) entity
				.getData();
		for (int x = 0; x < list.size(); x++) {
			SubGroup<SubType> group = list.get(x);
			List<SubType> types = group.getSubgrp();
			for (SubType s : types) {
				subTypes.add(s);
			}
		}
		// Log.i("subTypes", subTypes.toString());
		return subTypes;
	}

	// Gson½âÎö
	public static ArrayList<Comment> getComment(String data) {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		Gson gson = new Gson();
		Type type = new TypeToken<SubEntity<Comment>>() {
		}.getType();
		ArrayList<Comment> comment = gson.fromJson(data, type);
		for (Comment s : comment) {
			comments.add(s);
		}
		return comments;
	}

	// ÆÀÂÛ
	public static ArrayList<Comment> getComments(String response) {
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<Entity<ArrayList<Comment>>>() {
		}.getType();
		Entity<ArrayList<Comment>> entity = gson.fromJson(response, type);
		return entity.getData();
	}
}
