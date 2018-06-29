package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * slot
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotSlot extends CGMessage{
	
	/** 下注 */
	private int bet;
	/** 是否免费 */
	private int free;
	
	public CGSlotSlot (){
	}
	
	public CGSlotSlot (
			int bet,
			int free ){
			this.bet = bet;
			this.free = free;
	}
	
	@Override
	protected boolean readImpl() {
		bet = readInteger();
		free = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bet);
		writeInteger(free);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_SLOT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_SLOT";
	}

	public int getBet(){
		return bet;
	}
		
	public void setBet(int bet){
		this.bet = bet;
	}

	public int getFree(){
		return free;
	}
		
	public void setFree(int free){
		this.free = free;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotSlot(this.getSession().getPlayer(), this);
	}
}