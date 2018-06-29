package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * ScatterMultipleTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ScatterMultipleTemplateVO extends TemplateObject {

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

	/** 老虎机编号类型 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** scatter数量 */
	@ExcelCellBinding(offset = 6)
	protected int scatterNum;

	/** 免费转动次数 */
	@ExcelCellBinding(offset = 7)
	protected int freeSpinNum;

	/** 结果倍数 */
	@ExcelCellBinding(offset = 8)
	protected int multiple;


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
					6, "[老虎机编号类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getScatterNum() {
		return this.scatterNum;
	}


	public final static int getScatterNumMinLimit() {
		return 0;
	}

	public void setScatterNum(int scatterNum) {
		if (scatterNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[scatter数量]scatterNum的值不得小于0");
		}
		this.scatterNum = scatterNum;
	}
	
	public int getFreeSpinNum() {
		return this.freeSpinNum;
	}


	public final static int getFreeSpinNumMinLimit() {
		return 0;
	}

	public void setFreeSpinNum(int freeSpinNum) {
		if (freeSpinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[免费转动次数]freeSpinNum的值不得小于0");
		}
		this.freeSpinNum = freeSpinNum;
	}
	
	public int getMultiple() {
		return this.multiple;
	}


	public final static int getMultipleMinLimit() {
		return 0;
	}

	public void setMultiple(int multiple) {
		if (multiple < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[结果倍数]multiple的值不得小于0");
		}
		this.multiple = multiple;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ScatterMultipleTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ScatterMultipleTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ScatterMultipleTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ScatterMultipleTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", scatterNum=" + scatterNum + ", freeSpinNum=" + freeSpinNum + ", multiple=" + multiple + ",]";
	}
}