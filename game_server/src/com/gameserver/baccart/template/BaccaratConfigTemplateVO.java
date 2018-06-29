package com.gameserver.baccart.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BaccaratConfig
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BaccaratConfigTemplateVO extends TemplateObject {

	/** 洗牌时间 */
	@ExcelCellBinding(offset = 1)
	protected int shuffleTime;

	/** 押注时间 */
	@ExcelCellBinding(offset = 2)
	protected int betTime;

	/** 发牌时间 */
	@ExcelCellBinding(offset = 3)
	protected int dealTime;

	/** 补牌时间 */
	@ExcelCellBinding(offset = 4)
	protected int fillTime;

	/** 展示时间 */
	@ExcelCellBinding(offset = 5)
	protected int showTime;

	/** 彩金比例 */
	@ExcelCellBinding(offset = 6)
	protected int jackpotRatio;

	/** 彩池时间 */
	@ExcelCellBinding(offset = 7)
	protected int jackpotTime;


	public int getShuffleTime() {
		return this.shuffleTime;
	}


	public final static int getShuffleTimeMinLimit() {
		return 0;
	}

	public void setShuffleTime(int shuffleTime) {
		if (shuffleTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[洗牌时间]shuffleTime的值不得小于0");
		}
		this.shuffleTime = shuffleTime;
	}
	
	public int getBetTime() {
		return this.betTime;
	}


	public final static int getBetTimeMinLimit() {
		return 0;
	}

	public void setBetTime(int betTime) {
		if (betTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[押注时间]betTime的值不得小于0");
		}
		this.betTime = betTime;
	}
	
	public int getDealTime() {
		return this.dealTime;
	}


	public final static int getDealTimeMinLimit() {
		return 0;
	}

	public void setDealTime(int dealTime) {
		if (dealTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[发牌时间]dealTime的值不得小于0");
		}
		this.dealTime = dealTime;
	}
	
	public int getFillTime() {
		return this.fillTime;
	}


	public final static int getFillTimeMinLimit() {
		return 0;
	}

	public void setFillTime(int fillTime) {
		if (fillTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[补牌时间]fillTime的值不得小于0");
		}
		this.fillTime = fillTime;
	}
	
	public int getShowTime() {
		return this.showTime;
	}


	public final static int getShowTimeMinLimit() {
		return 0;
	}

	public void setShowTime(int showTime) {
		if (showTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[展示时间]showTime的值不得小于0");
		}
		this.showTime = showTime;
	}
	
	public int getJackpotRatio() {
		return this.jackpotRatio;
	}


	public final static int getJackpotRatioMinLimit() {
		return 0;
	}

	public void setJackpotRatio(int jackpotRatio) {
		if (jackpotRatio < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[彩金比例]jackpotRatio的值不得小于0");
		}
		this.jackpotRatio = jackpotRatio;
	}
	
	public int getJackpotTime() {
		return this.jackpotTime;
	}


	public final static int getJackpotTimeMinLimit() {
		return 0;
	}

	public void setJackpotTime(int jackpotTime) {
		if (jackpotTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[彩池时间]jackpotTime的值不得小于0");
		}
		this.jackpotTime = jackpotTime;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BaccaratConfigTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BaccaratConfigTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BaccaratConfigTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BaccaratConfigTemplateVO [  shuffleTime=" + shuffleTime + ", betTime=" + betTime + ", dealTime=" + dealTime + ", fillTime=" + fillTime + ", showTime=" + showTime + ", jackpotRatio=" + jackpotRatio + ", jackpotTime=" + jackpotTime + ",]";
	}
}