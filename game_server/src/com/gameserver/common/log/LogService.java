package com.gameserver.common.log;

import com.logserver.model.GoldLog;
import com.logserver.model.ChatLog;
import com.logserver.model.VipLog;
import com.logserver.model.DiamondLog;
import com.logserver.model.WeekcardLog;
import com.logserver.model.MonthcardLog;
import com.logserver.model.SignInLog;
import com.logserver.model.RechargeLog;
import com.logserver.model.BasicPlayerLog;
import com.logserver.model.OnlineTimeRewardLog;
import com.logserver.model.OnlineTimeLog;
import com.logserver.model.DailyTaskLog;
import com.logserver.model.ExceptionLog;
import com.logserver.model.CharmLog;
import com.logserver.model.FriendLog;
import com.logserver.model.ItemLog;
import com.logserver.model.RenameLog;
import com.logserver.model.TexasRoomLog;
import com.logserver.model.BaccaratRoomLog;
import com.logserver.model.LuckySpinLog;
import com.logserver.model.SlotLog;
import com.logserver.model.DataOverviewLog;
import com.logserver.model.NewRechargeLog;
import com.logserver.model.PlayerLoginLog;
import com.logserver.model.PlayerOnleLog;
import com.logserver.model.PlayerKeepLog;
import com.logserver.model.SlotRoomLog;
import com.logserver.model.InOutTimeLog;
import com.logserver.model.JackpotLog;
import com.logserver.model.TournamentLog;
import com.logserver.model.WorldBossLog;
import com.logserver.model.StatisticsTimeLog;
import com.logserver.model.DiceClassicalRoomLog;
import com.logserver.model.DiceClassicalGuessLog;
import com.logserver.model.DiceClassicalCallNumLog;
import com.logserver.model.DiceClassicalBazooGoldLog;
import com.logserver.model.DiceCowLog;
import com.logserver.model.DiceShowHandLog;
import com.logserver.model.DiceSignInLog;
import com.logserver.model.DiceBlackWhiteLog;
import com.logserver.model.DiceStatisticsWinLostLog;
import com.core.log.UdpLoggerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.common.LogReasons;
import com.gameserver.human.Human;
import com.gameserver.common.Globals;

/**
 * This is an auto generated source,please don't modify it.
 */
public class LogService {
	private static final Logger logger = LoggerFactory.getLogger(LogService.class);
	/** 日志客户端 */
	private final UdpLoggerClient udpLoggerClient = UdpLoggerClient.getInstance();
	/**  游戏区ID */
	private int regionID;
	/** 游戏服务器ID */
	private int serverID;
	/** 日志开关 */
	private LoggableSwitcher loggableSwitcher = Globals.getLoggableSwitcher();

	/**
	 * 初始化
	 * 
	 * @param logServerIp
	 *            日志服务器IP
	 * @param logServerPort
	 *            日志服务器端口
	 * @param regionID
	 *            游戏区ID
	 * @param serverID
	 *            游戏服务器ID
	 */
	public LogService(String logServerIp, int logServerPort, int regionID, int serverID) {
		udpLoggerClient.initialize(logServerIp, logServerPort);
		udpLoggerClient.setRegionId(regionID);
		udpLoggerClient.setServerId(serverID);

		this.regionID = regionID;
		this.serverID = serverID;
		
		if (logger.isInfoEnabled()) {
			logger.info("Connnect to Log server : " + logServerIp + " : " + logServerPort);
		}
	}

	/**
	 * 发送金钱日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param goldDelta 变化
	 * @param goldLeft 剩余
	 */
	public void sendGoldLog(Human human,
				LogReasons.GoldLogReason reason,				String param			,long goldDelta
			,long goldLeft
		) {
		if(!loggableSwitcher.isGoldLoggable()){return;}
		udpLoggerClient.sendMessage(new GoldLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,goldDelta
			,goldLeft
		));
	}

	/**
	 * 发送聊天日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param channel 聊天范围
	 * @param recCharName 如果为私聊，则记录私聊消息的接收玩家角色名称,否则该字段无意义
	 * @param content 聊天的内容
	 */
	public void sendChatLog(Human human,
				LogReasons.ChatLogReason reason,				String param			,int channel
			,String recCharName
			,String content
		) {
		if(!loggableSwitcher.isChatLoggable()){return;}
		udpLoggerClient.sendMessage(new ChatLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,channel
			,recCharName
			,content
		));
	}

	/**
	 * 发送vip日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param vipLevel vip等级
	 */
	public void sendVipLog(Human human,
				LogReasons.VipLogReason reason,				String param			,int vipLevel
		) {
		if(!loggableSwitcher.isVipLoggable()){return;}
		udpLoggerClient.sendMessage(new VipLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,vipLevel
		));
	}

	/**
	 * 发送平台币日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param diamondDelta 变化
	 * @param diamondLeft 剩余
	 */
	public void sendDiamondLog(Human human,
				LogReasons.DiamondLogReason reason,				String param			,long diamondDelta
			,long diamondLeft
		) {
		if(!loggableSwitcher.isDiamondLoggable()){return;}
		udpLoggerClient.sendMessage(new DiamondLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,diamondDelta
			,diamondLeft
		));
	}

	/**
	 * 发送周卡日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param duration 剩余时间
	 */
	public void sendWeekcardLog(Human human,
				LogReasons.WeekcardLogReason reason,				String param			,long duration
		) {
		if(!loggableSwitcher.isWeekcardLoggable()){return;}
		udpLoggerClient.sendMessage(new WeekcardLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,duration
		));
	}

	/**
	 * 发送月卡日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param duration 剩余时间
	 */
	public void sendMonthcardLog(Human human,
				LogReasons.MonthcardLogReason reason,				String param			,long duration
		) {
		if(!loggableSwitcher.isMonthcardLoggable()){return;}
		udpLoggerClient.sendMessage(new MonthcardLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,duration
		));
	}

	/**
	 * 发送签到日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param days 签到天
	 * @param culumative 总共签到天数
	 */
	public void sendSignInLog(Human human,
				LogReasons.SignInLogReason reason,				String param			,int days
			,int culumative
		) {
		if(!loggableSwitcher.isSignInLoggable()){return;}
		udpLoggerClient.sendMessage(new SignInLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,days
			,culumative
		));
	}

	/**
	 * 发送充值日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param orderId 订单号
	 * @param productId 产品id
	 * @param cost 花费
	 */
	public void sendRechargeLog(Human human,
				LogReasons.RechargeLogReason reason,				String param			,long orderId
			,int productId
			,int cost
		) {
		if(!loggableSwitcher.isRechargeLoggable()){return;}
		udpLoggerClient.sendMessage(new RechargeLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,orderId
			,productId
			,cost
		));
	}

	/**
	 * 发送玩家基础日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param ip IP地址
	 * @param exp 经验
	 * @param minute 当天累计在线时间(分钟)
	 * @param totalMinute 累计在线时间(分钟)
	 */
	public void sendBasicPlayerLog(Human human,
				LogReasons.BasicPlayerLogReason reason,				String param			,String ip
			,int exp
			,int minute
			,int totalMinute
		) {
		if(!loggableSwitcher.isBasicPlayerLoggable()){return;}
		udpLoggerClient.sendMessage(new BasicPlayerLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,ip
			,exp
			,minute
			,totalMinute
		));
	}

	/**
	 * 发送在线奖励日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param reward_id 在线奖励id
	 */
	public void sendOnlineTimeRewardLog(Human human,
				LogReasons.OnlineTimeRewardLogReason reason,				String param			,int reward_id
		) {
		if(!loggableSwitcher.isOnlineTimeRewardLoggable()){return;}
		udpLoggerClient.sendMessage(new OnlineTimeRewardLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,reward_id
		));
	}

	/**
	 * 发送在线时间
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param minute 当天累计在线时间(分钟)
	 * @param totalMinute 累计在线时间(分钟)
	 * @param lastLoginTime 最后一次登录时间
	 * @param lastLogoutTime 最后一次登出时间
	 */
	public void sendOnlineTimeLog(Human human,
				LogReasons.OnlineTimeLogReason reason,				String param			,int minute
			,int totalMinute
			,long lastLoginTime
			,long lastLogoutTime
		) {
		if(!loggableSwitcher.isOnlineTimeLoggable()){return;}
		udpLoggerClient.sendMessage(new OnlineTimeLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,minute
			,totalMinute
			,lastLoginTime
			,lastLogoutTime
		));
	}

	/**
	 * 发送每日任务日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param tId 任务id
	 * @param finished 完成度
	 * @param getNums 领取次数
	 */
	public void sendDailyTaskLog(Human human,
				LogReasons.DailyTaskLogReason reason,				String param			,int tId
			,int finished
			,int getNums
		) {
		if(!loggableSwitcher.isDailyTaskLoggable()){return;}
		udpLoggerClient.sendMessage(new DailyTaskLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,tId
			,finished
			,getNums
		));
	}

	/**
	 * 发送异常日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param shortMessage 短信息
	 * @param fullMessage 长信息
	 * @param className 类名
	 * @param methodName 方法名
	 */
	public void sendExceptionLog(Human human,
				LogReasons.ExceptionLogReason reason,				String param			,String shortMessage
			,String fullMessage
			,String className
			,String methodName
		) {
		if(!loggableSwitcher.isExceptionLoggable()){return;}
		udpLoggerClient.sendMessage(new ExceptionLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,shortMessage
			,fullMessage
			,className
			,methodName
		));
	}

	public void sendExceptionLog(String param, Throwable e){
		sendExceptionLog(null, LogReasons.ExceptionLogReason.DEFAULT_ERROR, param, e); 
	}
	public void sendExceptionLog(Human human, String param, Throwable e){
		sendExceptionLog(human, LogReasons.ExceptionLogReason.DEFAULT_ERROR, param, e); 
	}
	/**
	 * 获取错误信息
	 */
	private String getExceptionStackTrace(Throwable e, String newLine){
		StringBuffer traceMessage = new StringBuffer();
		if(e == null){
			return null;
		}
		StackTraceElement[] stes = e.getStackTrace();
		if(stes != null){
			for (StackTraceElement s : stes) {
				traceMessage.append(s.toString() + newLine);
			}
		}
		return traceMessage.toString();
	}
	public void sendExceptionLog(Human human,
								LogReasons.ExceptionLogReason reason,
								String param,
								Throwable e){
		if(!loggableSwitcher.isExceptionLoggable()){ return; }
		
		try {
			if(e != null){
				String shortMessage = e.getMessage();
				String fullMessage = getExceptionStackTrace(e,"<br>");
				String className = "";
				String methodName = "";
				{
					StackTraceElement[] stes = e.getStackTrace();
					if(stes != null && stes.length != 0){
						className = stes[0].getClassName();
						methodName = stes[0].getMethodName();
					}
				}
				sendExceptionLog(human, reason, param, shortMessage, fullMessage, className, methodName);
			}
		} catch (Exception e1) {
			// do nothing ...
		}
	}	
	/**
	 * 发送魅力日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param charmDelta 变化
	 * @param charmLeft 剩余
	 */
	public void sendCharmLog(Human human,
				LogReasons.CharmLogReason reason,				String param			,long charmDelta
			,long charmLeft
		) {
		if(!loggableSwitcher.isCharmLoggable()){return;}
		udpLoggerClient.sendMessage(new CharmLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,charmDelta
			,charmLeft
		));
	}

	/**
	 * 发送好友日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param friendId 好友id
	 */
	public void sendFriendLog(Human human,
				LogReasons.FriendLogReason reason,				String param			,long friendId
		) {
		if(!loggableSwitcher.isFriendLoggable()){return;}
		udpLoggerClient.sendMessage(new FriendLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,friendId
		));
	}

	/**
	 * 发送道具日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param templateId 道具模板ID
	 * @param delta 变化值
	 * @param resultCount 数量
	 */
	public void sendItemLog(Human human,
				LogReasons.ItemLogReason reason,				String param			,int templateId
			,int delta
			,int resultCount
		) {
		if(!loggableSwitcher.isItemLoggable()){return;}
		udpLoggerClient.sendMessage(new ItemLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,templateId
			,delta
			,resultCount
		));
	}

	/**
	 * 发送改名日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param beforeName 改名前
	 * @param afterName 改名后
	 */
	public void sendRenameLog(Human human,
				LogReasons.RenameLogReason reason,				String param			,String beforeName
			,String afterName
		) {
		if(!loggableSwitcher.isRenameLoggable()){return;}
		udpLoggerClient.sendMessage(new RenameLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,beforeName
			,afterName
		));
	}

	/**
	 * 发送房间日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param roomId 房间号
	 * @param modeId 模式id
	 * @param typeId 类型id
	 */
	public void sendTexasRoomLog(Human human,
				LogReasons.TexasRoomLogReason reason,				String param			,long roomId
			,long modeId
			,long typeId
		) {
		if(!loggableSwitcher.isTexasRoomLoggable()){return;}
		udpLoggerClient.sendMessage(new TexasRoomLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,roomId
			,modeId
			,typeId
		));
	}

	/**
	 * 发送百家乐房间日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param roomId 房间号
	 */
	public void sendBaccaratRoomLog(Human human,
				LogReasons.BaccaratRoomLogReason reason,				String param			,long roomId
		) {
		if(!loggableSwitcher.isBaccaratRoomLoggable()){return;}
		udpLoggerClient.sendMessage(new BaccaratRoomLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,roomId
		));
	}

	/**
	 * 发送幸运转盘日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param free 是否免费
	 * @param spinId spin档次
	 */
	public void sendLuckySpinLog(Human human,
				LogReasons.LuckySpinLogReason reason,				String param			,int free
			,int spinId
		) {
		if(!loggableSwitcher.isLuckySpinLoggable()){return;}
		udpLoggerClient.sendMessage(new LuckySpinLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,free
			,spinId
		));
	}

	/**
	 * 发送老虎机日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param slotId 
	 * @param slotType 
	 * @param bet 
	 */
	public void sendSlotLog(Human human,
				LogReasons.SlotLogReason reason,				String param			,long slotId
			,int slotType
			,int bet
		) {
		if(!loggableSwitcher.isSlotLoggable()){return;}
		udpLoggerClient.sendMessage(new SlotLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,slotId
			,slotType
			,bet
		));
	}

	/**
	 * 发送数据总览
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param value 数量
	 * @param age 年龄
	 * @param ipCountries 国家
	 * @param girl 1 是男，0是女
	 */
	public void sendDataOverviewLog(Human human,
				LogReasons.DataOverviewLogReason reason,				String param			,long value
			,int age
			,String ipCountries
			,int girl
		) {
		if(!loggableSwitcher.isDataOverviewLoggable()){return;}
		udpLoggerClient.sendMessage(new DataOverviewLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,value
			,age
			,ipCountries
			,girl
		));
	}

	/**
	 * 发送充值新日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param money 充值钱数
	 * @param timeDian 几点
	 * @param channelType 渠道
	 * @param deviceMode 手机类型
	 * @param clientVersion 版本号
	 * @param gameOrderId 订单号
	 * @param countries 国家
	 * @param age 年龄
	 * @param ipCountries 国家
	 * @param girl 1 是男，0是女
	 */
	public void sendNewRechargeLog(Human human,
				LogReasons.NewRechargeLogReason reason,				String param			,long money
			,int timeDian
			,int channelType
			,String deviceMode
			,String clientVersion
			,String gameOrderId
			,String countries
			,int age
			,String ipCountries
			,int girl
		) {
		if(!loggableSwitcher.isNewRechargeLoggable()){return;}
		udpLoggerClient.sendMessage(new NewRechargeLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,money
			,timeDian
			,channelType
			,deviceMode
			,clientVersion
			,gameOrderId
			,countries
			,age
			,ipCountries
			,girl
		));
	}

	/**
	 * 发送玩家登陆
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param channelType 渠道
	 * @param deviceMode 设备类型
	 * @param clientVersion 版本号
	 * @param countries 国家
	 * @param ipCountries 用户IP对应的国家
	 */
	public void sendPlayerLoginLog(Human human,
				LogReasons.PlayerLoginLogReason reason,				String param			,int channelType
			,String deviceMode
			,String clientVersion
			,String countries
			,String ipCountries
		) {
		if(!loggableSwitcher.isPlayerLoginLoggable()){return;}
		udpLoggerClient.sendMessage(new PlayerLoginLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,channelType
			,deviceMode
			,clientVersion
			,countries
			,ipCountries
		));
	}

	/**
	 * 发送玩家在线统计
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param timeDian 几点
	 * @param valueRole 当前在线人数
	 * @param countries 国家
	 * @param ipCountries 用户IP对应的国家
	 */
	public void sendPlayerOnleLog(Human human,
				LogReasons.PlayerOnleLogReason reason,				String param			,int timeDian
			,int valueRole
			,String countries
			,String ipCountries
		) {
		if(!loggableSwitcher.isPlayerOnleLoggable()){return;}
		udpLoggerClient.sendMessage(new PlayerOnleLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,timeDian
			,valueRole
			,countries
			,ipCountries
		));
	}

	/**
	 * 发送留存统计
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param loginDay 第几天登陆
	 * @param countries 国家
	 * @param ipCountries 用户IP对应的国家
	 * @param channelType 渠道类型
	 * @param deviceMode 设备的类型
	 * @param clientVersion 版本
	 */
	public void sendPlayerKeepLog(Human human,
				LogReasons.PlayerKeepLogReason reason,				String param			,int loginDay
			,String countries
			,String ipCountries
			,int channelType
			,String deviceMode
			,String clientVersion
		) {
		if(!loggableSwitcher.isPlayerKeepLoggable()){return;}
		udpLoggerClient.sendMessage(new PlayerKeepLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,loginDay
			,countries
			,ipCountries
			,channelType
			,deviceMode
			,clientVersion
		));
	}

	/**
	 * 发送老虎机房间快照
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param room_id 老虎机ID
	 */
	public void sendSlotRoomLog(Human human,
				LogReasons.SlotRoomLogReason reason,				String param			,long room_id
		) {
		if(!loggableSwitcher.isSlotRoomLoggable()){return;}
		udpLoggerClient.sendMessage(new SlotRoomLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,room_id
		));
	}

	/**
	 * 发送用户进入退出老虎机的时间
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param inTime 进入老虎机的时间
	 * @param slotType 区分老虎机 1、初级，2、中级、3、高级、4、精英
	 * @param outTime 退出老虎机的时间
	 * @param inOutTotalTime 退出时间 减去 进入时间的差值（一次得）
	 * @param charId 用户ID
	 * @param slotId 老虎机ID
	 */
	public void sendInOutTimeLog(Human human,
				LogReasons.InOutTimeLogReason reason,				String param			,long inTime
			,int slotType
			,long outTime
			,long inOutTotalTime
			,long charId
			,long slotId
		) {
		if(!loggableSwitcher.isInOutTimeLoggable()){return;}
		udpLoggerClient.sendMessage(new InOutTimeLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,inTime
			,slotType
			,outTime
			,inOutTotalTime
			,charId
			,slotId
		));
	}

	/**
	 * 发送新的彩金的日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param slotName 老虎机名称
	 * @param slotType 老虎机类型
	 * @param slotId 老虎机ID
	 * @param bet 当前的下注
	 * @param jackType 类型 1 变化前，2变化后
	 * @param jackpotNum 哪个倍数的
	 * @param cumuJackpotNum 哪个倍数的
	 * @param jackpot 当前jackpot
	 * @param cumuJackpot 当前cumuJackpot
	 * @param afterChangeJackpot 变化后的jackpot
	 * @param afterChangeCumuJackpot 变化后的cumuJackpot
	 * @param rewardPer 当前用户获取到的彩金
	 */
	public void sendJackpotLog(Human human,
				LogReasons.JackpotLogReason reason,				String param			,String slotName
			,int slotType
			,int slotId
			,int bet
			,int jackType
			,String jackpotNum
			,String cumuJackpotNum
			,long jackpot
			,long cumuJackpot
			,long afterChangeJackpot
			,long afterChangeCumuJackpot
			,long rewardPer
		) {
		if(!loggableSwitcher.isJackpotLoggable()){return;}
		udpLoggerClient.sendMessage(new JackpotLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,slotName
			,slotType
			,slotId
			,bet
			,jackType
			,jackpotNum
			,cumuJackpotNum
			,jackpot
			,cumuJackpot
			,afterChangeJackpot
			,afterChangeCumuJackpot
			,rewardPer
		));
	}

	/**
	 * 发送竞赛日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param tournamentId 竞赛的ID 唯一
	 * @param tournamentType 竞赛的类型
	 * @param slotType 老虎机的类型
	 * @param totalReward 每个竞赛累计的 总金币
	 * @param userId 当前用户ID,如果没有就是0
	 * @param rewards 发奖时 奖池中的总金币
	 * @param reward 发奖时 每个人的比例
	 * @param obtainedReward 发奖时 用户最终获得的钱
	 */
	public void sendTournamentLog(Human human,
				LogReasons.TournamentLogReason reason,				String param			,long tournamentId
			,int tournamentType
			,int slotType
			,long totalReward
			,long userId
			,long rewards
			,long reward
			,long obtainedReward
		) {
		if(!loggableSwitcher.isTournamentLoggable()){return;}
		udpLoggerClient.sendMessage(new TournamentLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,tournamentId
			,tournamentType
			,slotType
			,totalReward
			,userId
			,rewards
			,reward
			,obtainedReward
		));
	}

	/**
	 * 发送世界boss日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bossType boss的类型
	 * @param starttime boss开启的时间
	 * @param beginEnd boss开始或者结束 1 开始，2结束
	 * @param bloodBeginEnd 回血的开始或者结束 1开始，2结束
	 * @param curAttackBlood 用户攻击的血量
	 * @param bossId boss的Id
	 */
	public void sendWorldBossLog(Human human,
				LogReasons.WorldBossLogReason reason,				String param			,int bossType
			,String starttime
			,int beginEnd
			,int bloodBeginEnd
			,long curAttackBlood
			,long bossId
		) {
		if(!loggableSwitcher.isWorldBossLoggable()){return;}
		udpLoggerClient.sendMessage(new WorldBossLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bossType
			,starttime
			,beginEnd
			,bloodBeginEnd
			,curAttackBlood
			,bossId
		));
	}

	/**
	 * 发送老虎机主转的时间统计
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param userId 用户
	 * @param slotType 老虎机
	 * @param slotListPosition 在这个slotType类型下 的哪个老虎机
	 * @param bet 下注额
	 * @param slotStartBeforeTime 老虎机主转中 转动老虎机之前
	 * @param slotStartBeforeAfter 每个老虎机主转 时间（包括小游戏）
	 * @param achievementTournamentStartEnd 成就和竞赛 时间
	 * @param humanJackpotBroadcastNoticeStartEnd 全服广播（jackpot）
	 * @param activityBeforeEnd 所有活动 的时间
	 * @param taskCollectBeforeEnd 收集和 每日任务
	 * @param treasuryStartEnd 小金猪
	 * @param HuoYueForLaohujiWinEnd 俱乐部
	 * @param WorldBossNewBeforeEnd 世界boss
	 * @param totalTime 整个老虎机所需的时间
	 */
	public void sendStatisticsTimeLog(Human human,
				LogReasons.StatisticsTimeLogReason reason,				String param			,long userId
			,long slotType
			,long slotListPosition
			,long bet
			,long slotStartBeforeTime
			,long slotStartBeforeAfter
			,long achievementTournamentStartEnd
			,long humanJackpotBroadcastNoticeStartEnd
			,long activityBeforeEnd
			,long taskCollectBeforeEnd
			,long treasuryStartEnd
			,long HuoYueForLaohujiWinEnd
			,long WorldBossNewBeforeEnd
			,long totalTime
		) {
		if(!loggableSwitcher.isStatisticsTimeLoggable()){return;}
		udpLoggerClient.sendMessage(new StatisticsTimeLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,userId
			,slotType
			,slotListPosition
			,bet
			,slotStartBeforeTime
			,slotStartBeforeAfter
			,achievementTournamentStartEnd
			,humanJackpotBroadcastNoticeStartEnd
			,activityBeforeEnd
			,taskCollectBeforeEnd
			,treasuryStartEnd
			,HuoYueForLaohujiWinEnd
			,WorldBossNewBeforeEnd
			,totalTime
		));
	}

	/**
	 * 发送无双吹牛-房间信息-创建-进入-退出
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param roomStatus 房间类型 0:创建，1:进入，2:退出房间
	 * @param totalNum 房间总人数
	 * @param everyIds 每个人的ID用 "," 分割
	 */
	public void sendDiceClassicalRoomLog(Human human,
				LogReasons.DiceClassicalRoomLogReason reason,				String param			,int bet
			,String roomNum
			,int roomStatus
			,int totalNum
			,String everyIds
		) {
		if(!loggableSwitcher.isDiceClassicalRoomLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceClassicalRoomLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,roomStatus
			,totalNum
			,everyIds
		));
	}

	/**
	 * 发送无双吹牛-竞猜信息
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param bigSmall 用户猜的 1 还是 0 -- 这个是前端传过来的值
	 * @param isRight 用户猜的是对1  还是错0
	 * @param endCount 这个结算 主要是 让 每一轮 和每一轮 之间 有 个分界线
	 */
	public void sendDiceClassicalGuessLog(Human human,
				LogReasons.DiceClassicalGuessLogReason reason,				String param			,int bet
			,String roomNum
			,int bigSmall
			,int isRight
			,String endCount
		) {
		if(!loggableSwitcher.isDiceClassicalGuessLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceClassicalGuessLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,bigSmall
			,isRight
			,endCount
		));
	}

	/**
	 * 发送无双吹牛-叫号
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param numValue 当前 叫号几个几, 用"-"分割
	 * @param endCount 这个结算 主要是 让 每一轮 和每一轮 之间 有 个分界线
	 */
	public void sendDiceClassicalCallNumLog(Human human,
				LogReasons.DiceClassicalCallNumLogReason reason,				String param			,int bet
			,String roomNum
			,String numValue
			,String endCount
		) {
		if(!loggableSwitcher.isDiceClassicalCallNumLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceClassicalCallNumLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,numValue
			,endCount
		));
	}

	/**
	 * 发送无双吹牛-金币走向
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param goldType 金币变更类型
	 * @param goldReason 金币类型reason 描述
	 * @param goldChangeBefore 金币变更前
	 * @param goldChange 金币变更额度
	 * @param goldChangeAfter 金币变更额度后
	 * @param endCount 这个结算 主要是 让 每一轮 和每一轮 之间 有 个分界线
	 */
	public void sendDiceClassicalBazooGoldLog(Human human,
				LogReasons.DiceClassicalBazooGoldLogReason reason,				String param			,int bet
			,String roomNum
			,int goldType
			,String goldReason
			,long goldChangeBefore
			,long goldChange
			,long goldChangeAfter
			,String endCount
		) {
		if(!loggableSwitcher.isDiceClassicalBazooGoldLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceClassicalBazooGoldLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,goldType
			,goldReason
			,goldChangeBefore
			,goldChange
			,goldChangeAfter
			,endCount
		));
	}

	/**
	 * 发送无双吹牛-牛牛模式-色子值
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param diceValues 色子值
	 * @param diceName 几小牛-num值
	 */
	public void sendDiceCowLog(Human human,
				LogReasons.DiceCowLogReason reason,				String param			,int bet
			,String roomNum
			,String diceValues
			,String diceName
		) {
		if(!loggableSwitcher.isDiceCowLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceCowLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,diceValues
			,diceName
		));
	}

	/**
	 * 发送无双吹牛-梭哈模式-色子值
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param diceValues 色子值
	 * @param diceName 几小牛-num值
	 */
	public void sendDiceShowHandLog(Human human,
				LogReasons.DiceShowHandLogReason reason,				String param			,int bet
			,String roomNum
			,String diceValues
			,String diceName
		) {
		if(!loggableSwitcher.isDiceShowHandLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceShowHandLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,diceValues
			,diceName
		));
	}

	/**
	 * 发送无双吹牛-签到-色子值
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param diceValues 色子值
	 * @param diceName 几小牛-num值
	 */
	public void sendDiceSignInLog(Human human,
				LogReasons.DiceSignInLogReason reason,				String param			,int bet
			,String roomNum
			,String diceValues
			,String diceName
		) {
		if(!loggableSwitcher.isDiceSignInLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceSignInLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,diceValues
			,diceName
		));
	}

	/**
	 * 发送无双吹牛-签到-色子值
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param diceValues 色子值
	 * @param isOut 是否出局
	 * @param totalGold 用户当前总金币数量
	 */
	public void sendDiceBlackWhiteLog(Human human,
				LogReasons.DiceBlackWhiteLogReason reason,				String param			,int bet
			,String roomNum
			,String diceValues
			,int isOut
			,long totalGold
		) {
		if(!loggableSwitcher.isDiceBlackWhiteLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceBlackWhiteLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,diceValues
			,isOut
			,totalGold
		));
	}

	/**
	 * 发送无双吹牛-统计每一把输赢（所有人包括机器人）
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param level 玩家等级
	 * @param reason 原因
	 * @param param 其他参数
	 * @param bet 房间倍数
	 * @param roomNum 房间号
	 * @param isRobot 是否是机器人 1：是，0：否
	 * @param modeType 房间类型
	 * @param winOrLost 1：赢 ，0：输
	 */
	public void sendDiceStatisticsWinLostLog(Human human,
				LogReasons.DiceStatisticsWinLostLogReason reason,				String param			,int bet
			,String roomNum
			,int isRobot
			,int modeType
			,int winOrLost
		) {
		if(!loggableSwitcher.isDiceStatisticsWinLostLoggable()){return;}
		udpLoggerClient.sendMessage(new DiceStatisticsWinLostLog(
			
				Globals.getTimeService().now(),			
				this.regionID,			
				this.serverID,			
				(human == null ? -1 : human.getPassportId()),			
				(human == null ? "-1" : human.getName()),			
				(human == null ? -1 : human.getLevel()),			
				reason.reason,			
				param			
			,bet
			,roomNum
			,isRobot
			,modeType
			,winOrLost
		));
	}

}