/**
 * 测试harbor中user相关restful api
 */
package httpClient.httpClient;

import java.io.IOException;
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
public class TestUserApi {
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
	 * @author jinmingchao 
	 * 用途:设置通用请求头
	 * @param content_type 
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
	 * @date:2018-04-28 
	 * @usage:统计与指定用户相关的项目数与镜像仓库数
	 * @args:none
	 * @comment:
	 * 		curl指令:
	 *             curl -ivX GET 
	 *             "http://192.168.10.200:80/api/statistics" 
	 *             -u "admin:admin" -H "accept: application/json"
	 */
	@Test
	public void testStatistics() {
		int code = 0;
		String responseEntity = "啥都没";
		HttpGet get;
		try {			
			String url = "http://192.168.10.200:80/api/statistics";
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
	 * @date:2018-04-28 
	 * @usage:获取所有的注册用户信息(目前只有管理员用户可用该功能)
	 * @args:
	 *      optional:
	 *              username(string)
	 *              email(string)
	 *              page(interger)
	 *              page_size(interger)
	 * @comment:curl指令:curl -ivX GET "http://192.168.10.200:80/api/users" -u "admin:admin" -H "accept: application/json"
	 */
	@Test
	public void testGetUserList(){
		int code = 0;
		String responseEntity = "啥都没";
		//测试可选参数
		String username="work";
		HttpGet get;
		try {			
			String url = "http://192.168.10.200:80/api/users?username="+username;
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
	 * @date:2018-04-28 
	 * @usage:创建一个新用户
	 * @args:user(request body)
	 * @comment:
	 *          curl指令:
	 *          curl -ivX 
	 *          POST "http://192.168.10.200:80/api/users" 
	 *          -H "accept: application/json" 
	 *          -H "Content-Type: application/json" 
	 *          -d "{ \"user_id\": 12, \"username\": \"test02\", \"email\": \"test02@qq.com\", \"password\": \"Test02test02\", \"realname\": \"test02\", \"comment\": \"test_user_create\", \"deleted\": 0, \"role_name\": \"\", \"role_id\": 0, \"has_admin_role\": 0, \"reset_uuid\": \"\", \"Salt\": \"\", \"creation_time\": \"2018-04-26T14:14:00Z\", \"update_time\": \"2018-04-26T14:14:00Z\"}"
	 */
	
	
	/**
	 * @author jinmingchao
	 * @date 2018-05-07 
	 * @usage 
	 * @args
	        required:
				user(request body)	一个包含创建用户信息的请求体
			optional:
	 * @comment:
	 *      对应curl指令	:
				curl -ivX 
				POST "http://192.168.10.200:80/api/users" 
				-H "accept: application/json" 
				-H "Content-Type: application/json" 
				-d "{ \"user_id\": 12, \"username\": \"test02\", \"email\": \"test02@qq.com\", \"password\": \"Test02test02\", \"realname\": \"test02\", \"comment\": \"test_user_create\", \"deleted\": 0, \"role_name\": \"\", \"role_id\": 0, \"has_admin_role\": 0, \"reset_uuid\": \"\", \"Salt\": \"\", \"creation_time\": \"2018-04-26T14:14:00Z\", \"update_time\": \"2018-04-26T14:14:00Z\"}"				
	 *      				
	 */
	@Test
	public void testCreateUser() {
		int code = 0;
		String responseEntity = "啥都没";
        //测试检索项目名:
		String request_body="{ \"user_id\": 16, \"username\": \"test04\", \"email\": \"test02@qq.com\", \"password\": \"Test02test02\", \"realname\": \"test02\", \"comment\": \"test_user_create\", \\\"deleted\\\": 0, \"role_name\": \"\", \"role_id\": 0, \\\"has_admin_role\": 0, \"reset_uuid\": \"\", \"Salt\": \"\", \"creation_time\": \\\"2018-04-26T14:14:00Z\", \"update_time\": \"2018-04-26T14:14:00Z\"}";
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
	 * @author:jinmingchao
	 * @date:2018-05-04 
	 * @usage:获取当前登录用户的信息
	 * @args:null
	 * @comment:
	 *         curl指令:
	 * 					curl -ivX 
	 *                  GET "http://192.168.10.200:80/api/users/current"
	 *                  -u "admin:admin" 
	 *                  -H "accept: application/json"
	 */
	@Test
	public void testGetCurrentUser() {
		int code = 0;
		String responseEntity = "啥都没";
		HttpGet get;
		try {			
			String url = "http://192.168.10.200/api/users/current";
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
	 * @date:2018-05-04 
	 * @usage:通过用户id,获得匹配用户的信息
	 * @args:
	 *       user_id(integer)
	 * @comment:
	 *       curl指令:
	 *       curl -ivX 
	 *       GET "http://192.168.10.200:80/api/users/11" 
	 *       -u "admin:admin" -H "accept: application/json"
	 */
	@Test
	public void testGetUserInfoByUserId() {
		int code = 0;
		String responseEntity = "啥都没";
		//测试用用户id
		Integer user_id=4;
		HttpGet get;
		try {			
			String url = "http://192.168.10.200:80/api/users/"+user_id;
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
	 * @date:2018-05-04 
	 * @usage:通过指定的用户id,修改匹配用户的信息
	 * @args:
	 *       user_id(interger)
	 *       profile(request body)
	 * @comment:
	 *       curl指令:
	 *       curl -ivX PUT --header 'Content-Type: application/json' -u "admin:admin" --header 'Accept: text/plain' -d '{
     *       "email": "TTTTTT@163.com",
  	 *	     "realname": "jinmingchao",
  	 *       "comment": "I_update_it"
     *       }' 'http://192.168.10.200/api/users/4'
	 */
	@Test
	public void testUpdateUserBasicInfo() {
		int code = 0;	
		String request_body ="{\r\n" + 
				"  \"email\": \"TTTTTT@163.com\",\r\n" + 
				"  \"realname\": \"stringg\",\r\n" + 
				"  \"comment\": \"I_update_it\"\r\n" + 
				"}";
		//测试用戶id
		Integer user_id = 4;
		HttpPut put;
		try {
			String url = "http://192.168.10.200/api/users/"+user_id;
			put = new HttpPut(url);
			StringEntity se = new StringEntity(request_body);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");//发送json数据需要设置contentType
            put.setEntity(se);
            put=(HttpPut) curlSetHeader(put,"");
			httpResponse = httpClient.execute(put);
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
	 * @usage:通过指定用户id删除(冻结)匹配的用户
	 * @args:
	 *      user_id(interger)
	 * @comment:
	 *      curl -ivX 
	 *      DELETE "http://192.168.10.200:80/api/users/12" 
	 *      -u "admin:admin" 
	 *      -H "accept: application/json"
	 */
	@Test
	public void testDeleteUserByUserId() {
		int code = 0;
		String responseEntity = "啥都没";
		//测试用user_id
		int user_id=4;
		HttpDelete delete;
		try {
			String url = "http://192.168.10.200:80/api/users/"+user_id;
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
	 * @author:jinmingchao
	 * @date:2018-05-04 
	 * @usage:通过用户id,修改匹配用户密码
	 * @args:
	 *      用户id	user_id(integer)
	 *      包含新旧密码的请求体	password(request body)
	 * @comment:
	 *      curl指令:
	 *      curl -ivX 
	 *      PUT "http://192.168.10.200:80/api/users/14/password"
	 *      -u "admin:admin" 
	 *      -H "accept: application/json" 
	 *      -H "Content-Type: application/json" 
	 *      -d "{ \"old_password\": \"Test02test02\", \"new_password\": \"Test022test022\"}"
	 */
	@Test
	public void testModifyUserPwdByUserId(){
		int code = 0;	
		String request_body ="{ \"old_password\": \"Test1111\", \"new_password\": \"Test2222\"}";
		//测试用戶id
		Integer user_id = 5;
		HttpPut put;
		try {
			String url = "http://192.168.10.200:80/api/users/"+user_id+"/password";
			put = new HttpPut(url);
			StringEntity se = new StringEntity(request_body);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");//发送json数据需要设置contentType
            put.setEntity(se);
            put=(HttpPut) curlSetHeader(put,"");
			httpResponse = httpClient.execute(put);
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
	 * @usage:通过用户id,赋予匹配用户系统管理员权限
	 * @args:
	 *      用户id	user_id(integer)
	 *      管理员权限的字段(1是,0否)	has_admin_role(request body)
	 * @comment:
	 *      curl指令:
	 *      curl -ivX 
	 *      PUT "http://192.168.10.200:80/api/users/14/sysadmin" 
	 *      -u "admin:admin" 
	 *      -H "accept: application/json" 
	 *      -H "Content-Type: application/json" 
	 *      -d "{ \"has_admin_role\": 1}"
	 */
	@Test
	public void testEndueUserAdminAuth() {
		int code = 0;	
		String request_body ="{\"has_admin_role\": 1}";
		//测试用戶id
		Integer user_id = 5;
		HttpPut put;
		try {
			String url = "http://192.168.10.200:80/api/users/"+user_id+"/sysadmin";
			put = new HttpPut(url);
			StringEntity se = new StringEntity(request_body);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/json");//发送json数据需要设置contentType
            put.setEntity(se);
            put=(HttpPut) curlSetHeader(put,"");
			httpResponse = httpClient.execute(put);
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("状态码:" + code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


}