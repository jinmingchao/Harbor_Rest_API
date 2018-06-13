package cn.com.agree.ab.a4.server.harbor.bean;

public class HarborProjectMemberBean {
    private int role_id;
    private Integer user_id;
	private long relavent_project_id;
	private String username;
	
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
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
	
	public long getRelavent_project_id() {
		return relavent_project_id;
	}
	public void setRelavent_project_id(Integer relavent_project_id) {
		this.relavent_project_id = relavent_project_id;
	}
	
	//ProjectMemberOperatingService---buildRelationshipBetweenUserAndProject
	public HarborProjectMemberBean(int role_id, String username,long relavent_project_id) {
		super();
	    if(username==null||username.trim().equals(""))
	    	username="";
		this.role_id = role_id;
		this.username = username;
		this.relavent_project_id=relavent_project_id;
	}
	
	//ProjectMemberOperatingService---unbuildRelationshipBetweenUserAndProject
	public HarborProjectMemberBean(Integer user_id, long relavent_project_id) {
		super();
		this.user_id = user_id;
		this.relavent_project_id = relavent_project_id;
	}
	
	
    
}
