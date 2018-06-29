package com.gameserver.bazooachieve.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gameserver.bazoo.template.LiarsDiceRoomAchieveTemplate;
import com.gameserver.bazooachieve.HumanBazooAchieve;
import com.gameserver.bazooachieve.data.BazooAchieveData;
import com.gameserver.bazooachieve.data.BazooAchieveInfo;
import com.gameserver.bazooachieve.msg.CGBazooAchieve;
import com.gameserver.bazooachieve.msg.CGBazooAchieveFirst;
import com.gameserver.bazooachieve.msg.GCBazooAchieve;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

public class BazooachieveMessageHandler {

	/**
	 * 只取每个 类型的 第一个
	 * @param player
	 * @param cgBazooAchieveFirst
	 */
	public GCBazooAchieve handleBazooAchieveFirst(Player player,
			CGBazooAchieveFirst cgBazooAchieveFirst) {
		List<LiarsDiceRoomAchieveTemplate> achieveList = Globals.getHumanBazooAchieveService().getAchieveList();
		Map<Integer,List<LiarsDiceRoomAchieveTemplate>> templateMap = new HashMap<Integer,List<LiarsDiceRoomAchieveTemplate>>();
		
		for(int i=0;i<achieveList.size();i++){
			LiarsDiceRoomAchieveTemplate template = achieveList.get(i);
			if(templateMap.containsKey(template.getWayOfPlay())){
				List<LiarsDiceRoomAchieveTemplate> templateList = templateMap.get(template.getWayOfPlay());
				templateList.add(template);
			}else{
				List<LiarsDiceRoomAchieveTemplate> templateList = new ArrayList<LiarsDiceRoomAchieveTemplate>();
				templateList.add(template);
				templateMap.put(template.getWayOfPlay(), templateList);
				
			}
		}
		HumanBazooAchieve HumanBazooAchieve = player.getHuman().getHumanBazooAchieveManager().getHumanBazooAchieve();
		List<BazooAchieveInfo> BazooAchieveDataList = new ArrayList<BazooAchieveInfo>();
		Set<Integer> set = templateMap.keySet();
		for(Integer key:set){
			List<LiarsDiceRoomAchieveTemplate> teList = templateMap.get(key);
			Collections.sort(teList, new Comparator<LiarsDiceRoomAchieveTemplate>() {

				@Override
				public int compare(LiarsDiceRoomAchieveTemplate o1,
						LiarsDiceRoomAchieveTemplate o2) {
					if(o1.getRewardNum() > o2.getRewardNum()){
						return 1;
					}else if(o1.getRewardNum() < o2.getRewardNum()){
						return -1;
					}else{
						return 0;
					}
				}
			});
			for(LiarsDiceRoomAchieveTemplate achieve:teList){
				boolean find =false;
				for(BazooAchieveData bazooAchieveData:HumanBazooAchieve.getAchieves()){
					if(achieve.getId() == bazooAchieveData.getAchieveId()){
						if(bazooAchieveData.getFinished() == 0){//如果是未完成   就是他了
							BazooAchieveInfo BazooAchieveInfo = new BazooAchieveInfo();
							BazooAchieveInfo.setBigtype(achieve.getBigType());
							BazooAchieveInfo.setFinishVlaues(bazooAchieveData.getFinishValues());
							BazooAchieveInfo.setModeType(achieve.getModeType());
							BazooAchieveInfo.setRewardNum(achieve.getRewardNum());
							BazooAchieveInfo.setCondition(achieve.getCondition());
							BazooAchieveInfo.setWayOfPlay(achieve.getWayOfPlay());
							BazooAchieveInfo.setIsFinish(0);//未完成
							BazooAchieveDataList.add(BazooAchieveInfo);
							find=true;
							break;
						}else if(bazooAchieveData.getFinished() == 1 && bazooAchieveData.getGetNums() == 0){//如果是已将完成 未领奖  就是他了
							BazooAchieveInfo BazooAchieveInfo = new BazooAchieveInfo();
							BazooAchieveInfo.setBigtype(achieve.getBigType());
							BazooAchieveInfo.setFinishVlaues(bazooAchieveData.getFinishValues());
							BazooAchieveInfo.setModeType(achieve.getModeType());
							BazooAchieveInfo.setRewardNum(achieve.getRewardNum());
							BazooAchieveInfo.setCondition(achieve.getCondition());
							BazooAchieveInfo.setWayOfPlay(achieve.getWayOfPlay());
							BazooAchieveInfo.setIsFinish(1);//已完成
							BazooAchieveDataList.add(BazooAchieveInfo);
							find=true;
							break;
						}
					}
				}
				//如果找到了 
				if(find){
					break;
				}
				
			}
			
		}
		BazooAchieveInfo[] BazooAchieveInfoArr = new BazooAchieveInfo[BazooAchieveDataList.size()];
		for(int i=0;i<BazooAchieveDataList.size();i++){
			BazooAchieveInfoArr[i]=BazooAchieveDataList.get(i);
		}
		
		GCBazooAchieve  GCBazooAchieveFirst = new GCBazooAchieve();
		GCBazooAchieveFirst.setBazooAchieveInfo(BazooAchieveInfoArr);
		return GCBazooAchieveFirst;
	}

	public void handleBazooAchieve(Player player, CGBazooAchieve cgBazooAchieve) {
		BazooAchieveInfo[] BazooAchieveInfoArr = Globals.getHumanBazooAchieveService().getAllTasks(player);
		GCBazooAchieve GCBazooAchieve = new GCBazooAchieve();
		GCBazooAchieve.setBazooAchieveInfo(BazooAchieveInfoArr);
		player.sendMessage(GCBazooAchieve);
		
	}

}
