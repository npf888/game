package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 玩家现在使用版本
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGClientVersion extends CGMessage{
	
	/**  */
	private String version;
	
	public CGClientVersion (){
	}
	
	public CGClientVersion (
			String version ){
			this.version = version;
	}
	
	@Override
	protected boolean readImpl() {
		version = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(version);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLIENT_VERSION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLIENT_VERSION";
	}

	public String getVersion(){
		return version;
	}
		
	public void setVersion(String version){
		this.version = version;
	}
	


	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handleClientVersion(this.getSession().getPlayer(), this);
	}
}