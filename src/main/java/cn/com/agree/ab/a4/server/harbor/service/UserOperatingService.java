package cn.com.agree.ab.a4.server.harbor.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.agree.ab.a4.server.harbor.bean.HarborHttpRequestBean;
import cn.com.agree.ab.a4.server.harbor.bean.HarborUserBean;
import cn.com.agree.ab.a4.server.harbor.utils.Base64Util;
import cn.com.agree.ab.a4.server.harbor.utils.HarborApiUtil;
import cn.com.agree.ab.a4.server.harbor.utils.HarborApiUtil_dep;

/**
 * @author jinmingchao
 *
 * @usage harbor中对用户操作相关的restful api
 */
public class UserOperatingService {
	private static CloseableHttpClient httpClient = null;
	private static HttpClient postClient = null;
	private static HttpResponse httpResponse = null;

	/**
	 * @author jinmingchao
	 * @date 2018-05-07
	 * @usage 使用超级管理员的权限创建一个用户 使用该接口的用户需要拥有超级管理员权限:即has_admin_role = 1
	 * @args required: String domain:harbor服务暴露外网的域名或ip:port String
	 *       username:权限用户的用户名 String password:权限用户的密码 HarborUser
	 *       userjo:含有用户信息的HarborUser对象 optional:
	 * @return JSONObject returnObj:响应码的json对象
	 * @comment: curl command:
	 * 
	 */
	
	
	/**
	 * @author 
			jinmingchao
	 * @date 
			2018-06-05 17:23:39 
	 * @usage 
			  		
	 * @args
	        required:
				
			optional:
				
	   @return 
			JSONObject
	 *      				
	 */
	public JSONObject createUserBySysAdminAuthority(HarborUserBean createdUser) {
		String text = HarborApiUtil.HARBOR_SUPER_ADMIN_LOGIN_PAIR;
		String domain = createdUser.getDomain();	
		String url = "http://" + domain + "/api/users";
		Map<String, Object> userProps = new LinkedHashMap<String, Object>();
		userProps.put("user_id", 0);
		userProps.put("username", createdUser.getUsername());
		userProps.put("email", createdUser.getEmail());
		userProps.put("password", createdUser.getPassword());
		userProps.put("realname", createdUser.getRealname());
		userProps.put("comment", createdUser.getComment());
		userProps.put("deleted", 0);
		userProps.put("role_name", "");
		userProps.put("role_id", 0);
		userProps.put("has_admin_role", 0);
		userProps.put("reset_uuid", "");
		userProps.put("Salt", "");
		String timeStamp = HarborApiUtil_dep.getCurrentTimeStamp();
		userProps.put("creation_time", timeStamp);
		userProps.put("update_time", timeStamp);
		return HarborHttpRequestBean.sendHttpGet("createUserBySysAdminAuthority", text, domain, url);
	}

	/**
	 * @author jinmingchao
	 * @date 2018-05-09
	 * @usage 统计与指定用户相关的项目数与镜像仓库数
	 *        首先用户必须先登录,才能获取与用户相关仓库和镜像的信息,根据登录用户是不是系统管理员,会返回两种格式: 非系统管理员: {
	 *        "private_project_count": 2, "private_repo_count": 0,
	 *        "public_project_count": 1, "public_repo_count": 1 } 系统管理员: {
	 *        "private_project_count": 2, "private_repo_count": 0,
	 *        "public_project_count": 1, "public_repo_count": 1,
	 *        "total_project_count": 3, "total_repo_count": 1 }
	 * @args required: String domain:harbor服务暴露外网的域名或ip:port String
	 *       username:权限用户的用户名 String password:权限用户的密码 optional:
	 * @return JSONArray returnArr:包含响应码和响应体
	 * @comment curl command:
	 * 
	 */
	
	public static JSONObject getStatisticAboutUser(HarborUserBean searchedUser) {
		String text = searchedUser.getUsername() + ":" + searchedUser.getPassword();
		String domain = searchedUser.getDomain();	
		String url = "http://" + domain + "/api/users";
		return HarborHttpRequestBean.sendHttpGet("getStatisticAboutUser", text, domain, url);
			
		//		String text = username + ":" + password;
//		String encodingText;
//		encodingText = Base64Util.generateAuthorityText(text);
//		String responseEntity = "";
//		int code = -1;
//		HttpGet get;
//		try {
//			String url = "http://" + domain + "/api/statistics";
//			get = new HttpGet(url);
//			get = (HttpGet) HarborApiUtil_dep.curlSetHeader(get, encodingText, domain, "j");
//			httpResponse = httpClient.execute(get);
//			code = httpResponse.getStatusLine().getStatusCode();
//			responseEntity = EntityUtils.toString(httpResponse.getEntity());
//			// System.out.println("--------------responseEntity:"+httpResponse.getStatusLine().getReasonPhrase());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		JSONArray returnArr = new JSONArray();
//		JSONObject statusObj = new JSONObject();
//		JSONObject responseObj = new JSONObject();
//		statusObj.put("statusCode", code);
//		// System.out.println(responseEntity);
//		returnArr.add(statusObj);
//		if (code != 200) {
//			if (code == -1)
//				responseObj.put("error", "-1");
//			else
//				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
//		} else
//			responseObj.put("success", JSONObject.parseObject(responseEntity));
//		returnArr.add(responseObj);
//		return returnArr;

	}

	/**
	 * @author jinmingchao
	 * @date 2018-05-09
	 * @usage 删除(冻结)指定用户 通过指定用户id删除(冻结)匹配的用户
	 * @args required: String domain:harbor服务暴露外网的域名或ip:port String
	 *       username:权限用户的用户名 String password:权限用户的密码 int uid:要删除用户的id optional:
	 * 
	 * @return JSONObject returnObj:响应码的json对象
	 * @comment curl command:
	 * 
	 */
	public static JSONArray deleteUserByUserId(String domain, String username, String password, int uid) {
		String text = username + ":" + password;
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		int code = -1;
		JSONObject returnObj = new JSONObject();
		HttpDelete delete;
		try {
			String url = "http://" + domain + "/api/users/" + uid;
			delete = new HttpDelete(url);
			delete = (HttpDelete) HarborApiUtil_dep.curlSetHeader(delete, encodingText, domain, "");
			httpResponse = httpClient.execute(delete);
			code = httpResponse.getStatusLine().getStatusCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray returnArr = new JSONArray();
		JSONObject statusObj = new JSONObject();
		JSONObject responseObj = new JSONObject();
		statusObj.put("statusCode", code);
		// System.out.println(responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		} else
			responseObj.put("success", uid);
		returnArr.add(responseObj);
		return returnArr;

	}

	/**
	 * @author jinmingchao
	 * @date 2018-05-09
	 * @usage
	 * @args required: String domain:harbor服务暴露外网的域名或ip:port String
	 *       username:权限用户的用户名 String password:权限用户的密码 int uid:要删除用户的id int
	 *       has_admin_role:管理员权限的字段(1是,0否) optional:
	 * 
	 * @return JSONObject
	 * @comment curl command:
	 * 
	 */
	public static JSONArray endueUserSysAdminAuth(String domain, String username, String password, int uid,
			int has_admin_role) {
		String text = username + ":" + password;
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		int code = -1;
		String request_body = "{\"has_admin_role\":" + has_admin_role + "}";
		HttpPut put;
		try {
			String url = "http://" + domain + "/api/users/" + uid + "/sysadmin";
			put = new HttpPut(url);
			StringEntity se = new StringEntity(request_body);
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");// 发送json数据需要设置contentType
			put.setEntity(se);
			put = (HttpPut) HarborApiUtil_dep.curlSetHeader(put, encodingText, domain, "");
			httpResponse = httpClient.execute(put);
			code = httpResponse.getStatusLine().getStatusCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray returnArr = new JSONArray();
		JSONObject statusObj = new JSONObject();
		JSONObject responseObj = new JSONObject();
		statusObj.put("statusCode", code);
		// System.out.println(responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		} else
			responseObj.put("success", uid);
		returnArr.add(responseObj);
		return returnArr;
	}

	public static JSONArray getCurrentUser(String domain, String username, String password) {
		String text = username + ":" + password;
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		int code = -1;
		String responseEntity = "";
		HttpGet get;
		try {
			String url = "http://" + domain + "/api/users/current";
			get = new HttpGet(url);
			get = (HttpGet) HarborApiUtil_dep.curlSetHeader(get, encodingText, domain, "");
			httpResponse = httpClient.execute(get);
			@SuppressWarnings("unused")
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray returnArr = new JSONArray();
		JSONObject statusObj = new JSONObject();
		JSONObject responseObj = new JSONObject();
		statusObj.put("statusCode", code);
		// System.out.println(responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		} else
			responseObj.put("success", JSONObject.parseObject(responseEntity));
		returnArr.add(responseObj);
		return returnArr;

	}

	public static JSONArray searchUser(String domain, String username, String password, int uid) {
		String text = username + ":" + password;
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		int code = -1;
		String responseEntity = "";
		HttpGet get;
		try {
			String url = "http://" + domain + "/api/users/" + uid;
			get = new HttpGet(url);
			get = (HttpGet) HarborApiUtil_dep.curlSetHeader(get, encodingText, domain, "");
			httpResponse = httpClient.execute(get);
			@SuppressWarnings("unused")
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray returnArr = new JSONArray();
		JSONObject statusObj = new JSONObject();
		JSONObject responseObj = new JSONObject();
		statusObj.put("statusCode", code);
		// System.out.println(responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		} else
			responseObj.put("success", JSONObject.parseObject(responseEntity));
		returnArr.add(responseObj);
		return returnArr;
	}

	/**
	 * @author 
			jinmingchao
	 * @date 
			2018-06-05 10:25:05 
	 * @usage 
			 获得指定用户的基本信息 		
	 * @args
	        required:
				HarborUserBean userObj
	   @return 
			JSONObject
	 *      				
	 */
	public static JSONObject getUserInfoByUserId(HarborUserBean userObj) {	
		String text = userObj.getUsername() + ":" + userObj.getPassword();
		String domain = userObj.getDomain();	
		String url = "http://" + domain + "/api/users/"+userObj.getUser_id();
		return HarborHttpRequestBean.sendHttpGet("searchUsers", text, domain, url);
	}

	public static JSONArray modifyUserPwdByUserId(String domain, String username, String password, HarborUserBean userjo) {
		String text = username + ":" + password;
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		HttpPut put;
		JSONObject requestObj = new JSONObject();
		requestObj.put("old_password",
				userjo.getOld_password() == null || userjo.getOld_password().trim().equals("") ? ""
						: userjo.getOld_password());
		requestObj.put("new_password",
				userjo.getOld_password() == null || userjo.getNew_password().trim().equals("") ? ""
						: userjo.getNew_password());
		int code = -1;
		String responseEntity = "";
		int uid = userjo.getUser_id() == null ? -1 : userjo.getUser_id();
		try {
			String url = "http://" + domain + "/api/users/" + uid + "/password";
			put = new HttpPut(url);
			StringEntity se = new StringEntity(requestObj.toJSONString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");// 发送json数据需要设置contentType
			put.setEntity(se);
			put = (HttpPut) HarborApiUtil_dep.curlSetHeader(put, encodingText, domain, "");
			httpResponse = httpClient.execute(put);
			code = httpResponse.getStatusLine().getStatusCode();
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray returnArr = new JSONArray();
		JSONObject statusObj = new JSONObject();
		JSONObject responseObj = new JSONObject();
		statusObj.put("statusCode", code);
		// System.out.println(responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if (code == 401 || code == 404)
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
			else // 400,403
				responseObj.put("error", responseEntity.replaceAll("\\n", ""));
		} else
			responseObj.put("success", uid);
		returnArr.add(responseObj);
		return returnArr;
	}

	public static JSONArray updateUserBaseInfo(String domain, String username, String password, HarborUserBean userjo) {
		String text = username + ":" + password;
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);// X
		int code = -1;
		JSONObject requestObj = new JSONObject();
		requestObj.put("email",
				userjo.getEmail() == null || userjo.getEmail().trim().equals("") ? "" : userjo.getEmail());
		requestObj.put("realname",
				userjo.getRealname() == null || userjo.getRealname().trim().equals("") ? "" : userjo.getRealname());
		requestObj.put("comment",
				userjo.getComment() == null || userjo.getComment().trim().equals("") ? "" : userjo.getComment());
		HttpPut put;
		String responseEntity = "";
		int uid = userjo.getUser_id() == null ? -1 : userjo.getUser_id();
		try {
			String url = "http://" + domain + "/api/users/" + uid;
			put = new HttpPut(url);
			StringEntity se = new StringEntity(requestObj.toJSONString());
			se.setContentEncoding("UTF-8");
			se.setContentType("application/json");// 发送json数据需要设置contentType
			put.setEntity(se);
			put = (HttpPut) HarborApiUtil_dep.curlSetHeader(put, encodingText, domain, "");
			httpResponse = httpClient.execute(put);
			code = httpResponse.getStatusLine().getStatusCode();
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray returnArr = new JSONArray();
		JSONObject statusObj = new JSONObject();
		JSONObject responseObj = new JSONObject();
		statusObj.put("statusCode", code);
		// System.out.println(responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if (code == 401 || code == 404)
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
			else // 400,403
				responseObj.put("error", responseEntity.replaceAll("\\n", ""));
		} else
			responseObj.put("success", uid);
		returnArr.add(responseObj);
		return returnArr;
	}
}
