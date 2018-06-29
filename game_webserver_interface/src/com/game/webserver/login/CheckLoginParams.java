package com.game.webserver.login;

import com.alibaba.fastjson.JSON;


/**
 * 登录检测
 * @author wayne
 *
 */
public class CheckLoginParams extends AbstractParams<CheckLoginRes>{
	
	private long userId;
	private String userCode;
	private int serverId;

	public CheckLoginParams(String host,String string, boolean b) {
		super(host,string,b);
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}


	@Override
	protected String content() {
		
		return JSON.toJSONString(this);
	}

	@Override
	public CheckLoginRes getRes() {
		// TODO Auto-generated method stub
		if(this.errorCode!=0)
		{
			return null;
		}
		else
		{
			return JSON.parseObject(this.result,CheckLoginRes.class);
		}
	}
}
