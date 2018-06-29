package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * SocialitySpartaTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class SocialitySpartaTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 5)
	protected int slotsNum;

	/** 城墙血量 */
	@ExcelCellBinding(offset = 6)
	protected int wallNum;

	/** 随机元素数量 */
	@ExcelCellBinding(offset = 7)
	protected int randomNum;

	/** 社交转动次数 */
	@ExcelCellBinding(offset = 8)
	protected int socialityNum;


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
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getWallNum() {
		return this.wallNum;
	}


	public final static int getWallNumMinLimit() {
		return 0;
	}

	public void setWallNum(int wallNum) {
		if (wallNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[城墙血量]wallNum的值不得小于0");
		}
		this.wallNum = wallNum;
	}
	
	public int getRandomNum() {
		return this.randomNum;
	}


	public final static int getRandomNumMinLimit() {
		return 0;
	}

	public void setRandomNum(int randomNum) {
		if (randomNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[随机元素数量]randomNum的值不得小于0");
		}
		this.randomNum = randomNum;
	}
	
	public int getSocialityNum() {
		return this.socialityNum;
	}


	public final static int getSocialityNumMinLimit() {
		return 0;
	}

	public void setSocialityNum(int socialityNum) {
		if (socialityNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[社交转动次数]socialityNum的值不得小于0");
		}
		this.socialityNum = socialityNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, SocialitySpartaTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends SocialitySpartaTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, SocialitySpartaTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "SocialitySpartaTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", wallNum=" + wallNum + ", randomNum=" + randomNum + ", socialityNum=" + socialityNum + ",]";
	}
}