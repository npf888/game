package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusBrazilSambaStageTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusBrazilSambaStageTemplateVO extends TemplateObject {

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 1)
	protected int slotsNum;

	/** 作者: 力度条颜色 （前端上传）1：黄色 2：黄绿色 3：绿色 */
	@ExcelCellBinding(offset = 2)
	protected int velocity;

	/** 1.奖励类型 */
	@ExcelCellBinding(offset = 3)
	protected int type;

	/** 单线下注额倍数,(百分比，实际值除以100） */
	@ExcelCellBinding(offset = 4)
	protected int reward;

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
	
	public int getVelocity() {
		return this.velocity;
	}


	public final static int getVelocityMinLimit() {
		return 0;
	}

	public void setVelocity(int velocity) {
		if (velocity < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[作者: 力度条颜色 （前端上传）1：黄色 2：黄绿色 3：绿色]velocity的值不得小于0");
		}
		this.velocity = velocity;
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
					4, "[1.奖励类型]type的值不得小于0");
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
					5, "[单线下注额倍数,(百分比，实际值除以100）]reward的值不得小于0");
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
					6, "[权值]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusBrazilSambaStageTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusBrazilSambaStageTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusBrazilSambaStageTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusBrazilSambaStageTemplateVO [  slotsNum=" + slotsNum + ", velocity=" + velocity + ", type=" + type + ", reward=" + reward + ", weight=" + weight + ",]";
	}
}