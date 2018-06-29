package com.gameserver.newbie.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * NovicebootTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class NovicebootTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/**  */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/**  */
	@ExcelCellBinding(offset = 4)
	protected String stepDesc;

	/** 老虎机编号 */
	@ExcelCellBinding(offset = 5)
	protected int slotNum;

	/** 老虎机场景 */
	@ExcelCellBinding(offset = 6)
	protected int type;

	/**  */
	@ExcelCellBinding(offset = 7)
	protected int step;

	/** 0非强制1强制 */
	@ExcelCellBinding(offset = 8)
	protected int force;

	/** 0不可跳过      1可跳过 */
	@ExcelCellBinding(offset = 9)
	protected int ignore;


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
	
	public String getStepDesc() {
		return this.stepDesc;
	}



	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}
	
	public int getSlotNum() {
		return this.slotNum;
	}



	public void setSlotNum(int slotNum) {
		this.slotNum = slotNum;
	}
	
	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public int getStep() {
		return this.step;
	}



	public void setStep(int step) {
		this.step = step;
	}
	
	public int getForce() {
		return this.force;
	}



	public void setForce(int force) {
		this.force = force;
	}
	
	public int getIgnore() {
		return this.ignore;
	}



	public void setIgnore(int ignore) {
		this.ignore = ignore;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, NovicebootTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends NovicebootTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, NovicebootTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "NovicebootTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", stepDesc=" + stepDesc + ", slotNum=" + slotNum + ", type=" + type + ", step=" + step + ", force=" + force + ", ignore=" + ignore + ",]";
	}
}