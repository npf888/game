package com.gameserver.bazoo.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * LiarsDiceRoomSignInTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class LiarsDiceRoomSignInTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 多语言描述 */
	@ExcelCellBinding(offset = 3)
	protected String langDesc;

	/** 名称的  int值 */
	@ExcelCellBinding(offset = 4)
	protected int nameInt;

	/** 对应提供的金币 */
	@ExcelCellBinding(offset = 5)
	protected int offerGold;


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
	
	public int getNameInt() {
		return this.nameInt;
	}



	public void setNameInt(int nameInt) {
		this.nameInt = nameInt;
	}
	
	public int getOfferGold() {
		return this.offerGold;
	}



	public void setOfferGold(int offerGold) {
		this.offerGold = offerGold;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, LiarsDiceRoomSignInTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends LiarsDiceRoomSignInTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, LiarsDiceRoomSignInTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "LiarsDiceRoomSignInTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", nameInt=" + nameInt + ", offerGold=" + offerGold + ",]";
	}
}