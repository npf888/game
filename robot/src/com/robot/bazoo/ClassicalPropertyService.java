package com.robot.bazoo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.template.TemplateService;
import com.gameserver.bazoo.template.ClassicalPropertyTemplate;

public class ClassicalPropertyService {
	private Logger logger = Loggers.BAZOO;
	Map<Integer,List<ClassicalPropertyTemplate>> classicalPropertyTempalteMap = new HashMap<Integer,List<ClassicalPropertyTemplate>>();

	public void loadProperty(TemplateService templateService){
		Map<Integer,ClassicalPropertyTemplate> classicalPropertyTemplateList = templateService.getAll(ClassicalPropertyTemplate.class);
		classicalPropertyTempalteMap.put(1, new ArrayList<ClassicalPropertyTemplate>());
		classicalPropertyTempalteMap.put(2, new ArrayList<ClassicalPropertyTemplate>());
		classicalPropertyTempalteMap.put(3, new ArrayList<ClassicalPropertyTemplate>());
		for(ClassicalPropertyTemplate template:classicalPropertyTemplateList.values()){
			List<ClassicalPropertyTemplate> list = classicalPropertyTempalteMap.get(template.getCallType());
			list.add(template);
		}
	}
	
	
	/**
	 * 
	 * 根据人数 还有点数的个数 获取概率
	 * @param callType
	 * @param peopleNum
	 * @param pointNum
	 * @return
	 */
	public int getProbability(int callType,int peopleNum,int pointNum){
		
		List<ClassicalPropertyTemplate> list = classicalPropertyTempalteMap.get(callType);
		List<ClassicalPropertyTemplate> peopleNumList = new ArrayList<ClassicalPropertyTemplate>();
		for(ClassicalPropertyTemplate template :list){
			if(template.getPeopleNum() == peopleNum){
			    peopleNumList.add(template);
			}
		}
		//这里是判断 有没有相等的
		for(ClassicalPropertyTemplate template :peopleNumList){
			if(template.getPointNum() == pointNum){
				logger.info("[无双吹牛]---[获取概率 =====]---[callType:"+callType+"-peopleNum:"+peopleNum+"-pointNum:"+pointNum+"]---emplate.getProbability()"+template.getProbability());
				return template.getProbability();
			}
		}
		//当前的 pointNum超出 了最大的 num
		ClassicalPropertyTemplate templateMax = peopleNumList.get(0);
		if(pointNum > templateMax.getPointNum()){
			logger.info("[无双吹牛]---[获取概率  >>>>>]---[callType:"+callType+"-peopleNum:"+peopleNum+"-pointNum:"+pointNum+"]---emplate.getProbability()"+templateMax.getProbability());
			return templateMax.getProbability();
		}
		//当前的 pointNum 小于最小的 num
		ClassicalPropertyTemplate templateMin = peopleNumList.get(peopleNumList.size()-1);
		if(pointNum < templateMin.getPointNum()){
			logger.info("[无双吹牛]---[获取概率  <<<<<]---[callType:"+callType+"-peopleNum:"+peopleNum+"-pointNum:"+pointNum+"]---emplate.getProbability()"+templateMin.getProbability());
			return templateMin.getProbability();
		}
		
		logger.info("[无双吹牛]---[获取概率 end]---[callType:"+callType+"-peopleNum:"+peopleNum+"-pointNum:"+pointNum+"]");
		return 0;
	}
	
	
	
}
