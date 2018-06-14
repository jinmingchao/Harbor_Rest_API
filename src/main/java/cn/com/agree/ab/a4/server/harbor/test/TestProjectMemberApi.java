package cn.com.agree.ab.a4.server.harbor.test;

import com.alibaba.fastjson.JSONObject;

import cn.com.agree.ab.a4.server.harbor.bean.HarborProjectMemberBean;
import cn.com.agree.ab.a4.server.harbor.bean.HarborUserBean;
import cn.com.agree.ab.a4.server.harbor.service.ProjectMemberOperatingService;

public class TestProjectMemberApi {
	public static void main(String[] args) {
		ProjectMemberOperatingService pmo=new ProjectMemberOperatingService();
		//1.ProjectMemberOperatingService--getProjectMemberListByProjectId
//		HarborUserBean hu1 = new HarborUserBean("192.168.10.200:80","admin","admin");
//		JSONObject r1=pmo.getProjectMemberListByProjectId(hu1,6);
//		System.out.println(r1);
		//2.ProjectMemberOperatingService--buildRelationshipBetweenUserAndProjectByProjectId
//		HarborUserBean hu2 = new HarborUserBean("192.168.10.200:80","admin","admin");
//		HarborProjectMemberBean hpm2=new HarborProjectMemberBean(2,"test62",6);
//		JSONObject r2=pmo.buildRelationshipBetweenUserAndProject(hu2,hpm2);
//		System.out.println(r2);
		//3.ProjectMemberOperatingService--unbuildRelationshipBetweenUserAndProject
		HarborUserBean hu3 = new HarborUserBean("192.168.10.200:80","admin","admin");
		HarborProjectMemberBean hpm3=new HarborProjectMemberBean(20,6000);
		JSONObject r3=pmo.unbuildRelationshipBetweenUserAndProject(hu3,hpm3);
		System.out.println(r3);
		//4.ProjectMemberOperatingService--updateRelationshipBetweenUserAndProject
//		HarborUserBean hu4 = new HarborUserBean("192.168.10.200:80","admin","admin");
//		HarborProjectMemberBean hpm4=new HarborProjectMemberBean(20,6);
//		JSONObject r4=pmo.updateRelationshipBetweenUserAndProject(hu4,hpm4);
//		System.out.println(r4);
		//5.ProjectMemberOperatingService--getTheRelationshipBetweenUserAndProject
//		HarborUserBean hu5 = new HarborUserBean("192.168.10.200:80","admin","admin");
//		HarborProjectMemberBean hpm5=new HarborProjectMemberBean(1,1);
//		JSONObject r5=pmo.getTheRelationshipBetweenUserAndProject(hu5,hpm5);
//		System.out.println(r5);
	}
}
