package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BonusHolmesRewardTemplate;
import com.gameserver.slot.template.BonusHolmesTemplate;

public class BonusHolmesService implements InitializeRequired {

	Map<Integer,List<BonusHolmesTemplate>> BonusHolmesTemplateMap = new HashMap<Integer,List<BonusHolmesTemplate>>();
	Map<Integer,List<BonusHolmesRewardTemplate>> BonusHolmesRewardTemplateMap = new HashMap<Integer,List<BonusHolmesRewardTemplate>>();
	@Override
	public void init() {
		Map<Integer,BonusHolmesRewardTemplate> rBonusHolmesRewardTemplateMap = Globals.getTemplateService().getAll(BonusHolmesRewardTemplate.class);
		int startNum = -1;
		for(BonusHolmesRewardTemplate bonusHolmesRewardTemplate:rBonusHolmesRewardTemplateMap.values()){
			int type = bonusHolmesRewardTemplate.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BonusHolmesRewardTemplate> BonusHolmesRewardTemplateList = new ArrayList<BonusHolmesRewardTemplate>();
				BonusHolmesRewardTemplateList.add(bonusHolmesRewardTemplate);
				BonusHolmesRewardTemplateMap.put(type, BonusHolmesRewardTemplateList);
			}else{
				List<BonusHolmesRewardTemplate> BonusHolmesRewardTemplateList = BonusHolmesRewardTemplateMap.get(type);
				BonusHolmesRewardTemplateList.add(bonusHolmesRewardTemplate);
			}
		}
		
		Map<Integer,BonusHolmesTemplate> rBonusHolmesTemplateMap = Globals.getTemplateService().getAll(BonusHolmesTemplate.class);
		int startNum2 = -1;
		for(BonusHolmesTemplate bonusHolmesTemplate:rBonusHolmesTemplateMap.values()){
			int type = bonusHolmesTemplate.getSlotsNum();
			if(startNum2 != type){
				startNum2 = type;
				List<BonusHolmesTemplate> bonusHolmesTemplateList = new ArrayList<BonusHolmesTemplate>();
				bonusHolmesTemplateList.add(bonusHolmesTemplate);
				BonusHolmesTemplateMap.put(type, bonusHolmesTemplateList);
			}else{
				List<BonusHolmesTemplate> bonusHolmesTemplateList = BonusHolmesTemplateMap.get(type);
				bonusHolmesTemplateList.add(bonusHolmesTemplate);
			}
		}
	}
	public Map<Integer, List<BonusHolmesTemplate>> getBonusHolmesTemplateMap() {
		return BonusHolmesTemplateMap;
	}
	public void setBonusHolmesTemplateMap(
			Map<Integer, List<BonusHolmesTemplate>> bonusHolmesTemplateMap) {
		BonusHolmesTemplateMap = bonusHolmesTemplateMap;
	}
	public Map<Integer, List<BonusHolmesRewardTemplate>> getBonusHolmesRewardTemplateMap() {
		return BonusHolmesRewardTemplateMap;
	}
	public void setBonusHolmesRewardTemplateMap(
			Map<Integer, List<BonusHolmesRewardTemplate>> bonusHolmesRewardTemplateMap) {
		BonusHolmesRewardTemplateMap = bonusHolmesRewardTemplateMap;
	}
	
	

}
