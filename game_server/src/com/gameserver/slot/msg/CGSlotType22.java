package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 西部牛仔老虎机挖矿小游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType22 extends CGMessage{
	
	/** 下注 */
	private int bet;
	
	public CGSlotType22 (){
	}
	
	public CGSlotType22 (
			int bet ){
			this.bet = bet;
	}
	
	@Override
	protected boolean readImpl() {
		bet = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bet);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TYPE22;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE22";
	}

	public int getBet(){
		return bet;
	}
		
	public void setBet(int bet){
		this.bet = bet;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType22(this.getSession().getPlayer(), this);
	}
}