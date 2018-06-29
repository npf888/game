package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 轮到该玩家
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTexasPlayerTurn extends GCMessage{
	
	/** 轮到该玩家 */
	private int position;

	public GCTexasPlayerTurn (){
	}
	
	public GCTexasPlayerTurn (
			int position ){
			this.position = position;
	}

	@Override
	protected boolean readImpl() {
		position = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(position);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEXAS_PLAYER_TURN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEXAS_PLAYER_TURN";
	}

	public int getPosition(){
		return position;
	}
		
	public void setPosition(int position){
		this.position = position;
	}
}