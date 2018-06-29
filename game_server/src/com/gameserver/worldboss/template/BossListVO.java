package com.gameserver.worldboss.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BossList.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BossListVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** icon */
	@ExcelCellBinding(offset = 3)
	protected String icon1;

	/** 1、击杀榜 2、未击杀榜 */
	@ExcelCellBinding(offset = 4)
	protected int rankListId;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 5)
	protected String langDesc;

	/** 富豪榜伤害名次 */
	@ExcelCellBinding(offset = 6)
	protected int list1;

	/** 排名名次 */
	@ExcelCellBinding(offset = 7)
	protected int list2;

	/** 奖励2：物品id */
	@ExcelCellBinding(offset = 8)
	protected int itemid2;

	/** 奖励数量 奖励物品数量 */
	@ExcelCellBinding(offset = 9)
	protected int rewardNum2;


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
	
	public String getIcon1() {
		return this.icon1;
	}



	public void setIcon1(String icon1) {
		this.icon1 = icon1;
	}
	
	public int getRankListId() {
		return this.rankListId;
	}



	public void setRankListId(int rankListId) {
		this.rankListId = rankListId;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public int getList1() {
		return this.list1;
	}



	public void setList1(int list1) {
		this.list1 = list1;
	}
	
	public int getList2() {
		return this.list2;
	}



	public void setList2(int list2) {
		this.list2 = list2;
	}
	
	public int getItemid2() {
		return this.itemid2;
	}



	public void setItemid2(int itemid2) {
		this.itemid2 = itemid2;
	}
	
	public int getRewardNum2() {
		return this.rewardNum2;
	}



	public void setRewardNum2(int rewardNum2) {
		this.rewardNum2 = rewardNum2;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BossListVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BossListVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BossListVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BossListVO [  nameId=" + nameId + ", descrip=" + descrip + ", icon1=" + icon1 + ", rankListId=" + rankListId + ", langDesc=" + langDesc + ", list1=" + list1 + ", list2=" + list2 + ", itemid2=" + itemid2 + ", rewardNum2=" + rewardNum2 + ",]";
	}
}