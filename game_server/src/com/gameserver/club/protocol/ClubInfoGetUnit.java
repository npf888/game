package com.gameserver.club.protocol;

public class ClubInfoGetUnit {
	private String id;
	private String name;
	private Integer ico;
	private Integer level;
	private Integer progress;
	private String notice;
	private Integer huoyue;
	private Integer money;
	private Integer male;
	private Integer female;
	private Integer rankHuoYue;
	private Integer rankGongXian;
	private Integer clubType;
	private Integer limit;
	
	


	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getIco() {
		return ico;
	}

	public void setIco(Integer ico) {
		this.ico = ico;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Integer getHuoyue() {
		return huoyue;
	}

	public void setHuoyue(Integer huoyue) {
		this.huoyue = huoyue;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getMale() {
		return male;
	}

	public void setMale(Integer male) {
		this.male = male;
	}

	public Integer getFemale() {
		return female;
	}

	public void setFemale(Integer female) {
		this.female = female;
	}

	public Integer getRankHuoYue() {
		return rankHuoYue;
	}

	public void setRankHuoYue(Integer rankHuoYue) {
		this.rankHuoYue = rankHuoYue;
	}

	public Integer getRankGongXian() {
		return rankGongXian;
	}

	public void setRankGongXian(Integer rankGongxian) {
		this.rankGongXian = rankGongxian;
	}

	public Integer getClubType() {
		return clubType;
	}

	public void setClubType(Integer clubType) {
		this.clubType = clubType;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
