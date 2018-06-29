package com.gameserver.bazoorank.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoorank.handler.BazoorankHandlerFactory;

/**
 * 盈利 的 排行榜 
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooRankRequest extends CGMessage{
	
	/** 按什么查询：： 1:天   2:周  3:月 */
	private int dateType;
	
	public CGBazooRankRequest (){
	}
	
	public CGBazooRankRequest (
			int dateType ){
			this.dateType = dateType;
	}
	
	@Override
	protected boolean readImpl() {
		dateType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(dateType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_RANK_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_RANK_REQUEST";
	}

	public int getDateType(){
		return dateType;
	}
		
	public void setDateType(int dateType){
		this.dateType = dateType;
	}
	


	@Override
	public void execute() {
		BazoorankHandlerFactory.getHandler().handleBazooRankRequest(this.getSession().getPlayer(), this);
	}
}