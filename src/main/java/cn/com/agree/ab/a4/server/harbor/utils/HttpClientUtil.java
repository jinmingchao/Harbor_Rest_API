package cn.com.agree.ab.a4.server.harbor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.com.agree.ab.a4.server.harbor.bean.HarborHttpRequestBean;

/**
 * 
 * @Description: (httpClient工具类)
 * @author xupeng xupeng@agree.com.cn
 * @date 2017年6月26日 下午4:53:36
 * @version V1.0
 */
public class HttpClientUtil {
	public static final String CHARSET_UTF_8 = "utf-8";
	public static final String CONTENT_TYPE_TEXT_XML = "text/xml";
	public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
	public static final String ACCEPT_JSON = "application/json;charset=utf-8";

//	 private static final ConcurrentHashMap<String,String> methodsMap=new
//	 ConcurrentHashMap<String,String>();
//
//	 static {
//	 registryMethods();
//	 }
//	 private static void registryMethods() {
//	 InputStream in =
//	 HttpClientUtil.class.getClassLoader().getResourceAsStream("httpRequest.properties");
//	 Properties prop = new Properties();
//	 try {
//	 prop.load(in);
//	 } catch (IOException e) {
//	 // TODO Auto-generated catch block
//	 e.printStackTrace();
//	 }
//	 Iterator<Map.Entry<Object, Object>> it= prop.entrySet().iterator();
//	 while(it.hasNext()) {
//	 Map.Entry<Object, Object> entry=(Map.Entry<Object, Object>)it.next();
//	 System.out.println("==="+entry.getKey()+":"+entry.getValue()+"===");
//	 methodsMap.put((String)entry.getKey(),(String)entry.getValue());
//	 }
//	
//	 }

	private static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();
		return httpClient;
	}

	public static JSONObject sendHttpPost(HarborHttpRequestBean httpBean) {
		HttpPost post;
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		String responseEntity = "";
		String authorityText = generateAuthorityText(httpBean.getAuthorityText());
		Map<String, Object> kvSet = new LinkedHashMap<String, Object>();
		int code = -1;
		JSONObject requestObj = new JSONObject(httpBean.getRequestEntity());
		post = (HttpPost) SetRequestHeaders(new HttpPost(httpBean.getUrl()), authorityText, httpBean.getDomain(),
				requestObj.toJSONString());
		try {

			httpResponse = httpClient.execute(post);
			HttpEntity httpEntity = httpResponse.getEntity();
			responseEntity = EntityUtils.toString(httpEntity, CHARSET_UTF_8);
			System.out.println("============" + "REQUEST HEADERS" + "============");
			for (Header h : post.getAllHeaders())
				System.out.println(h.getName() + ":" + h.getValue());
			System.out.println("============" + "METHOD NAME" + "============");
			System.out.println(httpBean.getMethodToggle());
			System.out.println("============" + "RESPONSE ENTITY" + "============");
			System.out.println(responseEntity);
			EntityUtils.consume(httpEntity);
			code = httpResponse.getStatusLine().getStatusCode();
			kvSet.put("statuscode", code);
			/* 200/400/401/403/404/409/415/500 */
			// 200
			if (code != HttpStatus.SC_OK) {
				// buildRelationshipBetweenUserAndProject
				if (httpBean.getMethodToggle().equals("buildRelationshipBetweenUserAndProject")) {
					// 401/403/415/500
					if (code == HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE || code == HttpStatus.SC_UNAUTHORIZED
							|| code == HttpStatus.SC_FORBIDDEN || code == HttpStatus.SC_INTERNAL_SERVER_ERROR)
						kvSet.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
					// 400/404/409
					else if (code == HttpStatus.SC_NOT_FOUND || code == HttpStatus.SC_CONFLICT
							|| code == HttpStatus.SC_BAD_REQUEST)
						kvSet.put("error", responseEntity.replaceAll("\\n", ""));
				}
			} else {
				try {
					kvSet.put("result", JSONObject.parseObject(responseEntity));
				} catch (Exception e) {
					kvSet.put("result", JSONArray.parseArray(responseEntity));
				}
			}
			return new JSONObject(kvSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JSONObject.parseObject("{statuscode:" + code + "}");
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONObject sendHttpDelete(HarborHttpRequestBean httpBean,Integer... ids) {	
		HttpDelete delete;
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		String responseEntity = "";
		String authorityText = generateAuthorityText(httpBean.getAuthorityText());
		Map<String, Object> kvSet = new LinkedHashMap<String, Object>();
		int code = -1;
		delete = (HttpDelete) SetRequestHeaders(new HttpDelete(httpBean.getUrl()), authorityText, httpBean.getDomain());
		System.out.println("============" + "REQUEST HEADERS" + "============");
		for (Header h : delete.getAllHeaders())
			System.out.println(h.getName() + ":" + h.getValue());
		System.out.println("============" + "METHOD NAME" + "============");
		System.out.println(httpBean.getMethodToggle());
		try {
			httpResponse = httpClient.execute(delete);
			HttpEntity httpEntity = httpResponse.getEntity();
			responseEntity = EntityUtils.toString(httpEntity, CHARSET_UTF_8);
			EntityUtils.consume(httpEntity);
			code = httpResponse.getStatusLine().getStatusCode();
			kvSet.put("statuscode", code);

			/* 200/400/401/403/404/500 */
			if (code != HttpStatus.SC_OK) {
				/**ProjectMemberOperatingService**/
				//unbuildRelationshipBetweenUserAndProject
				/**UserOperatingService**/	
				//deleteUserByUserId
				if (httpBean.getMethodToggle().equals("unbuildRelationshipBetweenUserAndProject")
						||httpBean.getMethodToggle().equals("deleteUserByUserId")){
					// 400/401/403/500
					if (code == HttpStatus.SC_BAD_REQUEST || code == HttpStatus.SC_UNAUTHORIZED
							|| code == HttpStatus.SC_FORBIDDEN || code == HttpStatus.SC_INTERNAL_SERVER_ERROR)
						kvSet.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
					// 404
					else if (code == HttpStatus.SC_NOT_FOUND)
						kvSet.put("error", responseEntity.replaceAll("\\n", ""));
					// else
					// kvSet.put("result", JSONObject.parseObject(responseEntity));
				}
			} else {// 200
				try {
					kvSet.put("result", JSONObject.parseObject(responseEntity));
				} catch (Exception e) {
					kvSet.put("result", JSONArray.parseArray(responseEntity));
				}
			}

			return new JSONObject(kvSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JSONObject.parseObject("{statuscode:" + code + "}");
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// public static JSONObject sendHttpHead(HarborHttpRequestBean httpBean) {}
	public static JSONObject sendHttpPut(HarborHttpRequestBean httpBean) {
		HttpPut put;
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		String responseEntity = "";
		String authorityText = generateAuthorityText(httpBean.getAuthorityText());
		Map<String, Object> kvSet = new LinkedHashMap<String, Object>();
		int code = -1;
		JSONObject requestObj = new JSONObject(httpBean.getRequestEntity());
		put = (HttpPut) SetRequestHeaders(new HttpPut(httpBean.getUrl()), authorityText, httpBean.getDomain(),
				requestObj.toJSONString());
		System.out.println("============" + "REQUEST HEADERS" + "============");
		for (Header h : put.getAllHeaders())
			System.out.println(h.getName() + ":" + h.getValue());
		System.out.println("============" + "METHOD NAME" + "============");
		System.out.println(httpBean.getMethodToggle());

		try {
			httpResponse = httpClient.execute(put);
			HttpEntity httpEntity = httpResponse.getEntity();
			responseEntity = EntityUtils.toString(httpEntity, CHARSET_UTF_8);
			System.out.println("============" + "RESPONSE ENTITY" + "============");
			System.out.println(responseEntity);
			EntityUtils.consume(httpEntity);
			code = httpResponse.getStatusLine().getStatusCode();
			kvSet.put("statuscode", code);
			/* 200/400/401/403/404/500 */
			if (code != HttpStatus.SC_OK) {
				/**ProjectMemberOperatingService**/
				//updateRelationshipBetweenUserAndProject
				/**UserOperatingService**/
				//endueUserSysAdminAuth
				//modifyUserPwdByUserId
				//updateUserInfoByUserId
				if (httpBean.getMethodToggle().equals("updateRelationshipBetweenUserAndProject")
						||httpBean.getMethodToggle().equals("endueUserSysAdminAuth")
							||httpBean.getMethodToggle().equals("modifyUserPwdByUserId")
								||httpBean.getMethodToggle().equals("updateUserInfoByUserId")) {
					// 400/401/403/500
					if (code == HttpStatus.SC_BAD_REQUEST || code == HttpStatus.SC_UNAUTHORIZED
							|| code == HttpStatus.SC_FORBIDDEN )
						kvSet.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
					// 404
					else if (code == HttpStatus.SC_NOT_FOUND||code == HttpStatus.SC_INTERNAL_SERVER_ERROR)
						kvSet.put("error", responseEntity.replaceAll("\\n", ""));
				}				
			} else {
				try {
					kvSet.put("result", JSONObject.parseObject(responseEntity));
					return new JSONObject(kvSet);
				} catch (Exception e) {
					kvSet.put("result",JSONArray.parseArray(responseEntity));
					JSONObject resObj=new JSONObject();
					resObj.putAll(kvSet);											
					return resObj;
		 		}
			}			
			return JSONObject.parseObject("{statusCode:" + code + "}");	
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject("{statuscode:" + code + "}");
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// try {
		// put = new HttpPut(url);
		// StringEntity se = new StringEntity(requestObj.toJSONString());
		// se.setContentEncoding("UTF-8");
		//// se.setContentType("application/json");// 发送json数据需要设置contentType
		// put.setEntity(se);
		// put = (HttpPut) HarborApiUtil_dep.curlSetHeader(put,
		// encodingText,userObj.getDomain(), "");
		// httpResponse = httpClient.execute(put);
		// code = httpResponse.getStatusLine().getStatusCode();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println("url----"+url);
		// System.out.println("code----"+code);
		// System.out.println("responseEntity----"+responseEntity);
		// returnObj.put("statusCode",code);
		// if (code != 200) {//400/401/403/404/500
		// if (code == -1)
		// returnObj.put("error", code);
		// else if(code==401||code==403||code==500)
		// returnObj.put("error",
		// httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		// else if(code==400||code==404)
		// returnObj.put("error", responseEntity.replaceAll("\\n", ""));
		// } else
		// returnObj.put("error",
		// httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		// return returnObj;

	}

	public static JSONObject sendHttpGet(HarborHttpRequestBean httpBean) {
		HttpGet get;
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse httpResponse = null;
		String responseEntity = "";
		String authorityText = generateAuthorityText(httpBean.getAuthorityText());
		Map<String, Object> kvSet = new LinkedHashMap<String, Object>();
		int code = -1;
		get = (HttpGet) SetRequestHeaders(new HttpGet(httpBean.getUrl()), authorityText, httpBean.getDomain());
		try {
			System.out.println("============" + "REQUEST HEADERS" + "============");
			for (Header h : get.getAllHeaders())
				System.out.println(h.getName() + ":" + h.getValue());
			System.out.println("============" + "METHOD NAME" + "============");
			System.out.println(httpBean.getMethodToggle());
            try {
            	httpResponse = httpClient.execute(get);				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			HttpEntity httpEntity = httpResponse.getEntity();
			responseEntity = EntityUtils.toString(httpEntity, CHARSET_UTF_8);
			System.out.println("============" + "RESPONSE ENTITY" + "============");
			System.out.println(responseEntity);
			EntityUtils.consume(httpEntity);
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("============" + "      CODE     "+"============");
			System.out.println(code);
			kvSet.put("statuscode", code);
			
			/* 0/200/400/401/403/404/500 */
			if (code != HttpStatus.SC_OK) {
				/**ProjectMemberOperatingService**/
				// getTheRelationshipBetweenUserAndProject
				if (httpBean.getMethodToggle().equals("getTheRelationshipBetweenUserAndProject")) {
					// 400/401/403/500
					if (code == HttpStatus.SC_BAD_REQUEST || code == HttpStatus.SC_UNAUTHORIZED
							|| code == HttpStatus.SC_FORBIDDEN || code == HttpStatus.SC_INTERNAL_SERVER_ERROR)
						kvSet.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
					// 404
					else if (code == HttpStatus.SC_NOT_FOUND)
						kvSet.put("error", responseEntity.replaceAll("\\n", ""));
				}
				// getProjectMemberListByProjectId
				if (httpBean.getMethodToggle().equals("getProjectMemberListByProjectId")) {
					// 400/401/403/404/500
					if (code == HttpStatus.SC_UNAUTHORIZED || code == HttpStatus.SC_FORBIDDEN
							|| code == HttpStatus.SC_NOT_FOUND || code == HttpStatus.SC_BAD_REQUEST
							|| code == HttpStatus.SC_INTERNAL_SERVER_ERROR)
						kvSet.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
			}
				/**UserOperatingService**/	
				//searchUsers
				//getUserInfoByUserId
				//getStatisticAboutUser
				//getCurrentUser
				if (httpBean.getMethodToggle().equals("searchUsers")
						||httpBean.getMethodToggle().equals("getUserInfoByUserId")
							||httpBean.getMethodToggle().equals("getStatisticAboutUser")
								||httpBean.getMethodToggle().equals("getCurrentUser")) {					  
					// 400/401/403/404/500
					if (code == HttpStatus.SC_UNAUTHORIZED || code == HttpStatus.SC_FORBIDDEN
							|| code == HttpStatus.SC_NOT_FOUND || code == HttpStatus.SC_BAD_REQUEST
							|| code == HttpStatus.SC_INTERNAL_SERVER_ERROR)
						kvSet.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
					
					/*
					 * else if (code == HttpStatus.SC_BAD_REQUEST ) kvSet.put("error",
					 * responseEntity.replaceAll("\\n", ""));
					 */
				}				
			} else {
				try {
					kvSet.put("result", JSONObject.parseObject(responseEntity));
					return new JSONObject(kvSet);
				} catch (Exception e) {
					kvSet.put("result",JSONArray.parseArray(responseEntity));
					JSONObject resObj=new JSONObject();
					resObj.putAll(kvSet);											
					return resObj;
		 		}
			}			
			return JSONObject.parseObject("{statusCode:" + code + "}");	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JSONObject.parseObject("{statusCode:" + code + "}");
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	

	}

	/**
	 * 
	 * @Description: (put请求)
	 * @author xupeng xupeng@agree.com.cn
	 * @date 2017年6月26日 下午4:56:25
	 * @version V1.0
	 */
	/*
	 * public static String sendHttpPut(String httpUrl, Map<String, String> maps) {
	 * String parem = convertStringParamter(maps); return sendHttpPut(httpUrl,
	 * parem); }
	 */

	/**
	 * 
	 * @Description: (put请求)
	 * @author xupeng xupeng@agree.com.cn
	 * @date 2017年6月26日 下午4:56:46
	 * @version V1.0
	 */
	/*
	 * public static String sendHttpPut(String httpUrl, String params) { HttpPut
	 * httpPut = new HttpPut(httpUrl); try { if (params != null &&
	 * params.trim().length() > 0) { StringEntity stringEntity = new
	 * StringEntity(params, "UTF-8");
	 * stringEntity.setContentType(CONTENT_TYPE_FORM_URL);
	 * httpPut.setEntity(stringEntity); } } catch (Exception e) {
	 * e.printStackTrace(); } return sendHttpPut(httpPut); }
	 */

	/**
	 * 
	 * @Description: (将map的参数转化成为string)
	 * @author xupeng xupeng@agree.com.cn
	 * @date 2017年6月26日 下午4:57:22
	 * @version V1.0
	 */
	public static String convertStringParamter(Map parameterMap) {
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}
				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		return parameterBuffer.toString();
	}

	private static String generateAuthorityText(String text) {
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] textByte = null;
		try {
			textByte = text.getBytes(CHARSET_UTF_8);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 编码
		final String encodedText = encoder.encodeToString(textByte);
		return encodedText;
	}

	private static HttpRequestBase SetRequestHeaders(HttpRequestBase reqType, String... argList) {
		// argList[0]=encodeText;
		// argList[1]=domain;
		// argList[2]=requestEntity
		String Method = reqType.getMethod();
		System.out.println("============" + "HTTP METHOD" + "============");
		System.out.println(Method);
		if (Method.equals("GET")) {
			// Request Header
			reqType.setHeader("Authorization", "Basic " + argList[0]);
			reqType.setHeader("Host", argList[1]);
			// reqType.setHeader("Content-Type", HttpClientUtil.CONTENT_TYPE_JSON_URL);
			// reqType.setHeader("Accept", HttpClientUtil.ACCEPT_JSON_URL);
		}
		if (Method.equals("POST")) {
			// Request Header
			reqType.setHeader("Authorization", "Basic " + argList[0]);
			reqType.setHeader("Host", argList[1]);
			// Entity Header
			StringEntity se = null;
			try {
				se = new StringEntity(argList[2]);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			se.setContentType(CONTENT_TYPE_JSON);
			System.out.println("============" + "REQUEST ENTITY HEADERS" + "============");
			System.out.println("Content-Length:"+se.getContentLength());
			
			System.out.println(se.getContentType());

			((HttpPost) reqType).setEntity(se);
		}
		if (Method.equals("DELETE")) {
			reqType.setHeader("Authorization", "Basic " + argList[0]);
			reqType.setHeader("Host", argList[1]);
		}
		if (Method.equals("PUT")) {
			// Request Header
			reqType.setHeader("Authorization", "Basic " + argList[0]);
			reqType.setHeader("Host", argList[1]);
			// Entity Header
			StringEntity se = null;
			try {
				se = new StringEntity(argList[2]);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			se.setContentType(CONTENT_TYPE_JSON);
			System.out.println("============" + "REQUEST ENTITY HEADERS" + "============");
			System.out.println("Content-Length:"+se.getContentLength());

			((HttpPut) reqType).setEntity(se);
		}
		return reqType;
	}

	

}
