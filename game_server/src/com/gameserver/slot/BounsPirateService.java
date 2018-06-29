package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BounsPirate1Step1Template;
import com.gameserver.slot.template.BounsPirate1Step2Template;
import com.gameserver.slot.template.BounsPirate1Template;
import com.gameserver.slot.template.BounsPirate2RewardTemplate;
import com.gameserver.slot.template.BounsPirate2Template;
import com.gameserver.slot.template.BounsPirate3RewardTemplate;
import com.gameserver.slot.template.BounsPirate3Template;

/**
 * 海盗 老虎机
 * @author JavaServer
 *
 */
public class BounsPirateService implements InitializeRequired {

	/**
	 * 海盗交锋
	 */
	//slotNum--对应--的 List
	Map<Integer,List<BounsPirate1Template>> slotNumBounsPirate1Map = new HashMap<Integer,List<BounsPirate1Template>>();
	//slotNum--对应--的 List
	Map<Integer,List<BounsPirate1Step1Template>> slotNumBounsPirate1Step1Map = new HashMap<Integer,List<BounsPirate1Step1Template>>();
	Map<Integer,List<String>> pirate1Step1WeightMap = new HashMap<Integer,List<String>>();
	//slotNum--对应--的 List
	Map<Integer,List<BounsPirate1Step2Template>> slotNumBounsPirate2Step1Map = new HashMap<Integer,List<BounsPirate1Step2Template>>();
	Map<Integer,List<Integer>> pirate1Step2WeightMap = new HashMap<Integer,List<Integer>>();
	
	/**
	 * 海岛钓鱼
	 */
	//slotNum--对应--的 List
	Map<Integer,List<BounsPirate2Template>> slotNumBBounsPirate2Map = new HashMap<Integer,List<BounsPirate2Template>>();
	//slotNum--对应--的 List
	Map<Integer,List<BounsPirate2RewardTemplate>> slotNumBounsPirate2RewardMap = new HashMap<Integer,List<BounsPirate2RewardTemplate>>();
	Map<Integer,List<Integer>> bounsPirate2WeightMap = new HashMap<Integer,List<Integer>>();
	
	
	/**
	 * 宝藏探秘
	 */
	//slotNum--对应--的 List
	Map<Integer,List<BounsPirate3Template>> slotNumBounsPirate3Map = new HashMap<Integer,List<BounsPirate3Template>>();
	//slotNum--对应--的 List
	Map<Integer,List<BounsPirate3RewardTemplate>> slotNumBounsPirate3RewardMap = new HashMap<Integer,List<BounsPirate3RewardTemplate>>();
	Map<Integer,List<String>> bounsPirate3WeightMap = new HashMap<Integer,List<String>>();
	
	@Override
	public void init() {
		/**
		 * 海盗交锋
		 */
		initPirateFight1_1();
		initPirateFight1();
		initPirateFight2();
		/**
		 * 海岛钓鱼
		 */
		initIslandFish_1();
		initIslandFish();
		/**
		 * 宝藏探秘
		 */
		initPirateMystery_1();
		initPirateMystery();
		 
		
	}



	private void initPirateFight1_1() {
		Map<Integer, BounsPirate1Template> bounsPirate1TemplateMapXLS = Globals.getTemplateService().getAll(BounsPirate1Template.class);
		int startNum = -1;
		for(BounsPirate1Template bps: bounsPirate1TemplateMapXLS.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsPirate1Template> bounsPirate1TemplateList = new ArrayList<BounsPirate1Template>();
				bounsPirate1TemplateList.add(bps);
				slotNumBounsPirate1Map.put(type, bounsPirate1TemplateList);
			}else{
				List<BounsPirate1Template> BounsPirate1Step1List = slotNumBounsPirate1Map.get(type);
				BounsPirate1Step1List.add(bps);
			}
		}
		
	}

	/**
	 * 海盗交锋  1
	 */
	private void initPirateFight1() {
		
		Map<Integer, BounsPirate1Step1Template> bounsPirate1Step1MapXLS = Globals.getTemplateService().getAll(BounsPirate1Step1Template.class);
		int startNum = -1;
		for(BounsPirate1Step1Template bps: bounsPirate1Step1MapXLS.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsPirate1Step1Template> bounsPirate1Step1TemplateList = new ArrayList<BounsPirate1Step1Template>();
				bounsPirate1Step1TemplateList.add(bps);
				slotNumBounsPirate1Step1Map.put(type, bounsPirate1Step1TemplateList);
				List<String> weight = new ArrayList<String>();
				weight.add(bps.getType()+","+bps.getWeight());
				pirate1Step1WeightMap.put(type,weight);
			}else{
				List<BounsPirate1Step1Template> BounsPirate1Step1List = slotNumBounsPirate1Step1Map.get(type);
				BounsPirate1Step1List.add(bps);
				
				List<String> weight = pirate1Step1WeightMap.get(type);
				weight.add(bps.getType()+","+bps.getWeight());
			}
		}
		
	}
	
	public Object getPost(List<?> list1,List<Integer> list2){
        List<?>  post = ArrayUtils.randomFromArray(list1, ArrayUtils.intList2Array(list2), 1, false);
		return post.get(0);
	}
	/**
	 * 海盗交锋 2
	 */
	
	private void initPirateFight2() {
		Map<Integer, BounsPirate1Step2Template> bounsPirate1Step2MapXLS = Globals.getTemplateService().getAll(BounsPirate1Step2Template.class);
		 int startNum = -1;
		 for(BounsPirate1Step2Template bps: bounsPirate1Step2MapXLS.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsPirate1Step2Template> bounsPirate1Step2TemplateList = new ArrayList<BounsPirate1Step2Template>();
				bounsPirate1Step2TemplateList.add(bps);
				slotNumBounsPirate2Step1Map.put(type, bounsPirate1Step2TemplateList);
				
				List<Integer> weight = new ArrayList<Integer>();
				weight.add(bps.getWeight());
				pirate1Step2WeightMap.put(type,weight);
			}else{
				List<BounsPirate1Step2Template> BounsPirate1Step2List = slotNumBounsPirate2Step1Map.get(type);
				BounsPirate1Step2List.add(bps);
				
				List<Integer> weight = pirate1Step2WeightMap.get(type);
				weight.add(bps.getWeight());
			}
		 }
		
	}
	
	/**
	 * 海岛钓鱼
	 */
	private void initIslandFish_1() {
		// TODO Auto-generated method stub
		Map<Integer, BounsPirate2Template> bounsPirate2MapXLS = Globals.getTemplateService().getAll(BounsPirate2Template.class);
		int startNum = -1;
		for(BounsPirate2Template bps: bounsPirate2MapXLS.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsPirate2Template> bounsPirate2TemplateList = new ArrayList<BounsPirate2Template>();
				bounsPirate2TemplateList.add(bps);
				slotNumBBounsPirate2Map.put(type, bounsPirate2TemplateList);
			}else{
				List<BounsPirate2Template> BounsPirate1Step2List = slotNumBBounsPirate2Map.get(type);
				BounsPirate1Step2List.add(bps);
			}
		}
		
	}
	private void initIslandFish() {
		
		 Map<Integer, BounsPirate2RewardTemplate> bounsPirate2RewardMapXLS = Globals.getTemplateService().getAll(BounsPirate2RewardTemplate.class);
		 int startNum = -1;
		 for(BounsPirate2RewardTemplate bps: bounsPirate2RewardMapXLS.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsPirate2RewardTemplate> bounsPirate2TemplateList = new ArrayList<BounsPirate2RewardTemplate>();
				bounsPirate2TemplateList.add(bps);
				slotNumBounsPirate2RewardMap.put(type, bounsPirate2TemplateList);
				
				List<Integer> weight = new ArrayList<Integer>();
				weight.add(bps.getWeight());
				bounsPirate2WeightMap.put(type,weight);
			}else{
				List<BounsPirate2RewardTemplate> BounsPirate1RewardList = slotNumBounsPirate2RewardMap.get(type);
				BounsPirate1RewardList.add(bps);
				
				List<Integer> weight = bounsPirate2WeightMap.get(type);
				weight.add(bps.getWeight());
			}
		 }
		 
	}
	
	
	/**
	 * 宝藏探秘
	 */

	private void initPirateMystery_1() {
		Map<Integer, BounsPirate3Template> BounsPirate3MapXLS = Globals.getTemplateService().getAll(BounsPirate3Template.class);
		int startNum = -1;
		for(BounsPirate3Template bps: BounsPirate3MapXLS.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsPirate3Template> bounsPirate3List = new ArrayList<BounsPirate3Template>();
				bounsPirate3List.add(bps);
				slotNumBounsPirate3Map.put(type, bounsPirate3List);
			}else{
				List<BounsPirate3Template> bounsPirate3List = slotNumBounsPirate3Map.get(type);
				bounsPirate3List.add(bps);
			}
		}
	}
	private void initPirateMystery() {
		Map<Integer, BounsPirate3RewardTemplate> BounsPirate3RewardMapXLS = Globals.getTemplateService().getAll(BounsPirate3RewardTemplate.class);
		int startNum = -1;
		for(BounsPirate3RewardTemplate bps: BounsPirate3RewardMapXLS.values()){
			int type = bps.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsPirate3RewardTemplate> bounsPirate3RewardList = new ArrayList<BounsPirate3RewardTemplate>();
				bounsPirate3RewardList.add(bps);
				slotNumBounsPirate3RewardMap.put(type, bounsPirate3RewardList);
				
				List<String> weight = new ArrayList<String>();
				weight.add(bps.getLevel()+","+bps.getWeight());
				bounsPirate3WeightMap.put(type,weight);
			}else{
				List<BounsPirate3RewardTemplate> bounsPirate3List = slotNumBounsPirate3RewardMap.get(type);
				bounsPirate3List.add(bps);
				
				List<String> weight = bounsPirate3WeightMap.get(type);
				weight.add(bps.getLevel()+","+bps.getWeight());
			}
		 }
	}

	
	
	
	
	
	
	
	
	
	public List<BounsPirate1Step1Template> getSlotNumBounsPirate1Step1ListBySlotNum(int slotNum,int type) {
		List<BounsPirate1Step1Template> reList = new ArrayList<BounsPirate1Step1Template>();
		List<BounsPirate1Step1Template> bounsPirate1Step1TemplateList=  slotNumBounsPirate1Step1Map.get(slotNum);
		for(BounsPirate1Step1Template bounsPirate1Step1Template:bounsPirate1Step1TemplateList){
			int theType = bounsPirate1Step1Template.getType();
			if(theType == type){
				reList.add(bounsPirate1Step1Template);
			}
		}
		return reList;
	}

	
	
	private List<Integer> getWeight(List<String> weightList,int type){
		List<Integer> reList = new ArrayList<Integer>();
		for(String weight :weightList){
			String[] weightArr = weight.split(",");
			if(Integer.valueOf(weightArr[0]).intValue() == type){
				reList.add(Integer.valueOf(weightArr[1]));
			}
		}
		
		return reList;
	}
	public List<Integer> getPirate1Step1WeightList(int slotNum,int type) {
		return getWeight(pirate1Step1WeightMap.get(slotNum),type);
	}


	public List<BounsPirate1Step2Template> getSlotNumBounsPirate1Step2ListBySlotNum(int slotNum) {
		return slotNumBounsPirate2Step1Map.get(slotNum);
	}


	public List<Integer> getPirate1Step2WeightList(int slotNum) {
		return pirate1Step2WeightMap.get(slotNum);
	}





	public List<BounsPirate2RewardTemplate> getSlotNumBounsPirate2RewardMap(int type) {
		return slotNumBounsPirate2RewardMap.get(type);
	}



	public Map<Integer, List<BounsPirate3RewardTemplate>> getSlotNumBounsPirate3RewardMap() {
		return slotNumBounsPirate3RewardMap;
	}



	public List<Integer> getBounsPirate2WeightList(int slotNum) {
		return bounsPirate2WeightMap.get(slotNum);
	}


	public List<BounsPirate3RewardTemplate> getSlotNumBounsPirate3ListBySlotNum(int slotNum,int type, List<BounsPirate3RewardTemplate> existList) {
		
		List<BounsPirate3RewardTemplate> reList = new ArrayList<BounsPirate3RewardTemplate>();
		List<BounsPirate3RewardTemplate> bounsPirate1RewardTemplateList=  slotNumBounsPirate3RewardMap.get(slotNum);
		for(BounsPirate3RewardTemplate bounsPirate3RewardTemplate:bounsPirate1RewardTemplateList){
			int level = bounsPirate3RewardTemplate.getLevel();
			if(level == type){
				if(!existList.contains(bounsPirate3RewardTemplate)){
					reList.add(bounsPirate3RewardTemplate);
				}
			}
		}
		return reList;
		
	}


	public List<Integer> getBounsPirate3WeightList(int slotNum,int type) {
		return getWeight(bounsPirate3WeightMap.get(slotNum),type);
	}



	public int getBonusNum(int type) {
		return slotNumBBounsPirate2Map.get(type).get(0).getBonusNum();
	}
	public int getSameNum(int type) {
		return slotNumBBounsPirate2Map.get(type).get(0).getSamNum();
	}

	
	
	
	
	

}
