package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州玩家坐下
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasSeat extends CGMessage{
	
	/** 位置 */
	private int pos;
	
	public CGTexasSeat (){
	}
	
	public CGTexasSeat (
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
		return MessageType.CG_TEXAS_SEAT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_SEAT";
	}

	public int getPos(){
		return pos;
	}
		
	public void setPos(int pos){
		this.pos = pos;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasSeat(this.getSession().getPlayer(), this);
	}
}