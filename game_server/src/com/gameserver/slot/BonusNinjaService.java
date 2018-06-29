package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BonusNinjaRewardTemplate;
import com.gameserver.slot.template.BonusNinjaTemplate;

public class BonusNinjaService implements InitializeRequired {

	public static final int type_no = 1;
	public static final int type_yes = 2;
	
	List<BonusNinjaTemplate> bonusNinjaTemplateList = new ArrayList<BonusNinjaTemplate>();
	Map<Integer,List<BonusNinjaTemplate>> bonusNinjaTemplateMap = new HashMap<Integer,List<BonusNinjaTemplate>>();
	List<BonusNinjaRewardTemplate> bonusNinjaRewardTemplateList = new ArrayList<BonusNinjaRewardTemplate>();
	Map<Integer,List<BonusNinjaRewardTemplate>> bonusNinjaRewardTemplateMap = new HashMap<Integer,List<BonusNinjaRewardTemplate>>();
	List<Integer> weightList = new ArrayList<Integer>();
	Map<Integer,List<Integer>> weightListMap = new HashMap<Integer,List<Integer>>();
	@Override
	public void init() {
		Map<Integer,BonusNinjaTemplate> rbonusNinjaTemplateMap = Globals.getTemplateService().getAll(BonusNinjaTemplate.class);
		Map<Integer,BonusNinjaRewardTemplate> rbonusNinjaRewardTemplateMap = Globals.getTemplateService().getAll(BonusNinjaRewardTemplate.class);
		int startNum = -1;
		for(BonusNinjaTemplate bonusNinjaTemplate:rbonusNinjaTemplateMap.values()){
			bonusNinjaTemplateList.add(bonusNinjaTemplate);
			int slotsNum = bonusNinjaTemplate.getSlotsNum();
			if(startNum != slotsNum){
				startNum = slotsNum;
				List<BonusNinjaTemplate> bonusNinjaTemplateList = new ArrayList<BonusNinjaTemplate>();
				bonusNinjaTemplateList.add(bonusNinjaTemplate);
				bonusNinjaTemplateMap.put(slotsNum, bonusNinjaTemplateList);
			}else{
				List<BonusNinjaTemplate> bonusNinjaTemplateList = bonusNinjaTemplateMap.get(slotsNum);
				bonusNinjaTemplateList.add(bonusNinjaTemplate);
			}
		}
		int startNum2 = -1;
		int type2 = -1;
		for(BonusNinjaRewardTemplate bonusNinjaRewardTemplate:rbonusNinjaRewardTemplateMap.values()){
			bonusNinjaRewardTemplateList.add(bonusNinjaRewardTemplate);
			weightList.add(bonusNinjaRewardTemplate.getWeight());
			int slotsNum = bonusNinjaRewardTemplate.getSlotsNum();
			if(startNum2 != slotsNum){
				startNum2 = slotsNum;
				List<BonusNinjaRewardTemplate> bonusNinjaRewardTemplateList = new ArrayList<BonusNinjaRewardTemplate>();
				bonusNinjaRewardTemplateList.add(bonusNinjaRewardTemplate);
				bonusNinjaRewardTemplateMap.put(slotsNum, bonusNinjaRewardTemplateList);
				
 			}else{
				List<BonusNinjaRewardTemplate> bonusNinjaTemplateList = bonusNinjaRewardTemplateMap.get(slotsNum);
				bonusNinjaTemplateList.add(bonusNinjaRewardTemplate);
			}
			int type = bonusNinjaRewardTemplate.getType();
			if(type2 != type){
				type2=type;
				List<Integer> weightLL = new ArrayList<Integer>();
				weightLL.add(bonusNinjaRewardTemplate.getWeight());
				weightListMap.put(type,weightLL);
			}else{
				List<Integer> weightLL = weightListMap.get(type);
				weightLL.add(bonusNinjaRewardTemplate.getWeight());
			}
		}
		int i = 0;
	}
	
	public BonusNinjaRewardTemplate getBonusNinjaRewardTemplateByWeight(){
		List<BonusNinjaRewardTemplate>  post = ArrayUtils.randomFromArray(bonusNinjaRewardTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	public BonusNinjaRewardTemplate getBonusNinjaRewardTemplateByWeight(List<BonusNinjaRewardTemplate> bt,int type){
		List<BonusNinjaRewardTemplate>  post = ArrayUtils.randomFromArray(bt, ArrayUtils.intList2Array(weightListMap.get(type)), 1, false);
		return post.get(0);
	}
	
	
	public List<BonusNinjaTemplate> getBonusNinjaTemplateList() {
		return bonusNinjaTemplateList;
	}
	public void setBonusNinjaTemplateList(
			List<BonusNinjaTemplate> bonusNinjaTemplateList) {
		this.bonusNinjaTemplateList = bonusNinjaTemplateList;
	}
	public List<BonusNinjaRewardTemplate> getBonusNinjaRewardTemplateList() {
		return bonusNinjaRewardTemplateList;
	}
	public void setBonusNinjaRewardTemplateList(
			List<BonusNinjaRewardTemplate> bonusNinjaRewardTemplateList) {
		this.bonusNinjaRewardTemplateList = bonusNinjaRewardTemplateList;
	}


	public Map<Integer, List<BonusNinjaTemplate>> getBonusNinjaTemplateMap() {
		return bonusNinjaTemplateMap;
	}


	public void setBonusNinjaTemplateMap(
			Map<Integer, List<BonusNinjaTemplate>> bonusNinjaTemplateMap) {
		this.bonusNinjaTemplateMap = bonusNinjaTemplateMap;
	}


	public Map<Integer, List<BonusNinjaRewardTemplate>> getBonusNinjaRewardTemplateMap() {
		return bonusNinjaRewardTemplateMap;
	}


	public void setBonusNinjaRewardTemplateMap(
			Map<Integer, List<BonusNinjaRewardTemplate>> bonusNinjaRewardTemplateMap) {
		this.bonusNinjaRewardTemplateMap = bonusNinjaRewardTemplateMap;
	}


	public List<Integer> getWeightList() {
		return weightList;
	}


	public void setWeightList(List<Integer> weightList) {
		this.weightList = weightList;
	}
	
	
	

}
