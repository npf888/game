package com.game.webserver.robot;

import com.alibaba.fastjson.JSON;
import com.game.webserver.login.AbstractParams;

/**
 * 机器人退出请求
 * @author wayne
 *
 */
public class RobotExitRequestParam extends AbstractParams<RobotExitRequestRes>{

	private String serverId;
	private long robotId;

	public RobotExitRequestParam(){}
	public RobotExitRequestParam(String host, String page, boolean post) {
		super(host, page, post);
		// TODO Auto-generated constructor stub
	}



	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public long getRobotId() {
		return robotId;
	}
	public void setRobotId(long robotId) {
		this.robotId = robotId;
	}
	
	@Override
	protected String content() {
		// TODO Auto-generated method stub
		return JSON.toJSONString(this);
	}

	@Override
	public RobotExitRequestRes getRes() {
		// TODO Auto-generated method stub
		if(this.errorCode!=0)
		{
			return null;
		}
		else
		{
			return JSON.parseObject(this.result,RobotExitRequestRes.class);
		}
	}
}
