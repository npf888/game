package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BonusOceanRewardTemplate;
import com.gameserver.slot.template.BonusOceanTemplate;
import com.gameserver.slot.template.ScatterOceanRewardTemplate;
import com.gameserver.slot.template.ScatterOceanTemplate;

/**
 * 海底世界
 * @author JavaServer
 *
 */
public class BonusOceanService  implements InitializeRequired {

	/**
	 * old
	 */
	Map<Integer,List<BonusOceanTemplate>> bonusOceanTemplateMap = new HashMap<Integer,List<BonusOceanTemplate>>();
	Map<Integer,List<BonusOceanRewardTemplate>> bonusOceanRewardTemplateMap = new HashMap<Integer,List<BonusOceanRewardTemplate>>();
	Map<Integer,List<Integer>> weightList = new HashMap<Integer,List<Integer>>();
	/******************************************************************/
	
	/**
	 * new
	 */
	Map<Integer,List<ScatterOceanTemplate>> scatterOceanTemplateMap = new HashMap<Integer,List<ScatterOceanTemplate>>();
	Map<Integer,List<ScatterOceanRewardTemplate>> scatterOceanRewardTemplateMap = new HashMap<Integer,List<ScatterOceanRewardTemplate>>();
	Map<Integer,List<Integer>> scatterWeightList = new HashMap<Integer,List<Integer>>();
	
	/*********************************************************************/
	
	@Override
	public void init() {
		/**
		 * old
		 */
		Map<Integer,BonusOceanRewardTemplate> rBonusOceanRewardTemplateMap = Globals.getTemplateService().getAll(BonusOceanRewardTemplate.class);
		int startNum = -1;
		for(BonusOceanRewardTemplate bo: rBonusOceanRewardTemplateMap.values()){
			int type = bo.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BonusOceanRewardTemplate> BonusOceanRewardTemplateList = new ArrayList<BonusOceanRewardTemplate>();
				BonusOceanRewardTemplateList.add(bo);
				bonusOceanRewardTemplateMap.put(type, BonusOceanRewardTemplateList);
				
				List<Integer> weight = new ArrayList<Integer>();
				weight.add(bo.getValue());
				weightList.put(type,weight);
			}else{
				List<BonusOceanRewardTemplate> BonusOceanRewardTemplatList = bonusOceanRewardTemplateMap.get(type);
				BonusOceanRewardTemplatList.add(bo);
				
				List<Integer> weight = weightList.get(type);
				weight.add(bo.getValue());
			}
		}
		Map<Integer,BonusOceanTemplate> rBonusOceanTemplateMap = Globals.getTemplateService().getAll(BonusOceanTemplate.class);
		int startNum2 = -1;
		for(BonusOceanTemplate bo: rBonusOceanTemplateMap.values()){
			//单次 bonus 中奖，两次 bonus中奖 ，三次 bonus 中奖
			int type = bo.getSlotsNum();
			if(startNum2 != type){
				startNum2 = type;
				List<BonusOceanTemplate> BonusOceanTemplateList = new ArrayList<BonusOceanTemplate>();
				BonusOceanTemplateList.add(bo);
				bonusOceanTemplateMap.put(type, BonusOceanTemplateList);
			}else{
				List<BonusOceanTemplate> BonusOceanTemplateList = bonusOceanTemplateMap.get(type);
				BonusOceanTemplateList.add(bo);
			}
		}
		
		/***********************************************************************************************************************************/
		
		
		
		/**
		 * new
		 */
		
		Map<Integer,ScatterOceanRewardTemplate> rScatterOceanRewardTemplateMap = Globals.getTemplateService().getAll(ScatterOceanRewardTemplate.class);
		int scatterStartNum = -1;
		for(ScatterOceanRewardTemplate so: rScatterOceanRewardTemplateMap.values()){
			int type = so.getSlotsNum();
			if(scatterStartNum != type){
				scatterStartNum = type;
				List<ScatterOceanRewardTemplate> ScatterOceanRewardTemplateList = new ArrayList<ScatterOceanRewardTemplate>();
				ScatterOceanRewardTemplateList.add(so);
				scatterOceanRewardTemplateMap.put(type, ScatterOceanRewardTemplateList);
				
				List<Integer> weight = new ArrayList<Integer>();
				weight.add(so.getValue());
				scatterWeightList.put(type,weight);
			}else{
				List<ScatterOceanRewardTemplate> scatterOceanRewardTemplateList = scatterOceanRewardTemplateMap.get(type);
				scatterOceanRewardTemplateList.add(so);
				
				List<Integer> weight = scatterWeightList.get(type);
				weight.add(so.getValue());
			}
		}
		Map<Integer,ScatterOceanTemplate> rScatterOceanTemplateMap = Globals.getTemplateService().getAll(ScatterOceanTemplate.class);
		int scatterStartNum2 = -1;
		for(ScatterOceanTemplate so: rScatterOceanTemplateMap.values()){
			//单次 bonus 中奖，两次 bonus中奖 ，三次 bonus 中奖
			int type = so.getSlotsNum();
			if(scatterStartNum2 != type){
				scatterStartNum2 = type;
				List<ScatterOceanTemplate> scatterOceanTemplateList = new ArrayList<ScatterOceanTemplate>();
				scatterOceanTemplateList.add(so);
				scatterOceanTemplateMap.put(type, scatterOceanTemplateList);
			}else{
				List<ScatterOceanTemplate> ScatterOceanTemplateList = scatterOceanTemplateMap.get(type);
				ScatterOceanTemplateList.add(so);
			}
		}
		
		
		
		/***********************************************************************************************************************************/
	}
	/*
	 * 根据权值获取 bouns 随机数
	 */
	public BonusOceanRewardTemplate getRewardWeight(int slotNum){
		
		List<BonusOceanRewardTemplate>  post = ArrayUtils.randomFromArray(bonusOceanRewardTemplateMap.get(slotNum), ArrayUtils.intList2Array(weightList.get(slotNum)), 1, false);
		return post.get(0);
	}
	public Map<Integer, List<BonusOceanTemplate>> getBonusOceanTemplateMap() {
		return bonusOceanTemplateMap;
	}
	public void setBonusOceanTemplateMap(
			Map<Integer, List<BonusOceanTemplate>> bonusOceanTemplateMap) {
		this.bonusOceanTemplateMap = bonusOceanTemplateMap;
	}
	public Map<Integer, List<BonusOceanRewardTemplate>> getBonusOceanRewardTemplateMap() {
		return bonusOceanRewardTemplateMap;
	}
	public void setBonusOceanRewardTemplateMap(
			Map<Integer, List<BonusOceanRewardTemplate>> bonusOceanRewardTemplateMap) {
		this.bonusOceanRewardTemplateMap = bonusOceanRewardTemplateMap;
	}
	public Map<Integer, List<Integer>> getWeightList() {
		return weightList;
	}
	public void setWeightList(Map<Integer, List<Integer>> weightList) {
		this.weightList = weightList;
	}
	
	
	/**
	 * new
	 */
	/**
	 * 获取要超越的scatterNum
	 * @param slotNum
	 * @return
	 */
	public int getScatterNum(int slotNum){
		List<ScatterOceanTemplate>  scatterOceanList= getScatterOceanTemplateMap().get(slotNum);
		return scatterOceanList.get(0).getScatterNum();
	}
	/*
	 * 根据权值获取 bouns 随机数
	 */
	public ScatterOceanRewardTemplate getScatterRewardByWeight(int slotNum){
		List<Integer>  scatterWeightList= getScatterWeightListBySlotType(slotNum);
		List<ScatterOceanRewardTemplate>  scatterOceanRewardList=getScatterOceanRewardTemplateListBySlotType(slotNum);
		List<ScatterOceanRewardTemplate>  post = ArrayUtils.randomFromArray(scatterOceanRewardList, ArrayUtils.intList2Array(scatterWeightList), 1, false);
		return post.get(0);
	}
	
	/**
	 * 把剩下的两个 也随机取出来
	 * @param slotNum
	 * @param rScatterOceanRewardTemplate
	 * @return
	 */
	public List<ScatterOceanRewardTemplate> getScatterRewardByWeightOther(int slotNum,ScatterOceanRewardTemplate rScatterOceanRewardTemplate){
		List<ScatterOceanRewardTemplate>  scatterOceanRewardList=getScatterOceanRewardTemplateListBySlotType(slotNum);
		
		List<Integer> otherWeightList = new ArrayList<Integer>();
		List<ScatterOceanRewardTemplate>  otherScatterOceanRewardList=new ArrayList<ScatterOceanRewardTemplate>();
		for(ScatterOceanRewardTemplate scatterOceanRewardTemplate:scatterOceanRewardList){
			if(scatterOceanRewardTemplate.getId() == rScatterOceanRewardTemplate.getId()){
				continue;
			}
			otherScatterOceanRewardList.add(scatterOceanRewardTemplate);
			otherWeightList.add(scatterOceanRewardTemplate.getValue());
		}
		
		List<ScatterOceanRewardTemplate>  post = ArrayUtils.randomFromArray(otherScatterOceanRewardList, ArrayUtils.intList2Array(otherWeightList), 2, false);
		return post;
	}
	
	
	
	public List<ScatterOceanTemplate> getScatterOceanTemplateListBySlotType(int slotNum) {
		List<ScatterOceanTemplate>  scatterOceanList= getScatterOceanTemplateMap().get(slotNum);
		return scatterOceanList;
	}
	public List<ScatterOceanRewardTemplate> getScatterOceanRewardTemplateListBySlotType(int slotNum) {
		List<ScatterOceanRewardTemplate>  scatterOceanRewardList= getScatterOceanRewardTemplateMap().get(slotNum);
		return scatterOceanRewardList;
	}
	public List<Integer> getScatterWeightListBySlotType(int slotNum) {
		List<Integer>  scatterWeightList= getScatterWeightList().get(slotNum);
		return scatterWeightList;
	}
	
	
	
	
	
	
	
	
	
	public Map<Integer, List<ScatterOceanTemplate>> getScatterOceanTemplateMap() {
		return scatterOceanTemplateMap;
	}
	public void setScatterOceanTemplateMap(
			Map<Integer, List<ScatterOceanTemplate>> scatterOceanTemplateMap) {
		this.scatterOceanTemplateMap = scatterOceanTemplateMap;
	}
	public Map<Integer, List<ScatterOceanRewardTemplate>> getScatterOceanRewardTemplateMap() {
		return scatterOceanRewardTemplateMap;
	}
	public void setScatterOceanRewardTemplateMap(
			Map<Integer, List<ScatterOceanRewardTemplate>> scatterOceanRewardTemplateMap) {
		this.scatterOceanRewardTemplateMap = scatterOceanRewardTemplateMap;
	}
	public Map<Integer, List<Integer>> getScatterWeightList() {
		return scatterWeightList;
	}
	public void setScatterWeightList(Map<Integer, List<Integer>> scatterWeightList) {
		this.scatterWeightList = scatterWeightList;
	}
	
	
	
	
	
}
