package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BounsRedgirlRewardTemplate;
import com.gameserver.slot.template.BounsRedgirlTemplate;
import com.gameserver.slot.template.ScatterRedgirlTemplate;
/**
 * 小红帽
 * @author JavaServer
 *
 */
public class RedgirlService implements InitializeRequired {
	
	
	public static final Integer bet=1;//1：单线下注额倍数；
	public static final Integer back_two=2;//	2：后退2步
	public static final Integer back_four=3;//	3.前进4步
	public static final Integer reward=4;//4.宝箱奖励 这个就不用乘以单线下注额的 倍数
	
	
	private Map<Integer,List<BounsRedgirlTemplate>> bounsRedgirlTemplateMap = new HashMap<Integer,List<BounsRedgirlTemplate>>();
	private Map<Integer,List<BounsRedgirlRewardTemplate>> bounsRedgirlRewardTemplateMap = new HashMap<Integer,List<BounsRedgirlRewardTemplate>>();
	
	
	private Map<Integer,List<ScatterRedgirlTemplate>> scatterRedgirlTemplateMap = new HashMap<Integer,List<ScatterRedgirlTemplate>>();
		
		
		@Override
		public void init() {
			Map<Integer,BounsRedgirlTemplate> rBounsRedgirlTemplateMap = Globals.getTemplateService().getAll(BounsRedgirlTemplate.class);
			int startNum = -1;
			for(BounsRedgirlTemplate br: rBounsRedgirlTemplateMap.values()){
				//单次 bonus 中奖，两次 bonus中奖 ，三次 bonus 中奖
				int slotNum = br.getSlotsNum();
				if(startNum != slotNum){
					startNum = slotNum;
					List<BounsRedgirlTemplate> bounsRedgirlTemplateList = new ArrayList<BounsRedgirlTemplate>();
					bounsRedgirlTemplateList.add(br);
					bounsRedgirlTemplateMap.put(slotNum, bounsRedgirlTemplateList);
				}else{
					List<BounsRedgirlTemplate> bounsRedgirlTemplateList = bounsRedgirlTemplateMap.get(slotNum);
					bounsRedgirlTemplateList.add(br);
				}
			}
			
			
			Map<Integer,BounsRedgirlRewardTemplate> rBounsRedgirlRewardTemplateMap = Globals.getTemplateService().getAll(BounsRedgirlRewardTemplate.class);
			int startNum1 = -1;
			for(BounsRedgirlRewardTemplate br: rBounsRedgirlRewardTemplateMap.values()){
				//单次 bonus 中奖，两次 bonus中奖 ，三次 bonus 中奖
				int slotNum = br.getSlotsNum();
				if(startNum1 != slotNum){
					startNum1 = slotNum;
					List<BounsRedgirlRewardTemplate> bounsRedgirlRewardTemplateList = new ArrayList<BounsRedgirlRewardTemplate>();
					bounsRedgirlRewardTemplateList.add(br);
					bounsRedgirlRewardTemplateMap.put(slotNum, bounsRedgirlRewardTemplateList);
				}else{
					List<BounsRedgirlRewardTemplate> bounsRedgirlRewardTemplateList = bounsRedgirlRewardTemplateMap.get(slotNum);
					bounsRedgirlRewardTemplateList.add(br);
				}
			}
			
			
			Map<Integer,ScatterRedgirlTemplate> rScatterRedgirlTemplateMap = Globals.getTemplateService().getAll(ScatterRedgirlTemplate.class);
			int startNum2 = -1;
			for(ScatterRedgirlTemplate br: rScatterRedgirlTemplateMap.values()){
				//单次 bonus 中奖，两次 bonus中奖 ，三次 bonus 中奖
				int slotNum = br.getSlotsNum();
				if(startNum2 != slotNum){
					startNum2 = slotNum;
					List<ScatterRedgirlTemplate> scatterRedgirlTemplateList = new ArrayList<ScatterRedgirlTemplate>();
					scatterRedgirlTemplateList.add(br);
					scatterRedgirlTemplateMap.put(slotNum, scatterRedgirlTemplateList);
				}else{
					List<ScatterRedgirlTemplate> scatterRedgirlTemplateList = scatterRedgirlTemplateMap.get(slotNum);
					scatterRedgirlTemplateList.add(br);
				}
			}
		}
		
		
		/**
		 * 根据消除次数 取值
		 */
		public int getNumByTimes(int slotNum,int times){
			
			List<ScatterRedgirlTemplate> scatterRedgirlTemplateList = scatterRedgirlTemplateMap.get(slotNum);
			for(ScatterRedgirlTemplate sr:scatterRedgirlTemplateList){
				if(sr.getTimes() == times){
					return sr.getScatter();
				}
			}
			return 0;
		}
		
		
		/**
		 * bonus 元素的个数
		 */

		public int getBonusNumBySlotNum(int slotNum){
			List<BounsRedgirlTemplate>   bounsRedgirlTemplateList = bounsRedgirlTemplateMap.get(slotNum);
			int bonusNum = bounsRedgirlTemplateList.get(0).getBonusNum();
			return bonusNum;
		}
		/**
		 * 小游戏初始选取次数  selectNum
		 */
		
		public int getSelectNumBySlotNum(int slotNum){
			List<BounsRedgirlTemplate>   bounsRedgirlTemplateList = bounsRedgirlTemplateMap.get(slotNum);
			int selectNum = bounsRedgirlTemplateList.get(0).getSelectNum();
			return selectNum;
		}
		
		/**
		 * 摇色子
		 * position :到了第几个格子
		 */
		
		public BounsRedgirlRewardTemplate rollTheDice(int slotNum,int position){
			List<BounsRedgirlRewardTemplate>   bounsRedgirlRewardTemplateList= bounsRedgirlRewardTemplateMap.get(slotNum);
			for(BounsRedgirlRewardTemplate br:bounsRedgirlRewardTemplateList){
				if(br.getId() == position){
					return br;
				}
			}
			return null;
		}

		
}
