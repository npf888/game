package com.gameserver.bazoo.service.blackWhite;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.player.Player;

/**
 * 红黑单双  的逻辑
 * @author JavaServer
 *
 * 1:红色点数：1、4
 * 2:黑色点数：2、3、5、6
 * 
 * 3:单数点数：1、3、5
 * 4:双数点数：2、4、6
 *
 *
 */
public class BlackWhiteLogic {
	private Logger logger = Loggers.BAZOO;
	//用户叫完号之后消除相应的点数
	public void logic(List<Player> players,int oneOfFour){
		//红色
		if(oneOfFour == 1){
			for(Player player:players){
				List<Integer> diceValues = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
				List<Integer> perDiceValues = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getPerdiceValues();
				perDiceValues.clear();
				//这个集合 要发到前端
				perDiceValues.addAll(diceValues);
				
				List<Integer> removeDice = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDice();
				//这个集合 要发到前端
				List<Integer> removeDiceIndex = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDiceIndex();
				removeDice.clear();
				removeDiceIndex.clear();
				for(int i=0;i<diceValues.size();i++){
					Integer dice = diceValues.get(i);
					if(dice.intValue() == 1 || dice.intValue() == 4){
						removeDice.add(dice);
						removeDiceIndex.add(i);
					}
				}
				diceValues.removeAll(removeDice);	
				logger.info("[无双吹牛]---[消号]---[playerID::"+player.getPassportId()+"]");
				logger.info("[无双吹牛]---[消号]---[perDiceValues::"+DiceUtils.getStrFromDiceList(perDiceValues)+"]");
				logger.info("[无双吹牛]---[消号]---[removeDice::"+DiceUtils.getStrFromDiceList(removeDice)+"]");
				logger.info("[无双吹牛]---[消号]---[========================================================]");
			}
		//黑色
		}else if(oneOfFour == 2){
			for(Player player:players){
				List<Integer> diceValues = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
				List<Integer> perDiceValues = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getPerdiceValues();
				perDiceValues.clear();
				//这个集合 要发到前端
				perDiceValues.addAll(diceValues);
				
				List<Integer> removeDice = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDice();
				//这个集合 要发到前端
				List<Integer> removeDiceIndex = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDiceIndex();
				removeDice.clear();
				removeDiceIndex.clear();
				for(int i=0;i<diceValues.size();i++){
					Integer dice = diceValues.get(i);
					if(dice.intValue() == 2 || dice.intValue() == 3 || dice.intValue() == 5 || dice.intValue() == 6){
						removeDice.add(dice);
						removeDiceIndex.add(i);
					}
				}
				diceValues.removeAll(removeDice);	
				logger.info("[无双吹牛]---[消号]---[playerID::"+player.getPassportId()+"]");
				logger.info("[无双吹牛]---[消号]---[perDiceValues::"+DiceUtils.getStrFromDiceList(perDiceValues)+"]");
				logger.info("[无双吹牛]---[消号]---[removeDice::"+DiceUtils.getStrFromDiceList(removeDice)+"]");
				logger.info("[无双吹牛]---[消号]---[========================================================]");
			}
		//单数
		}else if(oneOfFour == 3){
			for(Player player:players){
				List<Integer> diceValues = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
				List<Integer> perDiceValues = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getPerdiceValues();
				perDiceValues.clear();
				//这个集合 要发到前端
				perDiceValues.addAll(diceValues);
				
				List<Integer> removeDice = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDice();
				//这个集合 要发到前端
				List<Integer> removeDiceIndex = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDiceIndex();
				removeDice.clear();
				removeDiceIndex.clear();
				for(int i=0;i<diceValues.size();i++){
					Integer dice = diceValues.get(i);
					if(dice.intValue() == 1 || dice.intValue() == 3 || dice.intValue() == 5){
						removeDice.add(dice);
						removeDiceIndex.add(i);
					}
				}
				diceValues.removeAll(removeDice);	
				logger.info("[无双吹牛]---[消号]---[playerID::"+player.getPassportId()+"]");
				logger.info("[无双吹牛]---[消号]---[perDiceValues::"+DiceUtils.getStrFromDiceList(perDiceValues)+"]");
				logger.info("[无双吹牛]---[消号]---[removeDice::"+DiceUtils.getStrFromDiceList(removeDice)+"]");
				logger.info("[无双吹牛]---[消号]---[========================================================]");
			}
		//双数
		}else if(oneOfFour == 4){
			for(Player player:players){
				List<Integer> diceValues = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
				List<Integer> perDiceValues = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getPerdiceValues();
				perDiceValues.clear();
				//这个集合 要发到前端
				perDiceValues.addAll(diceValues);
				
				List<Integer> removeDice = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDice();
				//这个集合 要发到前端
				List<Integer> removeDiceIndex = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDiceIndex();
				removeDice.clear();
				removeDiceIndex.clear();
				for(int i=0;i<diceValues.size();i++){
					Integer dice = diceValues.get(i);
					if(dice.intValue() == 2 || dice.intValue() == 4 || dice.intValue() == 6){
						removeDice.add(dice);
						removeDiceIndex.add(i);
					}
				}
				diceValues.removeAll(removeDice);	
				logger.info("[无双吹牛]---[消号]---[playerID::"+player.getPassportId()+"]");
				logger.info("[无双吹牛]---[消号]---[perDiceValues::"+DiceUtils.getStrFromDiceList(perDiceValues)+"]");
				logger.info("[无双吹牛]---[消号]---[removeDice::"+DiceUtils.getStrFromDiceList(removeDice)+"]");
				logger.info("[无双吹牛]---[消号]---[========================================================]");
			}
		}
	}
	
	
	/**
	 * 扣钱 （所有没有出局的人 给出局的人 发钱）
	 * @param list 
	 */
	public void countMoney(List<Player> players,int costMoney,int bet,PlayerInfo info){
		List<Player> allLeftPlayers = new ArrayList<Player>();
		//先过滤掉 出局的
		for(Player player:players){
			int isOut = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getIsOut();
			if(isOut == 0){//没有出局 0：否     1：是
				allLeftPlayers.add(player);
			}
		}
		
		//在计算 剩下的人
		List<Player> leftPlayers = new ArrayList<Player>();
		List<Player> outPlayers = new ArrayList<Player>();
		//每次摇 所有的人
		List<Player> allinPlayers = info.getAllInPlayers();
		allinPlayers.clear();
		//扣掉出局人的钱
		for(Player player:allLeftPlayers){
			List<Integer> diceValues = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			//出局了就要被扣钱
			if(diceValues.size() == 0){
				outPlayers.add(player);
			}else{
				leftPlayers.add(player);
			}
		}
		//加进去
		allinPlayers.addAll(leftPlayers);
		allinPlayers.addAll(outPlayers);
		
		
		if(outPlayers.size() == 0){
			for(Player player:leftPlayers){
				//没有出局 的就不输 不赢
				player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setGold(0);
				player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setMultiple(0);
					
			}
			return;
		}
		
		long totalWinMoney = 0;
		//出局的人数
		int loserNum = outPlayers.size();
		//给没有出局的人 加钱
		for(Player player:leftPlayers){
			List<Integer> diceValues = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			int leftNum = diceValues.size();
			//出现了 击杀，那么就会多赢 1倍，输家 多输1倍
			long winMoney = costMoney*leftNum*loserNum;
			totalWinMoney+=winMoney;
			player.getHuman().giveMoney(winMoney, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BLACK_WHITE_GIVE_GOLD, LogReasons.GoldLogReason.BAZOO_BLACK_WHITE_GIVE_GOLD.toString(), -1, 1);
			long money = player.getHuman().getBazooRoomEveryUserInfo().getMoney();
			long afterMoney = money+winMoney;
			int afterBet = Long.valueOf(Math.abs((afterMoney)/bet)).intValue();
			player.getHuman().getBazooRoomEveryUserInfo().setMoney(afterMoney);
			player.getHuman().getBazooRoomEveryUserInfo().setMultiple(afterBet);
			 
			//单次  赢钱
			int afterBetSingle = Long.valueOf(winMoney/bet).intValue();
			player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setGold(winMoney);
			player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setMultiple(afterBetSingle);
			
		}
		
		//每个人输的钱 都一样
		long loseMoney = totalWinMoney/loserNum;
		for(Player player:outPlayers){
			player.getHuman().costMoney(loseMoney, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BLACK_WHITE_COST_GOLD, LogReasons.GoldLogReason.BAZOO_BLACK_WHITE_COST_GOLD.toString(), -1, 1);
			//总和
			long money = player.getHuman().getBazooRoomEveryUserInfo().getMoney();
			long afterMoney = money-loseMoney;
			int afterBet = Long.valueOf(Math.abs((afterMoney)/bet)).intValue();
			player.getHuman().getBazooRoomEveryUserInfo().setMoney(afterMoney);
			player.getHuman().getBazooRoomEveryUserInfo().setMultiple(afterBet);
			
			//单次  输钱
			int afterBetSingle = Long.valueOf(loseMoney/bet).intValue();
			player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setGold(-loseMoney);
			player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setMultiple(afterBetSingle);
			//设置出局
			player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setIsOut(1);
		}
		
		// 三杀  四杀 五杀  单独算
		Player curPlayer = info.getLastMan();//当前叫号用户
		long winMoney=0l;
		long winBet=0l;
		if(loserNum>=2){
			for(Player player:outPlayers){
				winMoney+=bet;
				winBet++;
				player.getHuman().costMoney(bet, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BLACK_WHITE_COST_GOLD, LogReasons.GoldLogReason.BAZOO_BLACK_WHITE_COST_GOLD.toString(), -1, 1);
				//总和
				long money = player.getHuman().getBazooRoomEveryUserInfo().getMoney();
				long afterMoney = money-bet;
				int afterBet = Long.valueOf(Math.abs((afterMoney)/bet)).intValue();
				player.getHuman().getBazooRoomEveryUserInfo().setMoney(afterMoney);
				player.getHuman().getBazooRoomEveryUserInfo().setMultiple(afterBet);
				
				//单次  输钱
				long gold = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getGold();
				long afterGold = gold-bet;
				int afterBetSingle = Long.valueOf(afterGold/bet).intValue();
				player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setGold(afterGold);
				player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setMultiple(afterBetSingle);
			}
			
			//计算当前用户
			curPlayer.getHuman().giveMoney(winMoney, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_BLACK_WHITE_COST_GOLD, LogReasons.GoldLogReason.BAZOO_BLACK_WHITE_COST_GOLD.toString(), -1, 1);
			//总和
			long money = curPlayer.getHuman().getBazooRoomEveryUserInfo().getMoney();
			long afterMoney = money+winMoney;
			int afterBet = Long.valueOf(Math.abs((afterMoney)/bet)).intValue();
			curPlayer.getHuman().getBazooRoomEveryUserInfo().setMoney(afterMoney);
			curPlayer.getHuman().getBazooRoomEveryUserInfo().setMultiple(afterBet);
			
			//单次  输钱
			long gold = curPlayer.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getGold();
			long afterGold = gold+winMoney;
			int afterBetSingle = Long.valueOf(afterGold/bet).intValue();
			curPlayer.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setGold(afterGold);
			curPlayer.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setMultiple(afterBetSingle);
		}
		
		
		
	}
	
	
	/**
	 * 判断是否结束
	 */
	public boolean judgeEnd(List<Player> players){
		List<Player> leftPlayers = new ArrayList<Player>();
		for(Player player:players){
			List<Integer> diceValues = player.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			//出局了就要被扣钱
			if(diceValues.size() > 0){
				leftPlayers.add(player);
			}
		}
		//大于等于 2个 就没有结束
		if(leftPlayers.size()>=2){
			return false;
		}
		
		return true;
	}


	/**
	 * 统计
	 * @param playersPartIn
	 */
	public void countStatic(List<Player> playersPartIn,PlayerInfo info) {
		//计算连胜
		Globals.getBazooPubService().countWinTimes(playersPartIn,RoomNumber.MODE_TYPE_BLACK_WHITE);
		
		for(Player player:playersPartIn){
			//任务 或者成就
			Globals.getHumanBazooTaskService().finishTask(player);
			//排行榜
			Globals.getHumanBankService().addGold(player,player.getHuman().getBazooRoomEveryUserInfo().getMoney());
		}

		//个人的 信息变化记录
		for(Player p:playersPartIn){
			Globals.getHumanBazooPersonalService().addGold(p, p.getHuman().getBazooRoomEveryUserInfo().getMoney(),info);
		}
		
		
	}
}
