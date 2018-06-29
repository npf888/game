package com.gameserver.lobby.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.lobby.handler.LobbyHandlerFactory;

/**
 * 主动请求 进入老虎机后的第一个页面展示的 四个阶段的 彩金数
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotNewJackpots extends CGMessage{
	
	/** 根据bet数 获取的相应的彩金值 */
	private int slotType;
	
	public CGSlotNewJackpots (){
	}
	
	public CGSlotNewJackpots (
			int slotType ){
			this.slotType = slotType;
	}
	
	@Override
	protected boolean readImpl() {
		slotType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(slotType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_NEW_JACKPOTS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_NEW_JACKPOTS";
	}

	public int getSlotType(){
		return slotType;
	}
		
	public void setSlotType(int slotType){
		this.slotType = slotType;
	}
	


	@Override
	public void execute() {
		LobbyHandlerFactory.getHandler().handleSlotNewJackpots(this.getSession().getPlayer(), this);
	}
}