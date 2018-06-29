package com.gameserver.weixin;

import org.apache.log4j.Logger;

import com.common.InitializeRequired;
import com.gameserver.weixin.util.HttpClientUtil;

public class WeixinService implements InitializeRequired {
	private static Logger logger = Logger.getLogger(WeixinService.class);
	@Override
	public void init() {
		
	}
	
	
	public String sendMessage(){
		String url = "";
		String jsonParamStr = "";
		logger.info("触发微信远程调用 发送警告的接口：当前 url:"+url+" , 当前参数："+jsonParamStr);
		return HttpClientUtil.postJSONUrl(url, jsonParamStr);
	}

}
