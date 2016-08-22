package com.feicui.news.model.entity;

import java.io.Serializable;

/**
 * 新闻实体类
 * 
 * @author Administrator
 * 
 */
public class News implements Serializable {
	public String link;
	public String title;
	public String content;
	public String iconPath;
	public String stamp;
	public int type;
	public int nid;

	public News(String link, String title, String content, String iconPath,
			String stamp, int type, int nid) {
		super();
		this.link = link;
		this.title = title;
		this.content = content;
		this.iconPath = iconPath;
		this.stamp = stamp;
		this.type = type;
		this.nid = nid;
	}

	@Override
	public String toString() {
		return "News [link=" + link + ", title=" + title + ", content="
				+ content + ", iconPath=" + iconPath + ", stamp=" + stamp
				+ ", type=" + type + ", nid=" + nid + "]";
	}

	public News() {
		super();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getStamp() {
		return stamp;
	}

	public void setStamp(String stamp) {
		this.stamp = stamp;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}
}
