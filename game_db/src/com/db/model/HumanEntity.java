package com.db.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 数据库实体类：角色信息，暂时先放在这儿
 * @author Thinker
 */
@Entity
@Table(name = "t_human_info")
public class HumanEntity implements BaseEntity<Long>
{
	
	private static final long serialVersionUID = 372925036837477771L;
	/** 玩家角色ID 主键 */
	private long id;
	/** 玩家账号ID */
	private long passportId;
	/**图像*/
	private String img;
	/** 玩家的名字  */
	private String name;
	/** 是否为女性角色 */
	private int girlFlag;
	/** 等级 */
	private long level=1;
	
	/** 钻石 */
	private long diamond;
	/** 金币 */
	private long gold;
	/** 点券 */
	private long coupon;
	/** 当前经验 */
	private long curExp;
	/**魅力值*/
	private long charm;
	/** 所在场景Id */
	private int sceneId;

	
	/** 上次登陆IP */
	private String lastLoginIp;
	/** 上次登陆时间 */
	private long lastLoginTime;
	/** 上次登出时间 */
	private long lastLogoutTime;
	/** 累计在线时长(分钟) */
	private int totalMinute;
	/** 在线状态 */
	private int onlineStatus;
	/** 空闲时间 */
	private int idleTime;
	/** 创建时间 */
	private long createTime;
	/**首充 **/
	private long isPay;
	/**gameview 首存记录**/
    private String gameview;
    /**新手引导 **/
    private String newguide;
    /**添加好友ID隔天清空 **/
    private String addfriendIds;
    /**兑换随机码 **/
    private String receivecode;
    /**视频观看次数 **/
    private int watchNum;
    /**视频观看时间 **/
    private Date watchTime;
    /**国籍 **/
    private String countries;
    /**年龄 **/
    private int age;
    /**总转次数 **/
	private long slotRotate;
	 /**总赢得分 **/
	private long slotWin;
	 /**单次赢取最大 **/
	private long slotSingleWin;
	 /**玩家总胜利次数 **/
	private long slotWinNum;
	/**老虎机房间ID **/
	private String slotRoomId;
	/**老虎机ID **/
	private int slotId;
	/**好友ID,用 ， 分割 **/
	private String friendId;
	/**竞赛排名领奖记录 **/
	private String snginfo;
	/**请求的添加好友 但是还没有同意的好友ids 用','分割 **/
	private String requestFriendIds;
	/**新手大礼包**/
	private int newGuyGift;
	/**当天是否:'2017-07-25:0' 1 当天已经显示，0 当天未显示 **/
   	private int todayView;
	/**优惠券 比例**/
	private long couponExtraChip;
	/**优惠券结束日期**/
	private Date couponDurationDate;
	/**用户开始 循环处理 周 月活动的 开始时间**/
	private String regularTime;
	/**俱乐部id**/
	private String clubId;
	/**最近一次俱乐部签到时间戳 **/
	private Long clubSignTs;
	/**最近一次俱乐部捐献时间戳 **/
	private Long clubDonateTs;
	
	private Date doubleExpEndTime;
	private String clientVersion;
	
	
	/**
	 * 无双吹牛的字段
	 */
	//无双吹牛的房间号
	private String bazooRoom;
	//吹牛的金币
	private long bazooGold;
	//代理商是否显示 0：显示，1：不显示
	private int bazooAgentDisplay;
	//机器人 是否显示 0：显示，1：不显示
	private int bazooRobotDisplay;
	//新手的进度(1：，2：，3：，4：）
	private String bazooNewGuyProcess;
	public HumanEntity()
	{
		
	}

	@Id
	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public void setId(Long id)
	{
		this.id = id;
	}
	

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	@Column
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Column(length = 36)
	public String getName() {
		return name;
	}
	
	@Column
	public String getBazooNewGuyProcess() {
		return bazooNewGuyProcess;
	}

	public void setBazooNewGuyProcess(String bazooNewGuyProcess) {
		this.bazooNewGuyProcess = bazooNewGuyProcess;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public int getGirlFlag() {
		return this.girlFlag;
	}

	public void setGirlFlag(int girlFlag) {
		this.girlFlag = girlFlag;
	}
	@Column
	public long getLevel() {
		return this.level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public long getBazooGold() {
		return bazooGold;
	}

	public void setBazooGold(long bazooGold) {
		this.bazooGold = bazooGold;
	}

	@Column
	public long getDiamond() {
		return diamond;
	}

	public void setDiamond(long diamond) {
		this.diamond = diamond;
	}

	@Column
	public long getGold() {
		return this.gold;
	}

	public void setGold(long value) {
		this.gold = value;
	}

	@Column
	public long getCoupon() {
		return this.coupon;
	}
	
	public void setCoupon(long value) {
		this.coupon = value;
	}
	
	@Column
	public long getCharm() {
		return this.charm;
	}

	public void setCharm(long value) {
		this.charm = value;
	}
	
	@Column
	public long getCurExp() {
		return this.curExp;
	}

	public void setCurExp(long value) {
		this.curExp = value;
	}


	@Column(columnDefinition = " int default 0", nullable = false)
	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}


	@Column
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	@Column
	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column
	public long getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(long lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
	
	@Column(columnDefinition = " int default 0", nullable = false)
	public int getTotalMinute() {
		return totalMinute;
	}

	public void setTotalMinute(int totalMinute) {
		this.totalMinute = totalMinute;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}
	@Column
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	@Column
	public long getIsPay() {
		return isPay;
	}

	public void setIsPay(long isPay) {
		this.isPay = isPay;
	}
	
	@Column
	public String getGameview() {
		return gameview;
	}

	public void setGameview(String gameview) {
		this.gameview = gameview;
	}
	
	@Column
	public String getNewguide() {
		return newguide;
	}

	public void setNewguide(String newguide) {
		this.newguide = newguide;
	}

	@Column
	public String getAddfriendIds() {
		return addfriendIds;
	}

	public void setAddfriendIds(String addfriendIds) {
		this.addfriendIds = addfriendIds;
	}

	@Column
	public String getReceivecode() {
		return receivecode;
	}

	public void setReceivecode(String receivecode) {
		this.receivecode = receivecode;
	}

	@Column
	public int getWatchNum() {
		return watchNum;
	}

	public void setWatchNum(int watchNum) {
		this.watchNum = watchNum;
	}

	@Column
	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}
	@Column
	public Date getWatchTime() {
		return watchTime;
	}

	public void setWatchTime(Date watchTime) {
		this.watchTime = watchTime;
	}

	@Column
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column
	public long getSlotRotate() {
		return slotRotate;
	}

	public void setSlotRotate(long slotRotate) {
		this.slotRotate = slotRotate;
	}

	@Column
	public long getSlotWin() {
		return slotWin;
	}

	public void setSlotWin(long slotWin) {
		this.slotWin = slotWin;
	}

	@Column
	public long getSlotSingleWin() {
		return slotSingleWin;
	}

	public void setSlotSingleWin(long slotSingleWin) {
		this.slotSingleWin = slotSingleWin;
	}

	@Column
	public long getSlotWinNum() {
		return slotWinNum;
	}

	public void setSlotWinNum(long slotWinNum) {
		this.slotWinNum = slotWinNum;
	}

	@Column
	public String getSlotRoomId() {
		return slotRoomId;
	}

	public void setSlotRoomId(String slotRoomId) {
		this.slotRoomId = slotRoomId;
	}

	@Column
	public int getSlotId() {
		return slotId;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	@Column
	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	@Column
	public String getSnginfo() {
		return snginfo;
	}

	public void setSnginfo(String snginfo) {
		this.snginfo = snginfo;
	}

	public String getRequestFriendIds() {
		return requestFriendIds;
	}

	public void setRequestFriendIds(String requestFriendIds) {
		this.requestFriendIds = requestFriendIds;
	}

	public int getNewGuyGift() {
		return newGuyGift;
	}

	public void setNewGuyGift(int newGuyGift) {
		this.newGuyGift = newGuyGift;
	}

	public long getCouponExtraChip() {
		return couponExtraChip;
	}

	public void setCouponExtraChip(long couponExtraChip) {
		this.couponExtraChip = couponExtraChip;
	}

	public Date getCouponDurationDate() {
		return couponDurationDate;
	}

	public void setCouponDurationDate(Date couponDurationDate) {
		this.couponDurationDate = couponDurationDate;
	}

	public String getRegularTime() {
		return regularTime;
	}

	public void setRegularTime(String regularTime) {
		this.regularTime = regularTime;
	}

	public int getTodayView() {
		return todayView;
	}

	public void setTodayView(int todayView) {
		this.todayView = todayView;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	public Long getClubSignTs() {
		return clubSignTs;
	}

	public void setClubSignTs(Long clubSignTs) {
		this.clubSignTs = clubSignTs;
	}

	public Long getClubDonateTs() {
		return clubDonateTs;
	}

	public void setClubDonateTs(Long clubDonateTs) {
		this.clubDonateTs = clubDonateTs;
	}

	public Date getDoubleExpEndTime() {
		return doubleExpEndTime;
	}

	public void setDoubleExpEndTime(Date doubleExpEndTime) {
		this.doubleExpEndTime = doubleExpEndTime;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getBazooRoom() {
		return bazooRoom;
	}

	public void setBazooRoom(String bazooRoom) {
		this.bazooRoom = bazooRoom;
	}

	public int getBazooAgentDisplay() {
		return bazooAgentDisplay;
	}

	public void setBazooAgentDisplay(int bazooAgentDisplay) {
		this.bazooAgentDisplay = bazooAgentDisplay;
	}

	public int getBazooRobotDisplay() {
		return bazooRobotDisplay;
	}

	public void setBazooRobotDisplay(int bazooRobotDisplay) {
		this.bazooRobotDisplay = bazooRobotDisplay;
	}

	
	

	

	 
   

}
