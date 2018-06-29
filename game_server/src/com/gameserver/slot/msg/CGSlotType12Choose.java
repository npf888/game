package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 维密老虎机玩家选择ID
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType12Choose extends CGMessage{
	
	/** 维密老虎机玩家选择ID */
	private int id;
	
	public CGSlotType12Choose (){
	}
	
	public CGSlotType12Choose (
			int id ){
			this.id = id;
	}
	
	@Override
	protected boolean readImpl() {
		id = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TYPE12_CHOOSE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE12_CHOOSE";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType12Choose(this.getSession().getPlayer(), this);
	}
}