package com.feicui.news.model.entity;

import java.util.List;

public class SubEntity<T>{

	private String message;
	private int status;
	private List<T> data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public SubEntity(String message, int status, List<T> data) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public SubEntity() {
		super();
	}
	
}
