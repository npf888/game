package com.robot.bazoo;

import java.util.Random;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.core.util.RandomUtil;
import com.gameserver.bazoo.msg.CGBazooHeartBeat;
import com.gameserver.bazoo.msg.CGDiceSingleSwing;
import com.gameserver.bazoo.msg.CGGuessBigSmall;
import com.gameserver.bazoo.msg.CGRobOpen;
import com.gameserver.bazoo.msg.CGTalkBig;
import com.gameserver.bazoo.msg.GCBazooHeartBeat;
import com.gameserver.bazoo.msg.GCDiceUnifySwing;
import com.gameserver.bazoo.msg.GCDiceUserShouldSwing;
import com.gameserver.bazoo.msg.GCRobOpen;
import com.gameserver.bazoo.msg.GCTalkBig;
import com.robot.Robot;

public class BazooMessage2Handler {
	protected Logger logger = Loggers.BAZOO;
	private int robNum=0;
	public void execute(Robot robot, IMessage message,String doWhat) {
		
		
		if("GCBazooHeartBeat".equals(doWhat)){
			executeCGBazooHeartBeat(robot,(GCBazooHeartBeat)message);
		}
		if("shouldSwing".equals(doWhat)){
			executeCGDiceUserShouldSwing(robot,(GCDiceUserShouldSwing)message);
		}
		if("swingSingle".equals(doWhat)){
			executeCGDiceSingleSwing(robot);
		}
		//摇色子 返回
		if("swingBack".equals(doWhat)){
			executeGCDiceUnifySwing(robot,(GCDiceUnifySwing)message);
		}
		
		
		if("callBack".equals(doWhat)){
			executeGCTalkBig(robot,(GCTalkBig)message);
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
	
	
	private void executeCGBazooHeartBeat(Robot robot, GCBazooHeartBeat message) {
		logger.info("[无双吹牛]---[心跳]---[当前用户ID::"+robot.getPassportId()+"]");
		robot.sendMessage(new CGBazooHeartBeat());
		
	}


	private void executeCGDiceUserShouldSwing(Robot robot,GCDiceUserShouldSwing GCDiceUserShouldSwing) {
		
		logger.info("[无双吹牛]---[每一次]---[当前用户ID::"+robot.getPassportId()+"]");
		logger.info("[无双吹牛]---[每一次]---[应该叫号的用户ID::"+GCDiceUserShouldSwing.getShouldCallPassportId()+"]");
		//每一局 的第一次
		if(robot.getPassportId().equals(String.valueOf(GCDiceUserShouldSwing.getShouldCallPassportId()))){
			logger.info("[无双吹牛]---[每一次]---[当前用户ID::"+robot.getPassportId()+"]");
			CGTalkBig CGTalkBig = new CGTalkBig();
			CGTalkBig.setDiceNum(3);
			CGTalkBig.setDiceValue(3);
			robot.getRobotBazooCacheData().setRobNum(robot.getRobotBazooCacheData().getRobNum()+1);
			
			
			try{
				Thread.sleep(1500);
			}catch(Exception e){
				
			}
//			robot.sendMessage(CGTalkBig);
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
		logger.info("[无双吹牛]---[叫号返回参数2]---[该轮到谁了::"+passportId+"]");
		logger.info("[无双吹牛]---[叫号返回参数3]---[当前用户叫号::"+callDiceNum+"]");
		logger.info("[无双吹牛]---[叫号返回参数4]---[当前用户叫号::"+callDiceValue+"]");
		logger.info("[无双吹牛]---[叫号返回参数5]---[一点是否叫过::"+OnePoint+"]");
		
		//如果当前用户和 叫号的用户不相等  然后 叫号次数 超过了  10（可以设置） 那么就抢开
//		if(!robot.getPassportId().equals(String.valueOf(GCTalkBig.getCurPassportId()))){
			logger.info("[无双吹牛]---[叫号次数]---[当前次数::"+robot.getRobotBazooCacheData().getRobNum()+"]");
			if(robot.getRobotBazooCacheData().getRobNum() >= 2){
				/**
				 * 抢开
				 */
				Random random = new Random();
				int ran = random.nextInt(11);
				/**
				 * 每个人都有 30%抢开的概率
				 */
				if(ran >8){
					logger.info("[无双吹牛]---[用户抢开]---[当前次数::"+robot.getRobotBazooCacheData().getRobNum()+"]");
					BazooHandlerFactory.getHandler2().execute(robot,null,"rob");
					return;
				}
						
			}
//		}
		logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到下一个用户ID::"+passportId+"]");
		//轮到自己了
		if(robot.getPassportId().equals(String.valueOf(passportId))){
			logger.info("[无双吹牛]---[叫号返回]---[当前用户ID::"+robot.getPassportId()+"]---[轮到我了]");
			
			try{
				Thread.sleep(1500);
			}catch(Exception e){
				
			}
			
			
			//主动叫号
//			executeCGTalkBig(robot,GCTalkBig);
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
	
	/**
	 * 开始说大话
	 * @param gCTalkBig 
	 */
	private void executeCGTalkBig(Robot robot, GCTalkBig gCTalkBig) {
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


}
