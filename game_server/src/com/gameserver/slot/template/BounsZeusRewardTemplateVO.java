package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsZeusRewardTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsZeusRewardTemplateVO extends TemplateObject {

	/** 多语言描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** 老虎机编号类型 */
	@ExcelCellBinding(offset = 2)
	protected int slotsNum;

	/** 类型 1 单线压住额倍数 , 2 增加免费次数 */
	@ExcelCellBinding(offset = 3)
	protected int type;

	/** 数据都除100 */
	@ExcelCellBinding(offset = 4)
	protected double times;

	/** 权重 */
	@ExcelCellBinding(offset = 5)
	protected int weight;


	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[老虎机编号类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
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
					4, "[类型 1 单线压住额倍数 , 2 增加免费次数]type的值不得小于0");
		}
		this.type = type;
	}
	
	public double getTimes() {
		return this.times;
	}


	public final static double getTimesMinLimit() {
		return 0;
	}

	public void setTimes(double times) {
		if (times < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[数据都除100]times的值不得小于0");
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
					6, "[权重]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsZeusRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsZeusRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsZeusRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsZeusRewardTemplateVO [  langDesc=" + langDesc + ", slotsNum=" + slotsNum + ", type=" + type + ", times=" + times + ", weight=" + weight + ",]";
	}
}