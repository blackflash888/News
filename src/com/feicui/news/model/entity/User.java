package com.feicui.news.model.entity;

import java.util.List;

public class User<T> {

	private String uid;//用户名
	private String portrait;//图标
	private int integration;//积分
	private int comnum;//评论数量
	private List<LoginLog> loginlog;//登录日志集合
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public int getIntegration() {
		return integration;
	}
	public void setIntegration(int integration) {
		this.integration = integration;
	}
	public int getComnum() {
		return comnum;
	}
	public void setComnum(int comnum) {
		this.comnum = comnum;
	}
	public List<LoginLog> getLoginlog() {
		return loginlog;
	}
	public void setLoginlog(List<LoginLog> loginlog) {
		this.loginlog = loginlog;
	}
	public User(String uid, String portrait, int integration, int comnum,
			List<LoginLog> loginlog) {
		super();
		this.uid = uid;
		this.portrait = portrait;
		this.integration = integration;
		this.comnum = comnum;
		this.loginlog = loginlog;
	}
	public User() {
		super();
	}
	
}
