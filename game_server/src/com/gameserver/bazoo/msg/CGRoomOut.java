package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 退出房间
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoomOut extends CGMessage{
	
	/** 倍数 */
	private int bet;
	
	public CGRoomOut (){
	}
	
	public CGRoomOut (
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
		return MessageType.CG_ROOM_OUT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROOM_OUT";
	}

	public int getBet(){
		return bet;
	}
		
	public void setBet(int bet){
		this.bet = bet;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleRoomOut(this.getSession().getPlayer(), this);
	}
}