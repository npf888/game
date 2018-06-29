package com.gameserver.recharge.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * MultipleWheel.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class MultipleWheelVO extends TemplateObject {

	/**  */
	@ExcelCellBinding(offset = 1)
	protected int type;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected String descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 翻倍倍数 */
	@ExcelCellBinding(offset = 5)
	protected int multiple;

	/** 权重 */
	@ExcelCellBinding(offset = 6)
	protected int value;

	/** PID */
	@ExcelCellBinding(offset = 7)
	protected int pidID;


	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public String getDescrip() {
		return this.descrip;
	}



	public void setDescrip(String descrip) {
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
	
	public int getMultiple() {
		return this.multiple;
	}



	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	
	public int getValue() {
		return this.value;
	}



	public void setValue(int value) {
		this.value = value;
	}
	
	public int getPidID() {
		return this.pidID;
	}



	public void setPidID(int pidID) {
		this.pidID = pidID;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, MultipleWheelVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends MultipleWheelVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, MultipleWheelVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "MultipleWheelVO [  type=" + type + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", multiple=" + multiple + ", value=" + value + ", pidID=" + pidID + ",]";
	}
}