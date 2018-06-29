package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 发三张公共牌
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasFlop extends GCMessage{
	
	/** 公共牌 */
	private int[] cardList;

	public GCTexasFlop (){
	}
	
	public GCTexasFlop (
			int[] cardList ){
			this.cardList = cardList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		cardList = new int[count];
		for(int i=0; i<count; i++){
			cardList[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(cardList.length);
		for(int i=0; i<cardList.length; i++){
			writeInteger(cardList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_FLOP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_FLOP";
	}

	public int[] getCardList(){
		return cardList;
	}

	public void setCardList(int[] cardList){
		this.cardList = cardList;
	}	
}