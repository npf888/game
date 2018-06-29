package com.gameserver.bazoo.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * LiarsDiceRoomTaskTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class LiarsDiceRoomTaskTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 图标 */
	@ExcelCellBinding(offset = 4)
	protected String icons;

	/** 刷新类型  1:按天 ，2:按周 ，3:成就任务(成就不用刷新) */
	@ExcelCellBinding(offset = 5)
	protected int refreshtype;

	/** 吹牛 的类型 0:不区分模式， 1:吹牛，2:牛牛，3:梭哈 */
	@ExcelCellBinding(offset = 6)
	protected int modeType;

	/** 大的分类：0:任务，成就：1：胜利者，2：实践家，3：资本家 */
	@ExcelCellBinding(offset = 7)
	protected int bigType;

	/** 玩的方式：（1：只要玩了就算，2：胜场数，3：连胜数，4：玩的局数，5：钱数，6：段位数） */
	@ExcelCellBinding(offset = 8)
	protected int wayOfPlay;

	/** 应该满足的 条件 */
	@ExcelCellBinding(offset = 9)
	protected int condition;

	/** 应当给予的奖励 */
	@ExcelCellBinding(offset = 10)
	protected int rewardNum;


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
	
	public String getIcons() {
		return this.icons;
	}



	public void setIcons(String icons) {
		if (StringUtils.isEmpty(icons)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[图标]icons不可以为空");
		}
		this.icons = icons;
	}
	
	public int getRefreshtype() {
		return this.refreshtype;
	}



	public void setRefreshtype(int refreshtype) {
		this.refreshtype = refreshtype;
	}
	
	public int getModeType() {
		return this.modeType;
	}



	public void setModeType(int modeType) {
		this.modeType = modeType;
	}
	
	public int getBigType() {
		return this.bigType;
	}



	public void setBigType(int bigType) {
		this.bigType = bigType;
	}
	
	public int getWayOfPlay() {
		return this.wayOfPlay;
	}



	public void setWayOfPlay(int wayOfPlay) {
		this.wayOfPlay = wayOfPlay;
	}
	
	public int getCondition() {
		return this.condition;
	}



	public void setCondition(int condition) {
		this.condition = condition;
	}
	
	public int getRewardNum() {
		return this.rewardNum;
	}



	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, LiarsDiceRoomTaskTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends LiarsDiceRoomTaskTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, LiarsDiceRoomTaskTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "LiarsDiceRoomTaskTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icons=" + icons + ", refreshtype=" + refreshtype + ", modeType=" + modeType + ", bigType=" + bigType + ", wayOfPlay=" + wayOfPlay + ", condition=" + condition + ", rewardNum=" + rewardNum + ",]";
	}
}