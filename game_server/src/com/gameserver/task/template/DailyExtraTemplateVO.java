package com.gameserver.task.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * DailyExtraTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class DailyExtraTemplateVO extends TemplateObject {

	/** 名称 */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 完成任务个数 */
	@ExcelCellBinding(offset = 5)
	protected int finishNum;

	/** 奖励 */
	@ExcelCellBinding(offset = 6)
	protected int reward1Num;

	/**  */
	@ExcelCellBinding(offset = 7)
	protected int itemId;


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
		if (descrip == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[描述]descrip不可以为0");
		}
		this.descrip = descrip;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		if (StringUtils.isEmpty(icon)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[icon]icon不可以为空");
		}
		this.icon = icon;
	}
	
	public int getFinishNum() {
		return this.finishNum;
	}



	public void setFinishNum(int finishNum) {
		this.finishNum = finishNum;
	}
	
	public int getReward1Num() {
		return this.reward1Num;
	}



	public void setReward1Num(int reward1Num) {
		this.reward1Num = reward1Num;
	}
	
	public int getItemId() {
		return this.itemId;
	}



	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, DailyExtraTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends DailyExtraTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, DailyExtraTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "DailyExtraTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", finishNum=" + finishNum + ", reward1Num=" + reward1Num + ", itemId=" + itemId + ",]";
	}
}