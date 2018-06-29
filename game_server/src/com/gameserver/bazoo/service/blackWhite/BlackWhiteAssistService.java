package com.gameserver.bazoo.service.blackWhite;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.data.BlackWhiteBet;
import com.gameserver.bazoo.data.BlackWhiteDiceInfo;
import com.gameserver.bazoo.data.BlackWhiteNum;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.GCBlackWhiteAllSwing;
import com.gameserver.bazoo.msg.GCBlackWhiteCallNum;
import com.gameserver.bazoo.msg.GCBlackWhiteEndCount;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.bazoo.util.DiceInfoUtil;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.bazoo.util.ListToArrUtils;
import com.gameserver.player.Player;

/**
 * 黑白单双的 辅助 service
 * @author JavaServer
 *
 */
public class BlackWhiteAssistService {
	protected Logger logger = Loggers.BAZOO;
	public void sendDiceMessage(Player p,long[] passportIds,int[] leftDiceNum, List<Integer> list,PlayerInfo info) {
		GCBlackWhiteAllSwing GCBlackWhiteAllSwing = new GCBlackWhiteAllSwing();
		GCBlackWhiteAllSwing.setPassportId(passportIds);
		GCBlackWhiteAllSwing.setDiceValues(DiceUtils.getArrFromDiceList(list));
		GCBlackWhiteAllSwing.setBlackWhiteNum(this.setNum(list));
		GCBlackWhiteAllSwing.setMultiple(info.getTimes());
		GCBlackWhiteAllSwing.setLeftDiceNum(leftDiceNum);
		p.sendBazooMessage(GCBlackWhiteAllSwing);
	}


	/**
	 * 给所有人发送消息
	 * @param players
	 * @param info 
	 */
	public GCBlackWhiteCallNum sendAllDiceMessage(Player player,int diceType,int times,List<Player> players, PlayerInfo info) {
		GCBlackWhiteCallNum GCBlackWhiteCallNum = new GCBlackWhiteCallNum();
		GCBlackWhiteCallNum.setWhoCallPassportId(player.getPassportId());
		GCBlackWhiteCallNum.setDiceType(diceType);
		
		
		//先组装消息 消息要发给所有人
		List<BlackWhiteDiceInfo> DiceInfoList = new ArrayList<BlackWhiteDiceInfo>();
		//计算 几杀
		info.setKillNum(info.howManyKillNum());
		for(Player p :players){
			if(p.getHuman().getBazooRoomEveryUserInfo().getUserStatus() != RoomEveryUserInfo.USER_STATUS_PARTIN){
				continue;
			}
			BlackWhiteDiceInfo DiceInfo = new BlackWhiteDiceInfo();
			List<Integer> perDiceValues = p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getPerdiceValues();
			int isOut = p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getIsOut();
			DiceInfo.setIsOut(isOut);
			DiceInfo.setPassportId(p.getPassportId());
			DiceInfo.setDiceValues(DiceUtils.getArrFromDiceList(perDiceValues));
			DiceInfo.setRemoveDiceValues(DiceUtils.getArrFromDiceList(p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getRemoveDiceIndex()));
			DiceInfoList.add(DiceInfo);
		}
		//组装每次的结算消息
		List<BlackWhiteBet> BlackWhiteBetList = new ArrayList<BlackWhiteBet>();
		List<Integer> betList = new ArrayList<Integer>();
		List<Integer> winPasportIdList = new ArrayList<Integer>();
		for(Player p :info.getAllInPlayers()){
			if(p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getIsOut() == 0){
				betList.add(p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getMultiple());
				winPasportIdList.add(Long.valueOf(p.getPassportId()).intValue());
			}
		}
		for(Player p :info.getAllInPlayers()){
			BlackWhiteBet BlackWhiteBet = new BlackWhiteBet();
			BlackWhiteBet.setPassportId(p.getPassportId());
//			if(p.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getIsOut() == 0){
//				BlackWhiteBet.setBet(new int[0]);
//				BlackWhiteBet.setWinPassportIds(new int[0]);
//			}else{
				BlackWhiteBet.setBet(ListToArrUtils.IntegerlistToArr(betList));
				BlackWhiteBet.setWinPassportIds(ListToArrUtils.IntegerlistToArr(winPasportIdList));
//			}
			BlackWhiteBet.setGold(p.getHuman().getGold());
			BlackWhiteBetList.add(BlackWhiteBet);
		}
		GCBlackWhiteCallNum.setBlackWhiteDiceInfo(DiceInfoUtil.getArrFromEndListTwo(DiceInfoList));
		GCBlackWhiteCallNum.setBlackWhiteBet(DiceInfoUtil.getArrFromEndListThree(BlackWhiteBetList));
		GCBlackWhiteCallNum.setKillNum(info.getKillNum());
		GCBlackWhiteCallNum.setMultiple(times);
		//把 几杀 设置到当前用户里
		int personKillNum = player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().getKillNum();
		player.getHuman().getBazooRoomEveryUserInfo().getBlackWhiteInfo().setKillNum(personKillNum+info.getKillNum());
		//在给每个人 发送消息
		for(Player p :players){
			p.sendBazooMessage(GCBlackWhiteCallNum);
		}
		
		return GCBlackWhiteCallNum;
	}

	public GCBlackWhiteEndCount sendEndCount(List<Player> players,int bet) {
		GCBlackWhiteEndCount GCBlackWhiteEndCount = new GCBlackWhiteEndCount();
		
		List<Player> winPassportIdList = new ArrayList<Player>();
		List<Integer> winPassportIdBet = new ArrayList<Integer>();
		for(Player p:players){
			if(p.getHuman().getBazooRoomEveryUserInfo().getMoney() > 0){
				winPassportIdList.add(p);
				winPassportIdBet.add(Long.valueOf(p.getHuman().getBazooRoomEveryUserInfo().getMoney()/bet).intValue());
			}
		}
		
		List<EndCountInfo> EndCountInforList = new ArrayList<EndCountInfo>();
		for(Player p:players){
			EndCountInfo EndCountInfo = new EndCountInfo();
			EndCountInfo.setPassportId(p.getPassportId());
			EndCountInfo.setMultiple(p.getHuman().getBazooRoomEveryUserInfo().getMultiple());
			EndCountInfo.setName("");
			EndCountInfo.setGold(p.getHuman().getBazooRoomEveryUserInfo().getMoney());
			EndCountInfo.setPersonTotalGold(p.getHuman().getGold());
			EndCountInfo.setWinPassportId(ListToArrUtils.listToIntegerArr(winPassportIdList));
			EndCountInfo.setWinMultiple(DiceUtils.getArrFromDiceList(winPassportIdBet));
			EndCountInfo.setDiceValues(new int[0]);
			EndCountInforList.add(EndCountInfo);
			
			logger.info("[无双吹牛]---[红黑单双模式 结算]---[用户ID      ::"+p.getPassportId()+"]");
			logger.info("[无双吹牛]---[红黑单双模式 结算]---[getMoney   ::"+p.getHuman().getBazooRoomEveryUserInfo().getMoney()+"]");
			logger.info("[无双吹牛]---[红黑单双模式 结算]---[getMultiple::"+p.getHuman().getBazooRoomEveryUserInfo().getMultiple()+"]");
		}
		
		GCBlackWhiteEndCount.setEndCountInfo(DiceInfoUtil.getArrFromEndList(EndCountInforList));
		for(Player p:players){
			p.sendBazooMessage(GCBlackWhiteEndCount);
		}
		return GCBlackWhiteEndCount;
	}

	/**
	 *
	 * 1:红色点数：1、4
	 * 2:黑色点数：2、3、5、6
	 * 
	 * 3:单数点数：1、3、5
	 * 4:双数点数：2、4、6
	 *
	 *
	 */
	public BlackWhiteNum setNum(List<Integer> diceValues){
		int red=0;
		int black=0;
		int single=0;
		int doubles=0;
		for(int i=0;i<diceValues.size();i++){
			int value = diceValues.get(i);
			if(value == 1 || value == 4){//红色
				red++;
			}
			if(value == 2 || value == 3 || value == 5 || value == 6){//黑色色
				black++;
			}
			if(value == 1 || value == 3 || value == 5 ){//单
				single++;
			}
			if(value == 2 || value == 4 || value == 6){//双
				doubles++;
			}
		}
		
		BlackWhiteNum BlackWhiteNum = new BlackWhiteNum();
		BlackWhiteNum.setRed(red);
		BlackWhiteNum.setBlack(black);
		BlackWhiteNum.setSingle(single);
		BlackWhiteNum.setDoubles(doubles);
		return BlackWhiteNum;
	}
}
