package com.gameserver.slot.data;

/**
 * 老虎机实时排行数据
 * @author 郭君伟
 *
 */
public class SlotRankData {
   
	
	/**竞赛类型 **/
	private int tournamentType;
	/**名次 **/
	private int rank;
	/** 头像 **/
	private String img;
	/** 头像 **/
	private String name;
	/** 累计中奖金额 **/
	private long bonus;
	/**vip等级**/
	private int vipLevel;

	private long passportId;

	public int getTournamentType() {
		return tournamentType;
	}

	public void setTournamentType(int tournamentType) {
		this.tournamentType = tournamentType;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	
	
	
	
	
}
