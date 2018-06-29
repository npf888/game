package com.robot.slot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.core.template.TemplateService;
import com.gameserver.slot.template.BonusAztecTemplate;
import com.gameserver.slot.template.BonusOceanRewardTemplate;
import com.gameserver.slot.template.BoxTemplate;
import com.gameserver.slot.template.ScatterCowboyTemplate;
import com.gameserver.slot.template.ScatterCrastalTemplate;
import com.gameserver.slot.template.ScatterMultipleTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsListTemplate;

public class XLSService {

	public List<BonusOceanRewardTemplate> getTemplateFromXLS28(TemplateService templateService,int slotType){
		Map<Integer,List<BonusOceanRewardTemplate>> rBonusOceanRewardTemplateMap = new HashMap<Integer,List<BonusOceanRewardTemplate>>();
		Map<Integer,BonusOceanRewardTemplate> bonusOceanRewardTemplateMap = templateService.getAll(BonusOceanRewardTemplate.class);
		int startNum = -1;
		for(BonusOceanRewardTemplate bo: bonusOceanRewardTemplateMap.values()){
			int type = bo.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BonusOceanRewardTemplate> BonusOceanRewardTemplateList = new ArrayList<BonusOceanRewardTemplate>();
				BonusOceanRewardTemplateList.add(bo);
				rBonusOceanRewardTemplateMap.put(type, BonusOceanRewardTemplateList);
			}else{
				List<BonusOceanRewardTemplate> BonusOceanRewardTemplatList = rBonusOceanRewardTemplateMap.get(slotType);
				BonusOceanRewardTemplatList.add(bo);
			}
		}
		return rBonusOceanRewardTemplateMap.get(slotType);
	}

	public SlotsListTemplate getTemplateFromXLS1(TemplateService templateService,
			int slotId) {
		Collection<SlotsListTemplate> SlotsListTemplateLL= templateService.getAll(SlotsListTemplate.class).values();
		for(SlotsListTemplate slotsListTemplate:SlotsListTemplateLL){
			if(slotId == slotsListTemplate.getId()){
				return slotsListTemplate;
			}
		}
		return null;
	}
	
	public int getScatterTemplateMixScatterNum(TemplateService templateService,int slotType){
		//取出 scatterNum 最小的那个
		int scatterMin = 10000;
		Map<Integer,ScatterCrastalTemplate> scatterCrastalTemplateMap = templateService.getAll(ScatterCrastalTemplate.class);
		for(ScatterCrastalTemplate scatterCrastalTemplate:scatterCrastalTemplateMap.values()){
			if(scatterCrastalTemplate.getSlotsNum() == slotType){
				if(scatterCrastalTemplate.getScatterNum() < scatterMin){
					scatterMin=scatterCrastalTemplate.getScatterNum();
				}
			}
		}
		return scatterMin;
	}
	public int getNormolScatterTemplateMixScatterNum(TemplateService templateService,int slotType){
		//取出 scatterNum 最小的那个
		int scatterMin = 10000;
		Map<Integer,ScatterTemplate> scatterTemplateMap = templateService.getAll(ScatterTemplate.class);
		for(ScatterTemplate scatterTemplate:scatterTemplateMap.values()){
			if(scatterTemplate.getSlotsNum() == slotType){
				if(scatterTemplate.getScatterNum() < scatterMin){
					scatterMin=scatterTemplate.getScatterNum();
				}
			}
		}
		return scatterMin;
	}
	/**
	 * 西部牛仔
	 * @param templateService
	 * @param slotType
	 * @return
	 */
	public int getScatterCowboyRewardNum(TemplateService templateService,int slotType){
		//取出 scatterNum 最最大的那个
		int scatterMax = 0;
		int rewardNum = 0;
		Map<Integer,ScatterCowboyTemplate> scatterCowboyTemplateMap = templateService.getAll(ScatterCowboyTemplate.class);
		for(ScatterCowboyTemplate scatterCowboyTemplate:scatterCowboyTemplateMap.values()){
			if(scatterCowboyTemplate.getSlotsNum() == slotType){
				if(scatterCowboyTemplate.getScatterNum() > scatterMax){
					scatterMax=scatterCowboyTemplate.getScatterNum();
					rewardNum=scatterCowboyTemplate.getRewardNum();
				}
			}
		}
		return rewardNum/100;
	}

	/**
	 * 获取维密的最小scatterNum
	 * @param templateService
	 * @param slotType
	 * @return
	 */
	public int getScatterMultipleScatterNum(TemplateService templateService, int slotType) {
		//取出 scatterNum 最小的那个
		int scatterMin = 10000;
		Map<Integer,ScatterMultipleTemplate> scatterMultipleTemplateMap = templateService.getAll(ScatterMultipleTemplate.class);
		for(ScatterMultipleTemplate scatterMultipleTemplate:scatterMultipleTemplateMap.values()){
			if(scatterMultipleTemplate.getSlotsNum() == slotType){
				if(scatterMultipleTemplate.getScatterNum() < scatterMin){
					scatterMin=scatterMultipleTemplate.getScatterNum();
				}
			}
		}
		return scatterMin;
	}
	/**
	 * 西部牛仔的最小scatterNum
	 * @param templateService
	 * @param slotType
	 * @return
	 */
	public int getWestScatterNum(TemplateService templateService, int slotType) {
		//取出 scatterNum 最小的那个
		int scatterMin = 10000;
		Map<Integer,ScatterCowboyTemplate> scatterCowboyTemplateMap = templateService.getAll(ScatterCowboyTemplate.class);
		for(ScatterCowboyTemplate scatterCowboyTemplate:scatterCowboyTemplateMap.values()){
			if(scatterCowboyTemplate.getSlotsNum() == slotType){
				if(scatterCowboyTemplate.getScatterNum() < scatterMin){
					scatterMin=scatterCowboyTemplate.getScatterNum();
				}
			}
		}
		return scatterMin;
	}

	public BoxTemplate getBoxTemplate(TemplateService templateService,
			int slotType) {
		Map<Integer,BoxTemplate> boxTemplateMap = templateService.getAll(BoxTemplate.class);
		List<BoxTemplate> boxTemplates = new ArrayList<BoxTemplate>();
		for(BoxTemplate boxTemplate :boxTemplateMap.values()){
			if(boxTemplate.getSlotsNum() == slotType && boxTemplate.getLevelDown()<=1 && boxTemplate.getLevelUp()>=1){
				boxTemplates.add(boxTemplate);
			}
		}
		Random random = new Random();
		return boxTemplates.get(random.nextInt(boxTemplates.size()));
	}
	public int getBoxTemplateSize(TemplateService templateService,
			int slotType) {
		int i = 0;
		Map<Integer,BoxTemplate> boxTemplateMap = templateService.getAll(BoxTemplate.class);
		for(BoxTemplate boxTemplate :boxTemplateMap.values()){
			if(boxTemplate.getSlotsNum() == slotType && boxTemplate.getLevelDown()<=1 && boxTemplate.getLevelUp()>=1){
				i++;
			}
		}
		Random random = new Random();
		int x = random.nextInt(i);
		return x;
	}
	//阿兹特克 文明 获取奖金值
	public int getReward(TemplateService templateService,
			int slotType) {
		Map<Integer,BonusAztecTemplate> bonusAztecTemplateMap = templateService.getAll(BonusAztecTemplate.class);
		for(BonusAztecTemplate bonusAztecTemplate :bonusAztecTemplateMap.values()){
			if(slotType == bonusAztecTemplate.getSlotsNum()){
				return bonusAztecTemplate.getRewardNum();
			}
		}
		return 0;
	}
	//阿兹特克 文明 获取对比值
	public boolean isBonus(TemplateService templateService,int slotType,int num){
		Map<Integer,BonusAztecTemplate> bonusAztecTemplateMap = templateService.getAll(BonusAztecTemplate.class);
		for(BonusAztecTemplate bonusAztecTemplate :bonusAztecTemplateMap.values()){
			if(slotType == bonusAztecTemplate.getSlotsNum()){
				int puzzleNum = bonusAztecTemplate.getPuzzleNum();
				if(puzzleNum <= num){
					return true;
				}
			}
		}
		return false;
	}
}
