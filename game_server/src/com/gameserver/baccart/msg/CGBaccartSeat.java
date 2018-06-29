package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 玩家坐下
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartSeat extends CGMessage{
	
	/** 坐下位置 */
	private int pos;
	
	public CGBaccartSeat (){
	}
	
	public CGBaccartSeat (
			int pos ){
			this.pos = pos;
	}
	
	@Override
	protected boolean readImpl() {
		pos = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pos);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BACCART_SEAT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_SEAT";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartSeat(this.getSession().getPlayer(), this);
	}
}