package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BonusStoneAgeAppleTemplate;
import com.gameserver.slot.template.BonusStoneAgeOptimizeTemplate;
import com.gameserver.slot.template.BonusStoneAgePreyTemplate;
import com.gameserver.slot.template.BonusStoneAgeRuneTemplate;
import com.gameserver.slot.template.ScatterCrystalRewardTemplate;

/**
 * 石器时代  的 新的小游戏的 service
 * @author JavaServer
 *
 */
public class BonusStoneAgeService  implements InitializeRequired {

	Map<Integer,List<BonusStoneAgeOptimizeTemplate>> optimizeList = new HashMap<Integer,List<BonusStoneAgeOptimizeTemplate>>();
	
	/**
	 * 苹果
	 */
	Map<Integer,List<BonusStoneAgeAppleTemplate>> appleList = new HashMap<Integer,List<BonusStoneAgeAppleTemplate>>();
	Map<Integer,List<Integer>> appleWeightList = new HashMap<Integer,List<Integer>>();
	
	/**
	 * 翻牌
	 */
	Map<Integer,List<BonusStoneAgeRuneTemplate>> runeList = new HashMap<Integer,List<BonusStoneAgeRuneTemplate>>();
	Map<Integer,List<Integer>> runeWeightList = new HashMap<Integer,List<Integer>>();
	/**
	 * 捕猎
	 */
	Map<Integer,List<BonusStoneAgePreyTemplate>> preyList = new HashMap<Integer,List<BonusStoneAgePreyTemplate>>();
	Map<Integer,List<Integer>> preyWeightList = new HashMap<Integer,List<Integer>>();
	
	@Override
	public void init() {
		Map<Integer,BonusStoneAgeOptimizeTemplate> rBonusStoneAgeOptimizeTemplateMap = Globals.getTemplateService().getAll(BonusStoneAgeOptimizeTemplate.class);
		int startNum = -1;
		for(BonusStoneAgeOptimizeTemplate bsa: rBonusStoneAgeOptimizeTemplateMap.values()){
			int type = bsa.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BonusStoneAgeOptimizeTemplate> BonusStoneAgeOptimizeTemplateList = new ArrayList<BonusStoneAgeOptimizeTemplate>();
				BonusStoneAgeOptimizeTemplateList.add(bsa);
				optimizeList.put(type, BonusStoneAgeOptimizeTemplateList);
			}else{
				List<BonusStoneAgeOptimizeTemplate> BonusStoneAgeOptimizeTemplateList = optimizeList.get(type);
				BonusStoneAgeOptimizeTemplateList.add(bsa);
			}
		}
		
		/**
		 * 苹果 收集
		 */
		
		Map<Integer,BonusStoneAgeAppleTemplate> rBonusStoneAgeAppleTemplateMap = Globals.getTemplateService().getAll(BonusStoneAgeAppleTemplate.class);
		int startNum1 = -1;
		for(BonusStoneAgeAppleTemplate bsa: rBonusStoneAgeAppleTemplateMap.values()){
			int type = bsa.getSlotsNum();
			if(startNum1 != type){
				startNum1 = type;
				List<BonusStoneAgeAppleTemplate> BonusStoneAgeAppleTemplateList = new ArrayList<BonusStoneAgeAppleTemplate>();
				BonusStoneAgeAppleTemplateList.add(bsa);
				appleList.put(type, BonusStoneAgeAppleTemplateList);
				
				List<Integer> weight = new ArrayList<Integer>();
				weight.add(bsa.getWeight());
				appleWeightList.put(type,weight);
			}else{
				List<BonusStoneAgeAppleTemplate> BonusStoneAgeAppleTemplateList = appleList.get(type);
				BonusStoneAgeAppleTemplateList.add(bsa);
				
				List<Integer> weightList = appleWeightList.get(type);
				weightList.add(bsa.getWeight());
			}
		}
		
		/**
		 * 翻牌
		 */
		
		Map<Integer,BonusStoneAgeRuneTemplate> rBonusStoneAgeRuneTemplateMap = Globals.getTemplateService().getAll(BonusStoneAgeRuneTemplate.class);
		int startNum2 = -1;
		for(BonusStoneAgeRuneTemplate bsa: rBonusStoneAgeRuneTemplateMap.values()){
			int type = bsa.getSlotsNum();
			if(startNum2 != type){
				startNum2 = type;
				List<BonusStoneAgeRuneTemplate> BonusStoneAgeRuneTemplateList = new ArrayList<BonusStoneAgeRuneTemplate>();
				BonusStoneAgeRuneTemplateList.add(bsa);
				runeList.put(type, BonusStoneAgeRuneTemplateList);
				
				List<Integer> weight = new ArrayList<Integer>();
				weight.add(bsa.getWeight());
				runeWeightList.put(type,weight);
			}else{
				List<BonusStoneAgeRuneTemplate> BonusStoneAgeRuneTemplateList = runeList.get(type);
				BonusStoneAgeRuneTemplateList.add(bsa);
				
				List<Integer> weightList = runeWeightList.get(type);
				weightList.add(bsa.getWeight());
			}
		}
		
		/**
		 * 捕猎
		 */
		
		Map<Integer,BonusStoneAgePreyTemplate> rBonusStoneAgePreyTemplateMap = Globals.getTemplateService().getAll(BonusStoneAgePreyTemplate.class);
		int startNum3 = -1;
		for(BonusStoneAgePreyTemplate bsa: rBonusStoneAgePreyTemplateMap.values()){
			int type = bsa.getSlotsNum();
			if(startNum3 != type){
				startNum3 = type;
				List<BonusStoneAgePreyTemplate> BonusStoneAgePreyTemplateList = new ArrayList<BonusStoneAgePreyTemplate>();
				BonusStoneAgePreyTemplateList.add(bsa);
				preyList.put(type, BonusStoneAgePreyTemplateList);
				
				List<Integer> weight = new ArrayList<Integer>();
				weight.add(bsa.getWeight());
				preyWeightList.put(type,weight);
			}else{
				List<BonusStoneAgePreyTemplate> BonusStoneAgePreyTemplateList = preyList.get(type);
				BonusStoneAgePreyTemplateList.add(bsa);
				
				List<Integer> weightList = preyWeightList.get(type);
				weightList.add(bsa.getWeight());
			}
		}
		
	}

	
	/**
	 * 获取 bonus 数量（条件）
	 * @return
	 */
	public int getBonusNum(int slotType){
		List<BonusStoneAgeOptimizeTemplate> bonusStoneAgeOptimizeTemplateList= optimizeList.get(slotType);
		return bonusStoneAgeOptimizeTemplateList.get(0).getBonusNum();
	}
	/**
	 * 苹果 小游戏
	 * 
	 * 初始次数
	 * @return
	 */
	public int getAppleNum(int slotType){
		List<BonusStoneAgeOptimizeTemplate> bonusStoneAgeOptimizeTemplateList= optimizeList.get(slotType);
		return bonusStoneAgeOptimizeTemplateList.get(0).getAppleGameNum();
	}
	public int getAppleReward(int slotType){
		List<BonusStoneAgeOptimizeTemplate> bonusStoneAgeOptimizeTemplateList= optimizeList.get(slotType);
		return bonusStoneAgeOptimizeTemplateList.get(0).getAppleReward();
	}
	//随机选取苹果
	public BonusStoneAgeAppleTemplate getAppleTemplate(int slotType){
		List<BonusStoneAgeAppleTemplate> BonusStoneAgeAppleTemplateList= appleList.get(slotType);
		List<Integer> weightList= appleWeightList.get(slotType);
		List<BonusStoneAgeAppleTemplate>  post = ArrayUtils.randomFromArray(BonusStoneAgeAppleTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	
	/**
	 * 翻牌 小游戏
	 * 
	 * @return
	 */
	public int getRuneSameNum(int slotType){
		List<BonusStoneAgeOptimizeTemplate> bonusStoneAgeOptimizeTemplateList= optimizeList.get(slotType);
		return bonusStoneAgeOptimizeTemplateList.get(0).getSamNum();
	}
	//随机
	public BonusStoneAgeRuneTemplate getRuneTemplate(int slotType){
		List<BonusStoneAgeRuneTemplate> BonusStoneAgeRuneTemplateList= runeList.get(slotType);
		List<Integer> weightList= runeWeightList.get(slotType);
		List<BonusStoneAgeRuneTemplate>  post = ArrayUtils.randomFromArray(BonusStoneAgeRuneTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	
	/**
	 * 捕猎 小游戏
	 * 
	 * @return
	 */
	public int getPreyNum(int slotType){
		List<BonusStoneAgeOptimizeTemplate> bonusStoneAgeOptimizeTemplateList= optimizeList.get(slotType);
		return bonusStoneAgeOptimizeTemplateList.get(0).getPreyNum();
	}
	public int getPreyReward(int slotType){
		List<BonusStoneAgeOptimizeTemplate> bonusStoneAgeOptimizeTemplateList= optimizeList.get(slotType);
		return bonusStoneAgeOptimizeTemplateList.get(0).getPreyReward();
	}
	public BonusStoneAgePreyTemplate getPreyTemplate(int slotType){
		List<BonusStoneAgePreyTemplate> BonusStoneAgePreyTemplateList= preyList.get(slotType);
		List<Integer> weightList= preyWeightList.get(slotType);
		List<BonusStoneAgePreyTemplate>  post = ArrayUtils.randomFromArray(BonusStoneAgePreyTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	
}
