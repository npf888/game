package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.lobby.handler.LobbyHandlerFactory;

/**
 * 请求最高彩金获得者前20位
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGJackpotListData extends CGMessage{
	
	/** 请求类型 1 请求最近彩金获得者 2 获取自己适合的游戏类型彩金 */
	private int operationType;
	
	public CGJackpotListData (){
	}
	
	public CGJackpotListData (
			int operationType ){
			this.operationType = operationType;
	}
	
	@Override
	protected boolean readImpl() {
		operationType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(operationType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_JACKPOT_LIST_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "CG_JACKPOT_LIST_DATA";
	}

	public int getOperationType(){
		return operationType;
	}
		
	public void setOperationType(int operationType){
		this.operationType = operationType;
	}
	


	@Override
	public void execute() {
		LobbyHandlerFactory.getHandler().handleJackpotListData(this.getSession().getPlayer(), this);
	}
}