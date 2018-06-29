package com.gameserver.baccart.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * Jackpot
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class JackpotTemplateVO extends TemplateObject {

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

	/** 下限 */
	@ExcelCellBinding(offset = 5)
	protected int downLimit;

	/** 上限 */
	@ExcelCellBinding(offset = 6)
	protected int upLimit;

	/** 比例1 */
	@ExcelCellBinding(offset = 7)
	protected int ratio1;

	/** 比例2 */
	@ExcelCellBinding(offset = 8)
	protected int ratio2;

	/** 比例3 */
	@ExcelCellBinding(offset = 9)
	protected int ratio3;

	/** 几率1 */
	@ExcelCellBinding(offset = 10)
	protected int chance1;

	/** 几率2 */
	@ExcelCellBinding(offset = 11)
	protected int chance2;

	/** 几率3 */
	@ExcelCellBinding(offset = 12)
	protected int chance3;

	/** 复活1 */
	@ExcelCellBinding(offset = 13)
	protected int reviveNum1;

	/** 复活2 */
	@ExcelCellBinding(offset = 14)
	protected int reviveNum2;

	/** 复活3 */
	@ExcelCellBinding(offset = 15)
	protected int reviveNum3;

	/** 复活4 */
	@ExcelCellBinding(offset = 16)
	protected int reviveNum4;

	/** 复活5 */
	@ExcelCellBinding(offset = 17)
	protected int reviveNum5;

	/** 复活6 */
	@ExcelCellBinding(offset = 18)
	protected int reviveNum6;

	/** 复活7 */
	@ExcelCellBinding(offset = 19)
	protected int reviveNum7;

	/** 复活8 */
	@ExcelCellBinding(offset = 20)
	protected int reviveNum8;

	/** 复活9 */
	@ExcelCellBinding(offset = 21)
	protected int reviveNum9;


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
	
	public int getDownLimit() {
		return this.downLimit;
	}


	public final static int getDownLimitMinLimit() {
		return 0;
	}

	public void setDownLimit(int downLimit) {
		if (downLimit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[下限]downLimit的值不得小于0");
		}
		this.downLimit = downLimit;
	}
	
	public int getUpLimit() {
		return this.upLimit;
	}



	public void setUpLimit(int upLimit) {
		this.upLimit = upLimit;
	}
	
	public int getRatio1() {
		return this.ratio1;
	}



	public void setRatio1(int ratio1) {
		this.ratio1 = ratio1;
	}
	
	public int getRatio2() {
		return this.ratio2;
	}



	public void setRatio2(int ratio2) {
		this.ratio2 = ratio2;
	}
	
	public int getRatio3() {
		return this.ratio3;
	}



	public void setRatio3(int ratio3) {
		this.ratio3 = ratio3;
	}
	
	public int getChance1() {
		return this.chance1;
	}



	public void setChance1(int chance1) {
		this.chance1 = chance1;
	}
	
	public int getChance2() {
		return this.chance2;
	}



	public void setChance2(int chance2) {
		this.chance2 = chance2;
	}
	
	public int getChance3() {
		return this.chance3;
	}



	public void setChance3(int chance3) {
		this.chance3 = chance3;
	}
	
	public int getReviveNum1() {
		return this.reviveNum1;
	}



	public void setReviveNum1(int reviveNum1) {
		this.reviveNum1 = reviveNum1;
	}
	
	public int getReviveNum2() {
		return this.reviveNum2;
	}



	public void setReviveNum2(int reviveNum2) {
		this.reviveNum2 = reviveNum2;
	}
	
	public int getReviveNum3() {
		return this.reviveNum3;
	}



	public void setReviveNum3(int reviveNum3) {
		this.reviveNum3 = reviveNum3;
	}
	
	public int getReviveNum4() {
		return this.reviveNum4;
	}



	public void setReviveNum4(int reviveNum4) {
		this.reviveNum4 = reviveNum4;
	}
	
	public int getReviveNum5() {
		return this.reviveNum5;
	}



	public void setReviveNum5(int reviveNum5) {
		this.reviveNum5 = reviveNum5;
	}
	
	public int getReviveNum6() {
		return this.reviveNum6;
	}



	public void setReviveNum6(int reviveNum6) {
		this.reviveNum6 = reviveNum6;
	}
	
	public int getReviveNum7() {
		return this.reviveNum7;
	}



	public void setReviveNum7(int reviveNum7) {
		this.reviveNum7 = reviveNum7;
	}
	
	public int getReviveNum8() {
		return this.reviveNum8;
	}



	public void setReviveNum8(int reviveNum8) {
		this.reviveNum8 = reviveNum8;
	}
	
	public int getReviveNum9() {
		return this.reviveNum9;
	}



	public void setReviveNum9(int reviveNum9) {
		this.reviveNum9 = reviveNum9;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, JackpotTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends JackpotTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, JackpotTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "JackpotTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", downLimit=" + downLimit + ", upLimit=" + upLimit + ", ratio1=" + ratio1 + ", ratio2=" + ratio2 + ", ratio3=" + ratio3 + ", chance1=" + chance1 + ", chance2=" + chance2 + ", chance3=" + chance3 + ", reviveNum1=" + reviveNum1 + ", reviveNum2=" + reviveNum2 + ", reviveNum3=" + reviveNum3 + ", reviveNum4=" + reviveNum4 + ", reviveNum5=" + reviveNum5 + ", reviveNum6=" + reviveNum6 + ", reviveNum7=" + reviveNum7 + ", reviveNum8=" + reviveNum8 + ", reviveNum9=" + reviveNum9 + ",]";
	}
}