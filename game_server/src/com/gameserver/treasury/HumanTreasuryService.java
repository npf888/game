package com.gameserver.treasury;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.treasury.template.TreasuryTemplate;

public class HumanTreasuryService implements AfterInitializeRequired, InitializeRequired {

	Map<Integer,TreasuryTemplate> treasuryMap = new HashMap<Integer,TreasuryTemplate>();
	List<TreasuryTemplate> treasuryTemplateList = new ArrayList<TreasuryTemplate>();
	@Override
	public void init() {
		Map<Integer,TreasuryTemplate> treasuryTemplateMaps = Globals.getTemplateService().getAll(TreasuryTemplate.class);
		for(TreasuryTemplate treasuryTemplate:treasuryTemplateMaps.values()){
			treasuryMap.put(treasuryTemplate.getTypeTreasury(), treasuryTemplate);
			treasuryTemplateList.add(treasuryTemplate);
		}
		
	}

	@Override
	public void afterInit() {
		
	}

	public Map<Integer, TreasuryTemplate> getTreasuryMap() {
		return treasuryMap;
	}

	public void setTreasuryMap(Map<Integer, TreasuryTemplate> treasuryMap) {
		this.treasuryMap = treasuryMap;
	}

	public List<TreasuryTemplate> getTreasuryTemplateList() {
		return treasuryTemplateList;
	}

	public void setTreasuryTemplateList(List<TreasuryTemplate> treasuryTemplateList) {
		this.treasuryTemplateList = treasuryTemplateList;
	}
	
	

}
