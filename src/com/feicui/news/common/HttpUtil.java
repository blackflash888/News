package com.feicui.news.common;

import java.io.InputStream;
import java.io.UTFDataFormatException;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.content.Entity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 用于网络上发送请求的工具类
 * @author Administrator
 */
public final class HttpUtil {
	//用于获取图片数据
	public static Bitmap httpGetBitmap(String path) throws Exception{
		HttpClient client = HttpClientUtil.getInstance();
		//获取本地ip
//		java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
//		String address = inetAddress.getHostAddress();
		path = path.replace("localhost", "118.244.212.82");
		//创建get请求对象
		HttpGet hg = new HttpGet(path);
		//通过客户端对象发送请求
		//服务器返回的响应,包含响应头和响应体
		synchronized (client) {
			HttpResponse response = client.execute(hg);
			Log.i("Log", "7++");
			// 判断服务器是否连接成功
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				HttpEntity entity = response.getEntity();
				Log.i("Log", "8++");
				InputStream is = entity.getContent();
				//得到图片
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				return bitmap;
			} else {
				throw new Exception();
			}
		}
	}
		// 用于获取图片数据
		public static String httpGetString(String path) throws Exception{
			HttpClient client = HttpClientUtil.getInstance();
			//创建get请求对象
			HttpGet hg = new HttpGet(path);
			//通过客户端对象发送请求
			//服务器返回的响应,包含响应头和响应体
			HttpResponse response = client.execute(hg);
			//判断服务器是否连接成功
			if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity, "utf-8");
				return data;
			}else{
				throw new Exception();
			}
		}
}
