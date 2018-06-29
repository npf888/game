package com.gameserver.human.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * CouponTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class CouponTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 第二大分类 */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 优惠券有效期（秒） */
	@ExcelCellBinding(offset = 5)
	protected int couponDuration;

	/** 优惠券额外筹码百分比  获得筹码数 = 首充翻倍、VIP翻倍后的筹码数 X （1+ 优惠券额外筹码百分比/100 ) */
	@ExcelCellBinding(offset = 6)
	protected int couponExtraChip;

	/** 权重 */
	@ExcelCellBinding(offset = 7)
	protected int weight;

	/** 对应item表里的id; 一一对应 */
	@ExcelCellBinding(offset = 8)
	protected int itemID;


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
	
	public int getCouponDuration() {
		return this.couponDuration;
	}



	public void setCouponDuration(int couponDuration) {
		this.couponDuration = couponDuration;
	}
	
	public int getCouponExtraChip() {
		return this.couponExtraChip;
	}



	public void setCouponExtraChip(int couponExtraChip) {
		this.couponExtraChip = couponExtraChip;
	}
	
	public int getWeight() {
		return this.weight;
	}



	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getItemID() {
		return this.itemID;
	}



	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, CouponTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends CouponTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, CouponTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "CouponTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", couponDuration=" + couponDuration + ", couponExtraChip=" + couponExtraChip + ", weight=" + weight + ", itemID=" + itemID + ",]";
	}
}