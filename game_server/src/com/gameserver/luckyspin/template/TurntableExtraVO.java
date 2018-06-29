package com.gameserver.luckyspin.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * TurntableExtra.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class TurntableExtraVO extends TemplateObject {

	/** 名字id */
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

	/**  */
	@ExcelCellBinding(offset = 5)
	protected int itemId;

	/** 朋友加成 */
	@ExcelCellBinding(offset = 6)
	protected int friendreward;

	/** 等级加成 */
	@ExcelCellBinding(offset = 7)
	protected int levelreward;

	/** vip奖励描述id */
	@ExcelCellBinding(offset = 8)
	protected int vipDescrip;

	/** 好友奖励描述id */
	@ExcelCellBinding(offset = 9)
	protected int friendDescrip;

	/** 等级奖励描述id */
	@ExcelCellBinding(offset = 10)
	protected int levelDescrip;


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
	
	public int getItemId() {
		return this.itemId;
	}



	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public int getFriendreward() {
		return this.friendreward;
	}



	public void setFriendreward(int friendreward) {
		this.friendreward = friendreward;
	}
	
	public int getLevelreward() {
		return this.levelreward;
	}



	public void setLevelreward(int levelreward) {
		this.levelreward = levelreward;
	}
	
	public int getVipDescrip() {
		return this.vipDescrip;
	}



	public void setVipDescrip(int vipDescrip) {
		this.vipDescrip = vipDescrip;
	}
	
	public int getFriendDescrip() {
		return this.friendDescrip;
	}



	public void setFriendDescrip(int friendDescrip) {
		this.friendDescrip = friendDescrip;
	}
	
	public int getLevelDescrip() {
		return this.levelDescrip;
	}



	public void setLevelDescrip(int levelDescrip) {
		this.levelDescrip = levelDescrip;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, TurntableExtraVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends TurntableExtraVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, TurntableExtraVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "TurntableExtraVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", itemId=" + itemId + ", friendreward=" + friendreward + ", levelreward=" + levelreward + ", vipDescrip=" + vipDescrip + ", friendDescrip=" + friendDescrip + ", levelDescrip=" + levelDescrip + ",]";
	}
}