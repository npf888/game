package com.gameserver.player.cache;

import com.db.model.HumanEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 玩家缓存信息
 * @author wayne
 *
 */
public class PlayerCacheInfo {

	private long playerId;
	//服务器id
	private String serverId;
	//玩家状态 1 在线 0 不在线
	private int playerState;
	//筹码数量
	private long gold;
	//等级
	private long level;
	//钻石
	private long diamond;
	//性别
	private int sex;
	//charm
	private long charm;
	//名字
	private String name;
	//图片
	private String img;
	//VIP等级
	private int vipLevel;
	
	//国籍
	private String countries;
	
	//年龄
	private int age;
	
	 /**总转次数 **/
   	private long slotRotate;
   	 /**总赢得分 **/
   	private long slotWin;
   	 /**单次赢取最大 **/
   	private long slotSingleWin;
   	 /**玩家总胜利次数 **/
   	private long slotWinNum;
   	
   	/**是否在游戏中 1 在游戏中 0 没有在 **/
   	private int isGame;
   
   	/**下线时间点**/
   	private long offlineTime;
   	
   	/**排行榜积分 **/
   	private long integral;
   	
	/**礼物ID **/
   	private int giftId;
   	
   	private String clubId;
   	
   	private String roomNumber;
	
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public int getPlayerState() {
		return playerState;
	}
	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	

	
	public long getLevel() {
		return level;
	}
	public void setLevel(long level) {
		this.level = level;
	}
	public long getDiamond() {
		return diamond;
	}
	public void setDiamond(long diamond) {
		this.diamond = diamond;
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
	
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
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
	

	public int getIsGame() {
		return isGame;
	}
	public void setIsGame(int isGame) {
		this.isGame = isGame;
	}
	public long getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(long offlineTime) {
		this.offlineTime = offlineTime;
	}
	
	public long getIntegral() {
		return integral;
	}
	public void setIntegral(long integral) {
		this.integral = integral;
	}
	
	public int getGiftId() {
		return giftId;
	}
	public void setGiftId(int giftId) {
		this.giftId = giftId;
	}
	/**
	 * 同步在线玩家信息
	 * @param human
	 * @return
	 */
	public static PlayerCacheInfo playerCacheInfoFromHuman(Human human){
		PlayerCacheInfo playerCacheInfo = new PlayerCacheInfo();
		playerCacheInfo.setName(human.getName());
		playerCacheInfo.setPlayerState(human.getOnlineStatus());
		playerCacheInfo.setServerId(Globals.getServerConfig().getServerId());
		playerCacheInfo.setPlayerId(human.getPassportId());
		playerCacheInfo.setDiamond(human.getDiamond());
		playerCacheInfo.setLevel(human.getLevel());
		playerCacheInfo.setSex(human.getGirl());
		playerCacheInfo.setImg(human.getImg());
		playerCacheInfo.setGold(human.getGold());
		playerCacheInfo.setCharm(human.getCharm());
//		playerCacheInfo.setVipLevel(human.getHumanVipNewManager().getVipLv());
		playerCacheInfo.setCountries(human.getCountries());
		playerCacheInfo.setAge(human.getAge());
		playerCacheInfo.setSlotRotate(human.getSlotRotate());
		playerCacheInfo.setSlotSingleWin(human.getSlotSingleWin());
		playerCacheInfo.setSlotWin(human.getSlotWin());
		playerCacheInfo.setSlotWinNum(human.getSlotWinNum());
		playerCacheInfo.setIsGame(human.getSlotRoomId().equals("")? 0:1);
		playerCacheInfo.setOfflineTime(human.getLastLogoutTime());
//		playerCacheInfo.setIntegral(Globals.getRankNewServer().getRankIntegral(human.getPassportId()));
		playerCacheInfo.setGiftId(human.getGiftId());
		playerCacheInfo.setClubId(human.getClubId());
		playerCacheInfo.setRoomNumber(human.getBazooRoom());
		return playerCacheInfo;
	}
	
	
	public static PlayerCacheInfo playerCacheInfoFromOfflineHuman(HumanEntity humanEntity){
		PlayerCacheInfo playerCacheInfo = new PlayerCacheInfo();
		playerCacheInfo.setName(humanEntity.getName());
		playerCacheInfo.setPlayerState(0);
		playerCacheInfo.setServerId(Globals.getServerConfig().getServerId());
		playerCacheInfo.setSex(humanEntity.getGirlFlag());
		playerCacheInfo.setDiamond(humanEntity.getDiamond());
		playerCacheInfo.setLevel(humanEntity.getLevel());
		playerCacheInfo.setPlayerId(humanEntity.getPassportId());
		playerCacheInfo.setImg(humanEntity.getImg());
		playerCacheInfo.setGold(humanEntity.getGold());
		playerCacheInfo.setCharm(humanEntity.getCharm());
		playerCacheInfo.setCountries(humanEntity.getCountries());
		playerCacheInfo.setAge(humanEntity.getAge());
		playerCacheInfo.setSlotRotate(humanEntity.getSlotRotate());
		playerCacheInfo.setSlotSingleWin(humanEntity.getSlotSingleWin());
		playerCacheInfo.setSlotWin(humanEntity.getSlotWin());
		playerCacheInfo.setSlotWinNum(humanEntity.getSlotWinNum());
		playerCacheInfo.setIsGame(0);
		playerCacheInfo.setOfflineTime(humanEntity.getLastLogoutTime());
		playerCacheInfo.setIntegral(Globals.getRankNewServer().getRankIntegral(humanEntity.getPassportId()));
		playerCacheInfo.setClubId(humanEntity.getClubId());
		playerCacheInfo.setRoomNumber(humanEntity.getBazooRoom());
		return playerCacheInfo;
	}
	public String getClubId() {
		return clubId;
	}
	public void setClubId(String clubId) {
		this.clubId = clubId;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
}
