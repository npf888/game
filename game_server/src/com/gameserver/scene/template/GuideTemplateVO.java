package com.gameserver.scene.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.core.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 新手引导表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class GuideTemplateVO extends TemplateObject {

	/** 触发类型 */
	@ExcelCellBinding(offset = 1)
	protected int type;

	/** 类型参数 */
	@ExcelCellBinding(offset = 2)
	protected int typeParam;

	/** 道具ID */
	@ExcelCellBinding(offset = 3)
	protected String itemId;

	/** 道具数量 */
	@ExcelCellBinding(offset = 4)
	protected String itemNum;

	/** 引导步骤 */
	@ExcelCellBinding(offset = 5)
	protected String guideStep;

	/** 备注 */
	@ExcelCellBinding(offset = 6)
	protected String remarks;


	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public int getTypeParam() {
		return this.typeParam;
	}



	public void setTypeParam(int typeParam) {
		this.typeParam = typeParam;
	}
	
	public String getItemId() {
		return this.itemId;
	}



	public void setItemId(String itemId) {
		if (StringUtils.isEmpty(itemId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[道具ID]itemId不可以为空");
		}
		this.itemId = itemId;
	}
	
	public String getItemNum() {
		return this.itemNum;
	}



	public void setItemNum(String itemNum) {
		if (StringUtils.isEmpty(itemNum)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[道具数量]itemNum不可以为空");
		}
		this.itemNum = itemNum;
	}
	
	public String getGuideStep() {
		return this.guideStep;
	}



	public void setGuideStep(String guideStep) {
		if (StringUtils.isEmpty(guideStep)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[引导步骤]guideStep不可以为空");
		}
		this.guideStep = guideStep;
	}
	
	public String getRemarks() {
		return this.remarks;
	}



	public void setRemarks(String remarks) {
		if (StringUtils.isEmpty(remarks)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[备注]remarks不可以为空");
		}
		this.remarks = remarks;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, GuideTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends GuideTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, GuideTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "GuideTemplateVO [  type=" + type + ", typeParam=" + typeParam + ", itemId=" + itemId + ", itemNum=" + itemNum + ", guideStep=" + guideStep + ", remarks=" + remarks + ",]";
	}
}