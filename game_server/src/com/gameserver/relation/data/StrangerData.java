package com.gameserver.relation.data;

/**
 * 陌生人
 * @author 郭君伟
 *
 */
public class StrangerData {

	/**玩家唯一ID **/
	private long userId;
	
	/**头像 **/
	private String img;
	
	/** 名字 **/
	private String name;
	
	/** VIP等级 **/
	private int vipLevel;
	
	/** 等级 **/
	private int level;
	
	private int sex;
	
	private String countries;
	private int isRequest;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getIsRequest() {
		return isRequest;
	}

	public void setIsRequest(int isRequest) {
		this.isRequest = isRequest;
	}


	
	
}
