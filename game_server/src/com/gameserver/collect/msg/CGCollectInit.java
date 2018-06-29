package com.gameserver.collect.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.collect.handler.CollectHandlerFactory;

/**
 * 打开收集系统初始数据请求
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGCollectInit extends CGMessage{
	
	
	public CGCollectInit (){
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
		return MessageType.CG_COLLECT_INIT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_COLLECT_INIT";
	}
	


	@Override
	public void execute() {
		CollectHandlerFactory.getHandler().handleCollectInit(this.getSession().getPlayer(), this);
	}
}