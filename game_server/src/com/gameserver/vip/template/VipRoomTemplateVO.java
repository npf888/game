package com.gameserver.vip.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * viproom
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class VipRoomTemplateVO extends TemplateObject {

	/** 名称 */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** vip等级 */
	@ExcelCellBinding(offset = 5)
	protected int smallBlind;

	/** 服务费1 */
	@ExcelCellBinding(offset = 6)
	protected int serviceValue1;

	/** 筹码 */
	@ExcelCellBinding(offset = 7)
	protected int watchNum;

	/** 赠送经验 */
	@ExcelCellBinding(offset = 8)
	protected int serviceValue2;

	/** 经验速度 */
	@ExcelCellBinding(offset = 9)
	protected int victoryExp;

	/** 魅力值 */
	@ExcelCellBinding(offset = 10)
	protected int joinExp;

	/** 每天奖励 */
	@ExcelCellBinding(offset = 11)
	protected int dealerCost;

	/** 房间人数 */
	@ExcelCellBinding(offset = 12)
	protected int roomNum;

	/** 最小携带 */
	@ExcelCellBinding(offset = 13)
	protected int minCarry;

	/** 最大携带 */
	@ExcelCellBinding(offset = 14)
	protected int maxCarry;


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
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getSmallBlind() {
		return this.smallBlind;
	}



	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}
	
	public int getServiceValue1() {
		return this.serviceValue1;
	}



	public void setServiceValue1(int serviceValue1) {
		this.serviceValue1 = serviceValue1;
	}
	
	public int getWatchNum() {
		return this.watchNum;
	}



	public void setWatchNum(int watchNum) {
		this.watchNum = watchNum;
	}
	
	public int getServiceValue2() {
		return this.serviceValue2;
	}



	public void setServiceValue2(int serviceValue2) {
		this.serviceValue2 = serviceValue2;
	}
	
	public int getVictoryExp() {
		return this.victoryExp;
	}



	public void setVictoryExp(int victoryExp) {
		this.victoryExp = victoryExp;
	}
	
	public int getJoinExp() {
		return this.joinExp;
	}



	public void setJoinExp(int joinExp) {
		this.joinExp = joinExp;
	}
	
	public int getDealerCost() {
		return this.dealerCost;
	}



	public void setDealerCost(int dealerCost) {
		this.dealerCost = dealerCost;
	}
	
	public int getRoomNum() {
		return this.roomNum;
	}



	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	
	public int getMinCarry() {
		return this.minCarry;
	}



	public void setMinCarry(int minCarry) {
		this.minCarry = minCarry;
	}
	
	public int getMaxCarry() {
		return this.maxCarry;
	}



	public void setMaxCarry(int maxCarry) {
		this.maxCarry = maxCarry;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, VipRoomTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends VipRoomTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, VipRoomTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "VipRoomTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", smallBlind=" + smallBlind + ", serviceValue1=" + serviceValue1 + ", watchNum=" + watchNum + ", serviceValue2=" + serviceValue2 + ", victoryExp=" + victoryExp + ", joinExp=" + joinExp + ", dealerCost=" + dealerCost + ", roomNum=" + roomNum + ", minCarry=" + minCarry + ", maxCarry=" + maxCarry + ",]";
	}
}