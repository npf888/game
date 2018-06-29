package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州快速开始
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasHandCard extends GCMessage{
	
	/** 手牌类型 */
	private int handCardType;

	public GCTexasHandCard (){
	}
	
	public GCTexasHandCard (
			int handCardType ){
			this.handCardType = handCardType;
	}

	@Override
	protected boolean readImpl() {
		handCardType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(handCardType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_HAND_CARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_HAND_CARD";
	}

	public int getHandCardType(){
		return handCardType;
	}
		
	public void setHandCardType(int handCardType){
		this.handCardType = handCardType;
	}
}