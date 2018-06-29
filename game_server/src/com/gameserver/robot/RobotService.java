package com.gameserver.robot;


import java.text.MessageFormat;

import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.uuid.UUIDType;
import com.db.model.RobotComplementEntity;
import com.game.webserver.robot.RobotExitRequestRes;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.player.Player;
import com.gameserver.player.enums.PlayerRoleEnum;
import com.gameserver.recharge.async.AsyncHttpOperation.IAsyncHttpCallBack;
import com.gameserver.robot.async.AsyncPRCRobotRequestOperation;
import com.gameserver.robot.async.IAsyncPRCCallBack;


/**
 * 机器人服务
 * @author wayne
 *
 */
public class RobotService  implements InitializeRequired,AfterInitializeRequired {
	
	private Logger logger = Loggers.robotLogger;
	
	private static final long ROBOT_MIN_GOLD = 1500000;
	private static final long ROBOT_MAX_GOLD = 2000000;
	
	private RobotManager robotManager = RobotManager.getInstance();
	
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {

	}

	public RobotManager getRobotManager() {
		return robotManager;
	}

	public void setRobotManager(RobotManager robotManager) {
		this.robotManager = robotManager;
	}
	
	/**
	 * 机器人加入请求
	 * @param rId
	 */
	public void requestRobotJoin(long rId){
		logger.info("德州普通房间["+rId+"]请求机器人");

		AsyncPRCRobotRequestOperation<Long> asyncPRCRobotRequestOperation  = new AsyncPRCRobotRequestOperation<Long>(Globals.getServerConfig().getServerId(), rId,new RobotRequestJoinCallBack());
		Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(asyncPRCRobotRequestOperation);

	}
	
	/**
	 * 机器人加入
	 */
	public void robotJoin(Player player){
		
		if(player.getPlayerRoleEnum() != PlayerRoleEnum.ROBOT){
			return;
		}
		
		logger.info("机器人["+player.getPassportId()+"]加入");
		
		if(player.getHuman().getGold() < ROBOT_MIN_GOLD){
			long giveMoney = ROBOT_MAX_GOLD - player.getHuman().getGold();
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.ROBOT_GIVE.getReasonText(), giveMoney);
			player.getHuman().giveMoney(giveMoney, Currency.GOLD, true, LogReasons.GoldLogReason.ROBOT_GIVE, detailReason, -1, 1);	
			
			RobotComplementEntity robotComplementEntity=new RobotComplementEntity();
			robotComplementEntity.setComplement(giveMoney);
			robotComplementEntity.setRobotId(player.getPassportId());
			
			long now = Globals.getTimeService().now();
			long robotComplementId=Globals.getUUIDService().getNextUUID(now,UUIDType.ROBOTCOMPLEMENTID);
			robotComplementEntity.setId(robotComplementId);
			robotComplementEntity.setCreateTime(now);
			robotComplementEntity.setUpdateTime(now);
			
			Globals.getDaoService().getRobotComplementDao().save(robotComplementEntity);
		}
		
		robotManager.addRobot(player);
	}
	
	/**
	 * 机器人退出
	 */
	public void robotExit(Player player){

		if(player.getPlayerRoleEnum() != PlayerRoleEnum.ROBOT){
			return;
		}
		logger.info("机器人["+player.getPassportId()+"]退出");
		
		robotManager.removeRobot(player);
	}
	
	/**
	 * -------------------------call back---------------------------------
	*/
	public class RobotRequestJoinCallBack implements IAsyncPRCCallBack<Long> {

		@Override
		public void onFinished(Long res) {
			// TODO Auto-generated method stub
			if(res==null || res==0){
				logger.info("请求加入机器人失败");
				return;
			}
			logger.info("请求加入机器人成功,机器人id["+res+"]");
		}
		
	}
	
	public class RobotExitRequestCallBack implements IAsyncHttpCallBack<RobotExitRequestRes> {

		@Override
		public void onFinished(Player player, int errorCode,
				RobotExitRequestRes res) {
			// TODO Auto-generated method stub
			if(errorCode != 0){
				logger.warn("请求退出机器人失败,失败代码["+errorCode+"]");
			}else{
				logger.info("请求退出机器人成功,机器人id["+res.getRobotId()+"]");
			}
		}
		
	}
}
