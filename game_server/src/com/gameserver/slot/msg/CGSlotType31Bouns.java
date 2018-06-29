package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 海盗老虎机bouns小游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType31Bouns extends CGMessage{
	
	/** 第几个小游戏 */
	private int whichNum;
	
	public CGSlotType31Bouns (){
	}
	
	public CGSlotType31Bouns (
			int whichNum ){
			this.whichNum = whichNum;
	}
	
	@Override
	protected boolean readImpl() {
		whichNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(whichNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TYPE31_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE31_BOUNS";
	}

	public int getWhichNum(){
		return whichNum;
	}
		
	public void setWhichNum(int whichNum){
		this.whichNum = whichNum;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType31Bouns(this.getSession().getPlayer(), this);
	}
}