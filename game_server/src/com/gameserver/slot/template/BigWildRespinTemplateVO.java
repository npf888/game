package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BigWildRespinTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BigWildRespinTemplateVO extends TemplateObject {

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

	/** 老虎机机器编号 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** bigWildNum个数 */
	@ExcelCellBinding(offset = 6)
	protected int bigWildNum;

	/** 免费转动次数 */
	@ExcelCellBinding(offset = 7)
	protected int respinNum;


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



	public void setSlotsNum(int slotsNum) {
		this.slotsNum = slotsNum;
	}
	
	public int getBigWildNum() {
		return this.bigWildNum;
	}



	public void setBigWildNum(int bigWildNum) {
		this.bigWildNum = bigWildNum;
	}
	
	public int getRespinNum() {
		return this.respinNum;
	}



	public void setRespinNum(int respinNum) {
		this.respinNum = respinNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BigWildRespinTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BigWildRespinTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BigWildRespinTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BigWildRespinTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", bigWildNum=" + bigWildNum + ", respinNum=" + respinNum + ",]";
	}
}