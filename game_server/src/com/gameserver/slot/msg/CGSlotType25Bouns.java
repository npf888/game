package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 忍者老虎机bouns小游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType25Bouns extends CGMessage{
	
	/** 用户选择的位置 */
	private int position;
	
	public CGSlotType25Bouns (){
	}
	
	public CGSlotType25Bouns (
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
		return MessageType.CG_SLOT_TYPE25_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE25_BOUNS";
	}

	public int getPosition(){
		return position;
	}
		
	public void setPosition(int position){
		this.position = position;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType25Bouns(this.getSession().getPlayer(), this);
	}
}