package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 修改昵称 返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooChangeName extends GCMessage{
	
	/** 返回用户改的名称 */
	private String nickname;

	public GCBazooChangeName (){
	}
	
	public GCBazooChangeName (
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
		return MessageType.GC_BAZOO_CHANGE_NAME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_CHANGE_NAME";
	}

	public String getNickname(){
		return nickname;
	}
		
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
}