package com.gameserver.vip.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * vip
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class VipTemplateVO extends TemplateObject {

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
	protected int vipLv;

	/** 花费钻石 */
	@ExcelCellBinding(offset = 6)
	protected int costDiamonds;

	/** 筹码 */
	@ExcelCellBinding(offset = 7)
	protected int giftChips;

	/** 赠送经验 */
	@ExcelCellBinding(offset = 8)
	protected int giftExp;

	/** 经验速度 */
	@ExcelCellBinding(offset = 9)
	protected int expUp;

	/** 魅力值 */
	@ExcelCellBinding(offset = 10)
	protected int charmValue;

	/** 每天奖励 */
	@ExcelCellBinding(offset = 11)
	protected int dailyReward;

	/** 表情是否开启 */
	@ExcelCellBinding(offset = 12)
	protected int expression;

	/** vip房间 */
	@ExcelCellBinding(offset = 13)
	protected int vipRoom;

	/** 连赢次数 */
	@ExcelCellBinding(offset = 14)
	protected int winTimes;


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
	
	public int getVipLv() {
		return this.vipLv;
	}



	public void setVipLv(int vipLv) {
		if (vipLv == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[vip等级]vipLv不可以为0");
		}
		this.vipLv = vipLv;
	}
	
	public int getCostDiamonds() {
		return this.costDiamonds;
	}



	public void setCostDiamonds(int costDiamonds) {
		if (costDiamonds == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[花费钻石]costDiamonds不可以为0");
		}
		this.costDiamonds = costDiamonds;
	}
	
	public int getGiftChips() {
		return this.giftChips;
	}



	public void setGiftChips(int giftChips) {
		if (giftChips == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[筹码]giftChips不可以为0");
		}
		this.giftChips = giftChips;
	}
	
	public int getGiftExp() {
		return this.giftExp;
	}



	public void setGiftExp(int giftExp) {
		if (giftExp == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[赠送经验]giftExp不可以为0");
		}
		this.giftExp = giftExp;
	}
	
	public int getExpUp() {
		return this.expUp;
	}



	public void setExpUp(int expUp) {
		if (expUp == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[经验速度]expUp不可以为0");
		}
		this.expUp = expUp;
	}
	
	public int getCharmValue() {
		return this.charmValue;
	}



	public void setCharmValue(int charmValue) {
		if (charmValue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[魅力值]charmValue不可以为0");
		}
		this.charmValue = charmValue;
	}
	
	public int getDailyReward() {
		return this.dailyReward;
	}



	public void setDailyReward(int dailyReward) {
		if (dailyReward == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[每天奖励]dailyReward不可以为0");
		}
		this.dailyReward = dailyReward;
	}
	
	public int getExpression() {
		return this.expression;
	}



	public void setExpression(int expression) {
		this.expression = expression;
	}
	
	public int getVipRoom() {
		return this.vipRoom;
	}



	public void setVipRoom(int vipRoom) {
		this.vipRoom = vipRoom;
	}
	
	public int getWinTimes() {
		return this.winTimes;
	}



	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, VipTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends VipTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, VipTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "VipTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", vipLv=" + vipLv + ", costDiamonds=" + costDiamonds + ", giftChips=" + giftChips + ", giftExp=" + giftExp + ", expUp=" + expUp + ", charmValue=" + charmValue + ", dailyReward=" + dailyReward + ", expression=" + expression + ", vipRoom=" + vipRoom + ", winTimes=" + winTimes + ",]";
	}
}