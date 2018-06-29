package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 西方龙老虎机bouns小游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType29Bouns extends CGMessage{
	
	/** 龙蛋位置 */
	private int position;
	
	public CGSlotType29Bouns (){
	}
	
	public CGSlotType29Bouns (
			int position ){
			this.position = position;
	}
	
	@Override
	protected boolean readImpl() {
		position = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(position);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TYPE29_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE29_BOUNS";
	}

	public int getPosition(){
		return position;
	}
		
	public void setPosition(int position){
		this.position = position;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType29Bouns(this.getSession().getPlayer(), this);
	}
}