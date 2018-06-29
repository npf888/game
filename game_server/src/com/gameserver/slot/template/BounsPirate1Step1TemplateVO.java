package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsPirate1Step1Template.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsPirate1Step1TemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 老虎机机器编号 */
	@ExcelCellBinding(offset = 4)
	protected int slotsNum;

	/** 奖励类型： 0.金币  1.炮弹 */
	@ExcelCellBinding(offset = 5)
	protected int type;

	/** 炮弹的数量或者炮弹个数 */
	@ExcelCellBinding(offset = 6)
	protected int num;

	/** 免费转动次数 */
	@ExcelCellBinding(offset = 7)
	protected int weight;


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
	
	public int getSlotsNum() {
		return this.slotsNum;
	}



	public void setSlotsNum(int slotsNum) {
		this.slotsNum = slotsNum;
	}
	
	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public int getNum() {
		return this.num;
	}



	public void setNum(int num) {
		this.num = num;
	}
	
	public int getWeight() {
		return this.weight;
	}



	public void setWeight(int weight) {
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsPirate1Step1TemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsPirate1Step1TemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsPirate1Step1TemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsPirate1Step1TemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", slotsNum=" + slotsNum + ", type=" + type + ", num=" + num + ", weight=" + weight + ",]";
	}
}