package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.club.handler.ClubHandlerFactory;

/**
 * 俱乐部弹劾同意/拒绝
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClubTanheVote extends CGMessage{
	
	/** 类型 -1 拒绝 1 同意 */
	private int ret;
	
	public CGClubTanheVote (){
	}
	
	public CGClubTanheVote (
			int ret ){
			this.ret = ret;
	}
	
	@Override
	protected boolean readImpl() {
		ret = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(ret);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLUB_TANHE_VOTE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLUB_TANHE_VOTE";
	}

	public int getRet(){
		return ret;
	}
		
	public void setRet(int ret){
		this.ret = ret;
	}
	


	@Override
	public void execute() {
		ClubHandlerFactory.getHandler().handleClubTanheVote(this.getSession().getPlayer(), this);
	}
}