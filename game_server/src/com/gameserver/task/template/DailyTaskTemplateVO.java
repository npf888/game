package com.gameserver.task.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * DailyTaskTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class DailyTaskTemplateVO extends TemplateObject {

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

	/**  */
	@ExcelCellBinding(offset = 5)
	protected int clientType;

	/**  */
	@ExcelCellBinding(offset = 6)
	protected int serverType;

	/**  */
	@ExcelCellBinding(offset = 7)
	protected int condition1;

	/**  */
	@ExcelCellBinding(offset = 8)
	protected int itemId;

	/**  */
	@ExcelCellBinding(offset = 9)
	protected int reward1Num;

	/**  */
	@ExcelCellBinding(offset = 10)
	protected int refreshtype;

	/**  */
	@ExcelCellBinding(offset = 11)
	protected int leveljudge;

	/**  */
	@ExcelCellBinding(offset = 12)
	protected int goTo;


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
	
	public int getClientType() {
		return this.clientType;
	}



	public void setClientType(int clientType) {
		if (clientType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[]clientType不可以为0");
		}
		this.clientType = clientType;
	}
	
	public int getServerType() {
		return this.serverType;
	}



	public void setServerType(int serverType) {
		if (serverType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[]serverType不可以为0");
		}
		this.serverType = serverType;
	}
	
	public int getCondition1() {
		return this.condition1;
	}



	public void setCondition1(int condition1) {
		if (condition1 == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[]condition1不可以为0");
		}
		this.condition1 = condition1;
	}
	
	public int getItemId() {
		return this.itemId;
	}



	public void setItemId(int itemId) {
		if (itemId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[]itemId不可以为0");
		}
		this.itemId = itemId;
	}
	
	public int getReward1Num() {
		return this.reward1Num;
	}



	public void setReward1Num(int reward1Num) {
		if (reward1Num == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[]reward1Num不可以为0");
		}
		this.reward1Num = reward1Num;
	}
	
	public int getRefreshtype() {
		return this.refreshtype;
	}



	public void setRefreshtype(int refreshtype) {
		if (refreshtype == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[]refreshtype不可以为0");
		}
		this.refreshtype = refreshtype;
	}
	
	public int getLeveljudge() {
		return this.leveljudge;
	}



	public void setLeveljudge(int leveljudge) {
		this.leveljudge = leveljudge;
	}
	
	public int getGoTo() {
		return this.goTo;
	}



	public void setGoTo(int goTo) {
		this.goTo = goTo;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, DailyTaskTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends DailyTaskTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, DailyTaskTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "DailyTaskTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", clientType=" + clientType + ", serverType=" + serverType + ", condition1=" + condition1 + ", itemId=" + itemId + ", reward1Num=" + reward1Num + ", refreshtype=" + refreshtype + ", leveljudge=" + leveljudge + ", goTo=" + goTo + ",]";
	}
}