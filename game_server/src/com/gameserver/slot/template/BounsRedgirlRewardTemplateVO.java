package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsRedgirlRewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsRedgirlRewardTemplateVO extends TemplateObject {

	/** 多语言描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 2)
	protected int slotsNum;

	/** 1：单线下注额倍数；2：后退2步 3.前进4步 4.宝箱奖励 */
	@ExcelCellBinding(offset = 3)
	protected int type;

	/** 单线下注额倍数,选取的次数(百分比，实际值除以100） */
	@ExcelCellBinding(offset = 4)
	protected int times;


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
					3, "[老虎机类型]slotsNum的值不得小于0");
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
					4, "[1：单线下注额倍数；2：后退2步 3.前进4步 4.宝箱奖励]type的值不得小于0");
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
	

	/** 模板字典 */
	protected final static Map<Integer, BounsRedgirlRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsRedgirlRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsRedgirlRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsRedgirlRewardTemplateVO [  langDesc=" + langDesc + ", slotsNum=" + slotsNum + ", type=" + type + ", times=" + times + ",]";
	}
}