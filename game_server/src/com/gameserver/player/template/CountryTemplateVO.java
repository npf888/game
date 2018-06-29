package com.gameserver.player.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * CountryTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class CountryTemplateVO extends TemplateObject {

	/** 国家编号 */
	@ExcelCellBinding(offset = 1)
	protected String country;

	/** 对应中文名称 */
	@ExcelCellBinding(offset = 2)
	protected String langDesc;

	/** 国家对应的icon */
	@ExcelCellBinding(offset = 3)
	protected String icon;

	/** 国家名称 */
	@ExcelCellBinding(offset = 4)
	protected int nameId;


	public String getCountry() {
		return this.country;
	}



	public void setCountry(String country) {
		if (StringUtils.isEmpty(country)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[国家编号]country不可以为空");
		}
		this.country = country;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[对应中文名称]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getNameId() {
		return this.nameId;
	}



	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, CountryTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends CountryTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, CountryTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "CountryTemplateVO [  country=" + country + ", langDesc=" + langDesc + ", icon=" + icon + ", nameId=" + nameId + ",]";
	}
}