package com.robot.bazoo;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.bazoo.data.BetTotalNum;
import com.gameserver.bazoo.data.ReturnPlayerInfo;
import com.gameserver.bazoo.msg.CGRoomEnter;
import com.gameserver.bazoo.msg.CGRoomOut;
import com.gameserver.bazoo.msg.CGRoomPriJoin;
import com.gameserver.bazoo.msg.CGRoomPubJoin;
import com.gameserver.bazoo.msg.GCModeChose;
import com.gameserver.bazoo.msg.GCRobotWhichRoomToGoin;
import com.gameserver.bazoo.msg.GCRoomInit;
import com.gameserver.bazoo.msg.GCRoomMatched;
import com.gameserver.bazoo.msg.GCRoomOut;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.robot.Robot;

public class BazooMessageHandler {

	protected Logger logger = Loggers.BAZOO;
	public void execute(Robot robot, IMessage message) {
		
		
		
		/**
		 * 告诉机器人 进入哪个房间
		 */
		if(message instanceof GCRobotWhichRoomToGoin){
			executeGCRobotWhichRoomToGoin(robot,(GCRobotWhichRoomToGoin)message);
		}
		
		/**
		 * 返回模式
		 */
		if(message instanceof GCModeChose){
			executeGCModeChose(robot,(GCModeChose)message);
		}
		
		/**
		 * 进入房间 返回
		 */
		if(message instanceof GCRoomInit){
			executeGCRoomInit(robot,(GCRoomInit)message);
		}
		
		
		/**
		 * 进入房间 返回  匹配上的消息
		 */
		if(message instanceof GCRoomMatched){
			executeGCRoomMatched(robot,(GCRoomMatched)message);
		}
		
		/**
		 * 退出房间
		 */
		if(message instanceof GCRoomOut){
			executeGCRoomOut(robot,(GCRoomOut)message);
		}
		
		
	
		
		
		
		
		
	}
	


	/**
	 * 告诉机器人 进入哪个房间
	 * @param robot
	 * @param message
	 */
	private void executeGCRobotWhichRoomToGoin(Robot robot,
			GCRobotWhichRoomToGoin message) {
		try{
			Thread.sleep(1000);
		}catch(Exception e){
		
		}
		if(Long.valueOf(robot.getPassportId()).longValue() != message.getPassportId()){
			return;
		}
		logger.info("[无双吹牛]---[主动开启机器人]---当前用户的ID:"+robot.getPassportId()+"[message.getPassportId():"+message.getPassportId()+"]");
		String roomNumber = message.getRoomNumber();
		if(roomNumber.split("_")[1].equals(""+RoomNumber.PUB_ROOM)){//公共 房间
			CGRoomPubJoin CGRoomPubJoin = new CGRoomPubJoin();
			CGRoomPubJoin.setRoomNumber(roomNumber);
			robot.sendMessage(CGRoomPubJoin);
		}else{//私人房间
			CGRoomPriJoin CGRoomPriJoin = new CGRoomPriJoin();
			CGRoomPriJoin.setRoomNumber(roomNumber);
			robot.sendMessage(CGRoomPriJoin);
		}
	}



	private void executeGCRoomMatched(Robot robot, GCRoomMatched message) {
		int waitTime = message.getWaitTime();
		logger.info("[无双吹牛]---[匹配上]---[当前用户ID::"+robot.getPassportId()+"]---[等待时间::"+waitTime+"]");
		
	}

	/**
	 * 选择模式后  去进入房间
	 * @param robot
	 * @param GCModeChose
	 */
	private void executeGCModeChose(Robot robot,GCModeChose GCModeChose){
		BetTotalNum[] BetTotalNumArr = GCModeChose.getBetTotalNum();
		for(int i=0;i<BetTotalNumArr.length;i++){
			BetTotalNum BetTotalNum = BetTotalNumArr[i];
			logger.info("[无双吹牛]---[模式场次]---[当前bet::"+BetTotalNum.getBet()+"]---[当前总人数::"+BetTotalNum.getTotalNum()+"]");
		}
		
		//AllClassicalCowShowHandMain   RobotBazooClassicalMain  RobotBazooCowMain  RobotBazooShowHandMain 调用
		if(robot.getBazooTemp().getWayOfType().equals("normal")){
			CGRoomEnter CGRoomEnter =  new CGRoomEnter();
			CGRoomEnter.setBet(robot.getBazooTemp().getBet());
			robot.sendMessage(CGRoomEnter);
		}else{//RobotBazooClassicalRemote 这个调用
			//根据房间号 决定要去哪个 房间
			CGRoomPubJoin CGRoomPubJoin = new CGRoomPubJoin();
			CGRoomPubJoin.setRoomNumber(robot.getBazooTemp().getData().getRoomNumber());
			robot.sendMessage(CGRoomPubJoin);
		}
		
	}

	/**
	 * 进入房间 返回值
	 * @param GCRoomEnter
	 */
	private void executeGCRoomInit(Robot robot,GCRoomInit GCRoomInit){
		logger.info("[无双吹牛]---[进入房间]---[当前房间号:"+GCRoomInit.getRoomNum()+"]---[用户ID::"+robot.getPassportId()+" 的人进入房间]");
		/*int status = GCRoomInit.getStatus();
		if(status == 1){
			logger.info("[无双吹牛]---[房间状态]---[进行中... ...]");
		}else{
			logger.info("[无双吹牛]---[房间状态]---[未开始... ...]");
		}*/
		
		logger.info("[无双吹牛]---[房间参数1]---[PubOrPri::"+GCRoomInit.getPubOrPri()+"]");
		logger.info("[无双吹牛]---[房间参数2]---[LastPassportId::"+GCRoomInit.getLastPassportId()+"]");
		logger.info("[无双吹牛]---[房间参数3]---[OnePoint::"+GCRoomInit.getOnePoint()+"]");
		logger.info("[无双吹牛]---[房间参数4]---[GuessWait::"+GCRoomInit.getGuessWait()+"]");
		logger.info("[无双吹牛]---[房间参数5]---[WaitTime::"+GCRoomInit.getWaitTime()+"]");
		logger.info("[无双吹牛]---[房间参数6]---[TurnWait::"+GCRoomInit.getTurnWait()+"]");
		
		
		ReturnPlayerInfo[] allPlayerInfo = GCRoomInit.getReturnPlayerInfo();
		for(int i=0;i<allPlayerInfo.length;i++){
			ReturnPlayerInfo oInfo= allPlayerInfo[i];
			logger.info("[无双吹牛]---当前其他用户的ID:"+oInfo.getPassportId()+"---当前其他用户的名称:"+oInfo.getName()+"---"+oInfo.getCurGold()+"-"+oInfo.getHeadImg()+"-"+oInfo.getDiceContainer());
		}
	}
	
	/**
	 * 退出房间
	 * @param robot
	 */
	private void executeCGRoomOut(Robot robot) {
		CGRoomOut CGRoomOut =  new CGRoomOut();
		CGRoomOut.setBet(1000);
		robot.sendMessage(CGRoomOut);
		logger.info("[无双吹牛]---当前用户的ID:"+robot.getPassportId()+"---退出房间");
		
	}
	
	
	/**
	 * 房间里别的用户收到退出房间的信息
	 * @param message
	 */
	private void executeGCRoomOut(Robot robot,GCRoomOut message) {
		logger.info("[无双吹牛]---当前用户的ID:"+robot.getPassportId()+"---那个谁：："+message.getBeRemovedPassportIds()+"---退出了");
	}
	
	
}
