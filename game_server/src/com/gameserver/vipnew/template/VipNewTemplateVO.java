package com.gameserver.vipnew.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * vipnew
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class VipNewTemplateVO extends TemplateObject {

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

	/** vip最大点 */
	@ExcelCellBinding(offset = 6)
	protected int vipPoint;

	/** 是否开启表情 */
	@ExcelCellBinding(offset = 7)
	protected int expression;

	/** vip房间 */
	@ExcelCellBinding(offset = 8)
	protected int vipRoom;

	/** 连赢次数 */
	@ExcelCellBinding(offset = 9)
	protected int winTimes;

	/** 购买加成比例 */
	@ExcelCellBinding(offset = 10)
	protected float buyingRatio;

	/** vip点数加成 */
	@ExcelCellBinding(offset = 11)
	protected int vipPointRatio;

	/** 签到加成 */
	@ExcelCellBinding(offset = 12)
	protected int siginRatio;

	/** 百家乐明灯复活权限 */
	@ExcelCellBinding(offset = 13)
	protected int beaconRevive;

	/** vip加成系数 */
	@ExcelCellBinding(offset = 14)
	protected int dailyreward;


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
		this.vipLv = vipLv;
	}
	
	public int getVipPoint() {
		return this.vipPoint;
	}



	public void setVipPoint(int vipPoint) {
		this.vipPoint = vipPoint;
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
	
	public float getBuyingRatio() {
		return this.buyingRatio;
	}



	public void setBuyingRatio(float buyingRatio) {
		this.buyingRatio = buyingRatio;
	}
	
	public int getVipPointRatio() {
		return this.vipPointRatio;
	}



	public void setVipPointRatio(int vipPointRatio) {
		this.vipPointRatio = vipPointRatio;
	}
	
	public int getSiginRatio() {
		return this.siginRatio;
	}



	public void setSiginRatio(int siginRatio) {
		this.siginRatio = siginRatio;
	}
	
	public int getBeaconRevive() {
		return this.beaconRevive;
	}



	public void setBeaconRevive(int beaconRevive) {
		this.beaconRevive = beaconRevive;
	}
	
	public int getDailyreward() {
		return this.dailyreward;
	}



	public void setDailyreward(int dailyreward) {
		this.dailyreward = dailyreward;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, VipNewTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends VipNewTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, VipNewTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "VipNewTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", vipLv=" + vipLv + ", vipPoint=" + vipPoint + ", expression=" + expression + ", vipRoom=" + vipRoom + ", winTimes=" + winTimes + ", buyingRatio=" + buyingRatio + ", vipPointRatio=" + vipPointRatio + ", siginRatio=" + siginRatio + ", beaconRevive=" + beaconRevive + ", dailyreward=" + dailyreward + ",]";
	}
}