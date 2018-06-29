package com.gameserver.ranknew.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.ranknew.handler.RanknewHandlerFactory;

/**
 * 排行
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRequestRank extends CGMessage{
	
	/** 开始位置（包括） */
	private int start;
	/** 结束位置（包括） */
	private int end;
	
	public CGRequestRank (){
	}
	
	public CGRequestRank (
			int start,
			int end ){
			this.start = start;
			this.end = end;
	}
	
	@Override
	protected boolean readImpl() {
		start = readInteger();
		end = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(start);
		writeInteger(end);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REQUEST_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQUEST_RANK";
	}

	public int getStart(){
		return start;
	}
		
	public void setStart(int start){
		this.start = start;
	}

	public int getEnd(){
		return end;
	}
		
	public void setEnd(int end){
		this.end = end;
	}
	


	@Override
	public void execute() {
		RanknewHandlerFactory.getHandler().handleRequestRank(this.getSession().getPlayer(), this);
	}
}