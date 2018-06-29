package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 百家乐洗牌
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBaccartShuffle extends GCMessage{
	
	/** 剩余牌 */
	private int remainCards;

	public GCBaccartShuffle (){
	}
	
	public GCBaccartShuffle (
			int remainCards ){
			this.remainCards = remainCards;
	}

	@Override
	protected boolean readImpl() {
		remainCards = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainCards);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BACCART_SHUFFLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BACCART_SHUFFLE";
	}

	public int getRemainCards(){
		return remainCards;
	}
		
	public void setRemainCards(int remainCards){
		this.remainCards = remainCards;
	}
}