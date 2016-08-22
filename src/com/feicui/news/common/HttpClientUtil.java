package com.feicui.news.common;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * ��ȡhttpClient����Ĺ�����
 * @author Administrator
 */
public class HttpClientUtil {
	
	private HttpClientUtil(){}
	private static final String CHARSET = HTTP.UTF_8;
	private static HttpClient client;
	
	public static synchronized HttpClient getInstance(){
		
		if(client == null){
			HttpParams params =new BasicHttpParams();
            // ����һЩ��������
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params,CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            HttpProtocolParams
                    .setUserAgent(
                            params,
                            "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
                                    +"AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            // ��ʱ����
            /* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
            ConnManagerParams.setTimeout(params, 1000);
            /* ���ӳ�ʱ */
            HttpConnectionParams.setConnectionTimeout(params, 2000);
            /* ����ʱ */
            HttpConnectionParams.setSoTimeout(params, 4000);
            
            // �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
            client = new DefaultHttpClient();
		}
		
		return client;
	}
}
