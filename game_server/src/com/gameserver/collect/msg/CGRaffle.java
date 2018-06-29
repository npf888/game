package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.collect.handler.CollectHandlerFactory;

/**
 * 抽奖
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRaffle extends CGMessage{
	
	/** 1 铜 2 银 3 金 */
	private int cardType;
	
	public CGRaffle (){
	}
	
	public CGRaffle (
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
		return MessageType.CG_RAFFLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RAFFLE";
	}

	public int getCardType(){
		return cardType;
	}
		
	public void setCardType(int cardType){
		this.cardType = cardType;
	}
	


	@Override
	public void execute() {
		CollectHandlerFactory.getHandler().handleRaffle(this.getSession().getPlayer(), this);
	}
}