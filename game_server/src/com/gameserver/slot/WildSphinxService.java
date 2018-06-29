package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BounsSphinxRewardTemplate;
import com.gameserver.slot.template.BounsSphinxTemplate;
import com.gameserver.slot.template.WildSphinxTemplate;
/**
 * 狮身人面老虎机 wild值
 * @author 牛鹏飞
 *
 */
/**
 * 狮身人面老虎机 wild值
 * @author 牛鹏飞
 *
 */
public class WildSphinxService implements  InitializeRequired {
	

	public static final int type_1 = 1; 
	public static final int type_2 = 2; 
	public static final int type_3 = 3; 
	

	private Map<Integer,Integer> bounsMap = new HashMap<Integer,Integer>();
	//此老虎机的 bonus 小游戏
	private List<BounsSphinxTemplate> bounsSphinxTemplateList = new ArrayList<BounsSphinxTemplate>();
//	//此老虎机的 bonus 小游戏 奖池
//	private List<BounsSphinxRewardTemplate> bounsSphinxRewardTemplateList = new ArrayList<BounsSphinxRewardTemplate>();
	//此老虎机的 bonus 小游戏 奖池
	private Map<Integer, Map<Integer,List<BounsSphinxRewardTemplate>>> bounsSphinxRewardTemplateMap = new HashMap<Integer, Map<Integer,List<BounsSphinxRewardTemplate>>>();
	
	//bouns 权值 (每个奖池里的 值用一套权值)
	private Map<Integer,List<Integer>> rewardWeightMap = new HashMap<Integer,List<Integer>>();  
	
	
	//WILD元素  随机倍数（如果是WILD元素要被随机 相乘）
	private Map<Integer,List<WildSphinxTemplate>> wildSphinxMap = new HashMap<Integer,List<WildSphinxTemplate>>();
	//WILD元素权值
	private Map<Integer,List<Integer>> wildWeightMap =  new HashMap<Integer,List<Integer>>();   
	
	@Override
	public void init() {
		
		//加载WILD元素
		Map<Integer,WildSphinxTemplate> wildSphinxTemplateMap = Globals.getTemplateService().getAll(WildSphinxTemplate.class);
		int startSlotNum = -1;
		for(WildSphinxTemplate bt : wildSphinxTemplateMap.values()){
			int slotnum = bt.getSlotsNum();
			if(bt.getSlotsNum() != startSlotNum){
				startSlotNum = slotnum;
				List<WildSphinxTemplate> wildSphinxTemplates = new ArrayList<WildSphinxTemplate>();
				wildSphinxTemplates.add(bt);
				wildSphinxMap.put(slotnum, wildSphinxTemplates);
				//权值组装数据
				List<Integer> weightList = new ArrayList<Integer>();
				weightList.add(bt.getWeight());
				wildWeightMap.put(slotnum,weightList);
			}else{
				List<WildSphinxTemplate> existList = wildSphinxMap.get(slotnum);
				existList.add(bt);
				//权值组装数据
				List<Integer> weightList = wildWeightMap.get(slotnum);
				weightList.add(bt.getWeight());
			}
			
			
			
		}
		//加载 bonus 
		Map<Integer,BounsSphinxTemplate> bounsSphinxTemplateMap= Globals.getTemplateService().getAll(BounsSphinxTemplate.class);
		for(BounsSphinxTemplate bt : bounsSphinxTemplateMap.values()){
			bounsSphinxTemplateList.add(bt);
			bounsMap.put(bt.getSlotsNum(), bt.getBonusNum());
		}
		//加载bonus 小游戏 奖池
		Map<Integer,BounsSphinxRewardTemplate> backBounsSphinxRewardTemplateMap= Globals.getTemplateService().getAll(BounsSphinxRewardTemplate.class);
		int startRewardNum = -1;
		for(BounsSphinxRewardTemplate brt : backBounsSphinxRewardTemplateMap.values()){
//			bounsSphinxRewardTemplateList.add(brt);
			Map<Integer, List<BounsSphinxRewardTemplate>> m = bounsSphinxRewardTemplateMap.get(brt.getSlotsNum());
			if(m==null)
			{
				m = new HashMap<Integer, List<BounsSphinxRewardTemplate>>();
				bounsSphinxRewardTemplateMap.put(brt.getSlotsNum(), m);
			}
			int rewardPool = brt.getRewardPool();
			if(rewardPool != startRewardNum){
				startRewardNum=rewardPool;
				List<BounsSphinxRewardTemplate> bsrList = new ArrayList<BounsSphinxRewardTemplate>();
				bsrList.add(brt);
				
				m.put(rewardPool, bsrList);
				
				//权值数据的组装
				List<Integer> rewardWeight = new ArrayList<Integer>();
				rewardWeight.add(brt.getWeight());
				rewardWeightMap.put(rewardPool, rewardWeight);
			}else{
				List<BounsSphinxRewardTemplate> existList = m.get(rewardPool);
				if(existList==null)
				{
					existList = new ArrayList<BounsSphinxRewardTemplate>();
					m.put(rewardPool, existList);
				}
				existList.add(brt);
				
				//权值数据的组装
				List<Integer> rewardList = rewardWeightMap.get(rewardPool);
				rewardList.add(brt.getWeight());
			}
			
		}
		
	}
	/*
	 * 根据权值获取 bouns 随机数
	 */
	public BounsSphinxRewardTemplate getRewardWeight(int rewardPool, int slotNum){
		try{
			List<BounsSphinxRewardTemplate>  post = ArrayUtils.randomFromArray(bounsSphinxRewardTemplateMap.get(slotNum).get(rewardPool), ArrayUtils.intList2Array(rewardWeightMap.get(rewardPool)), 1, false);
			return post.get(0);
		}catch(Exception e){
			
			return null;
		}
	}
	/*
	 * 根据权值获取 wild 随机数
	 */
	public WildSphinxTemplate getWildWeight(int slotNum){
		
		List<WildSphinxTemplate>  post = ArrayUtils.randomFromArray(wildSphinxMap.get(slotNum), ArrayUtils.intList2Array(wildWeightMap.get(slotNum)), 1, false);
		return post.get(0);
	}

	
	
	
	
	public Map<Integer, List<WildSphinxTemplate>> getWildSphinxMap() {
		return wildSphinxMap;
	}
	public void setWildSphinxMap(
			Map<Integer, List<WildSphinxTemplate>> wildSphinxMap) {
		this.wildSphinxMap = wildSphinxMap;
	}
	public List<BounsSphinxTemplate> getBounsSphinxTemplateList() {
		return bounsSphinxTemplateList;
	}
	public void setBounsSphinxTemplateList(
			List<BounsSphinxTemplate> bounsSphinxTemplateList) {
		this.bounsSphinxTemplateList = bounsSphinxTemplateList;
	}
//	public List<BounsSphinxRewardTemplate> getBounsSphinxRewardTemplateList() {
//		return bounsSphinxRewardTemplateList;
//	}
//	public void setBounsSphinxRewardTemplateList(
//			List<BounsSphinxRewardTemplate> bounsSphinxRewardTemplateList) {
//		this.bounsSphinxRewardTemplateList = bounsSphinxRewardTemplateList;
//	}
	public int getBonusNum(int slotNum){
		if(bounsMap.containsKey(slotNum)){
			return bounsMap.get(slotNum);
		}
		return 9999;
	}
	public Map<Integer, Map<Integer,List<BounsSphinxRewardTemplate>>> getBounsSphinxRewardTemplateMap() {
		return bounsSphinxRewardTemplateMap;
	}
	public void setBounsSphinxRewardTemplateMap(
			Map<Integer, Map<Integer,List<BounsSphinxRewardTemplate>>> bounsSphinxRewardTemplateMap) {
		this.bounsSphinxRewardTemplateMap = bounsSphinxRewardTemplateMap;
	}
	
	

}
