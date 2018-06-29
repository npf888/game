package com.gameserver.guide.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.guide.handler.GuideHandlerFactory;

/**
 * 点击道具商城，调用此接口
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGItemInvoke extends CGMessage{
	
	
	public CGItemInvoke (){
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
		return MessageType.CG_ITEM_INVOKE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ITEM_INVOKE";
	}
	


	@Override
	public void execute() {
		GuideHandlerFactory.getHandler().handleItemInvoke(this.getSession().getPlayer(), this);
	}
}