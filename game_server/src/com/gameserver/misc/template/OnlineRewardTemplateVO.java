package com.gameserver.misc.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * 在线奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class OnlineRewardTemplateVO extends TemplateObject {

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

	/** 在线时长 */
	@ExcelCellBinding(offset = 5)
	protected int onlineTime;

	/** 奖励id */
	@ExcelCellBinding(offset = 6)
	protected int rewardId;

	/** 奖励数量 */
	@ExcelCellBinding(offset = 7)
	protected int rewardNum;


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
	
	public int getOnlineTime() {
		return this.onlineTime;
	}



	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
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
	

	/** 模板字典 */
	protected final static Map<Integer, OnlineRewardTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends OnlineRewardTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, OnlineRewardTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "OnlineRewardTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", onlineTime=" + onlineTime + ", rewardId=" + rewardId + ", rewardNum=" + rewardNum + ",]";
	}
}