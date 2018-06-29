package com.gameserver.slot.data;

public class SlotSngView {
	
	/**老虎机类型 **/
	private int slotType;
	/** 人物头像图片  **/
	private String img;
	/** 赢得竞赛奖金 **/
	private long bonus;
	
	public int getSlotType() {
		return slotType;
	}
	public void setSlotType(int slotType) {
		this.slotType = slotType;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getBonus() {
		return bonus;
	}
	public void setBonus(long bonus) {
		this.bonus = bonus;
	}
	
	
	

}
