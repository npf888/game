package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 手大礼包是否显示的 用户触发
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanDetailInfoTodayView extends CGMessage{
	
	
	public CGHumanDetailInfoTodayView (){
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
		return MessageType.CG_HUMAN_DETAIL_INFO_TODAY_VIEW;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_DETAIL_INFO_TODAY_VIEW";
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleHumanDetailInfoTodayView(this.getSession().getPlayer(), this);
	}
}