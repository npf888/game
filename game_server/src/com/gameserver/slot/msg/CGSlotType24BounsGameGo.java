package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.slot.handler.SlotHandlerFactory;

/**
 * 巴西风情老虎机 去游戏 游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotType24BounsGameGo extends CGMessage{
	
	/** 要玩 哪个小游戏 */
	private int gameType;
	/** 要是 桑巴小游戏 就传 color */
	private int color;
	
	public CGSlotType24BounsGameGo (){
	}
	
	public CGSlotType24BounsGameGo (
			int gameType,
			int color ){
			this.gameType = gameType;
			this.color = color;
	}
	
	@Override
	protected boolean readImpl() {
		gameType = readInteger();
		color = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(gameType);
		writeInteger(color);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_TYPE24_BOUNS_GAME_GO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_TYPE24_BOUNS_GAME_GO";
	}

	public int getGameType(){
		return gameType;
	}
		
	public void setGameType(int gameType){
		this.gameType = gameType;
	}

	public int getColor(){
		return color;
	}
		
	public void setColor(int color){
		this.color = color;
	}
	


	@Override
	public void execute() {
		SlotHandlerFactory.getHandler().handleSlotType24BounsGameGo(this.getSession().getPlayer(), this);
	}
}