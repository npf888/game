package com.gameserver.http.json;

public class RankDataVO {

	/** 玩家ID **/
	private long userId = 0;
	/** 名字 **/
	private String name = "";
	/** 头像  **/
	private String img = "";
	/** 玩家等级 **/
	private int level = 0;
	/** 玩家赢取 **/
	private long win = 0;

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
	
	
	
}
