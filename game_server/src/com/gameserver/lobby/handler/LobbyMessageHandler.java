package com.gameserver.lobby.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.lobby.data.JackpotList;
import com.gameserver.lobby.data.JackpotfitLevelData;
import com.gameserver.lobby.enums.GameType;
import com.gameserver.lobby.msg.CGAllSlotNewJackpots;
import com.gameserver.lobby.msg.CGGametypeJackpot;
import com.gameserver.lobby.msg.CGJackpotListData;
import com.gameserver.lobby.msg.CGNewJackpot;
import com.gameserver.lobby.msg.CGSlotNewJackpots;
import com.gameserver.lobby.msg.GCAllSlotNewJackpots;
import com.gameserver.lobby.msg.GCGametypeJackpot;
import com.gameserver.lobby.msg.GCJackpotLevelData;
import com.gameserver.lobby.msg.GCJackpotListData;
import com.gameserver.lobby.msg.GCNewJackpot;
import com.gameserver.lobby.msg.GCSlotNewJackpots;
import com.gameserver.player.Player;
import com.gameserver.slot.Slot;
import com.gameserver.slot.template.SlotsListTemplate;

/**
 * 休息处理类
 * @author 郭君伟
 *  
 */
public class LobbyMessageHandler {

	private Logger logger = LoggerFactory.getLogger(LobbyMessageHandler.class);
	
	public void handleJackpotListData(Player player, CGJackpotListData cgJackpotListData) {
	    int operationType = cgJackpotListData.getOperationType();
	    
		if(operationType == 1){
			List<JackpotList> list = Globals.getJackpotServer().getJackList();
			JackpotList[] listNum = new JackpotList[list.size()];
			for(int i = 0;i < list.size();i++){
				listNum[i] = list.get(i);
			}
			GCJackpotListData gcmessage = new GCJackpotListData();
			gcmessage.setJackpotList(listNum);
			player.sendMessage(gcmessage);
		}else if(operationType == 2){
			
			Human human = player.getHuman();
			//玩家等级
			int level = (int)human.getLevel();
			
			List<JackpotfitLevelData> list = new ArrayList<JackpotfitLevelData>();
			
			//百家乐最大适合等级彩金
			long jackpotBaccart = Globals.getBaccartService().getbaccartJackpotbyLevel();
			list.add(newData(GameType.BACCART.getIndex(),jackpotBaccart));
			
			Map<Integer,Long> slotMap = Globals.getSlotService().getLevelJackpot();
			
			for(Entry<Integer,Long> enn : slotMap.entrySet()){
				list.add(newData(Globals.getJackpotServer().getSlotType(enn.getKey()),enn.getValue()));
			}
			
			long texas = Globals.getTexasService().getTexasJackpot();
			list.add(newData(GameType.TEXAS.getIndex(),texas));
			
			JackpotfitLevelData[] data = new JackpotfitLevelData[list.size()];
			
			for(int i = 0 ; i< list.size();i++){
				data[i] = list.get(i);
			}
			
			GCJackpotLevelData gCJackpotLevelData = new GCJackpotLevelData();
			gCJackpotLevelData.setJackpotfitLevelData(data);
			player.sendMessage(gCJackpotLevelData);
			
		}
		
	}
	
	
	
	
	private JackpotfitLevelData newData(int gameType,long jackpot){
		JackpotfitLevelData data = new JackpotfitLevelData();
		data.setGameType(gameType);
		data.setJackpot(jackpot);
		return data;
	}



    /**
     * 请求具体的游戏类型彩金
     * @param player
     * @param cgGametypeJackpot
     */
	public void handleGametypeJackpot(Player player, CGGametypeJackpot cg) {
		
		
		int gameType = cg.getGameType();
		int gameid = cg.getGameId();
		long jackpot = Globals.getSlotService().getSlotJack(gameid);;
		
		/*
		GameType type = GameType.indexOf(gameType);
		
		if(type == GameType.BACCART){
			jackpot = Globals.getBaccartService().baccartRoomById(gameid).getBaccarRoomModel().getJackpot();
		}else if(type == GameType.TEXAS){
			jackpot = Globals.getTexasService().getTextas(gameid).getJackpot();
		}else{
			jackpot = Globals.getSlotService().getSlotJack(gameid);
		}*/
		
		GCGametypeJackpot gc = new GCGametypeJackpot();
		gc.setGameType(gameType);
		gc.setGameId(gameid);
		gc.setGamejackpot(jackpot);
		player.sendMessage(gc);
	}




	public void handleNewJackpot(Player player, CGNewJackpot cgNewJackpot) {
		int curBet = cgNewJackpot.getBet();
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
		SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		/**
		 * 存放用户 在哪个老虎机的信息
		 */
		Globals.getSlotService().enterSlot(slot, player, curBet);
		//如果是关闭的 就不发消息
		if(slotsListTemplate.getJackpotswitch() == 2){
			return;
		}
		GCNewJackpot gCNewJackpot  = new GCNewJackpot();
		if(curBet ==slotsListTemplate.getBet1() ){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot1()).intValue());
			
		}else if(curBet ==slotsListTemplate.getBet2()){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot2()).intValue());
			
		}else if(curBet ==slotsListTemplate.getBet3()){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot3()).intValue());
			
		}else if(curBet ==slotsListTemplate.getBet4()){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot4()).intValue());
			
		}else if(curBet ==slotsListTemplate.getBet5()){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot5()).intValue());
			
		}
		player.sendMessage(gCNewJackpot);
	}




	public void handleSlotNewJackpots(Player player,
			CGSlotNewJackpots cgSlotNewJackpots) {
		int slotType = cgSlotNewJackpots.getSlotType();
		List<Integer> jackPotNumList = new ArrayList<Integer>();
		List<Long> jackPotList = new ArrayList<Long>();
		Map<Integer,Slot> slotMap = Globals.getSlotService().getSlotList();
		//东方龙特殊处理
		if(slotType == 23){
			/**
			 * 把特殊的 东方龙加上
			 */
			List<SlotsListTemplate> sList  = Globals.getSlotService().getSlotsListTemplateList(23);
			for(int i=0;i<sList.size();i++){
				SlotsListTemplate slt = sList.get(i);
				Slot slot23 = slotMap.get(slt.getId());
				String jackpot23 = slot23.getSlotType23Jackpot();
				String jackpot23MaxValueStr = jackpot23.split(";")[4].split("=")[1];
				long jackpot23MaxValue = Long.valueOf(jackpot23MaxValueStr.split(",")[4]);
				jackPotNumList.add(i+1);
				jackPotList.add(jackpot23MaxValue);
				
			}
		}else{
			List<Slot> slotList = new ArrayList<Slot>();
			for(Slot slotOne:slotMap.values()){
				if(slotOne.getType() == slotType){
					slotList.add(slotOne);
				}
			}
			//从小到大 排序
			Collections.sort(slotList, new Comparator<Slot>(){
				@Override
				public int compare(Slot o1, Slot o2) {
					if(o1.getTempleId() > o2.getTempleId()){
						return 1;
					}
					return -1;
				}
				
			});
			//排序
			List<SlotsListTemplate> sList  = Globals.getSlotService().getSlotsListTemplateList(slotType);
			Collections.sort(sList, new Comparator<SlotsListTemplate>(){
				@Override
				public int compare(SlotsListTemplate o1, SlotsListTemplate o2) {
					if(o1.getId() > o2.getId()){
						return 1;
					}
					return -1;
				}
				
			});
			//slotsTemplateId 对应 的 位置
			Map<Integer,Integer> positionMap  = new HashMap<Integer,Integer>();
			for(int i=0;i<sList.size();i++){
				SlotsListTemplate SlotsListTemplate = sList.get(i);
				if(SlotsListTemplate.getJackpotswitch() == 2){
					continue;
				}
				positionMap.put(SlotsListTemplate.getId(), i+1);
			}
			
			
			for(Slot slotOne:slotList){
				Integer position = positionMap.get(slotOne.getTempleId());
				if(position != null){
					jackPotNumList.add(position);
					jackPotList.add(slotOne.getJackpot5());
				}
			}
			
		}
		
		/**
		 * 转换
		 */
		int[] jackPotNumArr = new int[jackPotNumList.size()];
		for(int i=0;i<jackPotNumList.size();i++){
			jackPotNumArr[i]=jackPotNumList.get(i);
		}
		long[] jackPotArr = new long[jackPotList.size()];
		for(int i=0;i<jackPotList.size();i++){
			jackPotArr[i]=jackPotList.get(i);
		}
		GCSlotNewJackpots gCSlotNewJackpots = new GCSlotNewJackpots();
		gCSlotNewJackpots.setJackpotNum(jackPotNumArr);
		gCSlotNewJackpots.setJackpot(jackPotArr);
		player.sendMessage(gCSlotNewJackpots);
	}




	public void handleAllSlotNewJackpots(Player player,
			CGAllSlotNewJackpots cgAllSlotNewJackpots) {
		
		Map<Integer,Slot> slotMap = Globals.getSlotService().getSlotList();
		List<Integer> slotTypeList = new ArrayList<Integer>();
		for(Slot slot:slotMap.values()){
			if(!slotTypeList.contains(slot.getType())){
				slotTypeList.add(slot.getType());
			}
		}
		/**
		 * 每个老虎机中 倍场 最大的那个 templateId
		 */
		List<Integer> slotTemplateIdList = new ArrayList<Integer>();
		for(Integer slotType:slotTypeList){
			List<SlotsListTemplate> sList  = Globals.getSlotService().getSlotsListTemplateList(slotType);
			int slotTemplateId=0;
			for(SlotsListTemplate slt :sList){
				if(slt.getJackpotswitch() == 2){
					continue;
				}
				if(slt.getId() > slotTemplateId){
					slotTemplateId=slt.getId();
				}
			}
			if(slotTemplateId != 0){
				slotTemplateIdList.add(slotTemplateId);
			}
		}
		
		
		GCAllSlotNewJackpots gCAllSlotNewJackpots = new GCAllSlotNewJackpots();
		
		List<Integer> slotTypeL = new ArrayList<Integer>();
		List<Long> jackpotL = new ArrayList<Long>();
		for(Integer templateId:slotTemplateIdList){
			Slot slot = slotMap.get(templateId);
			long jackpot5 = slot.getJackpot5();
			slotTypeL.add(slot.getType());
			jackpotL.add(jackpot5);
		}
		/**
		 * 把特殊的 东方龙加上
		 */
		List<SlotsListTemplate> sList  = Globals.getSlotService().getSlotsListTemplateList(23);
		int slotTemplateId=0;
		for(SlotsListTemplate slt :sList){
			if(slt.getId() > slotTemplateId){
				slotTemplateId=slt.getId();
			}
		}
		Slot slot23 = slotMap.get(slotTemplateId);
		String jackpot23 = slot23.getSlotType23Jackpot();
		String jackpot23MaxValueStr = jackpot23.split(";")[4].split("=")[1];
		long jackpot23MaxValue = Long.valueOf(jackpot23MaxValueStr.split(",")[4]);
		slotTypeL.add(23);
		jackpotL.add(jackpot23MaxValue);
		
		/**
		 * 转成数组
		 */
		int[] slotTypeArr = new int[slotTypeL.size()];
		for(int i=0;i<slotTypeL.size();i++){
			slotTypeArr[i]=slotTypeL.get(i);
		}
		long[] jackpotArr = new long[jackpotL.size()];
		for(int i=0;i<jackpotL.size();i++){
			jackpotArr[i]=jackpotL.get(i);
		}
		gCAllSlotNewJackpots.setSlotType(slotTypeArr);
		gCAllSlotNewJackpots.setJackpot(jackpotArr);
		
		player.sendMessage(gCAllSlotNewJackpots);
	}
	
	
	

}
