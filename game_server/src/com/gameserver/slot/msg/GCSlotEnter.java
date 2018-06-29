package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 进入老虎机
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotEnter extends GCMessage{
	
	/** 老虎机Id */
	private int slotId;
	/** 返回类型  0 成功 1 等级不足 2 筹码不足 */
	private int resultType;

	public GCSlotEnter (){
	}
	
	public GCSlotEnter (
			int slotId,
			int resultType ){
			this.slotId = slotId;
			this.resultType = resultType;
	}

	@Override
	protected boolean readImpl() {
		slotId = readInteger();
		resultType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotId);
		writeInteger(resultType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ENTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ENTER";
	}

	public int getSlotId(){
		return slotId;
	}
		
	public void setSlotId(int slotId){
		this.slotId = slotId;
	}

	public int getResultType(){
		return resultType;
	}
		
	public void setResultType(int resultType){
		this.resultType = resultType;
	}
}