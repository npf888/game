package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 紧接上一个接口，服务器等待一小会之后，告诉所有人谁改摇色子了shouldCallPassportId 如果等于 0 说明是重摇,不等于 0是 统一摇
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDiceUserShouldSwing extends GCMessage{
	
	/** 统一摇完之后 该哪个用户叫号 */
	private long shouldCallPassportId;

	public GCDiceUserShouldSwing (){
	}
	
	public GCDiceUserShouldSwing (
			long shouldCallPassportId ){
			this.shouldCallPassportId = shouldCallPassportId;
	}

	@Override
	protected boolean readImpl() {
		shouldCallPassportId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(shouldCallPassportId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DICE_USER_SHOULD_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DICE_USER_SHOULD_SWING";
	}

	public long getShouldCallPassportId(){
		return shouldCallPassportId;
	}
		
	public void setShouldCallPassportId(long shouldCallPassportId){
		this.shouldCallPassportId = shouldCallPassportId;
	}
}