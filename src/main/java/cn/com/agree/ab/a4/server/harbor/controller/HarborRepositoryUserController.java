package cn.com.agree.ab.a4.server.harbor.controller;

import java.util.HashMap;
import java.util.Map;

import cn.com.agree.ab.a4.pub.engine.logiclet.ILogiclet;
import cn.com.agree.ab.a4.pub.engine.process.ProcessResult;
import cn.com.agree.ab.a4.pub.runtime.realm.Callback;
import cn.com.agree.ab.a4.server.lfc.cloudserver.conf.CloudConf;
import cn.com.agree.ab.a4.server.lfc.cloudserver.conf.CloudResCode;
import cn.com.agree.ab.a4.server.lfc.cloudserver.conf.CloudType;
import cn.com.agree.ab.a4.server.lfc.cloudserver.utils.CloudTraceUtils;



/**
 * @author 
 *       jinmingchao
 * @date 
 *       2018-06-19 10:38:14 
 * @usage
 *       harbor用户相关接口
 * @CSDpath
 *           
 * @requestbody
 *              
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class HarborRepositoryUserController implements ILogiclet {

	@Override
	public void call(Map<String, Object> inArgMap, Callback<ProcessResult> callback) {
		Object resultObj=null;
		Map<String, Object> outMap = new HashMap<String, Object>();
		try {
			CloudTraceUtils.printEntryBaseInfo(this.getClass(), inArgMap);
	    	String type = (String) inArgMap.get("type");
	    	boolean isError=true;
	    	if(CloudType.checkWhetherTypeIslegal(type)) 
	    		isError=false;	    	
		    if(!isError){
		    	Map<String, Object> resMap = routingConfiguration(type,inArgMap);
		    	resultObj = resMap.get(type);
		    }
		    outMap.put("result", resultObj);	
		}catch (Exception e) {
			CloudConf.setResponse(CloudResCode.CODE_10000, CloudResCode.MESSAGE_10000);
			outMap.put("result",resultObj);
		}
		ProcessResult lr = new ProcessResult();
		lr.setOutArgMap(outMap);
		lr.setEnd(ProcessResult.END_SUCCESS);
		callback.call(lr);
 
	}

	private Map<String, Object> routingConfiguration(String type, Map<String, Object> inArgMap) throws Exception{
//		HashMap<String, Object> resMap = new HashMap<String, Object>();
//		String type = (String) inArgMap.get("type");
//				
//		if(type.equals(CloudType.GET)){	
//			String nameSpace=(String)inArgMap.get("namespace");
//			resMap.put(type,UserResourceQuota(nameSpace));
//			return resMap; 
//		}
//		return resMap;
		return null;
	}

}
