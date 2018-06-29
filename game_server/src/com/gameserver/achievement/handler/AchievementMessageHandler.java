package com.gameserver.achievement.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.common.LogReasons;
import com.db.model.HumanAchievementEntity;
import com.gameserver.achievement.HumanAchievement;
import com.gameserver.achievement.data.AchievementStateData;
import com.gameserver.achievement.data.AchievementsProgressData;
import com.gameserver.achievement.enums.AchState;
import com.gameserver.achievement.msg.CGAchInfo;
import com.gameserver.achievement.msg.CGReceiveAch;
import com.gameserver.achievement.msg.GCAchInfo;
import com.gameserver.achievement.msg.GCReceiveAch;
import com.gameserver.achievement.pojo.CompleteState;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanAchievementManager;
import com.gameserver.player.Player;
/**
 * 消息处理类
 * @author 郭君伟
 *
 */
public class AchievementMessageHandler {
	
	public void handleAchInfo(Player player, CGAchInfo cgAchInfo) {
		
		/*PlayerCacheInfo playerCacheInfo= Globals.getPlayerCacheService().getPlayerCacheById(cgQueryPlayerInfo.getUserId());			
		if(playerCacheInfo == null)
		{
			logger.warn("player["+player.getPassportId() +"]no exist");
			player.sendSystemMessage(LangConstants.FRIEND_NOT_EXIST);
			return;
		}*/
		
		long roleId = cgAchInfo.getRoleId();
		
		HumanAchievement achievement = new HumanAchievement();
		if(player.getPassportId() == roleId){
			Human human = player.getHuman();
			HumanAchievementManager manager = human.getHumanAchievementManager();
		    achievement =  manager.getHumanAchievement();
		}else{
			Player otherplayer = Globals.getOnlinePlayerService().getPlayerByPassportId(roleId);
			if(otherplayer != null){
				Human human = otherplayer.getHuman();
				HumanAchievementManager manager = human.getHumanAchievementManager();
			    achievement =  manager.getHumanAchievement();
			}else{
				HumanAchievementEntity entity =  Globals.getDaoService().getHumanAchievementDao().getAchievementEntity(roleId);
				if(entity != null){
					achievement.fromEntityCope(entity);
				}
			}
		}
		
		 Map<Integer, CompleteState> stateMap = achievement.getAchValueMap();
		  
		 List<AchievementStateData> stateList = new ArrayList<AchievementStateData>();
		 
		 for(CompleteState cs : stateMap.values()){
			 AchievementStateData data = new AchievementStateData();
			 data.setId(cs.getId());
			 data.setState(cs.getState());
			 data.setCompleteTime(cs.getCompleteTime());
			 stateList.add(data);
		 }
		 
		 Map<Integer, Map<Integer, String>> achDateMap = achievement.getAchDateMap();
		 
		 List<AchievementsProgressData> proList = new ArrayList<AchievementsProgressData>();
		
		 
		 for(Entry<Integer, Map<Integer, String>> en : achDateMap.entrySet()){
			     int serverType = en.getKey();
			     Map<Integer, String> map = en.getValue();
			     for(Entry<Integer, String> enn: map.entrySet()){
			    	 int smallType = enn.getKey();
			    	 String value = enn.getValue();
			    	 AchievementsProgressData data = new AchievementsProgressData();
			    	 data.setServerType(serverType);
			    	 data.setSmalType(smallType);
			    	 data.setValue(value);
			    	 proList.add(data);
			     }
		 }
		 
		GCAchInfo message = new GCAchInfo();
		
		message.setAchievementStateData(stateList.toArray(new AchievementStateData[stateList.size()]));
		
		message.setAchievementsProgressData(proList.toArray(new AchievementsProgressData[proList.size()]));
		
		player.sendMessage(message);
		
		
	}

	public void handleReceiveAch(Player player, CGReceiveAch cgReceiveAch) {
		Human human = player.getHuman();
		
		int id = cgReceiveAch.getId();
		
        HumanAchievementManager manager = human.getHumanAchievementManager();
		
		HumanAchievement achievement =  manager.getHumanAchievement();
		
		 Map<Integer, CompleteState> stateMap = achievement.getAchValueMap();
		
		 int result = 0;
		 
		 if(stateMap.containsKey(id)){
			 CompleteState state = stateMap.get(id);
			 if(state.getState() == AchState.AchState2.getIndex()){
				 state.setState(AchState.AchState3.getIndex());
				 result = 1;
				int gold = Globals.getHumanAchievementServer().getReceive(id);
				//增加货币
				 human.giveMoney(gold, Currency.GOLD, true, LogReasons.GoldLogReason.ACHIEVEMENT, LogReasons.GoldLogReason.ACHIEVEMENT.getReasonText(), -1, 1);
				//发奖励
				 achievement.setModified();
			 }
		 }
		 GCReceiveAch message = new GCReceiveAch();
		 message.setId(id);
		 message.setResult(result);
		 player.sendMessage(message);
	}

}
