package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 万圣节老虎机   用户玩 bonus小游戏 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType38Bonus extends CGMessage{
	
	/** 是否过关（0,1,2,3关）： 只要调用这个接口 就是过关 */
	private int isGhost;
	
	public CGSlotType38Bonus (){
	}
	
	public CGSlotType38Bonus (
			int isGhost ){
			this.isGhost = isGhost;
	}
	
	@Override
	protected boolean readImpl() {
		isGhost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isGhost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TYPE38_BONUS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE38_BONUS";
	}

	public int getIsGhost(){
		return isGhost;
	}
		
	public void setIsGhost(int isGhost){
		this.isGhost = isGhost;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType38Bonus(this.getSession().getPlayer(), this);
	}
}