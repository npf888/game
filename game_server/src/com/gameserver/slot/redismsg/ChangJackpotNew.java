package com.gameserver.slot.redismsg;

import com.gameserver.common.Globals;
import com.gameserver.redis.IRedisMessage;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.handler.SlotHandlerFactory;

public class ChangJackpotNew implements IRedisMessage {

	
	private int slotTemplaId;
	private long which;
	private long jackPot;
	private long cumeJackPot;
	public int getSlotTemplaId() {
		return slotTemplaId;
	}

	public void setSlotTemplaId(int slotTemplaId) {
		this.slotTemplaId = slotTemplaId;
	}

	public long getWhich() {
		return which;
	}

	public void setWhich(long which) {
		this.which = which;
	}

	public long getJackPot() {
		return jackPot;
	}

	public void setJackPot(long jackPot) {
		this.jackPot = jackPot;
	}

	public long getCumeJackPot() {
		return cumeJackPot;
	}

	public void setCumeJackPot(long cumeJackPot) {
		this.cumeJackPot = cumeJackPot;
	}

	@Override
	public void execute() {
		SlotService slotService = Globals.getSlotService();
		Slot slot = slotService.getSlotByTemplateId(slotTemplaId);
		SlotHandlerFactory.getHandler().changJackpot(slot,jackPot,cumeJackPot,which);
		
	}

}
