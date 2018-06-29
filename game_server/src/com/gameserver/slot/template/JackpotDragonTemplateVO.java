package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * JackpotDragonTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class JackpotDragonTemplateVO extends TemplateObject {

	/** 多语言描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** 老虎机ID */
	@ExcelCellBinding(offset = 2)
	protected int slotIdNum;

	/** 中jackpot数量 */
	@ExcelCellBinding(offset = 3)
	protected int jackpotNum;

	/** 奖池初始值（单先下注额倍数，选取的数除以100） */
	@ExcelCellBinding(offset = 4)
	protected int initial;

	/** 每次转动奖池增加数（单线下注额的倍数，取值除以10000） */
	@ExcelCellBinding(offset = 5)
	protected int increase;

	/** 单次中奖金额（当前该奖金池的倍数，取值除以100） */
	@ExcelCellBinding(offset = 6)
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
	
	public int getSlotIdNum() {
		return this.slotIdNum;
	}



	public void setSlotIdNum(int slotIdNum) {
		this.slotIdNum = slotIdNum;
	}
	
	public int getJackpotNum() {
		return this.jackpotNum;
	}



	public void setJackpotNum(int jackpotNum) {
		this.jackpotNum = jackpotNum;
	}
	
	public int getInitial() {
		return this.initial;
	}



	public void setInitial(int initial) {
		this.initial = initial;
	}
	
	public int getIncrease() {
		return this.increase;
	}



	public void setIncrease(int increase) {
		this.increase = increase;
	}
	
	public int getTimes() {
		return this.times;
	}



	public void setTimes(int times) {
		this.times = times;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, JackpotDragonTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends JackpotDragonTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, JackpotDragonTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "JackpotDragonTemplateVO [  langDesc=" + langDesc + ", slotIdNum=" + slotIdNum + ", jackpotNum=" + jackpotNum + ", initial=" + initial + ", increase=" + increase + ", times=" + times + ",]";
	}
}