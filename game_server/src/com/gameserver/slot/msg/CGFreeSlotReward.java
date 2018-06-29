package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * slot
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFreeSlotReward extends CGMessage{
	
	/** 盒子位置 */
	private int pos;
	
	public CGFreeSlotReward (){
	}
	
	public CGFreeSlotReward (
			int pos ){
			this.pos = pos;
	}
	
	@Override
	protected boolean readImpl() {
		pos = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_FREE_SLOT_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FREE_SLOT_REWARD";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleFreeSlotReward(this.getSession().getPlayer(), this);
	}
}