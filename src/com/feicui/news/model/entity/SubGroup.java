package com.feicui.news.model.entity;

import java.util.List;

public class SubGroup<T> {
	
	private List<T> subgrp;
	private int gid;
	private String group;
	public List<T> getSubgrp() {
		return subgrp;
	}
	public void setSubgrp(List<T> subgrp) {
		this.subgrp = subgrp;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public SubGroup(List<T> subgrp, int gid, String group) {
		super();
		this.subgrp = subgrp;
		this.gid = gid;
		this.group = group;
	}
	public SubGroup() {
		super();
	}
	
}
