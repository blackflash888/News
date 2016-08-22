package com.feicui.news.common;

import android.content.Context;
import android.widget.Toast;

/**
 * ��ӡ����Ϣ�Ĺ�����,Toast�����ǵ���
 * @author Administrator
 */

public final class ToastUtil {

	// Ϊ�˵���show������ʱ��Toast����Ҫÿ�ζ�����,ֻ��Ҫһ�����󼴿�
	private static Toast toast;

	/**
	 * ��ӡ����Ϣ
	 * @param context	������
	 * @param text	��������
	 * @param duration	ѡ����Ϣ����ʱ��
	 */
	
	public static void show(Context context, String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		}
		// �޸�����
		toast.setText(text);
		toast.setDuration(duration);
		// ��ʾ����
		toast.show();
	}
}
