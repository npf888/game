package com.gameserver.slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.template.RiyuetanMultipleTemplate;
/**
 * 老虎机日月潭数据
 * @author 郭君伟
 *
 */
public class RiyuetanMultipleService implements AfterInitializeRequired, InitializeRequired{

	private List<Integer> listTemplate = new ArrayList<Integer>(10);
	
	private int[] weightList = new int[10];
	
	@Override
	public void init() {
		 TemplateService templateService = Globals.getTemplateService();
		
		 Map<Integer,RiyuetanMultipleTemplate> map =  templateService.getAll(RiyuetanMultipleTemplate.class);
		 
		 for(RiyuetanMultipleTemplate tem : map.values()){
			 
			  int id = tem.getId()-1;
			  
			  int times = tem.getTimes()/100;
			  
			  int weight = tem.getWeight();
			  
			  listTemplate.add(id, times);
			  
			  weightList[id] = weight;
			  
		 }
		/* for(RiyuetanMultipleTemplate tem : map.values()){
			 int id = tem.getId()-1+5;
			 
			 int times = tem.getTimes()/100;
			 
			 int weight = tem.getWeight();
			 
			 listTemplate.add(id, times);
			 
			 weightList[id] = weight;
			 
		 }
		*/
		 
	}

	@Override
	public void afterInit() {}
	
	/**
	 * 获取倍数
	 * @return
	 */
	public List<Integer> getRandPosition(){
		
		List<Integer>  post = ArrayUtils.randomFromArray(listTemplate, weightList, 3, false);
	
		return post;
	}

}
