package com.gameserver.texas.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * interactiveItem
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class SngMatchTemplateVO extends TemplateObject {

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

	/** 房间是否开启 */
	@ExcelCellBinding(offset = 5)
	protected int openUp;

	/** 房间人数 */
	@ExcelCellBinding(offset = 6)
	protected int roomNum;

	/** dealer cost */
	@ExcelCellBinding(offset = 7)
	protected int dealerCost;

	/** 房间数 */
	@ExcelCellBinding(offset = 8)
	protected int openRoomNum;

	/** 入场费 */
	@ExcelCellBinding(offset = 9)
	protected int entryFee;

	/** 服务费 */
	@ExcelCellBinding(offset = 10)
	protected int serviceFee;

	/** 金杯筹码奖励 */
	@ExcelCellBinding(offset = 11)
	protected int chipsReward1;

	/** 金杯分数奖励 */
	@ExcelCellBinding(offset = 12)
	protected int scoreReward1;

	/** 银杯筹码奖励 */
	@ExcelCellBinding(offset = 13)
	protected int chipsReward2;

	/** 银杯分数奖励 */
	@ExcelCellBinding(offset = 14)
	protected int scoreReward2;

	/** 起始筹码 */
	@ExcelCellBinding(offset = 15)
	protected int initialChips;

	/** 门票id */
	@ExcelCellBinding(offset = 16)
	protected int ticketId;


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
	
	public int getOpenUp() {
		return this.openUp;
	}


	public final static int getOpenUpMinLimit() {
		return 0;
	}

	public void setOpenUp(int openUp) {
		if (openUp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[房间是否开启]openUp的值不得小于0");
		}
		this.openUp = openUp;
	}
	
	public int getRoomNum() {
		return this.roomNum;
	}


	public final static int getRoomNumMinLimit() {
		return 1;
	}

	public void setRoomNum(int roomNum) {
		if (roomNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[房间人数]roomNum的值不得小于1");
		}
		this.roomNum = roomNum;
	}
	
	public int getDealerCost() {
		return this.dealerCost;
	}


	public final static int getDealerCostMinLimit() {
		return 1;
	}

	public void setDealerCost(int dealerCost) {
		if (dealerCost < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[dealer cost]dealerCost的值不得小于1");
		}
		this.dealerCost = dealerCost;
	}
	
	public int getOpenRoomNum() {
		return this.openRoomNum;
	}


	public final static int getOpenRoomNumMinLimit() {
		return 0;
	}

	public void setOpenRoomNum(int openRoomNum) {
		if (openRoomNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[房间数]openRoomNum的值不得小于0");
		}
		this.openRoomNum = openRoomNum;
	}
	
	public int getEntryFee() {
		return this.entryFee;
	}


	public final static int getEntryFeeMinLimit() {
		return 1;
	}

	public void setEntryFee(int entryFee) {
		if (entryFee < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[入场费]entryFee的值不得小于1");
		}
		this.entryFee = entryFee;
	}
	
	public int getServiceFee() {
		return this.serviceFee;
	}


	public final static int getServiceFeeMinLimit() {
		return 1;
	}

	public void setServiceFee(int serviceFee) {
		if (serviceFee < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[服务费]serviceFee的值不得小于1");
		}
		this.serviceFee = serviceFee;
	}
	
	public int getChipsReward1() {
		return this.chipsReward1;
	}


	public final static int getChipsReward1MinLimit() {
		return 1;
	}

	public void setChipsReward1(int chipsReward1) {
		if (chipsReward1 < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[金杯筹码奖励]chipsReward1的值不得小于1");
		}
		this.chipsReward1 = chipsReward1;
	}
	
	public int getScoreReward1() {
		return this.scoreReward1;
	}


	public final static int getScoreReward1MinLimit() {
		return 1;
	}

	public void setScoreReward1(int scoreReward1) {
		if (scoreReward1 < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[金杯分数奖励]scoreReward1的值不得小于1");
		}
		this.scoreReward1 = scoreReward1;
	}
	
	public int getChipsReward2() {
		return this.chipsReward2;
	}


	public final static int getChipsReward2MinLimit() {
		return 1;
	}

	public void setChipsReward2(int chipsReward2) {
		if (chipsReward2 < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[银杯筹码奖励]chipsReward2的值不得小于1");
		}
		this.chipsReward2 = chipsReward2;
	}
	
	public int getScoreReward2() {
		return this.scoreReward2;
	}


	public final static int getScoreReward2MinLimit() {
		return 1;
	}

	public void setScoreReward2(int scoreReward2) {
		if (scoreReward2 < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[银杯分数奖励]scoreReward2的值不得小于1");
		}
		this.scoreReward2 = scoreReward2;
	}
	
	public int getInitialChips() {
		return this.initialChips;
	}


	public final static int getInitialChipsMinLimit() {
		return 1;
	}

	public void setInitialChips(int initialChips) {
		if (initialChips < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[起始筹码]initialChips的值不得小于1");
		}
		this.initialChips = initialChips;
	}
	
	public int getTicketId() {
		return this.ticketId;
	}


	public final static int getTicketIdMinLimit() {
		return 1;
	}

	public void setTicketId(int ticketId) {
		if (ticketId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[门票id]ticketId的值不得小于1");
		}
		this.ticketId = ticketId;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, SngMatchTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends SngMatchTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, SngMatchTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "SngMatchTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", openUp=" + openUp + ", roomNum=" + roomNum + ", dealerCost=" + dealerCost + ", openRoomNum=" + openRoomNum + ", entryFee=" + entryFee + ", serviceFee=" + serviceFee + ", chipsReward1=" + chipsReward1 + ", scoreReward1=" + scoreReward1 + ", chipsReward2=" + chipsReward2 + ", scoreReward2=" + scoreReward2 + ", initialChips=" + initialChips + ", ticketId=" + ticketId + ",]";
	}
}