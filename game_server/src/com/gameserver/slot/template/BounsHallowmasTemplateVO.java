package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * NoviceStepTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsHallowmasTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 4)
	protected int slotsNum;

	/** bonus的个数 */
	@ExcelCellBinding(offset = 5)
	protected int bonusNum;

	/** 收集南瓜个数 */
	@ExcelCellBinding(offset = 6)
	protected int collectPumpkin;

	/** 为单线下注额的倍数，数值除以100使用 */
	@ExcelCellBinding(offset = 7)
	protected int reward;

	/** wild游戏个数 */
	@ExcelCellBinding(offset = 8)
	protected int wildNum;

	/** jackpot个数 */
	@ExcelCellBinding(offset = 9)
	protected int jackpotNum;

	/** 轮数 */
	@ExcelCellBinding(offset = 10)
	protected int turntime;


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
	
	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[老虎机类型]slotsNum的值不得小于0");
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
					6, "[bonus的个数]bonusNum的值不得小于0");
		}
		this.bonusNum = bonusNum;
	}
	
	public int getCollectPumpkin() {
		return this.collectPumpkin;
	}


	public final static int getCollectPumpkinMinLimit() {
		return 0;
	}

	public void setCollectPumpkin(int collectPumpkin) {
		if (collectPumpkin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[收集南瓜个数]collectPumpkin的值不得小于0");
		}
		this.collectPumpkin = collectPumpkin;
	}
	
	public int getReward() {
		return this.reward;
	}


	public final static int getRewardMinLimit() {
		return 0;
	}

	public void setReward(int reward) {
		if (reward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[为单线下注额的倍数，数值除以100使用]reward的值不得小于0");
		}
		this.reward = reward;
	}
	
	public int getWildNum() {
		return this.wildNum;
	}


	public final static int getWildNumMinLimit() {
		return 0;
	}

	public void setWildNum(int wildNum) {
		if (wildNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[wild游戏个数]wildNum的值不得小于0");
		}
		this.wildNum = wildNum;
	}
	
	public int getJackpotNum() {
		return this.jackpotNum;
	}


	public final static int getJackpotNumMinLimit() {
		return 0;
	}

	public void setJackpotNum(int jackpotNum) {
		if (jackpotNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[jackpot个数]jackpotNum的值不得小于0");
		}
		this.jackpotNum = jackpotNum;
	}
	
	public int getTurntime() {
		return this.turntime;
	}


	public final static int getTurntimeMinLimit() {
		return 0;
	}

	public void setTurntime(int turntime) {
		if (turntime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[轮数]turntime的值不得小于0");
		}
		this.turntime = turntime;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsHallowmasTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsHallowmasTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsHallowmasTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsHallowmasTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", slotsNum=" + slotsNum + ", bonusNum=" + bonusNum + ", collectPumpkin=" + collectPumpkin + ", reward=" + reward + ", wildNum=" + wildNum + ", jackpotNum=" + jackpotNum + ", turntime=" + turntime + ",]";
	}
}