package com.gameserver.common.db;

import java.lang.reflect.Field;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.common.constants.Loggers;
import com.core.event.EventListenerAdapter;
import com.core.orm.DBAccessEvent;
import com.core.orm.DBAccessEventListener;
import com.core.orm.DBService;
import com.core.orm.DBServiceBuilder;
import com.db.dao.ActivityDao;
import com.db.dao.BaccartRoomModelDao;
import com.db.dao.BaseDao;
import com.db.dao.BazooRoomCreateDao;
import com.db.dao.BossDao;
import com.db.dao.ClubApplyDao;
import com.db.dao.ClubDao;
import com.db.dao.ClubGiftRecordtDao;
import com.db.dao.ClubInvateDao;
import com.db.dao.ClubInvatePlayerDao;
import com.db.dao.ClubInviteDao;
import com.db.dao.ClubMemberListDao;
import com.db.dao.ClubNoteDao;
import com.db.dao.ClubSeasonDao;
import com.db.dao.CommonActivityDao;
import com.db.dao.CompensationDao;
import com.db.dao.ConversioncodeDao;
import com.db.dao.DbVersionDao;
import com.db.dao.DeviceDao;
import com.db.dao.FunctionDao;
import com.db.dao.HumanAchievementDao;
import com.db.dao.HumanActivityDao;
import com.db.dao.HumanAttackBossDao;
import com.db.dao.HumanBaccartDao;
import com.db.dao.HumanBazooAchieveDao;
import com.db.dao.HumanBazooNewGuyDao;
import com.db.dao.HumanBazooPersonalDao;
import com.db.dao.HumanBazooRankDao;
import com.db.dao.HumanBazooSigninDao;
import com.db.dao.HumanBazooTaskDao;
import com.db.dao.HumanBazooWinsDao;
import com.db.dao.HumanCollectDao;
import com.db.dao.HumanCompensationDao;
import com.db.dao.HumanDao;
import com.db.dao.HumanFriendDao;
import com.db.dao.HumanFriendGiftDao;
import com.db.dao.HumanFriendRequestDao;
import com.db.dao.HumanGiftDao;
import com.db.dao.HumanGivealikeDao;
import com.db.dao.HumanItemDao;
import com.db.dao.HumanLuckyMatchDao;
import com.db.dao.HumanLuckySpinDao;
import com.db.dao.HumanMiscDao;
import com.db.dao.HumanMonthCardDao;
import com.db.dao.HumanMonthWeekDao;
import com.db.dao.HumanNewComerDao;
import com.db.dao.HumanPayguideDao;
import com.db.dao.HumanRechargeOrderDao;
import com.db.dao.HumanRefundDao;
import com.db.dao.HumanSlotDao;
import com.db.dao.HumanTaskDao;
import com.db.dao.HumanTexasDao;
import com.db.dao.HumanTexasSNGDao;
import com.db.dao.HumanTreasuryDao;
import com.db.dao.HumanVipDao;
import com.db.dao.HumanVipNewDao;
import com.db.dao.HumanWeekCardDao;
import com.db.dao.HumanWeekSignInDao;
import com.db.dao.MailDao;
import com.db.dao.NewbieDao;
import com.db.dao.NoticeDao;
import com.db.dao.RankDao;
import com.db.dao.RobotComplementDao;
import com.db.dao.RobotStatisDataDao;
import com.db.dao.SlotDao;
import com.db.dao.TextasDao;
import com.db.dao.UserInfoDao;
import com.gameserver.common.config.GameServerConfig;

/** 
 * GameServer用到的数据库访问对象管理器
 * @author Thinker
 * 
 */
public final class GameDaoService
{
	/** 辅助初始化类 */
	private DaoHelper daoHelper;

	public GameDaoService(GameServerConfig config)
	{
		daoHelper = new DaoHelper(config);
	}

	/**
	 * 获取DeviceDao
	 * 
	 * @return
	 */
	public DeviceDao getDeviceDao() 
	{
		return daoHelper.deviceDao;
	}

	public DBService getDBService() 
	{
		if (daoHelper == null) 
		{
			return null;
		}
		return daoHelper.dbService;
	}


	/**
	 * 获取人物角色数据管理操作类DAO
	 */
	public HumanDao getHumanDao()
	{
		return daoHelper.humanDao;
	}
	

	/**
	 * 获取人物道具管理操作类DAO
	 */
	public HumanItemDao getHumanItemDao()
	{
		return daoHelper.humanItemDao;
	}

	/**
	 * 赛季结束时间对现在在数据库保存
	 * @return
	 */
	public ClubSeasonDao getClubSeasonDao(){
		return daoHelper.clubSeasonDao;
	}
	/**
	 * 获取杂乱dao
	 */
	public HumanMiscDao getHumanMiscDao()
	{
		return daoHelper.humanMiscDao;
	}
	
	
	/**
	 * 获取人物角色数据管理操作类DAO
	 */
	public HumanTexasDao getHumanTexasDao()
	{
		return daoHelper.humanTexasDao;
	}
	
	/**
	 * 获取月卡dao
	 */
	public HumanMonthCardDao getHumanMonthCardDao()
	{
		return daoHelper.humanMonthCardDao;
	}
	/**
	 * 获取周卡dao
	 */
	public HumanWeekCardDao getHumanWeekCardDao()
	{
		return daoHelper.humanWeekCardDao;
	}

	/**
	 * 邮件dao
	 * @return
	 */
	public MailDao getMailDao() {
		// TODO Auto-generated method stub
		return daoHelper.mailDao;
	}
	
	/**
	 * 新手引导dao
	 * @return
	 */
	public NewbieDao getNewbieDao() {
		return daoHelper.newbieDao;
	}
	
	/**
	 *获取好友
	 */
	public HumanFriendDao getHumanFriendDao()
	{
		return daoHelper.humanFriendDao;
	}
	

	/**
	 * 好友请求dao
	 * @return
	 */
	public HumanFriendRequestDao getHumanFriendRequestDao() {
		// TODO Auto-generated method stub
		return daoHelper.humanFriendRequestDao;
	}
	

	/**
	 * 每周签到
	 * @return
	 */
	public HumanWeekSignInDao getHumanWeekSignInDao() {
		// TODO Auto-generated method stub
		return daoHelper.humanWeekSignInDao;
	}
	
	/**
	 * 获取数据库版本号Dao
	 */
	public DbVersionDao getDbVersionDao()
	{
		return daoHelper.dbVersionDao;
	}

	/**
	 * 跑马灯dao
	 */
	public NoticeDao getNoticeDao()
	{
		return daoHelper.noticeDao;
	}
	/**
	 * 活动dao
	 */
	public ActivityDao getActivityDao(){
		return daoHelper.activityDao;
	}
	/**
	 * 公共活动dao
	 */
	public CommonActivityDao getCommonActivityDao(){
		return daoHelper.commonActivityDao;
	}
	public FunctionDao getFunctionDao(){
		return daoHelper.functionDao;
	}
	
	/**
	 * 补偿dao
	 */
	public CompensationDao getCompensationDao(){
		return daoHelper.compensationDao;
	}
	
	/**
	 * 
	 */
	public HumanCompensationDao getHumanCompensationDao(){
		return daoHelper.humanCompensationDao;
	}
	
	/**
	 * vip dao
	 */
	public HumanVipDao getVipDao(){
		return daoHelper.humanVipDao;
	}
	
     public HumanVipNewDao getVipNewDao(){
    	 return daoHelper.humanVipNewDao;
     }
     
     public HumanCollectDao gethumanCollectDao(){
    	 return daoHelper.humanCollectDao;
     }
     public HumanTreasuryDao getHumanTreasuryDao(){
    	 return daoHelper.humanTreasuryDao;
     }

     public HumanMonthWeekDao getHumanMonthWeekDao(){
    	 return daoHelper.humanMonthWeekDao;
    	 
     }
	/**
	 * 充值 dao
	 */
	public HumanRechargeOrderDao getRechargeOrderDao(){
		return daoHelper.humanRechargeOrderDao;
	}
	
	/**
	 * 任务dao
	 */
	public HumanTaskDao getHumanTaskDao(){
		return daoHelper.humanTaskDao;
	}

	/**
	 * 活动数据dao
	 */
	public HumanActivityDao getHumanActivityDao(){
		return daoHelper.humanActivityDao;
	}
	
	/**
	 * 德州sng dao
	 */
	public HumanTexasSNGDao getHumanTexasSNGDao(){
		return daoHelper.humanTexasSNGDao;
	}
	
	/*
	 * 德州refund dao
	 */
	public HumanRefundDao getHumanRefundDao(){
		return daoHelper.humanRefundDao;
	} 
	
	/**
	 * 德州好友礼物 dao
	 */
	public HumanFriendGiftDao getHumanFriendGiftDao(){
		return daoHelper.humanFriendGiftDao;
	}
	
	
	/**
	 * 机器人
	 */
	public RobotComplementDao getRobotComplementDao(){
		return daoHelper.robotComplementDao;
	}

	/**
	 * 百家乐dao
	 */
	public HumanBaccartDao getHumanBaccartDao(){
		return daoHelper.humanBaccartDao;
	}
	
	/**
	 * 百家乐dao
	 */
	public BaccartRoomModelDao getBaccartRoomModelDao(){
		return daoHelper.baccartRoomModelDao;
	}
	
	/**
	 * 幸运转盘
	 * @return
	 */
	public HumanLuckySpinDao getHumanLuckySpinDao(){
		return daoHelper.humanLuckySpinDao;
	}
	
	/**
	 * 幸运匹配
	 * @return
	 */
	public HumanLuckyMatchDao getHumanLuckyMatchDao(){
		return daoHelper.humanLuckyMatchDao;
	}
	
	/**
	 *老虎机
	 * @return
	 */
	public SlotDao getSlotDao(){
		return daoHelper.slotDao;
	}
	
	/**
	 * 老虎机个人信息
	 */
	public HumanSlotDao getHumanSlotDao(){
		return daoHelper.humanSlotDao;
	}
	
	/**
	 * 获得成就信息
	 */
	public HumanAchievementDao getHumanAchievementDao(){
		return daoHelper.humanAchievementDao;
	}
	
	/**
	 * 获取德州扑克个人信息
	 * @return
	 */
	public TextasDao getTextasDao(){
		return daoHelper.textasDao;
	}
	
	public UserInfoDao getUserInfoDao(){
		return daoHelper.userInfoDao;
	}
	
	/**
	 * 获取排行榜数据
	 * @return
	 */
	public RankDao getRankDao(){
		return daoHelper.rankDao;
	}
	
	/**
	 * 获取随机码数据
	 * @return
	 */
	public ConversioncodeDao getConversioncodeDao(){
		return daoHelper.conversioncodeDao;
	}
	
	/**
	 * 获取充值礼包
	 * @return
	 */
	public HumanGiftDao getHumanGiftDao(){
		return daoHelper.humanGiftDao;
	}
	/**
	 * 老虎机机器人统计数据
	 * @return
	 */
	public RobotStatisDataDao getRobotStatisDataDao(){
		return daoHelper.robotStatisDataDao;
	}
	
	/**
	 * 俱乐部岛
	 * @return
	 */
	public ClubDao getClubDao() {
		return daoHelper.clubDao;
	}
	
	public ClubMemberListDao getClubMemberListDao() {
		return daoHelper.clubMemberListDao;
	}
	
	public ClubNoteDao getClubNoteDao() {
		return daoHelper.clubNoteDao;
	}
	
	public ClubInvateDao getClubInvateDao() {
		return daoHelper.clubInvateDao;
	}
	
	public ClubApplyDao getClubApplyDao() {
		return daoHelper.clubApplyDao;
	}

	public ClubInviteDao getClubInviteDao() {
		return daoHelper.clubInviteDao;
	}
	
	public ClubInvatePlayerDao getClubInvatePlayerDao(){
		return daoHelper.clubInvatePlayerDao;
	}
	
	public HumanAttackBossDao getHumanAttackBossDao(){
		return daoHelper.humanAttackBossDao;
	}
	
	public BossDao getBossDao(){
		return daoHelper.bossDao;
	}
	
	
	
	public HumanGivealikeDao getHumanGivealikeDao(){
		return daoHelper.humanGivealikeDao;
	}
	
	
	public HumanPayguideDao getHumanPayguideDao(){
		return daoHelper.humanPayguideDao;
	}
	public HumanNewComerDao getHumanNewComerDao(){
		return daoHelper.humanNewComerDao;
	}
	
	public ClubGiftRecordtDao getClubGiftRecordtDao() {
		return daoHelper.clubGiftRecordtDao;
	}
	
	
	public HumanBazooSigninDao getHumanBazooSigninDao() {
		return daoHelper.humanBazooSigninDao;
	}
	
	public HumanBazooRankDao getBazooRankDao() {
		return daoHelper.bazooRankDao;
	}
	
	public HumanBazooPersonalDao getHumanBazooPersonalDao() {
		return daoHelper.humanBazooPersonalDao;
	}
	public HumanBazooTaskDao getHumanBazooTaskDao() {
		return daoHelper.humanBazooTaskDao;
	}
	public HumanBazooAchieveDao getHumanBazooAchieveDao() {
		return daoHelper.humanBazooAchieveDao;
	}
	public HumanBazooNewGuyDao gethumanBazooNewGuyDao() {
		return daoHelper.humanBazooNewGuyDao;
	}
	public HumanBazooWinsDao getHumanBazooWinsDao() {
		return daoHelper.humanBazooWinsDao;
	}
	
	public BazooRoomCreateDao getBazooRoomCreateDao() {
		return daoHelper.bazooRoomCreateDao;
	}
	
	
	
	public DaoHelper getDaoHelper() {
		return daoHelper;
	}

	public void setDaoHelper(DaoHelper daoHelper) {
		this.daoHelper = daoHelper;
	}

	/**
	 * 检查所有 DAO 所使用的实体类
	 * 
	 * @throws SQLException
	 * 
	 */
	public void checkAllEntities() 
	{
		// 被排除的, 不需要检查的类
		Class<?>[] excludeClazzes = new Class<?>[] { };

		// 检查并获取所有 DAO 对象
		BaseDao<?>[] daos = checkAndGetAllDeclaredDao(this.daoHelper,excludeClazzes);

		if (daos == null || daos.length <= 0) return;

		for (BaseDao<?> dao : daos)
		{
			// 检查实体类
			logInfo(dao.getClass().getSimpleName() + " - 检查实体");
			//dao.checkEntity();
		}
	}

	/**
	 * 记录日志信息
	 * 
	 * @param msg
	 */
	private static void logInfo(String msg) {
		if (msg == null) {
			return;
		}

		if (Loggers.dbLogger.isInfoEnabled()) {
			Loggers.dbLogger.info(msg);
		}
	}

	/**
	 * 从参数中检查并获取已声明的 DAO 对象数组
	 * 
	 * @param daoHelper
	 * @param excludeClazzes
	 *            被排除的, 不被检查的 DAO 类
	 * @return
	 * 
	 * @throws IllegalArgumentException
	 *             if daoHelper == null
	 * @throws RuntimeException
	 * 
	 */
	private static BaseDao<?>[] checkAndGetAllDeclaredDao(DaoHelper daoHelper,Class<?>[] excludeClazzes)
	{
		if (daoHelper == null)
		{
			throw new IllegalArgumentException("daoHelper is null");
		}

		Field[] daoFields = daoHelper.getClass().getDeclaredFields();

		if (daoFields == null || daoFields.length <= 0)
		{
			return null;
		}

		// 结果列表
		List<BaseDao<?>> daoList = new ArrayList<BaseDao<?>>();
		// 被排除的类
		HashSet<Class<?>> excludeClazzSet = new HashSet<Class<?>>();

		if (excludeClazzes != null && excludeClazzes.length > 0) 
		{
			// 事先将被排除的类增加到字典,
			// 以备后面的步骤查询使用
			for (Class<?> clazz : excludeClazzes)
			{
				// 将类添加到字典
				excludeClazzSet.add(clazz);
			}
		}

		for (Field f : daoFields) 
		{
			// 设置为可访问
			f.setAccessible(true);

			try 
			{
				Object obj = f.get(daoHelper);

				if ((obj instanceof DBService) || (obj instanceof Map<?, ?>)|| excludeClazzSet.contains(obj.getClass()))
				{
					// 如果是 DBService 或者是 Map 的派生类,
					// 再或者是被排除的类,
					// 那么直接跳过, 不加到返回值列表中
					continue;
				}

				if (!(obj instanceof BaseDao<?>)) 
				{
					// 如果当前字段没有被排除, 那么就应该是 BaseDao 的派生类!
					// 如果不是 BaseDao 派生类, 那么直接抛出异常!!
					throw new RuntimeException(f.getName()+ "不是 BaseDao 的派生类 !!");
				}

				daoList.add((BaseDao<?>) obj);
			} catch (Exception ex)
			{
				// 抛出运行时异常
				throw new RuntimeException(ex);
			}
		}
		return daoList.toArray(new BaseDao<?>[0]);
	}
	
	

	/**
	 * Dao 配置，初始化 - dao
	 * @author Thinker
	 * 
	 */
	private static final class DaoHelper
	{
		/** 数据库连接 */
		private final DBService dbService;
		/** 玩家角色信息DAO */
		private final HumanDao humanDao;
	
		/**玩家道具包*/
		private final HumanItemDao humanItemDao;
		/**玩家德州DAO */
		private final HumanTexasDao humanTexasDao;
		/**玩家月卡DAO */
		private final HumanMonthCardDao humanMonthCardDao;
		/**玩家周卡DAO */
		private final HumanWeekCardDao humanWeekCardDao;
		/**邮件dao*/
		private final MailDao mailDao;
		/**新手dao*/
		private final NewbieDao newbieDao;
		/**好友dao*/
		private final HumanFriendDao humanFriendDao;
		/**申请好友dao*/
		private final HumanFriendRequestDao humanFriendRequestDao;
		/**每周签到*/
		private final HumanWeekSignInDao humanWeekSignInDao;
		/** 数据库版本号Dao */
		private final DbVersionDao dbVersionDao;
		/**跑马灯*/
		private final NoticeDao noticeDao;

		/**杂乱*/
		private final HumanMiscDao humanMiscDao;
		/**vip*/
		private final HumanVipDao humanVipDao;
		
		private final HumanVipNewDao humanVipNewDao;
		/**recharge*/
		private final HumanRechargeOrderDao humanRechargeOrderDao;
		/**任务*/
		private final HumanTaskDao humanTaskDao;
		/**活动数据*/
		private final HumanActivityDao humanActivityDao;
		/**活动*/
		private final ActivityDao activityDao;
		/**活动*/
		private final CommonActivityDao commonActivityDao;
		/**功能*/
		private final FunctionDao functionDao;
		/**全服补偿dao*/
		private final CompensationDao compensationDao;
		/**全服个人补偿dao*/
		private final HumanCompensationDao humanCompensationDao;
		/**sng*/
		private final HumanTexasSNGDao humanTexasSNGDao;
		/**refund*/
		private final HumanRefundDao humanRefundDao;
		/**friend gift*/
		private final HumanFriendGiftDao humanFriendGiftDao;
		/**机器人*/
		private final RobotComplementDao robotComplementDao;
		

		/**baccart dao*/
		private final HumanBaccartDao humanBaccartDao;
		/**百家乐房间dao*/
		private final BaccartRoomModelDao baccartRoomModelDao;

		/** 客户端设备信息DAO */
		private final DeviceDao deviceDao;
		
		/**幸运转盘*/
		private final HumanLuckySpinDao humanLuckySpinDao;
		/**幸运匹配*/
		private final HumanLuckyMatchDao humanLuckyMatchDao;
		/**老虎机*/
		private final SlotDao slotDao;
		/**老虎机个人信息*/
		private final HumanSlotDao humanSlotDao;
		/**德州数据 **/
		private final TextasDao textasDao;
		/**德州数据 **/
		private final UserInfoDao userInfoDao;
		/** 排行榜数据 **/
		private final RankDao rankDao;
		/**礼包  **/
		private final HumanGiftDao humanGiftDao;
		/**成就系统 **/
		private final HumanAchievementDao humanAchievementDao;
		
		/**随机码奖励系统 **/
		private final ConversioncodeDao conversioncodeDao;
		/**机器人统计**/
		private final RobotStatisDataDao robotStatisDataDao;
		
		private final HumanCollectDao humanCollectDao;
		
		private final HumanTreasuryDao humanTreasuryDao;
		
		private final HumanMonthWeekDao humanMonthWeekDao;
		
		private final ClubDao clubDao;
		
		private final ClubMemberListDao clubMemberListDao;
		
		private final ClubNoteDao clubNoteDao;
		
		private final ClubInvateDao clubInvateDao;

		private final ClubApplyDao clubApplyDao;

		private final ClubSeasonDao clubSeasonDao;

		private final ClubInvatePlayerDao clubInvatePlayerDao;
		
		private final HumanAttackBossDao humanAttackBossDao;
		
		private final BossDao bossDao;
		
		private final HumanGivealikeDao humanGivealikeDao;

		private final ClubInviteDao clubInviteDao;
		
		private final HumanPayguideDao humanPayguideDao;
		private final HumanNewComerDao humanNewComerDao;
		/**俱乐部礼包发送记录  **/
		private final ClubGiftRecordtDao clubGiftRecordtDao;
		
		/**无双吹牛  **/
		private final HumanBazooSigninDao humanBazooSigninDao;
		private final HumanBazooRankDao bazooRankDao;
		private final HumanBazooPersonalDao humanBazooPersonalDao;
		private final HumanBazooTaskDao humanBazooTaskDao;
		private final HumanBazooAchieveDao humanBazooAchieveDao;
		private final HumanBazooWinsDao humanBazooWinsDao;
		private final HumanBazooNewGuyDao humanBazooNewGuyDao;
		
		private final BazooRoomCreateDao bazooRoomCreateDao;
		
		private DaoHelper(GameServerConfig config){
			
			/** 资源初始化 */
			EventListenerAdapter eventAdapter = new EventListenerAdapter();
			eventAdapter.addListener(DBAccessEvent.class,new DBAccessEventListener(config));
			ClassLoader _classLoader = Thread.currentThread().getContextClassLoader();
			int daoType = config.getDbInitType();

			String[] _dbConfig = config.getDbConfigName().split(",");
			URL _dbUrl = _classLoader.getResource(_dbConfig[0]);
			String[] _dbResources = new String[_dbConfig.length - 1];
			if (_dbConfig.length > 1) 
			{
				System.arraycopy(_dbConfig, 1, _dbResources, 0,_dbConfig.length - 1);
			}

			/* 数据库类初始化 */
			dbService = DBServiceBuilder.buildDirectDBService(eventAdapter,daoType, _dbUrl, _dbResources);

			Loggers.gameLogger.info("DBService instance:" + dbService);

			
			humanDao = new HumanDao(dbService);
	
			humanItemDao = new HumanItemDao(dbService);
			humanTexasDao = new HumanTexasDao(dbService);
			humanMonthCardDao = new HumanMonthCardDao(dbService);
			humanWeekCardDao = new HumanWeekCardDao(dbService);
			dbVersionDao = new DbVersionDao(dbService);
			deviceDao = new DeviceDao(dbService);
			mailDao = new MailDao(dbService);
			newbieDao = new NewbieDao(dbService);
			humanFriendDao = new HumanFriendDao(dbService);
			humanFriendRequestDao = new HumanFriendRequestDao(dbService);

			humanWeekSignInDao = new HumanWeekSignInDao(dbService);
			noticeDao = new NoticeDao(dbService);
			humanMiscDao = new HumanMiscDao(dbService);
			humanVipDao = new HumanVipDao(dbService);
			humanVipNewDao = new HumanVipNewDao(dbService);
			
			humanRechargeOrderDao = new HumanRechargeOrderDao(dbService);
			humanTaskDao = new HumanTaskDao(dbService);
			humanActivityDao = new HumanActivityDao(dbService);
			activityDao = new ActivityDao(dbService);
			commonActivityDao = new CommonActivityDao(dbService);
			functionDao = new FunctionDao(dbService);
			compensationDao = new CompensationDao(dbService);
			humanTexasSNGDao = new HumanTexasSNGDao(dbService);
			humanCompensationDao = new HumanCompensationDao(dbService);
			humanRefundDao = new HumanRefundDao(dbService);
			humanFriendGiftDao = new HumanFriendGiftDao(dbService);
			robotComplementDao= new RobotComplementDao(dbService);
			humanBaccartDao = new HumanBaccartDao(dbService);
			baccartRoomModelDao = new BaccartRoomModelDao(dbService);
			humanLuckySpinDao = new HumanLuckySpinDao(dbService);
			humanLuckyMatchDao = new HumanLuckyMatchDao(dbService);
			slotDao = new SlotDao(dbService);
			humanSlotDao = new HumanSlotDao(dbService);
			textasDao = new TextasDao(dbService);
			userInfoDao = new UserInfoDao(dbService);
			rankDao = new RankDao(dbService);
			humanGiftDao = new HumanGiftDao(dbService);
			humanAchievementDao = new HumanAchievementDao(dbService);
			conversioncodeDao = new ConversioncodeDao(dbService);
			robotStatisDataDao = new RobotStatisDataDao(dbService);
			humanCollectDao = new HumanCollectDao(dbService);
			humanTreasuryDao = new HumanTreasuryDao(dbService);
			humanMonthWeekDao = new HumanMonthWeekDao(dbService);
			clubDao = new ClubDao(dbService);
			clubMemberListDao = new ClubMemberListDao(dbService);
			clubNoteDao = new ClubNoteDao(dbService);
			clubInvateDao = new ClubInvateDao(dbService);
			clubApplyDao = new ClubApplyDao(dbService);
			clubInviteDao = new ClubInviteDao(dbService);
			clubSeasonDao = new ClubSeasonDao(dbService);
			clubInvatePlayerDao = new ClubInvatePlayerDao(dbService);
			humanAttackBossDao = new HumanAttackBossDao(dbService);
			bossDao = new BossDao(dbService);
			humanGivealikeDao = new HumanGivealikeDao(dbService);
			humanPayguideDao = new HumanPayguideDao(dbService);
			humanNewComerDao = new HumanNewComerDao(dbService);
			clubGiftRecordtDao = new ClubGiftRecordtDao(dbService);
			humanBazooSigninDao = new HumanBazooSigninDao(dbService);
			bazooRankDao = new HumanBazooRankDao(dbService);
			humanBazooPersonalDao = new HumanBazooPersonalDao(dbService);
			humanBazooTaskDao = new HumanBazooTaskDao(dbService);
			humanBazooAchieveDao = new HumanBazooAchieveDao(dbService);
			humanBazooWinsDao = new HumanBazooWinsDao(dbService);
			bazooRoomCreateDao = new BazooRoomCreateDao(dbService);
			humanBazooNewGuyDao = new HumanBazooNewGuyDao(dbService);
		}



	}

	


}
