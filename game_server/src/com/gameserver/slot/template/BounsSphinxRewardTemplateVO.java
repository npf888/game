package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsSphinxRewardTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsSphinxRewardTemplateVO extends TemplateObject {

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** 1.奖池1 , 2.奖池2  , 。。。 , 5.奖池5 */
	@ExcelCellBinding(offset = 2)
	protected int rewardPool;

	/** 1：单线下注额倍数；   ,  2：没中奖   , 3：所有中奖和 */
	@ExcelCellBinding(offset = 3)
	protected int type;

	/** 单线下注额倍数,选取的次数(百分比，实际值除以100） */
	@ExcelCellBinding(offset = 4)
	protected int times;

	/** 权值 */
	@ExcelCellBinding(offset = 5)
	protected int weight;


	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getRewardPool() {
		return this.rewardPool;
	}


	public final static int getRewardPoolMinLimit() {
		return 0;
	}

	public void setRewardPool(int rewardPool) {
		if (rewardPool < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[1.奖池1 , 2.奖池2  , 。。。 , 5.奖池5]rewardPool的值不得小于0");
		}
		this.rewardPool = rewardPool;
	}
	
	public int getType() {
		return this.type;
	}


	public final static int getTypeMinLimit() {
		return 0;
	}

	public void setType(int type) {
		if (type < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[1：单线下注额倍数；   ,  2：没中奖   , 3：所有中奖和]type的值不得小于0");
		}
		this.type = type;
	}
	
	public int getTimes() {
		return this.times;
	}


	public final static int getTimesMinLimit() {
		return 0;
	}

	public void setTimes(int times) {
		if (times < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[单线下注额倍数,选取的次数(百分比，实际值除以100）]times的值不得小于0");
		}
		this.times = times;
	}
	
	public int getWeight() {
		return this.weight;
	}


	public final static int getWeightMinLimit() {
		return 0;
	}

	public void setWeight(int weight) {
		if (weight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[权值]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsSphinxRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsSphinxRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsSphinxRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsSphinxRewardTemplateVO [  slotsNum=" + slotsNum + ", rewardPool=" + rewardPool + ", type=" + type + ", times=" + times + ", weight=" + weight + ",]";
	}
}