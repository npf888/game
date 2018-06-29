package com.gameserver.bazoo.service.showHand;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.bazoo.data.DiceInfo;
import com.gameserver.bazoo.data.EndCountInfo;
import com.gameserver.bazoo.data.ShowHandBet;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.msg.GCShowHandEndCount;
import com.gameserver.bazoo.msg.GCShowHandLittleSwing;
import com.gameserver.bazoo.msg.GCShowHandSingleSwing;
import com.gameserver.bazoo.msg.GCStateRoomShowHandSingleSwing;
import com.gameserver.bazoo.service.room.BazooPubService;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoo.util.DiceUtils;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 梭哈模式的service
 * @author JavaServer
 *
 */
public class AssistShowHandService {

	public void swingSingle(Player player) {
		
		
	}

	public GCShowHandSingleSwing sendGCShowHandSingleSwing(List<Integer> newDices,Player player,Card card,Room room) {
		//告诉所有人 我摇的是啥
		GCShowHandSingleSwing GCShowHandSingleSwing = new GCShowHandSingleSwing();
		int[] newDiceArr = new int[newDices.size()];
		for(int i=0;i<newDices.size();i++){
			newDiceArr[i]=newDices.get(i);
		}
		GCShowHandSingleSwing.setDiceValues(newDiceArr);
		GCShowHandSingleSwing.setPassportId(player.getPassportId());
		GCShowHandSingleSwing.setNameInt(card.getCardNum());
		List<ShowHandBet> ShowHandBetList = new ArrayList<ShowHandBet>();
		for(Player p:room.getPlayersPartIn()){
			ShowHandBet ShowHandBet = new ShowHandBet();
			ShowHandBet.setPassportId(p.getPassportId());
			//这里设置的是  每一局 当中，每一次 的 输赢
			ShowHandBet.setBet(p.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getMultiple());
			ShowHandBet.setGold(p.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getMoney());
			ShowHandBet.setPersonTotalGold(p.getHuman().getGold());
			ShowHandBetList.add(ShowHandBet);
		}
		ShowHandBet[] ShowHandBetArr = new ShowHandBet[ShowHandBetList.size()];
		for(int i=0;i<ShowHandBetList.size();i++){
			ShowHandBetArr[i]=ShowHandBetList.get(i);
		}
		GCShowHandSingleSwing.setShowHandBet(ShowHandBetArr);
		GCShowHandSingleSwing.setLeftTimes(room.getPlayerInfo().getLeftTimes());
		room.sendMessage(room.getPlayers(), GCShowHandSingleSwing);
		
		return GCShowHandSingleSwing;
		
	}

	public void sendGCShowHandLittleSwing(List<Player> lessPlayers,Room room) {
		GCShowHandLittleSwing GCShowHandLittleSwing = new GCShowHandLittleSwing();
		List<Long> passportIds = new ArrayList<Long>();
		for(Player p:lessPlayers){
			passportIds.add(p.getPassportId());
		}
		long[] passportIdArr = new long[passportIds.size()];
		for(int i=0;i<passportIds.size();i++){
			passportIdArr[i]=passportIds.get(i);
		}
		GCShowHandLittleSwing.setPassportId(passportIdArr);
		
		room.sendMessage(room.getPlayers(), GCShowHandLittleSwing);
		
	}

	public void sendGCShowHandEndCount(Room room) {
		GCShowHandEndCount GCShowHandEndCount = new GCShowHandEndCount();
		List<EndCountInfo> EndCountInfoList = new ArrayList<EndCountInfo>();
		List<Player> playerPartIns = room.getPlayersPartIn();
		for(Player p:playerPartIns){
			EndCountInfo EndCountInfo = new EndCountInfo();
			EndCountInfo.setPassportId(p.getPassportId());
			EndCountInfo.setName(p.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard().getCardName());
			EndCountInfo.setMultiple(p.getHuman().getBazooRoomEveryUserInfo().getMultiple());
			EndCountInfo.setGold(p.getHuman().getBazooRoomEveryUserInfo().getMoney());
			EndCountInfo.setPersonTotalGold(p.getHuman().getGold());
			EndCountInfo.setDiceValues(DiceUtils.getArrFromDiceList(p.getHuman().getBazooRoomEveryUserInfo().getDiceValues()));
			EndCountInfo.setWinMultiple(new int[0]);
			EndCountInfo.setWinPassportId(new int[0]);
			EndCountInfoList.add(EndCountInfo);
		}
		EndCountInfo[] EndCountInfoArr = new EndCountInfo[EndCountInfoList.size()];
		for(int i=0;i<EndCountInfoList.size();i++){
			EndCountInfoArr[i]=EndCountInfoList.get(i);
		}
		GCShowHandEndCount.setEndCountInfo(EndCountInfoArr);
		
		room.sendMessage(room.getPlayers(), GCShowHandEndCount);
		//计算连胜
		Globals.getBazooPubService().countWinTimes(room.getPlayersPartIn(),RoomNumber.MODE_TYPE_SHOWHAND);
		
		for(Player player:playerPartIns){
			//任务 或者成就
			Globals.getHumanBazooTaskService().finishTask(player);
		}
		//个人的 信息变化记录
		for(Player p:room.getPlayersPartIn()){
			Globals.getHumanBazooPersonalService().addGold(p, p.getHuman().getBazooRoomEveryUserInfo().getMoney(),null);
		}
	}

	public GCStateRoomShowHandSingleSwing setGCStateRoomShowHandSingleSwing(GCShowHandSingleSwing GCShowHandSingleSwing,Room room,Player player,long time ) {
		//设置状态
		GCStateRoomShowHandSingleSwing GCStateRoomShowHandSingleSwing = new GCStateRoomShowHandSingleSwing();
		PlayerInfo playerInfo = room.getPlayerInfo();
		long[] shouldPassportIds = new long[playerInfo.getLastMans().size()];
		for(int i=0;i<playerInfo.getLastMans().size();i++){
			shouldPassportIds[i]=playerInfo.getLastMans().get(i).getPassportId();
		}
		GCStateRoomShowHandSingleSwing.setPassportId(shouldPassportIds);
		GCStateRoomShowHandSingleSwing.setLeftTimes(playerInfo.getLeftTimes());
		if(player != null){
			GCStateRoomShowHandSingleSwing.setLastPassportId(player.getPassportId());
		}else{
			GCStateRoomShowHandSingleSwing.setLastPassportId(0);
		}
		List<Player> pList = room.getPlayersPartIn();
		List<DiceInfo> DiceInfoList = new ArrayList<DiceInfo>();
		for(int i=0;i<pList.size();i++){
			DiceInfo DiceInfo = new DiceInfo();
			Player p = pList.get(i);
			List<Integer> diceValues = p.getHuman().getBazooRoomEveryUserInfo().getDiceValues();
			int cardNum = p.getHuman().getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard().getCardNum();
			DiceInfo.setCowNameInt(cardNum);
			DiceInfo.setPassportId(p.getPassportId());
			int[] diceValueArr = new int[diceValues.size()]; 
			for(int j=0;j<diceValues.size();j++){
				diceValueArr[j]=diceValues.get(j);
			}
			DiceInfo.setDiceValues(diceValueArr);
			DiceInfo.setRedDiceValues(new int[0]);
			DiceInfoList.add(DiceInfo);
		}
		
		DiceInfo[] diceValueArr = new DiceInfo[DiceInfoList.size()]; 
		for(int j=0;j<DiceInfoList.size();j++){
			diceValueArr[j]=DiceInfoList.get(j);
		}
		GCStateRoomShowHandSingleSwing.setDiceInfo(diceValueArr);
		if(GCShowHandSingleSwing != null){
			GCStateRoomShowHandSingleSwing.setShowHandBet(GCShowHandSingleSwing.getShowHandBet());
		}else{
			GCStateRoomShowHandSingleSwing.setShowHandBet(new ShowHandBet[0]);
		}
		GCStateRoomShowHandSingleSwing.setLeftSecond(time);
		return GCStateRoomShowHandSingleSwing;
		
	}

	public void costTheWinnerGold(BazooPubService bazooPubService, Room room) {
		List<Player> playerPartIns = room.getPlayersPartIn();
		for(Player player:playerPartIns){
			long money = player.getHuman().getBazooRoomEveryUserInfo().getMoney();
			if(money > 0){//说明赢了钱了  扣掉流水
				bazooPubService.costWinnerMoney(player, money);
			}
		}
		
	}
	
	
	
}
