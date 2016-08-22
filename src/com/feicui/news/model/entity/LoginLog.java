package com.feicui.news.model.entity;

public class LoginLog {

	private String time;
	private String address;
	private int device;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDevice() {
		return device;
	}
	public void setDevice(int device) {
		this.device = device;
	}
	public LoginLog(String time, String address, int device) {
		super();
		this.time = time;
		this.address = address;
		this.device = device;
	}
	public LoginLog() {
		super();
	}
}
