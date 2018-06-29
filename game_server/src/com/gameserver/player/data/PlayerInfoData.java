package com.gameserver.player.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gameserver.club.ClubZhiwu;
import com.gameserver.club.redis.ClubData;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.fw.ClubCache;
import com.gameserver.player.cache.PlayerCacheInfo;

/**
 * 玩家信息
 * @author wayne
 *
 */
public class PlayerInfoData {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PlayerInfoData.class);
	
	private long playerId;
	private String name;
	private String img;
	private long gold;
	private long diamond;
	private long level;
	private int sex;
	private long charm;
	private int viplevel;
	private String countries;
	private int age;
	 /**总转次数 **/
   	private long slotRotate;
   	 /**总赢得分 **/
   	private long slotWin;
   	 /**单次赢取最大 **/
   	private long slotSingleWin;
   	 /**玩家总胜利次数 **/
   	private long slotWinNum;
   	
   	private int rank;
   	private String clubId;
   	private int clubIco;
   	private int clubInvitedTimes;
    private int zhiwu;
   	/**排行榜积分 **/
   	private long integral;
   	private int isRequest;
   	/**新手大礼包:1已购买，0 未购买 **/
   	private int newGuyGift;
   	//成就  完成个数/总个数
  	private String achieveRate;
	public int getZhiwu() {
		return zhiwu;
	}
	public void setZhiwu(int zhiwu) {
		this.zhiwu = zhiwu;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}
	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}
	public long getGold() {
		return gold;
	}
	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getDiamond() {
		return diamond;
	}
	public void setDiamond(long diamond) {
		this.diamond = diamond;
	}
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}

	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public long getCharm() {
		return charm;
	}
	public void setCharm(long charm) {
		this.charm = charm;
	}
	
	public int getViplevel() {
		return viplevel;
	}
	public void setViplevel(int viplevel) {
		this.viplevel = viplevel;
	}
	
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public long getSlotRotate() {
		return slotRotate;
	}
	public void setSlotRotate(long slotRotate) {
		this.slotRotate = slotRotate;
	}
	public long getSlotWin() {
		return slotWin;
	}
	public void setSlotWin(long slotWin) {
		this.slotWin = slotWin;
	}
	public long getSlotSingleWin() {
		return slotSingleWin;
	}
	public void setSlotSingleWin(long slotSingleWin) {
		this.slotSingleWin = slotSingleWin;
	}
	public long getSlotWinNum() {
		return slotWinNum;
	}
	public void setSlotWinNum(long slotWinNum) {
		this.slotWinNum = slotWinNum;
	}
	
	public long getIntegral() {
		return integral;
	}
	public void setIntegral(long integral) {
		this.integral = integral;
	}
	
	public int getIsRequest() {
		return isRequest;
	}
	public void setIsRequest(int isRequest) {
		this.isRequest = isRequest;
	}
	
	
	public int getNewGuyGift() {
		return newGuyGift;
	}
	public void setNewGuyGift(int newGuyGift) {
		this.newGuyGift = newGuyGift;
	}
	public static PlayerInfoData convertFrom(PlayerCacheInfo playerCacheInfo)
	{
		PlayerInfoData playerInfoData = new PlayerInfoData();
		playerInfoData.setName(playerCacheInfo.getName());
		playerInfoData.setPlayerId(playerCacheInfo.getPlayerId());
		playerInfoData.setImg(playerCacheInfo.getImg());
		playerInfoData.setGold(playerCacheInfo.getGold());
		playerInfoData.setDiamond(playerCacheInfo.getDiamond());
		playerInfoData.setLevel(playerCacheInfo.getLevel());
		playerInfoData.setSex(playerCacheInfo.getSex());
		playerInfoData.setCharm(playerCacheInfo.getCharm());
		playerInfoData.setViplevel(playerCacheInfo.getVipLevel());
		playerInfoData.setCountries(playerCacheInfo.getCountries());
		playerInfoData.setAge(playerCacheInfo.getAge());
		playerInfoData.setSlotRotate(playerCacheInfo.getSlotRotate());
		playerInfoData.setSlotSingleWin(playerCacheInfo.getSlotSingleWin());
		playerInfoData.setSlotWin(playerCacheInfo.getSlotWin());
		playerInfoData.setSlotWinNum(playerCacheInfo.getSlotWinNum());
		playerInfoData.setIntegral(playerCacheInfo.getIntegral());
		String clubId = playerCacheInfo.getClubId();
		int zhiwu = ClubZhiwu.NONE.getLevel();
		ClubData cd = ClubCache.getClubs(clubId);
		if(cd!=null)
		{
			playerInfoData.setClubIco(cd.getIco());
			ClubMemberData cmd = ClubCache.retrieveMemberIfExist(clubId, playerCacheInfo.getPlayerId());
			if(cmd!=null)
			{
				zhiwu = cmd.getZhiwu();
			}
		}
		playerInfoData.setClubId(clubId);
		playerInfoData.setZhiwu(zhiwu);
		return  playerInfoData;
	}
	public int getClubIco() {
		return clubIco;
	}
	public void setClubIco(int clubIco) {
		this.clubIco = clubIco;
	}
	public int getClubInvitedTimes() {
		return clubInvitedTimes;
	}
	public void setClubInvitedTimes(int clubInvitedTimes) {
		this.clubInvitedTimes = clubInvitedTimes;
	}
	public String getAchieveRate() {
		return achieveRate;
	}
	public void setAchieveRate(String achieveRate) {
		this.achieveRate = achieveRate;
	}
	
	
}
