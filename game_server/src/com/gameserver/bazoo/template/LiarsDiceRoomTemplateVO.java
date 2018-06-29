package com.gameserver.bazoo.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * LiarsDiceRoomTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class LiarsDiceRoomTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 模式类型：1：经典吹牛模式，2：牛牛模式，3：梭哈模式，4:红黑单双模式，5：单机模式 */
	@ExcelCellBinding(offset = 3)
	protected int modeType;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 4)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 5)
	protected String icon;

	/** 是否开放：1开放，0不开放 */
	@ExcelCellBinding(offset = 6)
	protected int openUp;

	/** 底注 */
	@ExcelCellBinding(offset = 7)
	protected int smallBet;

	/** 最小买入 */
	@ExcelCellBinding(offset = 8)
	protected int minCarry;

	/** 抽水:获胜者赢取的筹码的比例（万分比） */
	@ExcelCellBinding(offset = 9)
	protected int extractValue;

	/** 房间人数 */
	@ExcelCellBinding(offset = 10)
	protected int roomNum;

	/** 重摇 开关 */
	@ExcelCellBinding(offset = 11)
	protected int onOff;

	/** 重摇花费初始值 */
	@ExcelCellBinding(offset = 12)
	protected int reroll;

	/** 重摇花费初始值 */
	@ExcelCellBinding(offset = 13)
	protected int rerollNum;


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
	
	public int getModeType() {
		return this.modeType;
	}



	public void setModeType(int modeType) {
		this.modeType = modeType;
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
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		if (StringUtils.isEmpty(icon)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[icon]icon不可以为空");
		}
		this.icon = icon;
	}
	
	public int getOpenUp() {
		return this.openUp;
	}



	public void setOpenUp(int openUp) {
		this.openUp = openUp;
	}
	
	public int getSmallBet() {
		return this.smallBet;
	}



	public void setSmallBet(int smallBet) {
		this.smallBet = smallBet;
	}
	
	public int getMinCarry() {
		return this.minCarry;
	}



	public void setMinCarry(int minCarry) {
		this.minCarry = minCarry;
	}
	
	public int getExtractValue() {
		return this.extractValue;
	}



	public void setExtractValue(int extractValue) {
		this.extractValue = extractValue;
	}
	
	public int getRoomNum() {
		return this.roomNum;
	}



	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	
	public int getOnOff() {
		return this.onOff;
	}



	public void setOnOff(int onOff) {
		this.onOff = onOff;
	}
	
	public int getReroll() {
		return this.reroll;
	}



	public void setReroll(int reroll) {
		this.reroll = reroll;
	}
	
	public int getRerollNum() {
		return this.rerollNum;
	}



	public void setRerollNum(int rerollNum) {
		this.rerollNum = rerollNum;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, LiarsDiceRoomTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends LiarsDiceRoomTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, LiarsDiceRoomTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "LiarsDiceRoomTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", modeType=" + modeType + ", langDesc=" + langDesc + ", icon=" + icon + ", openUp=" + openUp + ", smallBet=" + smallBet + ", minCarry=" + minCarry + ", extractValue=" + extractValue + ", roomNum=" + roomNum + ", onOff=" + onOff + ", reroll=" + reroll + ", rerollNum=" + rerollNum + ",]";
	}
}