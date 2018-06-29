package com.robot;

import com.common.constants.CommonErrorLogInfo;
import com.core.config.Config;
import com.core.util.ErrorsUtil;

public class RobotServerConfig implements Config{

	/** 生产模式:0 调式模式:1 */
	private int debug = 0;

	/** 系统配置的版本号 */
	private String version;
	
	private String telnetBindIp;
	/** 服务器监听的端口,多个商品以逗号","分隔 */
	private String telnetPort;
	/** 服务器的主机ip */
	private String serverHost;
	private String ports;
	private String robotPrefix;
	private long robotStartId;
	private int robotNum;

	
	public int getDebug() {
		return debug;
	}

	public void setDebug(int debug) {
		this.debug = debug;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return version;
	}



	public String getTelnetBindIp() {
		return telnetBindIp;
	}

	public void setTelnetBindIp(String telnetBindIp) {
		this.telnetBindIp = telnetBindIp;
	}

	public String getTelnetPort() {
		return telnetPort;
	}

	public void setTelnetPort(String telnetPort) {
		this.telnetPort = telnetPort;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	
	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}


	public String getRobotPrefix() {
		return robotPrefix;
	}

	public void setRobotPrefix(String robotPrefix) {
		this.robotPrefix = robotPrefix;
	}

	public long getRobotStartId() {
		return robotStartId;
	}

	public void setRobotStartId(long robotStartId) {
		this.robotStartId = robotStartId;
	}

	public int getRobotNum() {
		return robotNum;
	}

	public void setRobotNum(int robotNum) {
		this.robotNum = robotNum;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		if(this.ports == null||(ports = ports.trim()).length()==0)
		{
			throw new IllegalArgumentException(ErrorsUtil.error(CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "", "ports"));
		}
		// 版本号配置检查
		if (this.getVersion() == null)
		{
			throw new IllegalArgumentException("The version  must not be null");
		}
	}

	@Override
	public boolean getIsDebug() {
		// TODO Auto-generated method stub
		return getDebug() == 1;
	}

}
