package com.gameserver.club.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * ClubList.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ClubListTemplateVO extends TemplateObject {

	/** 名称id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 奖励筹码ico */
	@ExcelCellBinding(offset = 3)
	protected String icon1;

	/** 类型 */
	@ExcelCellBinding(offset = 4)
	protected int rankListId;

	/** 注释 */
	@ExcelCellBinding(offset = 5)
	protected String langDesc;

	/** 名词 */
	@ExcelCellBinding(offset = 6)
	protected int list1;

	/** 名次 */
	@ExcelCellBinding(offset = 7)
	protected int list2;

	/** 奖励1 */
	@ExcelCellBinding(offset = 8)
	protected int reward1;

	/** 奖励物品 */
	@ExcelCellBinding(offset = 9)
	protected String reward2;

	/** 人均奖励数量 */
	@ExcelCellBinding(offset = 10)
	protected int rewardNum1;


	public int getNameId() {
		return this.nameId;
	}



	public void setNameId(int nameId) {
		this.nameId = nameId;
	}
	
	public int getDescrip() {
		return this.descrip;
	}


	public final static int getDescripMinLimit() {
		return 0;
	}

	public void setDescrip(int descrip) {
		if (descrip < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[描述]descrip的值不得小于0");
		}
		this.descrip = descrip;
	}
	
	public String getIcon1() {
		return this.icon1;
	}



	public void setIcon1(String icon1) {
		this.icon1 = icon1;
	}
	
	public int getRankListId() {
		return this.rankListId;
	}


	public final static int getRankListIdMinLimit() {
		return 0;
	}

	public void setRankListId(int rankListId) {
		if (rankListId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[类型]rankListId的值不得小于0");
		}
		this.rankListId = rankListId;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	
	public int getList1() {
		return this.list1;
	}


	public final static int getList1MinLimit() {
		return 0;
	}

	public void setList1(int list1) {
		if (list1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[名词]list1的值不得小于0");
		}
		this.list1 = list1;
	}
	
	public int getList2() {
		return this.list2;
	}


	public final static int getList2MinLimit() {
		return 0;
	}

	public void setList2(int list2) {
		if (list2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[名次]list2的值不得小于0");
		}
		this.list2 = list2;
	}
	
	public int getReward1() {
		return this.reward1;
	}


	public final static int getReward1MinLimit() {
		return 0;
	}

	public void setReward1(int reward1) {
		if (reward1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[奖励1]reward1的值不得小于0");
		}
		this.reward1 = reward1;
	}
	
	public String getReward2() {
		return this.reward2;
	}



	public void setReward2(String reward2) {
		this.reward2 = reward2;
	}
	
	public int getRewardNum1() {
		return this.rewardNum1;
	}


	public final static int getRewardNum1MinLimit() {
		return 0;
	}

	public void setRewardNum1(int rewardNum1) {
		if (rewardNum1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[人均奖励数量]rewardNum1的值不得小于0");
		}
		this.rewardNum1 = rewardNum1;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ClubListTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ClubListTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ClubListTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ClubListTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", icon1=" + icon1 + ", rankListId=" + rankListId + ", langDesc=" + langDesc + ", list1=" + list1 + ", list2=" + list2 + ", reward1=" + reward1 + ", reward2=" + reward2 + ", rewardNum1=" + rewardNum1 + ",]";
	}
}