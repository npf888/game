package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州结算经验广播
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanTexasExp extends GCMessage{
	
	/** 玩家经验列表 */
	private com.gameserver.texas.data.TexasRoomExp[] texasRoomExp;

	public GCHumanTexasExp (){
	}
	
	public GCHumanTexasExp (
			com.gameserver.texas.data.TexasRoomExp[] texasRoomExp ){
			this.texasRoomExp = texasRoomExp;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		texasRoomExp = new com.gameserver.texas.data.TexasRoomExp[count];
		for(int i=0; i<count; i++){
			com.gameserver.texas.data.TexasRoomExp obj = new com.gameserver.texas.data.TexasRoomExp();
			obj.setPlayerId(readLong());
			obj.setExp(readInteger());
			texasRoomExp[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(texasRoomExp.length);
		for(int i=0; i<texasRoomExp.length; i++){
			writeLong(texasRoomExp[i].getPlayerId());
			writeInteger(texasRoomExp[i].getExp());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_TEXAS_EXP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_TEXAS_EXP";
	}

	public com.gameserver.texas.data.TexasRoomExp[] getTexasRoomExp(){
		return texasRoomExp;
	}

	public void setTexasRoomExp(com.gameserver.texas.data.TexasRoomExp[] texasRoomExp){
		this.texasRoomExp = texasRoomExp;
	}	
}