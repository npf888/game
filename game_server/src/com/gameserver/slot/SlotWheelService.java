package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.BigWheelTemplate;
import com.gameserver.slot.template.BounsGameTemplate;
import com.gameserver.slot.template.GoldBonusTemplate;
import com.gameserver.slot.template.ScatterWheel;

/**
 * 老虎机大转盘玩法静态数据
 * @author 郭君伟
 *
 */
public class SlotWheelService implements AfterInitializeRequired, InitializeRequired {
	
	
    /**key  老虎机类型   value: 图标个数 **/
	private Map<Integer,Integer> slotTyNum = new HashMap<Integer,Integer>();
	
	/** (1 glod 2 free )  (2 2转盘  3 3转盘)  **/
	private Map<Integer,Map<Integer,List<GoldBonusTemplate>>> goldBonusMap = new HashMap<Integer,Map<Integer,List<GoldBonusTemplate>>>();
	
	/**大转盘 **/
	private List<BigWheelTemplate> bigWheelList = new ArrayList<BigWheelTemplate>();
	
	
	//==================================================================
	
	/** **/
	private List<BounsGameTemplate> bounsList = new ArrayList<BounsGameTemplate>();
	
	
	
	@Override
	public void init() {
		
	}

	@Override
	public void afterInit() {
		TemplateService templateService = Globals.getTemplateService();
		
		Map<Integer,BigWheelTemplate> bigWheelTemplateMap = templateService.getAll(BigWheelTemplate.class);
		for(BigWheelTemplate bt : bigWheelTemplateMap.values()){
			bigWheelList.add(bt);
		}
		Map<Integer,BounsGameTemplate> bounsGameTemplateMap = templateService.getAll(BounsGameTemplate.class);
		for(BounsGameTemplate bt : bounsGameTemplateMap.values()){
			bounsList.add(bt);
		}
		
		Map<Integer,ScatterWheel> scatterWheelMap = templateService.getAll(ScatterWheel.class);
		
		for(ScatterWheel sw : scatterWheelMap.values()){
			slotTyNum.put(sw.getSlotType(), sw.getScatterNum());
		}
		
		
		Map<Integer,GoldBonusTemplate> goldBonusTemplateMap = templateService.getAll(GoldBonusTemplate.class);
		
		for(GoldBonusTemplate gt : goldBonusTemplateMap.values()){
			 int type = gt.getType();
			 int wheelType = gt.getWheelType();
			 
			 Map<Integer,List<GoldBonusTemplate>> map1 =  goldBonusMap.get(type);
			 if(map1 == null){
				 map1 = new HashMap<Integer,List<GoldBonusTemplate>>();
				 goldBonusMap.put(type, map1);
			 }
			 
			 List<GoldBonusTemplate> list =  map1.get(wheelType);
			 
			 if(list == null){
				 list = new ArrayList<GoldBonusTemplate>();
				 map1.put(wheelType, list);
			 }
			 list.add(gt);
		}
		
		
		
	}
	
	
	/**
	 * 根据老虎机类型获得所需图标数量
	 * @param slotType
	 * @return
	 */
	public int getSlotIconNum(int slotType){
	     if(slotTyNum.containsKey(slotType)){
	    	 return slotTyNum.get(slotType);
	     }
	     return 0;
	}
	
	/**
	 * 
	 * @param type 类型  1 glod 2 free
	 * @param wheelType 2 盘子 3 3盘zi
	 * @return  随机的位置  奖励类型 奖励数据
	 */
	public GoldBonusTemplate getTwoPlates(int type,int wheelType){
		
		if(goldBonusMap.containsKey(type)){
			Map<Integer,List<GoldBonusTemplate>> map = goldBonusMap.get(type);
			if(map.containsKey(wheelType)){
				List<GoldBonusTemplate> listG = map.get(wheelType);
				
				int index = RandomUtil.nextInt(0, listG.size());
				
				GoldBonusTemplate gb = listG.get(index);
				return gb;
			}
		}
		
		return null;
	}
	
	/**
	 * 随机大转盘
	 * @return 1 位置 2 数据
	 */
	public BigWheelTemplate getBigwheel(){
		int[] num = new int[bigWheelList.size()];
		for(int i = 0;i < bigWheelList.size();i++){
			num[i] = bigWheelList.get(i).getWeight();
		}
		List<BigWheelTemplate> listb = ArrayUtils.randomFromArray(bigWheelList, num, 1, false);
		BigWheelTemplate bg = listb.get(0);
	
		return bg;
	}
	
	/**
	 * 随机小游戏
	 * @return
	 */
	public BounsGameTemplate getBouns(){
		int[] num = new int[bounsList.size()];
		for(int i = 0;i < bounsList.size();i++){
			num[i] = bounsList.get(i).getWeight();
		}
		List<BounsGameTemplate> listb = ArrayUtils.randomFromArray(bounsList, num, 1, false);
		return listb.get(0);
	}
	

}
