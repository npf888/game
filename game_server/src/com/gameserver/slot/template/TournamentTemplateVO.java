package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * TournamentTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class TournamentTemplateVO extends TemplateObject {

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

	/**  */
	@ExcelCellBinding(offset = 5)
	protected int tournamentType;

	/**  */
	@ExcelCellBinding(offset = 6)
	protected int slotNum1;

	/**  */
	@ExcelCellBinding(offset = 7)
	protected int slotNum2;

	/**  */
	@ExcelCellBinding(offset = 8)
	protected int slotNum3;

	/**  */
	@ExcelCellBinding(offset = 9)
	protected int slotNum4;

	/**  */
	@ExcelCellBinding(offset = 10)
	protected int slotNum5;

	/**  */
	@ExcelCellBinding(offset = 11)
	protected int duration1;

	/**  */
	@ExcelCellBinding(offset = 12)
	protected int duration2;

	/**  */
	@ExcelCellBinding(offset = 13)
	protected int duration3;

	/**  */
	@ExcelCellBinding(offset = 14)
	protected int totalTime;

	/**  */
	@ExcelCellBinding(offset = 15)
	protected int raceReward;

	/**  */
	@ExcelCellBinding(offset = 16)
	protected int intervalRaceReward;


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
	
	public int getTournamentType() {
		return this.tournamentType;
	}



	public void setTournamentType(int tournamentType) {
		this.tournamentType = tournamentType;
	}
	
	public int getSlotNum1() {
		return this.slotNum1;
	}



	public void setSlotNum1(int slotNum1) {
		this.slotNum1 = slotNum1;
	}
	
	public int getSlotNum2() {
		return this.slotNum2;
	}



	public void setSlotNum2(int slotNum2) {
		this.slotNum2 = slotNum2;
	}
	
	public int getSlotNum3() {
		return this.slotNum3;
	}



	public void setSlotNum3(int slotNum3) {
		this.slotNum3 = slotNum3;
	}
	
	public int getSlotNum4() {
		return this.slotNum4;
	}



	public void setSlotNum4(int slotNum4) {
		this.slotNum4 = slotNum4;
	}
	
	public int getSlotNum5() {
		return this.slotNum5;
	}



	public void setSlotNum5(int slotNum5) {
		this.slotNum5 = slotNum5;
	}
	
	public int getDuration1() {
		return this.duration1;
	}



	public void setDuration1(int duration1) {
		this.duration1 = duration1;
	}
	
	public int getDuration2() {
		return this.duration2;
	}



	public void setDuration2(int duration2) {
		this.duration2 = duration2;
	}
	
	public int getDuration3() {
		return this.duration3;
	}



	public void setDuration3(int duration3) {
		this.duration3 = duration3;
	}
	
	public int getTotalTime() {
		return this.totalTime;
	}



	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	
	public int getRaceReward() {
		return this.raceReward;
	}



	public void setRaceReward(int raceReward) {
		this.raceReward = raceReward;
	}
	
	public int getIntervalRaceReward() {
		return this.intervalRaceReward;
	}



	public void setIntervalRaceReward(int intervalRaceReward) {
		this.intervalRaceReward = intervalRaceReward;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, TournamentTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends TournamentTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, TournamentTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "TournamentTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", tournamentType=" + tournamentType + ", slotNum1=" + slotNum1 + ", slotNum2=" + slotNum2 + ", slotNum3=" + slotNum3 + ", slotNum4=" + slotNum4 + ", slotNum5=" + slotNum5 + ", duration1=" + duration1 + ", duration2=" + duration2 + ", duration3=" + duration3 + ", totalTime=" + totalTime + ", raceReward=" + raceReward + ", intervalRaceReward=" + intervalRaceReward + ",]";
	}
}