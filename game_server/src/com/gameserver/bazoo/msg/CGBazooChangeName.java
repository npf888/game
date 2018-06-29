package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 修改昵称
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooChangeName extends CGMessage{
	
	/** 用户要变成的昵称 */
	private String nickname;
	
	public CGBazooChangeName (){
	}
	
	public CGBazooChangeName (
			String nickname ){
			this.nickname = nickname;
	}
	
	@Override
	protected boolean readImpl() {
		nickname = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(nickname);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_CHANGE_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_CHANGE_NAME";
	}

	public String getNickname(){
		return nickname;
	}
		
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleBazooChangeName(this.getSession().getPlayer(), this);
	}
}