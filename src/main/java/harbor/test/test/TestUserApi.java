package harbor.test.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.agree.ab.a4.server.harbor.bean.HarborUserBean;
import cn.com.agree.ab.a4.server.harbor.service.UserOperatingService;

public class TestUserApi {
	public static void main(String[] args) {
		UserOperatingService uos=new UserOperatingService();
		// 1.UserOperating--createUserBySysAdminAuthority
//		HarborUserBean createdUser = new HarborUserBean("192.168.10.200:80","test555", "test555@qq.com", "Test555test555", "Test555", "tteesstt555");		
//		JSONObject re = uos.createUserBySysAdminAuthority(createdUser);
//		System.out.println(re.toJSONString());
		// 2.UserOperating--getStatisticAboutUser
//		HarborUserBean searchUser = new HarborUserBean("192.168.10.200:80", "admin", "amin");
//		JSONObject re1 =uos.getStatisticAboutUser(searchUser);
//		System.out.println(re1.toJSONString());
		//3.UserOperating--deleteUserByUserId
//		HarborUserBean user = new HarborUserBean("192.168.10.200:80", "admin", "amin");
//		JSONArray re2 = UserOperating.deleteUserByUserId(user,21);
//		System.out.println(re2.toJSONString());
		//4.UserOperating--endueUserSysAdminAuth
//		JSONArray re3 = UserOperating.endueUserSysAdminAuth("192.168.10.200:80", "admin", "admin",111,0);
//		System.out.println(re3.toJSONString());
		//5.UserOperating--GetCurrentUser
//		JSONArray re4 = UserOperating.getCurrentUser("192.168.10.200:80", "adm1", "Adm1adm1");
//		System.out.println(re4.toJSONString());
		//6.UserOperating--GetUserInfoByUserId
//		JSONArray re5 = UserOperating.getUserInfoByUserId("192.168.10.200:80", "admin", "admin",100);
//		System.out.println(re5.toJSONString());
		//7.UserOperating--getUserInfoByUserId
//		HarborUserBean hu7 = new HarborUserBean("192.168.10.200:80","admin","admin",4);
//		JSONObject re6 = uos.getUserInfoByUserId(hu7);
//		System.out.println(re6.toJSONString());
		//8.UserOperating--modifyUserPwdByUserId
//		HarborUser hu1 = new HarborUser(null,"test666@qq.com","test6666");
//		JSONArray re7 = UserOperating.modifyUserPwdByUserId("192.168.10.200:80", "admin","admin",hu1 );
//		System.out.println(re7.toJSONString());
		//9.UserOperating--testUpdateUserBasicInfo
//		HarborUser hu2 = new HarborUser(null,"test666@qq.com","test6666","test66666");
//		JSONArray re8 = UserOperating.updateUserBaseInfo("192.168.10.200:80", "admin", "admin", hu1);
//		System.out.println(re8.toJSONString());	
		//10.UserOperating--

	}
}
