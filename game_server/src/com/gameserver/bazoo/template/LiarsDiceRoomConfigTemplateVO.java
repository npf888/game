package com.gameserver.bazoo.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * LiarsDiceRoomConfigTemplate.txt
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class LiarsDiceRoomConfigTemplateVO extends TemplateObject {

	/** 玩家等待开局时间（单位秒） */
	@ExcelCellBinding(offset = 1)
	protected int waitingTime;

	/** 轮到谁开始摇的时间（单位秒） */
	@ExcelCellBinding(offset = 2)
	protected int whoTurnTime;

	/** 行动时间 */
	@ExcelCellBinding(offset = 3)
	protected int actTime;

	/** 下局开始时间 */
	@ExcelCellBinding(offset = 4)
	protected int nextTime;

	/** 发牌时间 */
	@ExcelCellBinding(offset = 5)
	protected int preflopTime;

	/** 竞猜时间，单位秒 */
	@ExcelCellBinding(offset = 6)
	protected int guessTime;

	/** 最后结算比牌时间 */
	@ExcelCellBinding(offset = 7)
	protected int compareTime;

	/** 振动时间，单位毫秒 */
	@ExcelCellBinding(offset = 8)
	protected int vibrationTime;

	/** 抢开之后，竞猜之前 有动画要做，所以要等待 一段时间 */
	@ExcelCellBinding(offset = 9)
	protected int guessBeforeTime;

	/** 牛牛模式 统一摇完色子之后 到 重摇色子之前 */
	@ExcelCellBinding(offset = 10)
	protected int cowSwingToBegin;

	/** 牛牛模式 一局 的时间，也是重摇的时间 */
	@ExcelCellBinding(offset = 11)
	protected int cowOneRoundTime;

	/** 牛牛模式 一局 用户 重摇结束到 结算之前 会有段时间 去看别人的色子 */
	@ExcelCellBinding(offset = 12)
	protected int cowLookDiceValueTime;

	/** 牛牛模式 结算时间 */
	@ExcelCellBinding(offset = 13)
	protected int cowEndCountTime;

	/** 梭哈模式 该轮到谁摇 色子 */
	@ExcelCellBinding(offset = 14)
	protected int showHandWhoTurn;

	/** 梭哈模式 摇色子的时间 */
	@ExcelCellBinding(offset = 15)
	protected int showHandShakeTime;

	/** 梭哈模式 结束到开始 */
	@ExcelCellBinding(offset = 16)
	protected int showHandEndToStart;

	/** 红黑单双模式 开始等待时间 */
	@ExcelCellBinding(offset = 17)
	protected int blackWhiteBeginWaitTime;

	/** 红黑单双模式 摇剩余色子等待时间 */
	@ExcelCellBinding(offset = 18)
	protected int blackWhiteLeftWaitTime;

	/** 红黑单双模式 重摇玩 到 该谁叫号 了 的等待时间 */
	@ExcelCellBinding(offset = 19)
	protected int blackWhiteWhoTurnTime;

	/** 红黑单双模式  等待用户叫号的时间 */
	@ExcelCellBinding(offset = 20)
	protected int blackWhiteNextTime;

	/** 红黑单双模式 结束到开始 */
	@ExcelCellBinding(offset = 21)
	protected int blackWhiteEndWaitTime;


	public int getWaitingTime() {
		return this.waitingTime;
	}



	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public int getWhoTurnTime() {
		return this.whoTurnTime;
	}



	public void setWhoTurnTime(int whoTurnTime) {
		this.whoTurnTime = whoTurnTime;
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
	
	public int getGuessTime() {
		return this.guessTime;
	}



	public void setGuessTime(int guessTime) {
		this.guessTime = guessTime;
	}
	
	public int getCompareTime() {
		return this.compareTime;
	}



	public void setCompareTime(int compareTime) {
		this.compareTime = compareTime;
	}
	
	public int getVibrationTime() {
		return this.vibrationTime;
	}



	public void setVibrationTime(int vibrationTime) {
		this.vibrationTime = vibrationTime;
	}
	
	public int getGuessBeforeTime() {
		return this.guessBeforeTime;
	}



	public void setGuessBeforeTime(int guessBeforeTime) {
		this.guessBeforeTime = guessBeforeTime;
	}
	
	public int getCowSwingToBegin() {
		return this.cowSwingToBegin;
	}



	public void setCowSwingToBegin(int cowSwingToBegin) {
		this.cowSwingToBegin = cowSwingToBegin;
	}
	
	public int getCowOneRoundTime() {
		return this.cowOneRoundTime;
	}



	public void setCowOneRoundTime(int cowOneRoundTime) {
		this.cowOneRoundTime = cowOneRoundTime;
	}
	
	public int getCowLookDiceValueTime() {
		return this.cowLookDiceValueTime;
	}



	public void setCowLookDiceValueTime(int cowLookDiceValueTime) {
		this.cowLookDiceValueTime = cowLookDiceValueTime;
	}
	
	public int getCowEndCountTime() {
		return this.cowEndCountTime;
	}



	public void setCowEndCountTime(int cowEndCountTime) {
		this.cowEndCountTime = cowEndCountTime;
	}
	
	public int getShowHandWhoTurn() {
		return this.showHandWhoTurn;
	}



	public void setShowHandWhoTurn(int showHandWhoTurn) {
		this.showHandWhoTurn = showHandWhoTurn;
	}
	
	public int getShowHandShakeTime() {
		return this.showHandShakeTime;
	}



	public void setShowHandShakeTime(int showHandShakeTime) {
		this.showHandShakeTime = showHandShakeTime;
	}
	
	public int getShowHandEndToStart() {
		return this.showHandEndToStart;
	}



	public void setShowHandEndToStart(int showHandEndToStart) {
		this.showHandEndToStart = showHandEndToStart;
	}
	
	public int getBlackWhiteBeginWaitTime() {
		return this.blackWhiteBeginWaitTime;
	}



	public void setBlackWhiteBeginWaitTime(int blackWhiteBeginWaitTime) {
		this.blackWhiteBeginWaitTime = blackWhiteBeginWaitTime;
	}
	
	public int getBlackWhiteLeftWaitTime() {
		return this.blackWhiteLeftWaitTime;
	}



	public void setBlackWhiteLeftWaitTime(int blackWhiteLeftWaitTime) {
		this.blackWhiteLeftWaitTime = blackWhiteLeftWaitTime;
	}
	
	public int getBlackWhiteWhoTurnTime() {
		return this.blackWhiteWhoTurnTime;
	}



	public void setBlackWhiteWhoTurnTime(int blackWhiteWhoTurnTime) {
		this.blackWhiteWhoTurnTime = blackWhiteWhoTurnTime;
	}
	
	public int getBlackWhiteNextTime() {
		return this.blackWhiteNextTime;
	}



	public void setBlackWhiteNextTime(int blackWhiteNextTime) {
		this.blackWhiteNextTime = blackWhiteNextTime;
	}
	
	public int getBlackWhiteEndWaitTime() {
		return this.blackWhiteEndWaitTime;
	}



	public void setBlackWhiteEndWaitTime(int blackWhiteEndWaitTime) {
		this.blackWhiteEndWaitTime = blackWhiteEndWaitTime;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, LiarsDiceRoomConfigTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends LiarsDiceRoomConfigTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, LiarsDiceRoomConfigTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "LiarsDiceRoomConfigTemplateVO [  waitingTime=" + waitingTime + ", whoTurnTime=" + whoTurnTime + ", actTime=" + actTime + ", nextTime=" + nextTime + ", preflopTime=" + preflopTime + ", guessTime=" + guessTime + ", compareTime=" + compareTime + ", vibrationTime=" + vibrationTime + ", guessBeforeTime=" + guessBeforeTime + ", cowSwingToBegin=" + cowSwingToBegin + ", cowOneRoundTime=" + cowOneRoundTime + ", cowLookDiceValueTime=" + cowLookDiceValueTime + ", cowEndCountTime=" + cowEndCountTime + ", showHandWhoTurn=" + showHandWhoTurn + ", showHandShakeTime=" + showHandShakeTime + ", showHandEndToStart=" + showHandEndToStart + ", blackWhiteBeginWaitTime=" + blackWhiteBeginWaitTime + ", blackWhiteLeftWaitTime=" + blackWhiteLeftWaitTime + ", blackWhiteWhoTurnTime=" + blackWhiteWhoTurnTime + ", blackWhiteNextTime=" + blackWhiteNextTime + ", blackWhiteEndWaitTime=" + blackWhiteEndWaitTime + ",]";
	}
}