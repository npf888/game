package com.gameserver.texas.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * TexasJackpot
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class TexasJackpotTemplateVO extends TemplateObject {

	/** 描述 */
	@ExcelCellBinding(offset = 1)
	protected String langDesc;

	/** 牌类型 */
	@ExcelCellBinding(offset = 2)
	protected int cardType;

	/** 获取彩金池比例 万分比 */
	@ExcelCellBinding(offset = 3)
	protected int jackpotPer;


	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	
	public int getCardType() {
		return this.cardType;
	}


	public final static int getCardTypeMinLimit() {
		return 0;
	}

	public void setCardType(int cardType) {
		if (cardType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[牌类型]cardType的值不得小于0");
		}
		this.cardType = cardType;
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
					4, "[获取彩金池比例 万分比]jackpotPer的值不得小于0");
		}
		this.jackpotPer = jackpotPer;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, TexasJackpotTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends TexasJackpotTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, TexasJackpotTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "TexasJackpotTemplateVO [  langDesc=" + langDesc + ", cardType=" + cardType + ", jackpotPer=" + jackpotPer + ",]";
	}
}