package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 邀请朋友加入老虎机房间返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotRoomPlease extends GCMessage{
	
	/** 发出邀请的角色ID */
	private long playerId;
	/** 邀请的好友IMG */
	private String friendImg;
	/** 邀请的好友名字 */
	private String friendName;
	/** 要求加入的老虎机ID */
	private int slotId;
	/** VIP等级 */
	private int vipLevel;
	/** 房间ID */
	private String roomId;

	public GCSlotRoomPlease (){
	}
	
	public GCSlotRoomPlease (
			long playerId,
			String friendImg,
			String friendName,
			int slotId,
			int vipLevel,
			String roomId ){
			this.playerId = playerId;
			this.friendImg = friendImg;
			this.friendName = friendName;
			this.slotId = slotId;
			this.vipLevel = vipLevel;
			this.roomId = roomId;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		friendImg = readString();
		friendName = readString();
		slotId = readInteger();
		vipLevel = readInteger();
		roomId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeString(friendImg);
		writeString(friendName);
		writeInteger(slotId);
		writeInteger(vipLevel);
		writeString(roomId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ROOM_PLEASE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ROOM_PLEASE";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public String getFriendImg(){
		return friendImg;
	}
		
	public void setFriendImg(String friendImg){
		this.friendImg = friendImg;
	}

	public String getFriendName(){
		return friendName;
	}
		
	public void setFriendName(String friendName){
		this.friendName = friendName;
	}

	public int getSlotId(){
		return slotId;
	}
		
	public void setSlotId(int slotId){
		this.slotId = slotId;
	}

	public int getVipLevel(){
		return vipLevel;
	}
		
	public void setVipLevel(int vipLevel){
		this.vipLevel = vipLevel;
	}

	public String getRoomId(){
		return roomId;
	}
		
	public void setRoomId(String roomId){
		this.roomId = roomId;
	}
}