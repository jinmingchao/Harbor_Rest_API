package cn.com.agree.ab.a4.server.harbor.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.agree.ab.a4.server.harbor.bean.HarborProjectBean;
import cn.com.agree.ab.a4.server.harbor.bean.HarborProjectMetaDataBean;
import cn.com.agree.ab.a4.server.harbor.bean.HarborUserBean;
import cn.com.agree.ab.a4.server.harbor.utils.Base64Util;
import cn.com.agree.ab.a4.server.harbor.utils.HarborApiUtil_dep;

/**
 * @author jinmingchao
 *
 * @usage harbor中对项目操作相关的restful api
 */
public class ProjectOperatingService {
	private static HttpClient httpClient = HarborApiUtil_dep.initHarborHttpClient();
	private static HttpClient postClient = null;
	private static HttpResponse httpResponse = null;
	
	public static JSONArray createNewProject(HarborUserBean userObj,HarborProjectMetaDataBean hpmdObj) {
		String text =userObj.getUsername() + ":" + userObj.getPassword();
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		String responseEntity = "";
		int code = -1;
		try {	
			Map<String, Object> userProps = new LinkedHashMap<String, Object>();
			userProps.put("project_name",hpmdObj.getProject_name());
			userProps.put("public",hpmdObj.getIs_public());
			userProps.put("enable_content_trust",hpmdObj.getEnable_content_trust());
			userProps.put("prevent_vulnerable_images_from_running",hpmdObj.getPrevent_vulnerable_images_from_running_severity());
			userProps.put("prevent_vulnerable_images_from_running_severity",hpmdObj.getPrevent_vulnerable_images_from_running_severity());
			userProps.put("automatically_scan_images_on_push",hpmdObj.getAutomatically_scan_images_on_push());
			JSONObject requestObj = new JSONObject(userProps);			
			HttpPost post;
			String url = "http://" + userObj.getDomain() + "/api/projects";
			post = new HttpPost(url);
			post = (HttpPost) HarborApiUtil_dep.curlSetHeader(post, encodingText, userObj.getDomain(), "j");
			StringEntity se = new StringEntity(requestObj.toJSONString());
			se.setContentEncoding("UTF-8");
//			se.setContentType("application/json");// 发送json数据需要设置contentType
			post.setEntity(se);
			httpResponse = httpClient.execute(post);
			code = httpResponse.getStatusLine().getStatusCode();
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray returnArr = new JSONArray();
		JSONObject statusObj = new JSONObject();
		JSONObject responseObj = new JSONObject();
		System.out.println("code----"+code);
		System.out.println("responseEntity----"+responseEntity);
		statusObj.put("statusCode", code);

		returnArr.add(statusObj);
		if (code != 201) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if(code == 401||code == 409||code == 415)//401/409/415
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
			else
				responseObj.put("error", responseEntity.replaceAll("\\n", ""));
		} else
			responseObj.put("success", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		returnArr.add(responseObj);
		return returnArr;
		}
	
	
	public static JSONArray deleteProjectByProjectId(HarborUserBean userObj,long project_id) {
		String text =userObj.getUsername() + ":" + userObj.getPassword();
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		String responseEntity = "";
		int code = -1;
		HttpDelete delete;
		try {
			String url = "http://"+userObj.getDomain()+"/api/projects/"+project_id;
			delete = new HttpDelete(url);			
			delete = (HttpDelete) HarborApiUtil_dep.curlSetHeader(delete, encodingText, userObj.getDomain(), "j");		
			httpResponse = httpClient.execute(delete);
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
		System.out.println("code----"+code);
		System.out.println("responseEntity----"+responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if(code == 400||code == 401||code == 412||code == 500)//400/401/412/500
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
			else //404
				responseObj.put("error", responseEntity.replaceAll("\\n", ""));
		} else
			responseObj.put("success", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		returnArr.add(responseObj);
		return returnArr;
		}
	
	public static JSONArray getProjectMemberListByProjectId(HarborUserBean userObj,long project_id) {
		String text =userObj.getUsername() + ":" + userObj.getPassword();
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		String responseEntity = "";
		int code = -1;
		HttpGet get;
		try {
			String url = "http://"+userObj.getDomain()+"/api/projects/"+project_id+"/members";
			get = new HttpGet(url);
			get = (HttpGet) HarborApiUtil_dep.curlSetHeader(get, encodingText, userObj.getDomain(), "");
			httpResponse = httpClient.execute(get);	
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
		System.out.println("code----"+code);
		System.out.println("responseEntity----"+responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if(code == 400||code == 401||code == 403||code == 500)//400/401/403/500
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
			else//404
				responseObj.put("error", responseEntity.replaceAll("\\n", ""));
		} else
			responseObj.put("success", JSONArray.parseArray(responseEntity));
		returnArr.add(responseObj);
		return returnArr;
		}
	
	public static JSONArray getProjectMetadataByProjectId(HarborUserBean userObj,long project_id) {
		String text =userObj.getUsername() + ":" + userObj.getPassword();
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		String responseEntity = "";
		int code = -1;
		HttpGet get;
		try {
			String url = "http://"+userObj.getDomain()+"/api/projects/"+project_id+"/metadatas";
			get = new HttpGet(url);
			get = (HttpGet) HarborApiUtil_dep.curlSetHeader(get, encodingText, userObj.getDomain(), "");
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
		System.out.println("code----"+code);
		System.out.println("responseEntity----"+responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if(code == 401||code == 500)//401//500
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));	
			else if(code == 404)
				responseObj.put("error", responseEntity.replaceAll("\\n", ""));
		} else
			responseObj.put("success", JSONObject.parseObject(responseEntity));
		returnArr.add(responseObj);
		return returnArr;
		}
	
	public static JSONArray getProjectPropertiesByProjectId(HarborUserBean userObj,long project_id){
		String text =userObj.getUsername() + ":" + userObj.getPassword();
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		String responseEntity = "";
		int code = -1;
		HttpGet get;
		try {
			String url = "http://"+userObj.getDomain()+"/api/projects/"+project_id;
			get = new HttpGet(url);
			get = (HttpGet) HarborApiUtil_dep.curlSetHeader(get, encodingText, userObj.getDomain(), "");
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
		System.out.println("code----"+code);
		System.out.println("responseEntity----"+responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if(code == 400||code == 401||code == 403||code == 500)//400/401/403/500
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
			else if(code == 404)
				responseObj.put("error", responseEntity.replaceAll("\\n", ""));
		} else
			responseObj.put("success", JSONObject.parseObject(responseEntity));
		returnArr.add(responseObj);
		return returnArr;
		}
	
	public static JSONArray getIfProjectExsited(HarborUserBean userObj,String project_name) {
		String text =userObj.getUsername() + ":" + userObj.getPassword();
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		String responseEntity = "";
		int code = -1;
		HttpHead head;
		project_name=project_name==null?"":project_name.trim();
		try {			
			String url = "http://"+userObj.getDomain()+"/api/projects?project_name="+project_name;
			head=new HttpHead(url);		
			head = (HttpHead) HarborApiUtil_dep.curlSetHeader(head, encodingText, userObj.getDomain(), "");		
			httpResponse = httpClient.execute(head);
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		JSONArray returnArr = new JSONArray();
		JSONObject statusObj = new JSONObject();
		JSONObject responseObj = new JSONObject();
		statusObj.put("statusCode", code);
		System.out.println("code----"+code);
		System.out.println("responseEntity----"+responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if(code == 401||code == 404||code == 500)//400/401/500
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		} else
			responseObj.put("success", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		returnArr.add(responseObj);
		return returnArr;
		}
	
	public static JSONArray searchProjectOrRepositoryByKeyWord(HarborUserBean userObj,String key_word) {
		String text =userObj.getUsername() + ":" + userObj.getPassword();
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		String responseEntity = "";
		int code = -1;
		HttpGet get;
		key_word=key_word==null?"":key_word.trim();
		try {
			String url = "http://"+userObj.getDomain()+"/api/search?q="+key_word;
			get = new HttpGet(url);	
			get = (HttpGet) HarborApiUtil_dep.curlSetHeader(get, encodingText, userObj.getDomain(), "");
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
		System.out.println("code----"+code);
		System.out.println("responseEntity----"+responseEntity);
		returnArr.add(statusObj);
		if (code != 200) {
			if (code == -1)
				responseObj.put("error", "-1");
			else if(code == 500)//500
				responseObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		} else
			responseObj.put("success", JSONObject.parseObject(responseEntity));
		returnArr.add(responseObj);
		return returnArr;
		}
	
	public static JSONObject searchProjects(HarborUserBean userObj, HarborProjectBean project_fliter) {
		String text =userObj.getUsername() + ":" + userObj.getPassword();
		String encodingText;
		encodingText = Base64Util.generateAuthorityText(text);
		int code = -1;
		JSONObject returnObj = new JSONObject();
		String responseEntity = "";
		HttpGet get;     
		String url = "http://"+userObj.getDomain()+"/api/projects?";
		try {	
			if(project_fliter!=null) {
				url+=project_fliter.getName().equals("")?"":"name="+project_fliter.getName();
				url+=project_fliter.getIs_public().equals("")?"":"&public="+project_fliter.getIs_public();
				url+=project_fliter.getOwner_name().equals("")?"":"&owner="+project_fliter.getOwner_name();
				url+="&page="+project_fliter.getPage();
				url+="&page_size="+project_fliter.getPage_size();
			}else {
				returnObj.put("statusCode",code);	
				returnObj.put("error",JSONObject.parseObject("harbor project object can not be null"));				
				return returnObj;
			}
			get = new HttpGet(url);
			get = (HttpGet) HarborApiUtil_dep.curlSetHeader(get, encodingText, userObj.getDomain(), "");
			httpResponse = httpClient.execute(get);
			code = httpResponse.getStatusLine().getStatusCode();
			responseEntity = EntityUtils.toString(httpResponse.getEntity());			
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		System.out.println("url----"+url);
		System.out.println("code----"+code);
		System.out.println("responseEntity----"+responseEntity);
		returnObj.put("statusCode",code);
		if (code != 200) {
			if (code == -1)
				returnObj.put("error", code);
			else if(code==401||code==500)
				returnObj.put("error", httpResponse.getStatusLine().getReasonPhrase().replaceAll("\\n", ""));
		} else {		
			returnObj.put("total",httpResponse.getHeaders("X-Total-Count")[0].getValue());			
			//响应头分页信息提取&处理				
			HeaderElementIterator it = new BasicHeaderElementIterator(
					httpResponse.headerIterator("Link"));
			JSONObject pagination=new JSONObject();
			while (it.hasNext()) {
				HeaderElement elem = it.nextElement();
//				System.out.println("@@@@@@@@@@@@@@@"+elem.getName()+"="+elem.getValue());
				pagination.put(elem.getParameterByName("rel").getValue(),elem.getName()+"="+elem.getValue());
			}		  
			returnObj.put("pagination",pagination);
			returnObj.put("result",JSONArray.parseArray(responseEntity));
		}	
		return returnObj;
	}
	
	
//	public static JSONArray UpdateProjectPropertiesByProjectId() {}
}
