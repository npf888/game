package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回游戏彩金
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGametypeJackpot extends GCMessage{
	
	/** 游戏类型 百家乐 1 德州扑克 2 老虎机 classic：3  老虎机 四大美人：4 老虎机 水果：5 */
	private int gameType;
	/** 游戏的id */
	private int gameId;
	/** 游戏彩金 */
	private long gamejackpot;

	public GCGametypeJackpot (){
	}
	
	public GCGametypeJackpot (
			int gameType,
			int gameId,
			long gamejackpot ){
			this.gameType = gameType;
			this.gameId = gameId;
			this.gamejackpot = gamejackpot;
	}

	@Override
	protected boolean readImpl() {
		gameType = readInteger();
		gameId = readInteger();
		gamejackpot = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(gameType);
		writeInteger(gameId);
		writeLong(gamejackpot);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GAMETYPE_JACKPOT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GAMETYPE_JACKPOT";
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

	public long getGamejackpot(){
		return gamejackpot;
	}
		
	public void setGamejackpot(long gamejackpot){
		this.gamejackpot = gamejackpot;
	}
}