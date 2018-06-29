package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BonusWolfTemplate;

public class BonusWolfService implements InitializeRequired {

	private Map<Integer,List<BonusWolfTemplate>> bonusWolfTemplateMap = new HashMap<Integer,List<BonusWolfTemplate>>();
	@Override
	public void init() {
		Map<Integer,BonusWolfTemplate> bonusWolfTemplates = Globals.getTemplateService().getAll(BonusWolfTemplate.class);
		int startNum = -1;
		for(BonusWolfTemplate bw: bonusWolfTemplates.values()){
			//单次 bonus 中奖，两次 bonus中奖 ，三次 bonus 中奖
			int type = bw.getType();
			if(startNum != type){
				startNum = type;
				List<BonusWolfTemplate> bonusWolfTemplateList = new ArrayList<BonusWolfTemplate>();
				bonusWolfTemplateList.add(bw);
				bonusWolfTemplateMap.put(type, bonusWolfTemplateList);
			}else{
				List<BonusWolfTemplate> bonusWolfTemplateList = bonusWolfTemplateMap.get(type);
				bonusWolfTemplateList.add(bw);
			}
		}
	}
	public Map<Integer, List<BonusWolfTemplate>> getBonusWolfTemplateMap() {
		return bonusWolfTemplateMap;
	}
	public void setBonusWolfTemplateMap(
			Map<Integer, List<BonusWolfTemplate>> bonusWolfTemplateMap) {
		this.bonusWolfTemplateMap = bonusWolfTemplateMap;
	}

}
