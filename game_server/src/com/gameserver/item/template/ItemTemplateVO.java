package com.gameserver.item.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * 道具
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ItemTemplateVO extends TemplateObject {

	/** 语言Id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 名称 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 图标 */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 叠加上限 */
	@ExcelCellBinding(offset = 5)
	protected int overLimit;

	/** 开始时间 */
	@ExcelCellBinding(offset = 6)
	protected String beginTime;

	/** 过期时间 */
	@ExcelCellBinding(offset = 7)
	protected String endTime;

	/** 奖励id */
	@ExcelCellBinding(offset = 8)
	protected int rewardId;

	/** 奖励数量 */
	@ExcelCellBinding(offset = 9)
	protected int rewardNum;

	/** 是否发送 */
	@ExcelCellBinding(offset = 10)
	protected int send;

	/** 回收价钱 */
	@ExcelCellBinding(offset = 11)
	protected int recyclingPrice;

	/** 道具类型 */
	@ExcelCellBinding(offset = 12)
	protected int itemType;

	/** 使用时间 分钟 */
	@ExcelCellBinding(offset = 13)
	protected int time;

	/**  */
	@ExcelCellBinding(offset = 14)
	protected int cardID;

	/** 卡片类型 */
	@ExcelCellBinding(offset = 15)
	protected int cardType;

	/** 经验加成 */
	@ExcelCellBinding(offset = 16)
	protected int experience;

	/** 对应奖池 */
	@ExcelCellBinding(offset = 17)
	protected int poolType;

	/** 是否背包显示 1.是 2.不显示 */
	@ExcelCellBinding(offset = 18)
	protected int bag;


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
	
	public int getOverLimit() {
		return this.overLimit;
	}



	public void setOverLimit(int overLimit) {
		this.overLimit = overLimit;
	}
	
	public String getBeginTime() {
		return this.beginTime;
	}



	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}



	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	
	public int getSend() {
		return this.send;
	}



	public void setSend(int send) {
		this.send = send;
	}
	
	public int getRecyclingPrice() {
		return this.recyclingPrice;
	}



	public void setRecyclingPrice(int recyclingPrice) {
		this.recyclingPrice = recyclingPrice;
	}
	
	public int getItemType() {
		return this.itemType;
	}



	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	
	public int getTime() {
		return this.time;
	}



	public void setTime(int time) {
		this.time = time;
	}
	
	public int getCardID() {
		return this.cardID;
	}



	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	
	public int getCardType() {
		return this.cardType;
	}



	public void setCardType(int cardType) {
		this.cardType = cardType;
	}
	
	public int getExperience() {
		return this.experience;
	}



	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public int getPoolType() {
		return this.poolType;
	}



	public void setPoolType(int poolType) {
		this.poolType = poolType;
	}
	
	public int getBag() {
		return this.bag;
	}



	public void setBag(int bag) {
		this.bag = bag;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ItemTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ItemTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ItemTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ItemTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", overLimit=" + overLimit + ", beginTime=" + beginTime + ", endTime=" + endTime + ", rewardId=" + rewardId + ", rewardNum=" + rewardNum + ", send=" + send + ", recyclingPrice=" + recyclingPrice + ", itemType=" + itemType + ", time=" + time + ", cardID=" + cardID + ", cardType=" + cardType + ", experience=" + experience + ", poolType=" + poolType + ", bag=" + bag + ",]";
	}
}