package com.gameserver.status;


/**
 * 服务器状态
 * @author wayne
 *
 */
public class ServerStatus {
	private String ip;
	private int port;
	private String serverId;
	private int nums;
	private int maxNums;
	private int status;
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
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public int getMaxNums() {
		return maxNums;
	}
	public void setMaxNums(int maxNums) {
		this.maxNums = maxNums;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString()
	{
		return "服务器["+this.serverId+"],"+"ip["+this.getIp()+"],"+"port["+this.getPort()+"],"+"最大人数["+this.getMaxNums()+"],"+"在线人数["+this.getNums()+"],"+"状态["+this.getStatus()+"]";	
	}
}
