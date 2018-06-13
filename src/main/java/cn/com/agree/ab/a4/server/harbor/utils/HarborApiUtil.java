package cn.com.agree.ab.a4.server.harbor.utils;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Base64;
import org.apache.http.client.methods.HttpRequestBase;

public class HarborApiUtil {
	//超级管理员的"用户名:密码"键值对.该值在harbor.cfg文件中配置,在集群启动后无法修改
    public static final String HARBOR_SUPER_ADMIN_LOGIN_PAIR="admin:admin";
	
	public static String getCurrentTimeStamp() {			
		return Instant.now().toString(); 					
	}
	
	public static HttpRequestBase SetRequestHeaders(HttpRequestBase reqType, String encodingText) {
		String Method=reqType.getMethod();
		System.out.println("METHOD" +":"+ reqType.getMethod());				
		if(Method.equals("GET")) {		
			reqType.setHeader("Authorization", "Basic " + encodingText);
//			reqType.setHeader("Content-Type", HttpClientUtil.CONTENT_TYPE_JSON_URL);
//			reqType.setHeader("Accept", HttpClientUtil.ACCEPT_JSON_URL);
// 			reqType.setHeader("Host", domain);			
		}		
		return reqType;
	}
	
	
	public static String generateAuthorityText(String text) {	
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] textByte =null;
		try {
			textByte = text.getBytes(HttpClientUtil.CHARSET_UTF_8);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final String encodedText = encoder.encodeToString(textByte);
		return encodedText;
		}
}
