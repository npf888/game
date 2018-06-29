package com.gameserver.chat.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 聊天
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCChatMsg extends GCMessage{
	
	/** 频道   喇叭 SPEAKER(0),世界WORLD(1),房间ROOM(2),俱乐部CLUB(3),私聊 PRIVATE(4),百家乐BACCARAT(5); */
	private int channel;
	/** 发送玩家头像 */
	private String fromRoleImg;
	/** 发送玩家名字 */
	private String fromRoleName;
	/** 发送玩家id */
	private long fromRoleUUID;
	/** 国家 */
	private String national;
	/** 等级 */
	private int lv;
	/** vip等级 */
	private int viplv;
	/** 排行榜 */
	private int rank;
	/** 性别 1男    2女 */
	private int sex;
	/** 内容 */
	private String content;
	/** 房间号 */
	private String roomNumber;
	/** 消息类型（普通消息：0，可以加入房间的消息：1） */
	private int msgType;

	public GCChatMsg (){
	}
	
	public GCChatMsg (
			int channel,
			String fromRoleImg,
			String fromRoleName,
			long fromRoleUUID,
			String national,
			int lv,
			int viplv,
			int rank,
			int sex,
			String content,
			String roomNumber,
			int msgType ){
			this.channel = channel;
			this.fromRoleImg = fromRoleImg;
			this.fromRoleName = fromRoleName;
			this.fromRoleUUID = fromRoleUUID;
			this.national = national;
			this.lv = lv;
			this.viplv = viplv;
			this.rank = rank;
			this.sex = sex;
			this.content = content;
			this.roomNumber = roomNumber;
			this.msgType = msgType;
	}

	@Override
	protected boolean readImpl() {
		channel = readInteger();
		fromRoleImg = readString();
		fromRoleName = readString();
		fromRoleUUID = readLong();
		national = readString();
		lv = readInteger();
		viplv = readInteger();
		rank = readInteger();
		sex = readInteger();
		content = readString();
		roomNumber = readString();
		msgType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(channel);
		writeString(fromRoleImg);
		writeString(fromRoleName);
		writeLong(fromRoleUUID);
		writeString(national);
		writeInteger(lv);
		writeInteger(viplv);
		writeInteger(rank);
		writeInteger(sex);
		writeString(content);
		writeString(roomNumber);
		writeInteger(msgType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHAT_MSG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHAT_MSG";
	}

	public int getChannel(){
		return channel;
	}
		
	public void setChannel(int channel){
		this.channel = channel;
	}

	public String getFromRoleImg(){
		return fromRoleImg;
	}
		
	public void setFromRoleImg(String fromRoleImg){
		this.fromRoleImg = fromRoleImg;
	}

	public String getFromRoleName(){
		return fromRoleName;
	}
		
	public void setFromRoleName(String fromRoleName){
		this.fromRoleName = fromRoleName;
	}

	public long getFromRoleUUID(){
		return fromRoleUUID;
	}
		
	public void setFromRoleUUID(long fromRoleUUID){
		this.fromRoleUUID = fromRoleUUID;
	}

	public String getNational(){
		return national;
	}
		
	public void setNational(String national){
		this.national = national;
	}

	public int getLv(){
		return lv;
	}
		
	public void setLv(int lv){
		this.lv = lv;
	}

	public int getViplv(){
		return viplv;
	}
		
	public void setViplv(int viplv){
		this.viplv = viplv;
	}

	public int getRank(){
		return rank;
	}
		
	public void setRank(int rank){
		this.rank = rank;
	}

	public int getSex(){
		return sex;
	}
		
	public void setSex(int sex){
		this.sex = sex;
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
}