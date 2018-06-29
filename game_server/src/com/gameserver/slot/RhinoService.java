package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BounsRhinoTemplate;

public class RhinoService implements InitializeRequired {

	Map<Integer,List<BounsRhinoTemplate>> bounsRhinoTemplateMap = new HashMap<Integer,List<BounsRhinoTemplate>>();
	//每个老虎机里 那个 bonusNum 最大的那个
	Map<Integer,Integer> slotMaxBonusNum = new HashMap<Integer,Integer>();
	
	@Override
	public void init() {
		int startnum = 0;
		Map<Integer,BounsRhinoTemplate> rbounsRhinoTemplateMap = Globals.getTemplateService().getAll(BounsRhinoTemplate.class);
		for(BounsRhinoTemplate bounsRhinoTemplate:rbounsRhinoTemplateMap.values()){
			int slotNum = bounsRhinoTemplate.getSlotsNum();
			List<BounsRhinoTemplate> bounsRhinoTemplates = null;
			if(startnum != slotNum){
				startnum = slotNum;
				bounsRhinoTemplates=new ArrayList<BounsRhinoTemplate>();
				bounsRhinoTemplates.add(bounsRhinoTemplate);
				bounsRhinoTemplateMap.put(slotNum,bounsRhinoTemplates);
			}else{
				bounsRhinoTemplates=bounsRhinoTemplateMap.get(slotNum);
				bounsRhinoTemplates.add(bounsRhinoTemplate);
			}
			//获取每个老虎机 最大的bonusNum;
			Integer bonusNum = slotMaxBonusNum.get(slotNum);
			if(bonusNum == null){
				slotMaxBonusNum.put(slotNum, bounsRhinoTemplate.getBounsNum());
			}else{
				if(bounsRhinoTemplate.getBounsNum() > bonusNum.intValue()){
					slotMaxBonusNum.put(slotNum, bounsRhinoTemplate.getBounsNum());
				}
			}
		}
	}
	public Map<Integer, List<BounsRhinoTemplate>> getBounsRhinoTemplateMap() {
		return bounsRhinoTemplateMap;
	}
	public void setBounsRhinoTemplateMap(
			Map<Integer, List<BounsRhinoTemplate>> bounsRhinoTemplateMap) {
		this.bounsRhinoTemplateMap = bounsRhinoTemplateMap;
	}
	public Map<Integer, Integer> getSlotMaxBonusNum() {
		return slotMaxBonusNum;
	}
	public void setSlotMaxBonusNum(Map<Integer, Integer> slotMaxBonusNum) {
		this.slotMaxBonusNum = slotMaxBonusNum;
	}
	
	

}
