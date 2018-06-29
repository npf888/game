package com.gameserver.bazoosignin.handler;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.LogReasons.DiceSignInLogReason;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.gameserver.bazoo.msg.GCBazooHallStatus;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.bazoo.util.TimeComparisonUtils;
import com.gameserver.bazoosignin.HumanBazooSignIn;
import com.gameserver.bazoosignin.msg.CGBazooSignin;
import com.gameserver.bazoosignin.msg.GCBazooSignin;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.player.Player;

public class BazooSignInMessageHandler {

	public void handleBazooSignin(Player player, CGBazooSignin cgBazooSignin) {
		Logger logger = Loggers.BAZOO;
		Human human = player.getHuman();
		HumanBazooSignIn humanBazooSignIn  = human.getHumanBazooSignInManager().getHumanBazooSignIn();
		try {
			int comResult = TimeComparisonUtils.compareNowWithPassTimeTwo(humanBazooSignIn.getSignInTime());
			//可以领取  跨天了 重新 置为10次
			if((comResult == 0 && humanBazooSignIn.getTimes()<10) || comResult == 1){
				GCBazooSignin GCBazooSignin = new GCBazooSignin();
				List<Integer> diceValues = new ArrayList<Integer>();
				humanBazooSignIn.setSignInTime(new Date());
				if(comResult == 1){
					humanBazooSignIn.setTimes(1);
				}else{
					humanBazooSignIn.setTimes(humanBazooSignIn.getTimes()+1);
				}
				
				humanBazooSignIn.setModified();
				
				Globals.getBazooPubService().getShakeDice().shakeAll(diceValues);
				int nameInt = Globals.getBazooShowHandService().judgeDice(diceValues);
				if(nameInt == 6){
					nameInt=7;
				}else if(nameInt == 0){//等于散点   判断一下 是否是顺子
					//排序
					DiceUtils.sortDiceList(diceValues);
					int distance = diceValues.get((diceValues.size()-1))-diceValues.get(0);
					if(Math.abs(distance) == 4){
						nameInt=6;//顺子
					}
				}
				int gold = Globals.getBazooPubService().getDiceSignInMap().get(nameInt).getOfferGold();
				
				String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_BAZOO_SIGNIN.getReasonText(), null,gold);
				human.giveMoney(gold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BAZOO_SIGNIN, detailReason, -1, 1);
				int[] diceArr = DiceUtils.getArrFromDiceList(diceValues);
				GCBazooSignin.setDiceValues(diceArr);
				GCBazooSignin.setNameInt(nameInt);
				GCBazooSignin.setGold(gold);
				player.sendMessage(GCBazooSignin);
				
				String diceStr = DiceUtils.getStrFromDiceList(diceValues);
				String name = getName(nameInt);
				logger.info("[无双吹牛]---[签到]---[用户ID:"+player.getPassportId()+"]---[色子值="+diceStr+",nameInt="+nameInt+",name="+name+",gold="+gold+"]");
				//打印日志 和 输出日志
				Globals.getLogService().sendDiceSignInLog(human, DiceSignInLogReason.curDiceValues, DiceSignInLogReason.curDiceValues.getReasonText(), 0, null, diceStr, nameInt+"-"+name+"-"+gold);
				
				GCBazooHallStatus GCBazooHallStatus = player.getHuman().getHumanBazooManager().getStatus();
				player.sendMessage(GCBazooHallStatus);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String getName(int nameInt){
		if(nameInt == 7){
			return "豹子";
		}else if(nameInt == 6){
			return "顺子";
		}else if(nameInt == 5){
			return "炸弹";
		}else if(nameInt == 4){
			return "葫芦";
		}else if(nameInt == 3){
			return "三条";
		}else if(nameInt == 2){
			return "两对";
		}else if(nameInt == 1){
			return "对子";
		}else if(nameInt == 0){
			return "散点";
		}else{
			return "";
		}
	}

}
