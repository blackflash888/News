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
		// 获得手机最大运存
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		// 创建缓存
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

	// 用来从缓存中获取图片
	public Bitmap getBitmap(String iconPath) {
		/**
		 * 图片的下载 
		 * 1.先从LRU缓存中查找图片 
		 * 2.在从文件缓存中查找图片 
		 * 3.下载
		 */
		Bitmap bitmap = cache.get(iconPath);
		if (bitmap != null) {
			return bitmap;
		}
		// data/data/包名/cache
		LogUtil.d("context",context.toString());
		File file = context.getCacheDir();
		File[] files = file.listFiles();
		// 剪切路径中的文件名
		name = iconPath.substring(iconPath.lastIndexOf("/") + 1);
		for (int i = 0; i < files.length; i++) {
			// 判断cache缓存文件夹下的文件名传递过来的参数图片名称是否一致
			if (files[i].getName().equals(name)) {
				return BitmapFactory.decodeFile(files[i].getPath());
			}
		}
		//异步任务,下载图片
		MyTask task = new MyTask();
		task.execute(iconPath);
		return null;
	}

	/**
	 * 异步任务 泛型 
	 * 1. 要执行的任务的参数,如下载图片需要路径 
	 * 2. 执行任务过程中的进度 
	 * 3. 任务执行结束后返回的结果
	 */
	class MyTask extends AsyncTask<String, Void, Bitmap> {
		private String url;
		// 开辟一个新的子线程去执行下载的任务
		@Override
		protected Bitmap doInBackground(String... params) {
			url = params[0];
			try {
				Log.i("Log", "1"+url);
				//获取本地ip
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
					//将图片保存在缓存中
					//先保存到Lrucache
					cache.put(url, result);
					//再保存到文件中
					name = url.substring(url.lastIndexOf("/")+1);
					//data/data/包名/cache/xxx.jpg
					File file = new File(context.getCacheDir().getPath()+"/"+name);
					Log.i("Log", "3----"+context.getCacheDir().getPath()+"/"+name);
					OutputStream os = null;
					try {
						os = new FileOutputStream(file);
						//将一个Bitmap图片保存到指定的位置
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
		//在执行任务前要做的事(UI线程)
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		//当任务执行结束后在UI线程中执行
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			//通知MainActivity下载图片结束,并且将结果传递过去
			imageLoadListener.imageLoadOk(result ,url);
			LogUtil.d("onPostExecute",result.toString());
		}
	}
}
