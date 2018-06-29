package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.luckyspin.handler.LuckyspinHandlerFactory;

/**
 * 请求大转盘数据
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBigZhuanpan extends CGMessage{
	
	
	public CGBigZhuanpan (){
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
		return MessageType.CG_BIG_ZHUANPAN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BIG_ZHUANPAN";
	}
	


	@Override
	public void execute() {
		LuckyspinHandlerFactory.getHandler().handleBigZhuanpan(this.getSession().getPlayer(), this);
	}
}