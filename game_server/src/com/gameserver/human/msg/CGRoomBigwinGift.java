package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * bigWin分享礼物
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoomBigwinGift extends CGMessage{
	
	/** 礼物ID */
	private int giftId;
	
	public CGRoomBigwinGift (){
	}
	
	public CGRoomBigwinGift (
			int giftId ){
			this.giftId = giftId;
	}
	
	@Override
	protected boolean readImpl() {
		giftId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(giftId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ROOM_BIGWIN_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROOM_BIGWIN_GIFT";
	}

	public int getGiftId(){
		return giftId;
	}
		
	public void setGiftId(int giftId){
		this.giftId = giftId;
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleRoomBigwinGift(this.getSession().getPlayer(), this);
	}
}