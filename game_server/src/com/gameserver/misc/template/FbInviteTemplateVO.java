package com.gameserver.misc.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * slots list
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class FbInviteTemplateVO extends TemplateObject {

	/** 语言Id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 名称 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 图标 */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 游戏数量 */
	@ExcelCellBinding(offset = 5)
	protected int gamerNum;

	/** item id */
	@ExcelCellBinding(offset = 6)
	protected int itemId;

	/** item num */
	@ExcelCellBinding(offset = 7)
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
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getGamerNum() {
		return this.gamerNum;
	}



	public void setGamerNum(int gamerNum) {
		this.gamerNum = gamerNum;
	}
	
	public int getItemId() {
		return this.itemId;
	}



	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}



	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, FbInviteTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends FbInviteTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, FbInviteTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "FbInviteTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", gamerNum=" + gamerNum + ", itemId=" + itemId + ", itemNum=" + itemNum + ",]";
	}
}