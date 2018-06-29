package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 请求 列出所有的房间
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoomPriList extends CGMessage{
	
	
	public CGRoomPriList (){
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
		return MessageType.CG_ROOM_PRI_List;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROOM_PRI_List";
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleRoomPriList(this.getSession().getPlayer(), this);
	}
}