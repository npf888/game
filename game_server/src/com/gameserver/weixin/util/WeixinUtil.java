package com.gameserver.weixin.util;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * 微信工具类
 * @author JavaServer
 *
 */
public class WeixinUtil {
	private static Logger logger = Logger.getLogger(WeixinUtil.class);
	public static void sendMessage(String e){
		String url = "http://47.88.169.228:9901/invoke.do";
//		String url = "http://localhost:8080/invoke.do";
		try{
			HashMap<String,String> param = new HashMap<String,String>();
			param.put("active","exception_notice");
			param.put("e", e);
			logger.info("触发微信远程调用 发送警告的接口：当前 url:"+url+" , 当前参数："+e);
			HttpClientUtil.postUrl(url,param);
		}catch(Exception ee){
			logger.error("", ee);
		}
	}
	
	
	/**
	 * 测试
	 * @param args
	 */
	/*public static void main(String[] args){
		sendMessage("aaaaaa");
	}*/
}
