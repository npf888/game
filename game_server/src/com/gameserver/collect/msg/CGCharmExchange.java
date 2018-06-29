package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.collect.handler.CollectHandlerFactory;

/**
 * 魅力值兑换圈
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGCharmExchange extends CGMessage{
	
	/** exchange表ID */
	private int exchangeId;
	
	public CGCharmExchange (){
	}
	
	public CGCharmExchange (
			int exchangeId ){
			this.exchangeId = exchangeId;
	}
	
	@Override
	protected boolean readImpl() {
		exchangeId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(exchangeId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CHARM_EXCHANGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHARM_EXCHANGE";
	}

	public int getExchangeId(){
		return exchangeId;
	}
		
	public void setExchangeId(int exchangeId){
		this.exchangeId = exchangeId;
	}
	


	@Override
	public void execute() {
		CollectHandlerFactory.getHandler().handleCharmExchange(this.getSession().getPlayer(), this);
	}
}