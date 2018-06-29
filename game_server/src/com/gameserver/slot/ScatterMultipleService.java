package com.gameserver.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.ScatterMultipleTemplate;

public class ScatterMultipleService implements InitializeRequired {
	
	/**scatter个数，对应的列表 **/
	private Map<Integer,Map<Integer,List<ScatterMultipleTemplate>>> mapDate = new HashMap<Integer,Map<Integer,List<ScatterMultipleTemplate>>>();
	
	/**ID 数据 **/
	private Map<Integer,Map<Integer,ScatterMultipleTemplate>> templateMap = new HashMap<Integer,Map<Integer,ScatterMultipleTemplate>>();

	@Override
	public void init() {
		
		 TemplateService templateService = Globals.getTemplateService();
			
		 Map<Integer,ScatterMultipleTemplate> map =  templateService.getAll(ScatterMultipleTemplate.class);
		 
		 for(Entry<Integer,ScatterMultipleTemplate> en : map.entrySet()){
			 
			ScatterMultipleTemplate template = en.getValue();
			//老虎机类型
			int type = template.getSlotsNum();
			
			//Scatter 个数
			int scatterNum = template.getScatterNum();
			
			Map<Integer,List<ScatterMultipleTemplate>> mapType = mapDate.get(type);
			
			if(mapType == null){
				mapType = new HashMap<Integer,List<ScatterMultipleTemplate>>();
				mapDate.put(type, mapType);
			}
			List<ScatterMultipleTemplate> list = mapType.get(scatterNum);
			if(list == null){
				list = new ArrayList<ScatterMultipleTemplate>();
				mapType.put(scatterNum, list);
			}
			
			list.add(template);
			
			Map<Integer,ScatterMultipleTemplate> idsData = templateMap.get(type);
			
			if(idsData == null){
				idsData = new HashMap<Integer,ScatterMultipleTemplate>();
				templateMap.put(type, idsData);
			}
			
			idsData.put(template.getId(), template);
			 
		 }
		 
		 
	}

	public Map<Integer, List<ScatterMultipleTemplate>> getMapDate(int type) {
		return mapDate.get(type);
	}
	
	
	
	public ScatterMultipleTemplate getTemplate(int type,int id){
		return templateMap.get(type).get(id);
	}
	
	
	

}
