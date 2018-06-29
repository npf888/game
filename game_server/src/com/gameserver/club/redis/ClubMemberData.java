package com.gameserver.club.redis;

public class ClubMemberData {
	private long id;
//	private String clubId;
	private long signTime;
	private long donateTime;
	private long joinTime;
	private int zhiwu;
	private int gongxian;
	private int huoyue;
	private int sex;
	private int tanheVote;
	private int tanheState;
//	private long tanheTs;
//	private String agreeIds="";
//	private String refuseIds="";
	
	private String countries;
	private int level;
	private int vipLevel;
	private String name;	
	/**下线时间点**/
   	private long offlineTime;
   	private String img;
   	private int isOnline;
   	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
//	public String getClubId() {
//		return clubId;
//	}
//	public void setClubId(String clubId) {
//		this.clubId = clubId;
//	}
	public long getSignTime() {
		return signTime;
	}
	public void setSignTime(long signTime) {
		this.signTime = signTime;
	}
	public long getDonateTime() {
		return donateTime;
	}
	public void setDonateTime(long donateTime) {
		this.donateTime = donateTime;
	}
	public long getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}
	public int getZhiwu() {
		return zhiwu;
	}
	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}
	public int getGongxian() {
		return gongxian;
	}
	public void setGongxian(int gongxian) {
		this.gongxian = gongxian;
	}
	public int getHuoyue() {
		return huoyue;
	}
	public void setHuoyue(int huoyue) {
		this.huoyue = huoyue;
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(long offlineTime) {
		this.offlineTime = offlineTime;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	public int getTanheVote() {
		return tanheVote;
	}
	public void setTanheVote(int tanheVote) {
		this.tanheVote = tanheVote;
	}
	public int getTanheState() {
		return tanheState;
	}
	public void setTanheState(int tanheState) {
		this.tanheState = tanheState;
	}
//	public long getTanheTs() {
//		return tanheTs;
//	}
//	public void setTanheTs(long tanheTs) {
//		this.tanheTs = tanheTs;
//	}
	
}
