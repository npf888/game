package com.gameserver.gift.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * NewComerTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class NewComerTemplateVO extends TemplateObject {

	/** 礼包购买限时 单位,秒 */
	@ExcelCellBinding(offset = 1)
	protected int timeLimit;

	/** 礼包购买限时开关  0,开  1,关 */
	@ExcelCellBinding(offset = 2)
	protected int type;

	/** 判定玩家游戏天数 单位,天 */
	@ExcelCellBinding(offset = 3)
	protected int gameTime1;

	/** 判定玩家游戏天数  单位,天 */
	@ExcelCellBinding(offset = 4)
	protected int gameTime2;

	/** 对应recharge表内pid值 */
	@ExcelCellBinding(offset = 5)
	protected int pid;


	public int getTimeLimit() {
		return this.timeLimit;
	}



	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public int getType() {
		return this.type;
	}



	public void setType(int type) {
		this.type = type;
	}
	
	public int getGameTime1() {
		return this.gameTime1;
	}



	public void setGameTime1(int gameTime1) {
		this.gameTime1 = gameTime1;
	}
	
	public int getGameTime2() {
		return this.gameTime2;
	}



	public void setGameTime2(int gameTime2) {
		this.gameTime2 = gameTime2;
	}
	
	public int getPid() {
		return this.pid;
	}



	public void setPid(int pid) {
		this.pid = pid;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, NewComerTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends NewComerTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, NewComerTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "NewComerTemplateVO [  timeLimit=" + timeLimit + ", type=" + type + ", gameTime1=" + gameTime1 + ", gameTime2=" + gameTime2 + ", pid=" + pid + ",]";
	}
}