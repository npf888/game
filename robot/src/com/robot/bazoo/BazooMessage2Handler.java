package com.robot.bazoo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.core.util.RandomUtil;
import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.msg.CGDiceSingleSwing;
import com.gameserver.bazoo.msg.CGGuessBigSmall;
import com.gameserver.bazoo.msg.CGRobOpen;
import com.gameserver.bazoo.msg.CGTalkBig;
import com.gameserver.bazoo.msg.GCDiceUserShouldSwing;
import com.gameserver.bazoo.msg.GCRobOpen;
import com.gameserver.bazoo.msg.GCRobotDiceUnifySwing;
import com.gameserver.bazoo.msg.GCTalkBig;
import com.gameserver.bazoo.util.DiceUtils;
import com.robot.Robot;
import com.robot.bazoo.Util.ChanceUtil;
import com.robot.bazoo.data.BazooTemp;
import com.robot.bazoo.data.DiceNum;
import com.robot.bazoo.data.PointNumber;

public class BazooMessage2Handler {
	protected Logger logger = Loggers.BAZOO;
	private int robNum=0;
	public void execute(Robot robot, IMessage message,String doWhat) {
		
		
		//统一摇色子 返回所有用户的色子的值（最新的版本）
		if("swingBackRobot".equals(doWhat)){
			executeGCRobotDiceUnifySwing(robot,(GCRobotDiceUnifySwing)message);
		}
		
		if("shouldSwing".equals(doWhat)){
			executeCGDiceUserShouldSwing(robot,(GCDiceUserShouldSwing)message);
		}
		if("callBack".equals(doWhat)){
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
		List<Integer> ListdiceValues = DiceUtils.getListFromArr(diceValues);
		Collections.sort(ListdiceValues);
		int diceValue=0;
		int dv = 0;
		for(Integer dice:ListdiceValues){
			if(diceValue != dice.intValue()){
				diceValue=dice;
				dv++;
			}
		}
		if(dv==5){//是顺子
			robot.getBazooTemp().setStraight(true);
		}else{//不是顺子
			robot.getBazooTemp().setStraight(false);
		}
		
		//每一局开始都把 这里设置为0
		robot.getBazooTemp().setFirstTime(0);
	}


	private void executeCGDiceUserShouldSwing(Robot robot,GCDiceUserShouldSwing GCDiceUserShouldSwing) {
		
		logger.info("[无双吹牛]---[每一次]---[当前用户ID::"+robot.getPassportId()+"]");
		logger.info("[无双吹牛]---[每一次]---[应该叫号的用户ID::"+GCDiceUserShouldSwing.getShouldCallPassportId()+"]");
		//次数在 逐渐增加
		robot.getBazooTemp().setFirstTime(robot.getBazooTemp().getFirstTime()+1);
		
		//每一局 的第一次
		if(robot.getPassportId().equals(String.valueOf(GCDiceUserShouldSwing.getShouldCallPassportId()))){
			
			int firstTime = robot.getBazooTemp().getFirstTime();
			//说明 第一次 不是自己
			if(firstTime > 1){
				return;
			}
			
			logger.info("[无双吹牛]---[第一一一一一一一次]---[当前用户ID::"+robot.getPassportId()+"]");
			
			try{
				Thread.sleep(1500);
			}catch(Exception e){
				
			}
			/**
			 * 自己第一个 叫 点
			 */
			List<PointNumber> pnList = robot.getBazooTemp().getSelfDice(robot);
			
			boolean straight = robot.getBazooTemp().isStraight();
			if(!straight){//没有顺子
				CGTalkBig CGTalkBig = new CGTalkBig();
				//叫的个数为：自己拥有的个数+1点的个数+全场人数-1
				PointNumber pn = pnList.get(0);
				if(pn.getPoint() == 1){
					pn=pnList.get(1);
				}
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
			}else{//如果自己有顺子，从自己开始叫点： 2-6随机选一个点，叫的个数为所有人数+2
				int point = RandomUtil.nextInt(2, 7);
				int total = robot.getBazooTemp().getDiceInfoList().size();
				CGTalkBig CGTalkBig = new CGTalkBig();
				CGTalkBig.setDiceNum(total+2);
				CGTalkBig.setDiceValue(point);
				robot.sendMessage(CGTalkBig);
			}
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
			Thread.sleep(1500);
		}catch(Exception e){
			
		}
		
		
		/**
		 * 先看 是否抢开
		 */
		int passCallDiceNum = 0;
		//看看 当前用户叫的号 callDiceValue  自己有几个
		DiceInfo selfDiceInfo = bazooTemp.getSelfDiceInfo();
		if(selfDiceInfo == null){
			logger.info("[无双吹牛]---[当前用户处于观看者模式]---[当前用户ID::"+robot.getPassportId()+"]---[轮到下一个用户ID::"+passportId+"]");
			return;
		}
		int callTypeNew = 1;
		int callType = 1;
		if(OnePoint == 0 && !isStraight){//没1点  没顺子
			callType=1;
			for(int i=0;i<selfDiceInfo.getDiceValues().length;i++){
				int value = selfDiceInfo.getDiceValues()[i];
				if(value == callDiceValue || value == 1){
					passCallDiceNum++;
				}
			}
			
		}else if(OnePoint == 0 && isStraight){//没 1 点 ，有 顺子
			callType=2;
		}else if(OnePoint == 1 && !isStraight){//有 1 点 ，没 顺子
			callType=2;
			for(int i=0;i<selfDiceInfo.getDiceValues().length;i++){
				int value = selfDiceInfo.getDiceValues()[i];
				if(value == callDiceValue){
					passCallDiceNum++;
				}
			}
		}else if(OnePoint == 1 && isStraight){//有 1点 ，有顺子
			callType=3;
		}
		
		
		int probability = robot.getClassicalPropertyService().getProbability(callTypeNew, peopleNum, Long.valueOf(callDiceNum-passCallDiceNum).intValue());
		if(ChanceUtil.randomHundred(probability)){
			logger.info("[无双吹牛]---[用户抢开2]---[当前次数::"+robot.getRobotBazooCacheData().getRobNum()+"]");
			BazooHandlerFactory.getHandler2().execute(robot,null,"rob");
			return;
		}
		
		
		/**
		 * 没有抢开
		 * 又轮到自己  就去叫号
		 */
		if(robot.getPassportId().equals(String.valueOf(passportId))){
			
			logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
			
			
			logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到我了]");
			
			List<PointNumber> pnList = robot.getBazooTemp().getSelfDice(robot);
			PointNumber pn = pnList.get(0);
			
			boolean straight = robot.getBazooTemp().isStraight();
			if(!straight){//没有顺子
				CGTalkBig CGTalkBig = new CGTalkBig();
				//叫的个数为：自己拥有的个数+1点的个数+全场人数-1
				int onePointNumber = 0;//一点 个数
				if(OnePoint == 1){//被叫过了  就不算了
					for(PointNumber p:pnList){
						if(p.getPoint() == 1){
							onePointNumber=p.getNumber();
						}
					}
				}
				int number = pn.getNumber()+onePointNumber+robot.getBazooTemp().getDiceInfoList().size()-1;
				
				if(callDiceNum < number  && callDiceValue < pn.getPoint()){
					CGTalkBig.setDiceNum(number);
					CGTalkBig.setDiceValue(pn.getPoint());
					logger.info("[无双吹牛]---[上一用户叫号1]---[当前用户ID::"+passportId+"]---[diceNum:"+callDiceNum+"][diceValue:"+callDiceValue+"]");
					logger.info("[无双吹牛]---[当前用户叫号1]---[当前用户ID::"+robot.getPassportId()+"]---[diceNum:"+CGTalkBig.getDiceNum()+"][diceValue:"+CGTalkBig.getDiceValue()+"]");
				}else{
					CGTalkBig.setDiceNum(Long.valueOf(callDiceNum+1).intValue());
					CGTalkBig.setDiceValue(Long.valueOf(callDiceValue).intValue());
					
					logger.info("[无双吹牛]---[上一用户叫号2]---[当前用户ID::"+passportId+"]---[diceNum:"+callDiceNum+"][diceValue:"+callDiceValue+"]");
					logger.info("[无双吹牛]---[当前用户叫号2]---[当前用户ID::"+robot.getPassportId()+"]---[diceNum:"+CGTalkBig.getDiceNum()+"][diceValue:"+CGTalkBig.getDiceValue()+"]");
				}
				robot.sendMessage(CGTalkBig);
				
			}else{
				CGTalkBig CGTalkBig = new CGTalkBig();
				CGTalkBig.setDiceNum(Long.valueOf(callDiceNum+1).intValue());
				CGTalkBig.setDiceValue(Long.valueOf(callDiceValue).intValue());
				robot.sendMessage(CGTalkBig);
				
				logger.info("[无双吹牛]---[上一用户叫号3]---[当前用户ID::"+passportId+"]---[diceNum:"+callDiceNum+"][diceValue:"+callDiceValue+"]");
				logger.info("[无双吹牛]---[当前用户叫号3]---[当前用户ID::"+robot.getPassportId()+"]---[diceNum:"+CGTalkBig.getDiceNum()+"][diceValue:"+CGTalkBig.getDiceValue()+"]");

			}
		}
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
	
	


}
