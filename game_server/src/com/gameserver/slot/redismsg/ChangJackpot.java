package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.handler.SlotHandlerFactory;
/**
 * 
 * @author 郭君伟
 *
 */
public class ChangJackpot implements IRedisMessage {
	
	private int slotTemplaId;
	
	private long getJack;
	
	
	
	public int getSlotTemplaId() {
		return slotTemplaId;
	}



	public void setSlotTemplaId(int slotTemplaId) {
		this.slotTemplaId = slotTemplaId;
	}



	public long getGetJack() {
		return getJack;
	}



	public void setGetJack(long getJack) {
		this.getJack = getJack;
	}



	@Override
	public void execute() {
		SlotService slotService = Globals.getSlotService();
		Slot slot = slotService.getSlotByTemplateId(slotTemplaId);
		SlotHandlerFactory.getHandler().changJackpot(slot,getJack);

	}

}
