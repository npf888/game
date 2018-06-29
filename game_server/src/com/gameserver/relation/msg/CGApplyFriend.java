package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 客户端处理好友请求
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGApplyFriend extends CGMessage{
	
	/** 请求id */
	private long playerId;
	/** 处理结果 */
	private int result;
	
	public CGApplyFriend (){
	}
	
	public CGApplyFriend (
			long playerId,
			int result ){
			this.playerId = playerId;
			this.result = result;
	}
	
	@Override
	protected boolean readImpl() {
		playerId = readLong();
		result = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeInteger(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_APPLY_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_APPLY_FRIEND";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleApplyFriend(this.getSession().getPlayer(), this);
	}
}