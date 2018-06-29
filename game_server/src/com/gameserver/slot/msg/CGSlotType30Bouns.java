package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 福尔摩斯老虎机bouns小游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType30Bouns extends CGMessage{
	
	/** 猜大小的第几个 */
	private int number;
	
	public CGSlotType30Bouns (){
	}
	
	public CGSlotType30Bouns (
			int number ){
			this.number = number;
	}
	
	@Override
	protected boolean readImpl() {
		number = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(number);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TYPE30_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE30_BOUNS";
	}

	public int getNumber(){
		return number;
	}
		
	public void setNumber(int number){
		this.number = number;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType30Bouns(this.getSession().getPlayer(), this);
	}
}