package com.gameserver.rank.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.rank.handler.RankHandlerFactory;

/**
 * 排行
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGCommonRank extends CGMessage{
	
	/** 页面 */
	private int page;
	/** 排行版类型 */
	private int rankType;
	
	public CGCommonRank (){
	}
	
	public CGCommonRank (
			int page,
			int rankType ){
			this.page = page;
			this.rankType = rankType;
	}
	
	@Override
	protected boolean readImpl() {
		page = readInteger();
		rankType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(page);
		writeInteger(rankType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_COMMON_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_COMMON_RANK";
	}

	public int getPage(){
		return page;
	}
		
	public void setPage(int page){
		this.page = page;
	}

	public int getRankType(){
		return rankType;
	}
		
	public void setRankType(int rankType){
		this.rankType = rankType;
	}
	


	@Override
	public void execute() {
		RankHandlerFactory.getHandler().handleCommonRank(this.getSession().getPlayer(), this);
	}
}