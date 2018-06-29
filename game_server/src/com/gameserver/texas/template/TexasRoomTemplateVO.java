package com.gameserver.texas.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * 德州房间
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class TexasRoomTemplateVO extends TemplateObject {

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

	/** 房间类型 */
	@ExcelCellBinding(offset = 5)
	protected int roomType;

	/** 房间是否开启 */
	@ExcelCellBinding(offset = 6)
	protected int openUp;

	/** 小盲注 */
	@ExcelCellBinding(offset = 7)
	protected int smallBlind;

	/** 最小携带量 */
	@ExcelCellBinding(offset = 8)
	protected int minCarry;

	/** 最大携带量 */
	@ExcelCellBinding(offset = 9)
	protected int maxCarry;

	/** 池底抽水系数 *10000 */
	@ExcelCellBinding(offset = 10)
	protected int serviceValue1;

	/** 盲注倍数抽水系数*10000 */
	@ExcelCellBinding(offset = 11)
	protected int serviceValue2;

	/** 胜利的经验 */
	@ExcelCellBinding(offset = 12)
	protected int victoryExp;

	/** 失败经验 */
	@ExcelCellBinding(offset = 13)
	protected int joinExp;

	/** 观看人数 */
	@ExcelCellBinding(offset = 14)
	protected int watchNum;

	/** 座位 */
	@ExcelCellBinding(offset = 15)
	protected int roomNum;

	/** 打赏 */
	@ExcelCellBinding(offset = 16)
	protected int dealerCost;

	/** 房间数 */
	@ExcelCellBinding(offset = 17)
	protected int openRoomNum;

	/** 最小卡 */
	@ExcelCellBinding(offset = 18)
	protected int smallCard;

	/** 最合适的人数 */
	@ExcelCellBinding(offset = 19)
	protected int suitNum;

	/** 显示人数 */
	@ExcelCellBinding(offset = 20)
	protected int displayNum;

	/** 是否开启机器人 */
	@ExcelCellBinding(offset = 21)
	protected int operRb;

	/** 进入等级 */
	@ExcelCellBinding(offset = 22)
	protected int openLv;

	/** 显示类型 */
	@ExcelCellBinding(offset = 23)
	protected int list;

	/** 彩金池初始值 */
	@ExcelCellBinding(offset = 24)
	protected int jackpotOriValue;

	/** 彩金池初玩家抽水抽成比例参数 */
	@ExcelCellBinding(offset = 25)
	protected int jackpotPoolPer;

	/** 彩金累计池初玩家抽水抽成比例参数 */
	@ExcelCellBinding(offset = 26)
	protected int jackpotAddPoolPer;


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
	
	public int getRoomType() {
		return this.roomType;
	}


	public final static int getRoomTypeMinLimit() {
		return 0;
	}

	public void setRoomType(int roomType) {
		if (roomType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[房间类型]roomType的值不得小于0");
		}
		this.roomType = roomType;
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
					7, "[房间是否开启]openUp的值不得小于0");
		}
		this.openUp = openUp;
	}
	
	public int getSmallBlind() {
		return this.smallBlind;
	}


	public final static int getSmallBlindMinLimit() {
		return 1;
	}

	public void setSmallBlind(int smallBlind) {
		if (smallBlind < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[小盲注]smallBlind的值不得小于1");
		}
		this.smallBlind = smallBlind;
	}
	
	public int getMinCarry() {
		return this.minCarry;
	}


	public final static int getMinCarryMinLimit() {
		return 1;
	}

	public void setMinCarry(int minCarry) {
		if (minCarry < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[最小携带量]minCarry的值不得小于1");
		}
		this.minCarry = minCarry;
	}
	
	public int getMaxCarry() {
		return this.maxCarry;
	}


	public final static int getMaxCarryMinLimit() {
		return 1;
	}

	public void setMaxCarry(int maxCarry) {
		if (maxCarry < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[最大携带量]maxCarry的值不得小于1");
		}
		this.maxCarry = maxCarry;
	}
	
	public int getServiceValue1() {
		return this.serviceValue1;
	}


	public final static int getServiceValue1MinLimit() {
		return 0;
	}

	public void setServiceValue1(int serviceValue1) {
		if (serviceValue1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[池底抽水系数 *10000]serviceValue1的值不得小于0");
		}
		this.serviceValue1 = serviceValue1;
	}
	
	public int getServiceValue2() {
		return this.serviceValue2;
	}


	public final static int getServiceValue2MinLimit() {
		return 0;
	}

	public void setServiceValue2(int serviceValue2) {
		if (serviceValue2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[盲注倍数抽水系数*10000]serviceValue2的值不得小于0");
		}
		this.serviceValue2 = serviceValue2;
	}
	
	public int getVictoryExp() {
		return this.victoryExp;
	}


	public final static int getVictoryExpMinLimit() {
		return 0;
	}

	public void setVictoryExp(int victoryExp) {
		if (victoryExp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[胜利的经验]victoryExp的值不得小于0");
		}
		this.victoryExp = victoryExp;
	}
	
	public int getJoinExp() {
		return this.joinExp;
	}


	public final static int getJoinExpMinLimit() {
		return 0;
	}

	public void setJoinExp(int joinExp) {
		if (joinExp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[失败经验]joinExp的值不得小于0");
		}
		this.joinExp = joinExp;
	}
	
	public int getWatchNum() {
		return this.watchNum;
	}


	public final static int getWatchNumMinLimit() {
		return 0;
	}

	public void setWatchNum(int watchNum) {
		if (watchNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[观看人数]watchNum的值不得小于0");
		}
		this.watchNum = watchNum;
	}
	
	public int getRoomNum() {
		return this.roomNum;
	}


	public final static int getRoomNumMinLimit() {
		return 0;
	}

	public void setRoomNum(int roomNum) {
		if (roomNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[座位]roomNum的值不得小于0");
		}
		this.roomNum = roomNum;
	}
	
	public int getDealerCost() {
		return this.dealerCost;
	}


	public final static int getDealerCostMinLimit() {
		return 0;
	}

	public void setDealerCost(int dealerCost) {
		if (dealerCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[打赏]dealerCost的值不得小于0");
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
					18, "[房间数]openRoomNum的值不得小于0");
		}
		this.openRoomNum = openRoomNum;
	}
	
	public int getSmallCard() {
		return this.smallCard;
	}


	public final static int getSmallCardMinLimit() {
		return 2;
	}

	public void setSmallCard(int smallCard) {
		if (smallCard < 2) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[最小卡]smallCard的值不得小于2");
		}
		this.smallCard = smallCard;
	}
	
	public int getSuitNum() {
		return this.suitNum;
	}



	public void setSuitNum(int suitNum) {
		this.suitNum = suitNum;
	}
	
	public int getDisplayNum() {
		return this.displayNum;
	}


	public final static int getDisplayNumMinLimit() {
		return 0;
	}

	public void setDisplayNum(int displayNum) {
		if (displayNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[显示人数]displayNum的值不得小于0");
		}
		this.displayNum = displayNum;
	}
	
	public int getOperRb() {
		return this.operRb;
	}


	public final static int getOperRbMinLimit() {
		return 0;
	}

	public void setOperRb(int operRb) {
		if (operRb < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[是否开启机器人]operRb的值不得小于0");
		}
		this.operRb = operRb;
	}
	
	public int getOpenLv() {
		return this.openLv;
	}


	public final static int getOpenLvMinLimit() {
		return 0;
	}

	public void setOpenLv(int openLv) {
		if (openLv < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[进入等级]openLv的值不得小于0");
		}
		this.openLv = openLv;
	}
	
	public int getList() {
		return this.list;
	}


	public final static int getListMinLimit() {
		return 0;
	}

	public void setList(int list) {
		if (list < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[显示类型]list的值不得小于0");
		}
		this.list = list;
	}
	
	public int getJackpotOriValue() {
		return this.jackpotOriValue;
	}


	public final static int getJackpotOriValueMinLimit() {
		return 0;
	}

	public void setJackpotOriValue(int jackpotOriValue) {
		if (jackpotOriValue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[彩金池初始值]jackpotOriValue的值不得小于0");
		}
		this.jackpotOriValue = jackpotOriValue;
	}
	
	public int getJackpotPoolPer() {
		return this.jackpotPoolPer;
	}


	public final static int getJackpotPoolPerMinLimit() {
		return 0;
	}

	public void setJackpotPoolPer(int jackpotPoolPer) {
		if (jackpotPoolPer < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[彩金池初玩家抽水抽成比例参数]jackpotPoolPer的值不得小于0");
		}
		this.jackpotPoolPer = jackpotPoolPer;
	}
	
	public int getJackpotAddPoolPer() {
		return this.jackpotAddPoolPer;
	}


	public final static int getJackpotAddPoolPerMinLimit() {
		return 0;
	}

	public void setJackpotAddPoolPer(int jackpotAddPoolPer) {
		if (jackpotAddPoolPer < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[彩金累计池初玩家抽水抽成比例参数]jackpotAddPoolPer的值不得小于0");
		}
		this.jackpotAddPoolPer = jackpotAddPoolPer;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, TexasRoomTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends TexasRoomTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, TexasRoomTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "TexasRoomTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", roomType=" + roomType + ", openUp=" + openUp + ", smallBlind=" + smallBlind + ", minCarry=" + minCarry + ", maxCarry=" + maxCarry + ", serviceValue1=" + serviceValue1 + ", serviceValue2=" + serviceValue2 + ", victoryExp=" + victoryExp + ", joinExp=" + joinExp + ", watchNum=" + watchNum + ", roomNum=" + roomNum + ", dealerCost=" + dealerCost + ", openRoomNum=" + openRoomNum + ", smallCard=" + smallCard + ", suitNum=" + suitNum + ", displayNum=" + displayNum + ", operRb=" + operRb + ", openLv=" + openLv + ", list=" + list + ", jackpotOriValue=" + jackpotOriValue + ", jackpotPoolPer=" + jackpotPoolPer + ", jackpotAddPoolPer=" + jackpotAddPoolPer + ",]";
	}
}