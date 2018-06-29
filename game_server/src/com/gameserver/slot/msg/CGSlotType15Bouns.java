package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 狮身人面老虎机的bouns小游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType15Bouns extends CGMessage{
	
	/** bouns位置元素ID */
	private int rewardPool;
	
	public CGSlotType15Bouns (){
	}
	
	public CGSlotType15Bouns (
			int rewardPool ){
			this.rewardPool = rewardPool;
	}
	
	@Override
	protected boolean readImpl() {
		rewardPool = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rewardPool);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TYPE15_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE15_BOUNS";
	}

	public int getRewardPool(){
		return rewardPool;
	}
		
	public void setRewardPool(int rewardPool){
		this.rewardPool = rewardPool;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType15Bouns(this.getSession().getPlayer(), this);
	}
}