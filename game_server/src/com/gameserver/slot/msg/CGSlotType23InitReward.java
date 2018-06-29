package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 东方龙老虎机初始化奖池信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType23InitReward extends CGMessage{
	
	/** 当前下注额 */
	private int bet;
	
	public CGSlotType23InitReward (){
	}
	
	public CGSlotType23InitReward (
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
		return MessageType.CG_SLOT_TYPE23_INIT_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE23_INIT_REWARD";
	}

	public int getBet(){
		return bet;
	}
		
	public void setBet(int bet){
		this.bet = bet;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType23InitReward(this.getSession().getPlayer(), this);
	}
}