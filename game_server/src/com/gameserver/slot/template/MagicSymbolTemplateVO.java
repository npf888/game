package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * MagicSymbolTemplate
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class MagicSymbolTemplateVO extends TemplateObject {

	/** 老虎机编号 */
	@ExcelCellBinding(offset = 1)
	protected int slotNum;

	/** 卷轴ID */
	@ExcelCellBinding(offset = 2)
	protected int reel1;

	/** 权重 */
	@ExcelCellBinding(offset = 3)
	protected int value1;

	/** 卷轴ID */
	@ExcelCellBinding(offset = 4)
	protected int reel2;

	/** 权重 */
	@ExcelCellBinding(offset = 5)
	protected int value2;

	/** 卷轴ID */
	@ExcelCellBinding(offset = 6)
	protected int reel3;

	/** 权重 */
	@ExcelCellBinding(offset = 7)
	protected int value3;

	/** 卷轴ID */
	@ExcelCellBinding(offset = 8)
	protected int reel4;

	/** 权重 */
	@ExcelCellBinding(offset = 9)
	protected int value4;

	/** 卷轴ID */
	@ExcelCellBinding(offset = 10)
	protected int reel5;

	/** 权重 */
	@ExcelCellBinding(offset = 11)
	protected int value5;


	public int getSlotNum() {
		return this.slotNum;
	}


	public final static int getSlotNumMinLimit() {
		return 0;
	}

	public void setSlotNum(int slotNum) {
		if (slotNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[老虎机编号]slotNum的值不得小于0");
		}
		this.slotNum = slotNum;
	}
	
	public int getReel1() {
		return this.reel1;
	}


	public final static int getReel1MinLimit() {
		return 0;
	}

	public void setReel1(int reel1) {
		if (reel1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[卷轴ID]reel1的值不得小于0");
		}
		this.reel1 = reel1;
	}
	
	public int getValue1() {
		return this.value1;
	}


	public final static int getValue1MinLimit() {
		return 0;
	}

	public void setValue1(int value1) {
		if (value1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[权重]value1的值不得小于0");
		}
		this.value1 = value1;
	}
	
	public int getReel2() {
		return this.reel2;
	}


	public final static int getReel2MinLimit() {
		return 0;
	}

	public void setReel2(int reel2) {
		if (reel2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[卷轴ID]reel2的值不得小于0");
		}
		this.reel2 = reel2;
	}
	
	public int getValue2() {
		return this.value2;
	}


	public final static int getValue2MinLimit() {
		return 0;
	}

	public void setValue2(int value2) {
		if (value2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[权重]value2的值不得小于0");
		}
		this.value2 = value2;
	}
	
	public int getReel3() {
		return this.reel3;
	}


	public final static int getReel3MinLimit() {
		return 0;
	}

	public void setReel3(int reel3) {
		if (reel3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[卷轴ID]reel3的值不得小于0");
		}
		this.reel3 = reel3;
	}
	
	public int getValue3() {
		return this.value3;
	}


	public final static int getValue3MinLimit() {
		return 0;
	}

	public void setValue3(int value3) {
		if (value3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[权重]value3的值不得小于0");
		}
		this.value3 = value3;
	}
	
	public int getReel4() {
		return this.reel4;
	}


	public final static int getReel4MinLimit() {
		return 0;
	}

	public void setReel4(int reel4) {
		if (reel4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[卷轴ID]reel4的值不得小于0");
		}
		this.reel4 = reel4;
	}
	
	public int getValue4() {
		return this.value4;
	}


	public final static int getValue4MinLimit() {
		return 0;
	}

	public void setValue4(int value4) {
		if (value4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[权重]value4的值不得小于0");
		}
		this.value4 = value4;
	}
	
	public int getReel5() {
		return this.reel5;
	}


	public final static int getReel5MinLimit() {
		return 0;
	}

	public void setReel5(int reel5) {
		if (reel5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[卷轴ID]reel5的值不得小于0");
		}
		this.reel5 = reel5;
	}
	
	public int getValue5() {
		return this.value5;
	}


	public final static int getValue5MinLimit() {
		return 0;
	}

	public void setValue5(int value5) {
		if (value5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[权重]value5的值不得小于0");
		}
		this.value5 = value5;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, MagicSymbolTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends MagicSymbolTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, MagicSymbolTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "MagicSymbolTemplateVO [  slotNum=" + slotNum + ", reel1=" + reel1 + ", value1=" + value1 + ", reel2=" + reel2 + ", value2=" + value2 + ", reel3=" + reel3 + ", value3=" + value3 + ", reel4=" + reel4 + ", value4=" + value4 + ", reel5=" + reel5 + ", value5=" + value5 + ",]";
	}
}