package com.gameserver.slot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BounsHallowmasJackpotTemplate;
import com.gameserver.slot.template.BounsHallowmasRewardTemplate;
import com.gameserver.slot.template.BounsHallowmasTemplate;

/**
 * 万圣节 老虎机
 * @author JavaServer
 *
 */
public class BonusHallowmasService implements InitializeRequired {

	Map<Integer,List<BounsHallowmasTemplate>> BounsHallowmasTemplateMap = new HashMap<Integer,List<BounsHallowmasTemplate>>();
	Map<Integer,List<BounsHallowmasRewardTemplate>> BounsHallowmasRewardTemplateMap = new HashMap<Integer,List<BounsHallowmasRewardTemplate>>();
	Map<Integer,List<BounsHallowmasJackpotTemplate>> BounsHallowmasJackpotTemplateMap = new HashMap<Integer,List<BounsHallowmasJackpotTemplate>>();
	Map<Integer,List<Integer>> weightList = new HashMap<Integer,List<Integer>>();
	
	
	@Override
	public void init() {
		
		Map<Integer,BounsHallowmasTemplate> rBounsHallowmasTemplateMap = Globals.getTemplateService().getAll(BounsHallowmasTemplate.class);
		int startNum = -1;
		for(BounsHallowmasTemplate bsa: rBounsHallowmasTemplateMap.values()){
			int type = bsa.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsHallowmasTemplate> BounsHallowmasTemplateList = new ArrayList<BounsHallowmasTemplate>();
				BounsHallowmasTemplateList.add(bsa);
				BounsHallowmasTemplateMap.put(type, BounsHallowmasTemplateList);
			}else{
				List<BounsHallowmasTemplate> BounsHallowmasTemplateList = BounsHallowmasTemplateMap.get(type);
				BounsHallowmasTemplateList.add(bsa);
			}
		}
		/**
		 * reward
		 */
		Map<Integer,BounsHallowmasRewardTemplate> rBounsHallowmasRewardTemplateMap = Globals.getTemplateService().getAll(BounsHallowmasRewardTemplate.class);
		int startNum1 = -1;
		for(BounsHallowmasRewardTemplate bsa: rBounsHallowmasRewardTemplateMap.values()){
			int type = bsa.getSlotsNum();
			if(startNum1 != type){
				startNum1 = type;
				List<BounsHallowmasRewardTemplate> BounsHallowmasRewardTemplateList = new ArrayList<BounsHallowmasRewardTemplate>();
				BounsHallowmasRewardTemplateList.add(bsa);
				BounsHallowmasRewardTemplateMap.put(type, BounsHallowmasRewardTemplateList);
			}else{
				List<BounsHallowmasRewardTemplate> BounsHallowmasRewardTemplateList = BounsHallowmasRewardTemplateMap.get(type);
				BounsHallowmasRewardTemplateList.add(bsa);
			}
		}
		/**
		 * jackpot
		 */
		Map<Integer,BounsHallowmasJackpotTemplate> rBounsHallowmasJackpotTemplateMap = Globals.getTemplateService().getAll(BounsHallowmasJackpotTemplate.class);
		int startNum2 = -1;
		for(BounsHallowmasJackpotTemplate bsa: rBounsHallowmasJackpotTemplateMap.values()){
			int type = bsa.getSlotsNum();
			if(startNum2 != type){
				startNum2 = type;
				List<BounsHallowmasJackpotTemplate> BounsHallowmasJackpotTemplateList = new ArrayList<BounsHallowmasJackpotTemplate>();
				BounsHallowmasJackpotTemplateList.add(bsa);
				BounsHallowmasJackpotTemplateMap.put(type, BounsHallowmasJackpotTemplateList);
				
				List<Integer> weight = new ArrayList<Integer>();
				weight.add(bsa.getWeight());
				weightList.put(type,weight);
			}else{
				List<BounsHallowmasJackpotTemplate> BounsHallowmasJackpotTemplateList = BounsHallowmasJackpotTemplateMap.get(type);
				BounsHallowmasJackpotTemplateList.add(bsa);
				
				List<Integer> rWeightList = weightList.get(type);
				rWeightList.add(bsa.getWeight());
			}
		}
		
	}



	public int getBonusNum(int type) {
		return BounsHallowmasTemplateMap.get(type).get(0).getBonusNum();
	}

	/**
	 * wild
	 * @param type
	 * @return
	 */
	
	public int getWildNum(int type) {
		return BounsHallowmasTemplateMap.get(type).get(0).getWildNum();
	}
	/**
	 * 南瓜
	 * @param type
	 * @return
	 */

	public int getCollectPumpkin(int type) {
		return BounsHallowmasTemplateMap.get(type).get(0).getCollectPumpkin();
	}
	public int getCollectReward(int type) {
		return BounsHallowmasTemplateMap.get(type).get(0).getReward();
	}

	
	/**
	 * 获取最基本的 奖金
	 * @param type
	 * @return
	 */

	public long getBaseBlood(int type) {
		List<BounsHallowmasRewardTemplate> BounsHallowmasRewardTemplateList = BounsHallowmasRewardTemplateMap.get(type);
		for(BounsHallowmasRewardTemplate BounsHallowmasRewardTemplate:BounsHallowmasRewardTemplateList){
			if(BounsHallowmasRewardTemplate.getGuaranteed() == 0){
				return BounsHallowmasRewardTemplate.getReward();
			}
		}
		return 0;
	}
	
	/**
	 * 灵魂的 看看获取哪个奖励
	 * @param type
	 * @return
	 */
	
	public long getRewardGhost(int type,int times){
		List<BounsHallowmasRewardTemplate> BounsHallowmasRewardTemplateList = BounsHallowmasRewardTemplateMap.get(type);
		//先按 HitTheGhost 倒序 
		Collections.sort(BounsHallowmasRewardTemplateList,new Comparator<BounsHallowmasRewardTemplate>(){

			@Override
			public int compare(BounsHallowmasRewardTemplate o1,
					BounsHallowmasRewardTemplate o2) {
				if(o1.getHitTheGhost() > o2.getHitTheGhost()){
					return -1;
				}
				return 1;
			}
			
		});
		
		//再找出 获取的哪个奖励
		for(BounsHallowmasRewardTemplate BounsHallowmasRewardTemplate:BounsHallowmasRewardTemplateList){
			if(times >= BounsHallowmasRewardTemplate.getHitTheGhost()){
				return BounsHallowmasRewardTemplate.getReward();
			}
		}
		return 0;
	}
	//通过关卡 来查询奖励
	public List<BounsHallowmasRewardTemplate> getRewardGhostByCheckpoint(int type,int point){
		List<BounsHallowmasRewardTemplate> BounsHallowmasRewardTemplateList = BounsHallowmasRewardTemplateMap.get(type);
		List<BounsHallowmasRewardTemplate> passList = new ArrayList<BounsHallowmasRewardTemplate>();
		//先按 HitTheGhost 倒序 
		Collections.sort(BounsHallowmasRewardTemplateList,new Comparator<BounsHallowmasRewardTemplate>(){
			
			@Override
			public int compare(BounsHallowmasRewardTemplate o1,
					BounsHallowmasRewardTemplate o2) {
				if(o1.getHitTheGhost() > o2.getHitTheGhost()){
					return -1;
				}
				return 1;
			}
			
		});
		
		//再找出 获取的哪个奖励
		for(BounsHallowmasRewardTemplate BounsHallowmasRewardTemplate:BounsHallowmasRewardTemplateList){
			if(BounsHallowmasRewardTemplate.getCheckpoint() >= point){
				passList.add(BounsHallowmasRewardTemplate);
			}
		}
		return passList;
	}
	
	/*
	 * 本 老虎机  jackpot的随机奖励
	 */
	public BounsHallowmasJackpotTemplate getJackpotTemplate(int type){
		List<BounsHallowmasJackpotTemplate>  post = ArrayUtils.randomFromArray(BounsHallowmasJackpotTemplateMap.get(type), ArrayUtils.intList2Array(weightList.get(type)), 1, false);
		return post.get(0);
	}



	public int getJackpotNum(int type) {
		return BounsHallowmasTemplateMap.get(type).get(0).getJackpotNum();
	}
}
