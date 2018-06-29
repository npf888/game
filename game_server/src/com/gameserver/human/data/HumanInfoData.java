package com.gameserver.human.data;


/**
 * 玩家角色信息
 * 
 * @author Thinker
 * 
 */
public class HumanInfoData
{
	/** 玩家角色ID 主键 */
	private long roleId;
	/** 姓名 */
	private String name;
	/** 性别 */
	private int sex;
	/** 级别 */
	private long level;
	/** 级别 */
	private long vipLevel;
	/** 钻石 */
	private long diamond;
	/** 金币数量 */
	private long gold;
	/**当前经验*/
	private long curExp;
	/**经验上限*/
	private long maxExp;
	/**魅力值*/
	private long charm;
	/**当前所在城镇ID*/
	private int sceneId;
	
	/**gameview 首次充值记录*/
	private String gvfirst;
	/** 新手引导  ：0 大礼包 1 百家乐 2 德州 3 老虎**/
	private String newguide;
	
	/**玩家观看视频次数 **/
	private int watchNum;
	
	/**国籍 **/
	private String countries;
	
	/**排行榜积分 **/
   	private long integral;
	
   	/**新手大礼包:1已购买，0 未购买 **/
   	private int newGuyGift;
   	/**当天是否:'2017-07-25:0' 1 当天已经显示，0 当天未显示 **/
   	private int todayView;
   	
   	//无双吹牛 金币的数量
   	private long bazooGold;
   	
   	//用户在哪个房间
   	private String bazooRoom;
   	
	//新手的进度({classicCompleted:0, niuniuCompleted:0, showhandCompleted:0, redblackCompleted:0}）
	private String bazooNewGuyProcess;
	///////////////////////内部数据////////////////////////////

	
	public HumanInfoData()
	{
		
	}


	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
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

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getCurExp() {
		return curExp;
	}

	public void setCurExp(long curExp) {
		this.curExp = curExp;
	}

	public long getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(long maxExp) {
		this.maxExp = maxExp;
	}

	public long getCharm() {
		return charm;
	}


	public void setCharm(long charm) {
		this.charm = charm;
	}


	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}



	public long getVipLevel() {
		return vipLevel;
	}


	public void setVipLevel(long vipLevel) {
		this.vipLevel = vipLevel;
	}


	


	public String getNewguide() {
		return newguide;
	}


	public void setNewguide(String newguide) {
		this.newguide = newguide;
	}


	public String getGvfirst() {
		return gvfirst;
	}


	public void setGvfirst(String gvfirst) {
		this.gvfirst = gvfirst;
	}


	public int getWatchNum() {
		return watchNum;
	}


	public void setWatchNum(int watchNum) {
		this.watchNum = watchNum;
	}


	public String getCountries() {
		return countries;
	}


	public void setCountries(String countries) {
		this.countries = countries;
	}


	public long getIntegral() {
		return integral;
	}


	public void setIntegral(long integral) {
		this.integral = integral;
	}


	public int getNewGuyGift() {
		return newGuyGift;
	}


	public void setNewGuyGift(int newGuyGift) {
		this.newGuyGift = newGuyGift;
	}


	public int getTodayView() {
		return todayView;
	}


	public void setTodayView(int todayView) {
		this.todayView = todayView;
	}


	public long getBazooGold() {
		return bazooGold;
	}


	public void setBazooGold(long bazooGold) {
		this.bazooGold = bazooGold;
	}


	public String getBazooRoom() {
		return bazooRoom;
	}


	public void setBazooRoom(String bazooRoom) {
		this.bazooRoom = bazooRoom;
	}


	public String getBazooNewGuyProcess() {
		return bazooNewGuyProcess;
	}


	public void setBazooNewGuyProcess(String bazooNewGuyProcess) {
		this.bazooNewGuyProcess = bazooNewGuyProcess;
	}



   
	
}
