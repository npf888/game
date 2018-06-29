package com.gameserver.human.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * 角色经验
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class RoleExpTemplateVO extends TemplateObject {

	/** 角色经验 */
	@ExcelCellBinding(offset = 1)
	protected long exp;

	/** 角色升级奖励筹码 */
	@ExcelCellBinding(offset = 2)
	protected int rewardChips;


	public long getExp() {
		return this.exp;
	}



	public void setExp(long exp) {
		this.exp = exp;
	}
	
	public int getRewardChips() {
		return this.rewardChips;
	}



	public void setRewardChips(int rewardChips) {
		this.rewardChips = rewardChips;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, RoleExpTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends RoleExpTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, RoleExpTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "RoleExpTemplateVO [  exp=" + exp + ", rewardChips=" + rewardChips + ",]";
	}
}