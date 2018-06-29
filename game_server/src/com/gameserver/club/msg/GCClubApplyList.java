package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取俱乐留申请
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubApplyList extends GCMessage{
	
	/** 类型 0 all  1 增加  2 删除   */
	private int opType;
	/** 申请列表 */
	private com.gameserver.club.protocol.ClubApplyUnit[] list;

	public GCClubApplyList (){
	}
	
	public GCClubApplyList (
			int opType,
			com.gameserver.club.protocol.ClubApplyUnit[] list ){
			this.opType = opType;
			this.list = list;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		opType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		list = new com.gameserver.club.protocol.ClubApplyUnit[count];
		for(int i=0; i<count; i++){
			com.gameserver.club.protocol.ClubApplyUnit obj = new com.gameserver.club.protocol.ClubApplyUnit();
			obj.setPlayerId(readLong());
			obj.setPlayerName(readString());
			obj.setImg(readString());
			obj.setCountry(readString());
			obj.setLevel(readLong());
			obj.setTime(readLong());
			list[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(opType);
		writeShort(list.length);
		for(int i=0; i<list.length; i++){
			writeLong(list[i].getPlayerId());
			writeString(list[i].getPlayerName());
			writeString(list[i].getImg());
			writeString(list[i].getCountry());
			writeLong(list[i].getLevel());
			writeLong(list[i].getTime());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_APPLY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_APPLY_LIST";
	}

	public int getOpType(){
		return opType;
	}
		
	public void setOpType(int opType){
		this.opType = opType;
	}

	public com.gameserver.club.protocol.ClubApplyUnit[] getList(){
		return list;
	}

	public void setList(com.gameserver.club.protocol.ClubApplyUnit[] list){
		this.list = list;
	}	
}