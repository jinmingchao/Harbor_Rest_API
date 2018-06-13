package cn.com.agree.ab.a4.server.harbor.bean;

public class HarborProjectMetaDataBean {
	private String project_name;
	private int is_public; // 项目是否为公共项目
	private boolean enable_content_trust;
	private boolean prevent_vulnerable_images_from_running;
	private String prevent_vulnerable_images_from_running_severity;
	private boolean automatically_scan_images_on_push;

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public int getIs_public() {
		return is_public;
	}

	public void setIs_public(int is_public) {
		this.is_public = is_public;
	}

	public boolean getEnable_content_trust() {
		return enable_content_trust;
	}

	public void setEnable_content_trust(boolean enable_content_trust) {
		this.enable_content_trust = enable_content_trust;
	}

	public boolean getPrevent_vulnerable_images_from_running() {
		return prevent_vulnerable_images_from_running;
	}

	public void setPrevent_vulnerable_images_from_running(boolean prevent_vulnerable_images_from_running) {
		this.prevent_vulnerable_images_from_running = prevent_vulnerable_images_from_running;
	}

	public String getPrevent_vulnerable_images_from_running_severity() {
		return prevent_vulnerable_images_from_running_severity;
	}

	public void setPrevent_vulnerable_images_from_running_severity(
			String prevent_vulnerable_images_from_running_severity) {
		this.prevent_vulnerable_images_from_running_severity = prevent_vulnerable_images_from_running_severity;
	}

	public boolean getAutomatically_scan_images_on_push() {
		return automatically_scan_images_on_push;
	}

	public void setAutomatically_scan_images_on_push(boolean automatically_scan_images_on_push) {
		this.automatically_scan_images_on_push = automatically_scan_images_on_push;
	}

	// createNewProject
	public HarborProjectMetaDataBean(String project_name, int is_public, boolean enable_content_trust,
			boolean prevent_vulnerable_images_from_running, String prevent_vulnerable_images_from_running_severity,
			boolean automatically_scan_images_on_push) {
		super();
		this.project_name = project_name;
		this.is_public = is_public;
		this.enable_content_trust = enable_content_trust;
		this.prevent_vulnerable_images_from_running = prevent_vulnerable_images_from_running;
		this.prevent_vulnerable_images_from_running_severity = prevent_vulnerable_images_from_running_severity;
		this.automatically_scan_images_on_push = automatically_scan_images_on_push;
	}

}
