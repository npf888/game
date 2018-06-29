package com.gameserver.treasury.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * TreasuryTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class TreasuryTemplateVO extends TemplateObject {

	/** 描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** 作者:购买次数   超过5次视为5次 */
	@ExcelCellBinding(offset = 2)
	protected int typeTreasury;

	/** 初始金币 */
	@ExcelCellBinding(offset = 3)
	protected int originalChipTreasury;

	/** 金币存储系数（万分比） */
	@ExcelCellBinding(offset = 4)
	protected int factorTreasury;

	/** 作者:存储上限 */
	@ExcelCellBinding(offset = 5)
	protected int maxTreasury;

	/** 作者:VIP点数奖励 */
	@ExcelCellBinding(offset = 6)
	protected int vipPointTreasury;

	/** pid对应recharge */
	@ExcelCellBinding(offset = 7)
	protected int pid;


	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	
	public int getTypeTreasury() {
		return this.typeTreasury;
	}



	public void setTypeTreasury(int typeTreasury) {
		this.typeTreasury = typeTreasury;
	}
	
	public int getOriginalChipTreasury() {
		return this.originalChipTreasury;
	}



	public void setOriginalChipTreasury(int originalChipTreasury) {
		this.originalChipTreasury = originalChipTreasury;
	}
	
	public int getFactorTreasury() {
		return this.factorTreasury;
	}



	public void setFactorTreasury(int factorTreasury) {
		this.factorTreasury = factorTreasury;
	}
	
	public int getMaxTreasury() {
		return this.maxTreasury;
	}



	public void setMaxTreasury(int maxTreasury) {
		this.maxTreasury = maxTreasury;
	}
	
	public int getVipPointTreasury() {
		return this.vipPointTreasury;
	}



	public void setVipPointTreasury(int vipPointTreasury) {
		this.vipPointTreasury = vipPointTreasury;
	}
	
	public int getPid() {
		return this.pid;
	}



	public void setPid(int pid) {
		this.pid = pid;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, TreasuryTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends TreasuryTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, TreasuryTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "TreasuryTemplateVO [  langDesc=" + langDesc + ", typeTreasury=" + typeTreasury + ", originalChipTreasury=" + originalChipTreasury + ", factorTreasury=" + factorTreasury + ", maxTreasury=" + maxTreasury + ", vipPointTreasury=" + vipPointTreasury + ", pid=" + pid + ",]";
	}
}