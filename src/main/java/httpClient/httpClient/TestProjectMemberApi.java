/**
 * 测试harbor中project member相关restful api
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
public class TestProjectMemberApi {
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
	 * @usage:获得指定项目的所有成员信息
	 * @args:projecct_id(interger)
	 * @comment:
	 * curl指令:
	 * curl -ivX GET 
	 * "http://192.168.10.200:80/api/projects/2/members" 
	 * -u "admin:admin" -H "accept: application/json"
	 */
	@Test
	public void testGetProjectMemberById(){
		int code = 0;
		String responseEntity = "啥都没";
        //测试project_id:
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