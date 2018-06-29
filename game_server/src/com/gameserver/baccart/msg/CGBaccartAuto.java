package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 百家乐自动补充
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartAuto extends CGMessage{
	
	/** 自动补充 */
	private int auto;
	
	public CGBaccartAuto (){
	}
	
	public CGBaccartAuto (
			int auto ){
			this.auto = auto;
	}
	
	@Override
	protected boolean readImpl() {
		auto = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(auto);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BACCART_AUTO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_AUTO";
	}

	public int getAuto(){
		return auto;
	}
		
	public void setAuto(int auto){
		this.auto = auto;
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartAuto(this.getSession().getPlayer(), this);
	}
}