package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotService;
/**
 * 
 * @author 郭君伟
 *
 */
public class ChangSpinTimes implements IRedisMessage {
	
	private int slotTemplaId;
	

	
	public int getSlotTemplaId() {
		return slotTemplaId;
	}



	public void setSlotTemplaId(int slotTemplaId) {
		this.slotTemplaId = slotTemplaId;
	}



	@Override
	public void execute() {
		
		SlotService slotService = Globals.getSlotService();
		Slot slot = slotService.getSlotByTemplateId(slotTemplaId);
		slot.spin();
	}

}
