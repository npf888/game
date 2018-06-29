package com.common.constants;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 统一定义系统使用的slf4j的Logger
 * @author Thinker
 * 
 */
public final class Loggers
{
	/**错误级别*/
	public static final int ERROR=0;
	/**警告级别*/
	public static final int WARN=1;
	/**信息级别*/
	public static final int INFO=2;
	/**调试级别*/
	public static final int DEBUG=3;
	/**跟踪级别*/
	public static final int TRACE=4;
	
	
	
	////////////////////////////////系统级别输出日志////////////////////////////////////////////////////
	/** Server相关的日志 */
	public static final Logger serverLogger = LoggerFactory.getLogger("texas.server");
	/** Game Server相关的日志 */
	public static final Logger gameLogger = LoggerFactory.getLogger("texas.game");
	/** 登录相关的日志 */
	public static final Logger loginLogger = LoggerFactory.getLogger("texas.login");
	/** 异常相关的日志 */
	public static final Logger errorLogger = LoggerFactory.getLogger("texas.error");
	/** 数据库相关的日志 */
	public static final Logger dbLogger = LoggerFactory.getLogger("texas.db");
	/** reids的日志 */
	public static final Logger redisLogger = LoggerFactory.getLogger("texas.redis");
	/**rpc日志*/
	public static final Logger rpcLogger = LoggerFactory.getLogger("texas.rpc");
	/** 排行版的日志 */
	public static final Logger rankLogger = LoggerFactory.getLogger("texas.rank");


	/** 消息处理相关的日志 */
	public static final Logger msgLogger = LoggerFactory.getLogger("texas.msg");
	/** 玩家相关的日志 */
	public static final Logger playerLogger = LoggerFactory.getLogger("texas.player");
	/** 定时器的日志 */
	public static final Logger scheduleLogger = LoggerFactory.getLogger("texas.schedule");

	////////////////////////////////单独模块输出日志////////////////////////////////////////////////////
	/** 模板相关的日志 */
	public static final Logger templateLogger = LoggerFactory.getLogger("texas.template");
	/** GM命令相关的日志 */
	public static final Logger gmcmdLogger = LoggerFactory.getLogger("texas.gmcmd");
	
	/** 角色相关的日志 */
	public static final Logger humanLogger = LoggerFactory.getLogger("texas.human");
	/** 场景相关的日志 */
	public static final Logger sceneLogger = LoggerFactory.getLogger("texas.scene");
	/** 状态相关的日志 */
	public static final Logger stateLogger = LoggerFactory.getLogger("texas.state");
	/** 数据更新的日志 */
	public static final Logger updateLogger = LoggerFactory.getLogger("texas.update");
	/** 德州背包的日志 */
	public static final Logger bagLogger = LoggerFactory.getLogger("texas.bag");
	/** 德州背包的日志 */
	public static final Logger itemLogger = LoggerFactory.getLogger("texas.item");
	/** 德州商城的日志 */
	public static final Logger shopLogger = LoggerFactory.getLogger("texas.shop");
	/** 德州房间的日志 */
	public static final Logger texasRoomLogger = LoggerFactory.getLogger("texas.texasRoom");
	/** 德州月卡的日志 */
	public static final Logger monthCardLogger = LoggerFactory.getLogger("texas.monthCard");
	/** 德州周卡的日志 */
	public static final Logger weekCardLogger = LoggerFactory.getLogger("texas.weekCard");
	/** 邮件的日志 */
	public static final Logger mailLogger = LoggerFactory.getLogger("texas.mail");
	/**好友的日志 */
	public static final Logger relationLogger = LoggerFactory.getLogger("texas.relation");
	/**好友的redis日志 */
	public static final Logger relationRedisLogger = LoggerFactory.getLogger("texas.relationRedis");
	/**签到日志 */
	public static final Logger signInLogger = LoggerFactory.getLogger("texas.signIn");
	
	/**misc日志 */
	public static final Logger miscLogger = LoggerFactory.getLogger("texas.misc");
	/**vip日志 */
	public static final Logger vipLogger = LoggerFactory.getLogger("texas.vip");
	/**聊天日志 */
	public static final Logger chatLogger = LoggerFactory.getLogger("texas.chat");
	/**充值日志 */
	public static final Logger rechargeLogger = LoggerFactory.getLogger("texas.recharge");
	
	/**任务日志 */
	public static final Logger taskLogger = LoggerFactory.getLogger("texas.task");
	/**活动日志*/
	public static final Logger activityLogger = LoggerFactory.getLogger("texas.activity");
	
	/**卡牌日志*/
	public static final Logger texasCardLogger = LoggerFactory.getLogger("texas.card");
	
	/** 德州房间的日志 */
	public static final Logger clientTexasRoomLogger = LoggerFactory.getLogger("texas.clientTexasRoom");
	
	/**公告的日志*/
	public static final Logger noticeLogger = LoggerFactory.getLogger("texas.notice");
	/**sng*/
	public static final Logger sngLogger = LoggerFactory.getLogger("texas.sng");
	/**normal*/
	public static final Logger normalLogger = LoggerFactory.getLogger("texas.normal");
	
	/**vip room*/
	public static final Logger vipRoomLogger = LoggerFactory.getLogger("texas.vipRoom");
	
	
	/**compensation room*/
	public static final Logger compensationLogger = LoggerFactory.getLogger("texas.compensation");
	
	
	/**refund*/
	public static final Logger refundLogger = LoggerFactory.getLogger("texas.refund");
	
	/**robot*/
	public static final Logger robotLogger = LoggerFactory.getLogger("texas.robot");
	
	/** http相关的日志 */
	public static final Logger httpLogger = LoggerFactory.getLogger("texas.http");
	
	/**百家乐房间*/
	public static final Logger baccartRoomLogger = LoggerFactory.getLogger("baccart.baccartRoom");
	
	/**facebook 好友*/
	public static final Logger facebookLogger = LoggerFactory.getLogger("texas.facebook");
	
	/**luckyspin */
	public static final Logger luckyspinLogger = LoggerFactory.getLogger("texas.luckyspin");
	
	/**slot */
	public static final Logger slotLogger = LoggerFactory.getLogger("texas.slot");
	/**slotOther */
	public static final Logger slotOtherLogger = LoggerFactory.getLogger("texas.slotother");
	
	//=======================
	
	/**新排行榜 */
	public static final Logger RankNewServerLogger = LoggerFactory.getLogger("texas.rankNewServert");
	
	/**优惠礼包  */
	public static final Logger gfitLogger = LoggerFactory.getLogger("texas.gift");
	
	/**新排行榜 消息*/
	public static final Logger rankNewMessageLogger = LoggerFactory.getLogger("texas.rankNewMessage");
	
	/**成就 消息*/
	public static final Logger achievementLogger = LoggerFactory.getLogger("texas.achievement");
	
	/**mycard 充值信息*/
	public static final Logger mycardMessageLogger = LoggerFactory.getLogger("texas.mycardInfo");
	
	/**mycard 4.2 補儲流程:*/
	public static final Logger mycardcomesbackMessageLogger = LoggerFactory.getLogger("texas.mycardInfo.comesback");
	/**mycard 4.4 交易成功資料之差異比對*/
	public static final Logger mycardvalidationMessageLogger = LoggerFactory.getLogger("texas.mycardInfo.validation");
	
	/**亚马逊充值*/
	public static final Logger AMAZONINFO = LoggerFactory.getLogger("texas.amazon.verifyReceipt");
	
	/**MOL验证*/
	public static final Logger MOLVALIDATION = LoggerFactory.getLogger("texas.mol.molverifyReceipt");
	
	/**收集系统日志*/
	public static final Logger COLLECT = LoggerFactory.getLogger("texas.collect");
	
	/*世界boss系统日志*/
	public static final Logger WORLDBOSS = LoggerFactory.getLogger("texas.worldBoss");
	/*推送日志*/
	public static final Logger PUSH = LoggerFactory.getLogger("jpush");
	/*ip service*/
	public static final Logger IP_SERVICE = LoggerFactory.getLogger("ip_service");
	/*jackpot service*/
	public static final Logger JACKPOT = LoggerFactory.getLogger("jackpot");
	
	public static final Logger BAZOO = LoggerFactory.getLogger("bazoo");
	
	//==============================
	
	public static org.apache.log4j.Logger customLogger(String dir,String customName){

		org.apache.log4j.Logger customLogger =  org.apache.log4j.Logger.getLogger(customName);
		DailyRollingFileAppender appender = new DailyRollingFileAppender();
		appender.setName(customName);
		appender.setFile(dir+"/"+customName+".log");
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("[%d{yy/MM/dd HH:mm:ss,SSS}] --> %m%n");
		appender.setLayout(layout);
		appender.setDatePattern("'.'yyyy-MM-dd");
		appender.setEncoding("UTF-8");
		appender.setAppend(true);
		appender.activateOptions();
		customLogger.setAdditivity(true);
		customLogger.addAppender(appender);
		
		return customLogger;
	}
}
