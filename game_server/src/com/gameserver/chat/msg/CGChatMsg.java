package com.gameserver.chat.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.chat.handler.ChatHandlerFactory;

/**
 * 聊天
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGChatMsg extends CGMessage{
	
	/** 频道 */
	private int channel;
	/** 接收玩家id */
	private long destRoleUUID;
	/** 内容 */
	private String content;
	/** 房间号 */
	private String roomNumber;
	/** 消息类型（普通消息：0，可以加入房间的消息：1） */
	private int msgType;
	
	public CGChatMsg (){
	}
	
	public CGChatMsg (
			int channel,
			long destRoleUUID,
			String content,
			String roomNumber,
			int msgType ){
			this.channel = channel;
			this.destRoleUUID = destRoleUUID;
			this.content = content;
			this.roomNumber = roomNumber;
			this.msgType = msgType;
	}
	
	@Override
	protected boolean readImpl() {
		channel = readInteger();
		destRoleUUID = readLong();
		content = readString();
		roomNumber = readString();
		msgType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(channel);
		writeLong(destRoleUUID);
		writeString(content);
		writeString(roomNumber);
		writeInteger(msgType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CHAT_MSG;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHAT_MSG";
	}

	public int getChannel(){
		return channel;
	}
		
	public void setChannel(int channel){
		this.channel = channel;
	}

	public long getDestRoleUUID(){
		return destRoleUUID;
	}
		
	public void setDestRoleUUID(long destRoleUUID){
		this.destRoleUUID = destRoleUUID;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public String getRoomNumber(){
		return roomNumber;
	}
		
	public void setRoomNumber(String roomNumber){
		this.roomNumber = roomNumber;
	}

	public int getMsgType(){
		return msgType;
	}
		
	public void setMsgType(int msgType){
		this.msgType = msgType;
	}
	


	@Override
	public void execute() {
		ChatHandlerFactory.getHandler().handleChatMsg(this.getSession().getPlayer(), this);
	}
}