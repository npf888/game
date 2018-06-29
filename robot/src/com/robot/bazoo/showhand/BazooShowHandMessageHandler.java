package com.robot.bazoo.showhand;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.bazoo.data.BetTotalNum;
import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.data.ReturnPlayerInfo;
import com.gameserver.bazoo.data.ShowHandBet;
import com.gameserver.bazoo.msg.CGBazooHeartBeat;
import com.gameserver.bazoo.msg.CGRoomEnter;
import com.gameserver.bazoo.msg.CGRoomOut;
import com.gameserver.bazoo.msg.CGRoomPriJoin;
import com.gameserver.bazoo.msg.CGRoomPubJoin;
import com.gameserver.bazoo.msg.CGShowHandSingleSwing;
import com.gameserver.bazoo.msg.GCBazooHeartBeat;
import com.gameserver.bazoo.msg.GCModeChose;
import com.gameserver.bazoo.msg.GCRobotWhichRoomToGoin;
import com.gameserver.bazoo.msg.GCRoomInit;
import com.gameserver.bazoo.msg.GCRoomMatched;
import com.gameserver.bazoo.msg.GCRoomOut;
import com.gameserver.bazoo.msg.GCShowHandEndCount;
import com.gameserver.bazoo.msg.GCShowHandLittleSwing;
import com.gameserver.bazoo.msg.GCShowHandSingleSwing;
import com.gameserver.bazoo.msg.GCShowHandUnifySwing;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.util.DiceUtils;
import com.robot.Robot;

public class BazooShowHandMessageHandler {

	protected Logger logger = Loggers.BAZOO;
	
	private int[] diceValue =null;
	private boolean cgping= true;
	public void execute(Robot robot, IMessage message) {
//		if(message instanceof GCBazooHeartBeat){
//			executeCGBazooHeartBeat(robot,(GCBazooHeartBeat)message);
//		}
		
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
			/*try{
				Thread.sleep(8500);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			CGRoomOut CGRoomOut = new CGRoomOut();
			CGRoomOut.setBet(5000);
			robot.sendMessage(CGRoomOut);*/
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
		if(message instanceof GCShowHandUnifySwing){//匹配完 由服务器 统一摇色子
			executeGCShowHandUnifySwing(robot,(GCShowHandUnifySwing)message);
			
		}else if(message instanceof GCShowHandLittleSwing){//收到 最小摇号的 用户
			executeGCShowHandLittleSwing(robot,(GCShowHandLittleSwing)message);
			
		}else if(message instanceof GCShowHandSingleSwing){//单独摇完色子 的返回值
			executeGCShowHandSingleSwing(robot,(GCShowHandSingleSwing)message);
			
			
		}else if(message instanceof GCShowHandEndCount){//最终结算
			executeGCShowHandEndCount((GCShowHandEndCount)message);
			
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
	
	private void executeGCShowHandEndCount(GCShowHandEndCount message) {
		EndCountInfo[] EndCountInfoArr = message.getEndCountInfo();
		
		for(int i=0;i<EndCountInfoArr.length;i++){
			EndCountInfo EndCountInfo= EndCountInfoArr[i];
			int[] diceValues = EndCountInfo.getDiceValues();
			String dv = "";
			for(int j=0;j<diceValues.length;j++){
				dv+=diceValues[j]+",";
			}
			long gold = EndCountInfo.getGold();
			int multiple = EndCountInfo.getMultiple();
			long passportId = EndCountInfo.getPassportId();
			String name = EndCountInfo.getName();
			int[] winMultiple=	EndCountInfo.getWinMultiple();
			int[] winPassportId=	EndCountInfo.getWinPassportId();
			logger.info("[无双吹牛]---[梭哈模式-结算]---[用户ID::"+passportId+"]---[名称：："+name+"]"+"---[multiple::"+multiple+"]---[gold::"+gold+"]"+"[色子值::"+dv+"]");
		}
		
	}


	/**
	 * 用户单独摇完 色子 的返回值
	 * @param robot
	 * @param message
	 */
	private void executeGCShowHandSingleSwing(Robot robot,
			GCShowHandSingleSwing message) {
		long passportId = message.getPassportId();
		int leftTime = message.getLeftTimes();
		int nameInt = message.getNameInt();
		int[] diceValues = message.getDiceValues();
		
		if(passportId == Long.valueOf(robot.getPassportId()).longValue()){
			List<Integer> robotDiceValues = robot.getBazooTemp().getDiceValues();
			robotDiceValues.clear();
			for(int j=0;j<diceValues.length;j++){
				robotDiceValues.add(diceValues[j]);
			}
		}
		String dv = "";
		for(int j=0;j<diceValues.length;j++){
			dv+=diceValues[j]+",";
		}
		
		ShowHandBet[] ShowHandBetArr = message.getShowHandBet();
		String showhand = "";
		for(int j=0;j<ShowHandBetArr.length;j++){
			ShowHandBet ShowHandBet =ShowHandBetArr[j];
			long passportid = ShowHandBet.getPassportId();
			int bet = ShowHandBet.getBet();
			long gold = ShowHandBet.getGold();
			
			showhand+= passportid+"|"+bet+"|"+gold+",";
		}
		
		logger.info("[无双吹牛]---[梭哈模式]---[用户ID::"+passportId+"]---[名称：："+nameInt+"]"+"---[剩余次数::"+leftTime+"]---[当前色子值::"+dv+"]"+"[所有人 输赢::"+showhand+"]");
		
	}


	
	

	/**
	 * 返回 最小的 摇色子的人
	 * @param robot
	 * @param message
	 */
	private void executeGCShowHandLittleSwing(Robot robot,
			GCShowHandLittleSwing message) {
		long[] passportIds = message.getPassportId();
		String pa = "";
		for(int i=0;i<passportIds.length;i++){
			pa+=passportIds[i]+",";
		}
		logger.info("[无双吹牛]---[梭哈模式]---[下一波 需要摇色子的人::"+pa+"]");
		
		
		//等一会  就去摇色子
		
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			e.printStackTrace();
		}
		/*for(int i=0;i<10;i++){
			int index = RandomUtil.nextInt(0, 5);
			
			//先选择色子
			CGShowHandSingleSwich CGShowHandSingleSwich = new CGShowHandSingleSwich();
			CGShowHandSingleSwich.setDiceIndex(index);
			robot.sendMessage(CGShowHandSingleSwich);
			logger.info("[无双吹牛]---[梭哈模式]---[开始选择色子::]");
			
			try{
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//取消选择
			CGShowHandSingleSwichCancel CGShowHandSingleSwichCancel = new CGShowHandSingleSwichCancel();
			CGShowHandSingleSwichCancel.setDiceIndex(index);
			robot.sendMessage(CGShowHandSingleSwichCancel);
			logger.info("[无双吹牛]---[梭哈模式]---[开始 取消选择色子::]");
			
		}*/
		
		
		//摇色子
		boolean same = false;
		for(long passportId:passportIds){
			if(passportId == Long.valueOf(robot.getPassportId()).longValue()){
				same=true;
			}
		}
		if(same){
			CGShowHandSingleSwing CGShowHandSingleSwing = new CGShowHandSingleSwing();
			List<Integer> diceValues = robot.getBazooTemp().getDiceValues();
			List<Integer> beShakingDiceValueList = new ArrayList<Integer>();
			for(int i=0;i<diceValues.size();i++){
				if(diceValues.get(i).intValue() != 1){
					beShakingDiceValueList.add(diceValues.get(i));
				}
			}
			CGShowHandSingleSwing.setDiceValues(DiceUtils.getArrFromDiceList(beShakingDiceValueList));
			robot.sendMessage(CGShowHandSingleSwing);
		}
	}



	private void executeCGBazooHeartBeat(Robot robot, GCBazooHeartBeat message) {
		logger.info("[无双吹牛]---[心跳]---[当前用户ID::"+robot.getPassportId()+"]");
		robot.sendMessage(new CGBazooHeartBeat());
		
	}
	
	
	
	
	/**
	 * 统一摇色子 返回
	 * @param robot
	 * @param message
	 */
	private void executeGCShowHandUnifySwing(Robot robot,
			GCShowHandUnifySwing message) {
		DiceInfo[] diceInfoArr = message.getDiceInfo();
		for(int i=0;i<diceInfoArr.length;i++){
			DiceInfo diceInfo = diceInfoArr[i];
			long passportId = diceInfo.getPassportId();
			int cowNameInt = diceInfo.getCowNameInt();
			int[] diceValues = diceInfo.getDiceValues();
			List<Integer> rootDiceValues = null;
			if(passportId == Long.valueOf(robot.getPassportId()).longValue()){
				rootDiceValues = robot.getBazooTemp().getDiceValues();
				rootDiceValues.clear();
			}
			String dv = "";
			for(int j=0;j<diceValues.length;j++){
				dv+=diceValues[j]+",";
				if(rootDiceValues != null){
					rootDiceValues.add(diceValues[j]);
				}
			}
			logger.info("[无双吹牛]---[梭哈模式]---[第几个::"+i+"]---[用户ID::"+passportId+"]---[名称：："+cowNameInt+"]"+"---["+dv+"]");
		}
		
		logger.info("[无双吹牛]---[梭哈模式]---[剩余次数::"+message.getLeftTimes()+"]");
		
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
