package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * facebook好友
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFacebookFriendsSync extends CGMessage{
	
	/** 好友 */
	private String[] friendList;
	
	public CGFacebookFriendsSync (){
	}
	
	public CGFacebookFriendsSync (
			String[] friendList ){
			this.friendList = friendList;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
				friendList = new String[count];
				for(int i=0; i<count; i++){
			friendList[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(friendList.length);
		for(int i=0; i<friendList.length; i++){
			writeString(friendList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_FACEBOOK_FRIENDS_SYNC;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FACEBOOK_FRIENDS_SYNC";
	}

	public String[] getFriendList(){
		return friendList;
	}

	public void setFriendList(String[] friendList){
		this.friendList = friendList;
	}	
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleFacebookFriendsSync(this.getSession().getPlayer(), this);
	}
}