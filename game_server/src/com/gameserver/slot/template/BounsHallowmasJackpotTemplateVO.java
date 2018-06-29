package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * BounsHallowmasJackpotTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class BounsHallowmasJackpotTemplateVO extends TemplateObject {

	/** 名字id */
	@ExcelCellBinding(offset = 1)
	protected int nameId;

	/** 多语言描述id */
	@ExcelCellBinding(offset = 2)
	protected int descrip;

	/** 老虎机类型 */
	@ExcelCellBinding(offset = 3)
	protected int slotsNum;

	/** 第二次jackpot转出个数 */
	@ExcelCellBinding(offset = 4)
	protected int number;

	/** 彩金获得比例： X/100 */
	@ExcelCellBinding(offset = 5)
	protected int proportion;

	/** 权值 */
	@ExcelCellBinding(offset = 6)
	protected int weight;


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
	
	public int getSlotsNum() {
		return this.slotsNum;
	}


	public final static int getSlotsNumMinLimit() {
		return 0;
	}

	public void setSlotsNum(int slotsNum) {
		if (slotsNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[老虎机类型]slotsNum的值不得小于0");
		}
		this.slotsNum = slotsNum;
	}
	
	public int getNumber() {
		return this.number;
	}


	public final static int getNumberMinLimit() {
		return 0;
	}

	public void setNumber(int number) {
		if (number < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[第二次jackpot转出个数]number的值不得小于0");
		}
		this.number = number;
	}
	
	public int getProportion() {
		return this.proportion;
	}


	public final static int getProportionMinLimit() {
		return 0;
	}

	public void setProportion(int proportion) {
		if (proportion < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[彩金获得比例： X/100]proportion的值不得小于0");
		}
		this.proportion = proportion;
	}
	
	public int getWeight() {
		return this.weight;
	}


	public final static int getWeightMinLimit() {
		return 0;
	}

	public void setWeight(int weight) {
		if (weight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[权值]weight的值不得小于0");
		}
		this.weight = weight;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, BounsHallowmasJackpotTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends BounsHallowmasJackpotTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, BounsHallowmasJackpotTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "BounsHallowmasJackpotTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", slotsNum=" + slotsNum + ", number=" + number + ", proportion=" + proportion + ", weight=" + weight + ",]";
	}
}