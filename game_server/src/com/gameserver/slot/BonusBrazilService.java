package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BonusBrazilBarGameTemplate;
import com.gameserver.slot.template.BonusBrazilSambaStageTemplate;
import com.gameserver.slot.template.BonusBrazilTemplate;

/**
 * 巴西风情 新的 小游戏 的service
 * @author JavaServer
 *
 */
public class BonusBrazilService implements InitializeRequired {
	/**
	 * 老虎机类型  模板数据
	 */
	private Map<Integer,List<BonusBrazilTemplate>> brazilMap = new  HashMap<Integer,List<BonusBrazilTemplate>>();
	
	private Map<Integer,List<BonusBrazilBarGameTemplate>> brazilBarGameMap = new HashMap<Integer,List<BonusBrazilBarGameTemplate>>();
	
	private Map<Integer,List<BonusBrazilSambaStageTemplate>> brazilSambaStageMap = new HashMap<Integer,List<BonusBrazilSambaStageTemplate>>();
	

	@Override
	public void init() {
		 TemplateService templateService = Globals.getTemplateService();
		 /**
		  * 1
		  */
		 Map<Integer,BonusBrazilTemplate> map1 =  templateService.getAll(BonusBrazilTemplate.class);
		 int startNum1 = -1;
		 for(BonusBrazilTemplate bb: map1.values()){
			int type = bb.getSlotsNum();
			if(startNum1 != type){
				startNum1 = type;
				List<BonusBrazilTemplate> bonusBrazilTemplateList = new ArrayList<BonusBrazilTemplate>();
				bonusBrazilTemplateList.add(bb);
				brazilMap.put(type, bonusBrazilTemplateList);
			}else{
				List<BonusBrazilTemplate> bonusBrazilTemplateList = brazilMap.get(type);
				bonusBrazilTemplateList.add(bb);
			}
		 }
			
			
		 
		 Map<Integer,BonusBrazilBarGameTemplate> map2 =  templateService.getAll(BonusBrazilBarGameTemplate.class);
		 int startNum2 = -1;
		 for(BonusBrazilBarGameTemplate bb: map2.values()){
			 int type = bb.getSlotsNum();
			 if(startNum2 != type){
				 startNum2 = type;
				 List<BonusBrazilBarGameTemplate> bonusBrazilBarGameTemplateList = new ArrayList<BonusBrazilBarGameTemplate>();
				 bonusBrazilBarGameTemplateList.add(bb);
				 brazilBarGameMap.put(type, bonusBrazilBarGameTemplateList);
			 }else{
				 List<BonusBrazilBarGameTemplate> bonusBrazilBarGameTemplateList = brazilBarGameMap.get(type);
				 bonusBrazilBarGameTemplateList.add(bb);
			 }
		 }
		 
		 Map<Integer,BonusBrazilSambaStageTemplate> map3 =  templateService.getAll(BonusBrazilSambaStageTemplate.class);
		 int startNum3 = -1;
		 for(BonusBrazilSambaStageTemplate bb: map3.values()){
			 int type = bb.getSlotsNum();
			 if(startNum3 != type){
				 startNum3 = type;
				 List<BonusBrazilSambaStageTemplate> bonusBrazilSambaStageTemplateList = new ArrayList<BonusBrazilSambaStageTemplate>();
				 bonusBrazilSambaStageTemplateList.add(bb);
				 brazilSambaStageMap.put(type, bonusBrazilSambaStageTemplateList);
				 
			 }else{
				 List<BonusBrazilSambaStageTemplate> bonusBrazilSambaStageTemplateList = brazilSambaStageMap.get(type);
				 bonusBrazilSambaStageTemplateList.add(bb);
			 }
		 }
		
	}

	/**
	 * 有多少个bonus 触发小游戏
	 * @param slotType
	 * @return
	 */
	public int getBounusNum(int slotType){
		return brazilMap.get(slotType).get(0).getBonusNum();
	}
	/**
	 * 根据权值 获取玩哪个游戏
	 * @return
	 * 
	 */
	public int getGonaBePlayedGameBySlotType(int slotType){
		
		List<BonusBrazilTemplate> bonusBrazilTemplateList= brazilMap.get(slotType);
		List<Integer> weightList = new ArrayList<Integer>();
		//获取权值 list
		for(BonusBrazilTemplate bb:bonusBrazilTemplateList){
			weightList.add(bb.getValue());
		}
		List<BonusBrazilTemplate>  post = ArrayUtils.randomFromArray(bonusBrazilTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0).getType();
	}
	/**
	 * 根据权值 获取 他们的某一轮的 值
	 * @return
	 * 
	 */
	public BonusBrazilBarGameTemplate getBrazilBarGameBySlotType(int slotType,int whichRound){
		
		List<BonusBrazilBarGameTemplate> bonusBrazilBarGameTemplateList= brazilBarGameMap.get(slotType);
		List<Integer> weightList = new ArrayList<Integer>();
		List<BonusBrazilBarGameTemplate> rBonusBrazilBarGameTemplateList = new ArrayList<BonusBrazilBarGameTemplate>();
		//获取权值 list
		for(BonusBrazilBarGameTemplate bb:bonusBrazilBarGameTemplateList){
			if(bb.getStage() == whichRound){
				weightList.add(bb.getWeight());
				rBonusBrazilBarGameTemplateList.add(bb);
			}
		}
		List<BonusBrazilBarGameTemplate>  post = ArrayUtils.randomFromArray(rBonusBrazilBarGameTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	
	/**
	 * 根据 根据用户选择的颜色  通过 权值 获取 的对象
	 * @return
	 * 
	 */
	public BonusBrazilSambaStageTemplate getBrazilSambaStageBySlotType(int slotType,int whichColor){
		
		List<BonusBrazilSambaStageTemplate> bonusBrazilSambaStageTemplateList= brazilSambaStageMap.get(slotType);
		List<BonusBrazilSambaStageTemplate> cBonusBrazilSambaStageTemplateList = new ArrayList<BonusBrazilSambaStageTemplate>();
		List<Integer> weightList = new ArrayList<Integer>();
		//获取权值 list
		for(BonusBrazilSambaStageTemplate bb:bonusBrazilSambaStageTemplateList){
			if(bb.getVelocity() == whichColor){
				weightList.add(bb.getWeight());
				cBonusBrazilSambaStageTemplateList.add(bb);
			}
		}
		List<BonusBrazilSambaStageTemplate>  post = ArrayUtils.randomFromArray(cBonusBrazilSambaStageTemplateList, ArrayUtils.intList2Array(weightList), 1, false);
		return post.get(0);
	}
	
	
	/**
	 * 
	 * 喝酒小游戏
	 * 总共有几次 喝到酒 就停止
	 * @return
	 */
	
	public int getChanceBySlotType(int slotType,int gameType){
		List<BonusBrazilTemplate>  bonusBrazilTemplateList = brazilMap.get(slotType);
		for(BonusBrazilTemplate bb:bonusBrazilTemplateList){
			if(bb.getType() == gameType){
				return bb.getChance();
			}
		}
		/**
		 * 默认两次
		 */
		return 2;
	}
	/**
	 * 
	 * 喝酒小游戏
	 * 总共有几轮游戏
	 * @return
	 */
	
   public int getRoundBySlotType(int slotType){
		
		List<BonusBrazilBarGameTemplate> bonusBrazilBarGameTemplateList= brazilBarGameMap.get(slotType);
		//获取权值 list
		int round = 0;
		for(BonusBrazilBarGameTemplate bb:bonusBrazilBarGameTemplateList){
			if(bb.getStage() != round){
				round++;
			}
		}
		return round;
	}
	
   /**
    * 
    * 桑巴 小游戏
    * 总共有几种 颜色
    * @return
    */
   
   public int[] getColorBySlotType(int slotType){
	   
	   List<BonusBrazilSambaStageTemplate> bonusBrazilSambaStageTemplateList= brazilSambaStageMap.get(slotType);
	   //获取权值 list
	   int color = 0;
	   List<Integer> colors = new ArrayList<Integer>();
	   for(BonusBrazilSambaStageTemplate bb:bonusBrazilSambaStageTemplateList){
		   if(bb.getVelocity() != color){
			   color++;
			   colors.add(color);
		   }
	   }
	   int[] colorArr = new int[colors.size()];
	   for(int i=0;i<colors.size();i++){
		   colorArr[i]=colors.get(i);
	   }
	   return colorArr;
   }
   
	
	
	
	
	
	
	
	
	
	
   
   
   
   
   
   
   
   
	

	public Map<Integer, List<BonusBrazilTemplate>> getBrazilMap() {
		return brazilMap;
	}


	public void setBrazilMap(Map<Integer, List<BonusBrazilTemplate>> brazilMap) {
		this.brazilMap = brazilMap;
	}


	public Map<Integer, List<BonusBrazilBarGameTemplate>> getBrazilBarGameMap() {
		return brazilBarGameMap;
	}


	public void setBrazilBarGameMap(
			Map<Integer, List<BonusBrazilBarGameTemplate>> brazilBarGameMap) {
		this.brazilBarGameMap = brazilBarGameMap;
	}


	public Map<Integer, List<BonusBrazilSambaStageTemplate>> getBrazilSambaStageMap() {
		return brazilSambaStageMap;
	}


	public void setBrazilSambaStageMap(
			Map<Integer, List<BonusBrazilSambaStageTemplate>> brazilSambaStageMap) {
		this.brazilSambaStageMap = brazilSambaStageMap;
	}
	
	
	
	
	
}
