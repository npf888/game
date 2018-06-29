package com.gameserver.newbie.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * NoviceSlotsTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class NoviceSlotsTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/**  */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 老虎机编号 */
	@ExcelCellBinding(offset = 4)
	protected int slotNum;

	/**  */
	@ExcelCellBinding(offset = 5)
	protected int step;

	/** 机器线数 */
	@ExcelCellBinding(offset = 6)
	protected int payLinesNum;

	/**  */
	@ExcelCellBinding(offset = 7)
	protected int bet1;

	/**  */
	@ExcelCellBinding(offset = 8)
	protected int bet2;

	/**  */
	@ExcelCellBinding(offset = 9)
	protected int bet3;

	/**  */
	@ExcelCellBinding(offset = 10)
	protected int bet4;

	/**  */
	@ExcelCellBinding(offset = 11)
	protected int bet5;

	/** 下注额度 */
	@ExcelCellBinding(offset = 12)
	protected int bet;

	/** 线数倍数 */
	@ExcelCellBinding(offset = 13)
	protected int WinNum;

	/**  */
	@ExcelCellBinding(offset = 14)
	protected int openLv;

	/** 线类型 */
	@ExcelCellBinding(offset = 15)
	protected int lineType;

	/** 转动后卷轴位置 */
	@ExcelCellBinding(offset = 16)
	protected int firstReel1;

	/** 转动后卷轴位置 */
	@ExcelCellBinding(offset = 17)
	protected int firstReel2;

	/** 转动后卷轴位置 */
	@ExcelCellBinding(offset = 18)
	protected int firstReel3;

	/** 转动后卷轴位置 */
	@ExcelCellBinding(offset = 19)
	protected int firstReel4;

	/** 转动后卷轴位置 */
	@ExcelCellBinding(offset = 20)
	protected int firstReel5;


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
		this.langDesc = langDesc;
	}
	
	public int getSlotNum() {
		return this.slotNum;
	}



	public void setSlotNum(int slotNum) {
		this.slotNum = slotNum;
	}
	
	public int getStep() {
		return this.step;
	}



	public void setStep(int step) {
		this.step = step;
	}
	
	public int getPayLinesNum() {
		return this.payLinesNum;
	}



	public void setPayLinesNum(int payLinesNum) {
		this.payLinesNum = payLinesNum;
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
	
	public int getBet() {
		return this.bet;
	}



	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public int getWinNum() {
		return this.WinNum;
	}



	public void setWinNum(int WinNum) {
		this.WinNum = WinNum;
	}
	
	public int getOpenLv() {
		return this.openLv;
	}



	public void setOpenLv(int openLv) {
		this.openLv = openLv;
	}
	
	public int getLineType() {
		return this.lineType;
	}



	public void setLineType(int lineType) {
		this.lineType = lineType;
	}
	
	public int getFirstReel1() {
		return this.firstReel1;
	}



	public void setFirstReel1(int firstReel1) {
		this.firstReel1 = firstReel1;
	}
	
	public int getFirstReel2() {
		return this.firstReel2;
	}



	public void setFirstReel2(int firstReel2) {
		this.firstReel2 = firstReel2;
	}
	
	public int getFirstReel3() {
		return this.firstReel3;
	}



	public void setFirstReel3(int firstReel3) {
		this.firstReel3 = firstReel3;
	}
	
	public int getFirstReel4() {
		return this.firstReel4;
	}



	public void setFirstReel4(int firstReel4) {
		this.firstReel4 = firstReel4;
	}
	
	public int getFirstReel5() {
		return this.firstReel5;
	}



	public void setFirstReel5(int firstReel5) {
		this.firstReel5 = firstReel5;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, NoviceSlotsTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends NoviceSlotsTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, NoviceSlotsTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "NoviceSlotsTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", slotNum=" + slotNum + ", step=" + step + ", payLinesNum=" + payLinesNum + ", bet1=" + bet1 + ", bet2=" + bet2 + ", bet3=" + bet3 + ", bet4=" + bet4 + ", bet5=" + bet5 + ", bet=" + bet + ", WinNum=" + WinNum + ", openLv=" + openLv + ", lineType=" + lineType + ", firstReel1=" + firstReel1 + ", firstReel2=" + firstReel2 + ", firstReel3=" + firstReel3 + ", firstReel4=" + firstReel4 + ", firstReel5=" + firstReel5 + ",]";
	}
}