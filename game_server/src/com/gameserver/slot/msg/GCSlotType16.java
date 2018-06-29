package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 阿兹特克老虎机拼图个数
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType16 extends GCMessage{
	
	/** 图片个数 */
	private int cardNumber;

	public GCSlotType16 (){
	}
	
	public GCSlotType16 (
			int cardNumber ){
			this.cardNumber = cardNumber;
	}

	@Override
	protected boolean readImpl() {
		cardNumber = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(cardNumber);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE16;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE16";
	}

	public int getCardNumber(){
		return cardNumber;
	}
		
	public void setCardNumber(int cardNumber){
		this.cardNumber = cardNumber;
	}
}