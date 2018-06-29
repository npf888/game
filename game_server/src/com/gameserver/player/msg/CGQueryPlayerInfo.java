package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 客户端请求用户信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGQueryPlayerInfo extends CGMessage{
	
	/** 用户id */
	private long userId;
	
	public CGQueryPlayerInfo (){
	}
	
	public CGQueryPlayerInfo (
			long userId ){
			this.userId = userId;
	}
	
	@Override
	protected boolean readImpl() {
		userId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(userId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_QUERY_PLAYER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUERY_PLAYER_INFO";
	}

	public long getUserId(){
		return userId;
	}
		
	public void setUserId(long userId){
		this.userId = userId;
	}
	


	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handleQueryPlayerInfo(this.getSession().getPlayer(), this);
	}
}