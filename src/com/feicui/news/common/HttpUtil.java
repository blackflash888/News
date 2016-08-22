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
 * ���������Ϸ�������Ĺ�����
 * @author Administrator
 */
public final class HttpUtil {
	//���ڻ�ȡͼƬ����
	public static Bitmap httpGetBitmap(String path) throws Exception{
		HttpClient client = HttpClientUtil.getInstance();
		//��ȡ����ip
//		java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
//		String address = inetAddress.getHostAddress();
		path = path.replace("localhost", "118.244.212.82");
		//����get�������
		HttpGet hg = new HttpGet(path);
		//ͨ���ͻ��˶���������
		//���������ص���Ӧ,������Ӧͷ����Ӧ��
		synchronized (client) {
			HttpResponse response = client.execute(hg);
			Log.i("Log", "7++");
			// �жϷ������Ƿ����ӳɹ�
			if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
				HttpEntity entity = response.getEntity();
				Log.i("Log", "8++");
				InputStream is = entity.getContent();
				//�õ�ͼƬ
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				return bitmap;
			} else {
				throw new Exception();
			}
		}
	}
		// ���ڻ�ȡͼƬ����
		public static String httpGetString(String path) throws Exception{
			HttpClient client = HttpClientUtil.getInstance();
			//����get�������
			HttpGet hg = new HttpGet(path);
			//ͨ���ͻ��˶���������
			//���������ص���Ӧ,������Ӧͷ����Ӧ��
			HttpResponse response = client.execute(hg);
			//�жϷ������Ƿ����ӳɹ�
			if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
				HttpEntity entity = response.getEntity();
				String data = EntityUtils.toString(entity, "utf-8");
				return data;
			}else{
				throw new Exception();
			}
		}
}
