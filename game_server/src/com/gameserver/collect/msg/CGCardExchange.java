package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.collect.handler.CollectHandlerFactory;

/**
 * 卡片兑换
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGCardExchange extends CGMessage{
	
	/** 1 农场卡 2 建筑卡3 赌场卡 */
	private int cardType;
	
	public CGCardExchange (){
	}
	
	public CGCardExchange (
			int cardType ){
			this.cardType = cardType;
	}
	
	@Override
	protected boolean readImpl() {
		cardType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(cardType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CARD_EXCHANGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CARD_EXCHANGE";
	}

	public int getCardType(){
		return cardType;
	}
		
	public void setCardType(int cardType){
		this.cardType = cardType;
	}
	


	@Override
	public void execute() {
		CollectHandlerFactory.getHandler().handleCardExchange(this.getSession().getPlayer(), this);
	}
}