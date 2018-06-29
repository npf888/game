package com.gameserver.scene.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * 游戏
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class CityTemplateVO extends TemplateObject {

	/** 城镇名称多语言Id */
	@ExcelCellBinding(offset = 1)
	protected int cityNameLangId;

	/** 城镇名称 */
	@ExcelCellBinding(offset = 2)
	protected String cityName;

	/** 下一个场景id */
	@ExcelCellBinding(offset = 3)
	protected int nextCityId;

	/** 城镇最低等级 */
	@ExcelCellBinding(offset = 4)
	protected int minLevel;

	/** 城镇最高等级 */
	@ExcelCellBinding(offset = 5)
	protected int maxLevel;

	/** 城镇场景名 */
	@ExcelCellBinding(offset = 6)
	protected String citySceneName;

	/** 需求任务ID */
	@ExcelCellBinding(offset = 7)
	protected int needTaskId;

	/** 背景场景 */
	@ExcelCellBinding(offset = 8)
	protected String backgroundScence;


	public int getCityNameLangId() {
		return this.cityNameLangId;
	}



	public void setCityNameLangId(int cityNameLangId) {
		this.cityNameLangId = cityNameLangId;
	}
	
	public String getCityName() {
		return this.cityName;
	}



	public void setCityName(String cityName) {
		if (StringUtils.isEmpty(cityName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[城镇名称]cityName不可以为空");
		}
		this.cityName = cityName;
	}
	
	public int getNextCityId() {
		return this.nextCityId;
	}


	public final static int getNextCityIdMinLimit() {
		return 0;
	}

	public void setNextCityId(int nextCityId) {
		if (nextCityId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[下一个场景id]nextCityId的值不得小于0");
		}
		this.nextCityId = nextCityId;
	}
	
	public int getMinLevel() {
		return this.minLevel;
	}


	public final static int getMinLevelMinLimit() {
		return 0;
	}

	public void setMinLevel(int minLevel) {
		if (minLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[城镇最低等级]minLevel的值不得小于0");
		}
		this.minLevel = minLevel;
	}
	
	public int getMaxLevel() {
		return this.maxLevel;
	}


	public final static int getMaxLevelMinLimit() {
		return 0;
	}

	public void setMaxLevel(int maxLevel) {
		if (maxLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[城镇最高等级]maxLevel的值不得小于0");
		}
		this.maxLevel = maxLevel;
	}
	
	public String getCitySceneName() {
		return this.citySceneName;
	}



	public void setCitySceneName(String citySceneName) {
		if (StringUtils.isEmpty(citySceneName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[城镇场景名]citySceneName不可以为空");
		}
		this.citySceneName = citySceneName;
	}
	
	public int getNeedTaskId() {
		return this.needTaskId;
	}


	public final static int getNeedTaskIdMinLimit() {
		return 0;
	}

	public void setNeedTaskId(int needTaskId) {
		if (needTaskId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[需求任务ID]needTaskId的值不得小于0");
		}
		this.needTaskId = needTaskId;
	}
	
	public String getBackgroundScence() {
		return this.backgroundScence;
	}



	public void setBackgroundScence(String backgroundScence) {
		this.backgroundScence = backgroundScence;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, CityTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends CityTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, CityTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "CityTemplateVO [  cityNameLangId=" + cityNameLangId + ", cityName=" + cityName + ", nextCityId=" + nextCityId + ", minLevel=" + minLevel + ", maxLevel=" + maxLevel + ", citySceneName=" + citySceneName + ", needTaskId=" + needTaskId + ", backgroundScence=" + backgroundScence + ",]";
	}
}