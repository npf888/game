package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * BonusSpartaTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BonusSpartaTemplateVO extends TemplateObject {

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

	/** bonus的个数 */
	@ExcelCellBinding(offset = 6)
	protected int bonusNum;

	/** 游戏关卡 */
	@ExcelCellBinding(offset = 7)
	protected int bonusStage;

	/** 游戏第一关，敌人血量 */
	@ExcelCellBinding(offset = 8)
	protected int bonus1;

	/** 游戏第二关，敌人血量 */
	@ExcelCellBinding(offset = 9)
	protected int bonus2;

	/** 游戏第三关，敌人血量 */
	@ExcelCellBinding(offset = 10)
	protected int bonus3;

	/** 玩家血量 */
	@ExcelCellBinding(offset = 11)
	protected int player;


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
	
	public int getBonusNum() {
		return this.bonusNum;
	}


	public final static int getBonusNumMinLimit() {
		return 0;
	}

	public void setBonusNum(int bonusNum) {
		if (bonusNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[bonus的个数]bonusNum的值不得小于0");
		}
		this.bonusNum = bonusNum;
	}
	
	public int getBonusStage() {
		return this.bonusStage;
	}


	public final static int getBonusStageMinLimit() {
		return 0;
	}

	public void setBonusStage(int bonusStage) {
		if (bonusStage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[游戏关卡]bonusStage的值不得小于0");
		}
		this.bonusStage = bonusStage;
	}
	
	public int getBonus1() {
		return this.bonus1;
	}


	public final static int getBonus1MinLimit() {
		return 0;
	}

	public void setBonus1(int bonus1) {
		if (bonus1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[游戏第一关，敌人血量]bonus1的值不得小于0");
		}
		this.bonus1 = bonus1;
	}
	
	public int getBonus2() {
		return this.bonus2;
	}


	public final static int getBonus2MinLimit() {
		return 0;
	}

	public void setBonus2(int bonus2) {
		if (bonus2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[游戏第二关，敌人血量]bonus2的值不得小于0");
		}
		this.bonus2 = bonus2;
	}
	
	public int getBonus3() {
		return this.bonus3;
	}


	public final static int getBonus3MinLimit() {
		return 0;
	}

	public void setBonus3(int bonus3) {
		if (bonus3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[游戏第三关，敌人血量]bonus3的值不得小于0");
		}
		this.bonus3 = bonus3;
	}
	
	public int getPlayer() {
		return this.player;
	}


	public final static int getPlayerMinLimit() {
		return 0;
	}

	public void setPlayer(int player) {
		if (player < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[玩家血量]player的值不得小于0");
		}
		this.player = player;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BonusSpartaTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BonusSpartaTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BonusSpartaTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BonusSpartaTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", slotsNum=" + slotsNum + ", bonusNum=" + bonusNum + ", bonusStage=" + bonusStage + ", bonus1=" + bonus1 + ", bonus2=" + bonus2 + ", bonus3=" + bonus3 + ", player=" + player + ",]";
	}
}