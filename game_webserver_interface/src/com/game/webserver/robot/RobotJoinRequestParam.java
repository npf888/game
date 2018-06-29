package com.game.webserver.robot;

import com.alibaba.fastjson.JSON;
import com.game.webserver.login.AbstractParams;

/**
 * 机器人请求服务
 * @author wayne
 *
 */
public class RobotJoinRequestParam extends AbstractParams<RobotJoinRequestRes>{

	private String serverId;
	private long roomId;

	public RobotJoinRequestParam(){}
	public RobotJoinRequestParam(String host, String page, boolean post) {
		super(host, page, post);
		// TODO Auto-generated constructor stub
	}


	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	@Override
	protected String content() {
		// TODO Auto-generated method stub
		return JSON.toJSONString(this);
	}

	@Override
	public RobotJoinRequestRes getRes() {
		// TODO Auto-generated method stub
		if(this.errorCode!=0)
		{
			return null;
		}
		else
		{
			return JSON.parseObject(this.result,RobotJoinRequestRes.class);
		}
	}

}
