package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BounsZeusRewardTemplate;
import com.gameserver.slot.template.BounsZeusTemplate;
/**
 * 宙斯老虎机静态配置数据
 * @author 郭君伟
 *
 */
public class BounsZeusService implements InitializeRequired {
	
	/**
	 * 老虎机类型  模板数据
	 */
	private Map<Integer,BounsZeusTemplate> zousMap = new  HashMap<Integer,BounsZeusTemplate>();
	
	private Map<Integer,List<BounsZeusRewardTemplate>> zousReward = new HashMap<Integer,List<BounsZeusRewardTemplate>>();
	private Map<Integer,List<Integer>> zousRewardprobability = new HashMap<Integer,List<Integer>>();
	

	@Override
	public void init() {
		 TemplateService templateService = Globals.getTemplateService();
			
		 Map<Integer,BounsZeusTemplate> map1 =  templateService.getAll(BounsZeusTemplate.class);
		 
		 for(BounsZeusTemplate tem : map1.values()){
			 
			int type = tem.getSlotsNum();
			
			zousMap.put(type, tem);
		 }
		 
		 Map<Integer,BounsZeusRewardTemplate> map2 =  templateService.getAll(BounsZeusRewardTemplate.class);
		 
		 for(BounsZeusRewardTemplate tem : map2.values()){
			  int slotType = tem.getSlotsNum();
			  int id = tem.getId();
			 
			  List<BounsZeusRewardTemplate> list1 =  zousReward.get(slotType);
			  if(list1 == null ){
				  list1 = new ArrayList<BounsZeusRewardTemplate>();
				  zousReward.put(slotType, list1);
			  }
			  list1.add(tem);
			  
			  List<Integer> list2 =  zousRewardprobability.get(slotType);
			  if(list2 == null ){
				  list2 = new ArrayList<Integer>();
				  zousRewardprobability.put(slotType, list2);
			  }
			  list2.add(tem.getWeight());
		 }
		
	}
	
	public BounsZeusTemplate getBzTem(int slotType){
		return zousMap.get(slotType);
	} 
	
	/**
	 * 随机取一个元素要去掉一个元素，整个元素和逐渐减少
	 * @param slotType
	 * @return
	 */
	public BounsZeusRewardTemplate getBzRewardDragon(List<BounsZeusRewardTemplate> chList,List<Integer> weightList){
		List<BounsZeusRewardTemplate>  post = ArrayUtils.randomFromArray(chList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	public BounsZeusRewardTemplate getBzReward(int slotType){
		if(zousReward.containsKey(slotType) && zousRewardprobability.containsKey(slotType)){
			List<BounsZeusRewardTemplate> list1 = zousReward.get(slotType);
			List<Integer> list2 = zousRewardprobability.get(slotType);
			List<BounsZeusRewardTemplate>  post = ArrayUtils.randomFromArray(list1, ArrayUtils.intList2Array(list2), 1, false);
			return post.get(0);
		}
		return null;
	}

	public List<Integer> getZousRewardBySlotType(int slotType) {
		List<Integer> rList = new ArrayList<Integer>();
		List<BounsZeusRewardTemplate>  BounsZeusRewardTemplateList = zousReward.get(slotType);
		for(BounsZeusRewardTemplate BounsZeusRewardTemplate:BounsZeusRewardTemplateList){
			rList.add(BounsZeusRewardTemplate.getId());
		}
		return rList;
	}

	public List<BounsZeusRewardTemplate> getZousReward(int slotType) {
		return zousReward.get(slotType);
	}

	public void setZousReward(Map<Integer, List<BounsZeusRewardTemplate>> zousReward) {
		this.zousReward = zousReward;
	}

	public List<Integer> getZousRewardprobability(int slotType) {
		return zousRewardprobability.get(slotType);
	}


}
