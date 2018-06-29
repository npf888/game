package com.gameserver.bazoorank.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoorank.handler.BazoorankHandlerFactory;

/**
 * 用户当前所有的金币  排行榜 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooRankTotalGoldRequest extends CGMessage{
	
	
	public CGBazooRankTotalGoldRequest (){
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
		return MessageType.CG_BAZOO_RANK_TOTAL_GOLD_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_RANK_TOTAL_GOLD_REQUEST";
	}
	


	@Override
	public void execute() {
		BazoorankHandlerFactory.getHandler().handleBazooRankTotalGoldRequest(this.getSession().getPlayer(), this);
	}
}