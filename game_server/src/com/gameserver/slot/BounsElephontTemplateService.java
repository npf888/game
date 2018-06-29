package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BigWildRespinTemplate;
import com.gameserver.slot.template.BounsElephont1RewardTemplate;
import com.gameserver.slot.template.BounsElephont1Template;
import com.gameserver.slot.template.BounsElephontTemplate;

/**
 * 泰国大象老虎机
 * @author 郭君伟
 *
 */
public class BounsElephontTemplateService implements InitializeRequired {
	
	private static int post1 = 0;
	private static int post2 = 1;
	private static int post3 = 2;
	private static int post4 = 3;
	
	
	private Map<Integer,BigWildRespinTemplate> slotTypeWildNum = new HashMap<Integer,BigWildRespinTemplate>();
	
	private Map<Integer,Map<Integer,List<Integer>>> bonusPosMap = new HashMap<Integer,Map<Integer,List<Integer>>>();
	private Map<Integer,Map<Integer,List<Integer>>> bonusWeightMap = new HashMap<Integer,Map<Integer,List<Integer>>>();
	
	private Map<Integer,Integer> bonusNumMap = new HashMap<Integer,Integer>();

	/**
	 * 泰国象老虎机的小游戏 换成新的了
	 * 下面是新加 的xls表
	 */
	private Map<Integer,List<BounsElephont1Template>> slotType_BounsElephont1Template = new HashMap<Integer,List<BounsElephont1Template>>();
	
	private Map<Integer,List<BounsElephont1RewardTemplate>> slotType_BounsElephont1RewardTemplate 
		= new HashMap<Integer,List<BounsElephont1RewardTemplate>>();
	
	/***************************************************/
	
	
	@Override
	public void init() {
		
		 TemplateService templateService = Globals.getTemplateService();
			
		 Map<Integer,BigWildRespinTemplate> map1 =  templateService.getAll(BigWildRespinTemplate.class);
		 
		 for(BigWildRespinTemplate temp : map1.values()){
			 int slotType = temp.getSlotsNum();
			 slotTypeWildNum.put(slotType, temp);
		 }
		 
		 Map<Integer,BounsElephontTemplate> map2 =  templateService.getAll(BounsElephontTemplate.class);
		 
		 for(BounsElephontTemplate tem : map2.values()){
			 
			   int slotType = tem.getSlotsNum();
			   
			     int reel1 = tem.getBox1();
				 int reel2 = tem.getBox2();
				 int reel3 = tem.getBox3();
				 int reel4 = tem.getBox4();
			
				 
				 int weight1 = tem.getValue1();
				 int weight2 = tem.getValue2();
				 int weight3 = tem.getValue3();
				 int weight4 = tem.getValue4();
				 
				 assemblyData(bonusPosMap,slotType,post1,reel1);
				 assemblyData(bonusPosMap,slotType,post2,reel2);
				 assemblyData(bonusPosMap,slotType,post3,reel3);
				 assemblyData(bonusPosMap,slotType,post4,reel4);
				 assemblyData(bonusWeightMap,slotType,post1,weight1);
				 assemblyData(bonusWeightMap,slotType,post2,weight2);
				 assemblyData(bonusWeightMap,slotType,post3,weight3);
				 assemblyData(bonusWeightMap,slotType,post4,weight4);
				 
				 bonusNumMap.put(slotType, tem.getBonusNum());
		 }
		 
		 
		 /**
		 * 泰国象老虎机的小游戏 换成新的了
		 * 下面是新加 的xls表
		 */
		 
		 Map<Integer,BounsElephont1Template> rMapElephont1 =  templateService.getAll(BounsElephont1Template.class);
		 int startNum = -1;
		 for(BounsElephont1Template bounsElephont1Template:rMapElephont1.values()){
			int type = bounsElephont1Template.getSlotsNum();
			if(startNum != type){
				startNum = type;
				List<BounsElephont1Template> bounsElephont1TemplateList = new ArrayList<BounsElephont1Template>();
				bounsElephont1TemplateList.add(bounsElephont1Template);
				slotType_BounsElephont1Template.put(type, bounsElephont1TemplateList);
			}else{
				List<BounsElephont1Template> bounsElephont1TemplateList = slotType_BounsElephont1Template.get(type);
				bounsElephont1TemplateList.add(bounsElephont1Template);
			}
		 }
		 
		 Map<Integer,BounsElephont1RewardTemplate> rMapElephont1Reward =  templateService.getAll(BounsElephont1RewardTemplate.class);
		 int startNum1 = -1;
		 for(BounsElephont1RewardTemplate bounsElephont1RewardTemplate:rMapElephont1Reward.values()){
			int type = bounsElephont1RewardTemplate.getSlotsNum();
			if(startNum1 != type){
				startNum1 = type;
				List<BounsElephont1RewardTemplate> bounsElephont1RewardTemplateList = new ArrayList<BounsElephont1RewardTemplate>();
				bounsElephont1RewardTemplateList.add(bounsElephont1RewardTemplate);
				slotType_BounsElephont1RewardTemplate.put(type, bounsElephont1RewardTemplateList);
			
			}else{
				List<BounsElephont1RewardTemplate> bounsElephont1RewardTemplateList = slotType_BounsElephont1RewardTemplate.get(type);
				bounsElephont1RewardTemplateList.add(bounsElephont1RewardTemplate);
			}
		 }
		/***************************************************/
		 
	}
	

	private void assemblyData(Map<Integer,Map<Integer,List<Integer>>> map,int slotType,int post,int reel){
		 Map<Integer,List<Integer>> map1 =  map.get(slotType);
		 if(map1 == null){
			 map1 = new HashMap<Integer,List<Integer>>();
			 map.put(slotType, map1);
		 }
		 List<Integer> list1 = map1.get(post);
		 if(list1 == null){
			 list1 = new ArrayList<Integer>();
			 map1.put(post, list1);
		 }
		 list1.add(reel);
	}
	
	/**
	 * 是否中了大Wild
	 * @param slotType
	 * @param bigWildNum
	 * @return
	 */
	public boolean isBigWild(int slotType,int bigWildNum){
		if(slotTypeWildNum.containsKey(slotType)){
			int wildNum = slotTypeWildNum.get(slotType).getBigWildNum();
			if(wildNum <= bigWildNum){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 返回免费转动个数
	 * @param slotType
	 * @return
	 */
	public int bigWildNum(int slotType){
		return slotTypeWildNum.get(slotType).getRespinNum();
	}
	
	/**
	 * 是否中小游戏
	 * @param slotType
	 * @param bonusNum
	 * @return
	 */
	public boolean isBonus(int slotType,int bonusNum){
		if(bonusNumMap.containsKey(slotType)){
			if(bonusNumMap.get(slotType) <= bonusNum){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 随机4个箱子位置
	 * @param slotType
	 * @return
	 */
	public List<Integer> getRandBox(int slotType){
		List<Integer> listPost = new ArrayList<Integer>();
		if(!bonusPosMap.containsKey(slotType) || !bonusWeightMap.containsKey(slotType)){
			return null;
		}
		Map<Integer,List<Integer>> map1 = bonusPosMap.get(slotType);
		Map<Integer,List<Integer>> map2 = bonusWeightMap.get(slotType);
		
		listPost.add(getPost(map1.get(post1),map2.get(post1)));
		listPost.add(getPost(map1.get(post2),map2.get(post2)));
		listPost.add(getPost(map1.get(post3),map2.get(post3)));
		listPost.add(getPost(map1.get(post4),map2.get(post4)));
		return listPost;
	}
	
	private int getPost(List<Integer> list1,List<Integer> list2){
          List<Integer>  post = ArrayUtils.randomFromArray(list1, ArrayUtils.intList2Array(list2), 1, false);
		return post.get(0);
	}
	
	
	/**
	 * 
	 *泰国象翻新的游戏
	 * 
	 * 
	 */
	
	/**
	 * 是否中小游戏
	 * @param slotType
	 * @param bonusNum
	 * @return
	 */
	public boolean isNewBonus(int slotType,int bonusNum){
		 List<BounsElephont1Template>  bounsElephont1TemplateList =	slotType_BounsElephont1Template.get(slotType);
		 if(bounsElephont1TemplateList != null && bounsElephont1TemplateList.size()>0){
			 BounsElephont1Template bounsElephont1Template = bounsElephont1TemplateList.get(0);
			 if(bounsElephont1Template.getBonusNum() <= bonusNum){
				 return true;
			 }
		 }
		return false;
	}
	/**
	 * 返回相同的次数
	 * @param slotType
	 * @param bonusNum
	 * @return
	 */
	public int getSameNum(int slotType){
		List<BounsElephont1Template>  bounsElephont1TemplateList =	slotType_BounsElephont1Template.get(slotType);
		if(bounsElephont1TemplateList != null && bounsElephont1TemplateList.size()>0){
			BounsElephont1Template bounsElephont1Template = bounsElephont1TemplateList.get(0);
			return bounsElephont1Template.getSamNum();
		}
		return 0;
	}
	/**
	 * 返回游戏的次数的次数
	 * @param slotType
	 * @param bonusNum
	 * @return
	 */
	public int getTimes(int slotType){
		List<BounsElephont1Template>  bounsElephont1TemplateList =	slotType_BounsElephont1Template.get(slotType);
		if(bounsElephont1TemplateList != null && bounsElephont1TemplateList.size()>0){
			BounsElephont1Template bounsElephont1Template = bounsElephont1TemplateList.get(0);
			return bounsElephont1Template.getTimes();
		}
		return 0;
	}
	/**
	 * 总共有 几关
	 * @param slotType
	 * @param bonusNum
	 * @return
	 */
	public int getTotalPass(int slotType){
		List<BounsElephont1RewardTemplate> bounsElephont1RewardTemplateList = slotType_BounsElephont1RewardTemplate.get(slotType);
		int totalPass = 0;
		int pass = 0;
		if(bounsElephont1RewardTemplateList != null && bounsElephont1RewardTemplateList.size()>0){
			for(BounsElephont1RewardTemplate bounsElephont1RewardTemplate:bounsElephont1RewardTemplateList){
				if(pass != bounsElephont1RewardTemplate.getStage()){
					pass=bounsElephont1RewardTemplate.getStage();
					totalPass++;
				}
			}
		}
		return totalPass;
	}
	/**
	 * 根据 typeNum 来获取 需要的单线下注 额的倍数
	 * @param slotType
	 * @param bonusNum
	 * @return
	 */
	public int getRewardByTypeNum(int slotType,int pass,int samePictureNum){
		List<BounsElephont1RewardTemplate> bounsElephont1RewardTemplateList = slotType_BounsElephont1RewardTemplate.get(slotType);
		if(bounsElephont1RewardTemplateList != null && bounsElephont1RewardTemplateList.size()>0){
			for(BounsElephont1RewardTemplate bounsElephont1RewardTemplate:bounsElephont1RewardTemplateList){
				if(pass == bounsElephont1RewardTemplate.getStage() && samePictureNum == bounsElephont1RewardTemplate.getTypeNum()){
					return bounsElephont1RewardTemplate.getReward();
				}
			}
			
		}
		return 0;
	}
	//随机获取每一关的  template对象
	public BounsElephont1RewardTemplate getNewPost(int slotType,int pass,List<BounsElephont1RewardTemplate> hasPassRewardList){
		List<Integer> ids = new ArrayList<Integer>();
		for(BounsElephont1RewardTemplate hasReward:hasPassRewardList){
			ids.add(hasReward.getId());
		}
		List<BounsElephont1RewardTemplate> bounsElephont1RewardTemplateList = slotType_BounsElephont1RewardTemplate.get(slotType);
		List<BounsElephont1RewardTemplate> passRewardList = new ArrayList<BounsElephont1RewardTemplate>();
		List<Integer> weightList = new ArrayList<Integer>();
		for(BounsElephont1RewardTemplate bounsElephont1RewardTemplate:bounsElephont1RewardTemplateList){
			if(ids.contains(bounsElephont1RewardTemplate.getId())){
				continue;
			}
			int stage = bounsElephont1RewardTemplate.getStage();
			int weight = bounsElephont1RewardTemplate.getWeight();
			if(stage == pass){
				passRewardList.add(bounsElephont1RewardTemplate);
				weightList.add(weight);
			}
		}
		
        List<BounsElephont1RewardTemplate>  post = ArrayUtils.randomFromArray(passRewardList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	 
	 

}
