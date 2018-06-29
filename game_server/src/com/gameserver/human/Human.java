package com.gameserver.human;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.common.LogReasons.DiamondLogReason;
import com.common.LogReasons.ILogReason;
import com.common.LogReasons.LevelLogReason;
import com.common.LogReasons.OnlineTimeLogReason;
import com.common.constants.DisconnectReason;
import com.common.constants.Loggers;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.template.TemplateService;
import com.core.util.KeyValuePair;
import com.core.util.LogUtils;
import com.db.model.HumanEntity;
import com.db.model.UserInfo;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.club.redis.ClubMemberData;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.common.i18n.LangService;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.currency.Currency;
import com.gameserver.currency.CurrencyProcessor;
import com.gameserver.fw.ClubCache;
import com.gameserver.human.manager.HumanAchievementManager;
import com.gameserver.human.manager.HumanActivityManager;
import com.gameserver.human.manager.HumanAttackBossManager;
import com.gameserver.human.manager.HumanBaccartManager;
import com.gameserver.human.manager.HumanBagManager;
import com.gameserver.human.manager.HumanBazooAchieveManager;
import com.gameserver.human.manager.HumanBazooManager;
import com.gameserver.human.manager.HumanBazooNewGuyManager;
import com.gameserver.human.manager.HumanBazooPersonalManager;
import com.gameserver.human.manager.HumanBazooRankManager;
import com.gameserver.human.manager.HumanBazooSignInManager;
import com.gameserver.human.manager.HumanBazooTaskManager;
import com.gameserver.human.manager.HumanBazooWinsManager;
import com.gameserver.human.manager.HumanCollectManager;
import com.gameserver.human.manager.HumanCompensationManager;
import com.gameserver.human.manager.HumanGiftManager;
import com.gameserver.human.manager.HumanGivealikeManager;
import com.gameserver.human.manager.HumanInitManager;
import com.gameserver.human.manager.HumanLuckySpinManager;
import com.gameserver.human.manager.HumanMailManager;
import com.gameserver.human.manager.HumanMiscManager;
import com.gameserver.human.manager.HumanMonthCardManager;
import com.gameserver.human.manager.HumanNewComerManager;
import com.gameserver.human.manager.HumanPayguideManager;
import com.gameserver.human.manager.HumanRechargeManager;
import com.gameserver.human.manager.HumanRefundManager;
import com.gameserver.human.manager.HumanRegularTimeManager;
import com.gameserver.human.manager.HumanRelationManager;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.human.manager.HumanTaskManager;
import com.gameserver.human.manager.HumanTexasManager;
import com.gameserver.human.manager.HumanTreasuryManager;
import com.gameserver.human.manager.HumanVipManager;
import com.gameserver.human.manager.HumanVipNewManager;
import com.gameserver.human.manager.HumanWeekCardManager;
import com.gameserver.human.manager.HumanWeekSignInManager;
import com.gameserver.human.template.LevelGift;
import com.gameserver.human.template.RoleExpTemplate;
import com.gameserver.item.enums.ItemType;
import com.gameserver.item.template.InteractiveItemTemplate;
import com.gameserver.item.template.ItemTemplate;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerExitReason;
import com.gameserver.player.enums.PlayerRoleEnum;
import com.gameserver.player.msg.GCNotifyException;
import com.gameserver.redis.enums.RedisKey;
import com.gameserver.redis.msg_processing.processor.ClubMemberDataChangedProcessor;
import com.gameserver.role.Role;
import com.gameserver.role.RoleType;
import com.gameserver.role.properties.PropertyType;
import com.gameserver.role.properties.RoleBaseIntProperties;
import com.gameserver.role.properties.RoleBaseStrProperties;
import com.gameserver.slot.pojo.SngInfo;

/**
 * 玩家角色
 * 
 * @author Thinker
 */
public class Human extends Role implements PersistanceObject<Long, HumanEntity>, RoleDataHolder {
	/** 日志 */
	private static final Logger logger = Loggers.humanLogger;
	
	/** 人物uuid */
	private long roleUUID;
	/** 角色所属玩家 */
	private Player player;
	/**图像*/
	private String img;
	/** 是否为女性角色 */
	private int girl = 1;
	private int sceneId;
	/** 是否被删除 */
	private int deleted;
	/** 是否已在数据库中 */
	private boolean isInDb;
	/** 生命期 */
	private LifeCycle lifeCycle;
	/** 在线状态 */
	private int onlineStatus;
	/**首充 **/
	private long isPay = 0;
	
	/**gameview 首存 **/
	private Set<String> gameview = new HashSet<String>();
	/**新手引导 **/
    private String newguide;
    
    /**观看次数 **/
    private int watchNum;
    /**观看次数 **/
    private Date watchTime;
    /**国籍 **/
    private String countries;
    /**国籍 **/
    private String ipCountries;
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
   	
   	/**新手大礼包:1已购买，0 未购买**/
   	private int newGuyGift;
	/**当天是否:'2017-07-25:0' 1 当天已经显示，0 当天未显示 **/
   	private int todayView;
   	/**优惠券 比例**/
	private long couponExtraChip;
	/**优惠券结束日期**/
	private Date couponDurationDate;
	
	/**用户开始 循环处理 周 月活动的 开始时间**/
	private String regularTime;
    /**所在房间ID **/
    private volatile String slotRoomId = "";
    /**别人送的礼物ID **/
    private int giftId = 0;
   
	/**好友ID ,用 ，分割 **/
    private String friendId;
    
	/**请求的添加好友 但是还没有同意的好友ids 用','分割 **/
    private String requestFriendIds;
    /**俱乐部id**/
    private String clubId;
	/**最近一次俱乐部签到时间戳*/
	private Long clubSignTs;
	/**最近一次俱乐部捐献时间戳*/
	private Long clubDonateTs;
	
	private Date doubleExpEndTime;
	
	private String clientVersion;
	//无双吹牛房间号
	private String bazooRoom;
	//无双吹牛的 金币
	private long bazooGold;
	//代理商是否显示在排行榜 0：显示，1：不显示
	private int bazooAgentDisplay;
	//机器人 是否显示在排行榜 0：显示，1：不显示
	private int bazooRobotDisplay;
	
	//新手的进度({classicCompleted:0, niuniuCompleted:0, showhandCompleted:0, redblackCompleted:0}）
	private String bazooNewGuyProcess;
	
    /**添加好友ID隔天清空 **/
    private Map<Long,String> addfriendIds = new HashMap<Long,String>();
    
    /**兑换码记录 **/
    private Set<String> receivecode = new HashSet<String>();
    
    /**竞赛经历 **/
    private List<SngInfo> snginfos = new ArrayList<SngInfo>();

	/** 德州管理器 */
	private HumanTexasManager humanTexasManager;
	/** 背包 */
	private HumanBagManager humanBagManager;
	/** 月卡 */
	private HumanMonthCardManager humanMonthCardManager;
	/** 周卡 */
	private HumanWeekCardManager humanWeekCardManager;
	/**邮件管理器*/
	private HumanMailManager humanMailManager;
	/**好友管理器*/
	private HumanRelationManager humanRelationManager;
	/**签到管理器*/
	private HumanWeekSignInManager humanWeekSignInManager;
	/**misc 管理器*/
	private HumanMiscManager humanMiscManager;
	/**vip 管理器*/
	private HumanVipManager humanVipManager;
	
	/**vip新 管理器*/
	private HumanVipNewManager humanVipNewManager;
	
	/**充值管理器*/
	private HumanRechargeManager humanRechargeManager;
	/**任务管理器*/
	private HumanTaskManager humanTaskManager;
	/**活动管理器*/
	private HumanActivityManager humanActivityManager;
	/**用户补偿管理器*/
	private HumanCompensationManager humanCompensationManager;
	/**用户返还管理器*/
	private HumanRefundManager humanRefundManager;
	/**百家乐管理器*/
	private HumanBaccartManager humanBaccartManager;
	/**幸运转盘*/
	private HumanLuckySpinManager humanLuckySpinManager;
	/**老虎机信息*/
	private HumanSlotManager humanSlotManager;
	/**随机礼包管理器  **/
	private HumanGiftManager humanGiftManager;
	private HumanNewComerManager humanNewComerManager;
	/**成就管理器  **/
	private HumanAchievementManager humanAchievementManager;
	/**收集系统 **/
	private HumanCollectManager humanCollectManager;
	/**存钱罐 **/
	private HumanTreasuryManager humanTreasuryManager;


	/**周、月特惠充值活动（不是活动 是功能） **/
	private HumanRegularTimeManager humanRegularTimeManager;
	/**用户攻击boss的个人信息**/
	private HumanAttackBossManager humanAttackBossManager;
	/**用户攻击boss的个人信息**/
	private HumanGivealikeManager humanGivealikeManager;
	/**用户攻击boss的个人信息**/
	private HumanPayguideManager humanPayguideManager;
	/**无双吹牛**/
	private HumanBazooManager humanBazooManager;
	/**无双吹牛**/
	private HumanBazooSignInManager humanBazooSignInManager;
	/**无双吹牛**/
	private HumanBazooRankManager humanBazooRankManager;
	/**无双吹牛**/
	private HumanBazooPersonalManager humanBazooPersonalManager;
	/**无双吹牛**/
	private HumanBazooTaskManager humanBazooTaskManager;
	/**无双吹牛**/
	private HumanBazooAchieveManager humanBazooAchieveManager;
	/**无双吹牛**/
	private HumanBazooWinsManager humanBazooWinsManager;
	private HumanBazooNewGuyManager humanBazooNewGuyManager;

	private UserInfo userInfo;
	/**
	 * 类参数构造器
	 * 
	 * @param player
	 * 
	 */
	public Human(Player player) {
		super(RoleType.HUMAN);
		this.player = player;
		lifeCycle = new LifeCycleImpl(this);

		// 德州扑克
//		humanTexasManager = new HumanTexasManager(this);
		// 背包
		humanBagManager = new HumanBagManager(this);
		// 月卡
//		humanMonthCardManager = new HumanMonthCardManager(this);
		// 周卡
//		humanWeekCardManager = new HumanWeekCardManager(this);
		//邮件
		humanMailManager = new HumanMailManager(this);
		//好友
		humanRelationManager = new HumanRelationManager(this);
		//签到
//		humanWeekSignInManager = new HumanWeekSignInManager(this);
		//misc
		humanMiscManager = new HumanMiscManager(this);
		// vip 
		humanVipManager = new HumanVipManager(this);
		
//		humanVipNewManager = new HumanVipNewManager(this);
		
		//充值
		humanRechargeManager = new HumanRechargeManager(this);
		//任务
//		humanTaskManager = new HumanTaskManager(this);
		//活动
//		humanActivityManager = new HumanActivityManager(this);
		//补偿
		humanCompensationManager = new HumanCompensationManager(this);
		humanRefundManager = new HumanRefundManager(this);
		//百家乐
//		humanBaccartManager = new HumanBaccartManager(this);
//		humanLuckySpinManager= new HumanLuckySpinManager(this);
//		humanSlotManager= new HumanSlotManager(this);
//		humanGiftManager = new HumanGiftManager(this);
//		humanNewComerManager = new HumanNewComerManager(this);
//		humanAchievementManager = new HumanAchievementManager(this);
		
//		humanCollectManager = new HumanCollectManager(this);
//		humanTreasuryManager = new HumanTreasuryManager(this);
//
//		humanRegularTimeManager = new HumanRegularTimeManager(this);
//		humanAttackBossManager = new HumanAttackBossManager(this);
//		humanGivealikeManager = new HumanGivealikeManager(this);
//		humanPayguideManager = new HumanPayguideManager(this);
		humanBazooManager = new HumanBazooManager(this);
		humanBazooSignInManager = new HumanBazooSignInManager(this);
		humanBazooRankManager = new HumanBazooRankManager(this);
		humanBazooPersonalManager = new HumanBazooPersonalManager(this);
		humanBazooTaskManager = new HumanBazooTaskManager(this);
		humanBazooAchieveManager = new HumanBazooAchieveManager(this);
		humanBazooWinsManager = new HumanBazooWinsManager(this);
		humanBazooNewGuyManager = new HumanBazooNewGuyManager(this);

	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * 是否为女性角色 ?
	 * 
	 * @return
	 * 
	 */
	public int getGirl() {
		return this.girl;
	}

	/**
	 * 设置是否为女性角色
	 * 
	 * @param value
	 * 
	 */
	public void setGirl(int value) {
		this.girl = value;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getBazooRoom() {
		return bazooRoom;
	}

	public void setBazooRoom(String bazooRoom) {
		this.bazooRoom = bazooRoom;
	}

	public long getBazooGold() {
		return bazooGold;
	}

	public void setBazooGold(long bazooGold) {
		this.bazooGold = bazooGold;
	}

	public String getBazooNewGuyProcess() {
		return bazooNewGuyProcess;
	}

	public void setBazooNewGuyProcess(String bazooNewGuyProcess) {
		this.bazooNewGuyProcess = bazooNewGuyProcess;
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

	/**
	 * 在角色数据加载完成之后的初始化
	 * 
	 * @param templateServ
	 * @param langServ
	 */
	public void onInit(TemplateService templateServ, LangService langServ) {
		logger.info("玩家id[" + getCharId() + "]玩家名[" + getName() + "]角色数据加载完成");
		
		//判断是否是机器人
		if(this.getPlayer().getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
			if(this.getImg()==null || this.getImg().equalsIgnoreCase("")){
				int randomNum = (int)(Math.random()*12) +1;
				String imgName = "head_"+randomNum;
				this.setImg(imgName+".png");
			}
			//TODO 机器人初始属性修改
			this.getPlayer().getHuman().setLevel(100);
//			this.getPlayer().getHuman().setGold(9999999999999l);
			this.getPlayer().getHuman().setBazooRobotDisplay(1);//机器人默认不显示 在排行榜上
			
		}
		if(this.getImg()==null || this.getImg().length() == 0){
			this.setImg(this.getPlayer().getImg());
		}
	
//		humanTexasManager.init();
		humanBagManager.init();
//		humanMonthCardManager.init();
//		humanWeekCardManager.init();
		humanMailManager.init();
		humanRelationManager.init();
//		humanWeekSignInManager.init();
		humanMiscManager.init();
//		humanVipManager.init();
		humanRechargeManager.init();
//		humanTaskManager.init();
//		humanActivityManager.init();
		humanCompensationManager.init();
//		humanRefundManager.init();
//		humanBaccartManager.init();
//		humanLuckySpinManager.init();
//		humanSlotManager.init();
//		humanGiftManager.init();
//		humanNewComerManager.init();
//		humanAchievementManager.init();
//		humanCollectManager.init();
//		humanTreasuryManager.init();
//		humanRegularTimeManager.init();
//		humanAttackBossManager.init();
//		humanGivealikeManager.init();
//		humanAttackBossManager.init();
//		humanPayguideManager.init();
		humanBazooManager.init();
		humanBazooSignInManager.init();
		humanBazooRankManager.init();
		humanBazooPersonalManager.init();
		humanBazooTaskManager.init();
		humanBazooAchieveManager.init();
		humanBazooWinsManager.init();
		humanBazooNewGuyManager.init();
		Globals.getRankNewServer().loginData(this.getPassportId());
		
		logger.info("玩家id[" + getCharId() + "]玩家名[" + getName()+ "]加载完成后的初始化工作完成");

	}
	
	
	/**
	 * 在数据加载完之后的登陆
	 * 
	 * @param isFirstLogin
	 */
	public void onLogin(boolean isFirstLogin) {

		logger.info("[玩家登陆加载] [成功] [初次登陆标识:" + isFirstLogin + "]");
		if (isFirstLogin) {

			logger.info("玩家id[" + getCharId() + "]玩家名[" + getName() + "]首登成功");

		}

		long nowTime = Globals.getTimeService().now();
		// 记录登陆时间和 IP
		if (this.getPlayer().getSession() != null) {
			this.logLogin(nowTime, getPlayer().getSession().getIp());
		}
		logger.info("玩家id[" + getCharId() + "]玩家名[" + getName() + "]登陆成功");
	}

	@Override
	public int getId() {
		return (int) this.getUUID();
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public long getUUID() {
		return roleUUID;
	}

	@Override
	public String getGUID() {
		return "human#" + getUUID();
	}

	public void sendMessage(GCMessage msg) {
		if (msg != null && player != null) {
			player.sendMessage(msg);
		}
	}

	public void sendSystemMessage(Integer key) {
		if (player != null) {
			player.sendSystemMessage(key);
		}
	}

	public void sendErrorMessage(Integer key) {
		if (player != null) {
			player.sendErrorMessage(key);
		}
	}
	
	public void sendImportantMessage(Integer key) {
		if (player != null) {
			player.sendImportantMessage(key);
		}
	}

	public long getPassportId() {
		return player.getPassportId();
	}

	public String getPassportName() {
		return player.getPassportName();
	}

	@Override
	public void heartBeat() {
		try {
			super.heartBeat();
		} catch (Exception e) {
			// 心跳发生异常，发送错误码，把玩家踢下线
			Loggers.errorLogger.error(LogUtils.buildLogInfoStr(this.roleUUID, "玩家心跳时发生异常"), e);
			this.sendMessage(new GCNotifyException(DisconnectReason.HEARTBEAT_EXCEPTION.getIndex(), ""));
			this.getPlayer().exitReason = PlayerExitReason.SERVER_ERROR;
			this.getPlayer().disconnect();
		}
	}

	/**
	 * 给钱
	 * 
	 * @param amount
	 *            改变数量, 大于 0 才有效
	 * @param currencyType
	 *            货币类型
	 * @param logReason
	 *            给钱原因
	 * @param detailReason
	 *            详细原因, 通常为 null, 扩展使用
	 * @return 给钱成功返回 true, 否则返回 false, 失败可能是钱已经超出了最大限额, 参数不合法等
	 * 
	 */
	public boolean giveMoney(long amount, Currency currencyType,
			boolean needNotify, ILogReason logReason, String detailReason,
			int productId, int productCount) {
		// 如果数量小于等于 0,则直接退出!
		if (amount <= 0)
			return false;
         
		// 执行给钱逻辑
		return CurrencyProcessor.getInstance().giveMoney(this, // 玩家角色
				amount, // 给钱数量
				currencyType, // 货币类型
				needNotify, logReason, // 日志原因
				detailReason, // 详细原因
				productId, productCount);
	}
	
	public boolean giveDiamond(long amount,	boolean needNotify, DiamondLogReason logReason, String detailReason,
			int productId, int productCount){
		if (amount <= 0)
			return false;

		// 执行给钱逻辑
		return CurrencyProcessor.getInstance().giveMoney(this, // 玩家角色
				amount, // 给钱数量
				Currency.DIAMOND, // 货币类型
				needNotify, logReason, // 日志原因
				detailReason, // 详细原因
				productId, productCount);
	}
	

	/**
	 * 扣钱
	 * 
	 * @param amount
	 *            扣得数量, 大于 0 才有效
	 * @param mainCurrency
	 *            主货币类型, 不为 null 才有效
	 * @param altCurrency
	 *            替补货币类型, 可以为 null
	 * @param logReason
	 *            扣钱的原因
	 * @param detailReason
	 *            详细原因, 通常为null, 扩展使用
	 * @param reportItemID
	 *            向平台汇报贵重物品消耗时的 itemTemplateID, 非物品的消耗时使用 -1
	 * @return 扣钱成功返回 true, 否则返回false, 失败可能是钱已经超出了最大限额, 参数不合法等
	 * 
	 */
	public boolean costMoney(long amount, Currency mainCurrency, boolean needNotify, ILogReason logReason,
			String detailReason, int productId, int productCount) {
		// 如果数量小于等于 0,则直接退出!
		if (amount <= 0)
			return false;

		// 执行扣钱逻辑
		return CurrencyProcessor.getInstance().costMoney(this, amount,
				mainCurrency, needNotify, logReason, detailReason,
				productId, productCount);
	}

	/**
	 * 检查玩家是否足够指定货币, 如果替补货币为 null 则只检查主货币, 主货币不可以为 null
	 * 
	 * @param amount
	 *            数量, 大于等于 0 才有效, 等于 0 时永远返回 true
	 * @param mainCurrency
	 *            主货币类型
	 * @param altCurrency
	 *            替补货币类型, 为 null 时只检测主货币
	 * @return 如果主货币够 amount 返回 true; 主货币不够看替补货币够不够除现有主货币外剩下的, 够也返回 true;
	 *         加起来都不够返回 false; 参数无效也会返回 false;
	 * 
	 */
	public boolean hasEnoughMoney(int amount, Currency mainCurrency) {
		if (amount < 0) {
			// 数量小于 0, 则直接退出!
			return false;
		} else if (amount == 0) {
			// 数量等于 0, 直接返回 true
			return true;
		}
		// 判定金钱是否足够 ?
		return CurrencyProcessor.getInstance().hasEnoughMoney(this, // 玩家角色
				amount, // 金钱数量
				mainCurrency
				);
	}

	/**
	 * 获取玩家货币
	 * 
	 * @param currencyType
	 *            货币类型
	 * @return
	 * 
	 */
	public long getMoney(Currency currencyType) {
		return CurrencyProcessor.getInstance().getMoney(this, currencyType);
	}

	@Override
	public void checkAfterRoleLoad() {

	}

	@Override
	public void checkBeforeRoleEnter() {

	}

	@Override
	public long getCharId() {
		return roleUUID;
	}

	@Override
	public Long getDbId() {
		return roleUUID;
	}

	@Override
	public boolean isInDb() {
		return isInDb;
	}

	@Override
	public void setDbId(Long id) {
		this.roleUUID = id;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.isInDb = inDb;
	}

	@Override
	public HumanEntity toEntity() {
		HumanEntity entity = new HumanEntity();

		entity.setId(this.getUUID());
		entity.setImg(this.getImg());
		entity.setName(this.getName());
		entity.setGirlFlag(this.getGirl());
		if (this.getCreateTime() == 0) {
			entity.setCreateTime(Globals.getTimeService().now());
		} else {
			entity.setCreateTime(this.getCreateTime());
		}
		entity.setPassportId(this.getPassportId());
		entity.setLevel(this.getLevel());
		entity.setCoupon(this.getCoupon());
		entity.setGold(this.getGold());
		entity.setDiamond(this.getDiamond());
		entity.setCharm(this.getCharm());
		entity.setSceneId(this.getSceneId());
		entity.setLastLoginTime(this.getLastLoginTime());
		entity.setLastLogoutTime(this.getLastLogoutTime());
		entity.setLastLoginIp(this.getLastLoginIP());
		entity.setTotalMinute(this.getTotalMinute());
		entity.setOnlineStatus(this.getOnlineStatus());
		entity.setCurExp(this.getCurExp());
		entity.setIsPay(this.getIsPay());
		entity.setGameview(JSON.toJSONString(this.getGameview()));
		entity.setNewguide(newguide == null?"":newguide);
		entity.setAddfriendIds(JSON.toJSONString(this.getAddfriendIds()));
		entity.setReceivecode(JSON.toJSONString(this.getReceivecode()));
		entity.setWatchNum(watchNum);
		entity.setCountries(countries);
		entity.setAge(age);
		entity.setSlotRotate(slotRotate);
		entity.setSlotSingleWin(slotSingleWin);
		entity.setSlotWin(slotWin);
		entity.setSlotWinNum(slotWinNum);
		entity.setSlotRoomId(slotRoomId);
//		entity.setSlotId(this.getHumanSlotManager().getSlotId());
		entity.setFriendId(friendId);
		entity.setRequestFriendIds(requestFriendIds);
		entity.setSnginfo(JSON.toJSONString(snginfos));
		entity.setNewGuyGift(newGuyGift);
		entity.setCouponExtraChip(couponExtraChip);
		entity.setCouponDurationDate(couponDurationDate);
		entity.setRegularTime(regularTime);
		entity.setTodayView(todayView);
		entity.setClubId(clubId);
		entity.setClubSignTs(clubSignTs);
		entity.setClubDonateTs(clubDonateTs);
		entity.setDoubleExpEndTime(doubleExpEndTime);
		entity.setWatchTime(watchTime);
		entity.setClientVersion(clientVersion);
		entity.setBazooRoom(bazooRoom);
		entity.setBazooGold(bazooGold);
		entity.setBazooAgentDisplay(bazooAgentDisplay);
		entity.setBazooRobotDisplay(bazooRobotDisplay);
		entity.setBazooNewGuyProcess(bazooNewGuyProcess);
		return entity;
	}

	@Override
	public void fromEntity(HumanEntity entity) {
		HumanInitManager.getInstance().loadHuman(this, entity);
		
		//同时 把userInfo 也加载进来
		userInfo = Globals.getDaoService().getUserInfoDao().get(entity.getPassportId());
		
	}

	@Override
	public void onModified() {
		this.setModified();
	}

	@Override
	public void setModified() {
		if (this.lifeCycle == null || !this.lifeCycle.isActive()) {
			return;
		}
		
		player.syncPlayerCacheInfo();
		player.getDataUpdater().addUpdate(lifeCycle);
	
	}

	/**
	 * 记录登录时间
	 * 
	 * @param time
	 * @param ip
	 */
	public void logLogin(long time, String ip) {
		if (time > 0) {
			this.setLastLoginTime(time);
			this.setOnlineStatus(1);
			if (this.getPlayer() != null) {
				this.getPlayer().setLastLoginTime(time);
			}
		}
		this.setLastLoginIP(ip);
	}

	/**
	 * 记录退出时间和在线时长
	 */
	public void logLogout(OnlineTimeLogReason onlineTimeLogReason) {
		long now = Globals.getTimeService().now();
		this.setLastLogoutTime(now);

		this.setOnlineStatus(0);
		long _onlineTime = 0;
		long _loginTime = this.getLastLoginTime();
		long _logoutTime = this.getLastLogoutTime();
		if (_loginTime != 0 && _logoutTime != 0) {
			_onlineTime = _logoutTime - _loginTime;
		}
		this.setTotalMinute(this.getTotalMinute() + (int) (_onlineTime / (1000 * 60)));
		
		long passportId = this.getPassportId();
		ClubMemberData cmd = ClubCache.retrieveMemberIfExist(clubId, passportId);
		Jedis j = null;
		try {
			j = Globals.getRedisService().getJedisPool().getResource();
			if(cmd==null)
			{
				String json = j.hget(RedisKey.CLUB_MEMBER_INFO_HASH__+clubId, String.valueOf(passportId));
				cmd = Globals.gson.fromJson(json, ClubMemberData.class);
			}
			if(cmd!=null)
			{
				cmd.setOfflineTime(now);
				cmd.setIsOnline(0);
				ClubCache.putClubMember(clubId, cmd);
				j.publish(ClubMemberDataChangedProcessor.channel, clubId+"|"+passportId);
			}
		} finally {
			if(j!=null) {j.close();}
		}
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	/** 最后登陆 IP */
	private String _lastLoginIP = null;

	/**
	 * 得到最后一次登录 IP
	 * 
	 * @return
	 * 
	 */
	public String getLastLoginIP() {
		return this._lastLoginIP;
	}

	/**
	 * 设置最后一次登录 IP
	 * 
	 * @param value
	 * 
	 */
	public void setLastLoginIP(String value) {
		this._lastLoginIP = value;
	}

	/** 最后登陆时间 */
	private long _lastLoginTime = 0;

	/**
	 * 得到最后一次登录时间
	 * 
	 * @return
	 * 
	 */
	public long getLastLoginTime() {
		return this._lastLoginTime;
	}

	/**
	 * 设置最后一次登录时间
	 * 
	 * @param value
	 * 
	 */
	public void setLastLoginTime(long value) {
		this._lastLoginTime = value;
	}

	/** 最后登出时间 */
	private long _lastLogoutTime = 0;

	/**
	 * 得到最后一次登出时间
	 * 
	 * @return
	 * 
	 */
	public long getLastLogoutTime() {
		return this._lastLogoutTime;
	}

	/**
	 * 设置最后一次登出时间
	 * 
	 * @param value
	 * 
	 */
	public void setLastLogoutTime(long value) {
		this._lastLogoutTime = value;
	}

	/** 总在线时长 */
	private int _totalMinute = -1;

	/**
	 * 获得总在线时长 (分钟)
	 * 
	 * @return
	 */
	public int getTotalMinute() {
		return this._totalMinute;
	}

	/**
	 * 设置总在线时长
	 * 
	 * @param value
	 * 
	 */
	public void setTotalMinute(int value) {
		this._totalMinute = value;
	}

	// //////////////////////////////////////////人物角色属性逻辑////////////////////////////////////////////////////

	/**
	 * 获取点券
	 * 
	 * @return
	 * 
	 */
	public long getCoupon() {
		return baseIntProperties.getPropertyValue(RoleBaseIntProperties.COUPON);
	}

	/**
	 * 设置点券
	 * 
	 * @param value
	 * 
	 */
	public void setCoupon(long value) {
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.COUPON,
				value);
		this.setModified();
	}

	/**
	 * 获取金币数量
	 * 
	 * @return
	 * 
	 */
	public long getGold() {
		return baseIntProperties.getPropertyValue(RoleBaseIntProperties.GOLD);
	}

	/**
	 * 设置金币数量
	 * 
	 * @param value
	 */
	public void setGold(long value) {
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.GOLD,
				value);
		this.setModified();
	}

	/**
	 * 获取银两
	 * 
	 * @return
	 * 
	 */
	public long getDiamond() {
		return baseIntProperties
				.getPropertyValue(RoleBaseIntProperties.DIAMOND);
	}

	/**
	 * 设置银两
	 * 
	 * @param value
	 * 
	 */
	public void setDiamond(long value) {
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.DIAMOND,
				value);
		this.setModified();
	}

	@Override
	public int getSceneId() {
		return this.sceneId;
	}

	/**
	 * 设置所在场景 ID
	 * 
	 * @param value
	 * 
	 */
	public void setSceneId(int value) {
		this.sceneId = value;
	}

	public long getCreateTime() {
		return this.baseStrProperties.getLong(RoleBaseStrProperties.CREATETIME);
	}

	public void setCreateTime(long time) {
		this.baseStrProperties.setLong(RoleBaseStrProperties.CREATETIME, time);
		this.setModified();
	}

	/**
	 * 设置最后一次到达vip等级的时间
	 * 
	 * @param lastVipTime
	 */
	public void setLastVipTime(long lastVipTime) {
		// this.finalProps.setObject(RoleFinalProps.LAST_VIP_TIME, lastVipTime);
		this.setModified();
	}

	/**
	 * 玩家是否在线的状态
	 * 
	 * @return
	 */
	public int getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	/**
	 * 德州管理器
	 * 
	 * @return
	 */
	public HumanTexasManager getHumanTexasManager() {
		return humanTexasManager;
	}

	/**
	 * 背包
	 * 
	 * @return
	 */
	public HumanBagManager getHumanBagManager() {
		return humanBagManager;
	}

	/**
	 * 德州月卡
	 * 
	 * @return
	 */
	public HumanMonthCardManager getHumanMonthCardManager() {
		return humanMonthCardManager;
	}

	/**
	 * 德州周卡
	 * 
	 * @return
	 */
	public HumanWeekCardManager getHumanWeekCardManager() {
		return humanWeekCardManager;
	}
	
	/**
	 * 邮件
	 * 
	 * @return
	 */
	public HumanMailManager getHumanMailManager() {
		return humanMailManager;
	}

	/**
	 * 好友
	 * 
	 * @return
	 */
	public HumanRelationManager getHumanRelationManager() {
		return humanRelationManager;
	}
	
	/**
	 * 签到
	 * 
	 * @return
	 */
	public HumanWeekSignInManager getHumanWeekSignInManager() {
		return humanWeekSignInManager;
	}
	
	/**
	 * misc
	 * 
	 * @return
	 */
	public HumanMiscManager getHumanMiscManager() {
		return humanMiscManager;
	}
	
	/**
	 * vip
	 * 
	 * @return
	 */
	public HumanVipManager getHumanVipManager() {
		return humanVipManager;
	}
	
	
	/**
	 * Vip新
	 * @return
	 */
	public HumanVipNewManager getHumanVipNewManager() {
		return humanVipNewManager;
	}

	public Date getWatchTime() {
		return watchTime;
	}

	public void setWatchTime(Date watchTime) {
		this.watchTime = watchTime;
	}

	/**
	 * 充值 
	 * @return
	 */
	public HumanRechargeManager getHumanRechargeManager() {
		return humanRechargeManager;
	}
	
	/**
	 * 任务 
	 * @return
	 */
	public HumanTaskManager getHumanTaskManager() {
		return humanTaskManager;
	}
	
	/**
	 * 活动管理器
	 * @return
	 */
	public HumanActivityManager getHumanActivityManager(){
		return humanActivityManager;
	}
	
	/**
	 * 用户补偿管理器
	 */
	public HumanCompensationManager getHumanCompensationManager(){
		return humanCompensationManager;
	}
	
	/**
	 * 用户返还管理器
	 */
	public HumanRefundManager getHumanRefundManager(){
		return humanRefundManager;
	}
	/**
	 * 百家乐
	 * 
	 * @return
	 */
	public HumanBaccartManager getHumanBaccartManager() {
		return humanBaccartManager;
	}
	/**
	 * 幸运转盘
	 * @return
	 */
	public HumanLuckySpinManager getHumanLuckySpinManager(){
		return humanLuckySpinManager;
	}
	
	/**
	 * 老虎机管理器
	 * @return
	 */
	public HumanSlotManager getHumanSlotManager(){
		return humanSlotManager;
	}
	
	/**
	 * 优惠随机礼包管理器
	 * @return
	 */
	public HumanGiftManager getHumanGiftManager(){
		return humanGiftManager;
	}
	public HumanNewComerManager getHumanNewComerManager(){
		return humanNewComerManager;
	}
	/**
	 * 成就管理器
	 * @return
	 */
	public HumanAchievementManager getHumanAchievementManager(){
		return humanAchievementManager;
	}
	

	public HumanGivealikeManager getHumanGivealikeManager() {
		return humanGivealikeManager;
	}

	public void setHumanGivealikeManager(HumanGivealikeManager humanGivealikeManager) {
		this.humanGivealikeManager = humanGivealikeManager;
	}

	/**
	 * 收集系统管理器
	 * @return
	 */
	public HumanCollectManager getHumanCollectManager(){
		return humanCollectManager;
	}
	/**
	 * 存钱罐
	 * @return
	 */
	public HumanTreasuryManager getHumanTreasuryManager() {
		return humanTreasuryManager;
	}

	public void setHumanTreasuryManager(HumanTreasuryManager humanTreasuryManager) {
		this.humanTreasuryManager = humanTreasuryManager;
	}
	/**
	 * 周、月特惠充值活动（不是活动 是功能）
	 * @return
	 */
	public HumanRegularTimeManager getHumanRegularTimeManager() {
		return humanRegularTimeManager;
	}



	public void setHumanRegularTimeManager(
			HumanRegularTimeManager humanRegularTimeManager) {
		this.humanRegularTimeManager = humanRegularTimeManager;
	}

	
	

	public HumanAttackBossManager getHumanAttackBossManager() {
		return humanAttackBossManager;
	}

	public void setHumanAttackBossManager(
			HumanAttackBossManager humanAttackBossManager) {
		this.humanAttackBossManager = humanAttackBossManager;
	}

	public HumanPayguideManager getHumanPayguideManager() {
		return humanPayguideManager;
	}

	public HumanBazooManager getHumanBazooManager() {
		return humanBazooManager;
	}

	public void setHumanBazooManager(HumanBazooManager humanBazooManager) {
		this.humanBazooManager = humanBazooManager;
	}

	public HumanBazooSignInManager getHumanBazooSignInManager() {
		return humanBazooSignInManager;
	}

	public void setHumanBazooSignInManager(
			HumanBazooSignInManager humanBazooSignInManager) {
		this.humanBazooSignInManager = humanBazooSignInManager;
	}

	public HumanBazooRankManager getHumanBazooRankManager() {
		return humanBazooRankManager;
	}

	public void setHumanBazooRankManager(HumanBazooRankManager humanBazooRankManager) {
		this.humanBazooRankManager = humanBazooRankManager;
	}

	public HumanBazooPersonalManager getHumanBazooPersonalManager() {
		return humanBazooPersonalManager;
	}

	public void setHumanBazooPersonalManager(
			HumanBazooPersonalManager humanBazooPersonalManager) {
		this.humanBazooPersonalManager = humanBazooPersonalManager;
	}

	public HumanBazooTaskManager getHumanBazooTaskManager() {
		return humanBazooTaskManager;
	}

	public void setHumanBazooTaskManager(HumanBazooTaskManager humanBazooTaskManager) {
		this.humanBazooTaskManager = humanBazooTaskManager;
	}


	public HumanBazooAchieveManager getHumanBazooAchieveManager() {
		return humanBazooAchieveManager;
	}

	public void setHumanBazooAchieveManager(
			HumanBazooAchieveManager humanBazooAchieveManager) {
		this.humanBazooAchieveManager = humanBazooAchieveManager;
	}

	public HumanBazooNewGuyManager getHumanBazooNewGuyManager() {
		return humanBazooNewGuyManager;
	}

	public void setHumanBazooNewGuyManager(
			HumanBazooNewGuyManager humanBazooNewGuyManager) {
		this.humanBazooNewGuyManager = humanBazooNewGuyManager;
	}

	public HumanBazooWinsManager getHumanBazooWinsManager() {
		return humanBazooWinsManager;
	}

	public void setHumanBazooWinsManager(HumanBazooWinsManager humanBazooWinsManager) {
		this.humanBazooWinsManager = humanBazooWinsManager;
	}

	public void setHumanPayguideManager(HumanPayguideManager humanPayguideManager) {
		this.humanPayguideManager = humanPayguideManager;
	}

	/**
	 * 角色完全登陆初始化完毕之后,做出的一些操作
	 */
	public void afterLogin() {

	}

	public long getAllDiamond() {
		return getDiamond() + getCoupon();
	}

	@Override
	public long getLevel() {
		return baseIntProperties.getPropertyValue(RoleBaseIntProperties.LEVEL);
	}

	/**
	 * 设置等级
	 * 
	 * @param value
	 * 
	 */
	public void setLevel(long value) {
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.LEVEL,
				value);
	}

	@Override
	public long getCurExp() {
		return baseIntProperties
				.getPropertyValue(RoleBaseIntProperties.CUR_EXP);
	}

	/**
	 * 设置当前经验值
	 * 
	 * @param value
	 * 
	 */
	public void setCurExp(long value) {
		if (value < 0) {
			return;
		}
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.CUR_EXP,
				value);
	}

	/**
	 * 魅力值
	 * 
	 * @return
	 */
	public long getCharm() {
		return baseIntProperties.getPropertyValue(RoleBaseIntProperties.CHARM);
	}

	/**
	 * 设置魅力值
	 * 
	 * @param value
	 * 
	 */
	public void setCharm(long value) {
		if (value < 0) {
			return;
		}
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.CHARM,
				value);
	}

	/**
	 * 重置升级经验
	 */
	public void resetMaxExp() {
		this.setMaxExp(Long.valueOf(Globals.getTemplateService().get((int)this.getLevel(), RoleExpTemplate.class).getExp()).longValue());
	}

	/**
	 * 判断玩家等级是否达到上限
	 * 
	 * @param human
	 * @return
	 */
	public boolean isHumanLevelMax() {
		int lvLimit = Globals.getConfigTempl().getLvLimit();
		return lvLimit <= getLevel();
	}

	/**
	 * 获取升级所需经验值
	 * 
	 * @return
	 * 
	 */
	public long getMaxExp() {
		return baseIntProperties
				.getPropertyValue(RoleBaseIntProperties.MAX_EXP);
	}

	public void setMaxExp(long value) {
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.MAX_EXP,
				value);
	}

	@Override
	protected List<KeyValuePair<Integer, Long>> changedNum() {
		// TODO Auto-generated method stub
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Long>> intNumChanged = new ArrayList<KeyValuePair<Integer, Long>>();

	
		// 处理 baseIntProps
		if (this.baseIntProperties.isChanged())
		{
			KeyValuePair<Integer, Long>[] changes = this.baseIntProperties.getChanged();
			for (KeyValuePair<Integer, Long> pair : changes)
			{
				//特殊处理钻石
				if(pair.getKey() == PropertyType.genPropertyKey(RoleBaseIntProperties.COUPON, PropertyType.BASE_ROLE_PROPS_INT)) 
				{
					pair.setKey(PropertyType.genPropertyKey(RoleBaseIntProperties.DIAMOND, PropertyType.BASE_ROLE_PROPS_INT));
				}
				if(pair.getKey() == PropertyType.genPropertyKey(RoleBaseIntProperties.DIAMOND, PropertyType.BASE_ROLE_PROPS_INT)) 
				{
					pair.setValue(getAllDiamond());
				}
				intNumChanged.add(pair);
			}
		}
		return intNumChanged;
		
	}

	/**
	 * 给玩家增加经验
	 * 
	 * @param expAdd
	 * @param reason
	 */
	public void addExp(int expAdd, LevelLogReason reason) {
		if (expAdd == 0)
			return;
		// 等级达到上限，直接返回
		if (isHumanLevelMax())
			return;

		int bonus = Globals.getActivityService().isExpActivity(this);
		
		expAdd = expAdd+(int)Math.floor(expAdd*(bonus/100f));
		
		//双倍经验加成
		Date doubleExpEndTimeDate = this.getDoubleExpEndTime();
		if(doubleExpEndTimeDate != null && doubleExpEndTimeDate.getTime()>Globals.getTimeService().now()){
			expAdd=expAdd*2;
		}
		// 修改经验
		long newExp = getCurExp() + expAdd;
		
		
		setCurExp(newExp);

		// 检查升级
		checkExpAndLevel();

		// 同步属性给客户端
		snapChangedProperty(true);
		logger.info("玩家id[" + getCharId() + "]玩家名[" + getName() + "]角色增加经验成功");
	}

	/**
	 * 检查经验和升级 一般在改变武将经验后立即调用
	 * 
	 * @param human
	 * @param pet
	 */
	public void checkExpAndLevel() {
		//long oldLevel = this.getLevel();
		long oldExp = getCurExp();
		boolean levelUp = false;
		long newExp = oldExp;
		int tmplevel = 0;// 获取等级变化值
		// 循环判断，如果经验超过上限，则升级并扣除经验，重置经验上限
		while (newExp >= getMaxExp()) {
			newExp -= getMaxExp();
			setLevel(getLevel() + 1);//角色升级
			/**
			 * 看看每次的 级数
			 */
//			int levelNew = (int)getLevel();
			
			int levelGold = Globals.getHumanLevelGiftService().getLevelGold((int)getLevel());
			int rewardChips = Globals.getTemplateService().get((int)this.getLevel(), RoleExpTemplate.class).getRewardChips()+levelGold;
			
			/**
			 * 金币 放入缓存
			 */
			if(levelGold > 0){
				this.getHumanBagManager().addGoldCache(Currency.GOLD.getIndex(),rewardChips);
			}
			
			setGold(getGold()+rewardChips);
			//赠送 道具
			LevelGift levelGift = Globals.getHumanLevelGiftService().getLevelGift((int)getLevel());
			if(levelGift != null){
				
				/**
				 * 如果道具是双倍经验 就用第一个
				 * 
				 */
				ItemTemplate itemTemplate = Globals.getItemService().getItemTemplWithId(levelGift.getReward2Id());
				
				if(itemTemplate.getItemType() == ItemType.ItemType13.getIndex()){
					this.getHumanBagManager().addDoubleExp(this,levelGift.getReward2Id(), levelGift.getReward2Num());
				}else{
					this.getHumanCollectManager().exchangeAndBuyBoth(levelGift.getReward2Id(),levelGift.getReward2Num(), false);
				}
				/**
				 * 奖励 金银铜
				 */
//				ItemTemplate itemTemplate2 = Globals.getItemService().getItemTemplWithId(levelGift.getReward3Id());
				this.getHumanCollectManager().exchangeAndBuyBoth(levelGift.getReward3Id(),levelGift.getReward3Num(), false);
				
				
				/**
				 * 道具 放入缓存
				 */
				this.getHumanBagManager().addItemCache(levelGift.getReward2Id(),levelGift.getReward2Num());
				this.getHumanBagManager().addItemCache(levelGift.getReward3Id(),levelGift.getReward3Num());
				
			}
			
			// 同步属性给客户端
		   //snapChangedProperty(true);
			
			levelUp = true;
			resetMaxExp();
			// 等级达到上限时，抛弃剩余经验
			if (isHumanLevelMax())
				newExp = 0;
			tmplevel = tmplevel + 1;
		}
		
		/**
		 * 通知前端
		 */
		this.getHumanBagManager().remind(this);
		setCurExp(newExp);
		// 记录日志
		if (levelUp) {
			checkAfterLevelUp();
			
		}
	}

	/**
	 * 升级后逻辑处理
	 */
	public void checkAfterLevelUp() {
		this.humanAchievementManager.updateLevel();
		//Globals.getActivityService().changLevel(this);
		Globals.getActivityService().changLevelNew(this);
		Globals.getSlotRoomService().change2(player);
		Globals.getClubService().updateHumanInfoToClub(this);
	}
	
	
	
	
	/**
	 * 无双吹牛 获取 个人 临时玩法数据
	 * @return
	 */
	public RoomEveryUserInfo getBazooRoomEveryUserInfo(){
		return this.getHumanBazooManager().getBazooTemporaryData().getRoomEveryUserInfo();
	}
	
	/**
	 * 无双吹牛 获取 个人 临时房间数据
	 * @return
	 */
	public RoomNumber getBazooRoomNumber(){
		return this.getHumanBazooManager().getBazooTemporaryData().getRoomNumber();
	}
	public void setBazooRoomNumber(RoomNumber roomNumber){
		this.getHumanBazooManager().getBazooTemporaryData().setRoomNumber(roomNumber);
	}

	public long getIsPay() {
		return isPay;
	}

	public void setIsPay(long isPay) {
		this.isPay = isPay;
	}

	public Set<String> getGameview() {
		return gameview;
	}

	public void setGameview(Set<String> gameview) {
		this.gameview = gameview;
	}

	public String getNewguide() {
		return newguide;
	}

	public void setNewguide(String newguide) {
		this.newguide = newguide;
	}

	public Map<Long, String> getAddfriendIds() {
		return addfriendIds;
	}

	public void setAddfriendIds(Map<Long, String> addfriendIds) {
		this.addfriendIds = addfriendIds;
	}

	public List<SngInfo> getSnginfos() {
		return snginfos;
	}

	public void setSnginfos(List<SngInfo> snginfos) {
		this.snginfos = snginfos;
	}

	public Set<String> getReceivecode() {
		return receivecode;
	}

	public void setReceivecode(Set<String> receivecode) {
		this.receivecode = receivecode;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSlotRoomId() {
		return slotRoomId;
	}

	public void setSlotRoomId(String slotRoomId) {
		this.slotRoomId = slotRoomId;
		this.setModified();
	}

	public int getGiftId() {
		return giftId;
	}

	public void setGiftId(int giftId) {
		if(this.giftId == 0 || giftId == 0){
			this.giftId = giftId;
		}else{
			InteractiveItemTemplate newTem1 = Globals.getItemService().getInteractiveItem(giftId);
			InteractiveItemTemplate oldTem1 = Globals.getItemService().getInteractiveItem(this.giftId);
			if(newTem1.getQuality() >= oldTem1.getQuality()){
				this.giftId = giftId;
			}
		}
		
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

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getIpCountries() {
		return ipCountries;
	}

	public void setIpCountries(String ipCountries) {
		this.ipCountries = ipCountries;
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
		if(clubId==null)
		{
			clubId = "";
		}
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

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	
}
