package cn.com.agree.ab.a4.server.harbor.bean;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.com.agree.ab.a4.server.harbor.service.ProjectMemberOperatingService;
import cn.com.agree.ab.a4.server.harbor.utils.HttpClientUtil;

public class HarborHttpRequestBean {
	private static final String CONSTRUCTION_METHOD_ERROR_RESPONSE = "{\"status\":-1,\"error\":\"Construction Method Error\"}";
	// common
	private String authorityText;
	private String url;
	private String domain;
	private String methodToggle;
	// post
	private Map<String, Object> requestEntity;

	private HarborHttpRequestBean(String methodToggle, String authorityText, String domain,String url) {
		this.authorityText = authorityText;
		this.domain = domain;
		this.url = url;
		this.methodToggle = methodToggle;
	}
	private HarborHttpRequestBean(String methodToggle, String authorityText, String domain,String url,Map<String, Object> requestEntity) {
		this.authorityText = authorityText;
		this.domain = domain;
		this.url = url;
		this.methodToggle = methodToggle;
		this.requestEntity=requestEntity;
	}
	
	public String getAuthorityText() {
		return authorityText;
	}

	public void setAuthorityText(String authorityText) {
		this.authorityText = authorityText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethodToggle() {
		return methodToggle;
	}

	public void setMethodToggle(String methodToggle) {
		this.methodToggle = methodToggle;
	}

	public Map<String, Object> getRequestEntity() {
		return requestEntity;
	}

	public void setRequestEntity(Map<String, Object> requestEntity) {
		this.requestEntity = requestEntity;
	}

	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public static JSONObject sendHttpGet(String method, String authorityText,String domain,String url) {

		try {
			return HttpClientUtil.sendHttpGet(new HarborHttpRequestBean(method, authorityText, domain,url));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.parseObject(CONSTRUCTION_METHOD_ERROR_RESPONSE);
	}

	public static JSONObject sendHttpPost(String method, String authorityText, String domain,String url,Map<String,Object> reqMap) {

		try {
			return HttpClientUtil.sendHttpPost(new HarborHttpRequestBean(method,authorityText,domain,url,reqMap));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.parseObject(CONSTRUCTION_METHOD_ERROR_RESPONSE);
	}
     
	public static JSONObject sendHttpDelete(String method,String authorityText, String domain,String url) {

		try {
			return HttpClientUtil.sendHttpDelete(new HarborHttpRequestBean(method,authorityText,domain,url));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.parseObject(CONSTRUCTION_METHOD_ERROR_RESPONSE);
	}
	public static JSONObject sendHttpPut(String method,String authorityText, String domain,String url,Map<String,Object> reqMap) {

		try {
			return HttpClientUtil.sendHttpPut(new HarborHttpRequestBean(method,authorityText,domain,url,reqMap));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.parseObject(CONSTRUCTION_METHOD_ERROR_RESPONSE);
	}
}
