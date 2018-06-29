package com.robot.slot.slotname;
/**
 * 表示机器人中的  每一个 老虎机
 * @author JavaServer
 *
 */
public class SlotName {

	private	String slotName;
	private int slotId;
	private	int slotType;
	private int bet;
	public String getSlotName() {
		return slotName;
	}
	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}
	public int getSlotId() {
		return slotId;
	}
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	public int getSlotType() {
		return slotType;
	}
	public void setSlotType(int slotType) {
		this.slotType = slotType;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	
}
