package com.robot.slot.slot24;

import com.core.msg.IMessage;
import com.core.util.RandomUtil;
import com.gameserver.slot.msg.CGSlotType24BounsGameGo;
import com.gameserver.slot.msg.CGSlotType24BounsPrize;
import com.gameserver.slot.msg.GCSlotType24BounsBar;
import com.gameserver.slot.msg.GCSlotType24BounsGameStart;
import com.gameserver.slot.msg.GCSlotType24BounsSamba;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;
/**
 * 巴西风情小游戏 新的
 * @author JavaServer
 *
 */
public class GCSlotBonus24New extends GCSlotBonusBase{
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		
		//中了小游戏返回的第一个消息 
		if(message instanceof GCSlotType24BounsGameStart){
			GCSlotType24BounsGameStart gCSlotType24BounsGameStart = (GCSlotType24BounsGameStart)message;
			
			//如果触发了小游戏 就把 bonus 个数放到这里
			robot.getRobotSlotCacheData().setBonusPubNum(3);
			robot.getBonusHandler().triggerBonusNum(robot,0);
			
			
			
			int[] color = gCSlotType24BounsGameStart.getColor();
			int gameType = gCSlotType24BounsGameStart.getGameType();
			int round = gCSlotType24BounsGameStart.getRound();
			int chance = gCSlotType24BounsGameStart.getChance();
			CGSlotType24BounsGameGo cGSlotType24BounsGameGo = new CGSlotType24BounsGameGo();
			cGSlotType24BounsGameGo.setGameType(gameType);
			/**
			 * 如果是 酒吧游戏
		     * 不做任何处理
			 * 如果是 桑巴游戏 要把用户选择的 颜色 加上
			 */
			if(gameType == 2){
				int whichColor = RandomUtil.nextInt(1, color.length+1);
				cGSlotType24BounsGameGo.setColor(whichColor);
			}
			robot.sendMessage(cGSlotType24BounsGameGo);
			
			
			
		}else if(message instanceof GCSlotType24BounsBar){
			GCSlotType24BounsBar gCSlotType24BounsBar = (GCSlotType24BounsBar)message;
			
			int[] isRewards = gCSlotType24BounsBar.getIsReward();
			long totalGold = gCSlotType24BounsBar.getTotalGold();
			long[] rewards = gCSlotType24BounsBar.getRewards();
			
			
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(totalGold).intValue());
			robot.getRobotSlotCacheData().setBonusFree(false);
			
			robot.getTurnSlot().turnFree(robot, robot.getRobotSlotCacheData().getFreeTimes(), slotId);
		}else if(message instanceof GCSlotType24BounsSamba){
			GCSlotType24BounsSamba gCSlotType24BounsSamba = (GCSlotType24BounsSamba)message;
			
			long reward = gCSlotType24BounsSamba.getReward();
			
			robot.getRobotSlotCacheData().setBonusPubNum(0);
			robot.getBonusHandler().countBonusNum(robot, Long.valueOf(reward).intValue());
			robot.getRobotSlotCacheData().setBonusFree(false);
			
			robot.getTurnSlot().turnFree(robot, robot.getRobotSlotCacheData().getFreeTimes(), slotId);
			
		}
			
		
	}

}
