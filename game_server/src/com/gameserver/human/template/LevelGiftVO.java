package com.gameserver.human.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * levelGift.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class LevelGiftVO extends TemplateObject {

	/** 达到等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** 奖励ID 3为金钱 */
	@ExcelCellBinding(offset = 2)
	protected int reward1Id;

	/** 金钱数1 */
	@ExcelCellBinding(offset = 3)
	protected int reward1Num;

	/** 奖励ID 3为金钱 */
	@ExcelCellBinding(offset = 4)
	protected int reward2Id;

	/** 金钱数2 */
	@ExcelCellBinding(offset = 5)
	protected int reward2Num;

	/** 奖励ID 3为金钱 */
	@ExcelCellBinding(offset = 6)
	protected int reward3Id;

	/** 金钱数3 */
	@ExcelCellBinding(offset = 7)
	protected int reward3Num;


	public int getLevel() {
		return this.level;
	}



	public void setLevel(int level) {
		this.level = level;
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
	
	public int getReward3Id() {
		return this.reward3Id;
	}



	public void setReward3Id(int reward3Id) {
		this.reward3Id = reward3Id;
	}
	
	public int getReward3Num() {
		return this.reward3Num;
	}



	public void setReward3Num(int reward3Num) {
		this.reward3Num = reward3Num;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, LevelGiftVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends LevelGiftVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, LevelGiftVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "LevelGiftVO [  level=" + level + ", reward1Id=" + reward1Id + ", reward1Num=" + reward1Num + ", reward2Id=" + reward2Id + ", reward2Num=" + reward2Num + ", reward3Id=" + reward3Id + ", reward3Num=" + reward3Num + ",]";
	}
}