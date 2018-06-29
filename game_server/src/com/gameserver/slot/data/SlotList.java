package com.gameserver.slot.data;

/**
 * 老虎机列表
 * @author 郭君伟
 *
 */
public class SlotList {
	
	/** 唯一ID **/
	private int slotId;
	
	/** 彩金 **/
	private long handsel;
	
	public int getSlotId() {
		return slotId;
	}
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	
	public long getHandsel() {
		return handsel;
	}
	public void setHandsel(long handsel) {
		this.handsel = handsel;
	}
	
	

}
