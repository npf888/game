package com.gameserver.ranknew.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * ScoreListRewardTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ScoreListRewardTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 名次 */
	@ExcelCellBinding(offset = 4)
	protected int list1;

	/** 名次 */
	@ExcelCellBinding(offset = 5)
	protected int list2;

	/**  */
	@ExcelCellBinding(offset = 6)
	protected int reward;

	/**  */
	@ExcelCellBinding(offset = 7)
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
	
	public int getReward() {
		return this.reward;
	}



	public void setReward(int reward) {
		this.reward = reward;
	}
	
	public int getRewardNum() {
		return this.rewardNum;
	}



	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ScoreListRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ScoreListRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ScoreListRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ScoreListRewardTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", list1=" + list1 + ", list2=" + list2 + ", reward=" + reward + ", rewardNum=" + rewardNum + ",]";
	}
}