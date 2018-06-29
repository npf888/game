package com.game.webserver.login;


import java.io.IOException;
import java.net.URLEncoder;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.common.constants.Loggers;
import com.core.util.HttpUtil;



public class LoginServerRequest{
	private Logger logger = Loggers.httpLogger;
	protected String domain ;
	protected int timeout = 5;
    private String page;
    
    
	public LoginServerRequest(String domain)
    {
    	this.domain = domain;
    }
  
    
    public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
    
	
	public LoginServerResponse get()
	{
		
	   return send(null,false);
	}
	
	public LoginServerResponse post(String contentData)
	{
		return send(contentData,true);
	}
	
	public LoginServerResponse send(String contentData,boolean post){
		
		
		 String result = null;
		 try {
			 if(post){
				 logger.info(" 当前请求的 登录服务器的  URL::"+this.getUrl());
				 result = HttpUtil.postUrl(this.getUrl(),contentData);
				 logger.info(" 当前请求的 登录服务器的  返回的 result::"+result);
			 }
			 else
			 {
				 result = HttpUtil.getUrl(this.getUrl(), timeout);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Loggers.errorLogger.error("=======>#PLATFORM.LOCAL.REQUEST.ERROR:url==>"+this.getUrl()+"error==>"+e.getMessage());
		 
		    LoginServerResponse response = LoginServerResponse.httpErrorResponse();
		    return response;
		}
		  
		 LoginServerResponse loginResponse = JSON.parseObject(result, LoginServerResponse.class);
		 return loginResponse;
	}
	
	protected String getUrl()
	{
		return this.domain+this.page;
	}
	


	protected String encodeUrl(String str)
	{
	    try
	    {
	      return URLEncoder.encode(str, "UTF-8");
	    } catch (Exception e) {
	  
	    }return str;
	}
	


}
