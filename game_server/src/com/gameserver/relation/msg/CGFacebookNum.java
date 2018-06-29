package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * facebook好友个数
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFacebookNum extends CGMessage{
	
	/** 好友个数 */
	private int friendNum;
	
	public CGFacebookNum (){
	}
	
	public CGFacebookNum (
			int friendNum ){
			this.friendNum = friendNum;
	}
	
	@Override
	protected boolean readImpl() {
		friendNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(friendNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_FACEBOOK_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FACEBOOK_NUM";
	}

	public int getFriendNum(){
		return friendNum;
	}
		
	public void setFriendNum(int friendNum){
		this.friendNum = friendNum;
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleFacebookNum(this.getSession().getPlayer(), this);
	}
}