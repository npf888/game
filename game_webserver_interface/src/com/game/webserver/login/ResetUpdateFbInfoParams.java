package com.game.webserver.login;

import com.alibaba.fastjson.JSON;

public class ResetUpdateFbInfoParams extends AbstractParams<Object>{
	
	private long userId;

	public ResetUpdateFbInfoParams(){}
	public ResetUpdateFbInfoParams(String host, String page, boolean post) {
		super(host, page, post);
	}
	
	@Override
	protected String content() {
		
		return JSON.toJSONString(this);
	}

	@Override
	public Object getRes() {
		return null;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
