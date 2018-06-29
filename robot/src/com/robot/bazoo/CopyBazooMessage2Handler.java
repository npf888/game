package com.robot.bazoo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.core.util.RandomUtil;
import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.msg.CGBazooHeartBeat;
import com.gameserver.bazoo.msg.CGDiceSingleSwing;
import com.gameserver.bazoo.msg.CGGuessBigSmall;
import com.gameserver.bazoo.msg.CGRobOpen;
import com.gameserver.bazoo.msg.CGTalkBig;
import com.gameserver.bazoo.msg.GCBazooHeartBeat;
import com.gameserver.bazoo.msg.GCDiceUnifySwing;
import com.gameserver.bazoo.msg.GCDiceUserShouldSwing;
import com.gameserver.bazoo.msg.GCRobOpen;
import com.gameserver.bazoo.msg.GCRobotDiceUnifySwing;
import com.gameserver.bazoo.msg.GCTalkBig;
import com.gameserver.bazoo.util.DiceUtils;
import com.robot.Robot;
import com.robot.bazoo.Util.ChanceUtil;
import com.robot.bazoo.Util.CountNumUtil;
import com.robot.bazoo.data.BazooTemp;
import com.robot.bazoo.data.DiceNum;
import com.robot.bazoo.data.PointNumber;

public class CopyBazooMessage2Handler {
	protected Logger logger = Loggers.BAZOO;
	private int robNum=0;
	public void execute(Robot robot, IMessage message,String doWhat) {
		
		
		//统一摇色子 返回所有用户的色子的值（机器人）
		if("swingBackRobot".equals(doWhat)){
//			executeGCRobotDiceUnifySwing(robot,(GCRobotDiceUnifySwing)message);
		}
		//统一摇色子 返回所有用户的色子的值（最老的版本）
		if("swingBack".equals(doWhat)){
//			executeGCDiceUnifySwing(robot,(GCDiceUnifySwing)message);
		}
		//统一摇色子 返回所有用户的色子的值（最新的版本）
		if("swingBackRobot".equals(doWhat)){
			executeGCRobotDiceUnifySwing(robot,(GCRobotDiceUnifySwing)message);
		}
		
		
		
		
		
		
		if("shouldSwing".equals(doWhat)){
			executeCGDiceUserShouldSwing(robot,(GCDiceUserShouldSwing)message);
		}
		if("callBack".equals(doWhat)){
//			executeGCTalkBig(robot,(GCTalkBig)message);
//			executeGCTalkBigNew(robot,(GCTalkBig)message);
			executeGCTalkBigLastNew(robot,(GCTalkBig)message);
		}
		
		
		
		
		if("swingSingle".equals(doWhat)){
			executeCGDiceSingleSwing(robot);
		}
		
		
		
		//抢开
		if("rob".equals(doWhat)){
			executeCGRobOpen(robot);
		}
		//抢开返回
		if("robBack".equals(doWhat)){
			executeGCRobOpen(robot,(GCRobOpen)message);
		}
		//开始竞猜
		if("guessOpen".equals(doWhat)){
			executeCGGuessOpen(robot);
		}
		
		
		//竞猜大小
		if("guessBigSmall".equals(doWhat)){
			executeCGOtherGuessBigSmall(robot);
		}
		//竞猜大小
		if("guessBigSmallBack".equals(doWhat)){
			logger.info("[无双吹牛]---[竞猜大小]---[当前用户ID::"+robot.getPassportId()+"]");
		}
		
		
		//最终结算 返回
		if("endCountBack".equals(doWhat)){
			logger.info("[无双吹牛]---[最终结算]---[当前用户ID::"+robot.getPassportId()+"]");
		}
		
	}
	
	
	private void executeGCRobotDiceUnifySwing(Robot robot,
			GCRobotDiceUnifySwing message) {
		//所有的人 包括自己
		DiceInfo[] diceInfoArr = message.getDiceInfo();
		List<DiceInfo> diceInfoList = new ArrayList<DiceInfo>();
		long passportId = 0l;
		int[] diceValues =null;
		for(int i=0;i<diceInfoArr.length;i++){
			DiceInfo diceInfo = diceInfoArr[i];
			diceInfoList.add(diceInfo);
			long curPassportId = diceInfo.getPassportId();
			int[] curDiceValues = diceInfo.getDiceValues();
			if(curPassportId == Long.valueOf(robot.getPassportId())){
				passportId=curPassportId;
				diceValues=curDiceValues;
				//设置好自己的点数
				robot.getBazooTemp().setSelfDiceInfo(diceInfo);
			}
		}
		
		robot.getBazooTemp().setDiceInfoList(diceInfoList);
		//统计以下色子的个数
		robot.getBazooTemp().countDice(false);
		
		logger.info("----------------------------------------------------------------------");
		for(int i=0;i<diceValues.length;i++){
			logger.info("[无双吹牛]---[摇色子]---[当前用户ID"+passportId+"]---[当前用户的色子值---:"+diceValues[i]+"]");
		}
		
		//判断色子是否是顺子
		int x = 0;
		int change=0;
		for(int i=0;i<diceValues.length;i++){
			int value = diceValues[i];
			if(i==0){
				x=value;
				continue;
			}
			if(value == x){
				change++;
			}
		}
		if(change == 0){//是顺子
			robot.getBazooTemp().setStraight(true);
		}else{//不是顺子
			robot.getBazooTemp().setStraight(false);
		}
	}


	private void executeCGDiceUserShouldSwing(Robot robot,GCDiceUserShouldSwing GCDiceUserShouldSwing) {
		
		logger.info("[无双吹牛]---[每一次]---[当前用户ID::"+robot.getPassportId()+"]");
		logger.info("[无双吹牛]---[每一次]---[应该叫号的用户ID::"+GCDiceUserShouldSwing.getShouldCallPassportId()+"]");
		//每一局 的第一次
		if(robot.getPassportId().equals(String.valueOf(GCDiceUserShouldSwing.getShouldCallPassportId()))){
			logger.info("[无双吹牛]---[第一一次]---[当前用户ID::"+robot.getPassportId()+"]");
			
			
			/*BazooTemp bazooTemp = robot.getBazooTemp();
			PointNumber pn = bazooTemp.pointList().get(0);
			
			CGTalkBig CGTalkBig = new CGTalkBig();
			CGTalkBig.setDiceNum(pn.getPoint());
			CGTalkBig.setDiceValue(pn.getNumber());*/
			robot.getRobotBazooCacheData().setRobNum(robot.getRobotBazooCacheData().getRobNum()+1);
			
			
			try{
				Thread.sleep(1500);
			}catch(Exception e){
				
			}
			/**
			 * 自己第一个 叫 点
			 */
			List<PointNumber> pnList = robot.getBazooTemp().getSelfDice(robot);
			CGTalkBig CGTalkBig = new CGTalkBig();
			//叫的个数为：自己拥有的个数+1点的个数+全场人数-1
			PointNumber pn = pnList.get(0);
			int onePointNumber = 0;//一点 个数
			for(PointNumber p:pnList){
				if(p.getPoint() == 1){
					onePointNumber=p.getNumber();
				}
			}
			int number = pn.getNumber()+onePointNumber+robot.getBazooTemp().getDiceInfoList().size()-1;
			CGTalkBig.setDiceNum(number);
			CGTalkBig.setDiceValue(pn.getPoint());
			robot.sendMessage(CGTalkBig);
		}
		
	}
	/**
	 * 叫号 返回值
	 * @param robot
	 */
	private void executeGCTalkBig(Robot robot,GCTalkBig GCTalkBig) {
		long passportId = GCTalkBig.getWhoTurnPassportId();
		long curPassportId = GCTalkBig.getCurPassportId();
		long callDiceNum = GCTalkBig.getCallDiceNum();
		long callDiceValue = GCTalkBig.getCallDiceValue();
		long OnePoint = GCTalkBig.getOnePoint();
		
		logger.info("[无双吹牛]---[叫号返回参数1]---[当前叫号用户ID::"+curPassportId+"]");
		logger.info("[无双吹牛]---[叫号返回参数3]---[当前用户叫号::"+callDiceNum+"]");
		logger.info("[无双吹牛]---[叫号返回参数4]---[当前用户叫号::"+callDiceValue+"]");
		logger.info("[无双吹牛]---[叫号返回参数2]---[该轮到谁了::"+passportId+"]");
		logger.info("[无双吹牛]---[叫号返回参数5]---[一点是否叫过::"+OnePoint+"]");
		
		BazooTemp bazooTemp = robot.getBazooTemp();
		DiceNum diceNum = bazooTemp.getDiceNum();
		
		logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到下一个用户ID::"+passportId+"]");
		//轮到自己了
		if(robot.getPassportId().equals(String.valueOf(passportId))){
			
			logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到我了]");
			
			try{
				Thread.sleep(1500);
			}catch(Exception e){
				
			}
		
			/**
			 * 每个人都有 30%抢开的概率
			 */
			boolean rob = turnSelfRobOpen(robot,diceNum,callDiceNum,callDiceValue);
			//如果没有抢开就去叫号
			if(!rob){
				executeCGTalkBig(robot,GCTalkBig,diceNum);
			}
			
			
			
		}else{
			robOpen(robot,diceNum,callDiceNum,callDiceValue);
		}
	}

	/**
	 * 叫号 返回值
	 * @param robot
	 */
	private void executeGCTalkBigLastNew(Robot robot,GCTalkBig GCTalkBig) {
		long passportId = GCTalkBig.getWhoTurnPassportId();
		long curPassportId = GCTalkBig.getCurPassportId();
		long callDiceNum = GCTalkBig.getCallDiceNum();
		long callDiceValue = GCTalkBig.getCallDiceValue();
		long OnePoint = GCTalkBig.getOnePoint();
		if(callDiceValue == 1){
			robot.getBazooTemp().countDice(true);
		}
		//是否是 顺子
		boolean isStraight = robot.getBazooTemp().isStraight();
		int peopleNum = robot.getBazooTemp().getDiceInfoList().size();
		logger.info("[无双吹牛]---[叫号返回参数1]---[当前叫号用户ID::"+curPassportId+"]");
		logger.info("[无双吹牛]---[叫号返回参数3]---[当前用户叫号::"+callDiceNum+"]");
		logger.info("[无双吹牛]---[叫号返回参数4]---[当前用户叫号::"+callDiceValue+"]");
		logger.info("[无双吹牛]---[叫号返回参数2]---[该轮到谁了::"+passportId+"]");
		logger.info("[无双吹牛]---[叫号返回参数5]---[一点是否叫过::"+OnePoint+"]");
		
		BazooTemp bazooTemp = robot.getBazooTemp();
		DiceNum diceNum = bazooTemp.getDiceNum();
		
		logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到下一个用户ID::"+passportId+"]");
		
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			
		}
	
		
		/**
		 * 先看 是否抢开
		 */
		int callType = 1;
		if(OnePoint == 0 && !isStraight){//没1点  没顺子
			callType=1;
		}else if(OnePoint == 0 && isStraight){//没 1 点 ，有 顺子
			callType=2;
		}else if(OnePoint == 1 && !isStraight){//有 1 点 ，没 顺子
			callType=2;
		}else if(OnePoint == 1 && isStraight){//有 1点 ，有顺子
			callType=3;
		}
		
		int probability = robot.getClassicalPropertyService().getProbability(callType, peopleNum, Long.valueOf(callDiceNum).intValue());
		if(ChanceUtil.random(probability)){
			logger.info("[无双吹牛]---[用户抢开2]---[当前次数::"+robot.getRobotBazooCacheData().getRobNum()+"]");
			BazooHandlerFactory.getHandler2().execute(robot,null,"rob");
			return;
		}
		
		
		/**
		 * 没有抢开
		 * 又轮到自己  就去叫号
		 */
		if(robot.getPassportId().equals(String.valueOf(passportId))){
			
			logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到我了]");
			
			List<PointNumber> pnList = robot.getBazooTemp().getSelfDice(robot);
			
			CGTalkBig CGTalkBig = new CGTalkBig();
			//叫的个数为：自己拥有的个数+1点的个数+全场人数-1
			PointNumber pn = pnList.get(0);
			int onePointNumber = 0;//一点 个数
			for(PointNumber p:pnList){
				if(p.getPoint() == 1){
					onePointNumber=p.getNumber();
				}
			}
			int number = pn.getNumber()+onePointNumber+robot.getBazooTemp().getDiceInfoList().size()-1;
			CGTalkBig.setDiceNum(number);
			CGTalkBig.setDiceValue(pn.getPoint());
			robot.sendMessage(CGTalkBig);
		}
	}
	
	
	
	/**
	 * 叫号 返回值
	 * @param robot
	 */
	private void executeGCTalkBigNew(Robot robot,GCTalkBig GCTalkBig) {
		long passportId = GCTalkBig.getWhoTurnPassportId();
		long curPassportId = GCTalkBig.getCurPassportId();
		long callDiceNum = GCTalkBig.getCallDiceNum();
		long callDiceValue = GCTalkBig.getCallDiceValue();
		long OnePoint = GCTalkBig.getOnePoint();
		if(callDiceValue == 1){
			robot.getBazooTemp().countDice(true);
		}
		
		logger.info("[无双吹牛]---[叫号返回参数1]---[当前叫号用户ID::"+curPassportId+"]");
		logger.info("[无双吹牛]---[叫号返回参数3]---[当前用户叫号::"+callDiceNum+"]");
		logger.info("[无双吹牛]---[叫号返回参数4]---[当前用户叫号::"+callDiceValue+"]");
		logger.info("[无双吹牛]---[叫号返回参数2]---[该轮到谁了::"+passportId+"]");
		logger.info("[无双吹牛]---[叫号返回参数5]---[一点是否叫过::"+OnePoint+"]");
		
		BazooTemp bazooTemp = robot.getBazooTemp();
		DiceNum diceNum = bazooTemp.getDiceNum();
		
		logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到下一个用户ID::"+passportId+"]");
		//轮到自己了
		if(robot.getPassportId().equals(String.valueOf(passportId))){
			
			logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到我了]");
			
			try{
				Thread.sleep(1500);
			}catch(Exception e){
				
			}
			
			/**
			 * 每个人都有 30%抢开的概率
			 */
			if(ChanceUtil.random(3)){
				BazooHandlerFactory.getHandler2().execute(robot,null,"rob");
				return;
				
			}
			//如果没有抢开就去叫号
			executeCGTalkBigOne(robot,GCTalkBig,diceNum);
			
			
		}else{
			if(ChanceUtil.random(3)){
				logger.info("[无双吹牛]---[用户抢开2]---[当前次数::"+robot.getRobotBazooCacheData().getRobNum()+"]");
				BazooHandlerFactory.getHandler2().execute(robot,null,"rob");
			}
			return;
		}
	}


	private boolean turnSelfRobOpen(Robot robot,DiceNum diceNum,long callDiceNum,long callDiceValue){
		
		//判断当前色子 还有没有 可以叫的（正确的）
		if(!diceNum.judgeRightExist(callDiceNum, callDiceValue)){//错的
			
			//没有可以叫的了，而且 对方叫的是对的
			if(!diceNum.judge(callDiceNum, callDiceValue)){//对的
				if(ChanceUtil.random(8)){
					
					executeCGTalkBigO(robot,Long.valueOf(callDiceNum).intValue(),Long.valueOf(callDiceValue).intValue(),diceNum);
					return true;
				}
			}
			
			
			//没有就直接抢开了
			logger.info("[无双吹牛]---[用户抢开1]---[当前次数::"+robot.getRobotBazooCacheData().getRobNum()+"]");
			BazooHandlerFactory.getHandler2().execute(robot,null,"rob");
			return true;
		}
		return false;
	}
	private boolean robOpen(Robot robot,DiceNum diceNum,long callDiceNum,long callDiceValue){
		
		//判断当前色子 叫号 是否是 对的
		if(!diceNum.judge(callDiceNum, callDiceValue)){//错的
			//如果是错的 就可以去 抢开
			if(ChanceUtil.random(3)){
				logger.info("[无双吹牛]---[用户抢开2]---[当前次数::"+robot.getRobotBazooCacheData().getRobNum()+"]");
				BazooHandlerFactory.getHandler2().execute(robot,null,"rob");
				return true;
			}
		}
		return false;
	}
	



	/**
	 * 抢开
	 * @param robot
	 */
	private void executeCGRobOpen(Robot robot) {
		
		CGRobOpen CGRobOpen = new CGRobOpen();
		robot.sendMessage(CGRobOpen);
		
	}
	/***
	 * 抢开返回
	 * @param message
	 */
	
	private void executeGCRobOpen(Robot robot,GCRobOpen GCRobOpen) {
		
		long robPassportId = GCRobOpen.getRobPassportId();
		int multiple = GCRobOpen.getMultiple();
		logger.info("[无双吹牛]---[抢开完毕]---[抢开人ID::"+robPassportId+"]---[抢开人的倍数::"+multiple+"]");
		
	}

	/**
	 * 开始竞猜
	 * @param robot
	 */
	private void executeCGGuessOpen(Robot robot) {
		BazooHandlerFactory.getHandler2().execute(robot,null,"guessBigSmall");
	}

	/**
	 * 竞猜大小
	 * @param robot
	 */
	private void executeCGOtherGuessBigSmall(Robot robot) {
		CGGuessBigSmall CGOtherGuessBigSmall = new CGGuessBigSmall();
		CGOtherGuessBigSmall.setBigSmall(RandomUtil.nextInt(0, 2));
		robot.sendMessage(CGOtherGuessBigSmall);
		
	}




	/**
	 * 单独摇色子
	 * @param robot
	 * @param roomNum
	 */
	private void executeCGDiceSingleSwing(Robot robot) {
		CGDiceSingleSwing CGDiceSingleSwing = new CGDiceSingleSwing();
		robot.sendMessage(CGDiceSingleSwing);
		
	}
	
	
	/**
	 * 统一摇色子(由服务器来摇)  和 单独 摇色子  都是 这个 返回值
	 * @param gCDiceUnifySwing
	 */

	private void executeGCDiceUnifySwing(Robot robot,GCDiceUnifySwing gCDiceUnifySwing) {
//		long shuldSwingPassportId = gCDiceUnifySwing.getShouldCallPassportId();
		long passportId = gCDiceUnifySwing.getPassportId();
		int[] diceValues = gCDiceUnifySwing.getDiceValues();
		logger.info("----------------------------------------------------------------------");
		for(int i=0;i<diceValues.length;i++){
			logger.info("[无双吹牛]---[摇色子]---[当前用户ID"+passportId+"]---[当前用户的色子值---:"+diceValues[i]+"]");
		}
		
		//单独摇色子不管
//		if(shuldSwingPassportId == 0){
//			return;
//		}
		/**
		 * 停留两秒开始叫号
		 */
		try{
			Thread.sleep(1500);
		}catch(Exception e){
			
		}
		
		//每个人 摇一次色子
//		BazooHandlerFactory.getHandler2().execute(robot,null,"swingSingle");
		
		/**
		 *  开始 说大话( 轮到谁 谁才能说)
		 */
		logger.info("[无双吹牛]---[叫号通知]-----------------------------]");
		//新一轮的开始 所有机器 人 抢开Num 设置为0
		robot.getRobotBazooCacheData().setRobNum(0);
		robNum=0;
//		if(robot.getPassportId().equals(String.valueOf(shuldSwingPassportId))){
//			logger.info("[无双吹牛]---[叫号通知]---[当前用户ID"+passportId+"]");
//			BazooHandlerFactory.getHandler2().execute(robot,null,"call");
//		}
	}
	
	private void executeCGTalkBigOne(Robot robot, GCTalkBig gCTalkBig,DiceNum diceNum) {
		CGTalkBig CGTalkBig = new CGTalkBig();
		if(gCTalkBig != null){
			int callDiceNum = gCTalkBig.getCallDiceNum();
			int callDiceValue = gCTalkBig.getCallDiceValue();
			
			CGTalkBig.setDiceNum(callDiceNum+1);
			CGTalkBig.setDiceValue(callDiceValue);
			
		}
		robot.getRobotBazooCacheData().setRobNum(robot.getRobotBazooCacheData().getRobNum()+1);
		robot.sendMessage(CGTalkBig);
		
	}
	/**
	 * 开始说大话
	 * @param gCTalkBig 
	 */
	private void executeCGTalkBig(Robot robot, GCTalkBig gCTalkBig,DiceNum diceNum) {
		CGTalkBig CGTalkBig = new CGTalkBig();
		if(gCTalkBig != null){
			int callDiceNum = gCTalkBig.getCallDiceNum();
			int callDiceValue = gCTalkBig.getCallDiceValue();
			PointNumber pointNumber = diceNum.judgeRightNumValue(callDiceNum, callDiceValue);
			
			CGTalkBig.setDiceNum(pointNumber.getNumber());
			CGTalkBig.setDiceValue(pointNumber.getPoint());
		
		}
		robot.getRobotBazooCacheData().setRobNum(robot.getRobotBazooCacheData().getRobNum()+1);
		robot.sendMessage(CGTalkBig);
		
	}
	private void executeCGTalkBigO(Robot robot,int callDiceNum, int callDiceValue,DiceNum diceNum) {
		CGTalkBig CGTalkBig = new CGTalkBig();
		List<DiceInfo> DiceInfoList = robot.getBazooTemp().getDiceInfoList();
		int maxNum =  DiceInfoList.size()*6;
		
		callDiceNum = callDiceNum+1;
		callDiceValue = callDiceValue+1;
		if(callDiceNum>=maxNum && callDiceValue<6){
			CGTalkBig.setDiceNum(maxNum);
			CGTalkBig.setDiceValue(callDiceValue+1);
		}else if(callDiceNum>=maxNum){
			logger.info("[无双吹牛]---[用户抢开2]---[当前次数::"+robot.getRobotBazooCacheData().getRobNum()+"]");
			BazooHandlerFactory.getHandler2().execute(robot,null,"rob");
			return;
		}
		
			
		robot.getRobotBazooCacheData().setRobNum(robot.getRobotBazooCacheData().getRobNum()+1);
		robot.sendMessage(CGTalkBig);
		
	}


}
