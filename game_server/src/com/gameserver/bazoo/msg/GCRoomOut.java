package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 某个用户 退出房间 当前 房间内所有用户 收到 此退出房间消息 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomOut extends GCMessage{
	
	/** 每次退出房间时,被移除房间 的所有的用户ID */
	private long[] beRemovedPassportIds;

	public GCRoomOut (){
	}
	
	public GCRoomOut (
			long[] beRemovedPassportIds ){
			this.beRemovedPassportIds = beRemovedPassportIds;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		beRemovedPassportIds = new long[count];
		for(int i=0; i<count; i++){
			beRemovedPassportIds[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(beRemovedPassportIds.length);
		for(int i=0; i<beRemovedPassportIds.length; i++){
			writeLong(beRemovedPassportIds[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_OUT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_OUT";
	}

	public long[] getBeRemovedPassportIds(){
		return beRemovedPassportIds;
	}

	public void setBeRemovedPassportIds(long[] beRemovedPassportIds){
		this.beRemovedPassportIds = beRemovedPassportIds;
	}	
}