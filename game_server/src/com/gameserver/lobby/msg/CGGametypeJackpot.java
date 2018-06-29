package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.lobby.handler.LobbyHandlerFactory;

/**
 * 具体游戏类型彩金
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGametypeJackpot extends CGMessage{
	
	/** 游戏类型 百家乐 1 德州扑克 2 老虎机 classic：3  老虎机 四大美人：4 老虎机 水果：5 */
	private int gameType;
	/** 游戏的id */
	private int gameId;
	
	public CGGametypeJackpot (){
	}
	
	public CGGametypeJackpot (
			int gameType,
			int gameId ){
			this.gameType = gameType;
			this.gameId = gameId;
	}
	
	@Override
	protected boolean readImpl() {
		gameType = readInteger();
		gameId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(gameType);
		writeInteger(gameId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GAMETYPE_JACKPOT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GAMETYPE_JACKPOT";
	}

	public int getGameType(){
		return gameType;
	}
		
	public void setGameType(int gameType){
		this.gameType = gameType;
	}

	public int getGameId(){
		return gameId;
	}
		
	public void setGameId(int gameId){
		this.gameId = gameId;
	}
	


	@Override
	public void execute() {
		LobbyHandlerFactory.getHandler().handleGametypeJackpot(this.getSession().getPlayer(), this);
	}
}