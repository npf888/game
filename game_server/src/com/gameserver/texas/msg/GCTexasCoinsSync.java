package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州金币同步
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasCoinsSync extends GCMessage{
	
	/** 玩家  */
	private int pos;
	/** 金币数  */
	private long coins;

	public GCTexasCoinsSync (){
	}
	
	public GCTexasCoinsSync (
			int pos,
			long coins ){
			this.pos = pos;
			this.coins = coins;
	}

	@Override
	protected boolean readImpl() {
		pos = readInteger();
		coins = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		writeLong(coins);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_COINS_SYNC;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_COINS_SYNC";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}

	public long getCoins(){
		return coins;
	}
		
	public void setCoins(long coins){
		this.coins = coins;
	}
}