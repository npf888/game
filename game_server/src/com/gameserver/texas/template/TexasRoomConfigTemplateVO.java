package com.gameserver.texas.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * 德州配置
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class TexasRoomConfigTemplateVO extends TemplateObject {

	/** 房间等待时间 */
	@ExcelCellBinding(offset = 1)
	protected int waitingTime;

	/** 玩家行为时间 */
	@ExcelCellBinding(offset = 2)
	protected int actTime;

	/** 下盘开始时间 */
	@ExcelCellBinding(offset = 3)
	protected int nextTime;

	/** preflop时间 */
	@ExcelCellBinding(offset = 4)
	protected int preflopTime;

	/** flop时间 */
	@ExcelCellBinding(offset = 5)
	protected int flopTime;

	/** turn时间 */
	@ExcelCellBinding(offset = 6)
	protected int turnTime;

	/** river时间 */
	@ExcelCellBinding(offset = 7)
	protected int riverTime;

	/** 比牌时间 */
	@ExcelCellBinding(offset = 8)
	protected int compareTime;

	/** 结算时间 */
	@ExcelCellBinding(offset = 9)
	protected int settleTime;

	/** 震动时间 */
	@ExcelCellBinding(offset = 10)
	protected int vibrationTime;


	public int getWaitingTime() {
		return this.waitingTime;
	}



	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public int getActTime() {
		return this.actTime;
	}



	public void setActTime(int actTime) {
		this.actTime = actTime;
	}
	
	public int getNextTime() {
		return this.nextTime;
	}



	public void setNextTime(int nextTime) {
		this.nextTime = nextTime;
	}
	
	public int getPreflopTime() {
		return this.preflopTime;
	}



	public void setPreflopTime(int preflopTime) {
		this.preflopTime = preflopTime;
	}
	
	public int getFlopTime() {
		return this.flopTime;
	}



	public void setFlopTime(int flopTime) {
		this.flopTime = flopTime;
	}
	
	public int getTurnTime() {
		return this.turnTime;
	}



	public void setTurnTime(int turnTime) {
		this.turnTime = turnTime;
	}
	
	public int getRiverTime() {
		return this.riverTime;
	}



	public void setRiverTime(int riverTime) {
		this.riverTime = riverTime;
	}
	
	public int getCompareTime() {
		return this.compareTime;
	}



	public void setCompareTime(int compareTime) {
		this.compareTime = compareTime;
	}
	
	public int getSettleTime() {
		return this.settleTime;
	}



	public void setSettleTime(int settleTime) {
		this.settleTime = settleTime;
	}
	
	public int getVibrationTime() {
		return this.vibrationTime;
	}



	public void setVibrationTime(int vibrationTime) {
		this.vibrationTime = vibrationTime;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, TexasRoomConfigTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends TexasRoomConfigTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, TexasRoomConfigTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "TexasRoomConfigTemplateVO [  waitingTime=" + waitingTime + ", actTime=" + actTime + ", nextTime=" + nextTime + ", preflopTime=" + preflopTime + ", flopTime=" + flopTime + ", turnTime=" + turnTime + ", riverTime=" + riverTime + ", compareTime=" + compareTime + ", settleTime=" + settleTime + ", vibrationTime=" + vibrationTime + ",]";
	}
}