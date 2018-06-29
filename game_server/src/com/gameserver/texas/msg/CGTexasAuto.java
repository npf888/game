package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 德州改变自动补充筹码
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTexasAuto extends CGMessage{
	
	/** 补充 */
	private int isAuto;
	
	public CGTexasAuto (){
	}
	
	public CGTexasAuto (
			int isAuto ){
			this.isAuto = isAuto;
	}
	
	@Override
	protected boolean readImpl() {
		isAuto = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isAuto);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TEXAS_AUTO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TEXAS_AUTO";
	}

	public int getIsAuto(){
		return isAuto;
	}
		
	public void setIsAuto(int isAuto){
		this.isAuto = isAuto;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleTexasAuto(this.getSession().getPlayer(), this);
	}
}