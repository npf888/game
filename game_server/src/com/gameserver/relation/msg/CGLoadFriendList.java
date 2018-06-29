package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 客户端请求好友列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGLoadFriendList extends CGMessage{
	
	
	public CGLoadFriendList (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_LOAD_FRIEND_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LOAD_FRIEND_LIST";
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleLoadFriendList(this.getSession().getPlayer(), this);
	}
}