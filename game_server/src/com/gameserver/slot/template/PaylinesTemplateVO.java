package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * pay lines
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class PaylinesTemplateVO extends TemplateObject {

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

	/** 下限 */
	@ExcelCellBinding(offset = 5)
	protected int position1;

	/** 下限 */
	@ExcelCellBinding(offset = 6)
	protected int position2;

	/** 下限 */
	@ExcelCellBinding(offset = 7)
	protected int position3;

	/** 下限 */
	@ExcelCellBinding(offset = 8)
	protected int position4;

	/** 下限 */
	@ExcelCellBinding(offset = 9)
	protected int position5;

	/** 连线类型 1：3*5老虎机 2： 4*5老虎机 */
	@ExcelCellBinding(offset = 10)
	protected int lineType;


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
	
	public int getPosition1() {
		return this.position1;
	}


	public final static int getPosition1MinLimit() {
		return 0;
	}

	public void setPosition1(int position1) {
		if (position1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[下限]position1的值不得小于0");
		}
		this.position1 = position1;
	}
	
	public int getPosition2() {
		return this.position2;
	}


	public final static int getPosition2MinLimit() {
		return 0;
	}

	public void setPosition2(int position2) {
		if (position2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[下限]position2的值不得小于0");
		}
		this.position2 = position2;
	}
	
	public int getPosition3() {
		return this.position3;
	}


	public final static int getPosition3MinLimit() {
		return 0;
	}

	public void setPosition3(int position3) {
		if (position3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[下限]position3的值不得小于0");
		}
		this.position3 = position3;
	}
	
	public int getPosition4() {
		return this.position4;
	}


	public final static int getPosition4MinLimit() {
		return 0;
	}

	public void setPosition4(int position4) {
		if (position4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[下限]position4的值不得小于0");
		}
		this.position4 = position4;
	}
	
	public int getPosition5() {
		return this.position5;
	}


	public final static int getPosition5MinLimit() {
		return 0;
	}

	public void setPosition5(int position5) {
		if (position5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[下限]position5的值不得小于0");
		}
		this.position5 = position5;
	}
	
	public int getLineType() {
		return this.lineType;
	}


	public final static int getLineTypeMinLimit() {
		return 0;
	}

	public void setLineType(int lineType) {
		if (lineType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[连线类型 1：3*5老虎机 2： 4*5老虎机]lineType的值不得小于0");
		}
		this.lineType = lineType;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, PaylinesTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends PaylinesTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, PaylinesTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "PaylinesTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", position1=" + position1 + ", position2=" + position2 + ", position3=" + position3 + ", position4=" + position4 + ", position5=" + position5 + ", lineType=" + lineType + ",]";
	}
}