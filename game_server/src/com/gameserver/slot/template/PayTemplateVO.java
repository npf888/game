package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * pay
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class PayTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 老虎机号 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** 5个 */
	@ExcelCellBinding(offset = 6)
	protected int combination1;

	/** 4个 */
	@ExcelCellBinding(offset = 7)
	protected int combination2;

	/** 3个 */
	@ExcelCellBinding(offset = 8)
	protected int combination3;

	/** 2个 */
	@ExcelCellBinding(offset = 9)
	protected int combination4;

	/** 1个 */
	@ExcelCellBinding(offset = 10)
	protected int combination5;

	/** 赔率 */
	@ExcelCellBinding(offset = 11)
	protected int pay;

	/** 中jackpot类型 1.中5个 2.中4个 3.中3个 4.不中 */
	@ExcelCellBinding(offset = 12)
	protected int jackpotid;


	public int getNameId() {
		return this.nameId;
	}



	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	
	public int getDescrip() {
		return this.descrip;
	}



	public void setDescrip(int descrip) {
		this.descrip = descrip;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
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
					6, "[老虎机号]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getCombination1() {
		return this.combination1;
	}


	public final static int getCombination1MinLimit() {
		return 0;
	}

	public void setCombination1(int combination1) {
		if (combination1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[5个]combination1的值不得小于0");
		}
		this.combination1 = combination1;
	}
	
	public int getCombination2() {
		return this.combination2;
	}


	public final static int getCombination2MinLimit() {
		return 0;
	}

	public void setCombination2(int combination2) {
		if (combination2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[4个]combination2的值不得小于0");
		}
		this.combination2 = combination2;
	}
	
	public int getCombination3() {
		return this.combination3;
	}


	public final static int getCombination3MinLimit() {
		return 0;
	}

	public void setCombination3(int combination3) {
		if (combination3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[3个]combination3的值不得小于0");
		}
		this.combination3 = combination3;
	}
	
	public int getCombination4() {
		return this.combination4;
	}


	public final static int getCombination4MinLimit() {
		return 0;
	}

	public void setCombination4(int combination4) {
		if (combination4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[2个]combination4的值不得小于0");
		}
		this.combination4 = combination4;
	}
	
	public int getCombination5() {
		return this.combination5;
	}


	public final static int getCombination5MinLimit() {
		return 0;
	}

	public void setCombination5(int combination5) {
		if (combination5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[1个]combination5的值不得小于0");
		}
		this.combination5 = combination5;
	}
	
	public int getPay() {
		return this.pay;
	}


	public final static int getPayMinLimit() {
		return 0;
	}

	public void setPay(int pay) {
		if (pay < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[赔率]pay的值不得小于0");
		}
		this.pay = pay;
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
					13, "[中jackpot类型 1.中5个 2.中4个 3.中3个 4.不中]jackpotid的值不得小于0");
		}
		this.jackpotid = jackpotid;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, PayTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends PayTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, PayTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "PayTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", combination1=" + combination1 + ", combination2=" + combination2 + ", combination3=" + combination3 + ", combination4=" + combination4 + ", combination5=" + combination5 + ", pay=" + pay + ", jackpotid=" + jackpotid + ",]";
	}
}