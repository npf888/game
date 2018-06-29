package com.gameserver.chat.redisMsg;

import com.gameserver.chat.handler.ChatHandlerFactory;
import com.gameserver.redis.IRedisMessage;

/**
 * 聊天redis
 * @author wayne
 *
 */
public class ChatRedisMessage implements IRedisMessage{
	/** 频道 */
	private int channel;
	/** 接收玩家名字 */
	private String destRoleName;
	/** 接收玩家id */
	private long destRoleUUID;
	/** 发送玩家名字 */
	private String fromRoleName;
	/**发送玩家图片*/
	private String fromRoleImg;
	/** 发送玩家id */
	private long fromRoleUUID;
	/** 内容 */
	private String content;
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public String getDestRoleName() {
		return destRoleName;
	}
	public void setDestRoleName(String destRoleName) {
		this.destRoleName = destRoleName;
	}
	public long getDestRoleUUID() {
		return destRoleUUID;
	}
	public void setDestRoleUUID(long destRoleUUID) {
		this.destRoleUUID = destRoleUUID;
	}
	public String getFromRoleName() {
		return fromRoleName;
	}
	public void setFromRoleName(String fromRoleName) {
		this.fromRoleName = fromRoleName;
	}
	public String getFromRoleImg() {
		return fromRoleImg;
	}
	public void setFromRoleImg(String fromRoleImg) {
		this.fromRoleImg = fromRoleImg;
	}
	public long getFromRoleUUID() {
		return fromRoleUUID;
	}
	public void setFromRoleUUID(long fromRoleUUID) {
		this.fromRoleUUID = fromRoleUUID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ChatHandlerFactory.getRedisHandler().handleChatRedisMessage(this);
	}
	
	
}
