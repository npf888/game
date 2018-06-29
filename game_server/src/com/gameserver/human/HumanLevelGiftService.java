package com.gameserver.human;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.template.TemplateService;
import com.gameserver.common.Globals;
import com.gameserver.human.template.LevelGift;

public class HumanLevelGiftService implements InitializeRequired {

	private Map<Integer,LevelGift> levelDataMap = new HashMap<Integer,LevelGift>();
	
	@Override
	public void init() {
		
		 TemplateService templateService = Globals.getTemplateService();
			
		 Map<Integer,LevelGift> map1 =  templateService.getAll(LevelGift.class);
		 
		 for(LevelGift lg : map1.values()){
			 levelDataMap.put(lg.getLevel(), lg);
		 }
		 Loggers.gfitLogger.info(JSON.toJSONString(levelDataMap));
	}
	
	/**
	 * 升级获取礼包
	 * @param level
	 * @return
	 */
	public int getLevelGold(int level){
		if(levelDataMap.containsKey(level)){
			return levelDataMap.get(level).getReward1Num();
		}
		return 0;
	}

	public LevelGift getLevelGift(int level){
		if(levelDataMap.containsKey(level)){
			return levelDataMap.get(level);
		}
		return null;
	}
}
