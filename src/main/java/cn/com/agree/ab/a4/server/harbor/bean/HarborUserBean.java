package cn.com.agree.ab.a4.server.harbor.bean;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

/**
 * @author jinmingchao
 *
 * @usage harbor用户对象
 */
public class HarborUserBean {
	    private String domain;			//harbor节点域名或ip:port
        private Integer user_id;		//用户id
        private String  username;		//用户名称
        private String  email;			//用户email
        private String  password;		//用户密码
        private String  old_password;   //ModifyUserPwdByUserId-用户旧密码
        private String  new_password;	//ModifyUserPwdByUserId-用户新密码
        private String  realname;		//用户真实姓名
        private String  comment;		//用户备注
        private Integer deleted;		//用户是否被删除标记:0 未删除;1 已删除
        private String  reset_uuid;
        private String  salt;
        private	Integer sysadmin_flag;	//用户是否有管理员权限标记:0 没有:1 有
        private String  creation_time;	//用户创建时间
        private String  update_time;	//用户更新时间
        
        
      //UserOperatingService---searchUsers
    	private Integer page;				//总页数
    	private Integer page_size;          //每页显示结果数
        
		public String getDomain() {
			return domain;
		}
		public void setDomain(String domain) {
			this.domain = domain;
		}
		public Integer getUser_id() {
			return user_id;
		}
		public void setUser_id(Integer user_id) {
			this.user_id = user_id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

		public String getOld_password() {
			return old_password;
		}
		public void setOld_password(String old_password) {
			this.old_password = old_password;
		}
		public String getNew_password() {
			return new_password;
		}
		public void setNew_password(String new_password) {
			this.new_password = new_password;
		}
		public String getRealname() {
			return realname;
		}
		public void setRealname(String realname) {
			this.realname = realname;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public Integer getDeleted() {
			return deleted;
		}
		public void setDeleted(Integer deleted) {
			this.deleted = deleted;
		}
		public String getReset_uuid() {
			return reset_uuid;
		}
		public void setReset_uuid(String reset_uuid) {
			this.reset_uuid = reset_uuid;
		}
		public String getSalt() {
			return salt;
		}
		public void setSalt(String salt) {
			this.salt = salt;
		}
		public Integer getSysadmin_flag() {
			return sysadmin_flag;
		}
		public void setSysadmin_flag(Integer sysadmin_flag) {
			this.sysadmin_flag = sysadmin_flag;
		}
		public String getCreation_time() {
			return creation_time;
		}
		public void setCreation_time(String creation_time) {
			this.creation_time = creation_time;
		}
		public String getUpdate_time() {
			return update_time;
		}
		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}
		
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		public Integer getPage_size() {
			return page_size;
		}
		public void setPage_size(Integer page_size) {
			this.page_size = page_size;
		}
		/**
		 * 
		 * @author jinmingchao
		 * @usage createUserBySysAdminAuthority; 
		 * @param user_id
		 * @param username
		 * @param email
		 * @param password
		 * @param realname
		 * @param comment
		 * @param deleted
		 * @param reset_uuid
		 * @param salt
		 * @param sysadmin_flag
		 * @param creation_time
		 * @param update_time
		 * @comment 
		 *        user_id harbor自己uuid控制
		 *        deleted标记设置为0(未删除)	
		 *        reset_uuid设置为"":即不重置uuid
		 *        salt设为"":即不添加其他加密字符串
		 *        sysadmin_flag设置为0:即新用户没有系统管理员权限
		 *        (还有问题)creation_time和update_time使用本地系统时区时间戳"
		 *        
		 */
		public HarborUserBean(String username, String email, String password, String realname,
				String comment, Integer deleted, String reset_uuid, String salt,
				String creation_time, String update_time) {
			super();
			this.user_id = 0;
			this.username = username;
			this.email = email;
			this.password = password;
			this.realname = realname;
			this.comment = comment;
			this.deleted = deleted;
			this.reset_uuid = "";
			this.salt = "";
			this.sysadmin_flag = 0;
			this.creation_time = getCurrentTimeStamp();
			this.update_time = getCurrentTimeStamp();	
		}
		
		
		//UserOperatingService--createUserBySysAdminAuthority
		public HarborUserBean(String domain,String username, String email, String password, String realname, String comment) {
			super();			
			this.domain=domain;
			this.username = username;
			this.email = email;
			this.password = password;
			this.realname = realname;
			this.comment = comment;
		}
		
		//UserOperatingService--getUserInfoByUserId
		//                    --updateUserInfoByUserId
		public HarborUserBean(Integer user_id, String email, String realname, String comment) {
			super();
			this.user_id = user_id;
			this.email = email;
			this.realname = realname;
			this.comment = comment;
		}
		
		//UserOperatingService--ModifyUserPwdByUserId
		public HarborUserBean(Integer user_id, String old_password, String new_password) {
			super();
			this.user_id = user_id;
			this.old_password = old_password;
			this.new_password = new_password;
		}
		
		//用户权限信息对象,放harbor域名/用户名/密码
		//使用该构造器的方法
		//UserOperatingService--getStatisticAboutUser
		public HarborUserBean(String domain, String username, String password) {			
			super();
			this.domain = domain==null||domain.trim().equals("")?"":domain.trim();
			this.username =  username==null||username.trim().equals("")?"":username.trim();
			this.password = password==null||password.trim().equals("")?"":password.trim();		
		}
		//UserOperatingService--getUserInfoByUserId
        public HarborUserBean(String domain, String username, String password,Integer user_id) {			
			super();
			this.domain = domain==null||domain.trim().equals("")?"":domain.trim();
			this.username =  username==null||username.trim().equals("")?"":username.trim();
			this.password = password==null||password.trim().equals("")?"":password.trim();
			this.user_id=user_id<=0||user_id>Integer.MAX_VALUE?0:user_id;
		}
		
		

		//ProjectMemberOperatingService--unbuildRelationshipBetweenUserAndProject
		//							   --getTheRelationshipBetweenUserAndProject
		public HarborUserBean(String domain, Integer user_id, String username, String password) {
			super();
			this.domain = domain;
			this.user_id = user_id;
			this.username = username;
			this.password = password;
		}
		
		
		
		@SuppressWarnings("unused")
		private String getCurrentTimeStamp() {			
			return Instant.now().toString(); 					
		}
        
        
}
