package cn.com.agree.ab.a4.server.harbor.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.agree.ab.a4.server.harbor.bean.HarborHttpRequestBean;
import cn.com.agree.ab.a4.server.harbor.bean.HarborProjectMemberBean;
import cn.com.agree.ab.a4.server.harbor.bean.HarborUserBean;

/**
 * @author jinmingchao
 *
 * @usage harbor中对项目成员操作相关的restful api
 */
public class ProjectMemberOperatingService {

	public JSONObject getProjectMemberListByProjectId(HarborUserBean userObj, long project_id) {
		String text = userObj.getUsername() + ":" + userObj.getPassword();
		String domain = userObj.getDomain();
		String url = "http://" + domain + "/api/projects/" + project_id + "/members";
		return HarborHttpRequestBean.sendHttpGet("getProjectMemberListByProjectId", text, domain, url);
	}

	public synchronized JSONObject buildRelationshipBetweenUserAndProject(HarborUserBean userObj, HarborProjectMemberBean hpmbObj) {
		String text = userObj.getUsername() + ":" + userObj.getPassword();
		String domain = userObj.getDomain();
		String url = "http://" + domain + "/api/projects/" + hpmbObj.getRelavent_project_id() + "/members/";
		Map<String, Object> argSet = new LinkedHashMap<String, Object>();
		argSet.put("roles", JSONArray.parseArray("[" + hpmbObj.getRole_id() + "]"));
		argSet.put("username", hpmbObj.getUsername());
		return HarborHttpRequestBean.sendHttpPost("buildRelationshipBetweenUserAndProject", text, domain, url, argSet);
	}
 
	public synchronized JSONObject unbuildRelationshipBetweenUserAndProject(HarborUserBean userObj,
			HarborProjectMemberBean hpmbObj) {
		String text = userObj.getUsername() + ":" + userObj.getPassword();
		String domain = userObj.getDomain();
		String url = "http://" + domain + "/api/projects/" + hpmbObj.getRelavent_project_id() + "/members/"
				+ hpmbObj.getUser_id();
		return HarborHttpRequestBean.sendHttpDelete("unbuildRelationshipBetweenUserAndProject", text, domain, url);
	}

	public JSONObject getTheRelationshipBetweenUserAndProject(HarborUserBean userObj, HarborProjectMemberBean hpmbObj) {
		String text = userObj.getUsername() + ":" + userObj.getPassword();
		String url = "http://" + userObj.getDomain() + "/api/projects/" + hpmbObj.getRelavent_project_id() + "/members/"
				+ hpmbObj.getUser_id();
		String domain = userObj.getDomain();
		return HarborHttpRequestBean.sendHttpGet("getTheRelationshipBetweenUserAndProject", text, domain, url);
	}

	public synchronized JSONObject updateRelationshipBetweenUserAndProject(HarborUserBean userObj, HarborProjectMemberBean hpmbObj) {
		String text = userObj.getUsername() + ":" + userObj.getPassword();
		String domain = userObj.getDomain();
		String url = "http://" + domain + "/api/projects/" + hpmbObj.getRelavent_project_id() + "/members/"
				+ hpmbObj.getUser_id();
		Map<String, Object> argSet = new LinkedHashMap<String, Object>();
		argSet.put("roles", JSONArray.parseArray("[" + hpmbObj.getRole_id() + "]"));
		argSet.put("username", hpmbObj.getUsername());
		return HarborHttpRequestBean.sendHttpPut("updateRelationshipBetweenUserAndProject", text, domain, url, argSet);

	}

}
