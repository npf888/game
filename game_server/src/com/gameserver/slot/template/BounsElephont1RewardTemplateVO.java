package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsElephont1RewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsElephont1RewardTemplateVO extends TemplateObject {

	/** 老虎机编号类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** bouns个数 */
	@ExcelCellBinding(offset = 2)
	protected int stage;

	/** 初始选择次数 */
	@ExcelCellBinding(offset = 3)
	protected int type;

	/** 两个相同元素 取 reward的值, 要根据 typeNum的数量来定 */
	@ExcelCellBinding(offset = 4)
	protected int reward;

	/** 初始选择次数 */
	@ExcelCellBinding(offset = 5)
	protected int weight;

	/** 同类型元素数量 */
	@ExcelCellBinding(offset = 6)
	protected int typeNum;


	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[老虎机编号类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getStage() {
		return this.stage;
	}


	public final static int getStageMinLimit() {
		return 0;
	}

	public void setStage(int stage) {
		if (stage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[bouns个数]stage的值不得小于0");
		}
		this.stage = stage;
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
					4, "[初始选择次数]type的值不得小于0");
		}
		this.type = type;
	}
	
	public int getReward() {
		return this.reward;
	}


	public final static int getRewardMinLimit() {
		return 0;
	}

	public void setReward(int reward) {
		if (reward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[两个相同元素 取 reward的值, 要根据 typeNum的数量来定]reward的值不得小于0");
		}
		this.reward = reward;
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
					6, "[初始选择次数]weight的值不得小于0");
		}
		this.weight = weight;
	}
	
	public int getTypeNum() {
		return this.typeNum;
	}


	public final static int getTypeNumMinLimit() {
		return 0;
	}

	public void setTypeNum(int typeNum) {
		if (typeNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[同类型元素数量]typeNum的值不得小于0");
		}
		this.typeNum = typeNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsElephont1RewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsElephont1RewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsElephont1RewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsElephont1RewardTemplateVO [  slotsNum=" + slotsNum + ", stage=" + stage + ", type=" + type + ", reward=" + reward + ", weight=" + weight + ", typeNum=" + typeNum + ",]";
	}
}