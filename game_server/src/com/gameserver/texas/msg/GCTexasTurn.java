package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 轮到该玩家
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasTurn extends GCMessage{
	
	/** 第4张牌 */
	private int card;

	public GCTexasTurn (){
	}
	
	public GCTexasTurn (
			int card ){
			this.card = card;
	}

	@Override
	protected boolean readImpl() {
		card = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(card);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_TURN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_TURN";
	}

	public int getCard(){
		return card;
	}
		
	public void setCard(int card){
		this.card = card;
	}
}