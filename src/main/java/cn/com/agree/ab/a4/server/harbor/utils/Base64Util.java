package cn.com.agree.ab.a4.server.harbor.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {
	 
	/**
	 * @author jinmingchao
	 * @date 2018-05-08 
	 * @usage
	 * @args
	        required:
				
			optional:
				
	 * @comment:
	 *      curl command:
	 *      				
	 */
	public static String generateAuthorityText(String text) {	
	Base64.Encoder encoder = Base64.getEncoder();
	byte[] textByte =null;
	try {
		textByte = text.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	//编码
	final String encodedText = encoder.encodeToString(textByte);
	return encodedText;
	}
}
