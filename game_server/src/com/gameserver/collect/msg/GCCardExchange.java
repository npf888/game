package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 卡片兑换返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCardExchange extends GCMessage{
	
	/** 1 农场卡 2 建筑卡3 赌场卡 */
	private int cardType;
	/** 1 成功 0失败 */
	private int returnRes;

	public GCCardExchange (){
	}
	
	public GCCardExchange (
			int cardType,
			int returnRes ){
			this.cardType = cardType;
			this.returnRes = returnRes;
	}

	@Override
	protected boolean readImpl() {
		cardType = readInteger();
		returnRes = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(cardType);
		writeInteger(returnRes);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CARD_EXCHANGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CARD_EXCHANGE";
	}

	public int getCardType(){
		return cardType;
	}
		
	public void setCardType(int cardType){
		this.cardType = cardType;
	}

	public int getReturnRes(){
		return returnRes;
	}
		
	public void setReturnRes(int returnRes){
		this.returnRes = returnRes;
	}
}