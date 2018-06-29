package com.game.webserver.login;


import org.slf4j.Logger;

import com.alibaba.fastjson.annotation.JSONField;
import com.common.constants.Loggers;

public abstract class AbstractParams<T> {
	private Logger logger = Loggers.httpLogger;
	@JSONField(serialize = false)
	private String host;
	@JSONField(serialize = false)
	protected String page;
	@JSONField(serialize = false)
	private boolean post;
	@JSONField(serialize = false)
	protected String result;
	@JSONField(serialize = false)
	protected int errorCode;
	
	public AbstractParams(){}
	public AbstractParams(String host,String page,boolean post)
	{
		this.host = host;
		this.page = page;
		this.post = post;
	}
	
	public void send()
	{
		logger.info("开始请求 登录服务器......");
		LoginServerRequest loginRequest= new LoginServerRequest(this.getHost());
		loginRequest.setPage(this.page);
		
		logger.info("请求 登录服务器 数据start......content:::"+content());
		LoginServerResponse response = loginRequest.send(content(), isPost());
		errorCode = response.getErrorCode();
		logger.info("请求 登录服务器 返回数据......errorCode:::"+errorCode);
		result = response.getResult();
	}
	
	protected abstract String content();
	public abstract T getRes();

	protected boolean isPost() {
		return post;
	}

	protected void setPost(boolean post) {
		this.post = post;
	}
	

	protected String getHost() {
		return host;
	}

	protected void setHost(String host) {
		this.host = host;
	}

	protected String getPage() {
		return page;
	}

	protected void setPage(String page) {
		this.page = page;
	}

	
	
	public int getErrorCode() {
		return errorCode;
	}


}
