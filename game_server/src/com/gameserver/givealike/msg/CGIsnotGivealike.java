package com.gameserver.givealike.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.givealike.handler.GivealikeHandlerFactory;

/**
 * 判断老虎机 是否 是 评论过
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGIsnotGivealike extends CGMessage{
	
	/** 老虎机类型 */
	private int slotType;
	
	public CGIsnotGivealike (){
	}
	
	public CGIsnotGivealike (
			int slotType ){
			this.slotType = slotType;
	}
	
	@Override
	protected boolean readImpl() {
		slotType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ISNOT_GIVEALIKE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ISNOT_GIVEALIKE";
	}

	public int getSlotType(){
		return slotType;
	}
		
	public void setSlotType(int slotType){
		this.slotType = slotType;
	}
	


	@Override
	public void execute() {
		GivealikeHandlerFactory.getHandler().handleIsnotGivealike(this.getSession().getPlayer(), this);
	}
}