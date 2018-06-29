package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.ScatterCowboyTemplate;
import com.gameserver.slot.template.ScatterGoldTemplate;

/**
 * 西部牛仔配置数据
 * @author 郭君伟
 *
 */
public class ScatterCowboyService implements InitializeRequired{
	
	/**key:老虎机类型 value:（scatter个数 ，模板数据）**/
	private Map<Integer,Map<Integer,ScatterCowboyTemplate>> cowBayMap = new HashMap<Integer,Map<Integer,ScatterCowboyTemplate>>();
	
	
	private Map<Integer,List<Integer>> goldDataMap = new HashMap<Integer,List<Integer>>();
	private Map<Integer,List<Integer>> goldDataWeightMap = new HashMap<Integer,List<Integer>>();

	@Override
	public void init() {
		 TemplateService templateService = Globals.getTemplateService();
			
		 Map<Integer,ScatterCowboyTemplate> map1 =  templateService.getAll(ScatterCowboyTemplate.class);
		 for(ScatterCowboyTemplate temp : map1.values()){
			 int slotType = temp.getSlotsNum();
			 int scatterNum = temp.getScatterNum();
			 Map<Integer,ScatterCowboyTemplate> map =  cowBayMap.get(slotType);
			 if(map == null){
				 map = new HashMap<Integer,ScatterCowboyTemplate>();
				 cowBayMap.put(slotType, map);
			 }
			 map.put(scatterNum, temp);
			 
		 }
		 Map<Integer,ScatterGoldTemplate> map2 =  templateService.getAll(ScatterGoldTemplate.class);
		 for(ScatterGoldTemplate temp : map2.values()){
			 
			 int slotType = temp.getSlotsNum();
			 int times = temp.getTimes();
			 int weight = temp.getWeight();
			 assembly(goldDataMap,slotType,times);
			 assembly(goldDataWeightMap,slotType,weight);
			 
		 }
	}
	
	private void assembly(Map<Integer,List<Integer>> map,int slotType,int value){
		 List<Integer> list1 =  map.get(slotType);
		 if(list1 == null){
			 list1 = new ArrayList<Integer>();
			 map.put(slotType, list1);
		 }
		 list1.add(value);
	}
	
	/**
	 * 根据scatter个数获取模板数据
	 * @param slotType
	 * @param scatterNum
	 * @return
	 */
	public ScatterCowboyTemplate getSCT(int slotType,int scatterNum){
		if(cowBayMap.containsKey(slotType)){
			Map<Integer,ScatterCowboyTemplate> map = cowBayMap.get(slotType);
			
			int n = 0;
			for(int v : map.keySet()){
				if(v <= scatterNum && n < v){
					n = v;
				}
			}
			if(n != 0){
			return map.get(n);
			}
		}
		return null;
	}
	
	/**
	 * 根据权重返回 
	 * @param slotType
	 * @return
	 */
	public int getTimes(int slotType){
		if(goldDataMap.containsKey(slotType) && goldDataWeightMap.containsKey(slotType)){
			List<Integer> list1 = goldDataMap.get(slotType);
			List<Integer> list2 = goldDataWeightMap.get(slotType);
			
			List<Integer>  post = ArrayUtils.randomFromArray(list1, ArrayUtils.intList2Array(list2), 1, false);
			return post.get(0);
		}
		return 1;
	}
	

}
