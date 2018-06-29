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
public class ChangStock implements IRedisMessage {
	
	private int slotTemplaId;
	
	/**true 押注 false返还 **/
	private boolean fly;
	
	private long changStock;
	
	

	public int getSlotTemplaId() {
		return slotTemplaId;
	}



	public void setSlotTemplaId(int slotTemplaId) {
		this.slotTemplaId = slotTemplaId;
	}



	


	public boolean isFly() {
		return fly;
	}



	public void setFly(boolean fly) {
		this.fly = fly;
	}



	public long getChangStock() {
		return changStock;
	}



	public void setChangStock(long changStock) {
		this.changStock = changStock;
	}



	@Override
	public void execute() {
		SlotService slotService = Globals.getSlotService();
		Slot slot = slotService.getSlotByTemplateId(slotTemplaId);
		
		if(fly){
			slot.bet(changStock);
		}else{
			slot.refund(changStock);
		}

	}

}
