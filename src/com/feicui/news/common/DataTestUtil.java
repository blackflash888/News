package com.feicui.news.common;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * µ¥Ôª²âÊÔ¿ò¼Ü
 * @author Administrator
 */
public class DataTestUtil extends AndroidTestCase {

	public void testHttpGetString() {
		new Thread() {
			public void run() {
				try {
					String data = HttpUtil.httpGetString(CommonUtil.PATH1
							+ CommonUtil.PATH2);
//					LogUtil.d("DataTest", data);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
}
