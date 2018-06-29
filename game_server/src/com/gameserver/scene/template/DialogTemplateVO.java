package com.gameserver.scene.template;

import java.util.Map;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;
import com.core.annotation.ExcelRowBinding;
import com.core.template.TemplateObject;
import com.core.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 剧情对话表
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class DialogTemplateVO extends TemplateObject {

	/** 内容 */
	@ExcelCellBinding(offset = 1)
	protected String content;

	/** 发言人类型 */
	@ExcelCellBinding(offset = 2)
	protected int spokesmanType;

	/** 发言人ID */
	@ExcelCellBinding(offset = 3)
	protected int spokesmanID;

	/** 名字的颜色 */
	@ExcelCellBinding(offset = 4)
	protected String nameColor;


	public String getContent() {
		return this.content;
	}



	public void setContent(String content) {
		if (StringUtils.isEmpty(content)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[内容]content不可以为空");
		}
		this.content = content;
	}
	
	public int getSpokesmanType() {
		return this.spokesmanType;
	}



	public void setSpokesmanType(int spokesmanType) {
		this.spokesmanType = spokesmanType;
	}
	
	public int getSpokesmanID() {
		return this.spokesmanID;
	}



	public void setSpokesmanID(int spokesmanID) {
		this.spokesmanID = spokesmanID;
	}
	
	public String getNameColor() {
		return this.nameColor;
	}



	public void setNameColor(String nameColor) {
		if (StringUtils.isEmpty(nameColor)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[名字的颜色]nameColor不可以为空");
		}
		this.nameColor = nameColor;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, DialogTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends DialogTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, DialogTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "DialogTemplateVO [  content=" + content + ", spokesmanType=" + spokesmanType + ", spokesmanID=" + spokesmanID + ", nameColor=" + nameColor + ",]";
	}
}