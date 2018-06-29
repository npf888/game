package com.gameserver.slot.pojo;

/**
 * 排行榜数据  
 * @author 郭君伟
 *
 */
public class HumanRank implements Comparable<HumanRank> {
	
	private long passportId;
	
	private String img;
	
	private String name;
	
	private long value;

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(HumanRank o) {
		if(o.getValue() > this.value){
			return 1;
		}
		return -1;
	}

}
