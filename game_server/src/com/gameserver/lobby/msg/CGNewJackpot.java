package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.lobby.handler.LobbyHandlerFactory;

/**
 * 返回游戏彩金数
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGNewJackpot extends CGMessage{
	
	/** 根据bet数 获取相应的彩金值 */
	private int bet;
	
	public CGNewJackpot (){
	}
	
	public CGNewJackpot (
			int bet ){
			this.bet = bet;
	}
	
	@Override
	protected boolean readImpl() {
		bet = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bet);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_NEW_JACKPOT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_NEW_JACKPOT";
	}

	public int getBet(){
		return bet;
	}
		
	public void setBet(int bet){
		this.bet = bet;
	}
	


	@Override
	public void execute() {
		LobbyHandlerFactory.getHandler().handleNewJackpot(this.getSession().getPlayer(), this);
	}
}