package cn.com.agree.ab.a4.server.harbor.test;

import com.alibaba.fastjson.JSONObject;

import cn.com.agree.ab.a4.server.harbor.bean.HarborProjectBean;
import cn.com.agree.ab.a4.server.harbor.bean.HarborUserBean;
import cn.com.agree.ab.a4.server.harbor.service.ProjectOperatingService;

public class TestProjectApi {
	public static void main(String[] args) {
		//1.ProjectOperatingService-createNewProject
//		HarborUser u1=new HarborUser("192.168.10.200:80","admin","admin");
//		HarborProjectMetaData hpmd1=new HarborProjectMetaData("abs3",2,true,true,"",false);
//		JSONArray re1=ProjectOperating.createNewProject(u1,hpmd1);
//		System.out.println(re1);
		//2.ProjectOperatingService-DeleteProjectByProjectId
		/*HarborUser u2=new HarborUser("192.168.10.200:80","admin","admin");		
		JSONArray re2=ProjectOperating.deleteProjectByProjectId(u2,10);
		System.out.println(re2);*/
		//3.ProjectOperatingService-GetProjectMemberListByProjectId
//		HarborUser u3=new HarborUser("192.168.10.200:80","","");		
//		JSONArray re3=ProjectOperating.getProjectMemberListByProjectId(u3,1);
//		System.out.println(re3);
		//4.ProjectOperatingService-getProjectMetadataByProjectId
//		HarborUser u4=new HarborUser("192.168.10.200:80","admin","admin");		
//		JSONArray re4=ProjectOperating.getProjectMetadataByProjectId(u4,6);
//		System.out.println(re4);
		//5.ProjectOperatingService-getProjectPropertiesByProjectId
//		HarborUser u5=new HarborUser("192.168.10.200:80","admin","admin");
//		JSONArray re5=ProjectOperating.getProjectPropertiesByProjectId(u5,23131);
//		System.out.println(re5);
		//6.ProjectOperatingService-ifProjectExsited
//		HarborUser u6=new HarborUser("192.168.10.200:80","admin","admin");
//		JSONArray re6=ProjectOperating.ifProjectExsited(u6, "abs1");
//		System.out.println(re6);
		//7.ProjectOperatingService-searchProjectOrRepository
//		HarborUser u7=new HarborUser("192.168.10.200:80","adn","admin");
//		JSONArray re7=ProjectOperating.searchProjectOrRepositoryByKeyWord(u7, "dsada");
//		System.out.println(re7);
		//8.ProjectOperatingService-searchProjects
		HarborUserBean u8=new HarborUserBean("192.168.10.200:80","admin","admin");
		HarborProjectBean hp8=new HarborProjectBean("","","", 2, 2);
		JSONObject re8=ProjectOperatingService.searchProjects(u8, hp8);
		System.out.println(re8.toJSONString());

	}
}
