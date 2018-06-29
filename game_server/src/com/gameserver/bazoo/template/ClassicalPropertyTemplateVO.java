package com.gameserver.bazoo.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * ClassicalPropertyTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ClassicalPropertyTemplateVO extends TemplateObject {

	/** 1:自己没有顺子且没有叫1点  2:有顺子没叫1点 或者 有叫1点没有顺子   3：自己有顺子且叫了1点 */
	@ExcelCellBinding(offset = 1)
	protected int callType;

	/** 玩游戏的人数 */
	@ExcelCellBinding(offset = 2)
	protected int peopleNum;

	/** 随便叫 哪个点的数量 */
	@ExcelCellBinding(offset = 3)
	protected int pointNum;

	/** 概率 */
	@ExcelCellBinding(offset = 4)
	protected int probability;


	public int getCallType() {
		return this.callType;
	}



	public void setCallType(int callType) {
		this.callType = callType;
	}
	
	public int getPeopleNum() {
		return this.peopleNum;
	}



	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}
	
	public int getPointNum() {
		return this.pointNum;
	}



	public void setPointNum(int pointNum) {
		this.pointNum = pointNum;
	}
	
	public int getProbability() {
		return this.probability;
	}



	public void setProbability(int probability) {
		this.probability = probability;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ClassicalPropertyTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ClassicalPropertyTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ClassicalPropertyTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ClassicalPropertyTemplateVO [  callType=" + callType + ", peopleNum=" + peopleNum + ", pointNum=" + pointNum + ", probability=" + probability + ",]";
	}
}