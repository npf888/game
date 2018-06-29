package com.gameserver.baccart.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * baccaratRoom
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BaccaratRoomTemplateVO extends TemplateObject {

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

	/** 最小携带 */
	@ExcelCellBinding(offset = 5)
	protected int minCarry;

	/** 最大携带 */
	@ExcelCellBinding(offset = 6)
	protected int maxCarry;

	/** 最大人数 */
	@ExcelCellBinding(offset = 7)
	protected int maxNum;

	/** 初始库存 */
	@ExcelCellBinding(offset = 8)
	protected int stock;

	/** 开启 */
	@ExcelCellBinding(offset = 9)
	protected int openUp;

	/** 赢钱税 */
	@ExcelCellBinding(offset = 10)
	protected int tax;

	/** 彩金 */
	@ExcelCellBinding(offset = 11)
	protected int jackpot;

	/** 押注1 */
	@ExcelCellBinding(offset = 12)
	protected int bet1;

	/** 押注2 */
	@ExcelCellBinding(offset = 13)
	protected int bet2;

	/** 押注3 */
	@ExcelCellBinding(offset = 14)
	protected int bet3;

	/** 押注4 */
	@ExcelCellBinding(offset = 15)
	protected int bet4;

	/** 押注5 */
	@ExcelCellBinding(offset = 16)
	protected int bet5;

	/** 进入等级 */
	@ExcelCellBinding(offset = 17)
	protected int openLv;

	/** 显示类型 */
	@ExcelCellBinding(offset = 18)
	protected int list;


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
	
	public int getMinCarry() {
		return this.minCarry;
	}


	public final static int getMinCarryMinLimit() {
		return 0;
	}

	public void setMinCarry(int minCarry) {
		if (minCarry < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[最小携带]minCarry的值不得小于0");
		}
		this.minCarry = minCarry;
	}
	
	public int getMaxCarry() {
		return this.maxCarry;
	}


	public final static int getMaxCarryMinLimit() {
		return 0;
	}

	public void setMaxCarry(int maxCarry) {
		if (maxCarry < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[最大携带]maxCarry的值不得小于0");
		}
		this.maxCarry = maxCarry;
	}
	
	public int getMaxNum() {
		return this.maxNum;
	}


	public final static int getMaxNumMinLimit() {
		return 1;
	}

	public void setMaxNum(int maxNum) {
		if (maxNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[最大人数]maxNum的值不得小于1");
		}
		this.maxNum = maxNum;
	}
	
	public int getStock() {
		return this.stock;
	}


	public final static int getStockMinLimit() {
		return 1;
	}

	public void setStock(int stock) {
		if (stock < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[初始库存]stock的值不得小于1");
		}
		this.stock = stock;
	}
	
	public int getOpenUp() {
		return this.openUp;
	}



	public void setOpenUp(int openUp) {
		this.openUp = openUp;
	}
	
	public int getTax() {
		return this.tax;
	}


	public final static int getTaxMinLimit() {
		return 1;
	}

	public void setTax(int tax) {
		if (tax < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[赢钱税]tax的值不得小于1");
		}
		this.tax = tax;
	}
	
	public int getJackpot() {
		return this.jackpot;
	}


	public final static int getJackpotMinLimit() {
		return 1;
	}

	public void setJackpot(int jackpot) {
		if (jackpot < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[彩金]jackpot的值不得小于1");
		}
		this.jackpot = jackpot;
	}
	
	public int getBet1() {
		return this.bet1;
	}



	public void setBet1(int bet1) {
		this.bet1 = bet1;
	}
	
	public int getBet2() {
		return this.bet2;
	}



	public void setBet2(int bet2) {
		this.bet2 = bet2;
	}
	
	public int getBet3() {
		return this.bet3;
	}



	public void setBet3(int bet3) {
		this.bet3 = bet3;
	}
	
	public int getBet4() {
		return this.bet4;
	}



	public void setBet4(int bet4) {
		this.bet4 = bet4;
	}
	
	public int getBet5() {
		return this.bet5;
	}



	public void setBet5(int bet5) {
		this.bet5 = bet5;
	}
	
	public int getOpenLv() {
		return this.openLv;
	}



	public void setOpenLv(int openLv) {
		this.openLv = openLv;
	}
	
	public int getList() {
		return this.list;
	}



	public void setList(int list) {
		this.list = list;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BaccaratRoomTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BaccaratRoomTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BaccaratRoomTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BaccaratRoomTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", minCarry=" + minCarry + ", maxCarry=" + maxCarry + ", maxNum=" + maxNum + ", stock=" + stock + ", openUp=" + openUp + ", tax=" + tax + ", jackpot=" + jackpot + ", bet1=" + bet1 + ", bet2=" + bet2 + ", bet3=" + bet3 + ", bet4=" + bet4 + ", bet5=" + bet5 + ", openLv=" + openLv + ", list=" + list + ",]";
	}
}