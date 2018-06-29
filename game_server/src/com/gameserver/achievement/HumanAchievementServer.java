package com.gameserver.achievement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.gameserver.achievement.enums.AchState;
import com.gameserver.achievement.enums.ParamType;
import com.gameserver.achievement.pojo.CompleteState;
import com.gameserver.achievement.template.AchievementTemplate;
import com.gameserver.common.Globals;

/**
 * 模板数据组合
 * @author 郭君伟
 *
 */
public class HumanAchievementServer implements InitializeRequired, AfterInitializeRequired {

	private static final Logger logger = LoggerFactory.getLogger(HumanAchievementServer.class);
	
	/**key:成就ID value:模板数据 **/
	private Map<Integer, AchievementTemplate> primaryData = new HashMap<Integer, AchievementTemplate>();
	
	/**key:大类型  value: 小类型，具体值**/
	private Map<Integer,Map<Integer,String>> progData = new HashMap<Integer,Map<Integer,String>>();
	
	/**准备的数据 **/
	private Map<Integer,CompleteState> achValueMap = new HashMap<Integer,CompleteState>();
	
	//===================================================
	
	/**key:大类型  value: 小类型，参数类型**/
	private Map<Integer,Map<Integer,Integer>> progDataParam = new HashMap<Integer,Map<Integer,Integer>>();
	
	/**key:大类型  value: 小类型，ids**/
	private Map<Integer,Map<Integer,List<AchievementTemplate>>> progDataIdList = new HashMap<Integer,Map<Integer,List<AchievementTemplate>>>();
	
	
	
	@Override
	public void init() {
		
		primaryData = Globals.getTemplateService().getAll(AchievementTemplate.class);
		
		for(AchievementTemplate template : primaryData.values()){
			
			int id = template.getId();
			
			int serverType =  template.getServerType();
			
			int smallType = template.getSmallType();
			
			int paramType = template.getParamType();
			
			Map<Integer,String> map = progData.get(serverType);
			
			if(map == null){
				map = new HashMap<Integer,String>();
				progData.put(serverType, map);
			}
			
			if(paramType == ParamType.ParamType1.getIndex()){
				map.put(smallType, "");
			}else if(paramType == ParamType.ParamType2.getIndex()){
				String strValue = map.get(smallType);
				int condition2 = template.getCondition2();
				if(strValue == null){
					Map<Integer,Integer> mapv = new HashMap<Integer,Integer>();
					mapv.put(condition2, 0);
					map.put(smallType,JSON.toJSONString(mapv));
				}else{
					@SuppressWarnings("unchecked")
					Map<Integer,Integer> mapv = JSON.parseObject(strValue, HashMap.class);
					mapv.put(condition2, 0);
					map.put(smallType, JSON.toJSONString(mapv));
				}
			}
			//======================================================
			CompleteState state = new CompleteState();
			state.setId(id);
			state.setState(AchState.AchState1.getIndex());
			state.setCompleteTime(0);
			achValueMap.put(id, state);
			//===================================================
			Map<Integer,Integer> map1 = progDataParam.get(serverType);
			if(map1 == null){
				map1 = new HashMap<Integer,Integer>();
				progDataParam.put(serverType, map1);
			}
			map1.put(smallType, template.getParamType());
			//========================================================
			Map<Integer,List<AchievementTemplate>> mapList = progDataIdList.get(serverType);
			if(mapList == null){
				mapList = new HashMap<Integer,List<AchievementTemplate>>();
				progDataIdList.put(serverType, mapList);
			}
			List<AchievementTemplate> list = mapList.get(smallType);
			if(list == null){
				list = new ArrayList<AchievementTemplate>();
				mapList.put(smallType, list);
			}
			list.add(template);
		}
	}
	
	@Override
	public void afterInit() {}

	
	public Map<Integer, AchievementTemplate> getPrimaryData() {
		Map<Integer, AchievementTemplate> map = new HashMap<Integer, AchievementTemplate>();
		map.putAll(primaryData);
		return map;
	}

	public Map<Integer, Map<Integer, String>> getProgData() {
		 Map<Integer, Map<Integer, String>> map = new  HashMap<Integer, Map<Integer, String>>();
		 for(Entry<Integer,Map<Integer,String>> en : progData.entrySet()){
			 int key = en.getKey();
			 Map<Integer,String> mapValue =  en.getValue();
			 Map<Integer,String> mapValueCopy =  new HashMap<Integer,String>();
			 mapValueCopy.putAll(mapValue);
			 map.put(key, mapValueCopy);
		 }
		
		return map;
	}

	public Map<Integer, CompleteState> getAchValueMap() {
		Map<Integer, CompleteState> map = new HashMap<Integer, CompleteState>();
		for(Entry<Integer, CompleteState> en : achValueMap.entrySet()){
			CompleteState old = en.getValue();
			CompleteState cs = new CompleteState();
			cs.setId(old.getId());
			cs.setState(old.getState());
			cs.setCompleteTime(old.getCompleteTime());
			map.put(en.getKey(), cs);
		}
		
		return map;
	}
	
	/**
	 * 获取参数类型
	 * @param serverType
	 * @param smallType
	 * @return
	 */
	public int getParamDate(int serverType,int smallType){
		try{
			return progDataParam.get(serverType).get(smallType);
		}catch(Exception e){
			logger.error("", e);
		}
		return 0;
	}
	
	/**
	 * 获取对应的ID
	 * @param serverType
	 * @param smallType
	 * @return
	 */
	public List<AchievementTemplate> getIDS(int serverType,int smallType){
		return progDataIdList.get(serverType).get(smallType);
	}

	/**
	 * 获取奖励
	 * @param id
	 * @return
	 */
    public int getReceive(int id){
    	if(primaryData.containsKey(id)){
    		return primaryData.get(id).getReward1Num();
    	}
    	return 0;
    }
}
