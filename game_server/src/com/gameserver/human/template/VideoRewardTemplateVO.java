package com.gameserver.human.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * VideoRewardTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class VideoRewardTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** itemId */
	@ExcelCellBinding(offset = 4)
	protected int itemId;

	/** itemNum */
	@ExcelCellBinding(offset = 5)
	protected int itemNum;


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
	
	public int getItemId() {
		return this.itemId;
	}


	public final static int getItemIdMinLimit() {
		return 0;
	}

	public void setItemId(int itemId) {
		if (itemId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[itemId]itemId的值不得小于0");
		}
		this.itemId = itemId;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}


	public final static int getItemNumMinLimit() {
		return 0;
	}

	public void setItemNum(int itemNum) {
		if (itemNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[itemNum]itemNum的值不得小于0");
		}
		this.itemNum = itemNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, VideoRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends VideoRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, VideoRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "VideoRewardTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", itemId=" + itemId + ", itemNum=" + itemNum + ",]";
	}
}