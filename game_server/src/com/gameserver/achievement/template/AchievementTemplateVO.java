package com.gameserver.achievement.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * AchievementTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class AchievementTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** icon */
	@ExcelCellBinding(offset = 4)
	protected String icon;

	/** 客户端类型 */
	@ExcelCellBinding(offset = 5)
	protected int clientType;

	/** 服务器类型 */
	@ExcelCellBinding(offset = 6)
	protected int serverType;

	/** 小类型 */
	@ExcelCellBinding(offset = 7)
	protected int smallType;

	/** 参数类型  1 单个 2 多个 */
	@ExcelCellBinding(offset = 8)
	protected int paramType;

	/** 条件一 */
	@ExcelCellBinding(offset = 9)
	protected int condition1;

	/** 条件二 */
	@ExcelCellBinding(offset = 10)
	protected int condition2;

	/** 奖励类型 */
	@ExcelCellBinding(offset = 11)
	protected int reward1Id;

	/** 奖励数量 */
	@ExcelCellBinding(offset = 12)
	protected int reward1Num;

	/** 奖励类型 */
	@ExcelCellBinding(offset = 13)
	protected int reward2Id;

	/** 奖励数量 */
	@ExcelCellBinding(offset = 14)
	protected int reward2Num;


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
		if (StringUtils.isEmpty(langDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[多语言描述]langDesc不可以为空");
		}
		this.langDesc = langDesc;
	}
	
	public String getIcon() {
		return this.icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getClientType() {
		return this.clientType;
	}



	public void setClientType(int clientType) {
		this.clientType = clientType;
	}
	
	public int getServerType() {
		return this.serverType;
	}



	public void setServerType(int serverType) {
		this.serverType = serverType;
	}
	
	public int getSmallType() {
		return this.smallType;
	}



	public void setSmallType(int smallType) {
		this.smallType = smallType;
	}
	
	public int getParamType() {
		return this.paramType;
	}



	public void setParamType(int paramType) {
		this.paramType = paramType;
	}
	
	public int getCondition1() {
		return this.condition1;
	}



	public void setCondition1(int condition1) {
		this.condition1 = condition1;
	}
	
	public int getCondition2() {
		return this.condition2;
	}



	public void setCondition2(int condition2) {
		this.condition2 = condition2;
	}
	
	public int getReward1Id() {
		return this.reward1Id;
	}



	public void setReward1Id(int reward1Id) {
		this.reward1Id = reward1Id;
	}
	
	public int getReward1Num() {
		return this.reward1Num;
	}



	public void setReward1Num(int reward1Num) {
		this.reward1Num = reward1Num;
	}
	
	public int getReward2Id() {
		return this.reward2Id;
	}



	public void setReward2Id(int reward2Id) {
		this.reward2Id = reward2Id;
	}
	
	public int getReward2Num() {
		return this.reward2Num;
	}



	public void setReward2Num(int reward2Num) {
		this.reward2Num = reward2Num;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, AchievementTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends AchievementTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, AchievementTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "AchievementTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", clientType=" + clientType + ", serverType=" + serverType + ", smallType=" + smallType + ", paramType=" + paramType + ", condition1=" + condition1 + ", condition2=" + condition2 + ", reward1Id=" + reward1Id + ", reward1Num=" + reward1Num + ", reward2Id=" + reward2Id + ", reward2Num=" + reward2Num + ",]";
	}
}