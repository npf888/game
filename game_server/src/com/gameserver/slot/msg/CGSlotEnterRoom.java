package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 进入老虎机
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotEnterRoom extends CGMessage{
	
	/** 老虎机Id */
	private int slotId;
	/** 房间ID */
	private String roomId;
	
	public CGSlotEnterRoom (){
	}
	
	public CGSlotEnterRoom (
			int slotId,
			String roomId ){
			this.slotId = slotId;
			this.roomId = roomId;
	}
	
	@Override
	protected boolean readImpl() {
		slotId = readInteger();
		roomId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotId);
		writeString(roomId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_ENTER_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_ENTER_ROOM";
	}

	public int getSlotId(){
		return slotId;
	}
		
	public void setSlotId(int slotId){
		this.slotId = slotId;
	}

	public String getRoomId(){
		return roomId;
	}
		
	public void setRoomId(String roomId){
		this.roomId = roomId;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotEnterRoom(this.getSession().getPlayer(), this);
	}
}