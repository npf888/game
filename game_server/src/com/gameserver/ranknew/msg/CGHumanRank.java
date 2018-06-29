package com.gameserver.ranknew.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.ranknew.handler.RanknewHandlerFactory;

/**
 * 请求自己的排名
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHumanRank extends CGMessage{
	
	
	public CGHumanRank (){
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
		return MessageType.CG_HUMAN_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HUMAN_RANK";
	}
	


	@Override
	public void execute() {
		RanknewHandlerFactory.getHandler().handleHumanRank(this.getSession().getPlayer(), this);
	}
}