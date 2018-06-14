package cn.com.agree.ab.a4.server.harbor.test;



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
// 		System.out.println(re1.toJSONString());
		//3.UserOperating--deleteUserByUserId
//		HarborUserBean user = new HarborUserBean("192.168.10.200:80", "admin", "admin");
//		JSONObject re2 = uos.deleteUserByUserId(user,17);
//		System.out.println(re2.toJSONString());
		//4.UserOperating--endueUserSysAdminAuth
//		HarborUserBean user = new HarborUserBean("192.168.10.200:80", "admin", "admin");
//		JSONObject re3 = uos.endueUserSysAdminAuth(user,20,0);		
//		System.out.println(re3.toJSONString());
		//5.UserOperating--GetCurrentUser
//		HarborUserBean user = new HarborUserBean("192.168.10.200:80", "admi", "admin");
//		JSONObject re4 = uos.getCurrentUser(user);
//		System.out.println(re4.toJSONString());
		//6.UserOperating--getUserInfoByUserId
//		HarborUserBean user = new HarborUserBean("192.168.10.200:80", "admin", "admin",1);
//		JSONObject re5 = uos.getUserInfoByUserId(user);
//		System.out.println(re5.toJSONString());
		//7.UserOperating--searchUser
//		HarborUserBean user = new HarborUserBean("192.168.10.200:80","admin","admin",4);
//		JSONObject re6 = uos.getUserInfoByUserId(user);
//		System.out.println(re6.toJSONString());
		//8.UserOperating--modifyUserPwdByUserId
//		HarborUserBean authUser = new HarborUserBean("192.168.10.200:80","adm1","Adm1");
//		HarborUserBean modifiedUser=new HarborUserBean(3,"Adm1adm1","     ");
//		JSONObject re7 = uos.modifyUserPwdByUserId(authUser,modifiedUser);
//		System.out.println(re7.toJSONString());
		//9.UserOperating--updateUserBaseInfo
		HarborUserBean authUser = new HarborUserBean("192.168.10.200","admin","admin");
		HarborUserBean modifiedUser=new HarborUserBean(23,"adm1adm1@163.com","test6666","test66666");		
		JSONObject re8 =uos.updateUserInfoByUserId(authUser, modifiedUser);
		System.out.println(re8.toJSONString());	


	}
}
