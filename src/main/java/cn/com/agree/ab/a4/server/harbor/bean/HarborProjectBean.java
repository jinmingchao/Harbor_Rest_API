package cn.com.agree.ab.a4.server.harbor.bean;

/**
 * @author jinmingchao
 *
 * @usage harbor项目(镜像仓库)对象
 */
public class HarborProjectBean {
	private long project_id;			//项目id
	private Integer owner_id;			//项目所有者id
	private String  name;				//项目名称
	private String  creation_time;		//项目创建时间
	private String  update_time;		//项目更新时间
	private Integer deleted;			//项目是否被删除标记:0 未删除;1 已删除
	//ProjectOperatingService---searchProjects
	private String is_public;			//项目是否为公共项目
	private String owner_name;			//项目拥有者名称
	private Integer page;				//总页数
	private Integer page_size;          //每页显示结果数
	
	
	
	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	public Integer getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getIs_public() {
		return is_public;
	}

	public void setIs_public(String is_public) {
		this.is_public = is_public;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
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
    //searchProjects--过滤对象
	public HarborProjectBean(String name, String is_public, String owner_name, Integer page, Integer page_size) {
		super();
		this.name = name==null||name.trim().equals("")?"":name.trim();
		this.is_public = is_public==null||is_public.trim().equals("")||(!is_public.trim().equals("true")&&!is_public.trim().equals("false"))?"":is_public.trim();
		this.owner_name = owner_name==null||owner_name.trim().equals("")?"":owner_name.trim();
		this.page = page <= 0?1:page;
		if(page_size > 100)
			page_size=100;
		
		this.page_size = page_size <= 0?10:page_size;
		System.out.println("name:"+this.name);
		System.out.println("is_public:"+this.is_public);
		System.out.println("owner_name:"+this.owner_name);
		System.out.println("page:"+this.page);
		System.out.println("page_size:"+this.page_size);
	}
	
	

}
