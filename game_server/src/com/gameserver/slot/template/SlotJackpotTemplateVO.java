package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * SlotJackpot
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class SlotJackpotTemplateVO extends TemplateObject {

	/** 描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** 老虎机ID */
	@ExcelCellBinding(offset = 2)
	protected int slotId;

	/** 转轴图案组合ID */
	@ExcelCellBinding(offset = 3)
	protected int payId;

	/** 老虎机下注额ID */
	@ExcelCellBinding(offset = 4)
	protected int bet;

	/** 彩金池获奖比例 万分比 */
	@ExcelCellBinding(offset = 5)
	protected int rewardPer;

	/** 5个jackPort彩金 */
	@ExcelCellBinding(offset = 6)
	protected int jackpotPer;


	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	
	public int getSlotId() {
		return this.slotId;
	}


	public final static int getSlotIdMinLimit() {
		return 0;
	}

	public void setSlotId(int slotId) {
		if (slotId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[老虎机ID]slotId的值不得小于0");
		}
		this.slotId = slotId;
	}
	
	public int getPayId() {
		return this.payId;
	}


	public final static int getPayIdMinLimit() {
		return 0;
	}

	public void setPayId(int payId) {
		if (payId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[转轴图案组合ID]payId的值不得小于0");
		}
		this.payId = payId;
	}
	
	public int getBet() {
		return this.bet;
	}


	public final static int getBetMinLimit() {
		return 0;
	}

	public void setBet(int bet) {
		if (bet < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[老虎机下注额ID]bet的值不得小于0");
		}
		this.bet = bet;
	}
	
	public int getRewardPer() {
		return this.rewardPer;
	}


	public final static int getRewardPerMinLimit() {
		return 0;
	}

	public void setRewardPer(int rewardPer) {
		if (rewardPer < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[彩金池获奖比例 万分比]rewardPer的值不得小于0");
		}
		this.rewardPer = rewardPer;
	}
	
	public int getJackpotPer() {
		return this.jackpotPer;
	}


	public final static int getJackpotPerMinLimit() {
		return 0;
	}

	public void setJackpotPer(int jackpotPer) {
		if (jackpotPer < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[5个jackPort彩金]jackpotPer的值不得小于0");
		}
		this.jackpotPer = jackpotPer;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, SlotJackpotTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends SlotJackpotTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, SlotJackpotTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "SlotJackpotTemplateVO [  langDesc=" + langDesc + ", slotId=" + slotId + ", payId=" + payId + ", bet=" + bet + ", rewardPer=" + rewardPer + ", jackpotPer=" + jackpotPer + ",]";
	}
}