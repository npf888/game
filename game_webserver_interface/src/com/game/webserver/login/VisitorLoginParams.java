package com.game.webserver.login;

import com.alibaba.fastjson.JSON;

public class VisitorLoginParams extends AbstractParams<LoginRes>{

	
	private String deviceMac;
	private String deviceId;
	private String macAddress;
	private String androidId;
	private String ip;
	private int port;
	private String serverId;
	
	public VisitorLoginParams(String host,String port, boolean post) {
		super(host,port,post);
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}





	public String getServerId() {
		return serverId;
	}



	public void setServerId(String serverId) {
		this.serverId = serverId;
	}



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public int getPort() {
		return port;
	}



	public void setPort(int port) {
		this.port = port;
	}



	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getAndroidId() {
		return androidId;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	@Override
	protected String content() {
		
		return "deviceMac="+deviceMac+"&deviceId="+deviceId+"&macAddress="+macAddress+"&androidId="+androidId;//JSON.toJSONString(this);
	}

	@Override
	public LoginRes getRes() {
		// TODO Auto-generated method stub
		if(this.errorCode!=0)
		{
			return null;
		}
		else
		{
			return JSON.parseObject(this.result,LoginRes.class);
		}
	}

}
