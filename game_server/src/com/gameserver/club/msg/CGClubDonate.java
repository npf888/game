package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐捐献
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubDonate extends CGMessage{
	
	/** 捐献数量 */
	private int count;
	
	public CGClubDonate (){
	}
	
	public CGClubDonate (
			int count ){
			this.count = count;
	}
	
	@Override
	protected boolean readImpl() {
		count = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(count);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_DONATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_DONATE";
	}

	public int getCount(){
		return count;
	}
		
	public void setCount(int count){
		this.count = count;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubDonate(this.getSession().getPlayer(), this);
	}
}