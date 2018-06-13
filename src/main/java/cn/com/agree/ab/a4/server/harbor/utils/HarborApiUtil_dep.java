package cn.com.agree.ab.a4.server.harbor.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HarborApiUtil_dep {
		
	public static HttpClient initHarborHttpClient() {
		@SuppressWarnings("unused")
		HttpClient httpClient = null;
		List<NameValuePair> list = null;
		// 设置组件参数, HTTP协议的版本:1.1
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
		HttpProtocolParams.setUseExpectContinue(params, true);
		// //权限设置
		// //凭证注册器
		// CredentialsProvider credsProvider = new BasicCredentialsProvider();
		// //注册1 somehost主机名 任意PORT 指定用户U1 密码P1
		// credsProvider.setCredentials(
		// new AuthScope("somehost", AuthScope.ANY_PORT),
		// new UsernamePasswordCredentials("u1", "p1"));
		// 初始化HttpClient对象
		// 设置登录参数
		list = new ArrayList<NameValuePair>();
//		list.add(new BasicNameValuePair("admin", "admin"));// 登录参数
		return httpClient = new DefaultHttpClient(params);
	}
	
	public static String getCurrentTimeStamp() {			
		return Instant.now().toString(); 					
	}
	
	public static HttpRequestBase curlSetHeader(HttpRequestBase tog, String encodingText,String domain,String content_type) {		
		if(!content_type.trim().equals(""))
		tog.setHeader("Content-Type","application/json");
		tog.setHeader("Authorization","Basic "+encodingText);
		tog.setHeader("Host",domain );
		tog.setHeader("User-Agent","curl/7.29.0");
		tog.setHeader("accept", "application/json");
		for(Header h:tog.getAllHeaders()) {
			System.out.println(h.getName()+":"+h.getValue());
		}		
		return tog;
	}
}
