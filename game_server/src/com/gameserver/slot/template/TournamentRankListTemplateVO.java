package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * TournamentRankListTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class TournamentRankListTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/**  */
	@ExcelCellBinding(offset = 3)
	protected int tournamentId;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 4)
	protected String langDesc;

	/** rank1 到 rank2 是个范围 在这个范围内的人拿这个奖励 */
	@ExcelCellBinding(offset = 5)
	protected int rank1;

	/** rank1 到 rank2 是个范围 */
	@ExcelCellBinding(offset = 6)
	protected int rank2;

	/** 奖励比例（万分比） */
	@ExcelCellBinding(offset = 7)
	protected int reward;


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
	
	public int getTournamentId() {
		return this.tournamentId;
	}



	public void setTournamentId(int tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	public String getLangDesc() {
		return this.langDesc;
	}



	public void setLangDesc(String langDesc) {
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public int getRank1() {
		return this.rank1;
	}



	public void setRank1(int rank1) {
		this.rank1 = rank1;
	}
	
	public int getRank2() {
		return this.rank2;
	}



	public void setRank2(int rank2) {
		this.rank2 = rank2;
	}
	
	public int getReward() {
		return this.reward;
	}



	public void setReward(int reward) {
		this.reward = reward;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, TournamentRankListTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends TournamentRankListTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, TournamentRankListTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "TournamentRankListTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", tournamentId=" + tournamentId + ", langDesc=" + langDesc + ", rank1=" + rank1 + ", rank2=" + rank2 + ", reward=" + reward + ",]";
	}
}