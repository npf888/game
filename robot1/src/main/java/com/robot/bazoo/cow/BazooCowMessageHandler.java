package com.robot.bazoo.cow;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.bazoo.data.BetTotalNum;
import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.data.ReturnPlayerInfo;
import com.gameserver.bazoo.msg.CGBazooHeartBeat;
import com.gameserver.bazoo.msg.CGCowSingleSwing;
import com.gameserver.bazoo.msg.CGRoomEnter;
import com.gameserver.bazoo.msg.CGRoomOut;
import com.gameserver.bazoo.msg.GCBazooHeartBeat;
import com.gameserver.bazoo.msg.GCCowEndUnifySwing;
import com.gameserver.bazoo.msg.GCCowSingleSwingBegin;
import com.gameserver.bazoo.msg.GCCowSingleSwingEnd;
import com.gameserver.bazoo.msg.GCCowUnifySwing;
import com.gameserver.bazoo.msg.GCModeChose;
import com.gameserver.bazoo.msg.GCRoomInit;
import com.gameserver.bazoo.msg.GCRoomMatched;
import com.gameserver.bazoo.msg.GCRoomOut;
import com.robot.Robot;

public class BazooCowMessageHandler {

	protected Logger logger = Loggers.BAZOO;
	
	private int[] diceValue =null;
	public void execute(Robot robot, IMessage message) {
		if(message instanceof GCBazooHeartBeat){
			executeCGBazooHeartBeat(robot,(GCBazooHeartBeat)message);
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
		
		//统一摇色子  和 单独 摇色子 返回
		if(message instanceof GCCowUnifySwing){//匹配完 由服务器 统一摇色子
			executeGCCowUnifySwing(robot,(GCCowUnifySwing)message);
		}else if(message instanceof GCCowSingleSwingBegin){//收到重摇消息 开始重摇
			executeGCCowSingleSwingBegin(robot,(GCCowSingleSwingBegin)message);
			
		}else if(message instanceof GCCowSingleSwingEnd){//重摇结束
			executeGCCowSingleSwingEnd(robot,(GCCowSingleSwingEnd)message);
			
		}else if(message instanceof GCCowEndUnifySwing){
			executeGCCowEndUnifySwing((GCCowEndUnifySwing)message);
			
		}
	
		
		
	}
	
	private void executeCGBazooHeartBeat(Robot robot, GCBazooHeartBeat message) {
		logger.info("[无双吹牛]---[心跳]---[当前用户ID::"+robot.getPassportId()+"]");
		robot.sendMessage(new CGBazooHeartBeat());
		
	}
	

	/**
	 * 统一摇色子返回
	 * @param message
	 */
	private void executeGCCowUnifySwing(Robot robot,GCCowUnifySwing message) {
		int oneRoundTime = message.getOneRoundTime();
		long bankPassportId = message.getPassportId();
		
		Integer cowNameInt = message.getCowNameInt();
		int[] diceValues = message.getDiceValues();
		diceValue=diceValues;
		int[] redDiceValues = message.getRedDiceValues();
		
		
		logger.info("[无双吹牛]---[牛牛模式]---[庄家的ID::"+bankPassportId+"]---[一局时间::"+oneRoundTime+"]");
		String diceValuesStr = "";
		for(int i=0;i<diceValues.length;i++){
			diceValuesStr+=","+diceValues[i];
		}
		String redDiceValuesStr = "";
		for(int i=0;i<redDiceValues.length;i++){
			redDiceValuesStr+=","+redDiceValues[i];
		}
		logger.info("[无双吹牛]---[牛牛模式]---[牛牛名称::"+cowNameInt+"]---[色子值::"+diceValuesStr+"]---[色子颜色：："+redDiceValuesStr+"]");
		
		
		
		
	}
	
	
	
	private void executeGCCowSingleSwingBegin(Robot robot,
			GCCowSingleSwingBegin message) {
		try{
			Thread.sleep(500);
		}catch(Exception e){
			e.printStackTrace();
		}
		/**
		 * 单独摇色子
		 */
		for(int i=0;i<3;i++){
			try{
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			executeCGCowSingleSwing(robot,diceValue);
		}
		
	}
	
	
	//重摇结束收到消息
	private void executeGCCowSingleSwingEnd(Robot robot,
			GCCowSingleSwingEnd message) {
		DiceInfo[] DiceInfos = message.getDiceInfo();
		
		for(DiceInfo info :DiceInfos){
			int cowNameInt = info.getCowNameInt();
			int[] diceValues = info.getDiceValues();
			int[] redDiceValues = info.getRedDiceValues();
			String diceValuesStr = "";
			for(int i=0;i<diceValues.length;i++){
				diceValuesStr+=","+diceValues[i];
			}
			String redDiceValuesStr = "";
			for(int i=0;i<redDiceValues.length;i++){
				redDiceValuesStr+=","+redDiceValues[i];
			}
			long passportId = info.getPassportId();
			logger.info("[无双吹牛]---[重摇结束看所有人的色子值]---[谁::"+passportId+"][几小牛::"+cowNameInt+"][所有值：："+diceValuesStr+"][红色::"+redDiceValuesStr+"]");
		}
	}






	
	/**
	 * 单独摇色子 返回
	 * @param message
	 */
	private void executeCGCowSingleSwing(Robot robot,int[] diceValues) {
		CGCowSingleSwing CGCowSingleSwing = new CGCowSingleSwing();
		int[] left = new int[3];
		for(int i=0;i<diceValues.length;i++){
			if(i>2){
				break;
			}
			left[i]=diceValues[i];
		}
		CGCowSingleSwing.setDiceValues(left);
		robot.sendMessage(CGCowSingleSwing);
		
	}
	
	/**
	 * 最终结算
	 * @param message
	 */
	private void executeGCCowEndUnifySwing(GCCowEndUnifySwing message) {
		EndCountInfo[] EndCountInfo = message.getEndCountInfo();
		
		for(int i=0;i<EndCountInfo.length;i++){
			EndCountInfo info = EndCountInfo[i];
			long passportId = info.getPassportId();
			int multiple = info.getMultiple();
			String name = info.getName();
			long gold = info.getGold();
			int[] diceValues = info.getDiceValues();
			String diceValuesStr = "";
			for(int j=0;j<diceValues.length;j++){
				diceValuesStr+=","+diceValues[j];
			}
			
			
			int[] winPassportId = info.getWinPassportId();
			String winPassportIdStr = "";
			for(int j=0;j<winPassportId.length;j++){
				winPassportIdStr+=","+winPassportId[j];
			}
			
			int[] winMultiple = info.getWinMultiple();
			String winMultipleStr = "";
			for(int j=0;j<winMultiple.length;j++){
				winMultipleStr+=","+winMultiple[j];
			}
			logger.info("[无双吹牛]---[牛牛模式]---[结算]---[当前用户ID::"+passportId+"]---[所翻的倍数::"+multiple+"]---[牛牛名称::"+name+"]---[金币::"+gold+"]["+diceValuesStr+"]");
			logger.info("[无双吹牛]---[牛牛模式]---[结算]---[ID:"+winPassportIdStr+"]---[倍数:"+winMultipleStr+"]");
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
		
		CGRoomEnter CGRoomEnter =  new CGRoomEnter();
//		CGRoomEnter.setBet(500);
		CGRoomEnter.setBet(5000);
		robot.sendMessage(CGRoomEnter);
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
			logger.info("[无双吹牛]---[ReturnPlayerInfo::ID"+oInfo.getPassportId()+"---UserStatus:"+oInfo.getUserStatus()+"]");
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
