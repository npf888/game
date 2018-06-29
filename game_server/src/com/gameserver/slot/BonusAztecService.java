package com.gameserver.slot;

import java.util.HashMap;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BonusAztecTemplate;

/**
 * 阿兹特克文明
 * @author 郭君伟
 *
 */
public class BonusAztecService implements InitializeRequired {
	
	private Map<Integer,BonusAztecTemplate> batMap = new HashMap<Integer,BonusAztecTemplate>();

	@Override
	public void init() {
		 TemplateService templateService = Globals.getTemplateService();
			
		 Map<Integer,BonusAztecTemplate> map =  templateService.getAll(BonusAztecTemplate.class);
		 
		 for(BonusAztecTemplate bt : map.values()){
			 batMap.put(bt.getSlotsNum(), bt);
		 }

	}
	
	/**
	 * 是否中小游戏
	 * @param slotType
	 * @param num
	 * @return
	 */
	public boolean isBonus(int slotType,int num){
		if(batMap.containsKey(slotType)){
			int puzzleNum = batMap.get(slotType).getPuzzleNum();
			if(puzzleNum <= num){
				return true;
			}
		}
		return false;
	}
	
	public int getBonus(int slotType){
		return batMap.get(slotType).getPuzzleNum();
	}
	
	/**
	 * 
	 * @param slotType
	 * @return
	 */
	public int getRewardNum(int slotType){
		if(batMap.containsKey(slotType)){
			return batMap.get(slotType).getRewardNum();
		}
		return 1;
	}

}
