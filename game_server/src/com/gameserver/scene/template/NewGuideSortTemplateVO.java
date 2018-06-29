package com.gameserver.scene.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.core.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 新手行为表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class NewGuideSortTemplateVO extends TemplateObject {

	/** 类型 */
	@ExcelCellBinding(offset = 1)
	protected int type;

	/** 类型Id */
	@ExcelCellBinding(offset = 2)
	protected String typeId;

	/** 备注 */
	@ExcelCellBinding(offset = 3)
	protected String remarks;


	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public String getTypeId() {
		return this.typeId;
	}



	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	
	public String getRemarks() {
		return this.remarks;
	}



	public void setRemarks(String remarks) {
		if (StringUtils.isEmpty(remarks)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[备注]remarks不可以为空");
		}
		this.remarks = remarks;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, NewGuideSortTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends NewGuideSortTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, NewGuideSortTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "NewGuideSortTemplateVO [  type=" + type + ", typeId=" + typeId + ", remarks=" + remarks + ",]";
	}
}