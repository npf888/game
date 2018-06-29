package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 获取老虎机列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotGetList extends CGMessage{
	
	/** 老虎机类型 */
	private int soltType;
	
	public CGSlotGetList (){
	}
	
	public CGSlotGetList (
			int soltType ){
			this.soltType = soltType;
	}
	
	@Override
	protected boolean readImpl() {
		soltType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(soltType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_GET_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_GET_LIST";
	}

	public int getSoltType(){
		return soltType;
	}
		
	public void setSoltType(int soltType){
		this.soltType = soltType;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotGetList(this.getSession().getPlayer(), this);
	}
}