package com.feicui.news.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;

public class ImageLoaderUtil {
	private LruCache<String, Bitmap> cache;
	private ImageLoadListener imageLoadListener;
	private Context context;
	private String name;

	public ImageLoaderUtil(ImageLoadListener imageLoadListener, Context context) {
		this.context = context;
		this.imageLoadListener = imageLoadListener;
		// ����ֻ�����˴�
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		// ��������
		cache = new LruCache<String, Bitmap>(maxMemory / 4) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getHeight() * value.getRowBytes();
			}
		};
	}

	public interface ImageLoadListener {
		void imageLoadOk(Bitmap bitmap, String url);
	}

	// �����ӻ����л�ȡͼƬ
	public Bitmap getBitmap(String iconPath) {
		/**
		 * ͼƬ������ 
		 * 1.�ȴ�LRU�����в���ͼƬ 
		 * 2.�ڴ��ļ������в���ͼƬ 
		 * 3.����
		 */
		Bitmap bitmap = cache.get(iconPath);
		if (bitmap != null) {
			return bitmap;
		}
		// data/data/����/cache
		LogUtil.d("context",context.toString());
		File file = context.getCacheDir();
		File[] files = file.listFiles();
		// ����·���е��ļ���
		name = iconPath.substring(iconPath.lastIndexOf("/") + 1);
		for (int i = 0; i < files.length; i++) {
			// �ж�cache�����ļ����µ��ļ������ݹ����Ĳ���ͼƬ�����Ƿ�һ��
			if (files[i].getName().equals(name)) {
				return BitmapFactory.decodeFile(files[i].getPath());
			}
		}
		//�첽����,����ͼƬ
		MyTask task = new MyTask();
		task.execute(iconPath);
		return null;
	}

	/**
	 * �첽���� ���� 
	 * 1. Ҫִ�е�����Ĳ���,������ͼƬ��Ҫ·�� 
	 * 2. ִ����������еĽ��� 
	 * 3. ����ִ�н����󷵻صĽ��
	 */
	class MyTask extends AsyncTask<String, Void, Bitmap> {
		private String url;
		// ����һ���µ����߳�ȥִ�����ص�����
		@Override
		protected Bitmap doInBackground(String... params) {
			url = params[0];
			try {
				Log.i("Log", "1"+url);
				//��ȡ����ip
//				java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
//				String address = inetAddress.getHostAddress();
//				Log.i("address",address);
				String path = url.replace("localhost","118.244.212.82");
				Log.i("path", path);
				Bitmap result = HttpUtil.httpGetBitmap(path);
				Log.i("bitmap",result.toString());
				Log.i("Log", result+"");
				Log.i("result", result+"");
				if(result != null){
					//��ͼƬ�����ڻ�����
					//�ȱ��浽Lrucache
					cache.put(url, result);
					//�ٱ��浽�ļ���
					name = url.substring(url.lastIndexOf("/")+1);
					//data/data/����/cache/xxx.jpg
					File file = new File(context.getCacheDir().getPath()+"/"+name);
					Log.i("Log", "3----"+context.getCacheDir().getPath()+"/"+name);
					OutputStream os = null;
					try {
						os = new FileOutputStream(file);
						//��һ��BitmapͼƬ���浽ָ����λ��
						result.compress(CompressFormat.JPEG, 70, os);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally{
						if(os != null){
							try {
								os.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		//��ִ������ǰҪ������(UI�߳�)
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		//������ִ�н�������UI�߳���ִ��
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			//֪ͨMainActivity����ͼƬ����,���ҽ�������ݹ�ȥ
			imageLoadListener.imageLoadOk(result ,url);
			LogUtil.d("onPostExecute",result.toString());
		}
	}
}
