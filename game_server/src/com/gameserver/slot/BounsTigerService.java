package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BounsTigerRewardTemplate;
import com.gameserver.slot.template.BounsTigerTemplate;

public class BounsTigerService implements InitializeRequired {

	public static final int is_mathch_yes = 1;
	public static final int is_mathch_no = 0;
	List<BounsTigerTemplate> bounsTigerList = new ArrayList<BounsTigerTemplate>();
	List<BounsTigerRewardTemplate> bounsTigerRewardList = new ArrayList<BounsTigerRewardTemplate>();
	//相应奖池对应的对象
	Map<Integer,BounsTigerRewardTemplate> bounsTigerRewardMap = new HashMap<Integer,BounsTigerRewardTemplate>();
	List<Integer> weightList = new ArrayList<Integer>();
	@Override
	public void init() {
		Map<Integer,BounsTigerTemplate> bounsTigerMap = Globals.getTemplateService().getAll(BounsTigerTemplate.class);
		for(BounsTigerTemplate bounsTigerTemplate:bounsTigerMap.values()){
			bounsTigerList.add(bounsTigerTemplate);
		}
		
		Map<Integer,BounsTigerRewardTemplate> reBounsTigerRewardMap = Globals.getTemplateService().getAll(BounsTigerRewardTemplate.class);
		for(BounsTigerRewardTemplate bounsTigerRewardTemplate:reBounsTigerRewardMap.values()){
			bounsTigerRewardList.add(bounsTigerRewardTemplate);
			bounsTigerRewardMap.put(bounsTigerRewardTemplate.getRewardPool(), bounsTigerRewardTemplate);
			weightList.add(bounsTigerRewardTemplate.getWeight());
		}
		
	}
	
	/*
	 * 根据权值获取 bouns 随机数
	 */
	public BounsTigerRewardTemplate getRewardBYWeight(){
		
		List<BounsTigerRewardTemplate>  post = ArrayUtils.randomFromArray(bounsTigerRewardList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	
	public List<BounsTigerTemplate> getBounsTigerList() {
		return bounsTigerList;
	}
	public void setBounsTigerList(List<BounsTigerTemplate> bounsTigerList) {
		this.bounsTigerList = bounsTigerList;
	}
	public List<BounsTigerRewardTemplate> getBounsTigerRewardList() {
		return bounsTigerRewardList;
	}
	public void setBounsTigerRewardList(
			List<BounsTigerRewardTemplate> bounsTigerRewardList) {
		this.bounsTigerRewardList = bounsTigerRewardList;
	}
	public Map<Integer, BounsTigerRewardTemplate> getBounsTigerRewardMap() {
		return bounsTigerRewardMap;
	}
	public void setBounsTigerRewardMap(
			Map<Integer, BounsTigerRewardTemplate> bounsTigerRewardMap) {
		this.bounsTigerRewardMap = bounsTigerRewardMap;
	}
	public List<Integer> getWeightList() {
		return weightList;
	}
	public void setWeightList(List<Integer> weightList) {
		this.weightList = weightList;
	}
	
	
	

}
