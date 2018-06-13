/**
 * 测试harbor中project相关restful api
 */
package httpClient.httpClient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author jinmingchao
 *
 */
public class TestProjectApi {
//	private static final Log log = LogFactory.getLog(httpUtil.class);
	private HttpClient httpClient = null;
	@SuppressWarnings("unused")
	private HttpClient postClient = null;
	private HttpResponse httpResponse = null;
	private List<NameValuePair> list = null;

	@Before
	public void initRequest() {
		// 设置组件参数, HTTP协议的版本:1.1
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
		HttpProtocolParams.setUseExpectContinue(params, true);
		// //权限设置
		// //凭证注册器
		// CredentialsProvider credsProvider = new BasicCredentialsProvider();
		// //注册1 somehost主机名 任意PORT 指定用户U1 密码P1
		// credsProvider.setCredentials(
		// new AuthScope("somehost", AuthScope.ANY_PORT),
		// new UsernamePasswordCredentials("u1", "p1"));
		// 初始化HttpClient对象
		// 设置登录参数
		list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("admin", "admin"));// 登录参数
		httpClient = new DefaultHttpClient(params);

	}

	@After
	public void endRequest() {
        
	}

	/**
	 * @author:jinmingchao
	 * @date:2018-05-04 
	 * @usage:设置通用请求头
	 * @args:
	 *      tog(HttpRequestBase)
	 *      content_type(String) 
	 * @comment:
	 */
	private HttpRequestBase curlSetHeader(HttpRequestBase tog, String content_type) {
		if(!content_type.trim().equals(""))
		tog.setHeader("Content-Type","application/json");
		tog.setHeader("Authorization","Basic YWRtaW46YWRtaW4=");
		tog.setHeader("Host", "192.168.10.200");
		tog.setHeader("User-Agent","curl/7.29.0");
		tog.setHeader("accept", "application/json");
		for(Header h:tog.getAllHeaders()) {
			System.out.println(h.getName()+":"+h.getValue());
		}		
		return tog;
	}

	/**
	 * @author:jinmingchao
	 * @date:2018-05-07 
	 * @usage:根据提供的参数,获得所有相关的项目和仓库列表
	 * @args:
	 *      optional:
	 * 			name(string)	项目名称
	 * @comment:
	 * 		对应curl指令:
	 *      curl -ivX 
	 *      GET "http://192.168.10.200:80/api/search?q=library" 
	 *      -H "accept: application/json"
	 */
	@Test
	public void testSearchProjectOrRepository() {
		int code = 0;
		String responseEntity = "啥都没";
//		String authStr;
		//测试用检索条件:library
		String name="library";
		HttpGet get;
		try {
//			authStr = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
			String url = "http://192.168.10.200:80/api/search?q="+name;
//			get = new HttpGet(url + "?" + authStr);
			get = new HttpGet(url);
			get=(HttpGet) curlSetHeader(get,"");			
			
			httpResponse = httpClient.execute(get);
			@SuppressWarnings("unused")
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("响应体:" + responseEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * @author:jinmingchao
	 * @date:2018-05-07 
	 * @usage:获得所有状态为公开的项目信息,并可以在项目名上进行过滤。
	 * @args:
	 *       optional:
	 * 		 project_id(integer)   项目id
	 * @comment:
	 *       对应curl指令:
	 *       curl -ivX 
	 *       GET "http://192.168.10.200:80/api/projects/1" 
	 *       -H "accept: application/json"
	 */
	@Test
	public void testSearchProjects() {
		int code = 0;
		String responseEntity = "啥都没";
        //测试检索项目名:
		String project_name="os1";
		HttpGet get;
		try {			
			String url = "http://192.168.10.200:80/api/projects?name="+project_name;
			get = new HttpGet(url);
			get=(HttpGet) curlSetHeader(get,"");		
			httpResponse = httpClient.execute(get);
			@SuppressWarnings("unused")
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("响应体:" + responseEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * @author:jinmingchao
	 * @date:2018-05-07 
	 * @usage:检查与用户提供的项目名称匹配的项目是否存在
	 * @args:
	        required:
	        	*project_name(string)	项目名称
			optional:
	 * @comment:
	 *      对应curl指令:
	 *      curl -ivX 
	 *      HEAD "http://192.168.10.200:80/api/projects?project_name=abs1" 
	 *      -H "accept: application/json"		
	 */
	@Test
	public void testIfProjectExsited() {
		int code = 0;
        //测试检索项目名:
		String project_name="abs1";
		HttpHead head;
		try {			
			String url = "http://192.168.10.200:80/api/projects?project_name="+project_name;
			head=new HttpHead(url);		
			head=(HttpHead) curlSetHeader(head,"");		
			System.out.println("请求url:"+url);
			httpResponse = httpClient.execute(head);
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	/**
	 * @author jinmingchao 
	 * 用途:提供一个项目名称的请求体,以便创建一个新的项目
	 * 对应curl指令:
	 * curl -ivX POST "http://192.168.10.200:80/api/projects" 
	 * -u "admin:admin" 
	 * -H "accept: application/json" 
	 * -H "Content-Type: application/json" 
	 * -d 
	 * "{ \"project_name\": \"string\",\"public\":0,\"enable_content_trust\":true,\"prevent_vulnerable_images_from_running\":true,\"prevent_vulnerable_images_from_running_severity\":\"string\",\"automatically_scan_images_on_push\": true}"
	 */
	@Test
	public void testCreateNewProject() {
		int code = 0;
		String responseEntity = "啥都没";
        //测试检索项目名:
		String request_body="{\"project_name\":\"string\",\"public\":0,\"enable_content_trust\":true,\"prevent_vulnerable_images_from_running\":true,\"prevent_vulnerable_images_from_running_severity\":\"string\",\"automatically_scan_images_on_push\": true}";
		HttpPost post;
		try {			
			String url = "http://192.168.10.200:80/api/projects";
			post=new HttpPost(url);		
			post=(HttpPost) curlSetHeader(post,"j");
			StringEntity se = new StringEntity(request_body);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(se);
			System.out.println("请求url:"+url);
			httpResponse = httpClient.execute(post);
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	/**
	 * @author jinmingchao 
	 * 用途:根据项目id返回项目信息
	 * 对应curl指令:curl -ivX GET "http://192.168.10.200:80/api/projects/1" -H "accept: application/json"
	 */
	@Test
	public void testGetProjectPropertiesByProjectId() {
		int code = 0;
		String responseEntity = "啥都没";
//		String authStr;
		//测试用检索条件:1
		Integer project_id = 7;
		HttpGet get;
		try {
//			authStr = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
			String url = "http://192.168.10.200:80/api/projects/"+project_id;
//			get = new HttpGet(url + "?" + authStr);
			get = new HttpGet(url);
			get=(HttpGet) curlSetHeader(get,"");						
			httpResponse = httpClient.execute(get);
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("响应体:" + responseEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}

	
	
	/**
	 * @author:jinmingchao
	 * @date:2018-05-07 
	 * @usage:
	 * 		以项目id为查询条件,修改匹配项目信息
	 * @args:
	        required:
	        	*project_id (integer)	项目id
	        	*project(request body)
			optional:
	 * @comment:
	 *      对应curl指令:
	 *      curl -ivX PUT -u "admin:admin" --header 'Content-Type: application/json' --header 'Accept: text/plain' -d '{
  			"project_id": 6,
  			"owner_id": 0,
  			"name": "",
  			"creation_time": "",
  			"update_time": "",
  			"deleted": 0,
  			"owner_name": "",
  			"togglable": false,
  			"current_user_role_id": 0,
  			"repo_count": 0,
			  "metadata": {
			    "public": "true"
			  }
			}' 'http://192.168.10.200/api/projects/6'
		
	 */
	@Test
	public void testUpdateProjectPropertiesByProjectId() {
		int code = 0;
		String responseEntity = "啥都没";
		String request_body ="{\"project_id\": 7,\"owner_id\": 1, \"name\": \"string_string\", \"creation_time\": \"2018-04-26T18:40:38Z\",\"update_time\": \"2018-04-26T18:40:38Z\",\"deleted\": 0,\"owner_name\": \"\", \"togglable\": false, \"current_user_role_id\": 0, \"repo_count\": 0, \"metadata\": { \"public\": \"false\" }}";
		//测试用检索条件:1
		Integer project_id = 7;
		HttpPut put;
		try {
			String url = "http://192.168.10.200:80/api/projects/"+project_id;
			put = new HttpPut(url);
			StringEntity se = new StringEntity(request_body);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");//发送json数据需要设置contentType
            put.setEntity(se);
            put=(HttpPut) curlSetHeader(put,"");
			httpResponse = httpClient.execute(put);
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}	
	/**
	 * @author:jinmingchao
	 * @date:2018-05-04 
	 * @usage:以项目id为查询条件,删除(冻结)指定项目(项目id)
	 * @args:
	 *      项目id	project_id(Integer)
	 * @comment:
	 *      curl指令:
	 *      curl -ivX 
	 *      DELETE "http://192.168.10.200:80/api/projects/7" 
	 *      -u "admin:admin" 
	 *      -H "accept: application/json"
	 */
	@Test
	public void testDeleteProjectByProjectId() {
		int code = 0;
		String responseEntity = "啥都没";
		//测试用检索条件:7
		int project_id=7;
		HttpDelete delete;
		try {
			String url = "http://192.168.10.200:80/api/projects/"+project_id;
			delete = new HttpDelete(url);
			delete=(HttpDelete) curlSetHeader(delete,"");					
			httpResponse = httpClient.execute(delete);
			@SuppressWarnings("unused")
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);
//			responseEntity = EntityUtils.toString(httpResponse.getEntity());
//			System.out.println("响应体:" + responseEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * @author jinmingchao 
	 * 用途:通过项目id获得指定项目的元数据信息
	 * 对应curl指令:curl -ivX GET "http://192.168.10.200:80/api/projects/3/metadatas" 
	 * -H "accept: application/json" 
	 * 
	 */
	
	
	/**
	 * @author:jinmingchao
	 * @date:2018-05-07 
	 * @usage:通过项目id获得指定项目的元数据信息
	 * @args:
	        required:
	        	*project_id(interger)	项目id
			optional:
	 * @comment:
	 *      对应curl指令:
	 *      curl -ivX 
	 *      GET "http://192.168.10.200:80/api/projects/3/metadatas" 
	 *      -H "accept: application/json"		
	 */
	@Test
	public void testGetProjectMetadataByProjectId() {
		int code = 0;
		String responseEntity = "啥都没";
		//测试用检索条件:1
		int project_id=1;
		HttpGet get;
		try {
			String url = "http://192.168.10.200:80/api/projects/"+project_id+"/metadatas";
			get = new HttpGet(url);
			get=(HttpGet) curlSetHeader(get,"");					
			httpResponse = httpClient.execute(get);
			@SuppressWarnings("unused")
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("响应体:" + responseEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	/**
	 * @author jinmingchao 
	 * 用途:获取项目所有成员的列表(项目id)
	 * 对应curl指令:curl -ivX GET 
	 * "http://192.168.10.200:80/api/projects/2/members" 
	 * -u "admin:admin" -H "accept: application/json" 
	 * 
	 */
	@Test
	public void testGetProjectMemberListById() {
		int code = 0;
		String responseEntity = "啥都没";
		//测试用检索条件:2
		int project_id=2;
		HttpGet get;
		try {
			String url = "http://192.168.10.200:80/api/projects/"+project_id+"/members";
			get = new HttpGet(url);
			get=(HttpGet) curlSetHeader(get,"");					
			httpResponse = httpClient.execute(get);
			@SuppressWarnings("unused")
			HttpEntity httpEntity = httpResponse.getEntity();
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);
			responseEntity = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("响应体:" + responseEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	


}