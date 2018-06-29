package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * SlotJackpotNew
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class SlotJackpotNewTemplateVO extends TemplateObject {

	/** 描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** SlotsListTemplateVO id列 */
	@ExcelCellBinding(offset = 2)
	protected int slotNum;

	/** PayTemplateVO */
	@ExcelCellBinding(offset = 3)
	protected int jackpotid;

	/** jackpot中奖获得奖金比（万分比） */
	@ExcelCellBinding(offset = 4)
	protected int jackpotreward;


	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	
	public int getSlotNum() {
		return this.slotNum;
	}


	public final static int getSlotNumMinLimit() {
		return 0;
	}

	public void setSlotNum(int slotNum) {
		if (slotNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[SlotsListTemplateVO id列]slotNum的值不得小于0");
		}
		this.slotNum = slotNum;
	}
	
	public int getJackpotid() {
		return this.jackpotid;
	}


	public final static int getJackpotidMinLimit() {
		return 0;
	}

	public void setJackpotid(int jackpotid) {
		if (jackpotid < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[PayTemplateVO]jackpotid的值不得小于0");
		}
		this.jackpotid = jackpotid;
	}
	
	public int getJackpotreward() {
		return this.jackpotreward;
	}


	public final static int getJackpotrewardMinLimit() {
		return 0;
	}

	public void setJackpotreward(int jackpotreward) {
		if (jackpotreward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[jackpot中奖获得奖金比（万分比）]jackpotreward的值不得小于0");
		}
		this.jackpotreward = jackpotreward;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, SlotJackpotNewTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends SlotJackpotNewTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, SlotJackpotNewTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "SlotJackpotNewTemplateVO [  langDesc=" + langDesc + ", slotNum=" + slotNum + ", jackpotid=" + jackpotid + ", jackpotreward=" + jackpotreward + ",]";
	}
}