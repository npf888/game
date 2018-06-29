package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 客户端请求用户信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGQueryPlayerInfoName extends CGMessage{
	
	/** 用户名字 */
	private String userName;
	
	public CGQueryPlayerInfoName (){
	}
	
	public CGQueryPlayerInfoName (
			String userName ){
			this.userName = userName;
	}
	
	@Override
	protected boolean readImpl() {
		userName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(userName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_QUERY_PLAYER_INFO_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUERY_PLAYER_INFO_NAME";
	}

	public String getUserName(){
		return userName;
	}
		
	public void setUserName(String userName){
		this.userName = userName;
	}
	


	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handleQueryPlayerInfoName(this.getSession().getPlayer(), this);
	}
}