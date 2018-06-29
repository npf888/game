package com.gameserver.ranknew.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * ScoreListTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ScoreListTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 段位 */
	@ExcelCellBinding(offset = 4)
	protected int grades;

	/** 星级 */
	@ExcelCellBinding(offset = 5)
	protected int starLv;

	/**  */
	@ExcelCellBinding(offset = 6)
	protected int rewardId;

	/**  */
	@ExcelCellBinding(offset = 7)
	protected int rewardNum;

	/** 积分下限 */
	@ExcelCellBinding(offset = 8)
	protected int scoreLower;

	/** 积分上限 */
	@ExcelCellBinding(offset = 9)
	protected int scoreUpper;

	/** 0没有奖励 1有奖励 */
	@ExcelCellBinding(offset = 10)
	protected int rewardState;


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
	
	public int getGrades() {
		return this.grades;
	}



	public void setGrades(int grades) {
		this.grades = grades;
	}
	
	public int getStarLv() {
		return this.starLv;
	}



	public void setStarLv(int starLv) {
		this.starLv = starLv;
	}
	
	public int getRewardId() {
		return this.rewardId;
	}



	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	
	public int getRewardNum() {
		return this.rewardNum;
	}



	public void setRewardNum(int rewardNum) {
		this.rewardNum = rewardNum;
	}
	
	public int getScoreLower() {
		return this.scoreLower;
	}



	public void setScoreLower(int scoreLower) {
		this.scoreLower = scoreLower;
	}
	
	public int getScoreUpper() {
		return this.scoreUpper;
	}



	public void setScoreUpper(int scoreUpper) {
		this.scoreUpper = scoreUpper;
	}
	
	public int getRewardState() {
		return this.rewardState;
	}



	public void setRewardState(int rewardState) {
		this.rewardState = rewardState;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ScoreListTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ScoreListTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ScoreListTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ScoreListTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", grades=" + grades + ", starLv=" + starLv + ", rewardId=" + rewardId + ", rewardNum=" + rewardNum + ", scoreLower=" + scoreLower + ", scoreUpper=" + scoreUpper + ", rewardState=" + rewardState + ",]";
	}
}