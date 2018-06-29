package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BonusStoneAgeRewardTemplate;
import com.gameserver.slot.template.BonusStoneAgeTemplate;
import com.gameserver.slot.template.MagicSymbolTemplate;
/**
 * 石器时代老虎机静态配置数据
 * @author 郭君伟
 *
 */
public class MagicSymbolService implements InitializeRequired {
	
	private static int post1 = 0;
	private static int post2 = 1;
	private static int post3 = 2;
	private static int post4 = 3;
	private static int post5 = 4;
	

	 private Map<Integer,Map<Integer,List<Integer>>>  mapData = new HashMap<Integer,Map<Integer,List<Integer>>>();
	 
	 private Map<Integer,Map<Integer,List<Integer>>>  mapDataWeight = new HashMap<Integer,Map<Integer,List<Integer>>>();
	 
	 //====================================================================================
	 
	 private Map<Integer,Integer> bounsMap = new HashMap<Integer,Integer>();
	 
	 private Map<Integer,List<BonusStoneAgeRewardTemplate>> bounsReward = new HashMap<Integer,List<BonusStoneAgeRewardTemplate>>();
	 
	 private Map<Integer,List<Integer>> bounsRewardWeight = new HashMap<Integer,List<Integer>>();
	 
	
	 
	
	 
	
	@Override
	public void init() {
		
		 TemplateService templateService = Globals.getTemplateService();
			
		 Map<Integer,MagicSymbolTemplate> map =  templateService.getAll(MagicSymbolTemplate.class);
		 
		 for(MagicSymbolTemplate tem : map.values()){
			 
			 int slotType = tem.getSlotNum();
			 
			 int reel1 = tem.getReel1();
			 int reel2 = tem.getReel2();
			 int reel3 = tem.getReel3();
			 int reel4 = tem.getReel4();
			 int reel5 = tem.getReel5();
			 
			 int weight1 = tem.getValue1();
			 int weight2 = tem.getValue2();
			 int weight3 = tem.getValue3();
			 int weight4 = tem.getValue4();
			 int weight5 = tem.getValue5();
			
			 assemblyData(mapData,slotType,post1,reel1);
			 assemblyData(mapData,slotType,post2,reel2);
			 assemblyData(mapData,slotType,post3,reel3);
			 assemblyData(mapData,slotType,post4,reel4);
			 assemblyData(mapData,slotType,post5,reel5);
			 
			 assemblyData(mapDataWeight,slotType,post1,weight1);
			 assemblyData(mapDataWeight,slotType,post2,weight2);
			 assemblyData(mapDataWeight,slotType,post3,weight3);
			 assemblyData(mapDataWeight,slotType,post4,weight4);
			 assemblyData(mapDataWeight,slotType,post5,weight5);
			
		 }
		 
		 Map<Integer,BonusStoneAgeTemplate> bsamap =  templateService.getAll(BonusStoneAgeTemplate.class);
		 
		 for(BonusStoneAgeTemplate temp : bsamap.values()){
			 bounsMap.put(temp.getSlotsNum(), temp.getBonusNum());
		 }
		 
		 Map<Integer,BonusStoneAgeRewardTemplate> bsarmap =  templateService.getAll(BonusStoneAgeRewardTemplate.class);
		 
		for(BonusStoneAgeRewardTemplate temp : bsarmap.values()){
			
			int slotType = temp.getSlotsNum();
			
			int weight = temp.getWeight();
			
			List<BonusStoneAgeRewardTemplate> list1 = bounsReward.get(slotType);
			
			List<Integer> list2 = bounsRewardWeight.get(slotType);
			
			if(list1 == null ){
				list1 = new ArrayList<BonusStoneAgeRewardTemplate>();
				bounsReward.put(slotType, list1);
			}
			if(list2 == null ){
				list2 = new ArrayList<Integer>();
				bounsRewardWeight.put(slotType, list2);
			}
			
			list1.add(temp);
			list2.add(weight);
			
			
			
		}
		
		
       
	}
	
	private void assemblyData(Map<Integer,Map<Integer,List<Integer>>> map,int slotType,int post,int reel){
		 Map<Integer,List<Integer>> map1 =  map.get(slotType);
		 
		 if(map1 == null){
			 map1 = new HashMap<Integer,List<Integer>>();
			 map.put(slotType, map1);
		 }
		 List<Integer> list1 = map1.get(post);
		 if(list1 == null){
			 list1 = new ArrayList<Integer>();
			 map1.put(post, list1);
		 }
		 
		 list1.add(reel);
	}
	
	/**
	 * 随机元素
	 * @param slotType
	 * @param reel
	 * @return
	 */
	public int getReel(int slotType,int reel){
		List<Integer> list1 = mapData.get(slotType).get(reel);
		List<Integer> list2 = mapDataWeight.get(slotType).get(reel);
		
		List<Integer>  reels = ArrayUtils.randomFromArray(list1, ArrayUtils.intList2Array(list2), 1, false);
		
		return reels.get(0);
	}
	
	/**
	 * 是否中bonus游戏
	 * @param slotType
	 * @return
	 */
	public int getBonusNum(int slotType){
		if(bounsMap.containsKey(slotType)){
			return bounsMap.get(slotType);
		}
		return 9999;
	}
	
	/**
	 * 
	 * @param slotType
	 * @return
	 */
	public int getBonus(int slotType){
		if(bounsReward.containsKey(slotType) && bounsRewardWeight.containsKey(slotType)){
			
			List<BonusStoneAgeRewardTemplate> list1 = bounsReward.get(slotType);
			List<Integer> list2 = bounsRewardWeight.get(slotType);
			List<BonusStoneAgeRewardTemplate>  post = ArrayUtils.randomFromArray(list1, ArrayUtils.intList2Array(list2), 1, false);
			
			BonusStoneAgeRewardTemplate bar = post.get(0);
			
			return bar.getTimes();
		}
		
		return 1;
	}

}
