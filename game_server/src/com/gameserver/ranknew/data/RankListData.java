package com.gameserver.ranknew.data;

/**
 * 
 * @author 郭君伟
 *
 */
public class RankListData {
    
	/** 国家 **/
	private String countries;
	/** 玩家ID **/
	private long userId;
	/** 名字 **/
	private String name;
	/** 头像  **/
	private String img;
	/** 玩家等级 **/
	private int level;
	/**排名 **/
	private int rank;
	
	/**VIP等级 **/
	private int vipLevel;
	
	/** 玩家赢取 **/
	private long win;

	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}



	public long getWin() {
		return win;
	}

	public void setWin(long win) {
		this.win = win;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	
	
	
	
	
}
