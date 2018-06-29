package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 进入房间
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoomEnter extends CGMessage{
	
	/** 倍数 */
	private int bet;
	
	public CGRoomEnter (){
	}
	
	public CGRoomEnter (
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
		return MessageType.CG_ROOM_ENTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROOM_ENTER";
	}

	public int getBet(){
		return bet;
	}
		
	public void setBet(int bet){
		this.bet = bet;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleRoomEnter(this.getSession().getPlayer(), this);
	}
}