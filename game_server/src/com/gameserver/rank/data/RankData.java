package com.gameserver.rank.data;

/**
 * 排行版数据
 * @author wayne
 *
 */
public class RankData {
	private long uId;
	private String img;
	private String name;
	private long score;

	public long getUId() {
		return uId;
	}
	public void setUId(long uId) {
		this.uId = uId;
	}

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
