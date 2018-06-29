package com.gameserver.common;


import java.util.TimeZone;

import com.common.service.DirtFilterService;
import com.core.async.AsyncService;
import com.core.config.ConfigUtil;
import com.core.config.ServerConfig;
import com.core.heartbeat.LLScheduleRunner;
import com.core.heartbeat.LLTimeRunner;
import com.core.heartbeat.MilliHeartbeatTimer;
import com.core.i18n.DirtyWordService;
import com.core.i18n.impl.DirtyWordServiceImpl;
import com.core.orm.DBService;
import com.core.schedule.LLScheduleService;
import com.core.schedule.LLScheduleServiceImpl;
import com.core.server.MessageDispatcher;
import com.core.server.ShutdownHooker;
import com.core.session.OnlineSessionService;
import com.core.template.TemplateService;
import com.core.time.SystemTimeService;
import com.core.time.TimeService;
import com.core.util.TimeUtils;
import com.core.uuid.MyUUIDService;
import com.core.uuid.MyUUIDServiceImpl;
import com.core.uuid.UUIDType;
import com.game.webserver.Config.LocalConfig;
import com.game.webserver.login.ILoginServerHandler;
import com.game.webserver.login.LoginServerHandler;
import com.gameserver.GameServer;
import com.gameserver.achievement.HumanAchievementServer;
import com.gameserver.activity.ActivityService;
import com.gameserver.activity.CommonActivityService;
import com.gameserver.activity.WeeklyMonthlyPackService;
import com.gameserver.baccart.BaccartService;
import com.gameserver.bazoo.service.BazooService;
import com.gameserver.bazoo.service.blackWhite.BlackWhiteService;
import com.gameserver.bazoo.service.classical.ClassicalService;
import com.gameserver.bazoo.service.cow.CowService;
import com.gameserver.bazoo.service.room.BazooPriService;
import com.gameserver.bazoo.service.room.BazooPubService;
import com.gameserver.bazoo.service.showHand.ShowHandService;
import com.gameserver.bazooachieve.HumanBazooAchieveService;
import com.gameserver.bazoogift.BazooGiftService;
import com.gameserver.bazoopersonal.HumanBazooPersonalService;
import com.gameserver.bazoorank.HumanBankService;
import com.gameserver.bazoorpc.BazooRpcService;
import com.gameserver.bazootask.HumanBazooTaskService;
import com.gameserver.chat.ChatService;
import com.gameserver.chat.WordFilterService;
import com.gameserver.chat.impl.WordFilterServiceImpl;
import com.gameserver.club.ClubService;
import com.gameserver.club.ClubTemplateService;
import com.gameserver.collect.CardRewardServer;
import com.gameserver.collect.CollectServer;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.common.db.AsyncServiceImpl;
import com.gameserver.common.db.GameDaoService;
import com.gameserver.common.heartbeat.HeartbeatThread;
import com.gameserver.common.i18n.LangService;
import com.gameserver.common.i18n.LangServiceImpl;
import com.gameserver.common.log.LogService;
import com.gameserver.common.log.LoggableSwitcher;
import com.gameserver.common.template.ConfigTemplate;
import com.gameserver.common.thread.CrossThreadOperationService;
import com.gameserver.compensation.CompensationService;
import com.gameserver.conversioncode.ConversioncodeService;
import com.gameserver.function.FunctionService;
import com.gameserver.gift.GiftService;
import com.gameserver.givealike.service.HumanGivealikeService;
import com.gameserver.http.HttpServer;
import com.gameserver.human.CouponService;
import com.gameserver.human.HumanLevelGiftService;
import com.gameserver.human.HumanService;
import com.gameserver.human.VideoRewardServer;
import com.gameserver.ip.geoip.IPSearchService;
import com.gameserver.item.ItemNewService;
import com.gameserver.item.ItemService;
import com.gameserver.lobby.JackpotServer;
import com.gameserver.luckyspin.LuckySpinService;
import com.gameserver.misc.MiscService;
import com.gameserver.newbie.NewbieService;
import com.gameserver.notice.NoticeService;
import com.gameserver.player.LoginLogicalProcessor;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.PlayerCacheService;
import com.gameserver.player.auth.LocalUserAuthImpl;
import com.gameserver.player.auth.UserAuth;
import com.gameserver.rank.RankService;
import com.gameserver.ranknew.RankNewServer;
import com.gameserver.recharge.ChannelDiffService;
import com.gameserver.recharge.MultipleWheelServer;
import com.gameserver.recharge.RechargeService;
import com.gameserver.redis.RedisService;
import com.gameserver.robot.RobotService;
import com.gameserver.rpc.RpcService;
import com.gameserver.scene.SceneService;
import com.gameserver.shop.ShopService;
import com.gameserver.signin.SignInService;
import com.gameserver.slot.BonusAztecService;
import com.gameserver.slot.BonusBrazilService;
import com.gameserver.slot.BonusHallowmasService;
import com.gameserver.slot.BonusHolmesService;
import com.gameserver.slot.BonusNinjaService;
import com.gameserver.slot.BonusOceanService;
import com.gameserver.slot.BonusStoneAgeService;
import com.gameserver.slot.BonusWolfService;
import com.gameserver.slot.BounsElephontTemplateService;
import com.gameserver.slot.BounsPirateService;
import com.gameserver.slot.BounsTigerService;
import com.gameserver.slot.BounsZeusService;
import com.gameserver.slot.JackpotDragonService;
import com.gameserver.slot.MagicSymbolService;
import com.gameserver.slot.RedgirlService;
import com.gameserver.slot.RhinoService;
import com.gameserver.slot.RiyuetanMultipleService;
import com.gameserver.slot.ScatterCowboyService;
import com.gameserver.slot.ScatterCrystalService;
import com.gameserver.slot.ScatterMultipleService;
import com.gameserver.slot.SlotRoomService;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.SlotWheelService;
import com.gameserver.slot.SpartaService;
import com.gameserver.slot.TournamentService;
import com.gameserver.slot.WildSphinxService;
import com.gameserver.slot.WinnerWheelService;
import com.gameserver.startup.GameExecutorService;
import com.gameserver.startup.GameMessageProcessor;
import com.gameserver.startup.MinaGameClientSession;
import com.gameserver.status.ServerStatusService;
import com.gameserver.task.TaskService;
import com.gameserver.texas.TexasService;
import com.gameserver.treasury.HumanTreasuryService;
import com.gameserver.vip.VipService;
import com.gameserver.vipnew.VipNewServer;
import com.gameserver.weixin.WeixinService;
import com.gameserver.worldboss.service.WorldBossNewService;
import com.google.gson.Gson;

/**
 * 各种全局的业务管理器、公共服务实例的持有者，负责各种管理器的初始化和实例的获取
 * 
 * @author Thinker
 */
public final class Globals {

	/**
	 * 常量设置
	 */
	
	private static String TEMPLE_FILE_PATH = "templates.xml";
	
	/** 服务器配置信息 */
	private static GameServerConfig config;
	/** 场景心跳 */
	private static HeartbeatThread heartbeatThread;
	/** 主消息处理器，运行在主线程中，处理玩家登陆退出以及服务器内部消息 */
	private static MessageDispatcher<GameMessageProcessor> messageProcessor;
	/** 进程停止Hooker */
	private static ShutdownHooker shutdownHooker;
	/**日志开关*/
	private static LoggableSwitcher loggableSwitcher;
	/**日志服务*/
	private static LogService logService;
	/** 全局业务 */
	private static GlobalLogicRunner globalLogicRunner;
	/** 登陆逻辑处理器 */
	private static LoginLogicalProcessor loginLogicalProcessor;

	/** 负责系统中时间操作的服务类 */
	private static TimeService timeService;
	/** 数据访问对象管理器 */
	private static GameDaoService daoService;
	/**redis service*/
	private static RedisService redisService;
	/**rpc service*/
	private static RpcService rpcService;
	/** 多语言管理器 */
	private static LangService langService;
	/** 模板数据管理器 */
	private static TemplateService templateService;
	/** 会话管理器 */
	private static OnlineSessionService<MinaGameClientSession> sessionService;
	/** 在线玩家管理器 */
	private static OnlinePlayerService onlinePlayerService;
	/** 系统线程池服务 */
	private static GameExecutorService executorService;
	/** 定时任务管理器 */
	private static LLScheduleService scheduleService;
	/** 异步操作管理器 */
	private static AsyncService asyncService;
	/** 跨线程操作服务 */
	private static CrossThreadOperationService crossThreadOperationService;
	/** UUID服务 */
	private static MyUUIDService uuidService;
	/** 服务器状态汇报服务 */
	private static ServerStatusService serverStatusService;
	/**排行版服务*/
	private static RankService rankService;
	/** 场景管理器 */
	private static SceneService sceneService;

	/** H2Cache的服务 */
	private static HumanService humanService;
	/**德州服务*/
	private static TexasService texasService;

	/**商店服务*/
	private static ShopService shopService;
	/**物品服务*/
	private static ItemService itemService;
	/**物品服务*/
	private static ItemNewService itemNewService;
	/***签到*/
	private static SignInService signInService;
	/**跑马灯*/
	private static NoticeService noticeService;
	/**活动*/
	private static ActivityService activityService;
	/**misc*/
	private static MiscService miscService;
	/**vip*/
	private static VipService vipService;
	/** 聊天不良信息过滤服务 */
	private static WordFilterService wordFilterService;
	/** 脏话过滤器 */
	private static DirtFilterService dirtFilterService;
	/**玩家缓存服务*/
	private static PlayerCacheService playerCacheService;
	/**充值服务*/
	private static RechargeService  rechargeService;
	/**聊天服务*/
	private static ChatService chatService;
	
	
	private static TaskService taskNewService;
	
	/**补偿列表*/
	private static CompensationService compensationService;
     /**机器人*/
	private static RobotService robotService;
	/**百家乐服务*/
	private static BaccartService baccartService;
	
	private  static LuckySpinService luckySpinService;
	
	/**老虎机服务*/
	private static SlotService slotService;
	/**老虎机服务*/
	private static SlotWheelService slotWheelService;
	
	/**日月潭老虎机数据*/
	private static RiyuetanMultipleService riyuetanMultipleService;
	/**维密老虎机数据 **/
	private static ScatterMultipleService scatterMultipleService;
	/**宙斯老虎机数据 **/
	private static BounsZeusService bounsZeusService;
	/**石器时代老虎机数据 **/
	private static MagicSymbolService magicSymbolService;

	/** 狮身人面 wild 数据**/
	private static WildSphinxService wildSphinxService;
	
	/** 阿兹特克文明老虎机 **/
	private static BonusAztecService bonusAztecService;
	
	/** 狼老虎机 **/
	private static BonusWolfService bonusWolfService;
	
	/** 水晶魔法宝石老虎机 **/
	private static ScatterCrystalService scatterCrystalService;
	
	/**泰国老虎机 **/
	private static BounsElephontTemplateService bounsElephontTemplateService;
	
	/**老虎老虎机 **/
	private static BounsTigerService bounsTigerService;
	
	/**西部牛仔老虎机 **/
	private static ScatterCowboyService scatterCowboyService;
	
	/**东方龙老虎机 **/
	private static JackpotDragonService jackpotDragonService;
	
	/**忍者老虎机 **/
	private static BonusNinjaService bonusNinjaService;
	
	/**犀牛老虎机 **/
	private static RhinoService rhinoService;
	
	
	/**海底世界老虎机 **/
	private static BonusOceanService bonusOceanService;
	
	/**福尔摩斯老虎机 **/
	private static BonusHolmesService bonusHolmesService;
	/** 俱乐部服务  **/
	private static ClubService clubService;
	
	
	private static ClubTemplateService clubTemplateService;
	
	/** 新手  **/
	private static NewbieService newbieService;
//	private static ClubInvateService clubInvateService;
//	
//	private static ClubRankingService clubRankingService;
//	
//	private static ClubApplyService clubApplyService;
//	
//	private static ClubNoteService clubNoteService;
	
	/**根据IP查询国家 **/
	private static IPSearchService iPSearchService;
	
	
	/** 调用微信发送接口 **/
	private static WeixinService weixinService;
	
	/**服务器ID服务 **/
	private static ServerComm serverComm;
	
	/**老虎机房间管理器 **/
	private static SlotRoomService slotRoomService;

	/** VIP 新系统  **/
	private static VipNewServer vipNewServer;
	
	/**彩金数据 **/
	private static JackpotServer jackpotServer;
	
	/**新排行榜管理器      **/
	private static RankNewServer rankNewServer;
	
	/** 礼包 **/
	private static GiftService giftService;
	
	/** 成就管理器 **/
	private static HumanAchievementServer humanAchievementServer;
	
	/**领取筹码 **/
	private static ConversioncodeService conversioncodeService;
	
	private static HumanLevelGiftService humanLevelGiftService;
	
	/** jetty **/
	private static HttpServer httpServer;
	
	/**观看视频奖励 **/
	private static VideoRewardServer videoRewardServer;
	

	private static MultipleWheelServer multipleWheelServer;
	
    private static CardRewardServer cardRewardServer;
    
    private static CollectServer collectServer;
    
    private static TournamentService tournamentService;
	
	private static LocalConfig localConfig;
	// /////////////////////要求上面的单例和下面的逻辑依次对应:必须遵守规范////////////////////

	//登录服http同步请求
	private static ILoginServerHandler synLoginServerHandler; 
	
	//初始化存钱罐
	private static HumanTreasuryService humanTreasuryService; 
	
	//海盗老虎机
	private static BounsPirateService bounsPirateService; 
	
	//优惠券
	private static CouponService couponService; 
	
	//优惠券
	private static SpartaService spartaService; 
	
	//优惠券
	private static CommonActivityService commonActivityService; 
	
	//周、月特惠充值活动
	private static WeeklyMonthlyPackService weeklyMonthlyPackService; 
	
	//巴西风情老虎机 新的 小游戏 的service
	private static BonusBrazilService bonusBrazilService; 
	//小红帽的小游戏
	private static RedgirlService redgirlService; 
	
	//世界boss
	private static WorldBossNewService worldBossNewService; 
	//评论
	private static HumanGivealikeService humanGivealikeService; 
	//评论
	private static BonusStoneAgeService bonusStoneAgeService; 
	//评论
	private static BonusHallowmasService bonusHallowmasService; 
	//评论
	private static WinnerWheelService winnerWheelService; 
	private static FunctionService functionService; 
	
	
	/*** 无双吹牛  主流程***/
	private static BazooPubService bazooPubService; 
	private static BazooPriService bazooPriService; 
	private static BazooService bazooService; 
	private static ClassicalService bazooMService; 
	private static CowService bazooCowService; 
	private static ShowHandService bazooShowHandService; 
	
	/*** 无双吹牛  外围***/
	private static HumanBankService humanBankService; 
	private static HumanBazooPersonalService humanBazooPersonalService; 
	private static HumanBazooTaskService humanBazooTaskService; 
	private static HumanBazooAchieveService humanBazooAchieveService; 
	
	/*** 无双吹牛  远程调用RPC***/
	private static BazooRpcService bazooRpcService; 
	private static BazooGiftService bazooGiftService; 
	/*** 无双吹牛  红黑单双***/
	private static BlackWhiteService blackWhiteService; 
	/*** 无双吹牛  区分渠道的***/
	private static ChannelDiffService channelDiffService; 
	
	//utc offset
	private static long utcOffset = 0l;
	
	public static Gson gson = new Gson();
	/**
	 * 服务器启动时调用，初始化所有管理器实例
	 * 
	 * @param cfg
	 * @throws Exception
	 * @see GameServer
	 */
	public static void init(GameServerConfig cfg) throws Exception {
		config = cfg;
		
		//初始化时区
		initTimeZone(cfg.getTimeZone());
		initProbeCollector(config);//目前空实现

		// 游戏心跳线程
		heartbeatThread = new HeartbeatThread();
		heartbeatThread.setName("HeartbeatThread");
		
        //重要 消息处理器
		messageProcessor = buildMessageProcessor();

		timeService = new SystemTimeService(true);
		shutdownHooker = new ShutdownHooker();

		// 初始化数据库服务并检查实体
		daoService = new GameDaoService(cfg);
		daoService.checkAllEntities();
		
		//log
		loggableSwitcher = new LoggableSwitcher();
		logService = new LogService(cfg.getLogHost(),cfg.getLogPort(),Integer.parseInt(cfg.getRegionId()),Integer.parseInt(cfg.getServerId()));
		
		//初始化redis
		redisService = new RedisService();
	
		
		//初始化rpc
		rpcService = new RpcService();
		

		// 初始化各种管理器实例
		MilliHeartbeatTimer.setTimeService(timeService);
		
		langService = LangServiceImpl.buildLangService(cfg);

		// 策划数据模板类
		templateService = new TemplateService(cfg.getScriptDirFullPath(),cfg.isTemplateXorLoad());
		
		templateService.init(ConfigUtil.getConfigURL(TEMPLE_FILE_PATH));

		// 会话服务
		sessionService = new OnlineSessionService<MinaGameClientSession>();

		// 在线玩家服务
		onlinePlayerService = new OnlinePlayerService();
		onlinePlayerService.init();

		// 定时服务
		scheduleService = new LLScheduleServiceImpl(timeService);


		/** 全局业务逻辑 */
		globalLogicRunner = new GlobalLogicRunner();
		globalLogicRunner.init();

		// 人物服务
		 humanService = new HumanService();
		 humanService.init();

		// 服务器状态
		serverStatusService = new ServerStatusService();

		// 初始化各种基础服务
		executorService = new GameExecutorService();

		// 异步服务
		asyncService = new AsyncServiceImpl(10, 5, 50, messageProcessor);
		
		// 跨线程操作服务 这个已经没有用了
		crossThreadOperationService = new CrossThreadOperationService(asyncService, onlinePlayerService);

		// UUID服务
		uuidService = buildUUIDService(daoService.getDBService(), cfg);
		

		// 初始化登录逻辑处理器
		UserAuth userAuth = buildUserAuth();
		loginLogicalProcessor = new LoginLogicalProcessor(userAuth);
		
		//排行版服务
		rankService = new RankService();
		//德州服务
		texasService = new TexasService();
	
	
		//场景服务
		sceneService = new SceneService(templateService, onlinePlayerService);
		
		//商店服务
		shopService = new ShopService(templateService);
		
		//物品服务
		itemService = new ItemService(templateService);
		
		//物品服务  新
		itemNewService = new ItemNewService();
		
		//玩家缓存服务
		playerCacheService = new PlayerCacheService();
		
		//签到服务
		signInService = new SignInService();
		
		//公告服务
		noticeService = new NoticeService();
		//活动服务
		activityService = new ActivityService();
		//misc
		miscService= new MiscService();
		
		//vip
		vipService = new VipService();
		
		//充值
		rechargeService = new RechargeService();
		
		wordFilterService = new WordFilterServiceImpl();
		
		rechargeService = new RechargeService();
		
		chatService = new ChatService();

		taskNewService = new TaskService();
		
		compensationService= new CompensationService();
		robotService = new RobotService();
		baccartService = new BaccartService();
		luckySpinService = new LuckySpinService();
		slotService = new SlotService();
		vipNewServer = new VipNewServer();
		
		jackpotServer = new JackpotServer();
		
		rankNewServer = new RankNewServer();
		
		giftService = new GiftService();
		
		humanAchievementServer = new HumanAchievementServer();
		
		conversioncodeService = new ConversioncodeService();
		
		slotWheelService = new SlotWheelService();
		
		riyuetanMultipleService = new RiyuetanMultipleService();
		
		scatterMultipleService = new ScatterMultipleService();

		magicSymbolService = new MagicSymbolService();
		
		bounsZeusService = new BounsZeusService();
		
		httpServer = new HttpServer();
		
		videoRewardServer = new VideoRewardServer();

		wildSphinxService = new  WildSphinxService();
		
		bonusAztecService = new BonusAztecService();
		
		bonusWolfService = new BonusWolfService();
		
		scatterCrystalService = new ScatterCrystalService();
		
		bounsElephontTemplateService = new BounsElephontTemplateService();
		
		bounsTigerService = new BounsTigerService();
		
		scatterCowboyService = new ScatterCowboyService();
		
		jackpotDragonService = new JackpotDragonService();
		
		bonusNinjaService = new BonusNinjaService();
		
		rhinoService = new RhinoService();
		
		bonusOceanService = new BonusOceanService();
		
		bonusHolmesService = new BonusHolmesService();
		
		iPSearchService = new IPSearchService();
		
		weixinService = new WeixinService();
		
		serverComm = new ServerComm();
		
		slotRoomService = new SlotRoomService();
		
		humanLevelGiftService = new HumanLevelGiftService();
		
		multipleWheelServer = new MultipleWheelServer();
		
		cardRewardServer = new CardRewardServer();
		
		collectServer = new CollectServer();
		
		tournamentService = new TournamentService();
		
		humanTreasuryService = new HumanTreasuryService();
		
		bounsPirateService = new BounsPirateService();
		
		couponService = new CouponService();
		
		spartaService = new SpartaService();
		
		commonActivityService = new CommonActivityService();

		weeklyMonthlyPackService = new WeeklyMonthlyPackService();
		
		bonusBrazilService = new BonusBrazilService();
		
		redgirlService = new RedgirlService();
		
		worldBossNewService = new WorldBossNewService();
		humanGivealikeService = new HumanGivealikeService();
		bonusStoneAgeService = new BonusStoneAgeService();
		bonusHallowmasService = new BonusHallowmasService();
		winnerWheelService = new WinnerWheelService();
		functionService = new FunctionService();
		
		bazooService = new BazooService();
		bazooPubService = new BazooPubService();
		
		bazooPriService = new BazooPriService(bazooPubService);
		//初始化 吹牛的 service
		bazooMService = new ClassicalService(bazooPubService);
		bazooCowService = new CowService(bazooPubService);
		bazooShowHandService = new ShowHandService(bazooPubService);

		humanBankService = new HumanBankService();
		humanBazooPersonalService = new HumanBazooPersonalService();
		humanBazooTaskService = new HumanBazooTaskService();
		humanBazooAchieveService = new HumanBazooAchieveService();
		
		clubService = new ClubService();
		
		clubTemplateService = new ClubTemplateService();
		
		newbieService = new NewbieService(templateService);
		
		bazooRpcService = new BazooRpcService();
		
		bazooGiftService = new BazooGiftService(itemNewService);

		blackWhiteService = new BlackWhiteService();
		
		channelDiffService = new ChannelDiffService();

		//初始化屏蔽字库
		{
			String langBasePath = cfg.getResourceFullPath(cfg.getI18nDir(),cfg.getLanguage());
			// 设置屏蔽字
			String xlsFilename = langBasePath + "/dirtywords.xls";
			// 创建屏蔽字服务
			DirtyWordService dirtyWordServ = new DirtyWordServiceImpl(xlsFilename);
			dirtyWordServ.init();
	
			String[] dirtyWords = dirtyWordServ.getDirtyWordsArray();
			String[] dirtyNames = dirtyWordServ.getDirtyNamesArray();
	
			// 合并 dirtyWords 到 dirtyNames
			dirtyNames = mergeArray(dirtyWords, dirtyNames);
	
			dirtFilterService = new DirtFilterService(langService.getSysLangSerivce(), dirtyWords, dirtyNames);
		}
		
		initService();
		afterInitService();
		//加载jar包信息
		localConfig = new LocalConfig();
		synLoginServerHandler=new LoginServerHandler(localConfig);
		
		
		
	}
	
//	public static ClubNoteService getClubNoteService() {
//		return clubNoteService;
//	}

	/**
	 * 合并两个数组
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	private static String[] mergeArray(String[] arr1, String[] arr2)
	{
		int len = arr1.length + arr2.length;
		String[] arr = new String[len];
		System.arraycopy(arr1, 0, arr, 0, arr1.length);
		System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);
		return arr;
	}
	
	/**
	 * init service
	 */
	private static void initService()
	{
		redisService.init();
		serverComm.init();
	//	rpcService.init();
		serverStatusService.init();
		rankService.init();
		texasService.init();
		sceneService.init();
		itemService.init();
		itemNewService.init();
		shopService.init();
		playerCacheService.init();
		signInService.init();
		noticeService.init();
		activityService.init();
		miscService.init();
		vipService.init();
		chatService.init();
		
		compensationService.init();
        robotService.init();
		baccartService.init();
		luckySpinService.init();
		slotService.init();
		vipNewServer.init();
		jackpotServer.init();
		rankNewServer.init();
		giftService.init();
		humanAchievementServer.init();
		conversioncodeService.init();
		slotWheelService.init();
		httpServer.init();
		videoRewardServer.init();
		riyuetanMultipleService.init();
		scatterMultipleService.init();
		bounsZeusService.init();
		magicSymbolService.init();
		wildSphinxService.init();
		bonusAztecService.init();
		bonusWolfService.init();
		scatterCrystalService.init();
		bounsElephontTemplateService.init();
		bounsTigerService.init();
		scatterCowboyService.init();
		bonusNinjaService.init();
		rhinoService.init();
		bonusOceanService.init();
		bonusHolmesService.init();
		iPSearchService.init();
		weixinService.init();
		slotRoomService.init();
		humanLevelGiftService.init();
		taskNewService.init();
		multipleWheelServer.init();
		cardRewardServer.init();
		collectServer.init();
		tournamentService.init();
		humanTreasuryService.init();
		bounsPirateService.init();
		couponService.init();
		spartaService.init();
		commonActivityService.init();

		weeklyMonthlyPackService.init();
		
		bonusBrazilService.init();
		redgirlService.init();
		worldBossNewService.init();
		humanGivealikeService.init();
		bonusStoneAgeService.init();
		bonusHallowmasService.init();
		winnerWheelService.init();
		functionService.init();
		bazooPubService.init();
		bazooPriService.init();
		bazooService.init();
		bazooMService.init();
		bazooCowService.init();
		bazooShowHandService.init();
		humanBankService.init();
		humanBazooPersonalService.init();
		humanBazooTaskService.init();
		humanBazooAchieveService.init();
		bazooRpcService.init();
		bazooGiftService.init();
		blackWhiteService.init();
		clubService.init();
		clubTemplateService.init();
		newbieService.init();
		channelDiffService.init();

	}
	
//	public static ClubRankingService getClubRankingService() {
//		return clubRankingService;
//	}

	/**
	 * after init
	 */
	private static void afterInitService(){
		
		redisService.afterInit();
		rpcService.afterInit();
		serverStatusService.afterInit();
		rankService.afterInit();
		texasService.afterInit();
		sceneService.afterInit();
		itemService.afterInit();
		shopService.afterInit();
		playerCacheService.afterInit();
		signInService.afterInit();
		noticeService.afterInit();
		activityService.afterInit();
		miscService.afterInit();
		vipService.afterInit();
		chatService.afterInit();
		
		compensationService.afterInit();
		rechargeService.afterInit();
		robotService.afterInit();
		baccartService.afterInit();
		luckySpinService.afterInit();
		slotService.afterInit();
		rankNewServer.afterInit();
		humanAchievementServer.afterInit();
		slotWheelService.afterInit();
		riyuetanMultipleService.afterInit();
		jackpotDragonService.afterInit();
		clubService.afterInit();
		newbieService.afterInit();
	}

//	public static ClubApplyService getClubApplyService() {
//		return clubApplyService;
//	}

	// ////////////////////////////////////初始化逻辑函数开始////////////////////////////////////////////
	/**
	 * 初始化时区
	 * 
	 * @param timeZoneStr
	 */
	private static void initTimeZone(String timeZoneStr) {
		if (timeZoneStr != null && !timeZoneStr.equals("")) {
			TimeZone.setDefault(TimeZone.getTimeZone(timeZoneStr));
			
		}
		
		TimeUtils.TIME_ZONE = TimeZone.getDefault();
		utcOffset = TimeUtils.TIME_ZONE.getRawOffset()*1000;
	}

	/**
	 * 初始化指标采集
	 * 
	 * @param gameServerConfig
	 */
	private static void initProbeCollector(ServerConfig gameServerConfig) {

	}

	/**
	 * 构建消息处理器,分拆不同类型的消息到不同的消息处理器
	 * 
	 * @return
	 */
	private static MessageDispatcher<GameMessageProcessor> buildMessageProcessor() {
		// 主消息处理器，处理登录、聊天等没有IO操作的请求
		GameMessageProcessor mainMessageProcessor = new GameMessageProcessor();
		
		// 消息分发器，将收到的消息转发到不同的消息处理器
		MessageDispatcher<GameMessageProcessor> msgDispatcher = new MessageDispatcher<GameMessageProcessor>(mainMessageProcessor);
		
		return msgDispatcher;
	}



	/**
	 * 构建UUID管理器
	 * 
	 * @param serverConfig
	 * @return
	 */
	private static MyUUIDService buildUUIDService(DBService dbServices,
			ServerConfig serverConfig) {
		UUIDType[] _types = new UUIDType[] { UUIDType.HUMAN,
				UUIDType.EXITREASONID,UUIDType.HUMANTEXASID,
				UUIDType.HUMANBAGID,UUIDType.HUMANMONTHCARDID,
				UUIDType.HUMANWEEKCARDID,UUIDType.HUMANMAILID,
				UUIDType.HUMANFRIENDID,UUIDType.HUMANFRIENDREQUESTID,
				UUIDType.HUMANWEEKSIGNINID,UUIDType.HUMANVIPID,
				UUIDType.HUMANRECHARGEID,UUIDType.HUMANTASKID,
				UUIDType.HUMANACTIVITYID,UUIDType.HUMANTEXASROOMID,
				UUIDType.HUMANTEXASROOMGAMEID,UUIDType.HUMANITEMID,
				UUIDType.HUMANTEXASSNGID,UUIDType.HUMANCOMPENSATIONID,
				UUIDType.HUMANMISCID,UUIDType.HUMANFRIENDGIFTID,
				UUIDType.ROBOTCOMPLEMENTID,
				UUIDType.HUMANBACCARTID,UUIDType.BACCARTROOMMODELID ,
				UUIDType.LUCKYSPINID,
				UUIDType.LUCKYMATCHID,UUIDType.SLOT,UUIDType.HUMANSLOT,
				UUIDType.RANKNEW,UUIDType.HUMANGIFT,UUIDType.TEXASROOT,
				UUIDType.ACHIEVEMENT,
				UUIDType.CONVERSIONCODE,
				UUIDType.COLLECT,
				UUIDType.CLUB,
				UUIDType.CLUB_MEMBER,
				UUIDType.HUMANTREASURYID,
				UUIDType.WORLDBOSSID,
				UUIDType.HUMANATTACKBOSSID,
				UUIDType.HUMANGIVEALIKE,
				UUIDType.HUMANMONTHWEEK,
				UUIDType.HUMANPAYGUIDE,
				UUIDType.HUMANNEWCOMER,
				UUIDType.FUNCTIONUUID,
				UUIDType.HUMANBAZOOSIGNIN,
				UUIDType.HUMANBAZOORANK,
				UUIDType.HUMANBAZOOPERSONAL,
				UUIDType.HUMANBAZOOTASK,
				UUIDType.HUMANBAZOOWINS,
				UUIDType.BAZOOROOMCREATE,
				UUIDType.BAZOONEWGUY,
				};

		return new MyUUIDServiceImpl(_types,Integer.parseInt(serverConfig.getServerId()),Globals.getTimeService().now());
	}




	/**
	 * 构建认证接口
	 * 
	 * @return
	 */
	private static UserAuth buildUserAuth() {
		return new LocalUserAuthImpl(langService.getSysLangSerivce());
	}



	/**
	 * 设置多语言服务, <font color='#990000'><b>只在 GM 命令中调用!!</b></font>
	 * 
	 * @param value
	 * @see ReloadLangXlsCmd#execute
	 * 
	 */
	public static void setLangService(LangService value) {
		langService = value;
	}

	

	/**
	 * 启动游戏相关服务
	 * 
	 * @see GameServer#start()
	 * 
	 */
	public static void start() {
		// 启动场景线程
		sceneService.start();
		//启动redis线程
		redisService.start();
		//启动rpc线程
	//	rpcService.start();
		// 注册心跳运行器
		heartbeatThread.AddHeartbeatRunner(new LLTimeRunner(timeService));
		heartbeatThread.AddHeartbeatRunner(new LLScheduleRunner(scheduleService));
		heartbeatThread.start();
		
		
	}
	
	/**
	 * 关闭
	 * @return
	 */
	public static void stop(){
		heartbeatThread.shutdown();
	}

	// /////////////////////获取各种服务单例开始////////////////////


	//获取全局配置模板
	public static ConfigTemplate getConfigTempl() {
		return templateService.get(1, ConfigTemplate.class);
	}

	
	/**
	 * 服务器配置信息
	 */
	public static GameServerConfig getServerConfig() {
		return config;
	}

	public static LLScheduleService getScheduleService() {
		return scheduleService;
	}

	/**
	 * 消息处理器
	 */
	public static GameMessageProcessor getMessageProcessor() {
		return messageProcessor.getMainProcessor();
	}

	/**
	 * 获得时间服务
	 */
	public static TimeService getTimeService() {
		return timeService;
	}

	/**
	 * 进程停止Hooker
	 */
	public static ShutdownHooker getShutdownHooker() {
		return shutdownHooker;
	}

	/**
	 * 数据访问对象管理器
	 */
	public static GameDaoService getDaoService() {
		return daoService;
	}

	/**
	 * 多语言管理器
	 */
	public static LangService getLangService() {
		return langService;
	}

	/**
	 * 获得模板数据管理器
	 */
	public static TemplateService getTemplateService() {
		return templateService;
	}

	/**
	 * 获得会话服务器实例
	 */
	public static OnlineSessionService<MinaGameClientSession> getSessionService() {
		return sessionService;
	}

	/**
	 * 获得在线玩家管理器实例
	 */
	public static OnlinePlayerService getOnlinePlayerService() {
		return onlinePlayerService;
	}

	public static GlobalLogicRunner getGlobalLogicRunner() {
		return globalLogicRunner;
	}

	/**
	 * 得到Human辅助的服务
	 * 
	 * @return
	 */
	public static HumanService getHumanService() {
		return humanService;
	}
	
	/**返回jetty **/
    public static HttpServer getHttpServer(){
    	return httpServer;
    }
    
    /**
     * 返回视频配置数据
     * @return
     */
    public static VideoRewardServer getVideoRewardServer(){
    	return videoRewardServer;
    }
    
    /**
     * 老虎机竞赛管理器
     * @return
     */
    public static TournamentService getTournamentService(){
    	return tournamentService;
    }
    
	/**
	 * 系统线程池服务
	 */
	public static GameExecutorService getExecutorService() {
		return executorService;
	}

	public static AsyncService getAsyncService() {
		return asyncService;
	}

	/**
	 * 获取跨线程操作服务
	 * 
	 * @return
	 */
	public static CrossThreadOperationService getCrossThreadOperationService() {
		return crossThreadOperationService;
	}

	public static MultipleWheelServer getMultipleWheelServer(){
		return multipleWheelServer;
	}
	/**
	 * UUID服务
	 */
	public static MyUUIDService getUUIDService() {
		return uuidService;
	}


	/**
	 * 获得服务器状态汇报服务
	 */
	public static ServerStatusService getServerStatusService() {
		return serverStatusService;
	}

	/**
	 * 登陆逻辑处理器
	 */
	public static LoginLogicalProcessor getLoginLogicalProcessor() {
		return loginLogicalProcessor;
	}



	/**
	 * 获得场景管理器实例
	 */
	public static SceneService getSceneService() {
		return sceneService;
	}
	


	/**
	 * 德州服务 
	 */
	public static TexasService getTexasService(){
		return texasService;
	}
//	/**
//	 *
//	 */
//	public static ClubService getClubService(){
//		return clubService;
//	}

	
	
	/**
	 * 商品服务 
	 */
	public static ShopService getShopService(){
		return shopService;
	}
	
	/**
	 * 物品服务 
	 */
	public static ItemService getItemService(){
		return itemService;
	}
	
	public static ItemNewService getItemNewService(){
		return itemNewService;
	}
	
	
	
	/**
	 * 任务新
	 * @return
	 */
	public static TaskService getTaskNewService(){
		return taskNewService;
	}
	
	/**
	 * 玩家缓存服务 
	 */
	public static PlayerCacheService getPlayerCacheService(){
		return playerCacheService;
	}
	
	/**
	 * 同步服务
	 */
	public static ILoginServerHandler getSynLoginServerHandler(){
		return synLoginServerHandler;
	}
	
	/**
	 * redis缓存服务
	 */
	public static RedisService getRedisService()
	{
		return redisService;
	}
	
	/**
	 * rpc服务
	 */
	public static RpcService getRpcService()
	{
		return rpcService;
	}
	
	
	/**
	 * 排行版服务
	 */
	public static RankService getRankService(){
		return rankService;
	}
	
	/**
	 * vipservice
	 */
	public static VipService getVipService(){
		return vipService;
	}
	
	/**
	 * 日志开关
	 * @return
	 */
	public static LoggableSwitcher getLoggableSwitcher() {
		// TODO Auto-generated method stub
		return loggableSwitcher;
	}
	
	/**
	 * 日志服务
	 * @return
	 */
	public static LogService getLogService(){
		return logService;
	}
	
	/**
	 * 签到服务
	 * @return
	 */
	public static SignInService getSignInService(){
		return signInService;
	}
	
	/**
	 * 跑马灯服务
	 */
	public static NoticeService getNoticeService(){
		return noticeService;
	}
	
	/**
	 * 活动服务
	 */
	public static ActivityService getActivityService(){
		return activityService;
	}
	
	/**
	 * misc服务
	 */
	public static MiscService getMiscService(){
		return miscService;
	}
	/**
	 * 获取字符过滤服务
	 * 
	 * @return
	 */
	public static WordFilterService getWordFilterService() 
	{
		return wordFilterService;
	}
	
	/**
	 * 脏话过滤器
	 */
	public static DirtFilterService getDirtFilterService()
	{
		return dirtFilterService;
	}
	
	/**
	 * 充值
	 */
	public static RechargeService getRechargeService()
	{
		return rechargeService;
	}
	
	/**聊天服务*/
	public static ChatService getChatService(){
		return chatService;
	}
	
	/**
	 * 补偿服务
	 */
	public static CompensationService getCompensationService(){
		return compensationService;
	}
		/**

	 *机器人服务
	 */
	public static RobotService getRobotService(){
		return robotService;
	}
	/**
	 * 百家乐服务
	 * @return
	 */
	public static BaccartService getBaccartService(){
		return baccartService;
	}
	
	public static LuckySpinService getLuckySpinService(){
		return luckySpinService;
	}
	
	public static SlotService getSlotService(){
		return slotService;
	}
	/**
	 * 老虎机大转盘玩法
	 * @return
	 */
	public static SlotWheelService getSlotWheelService(){
		return slotWheelService;
	}
	
	public static RankNewServer getRankNewServer(){
		return rankNewServer;
	}
	
	/**
	 * 日月潭数据
	 * @return
	 */
	public static RiyuetanMultipleService getRiyuetanMultipleService(){
		return riyuetanMultipleService;
	}
	
	
	/**
	 * 维密老虎机数据
	 * @return
	 */
	public static ScatterMultipleService getScatterMultipleService(){
		return scatterMultipleService;
	}
	/**
	 *宙斯老虎机数据
	 * @return
	 */
	public static BounsZeusService getBounsZeusService(){
		return bounsZeusService;
	}
	
	/**
	 * 获取角色升级礼包
	 * @return
	 */
	public static HumanLevelGiftService getHumanLevelGiftService(){
		return humanLevelGiftService;
	}
	
	/**泰国大象老虎机 **/
	public static BounsElephontTemplateService getBounsElephontTemplateService(){
		return bounsElephontTemplateService;
	}
	
	public static BonusAztecService getBonusAztecService(){
		return bonusAztecService;
	}
	
	
	/** 老虎老虎机**/
	public static BounsTigerService getBounsTigerService() {
		return bounsTigerService;
	}
	/** 西部牛仔老虎机**/
	public static ScatterCowboyService getScatterCowboyService(){
		return scatterCowboyService;
	}

	public static void setBounsTigerService(BounsTigerService bounsTigerService) {
		Globals.bounsTigerService = bounsTigerService;
	}
	/** 东方龙  老虎机 **/
	public static JackpotDragonService getJackpotDragonService() {
		return jackpotDragonService;
	}

	public static void setJackpotDragonService(
			JackpotDragonService jackpotDragonService) {
		Globals.jackpotDragonService = jackpotDragonService;
	}
	
	/** 忍者  老虎机 **/

	public static BonusNinjaService getBonusNinjaService() {
		return bonusNinjaService;
	}

	public static void setBonusNinjaService(BonusNinjaService bonusNinjaService) {
		Globals.bonusNinjaService = bonusNinjaService;
	}
	/**犀牛老虎机**/

	public static RhinoService getRhinoService() {
		return rhinoService;
	}

	public static void setRhinoService(RhinoService rhinoService) {
		Globals.rhinoService = rhinoService;
	}
	
	/**
	 * 海底世界老虎机
	 * @return
	 */

	public static BonusOceanService getBonusOceanService() {
		return bonusOceanService;
	}

	public static void setBonusOceanService(BonusOceanService bonusOceanService) {
		Globals.bonusOceanService = bonusOceanService;
	}
	/**
	 * 福尔摩斯老虎机
	 * @return
	 */
	
	public static BonusHolmesService getBonusHolmesService() {
		return bonusHolmesService;
	}

	public static void setBonusHolmesService(BonusHolmesService bonusHolmesService) {
		Globals.bonusHolmesService = bonusHolmesService;
	}

	/**
	 * 根据IP查询国家
	 * @return
	 */
	public static IPSearchService getiPSearchService() {
		return iPSearchService;
	}

	public static void setiPSearchService(IPSearchService iPSearchService) {
		Globals.iPSearchService = iPSearchService;
	}

	/**微信远程调用接口**/
	public static WeixinService getWeixinService() {
		return weixinService;
	}
	
	/**获取服务器最小ID **/
	public static ServerComm getServerComm(){
		return serverComm;
	}
	
	/**老虎机房间管理器 **/
	public static SlotRoomService getSlotRoomService(){
		return slotRoomService;
	}

	public static void setWeixinService(WeixinService weixinService) {
		Globals.weixinService = weixinService;
	}

	/**
	 * 获取石器时老虎机数据
	 * @return
	 */
	public static MagicSymbolService getMagicSymbolService(){
		return magicSymbolService;
	}
	
	/**
	 * 获取Vip 新系统
	 * @return
	 */
	public static VipNewServer getVipNewServer(){
		return vipNewServer;
	}
	
	/** 公共彩金数据 **/
	public static JackpotServer getJackpotServer(){
		return jackpotServer;
	}
	
	/**
	 * 礼包服务
	 * @return
	 */
	public static GiftService getGiftService(){
		return giftService;
	}
	/**
	 * 成就服务
	 * @return
	 */
	public static HumanAchievementServer getHumanAchievementServer(){
		return humanAchievementServer;
	}
	
	/**
	 * 兑换码服务
	 * @return
	 */
	public static ConversioncodeService getConversioncodeService(){
		return conversioncodeService;
	}
	
	/**
	 * 卡片兑换
	 * @return
	 */
	public static CardRewardServer getCardRewardServer(){
		return cardRewardServer;
	}
	
	/**
	 * 收集
	 * @return
	 */
	public static CollectServer getCollectServer(){
		return collectServer;
	}
	
	/**
	 * 本地配置
	 * @return
	 */
	public static LocalConfig getLocalConfig(){
		return localConfig;
	}

	public static long getUtcOffset() {
		return utcOffset;
	}
	/**
	 * 狮身人面老虎机
	 * @return
	 */
	public static WildSphinxService getWildSphinxService() {
		return wildSphinxService;
	}

	public static void setWildSphinxService(WildSphinxService wildSphinxService) {
		Globals.wildSphinxService = wildSphinxService;
	}
	/**
	 * 狼老虎机
	 * @return
	 */
	public static BonusWolfService getBonusWolfService() {
		return bonusWolfService;
	}

	public static void setBonusWolfService(BonusWolfService bonusWolfService) {
		Globals.bonusWolfService = bonusWolfService;
	}
	/**
	 * 水晶魔法宝石老虎机
	 * @return
	 */
	public static ScatterCrystalService getScatterCrystalService() {
		return scatterCrystalService;
	}

	public static void setScatterCrystalService(
			ScatterCrystalService scatterCrystalService) {
		Globals.scatterCrystalService = scatterCrystalService;
	}
	/**
	 * 存钱罐
	 * @return
	 */
	public static HumanTreasuryService getHumanTreasuryService() {
		return humanTreasuryService;
	}
	/**
	 * 海盗老虎机
	 * @return
	 */
	public static BounsPirateService getBounsPirateService() {
		return bounsPirateService;
	}

	public static CouponService getCouponService() {
		return couponService;
	}

	public static SpartaService getSpartaService() {
		return spartaService;
	}

	public static CommonActivityService getCommonActivityService() {
		return commonActivityService;
	}

	public static ClubTemplateService getClubTemplateService() {
		return clubTemplateService;
	}
	public static ClubService getClubService() {
		return clubService;
	}
	public static WeeklyMonthlyPackService getWeeklyMonthlyPackService() {
		return weeklyMonthlyPackService;
	}
	
//	public static ClubInvateService getClubInvateService() {
//		return clubInvateService;
//	}

	public static BonusBrazilService getBonusBrazilService() {
		return bonusBrazilService;
	}

	public static void setBonusBrazilService(BonusBrazilService bonusBrazilService) {
		Globals.bonusBrazilService = bonusBrazilService;
	}

	public static RedgirlService getRedgirlService() {
		return redgirlService;
	}

	public static void setRedgirlService(RedgirlService redgirlService) {
		Globals.redgirlService = redgirlService;
	}


	public static WorldBossNewService getWorldBossNewService() {
		return worldBossNewService;
	}

	public static void setWorldBossNewService(
			WorldBossNewService worldBossNewService) {
		Globals.worldBossNewService = worldBossNewService;
	}


	public static HumanGivealikeService getHumanGivealikeService() {
		return humanGivealikeService;
	}

	public static void setHumanGivealikeService(
			HumanGivealikeService humanGivealikeService) {
		Globals.humanGivealikeService = humanGivealikeService;
	}
	
	public static BonusStoneAgeService getBonusStoneAgeService() {
		return bonusStoneAgeService;
	}

	public static void setBonusStoneAgeService(
			BonusStoneAgeService bonusStoneAgeService) {
		Globals.bonusStoneAgeService = bonusStoneAgeService;
	}

	public static BonusHallowmasService getBonusHallowmasService() {
		return bonusHallowmasService;
	}

	public static void setBonusHallowmasService(
			BonusHallowmasService bonusHallowmasService) {
		Globals.bonusHallowmasService = bonusHallowmasService;
	}

	public static WinnerWheelService getWinnerWheelService() {
		return winnerWheelService;
	}

	public static void setWinnerWheelService(WinnerWheelService winnerWheelService) {
		Globals.winnerWheelService = winnerWheelService;
	}

	public static FunctionService getFunctionService() {
		return functionService;
	}

	public static void setFunctionService(FunctionService functionService) {
		Globals.functionService = functionService;
	}

	public static BazooService getBazooService() {
		return bazooService;
	}

	public static void setBazooService(BazooService bazooService) {
		Globals.bazooService = bazooService;
	}

	public static ClassicalService getBazooMService() {
		return bazooMService;
	}

	public static void setBazooMService(ClassicalService bazooMService) {
		Globals.bazooMService = bazooMService;
	}

	public static BazooPubService getBazooPubService() {
		return bazooPubService;
	}

	public static void setBazooPubService(BazooPubService bazooPubService) {
		Globals.bazooPubService = bazooPubService;
	}
	

	public static BazooPriService getBazooPriService() {
		return bazooPriService;
	}

	public static void setBazooPriService(BazooPriService bazooPriService) {
		Globals.bazooPriService = bazooPriService;
	}

	public static CowService getBazooCowService() {
		return bazooCowService;
	}

	public static void setBazooCowService(CowService bazooCowService) {
		Globals.bazooCowService = bazooCowService;
	}

	

	public static ShowHandService getBazooShowHandService() {
		return bazooShowHandService;
	}

	public static void setBazooShowHandService(
			ShowHandService bazooShowHandService) {
		Globals.bazooShowHandService = bazooShowHandService;
	}

	
	
	
	
	public static HumanBankService getHumanBankService() {
		return humanBankService;
	}

	public static void setHumanBankService(HumanBankService humanBankService) {
		Globals.humanBankService = humanBankService;
	}

	public static HumanBazooPersonalService getHumanBazooPersonalService() {
		return humanBazooPersonalService;
	}

	public static void setHumanBazooPersonalService(
			HumanBazooPersonalService humanBazooPersonalService) {
		Globals.humanBazooPersonalService = humanBazooPersonalService;
	}

	public static HumanBazooTaskService getHumanBazooTaskService() {
		return humanBazooTaskService;
	}

	public static HumanBazooAchieveService getHumanBazooAchieveService() {
		return humanBazooAchieveService;
	}

	public static void setHumanBazooAchieveService(
			HumanBazooAchieveService humanBazooAchieveService) {
		Globals.humanBazooAchieveService = humanBazooAchieveService;
	}

	public static void setHumanBazooTaskService(
			HumanBazooTaskService humanBazooTaskService) {
		Globals.humanBazooTaskService = humanBazooTaskService;
	}

	public static BazooRpcService getBazooRpcService() {
		return bazooRpcService;
	}
	public static BazooGiftService getBazooGiftService() {
		return bazooGiftService;
	}

	
	public static void setBazooRpcService(BazooRpcService bazooRpcService) {
		Globals.bazooRpcService = bazooRpcService;
	}

	public static BlackWhiteService getBlackWhiteService() {
		return blackWhiteService;
	}

	public static void setBlackWhiteService(BlackWhiteService blackWhiteService) {
		Globals.blackWhiteService = blackWhiteService;
	}

	public static ChannelDiffService getChannelDiffService() {
		return channelDiffService;
	}

	public static void setChannelDiffService(ChannelDiffService channelDiffService) {
		Globals.channelDiffService = channelDiffService;
	}

	/**
	 * 新手服务
	 */
	public static NewbieService getNewbieService(){
		return newbieService;
	}

}
