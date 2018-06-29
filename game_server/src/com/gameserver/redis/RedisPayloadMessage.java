package com.gameserver.redis;

/**
 * redis payload message
 * @author wayne
 *
 */
public class RedisPayloadMessage {
	private String cmd;
	private String payload;
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
}
