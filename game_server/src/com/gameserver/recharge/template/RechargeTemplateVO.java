package com.gameserver.recharge.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * recharge
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class RechargeTemplateVO extends TemplateObject {

	/** 渠道id */
	@ExcelCellBinding(offset = 1)
	protected int channelId;

	/** pid */
	@ExcelCellBinding(offset = 2)
	protected int pid;

	/** 名字id */
	@ExcelCellBinding(offset = 3)
	protected int nameId;

	/** 描述 */
	@ExcelCellBinding(offset = 4)
	protected int descrip;

	/** 语言描述 */
	@ExcelCellBinding(offset = 5)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 6)
	protected String icon;

	/** 花费数量 */
	@ExcelCellBinding(offset = 7)
	protected int num;

	/** 钻石数量 */
	@ExcelCellBinding(offset = 8)
	protected int itemNum;

	/** 赠送数量 */
	@ExcelCellBinding(offset = 9)
	protected int giftNum;

	/** 产品id */
	@ExcelCellBinding(offset = 10)
	protected String productId;

	/** 是否使用 */
	@ExcelCellBinding(offset = 11)
	protected int used;

	/** mark */
	@ExcelCellBinding(offset = 12)
	protected int mark;

	/** 物品ID */
	@ExcelCellBinding(offset = 13)
	protected int itemId;

	/** 物品类型 */
	@ExcelCellBinding(offset = 14)
	protected int smallCategory;

	/** vip点 */
	@ExcelCellBinding(offset = 15)
	protected int vipPoint;

	/** 类型 */
	@ExcelCellBinding(offset = 16)
	protected int category;

	/** 支付类型1mycard */
	@ExcelCellBinding(offset = 17)
	protected int payType;

	/** 物品类型 */
	@ExcelCellBinding(offset = 18)
	protected int itemType;


	public int getChannelId() {
		return this.channelId;
	}


	public final static int getChannelIdMinLimit() {
		return 0;
	}

	public void setChannelId(int channelId) {
		if (channelId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[渠道id]channelId的值不得小于0");
		}
		this.channelId = channelId;
	}
	
	public int getPid() {
		return this.pid;
	}



	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public int getNameId() {
		return this.nameId;
	}


	public final static int getNameIdMinLimit() {
		return 0;
	}

	public void setNameId(int nameId) {
		if (nameId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[名字id]nameId的值不得小于0");
		}
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
	
	public int getNum() {
		return this.num;
	}


	public final static int getNumMinLimit() {
		return 1;
	}

	public void setNum(int num) {
		if (num < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[花费数量]num的值不得小于1");
		}
		this.num = num;
	}
	
	public int getItemNum() {
		return this.itemNum;
	}


	public final static int getItemNumMinLimit() {
		return 1;
	}

	public void setItemNum(int itemNum) {
		if (itemNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[钻石数量]itemNum的值不得小于1");
		}
		this.itemNum = itemNum;
	}
	
	public int getGiftNum() {
		return this.giftNum;
	}


	public final static int getGiftNumMinLimit() {
		return 0;
	}

	public void setGiftNum(int giftNum) {
		if (giftNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[赠送数量]giftNum的值不得小于0");
		}
		this.giftNum = giftNum;
	}
	
	public String getProductId() {
		return this.productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public int getUsed() {
		return this.used;
	}



	public void setUsed(int used) {
		this.used = used;
	}
	
	public int getMark() {
		return this.mark;
	}


	public final static int getMarkMinLimit() {
		return 0;
	}

	public void setMark(int mark) {
		if (mark < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[mark]mark的值不得小于0");
		}
		this.mark = mark;
	}
	
	public int getItemId() {
		return this.itemId;
	}


	public final static int getItemIdMinLimit() {
		return 1;
	}

	public void setItemId(int itemId) {
		if (itemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[物品ID]itemId的值不得小于1");
		}
		this.itemId = itemId;
	}
	
	public int getSmallCategory() {
		return this.smallCategory;
	}


	public final static int getSmallCategoryMinLimit() {
		return 1;
	}

	public void setSmallCategory(int smallCategory) {
		if (smallCategory < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[物品类型]smallCategory的值不得小于1");
		}
		this.smallCategory = smallCategory;
	}
	
	public int getVipPoint() {
		return this.vipPoint;
	}


	public final static int getVipPointMinLimit() {
		return 0;
	}

	public void setVipPoint(int vipPoint) {
		if (vipPoint < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[vip点]vipPoint的值不得小于0");
		}
		this.vipPoint = vipPoint;
	}
	
	public int getCategory() {
		return this.category;
	}


	public final static int getCategoryMinLimit() {
		return 1;
	}

	public void setCategory(int category) {
		if (category < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[类型]category的值不得小于1");
		}
		this.category = category;
	}
	
	public int getPayType() {
		return this.payType;
	}


	public final static int getPayTypeMinLimit() {
		return 0;
	}

	public void setPayType(int payType) {
		if (payType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[支付类型1mycard]payType的值不得小于0");
		}
		this.payType = payType;
	}
	
	public int getItemType() {
		return this.itemType;
	}


	public final static int getItemTypeMinLimit() {
		return 0;
	}

	public void setItemType(int itemType) {
		if (itemType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[物品类型]itemType的值不得小于0");
		}
		this.itemType = itemType;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, RechargeTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends RechargeTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, RechargeTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "RechargeTemplateVO [  channelId=" + channelId + ", pid=" + pid + ", nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", num=" + num + ", itemNum=" + itemNum + ", giftNum=" + giftNum + ", productId=" + productId + ", used=" + used + ", mark=" + mark + ", itemId=" + itemId + ", smallCategory=" + smallCategory + ", vipPoint=" + vipPoint + ", category=" + category + ", payType=" + payType + ", itemType=" + itemType + ",]";
	}
}