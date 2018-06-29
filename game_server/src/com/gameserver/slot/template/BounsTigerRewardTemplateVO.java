package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsTigerRewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsTigerRewardTemplateVO extends TemplateObject {

	/** 老虎机机器编号 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** 1.奖池1 , 2.奖池2 ,3.奖池3 , 4.奖池4 */
	@ExcelCellBinding(offset = 2)
	protected int rewardPool;

	/** 单线下注额倍数,选取的次数(百分比，实际值除以100） */
	@ExcelCellBinding(offset = 3)
	protected int times;

	/** 相同元素的数量 */
	@ExcelCellBinding(offset = 4)
	protected int weight;


	public int getSlotsNum() {
		return this.slotsNum;
	}



	public void setSlotsNum(int slotsNum) {
		this.slotsNum = slotsNum;
	}
	
	public int getRewardPool() {
		return this.rewardPool;
	}



	public void setRewardPool(int rewardPool) {
		this.rewardPool = rewardPool;
	}
	
	public int getTimes() {
		return this.times;
	}



	public void setTimes(int times) {
		this.times = times;
	}
	
	public int getWeight() {
		return this.weight;
	}



	public void setWeight(int weight) {
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsTigerRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsTigerRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsTigerRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsTigerRewardTemplateVO [  slotsNum=" + slotsNum + ", rewardPool=" + rewardPool + ", times=" + times + ", weight=" + weight + ",]";
	}
}