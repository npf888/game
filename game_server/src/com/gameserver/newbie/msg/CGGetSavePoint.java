package com.gameserver.newbie.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.newbie.handler.NewbieHandlerFactory;

/**
 * 获取存盘点
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetSavePoint extends CGMessage{
	
	
	public CGGetSavePoint (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_SAVE_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_SAVE_POINT";
	}
	


	@Override
	public void execute() {
		NewbieHandlerFactory.getHandler().handleGetSavePoint(this.getSession().getPlayer(), this);
	}
}