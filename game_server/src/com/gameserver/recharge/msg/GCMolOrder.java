package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 待验证发货订单
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCMolOrder extends GCMessage{
	
	/** 待验证订单链表  */
	private com.gameserver.recharge.data.MolValidationOrder[] molValidationOrder;

	public GCMolOrder (){
	}
	
	public GCMolOrder (
			com.gameserver.recharge.data.MolValidationOrder[] molValidationOrder ){
			this.molValidationOrder = molValidationOrder;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		molValidationOrder = new com.gameserver.recharge.data.MolValidationOrder[count];
		for(int i=0; i<count; i++){
			com.gameserver.recharge.data.MolValidationOrder obj = new com.gameserver.recharge.data.MolValidationOrder();
			obj.setReferenceId(readString());
			obj.setPaymentId(readString());
			molValidationOrder[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(molValidationOrder.length);
		for(int i=0; i<molValidationOrder.length; i++){
			writeString(molValidationOrder[i].getReferenceId());
			writeString(molValidationOrder[i].getPaymentId());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MOL_ORDER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MOL_ORDER";
	}

	public com.gameserver.recharge.data.MolValidationOrder[] getMolValidationOrder(){
		return molValidationOrder;
	}

	public void setMolValidationOrder(com.gameserver.recharge.data.MolValidationOrder[] molValidationOrder){
		this.molValidationOrder = molValidationOrder;
	}	
}