package com.gameserver.gift.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * GiftTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class GiftTemplateVO extends TemplateObject {

	/** 名称 */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 图片 */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 礼包类型 1 商店礼包 2新手礼包 3 多种礼包 */
	@ExcelCellBinding(offset = 5)
	protected int giftType;

	/** 等级下限 */
	@ExcelCellBinding(offset = 6)
	protected int levelDown;

	/** 等级上限 */
	@ExcelCellBinding(offset = 7)
	protected int levelUp;

	/** vip等级限制 */
	@ExcelCellBinding(offset = 8)
	protected int vipLevel;

	/** 礼包1 */
	@ExcelCellBinding(offset = 9)
	protected int gift1;

	/** 礼包2 */
	@ExcelCellBinding(offset = 10)
	protected int gift2;

	/** 礼包3 */
	@ExcelCellBinding(offset = 11)
	protected int gift3;

	/** 非VIP持续时间 */
	@ExcelCellBinding(offset = 12)
	protected int noVipGiftTime;

	/** VIP持续时间 */
	@ExcelCellBinding(offset = 13)
	protected int vipGiftTime;

	/** 没钱持续时间 */
	@ExcelCellBinding(offset = 14)
	protected int noMoneyInGame;

	/**  */
	@ExcelCellBinding(offset = 15)
	protected int buyTimes;


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
	
	public int getGiftType() {
		return this.giftType;
	}



	public void setGiftType(int giftType) {
		this.giftType = giftType;
	}
	
	public int getLevelDown() {
		return this.levelDown;
	}



	public void setLevelDown(int levelDown) {
		this.levelDown = levelDown;
	}
	
	public int getLevelUp() {
		return this.levelUp;
	}



	public void setLevelUp(int levelUp) {
		this.levelUp = levelUp;
	}
	
	public int getVipLevel() {
		return this.vipLevel;
	}



	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	
	public int getGift1() {
		return this.gift1;
	}



	public void setGift1(int gift1) {
		this.gift1 = gift1;
	}
	
	public int getGift2() {
		return this.gift2;
	}



	public void setGift2(int gift2) {
		this.gift2 = gift2;
	}
	
	public int getGift3() {
		return this.gift3;
	}



	public void setGift3(int gift3) {
		this.gift3 = gift3;
	}
	
	public int getNoVipGiftTime() {
		return this.noVipGiftTime;
	}



	public void setNoVipGiftTime(int noVipGiftTime) {
		this.noVipGiftTime = noVipGiftTime;
	}
	
	public int getVipGiftTime() {
		return this.vipGiftTime;
	}



	public void setVipGiftTime(int vipGiftTime) {
		this.vipGiftTime = vipGiftTime;
	}
	
	public int getNoMoneyInGame() {
		return this.noMoneyInGame;
	}



	public void setNoMoneyInGame(int noMoneyInGame) {
		this.noMoneyInGame = noMoneyInGame;
	}
	
	public int getBuyTimes() {
		return this.buyTimes;
	}



	public void setBuyTimes(int buyTimes) {
		this.buyTimes = buyTimes;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, GiftTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends GiftTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, GiftTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "GiftTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", giftType=" + giftType + ", levelDown=" + levelDown + ", levelUp=" + levelUp + ", vipLevel=" + vipLevel + ", gift1=" + gift1 + ", gift2=" + gift2 + ", gift3=" + gift3 + ", noVipGiftTime=" + noVipGiftTime + ", vipGiftTime=" + vipGiftTime + ", noMoneyInGame=" + noMoneyInGame + ", buyTimes=" + buyTimes + ",]";
	}
}