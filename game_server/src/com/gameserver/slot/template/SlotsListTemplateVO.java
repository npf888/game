package com.gameserver.slot.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.util.StringUtils;
import com.core.annotation.ExcelCellBinding;

/**
 * slots list
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class SlotsListTemplateVO extends TemplateObject {

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
	protected int payLinesNum;

	/** 下注1 */
	@ExcelCellBinding(offset = 6)
	protected int bet1;

	/** 下注2 */
	@ExcelCellBinding(offset = 7)
	protected int bet2;

	/** 下注3 */
	@ExcelCellBinding(offset = 8)
	protected int bet3;

	/** 下注4 */
	@ExcelCellBinding(offset = 9)
	protected int bet4;

	/** 下注5 */
	@ExcelCellBinding(offset = 10)
	protected int bet5;

	/** 卷轴1随机数范围 */
	@ExcelCellBinding(offset = 11)
	protected int reel1Num;

	/** 卷轴2随机数范围 */
	@ExcelCellBinding(offset = 12)
	protected int reel2Num;

	/** 卷轴3随机数范围 */
	@ExcelCellBinding(offset = 13)
	protected int reel3Num;

	/** 卷轴4随机数范围 */
	@ExcelCellBinding(offset = 14)
	protected int reel4Num;

	/** 卷轴5随机数范围 */
	@ExcelCellBinding(offset = 15)
	protected int reel5Num;

	/** 行数 */
	@ExcelCellBinding(offset = 16)
	protected int rows;

	/** 列数 */
	@ExcelCellBinding(offset = 17)
	protected int columns;

	/** 列数 */
	@ExcelCellBinding(offset = 18)
	protected int bigWinNum;

	/** 列数 */
	@ExcelCellBinding(offset = 19)
	protected int megaWinNum;

	/** 列数 */
	@ExcelCellBinding(offset = 20)
	protected int superWinNum;

	/** 列数 */
	@ExcelCellBinding(offset = 21)
	protected int epicWinNum;

	/** 进入等级 */
	@ExcelCellBinding(offset = 22)
	protected int openLv;

	/** 类型 */
	@ExcelCellBinding(offset = 23)
	protected int type;

	/** 显示类型 */
	@ExcelCellBinding(offset = 24)
	protected int list;

	/** 房间是否开jackpot 1.开 2.关 */
	@ExcelCellBinding(offset = 25)
	protected int jackpotswitch;

	/** 压注最小等级 */
	@ExcelCellBinding(offset = 26)
	protected int bet1Lv;

	/** 压注最小等级 */
	@ExcelCellBinding(offset = 27)
	protected int bet2Lv;

	/** 压注最小等级 */
	@ExcelCellBinding(offset = 28)
	protected int bet3Lv;

	/** 压注最小等级 */
	@ExcelCellBinding(offset = 29)
	protected int bet4Lv;

	/** 压注最小等级 */
	@ExcelCellBinding(offset = 30)
	protected int bet5Lv;

	/** 彩金初始值 */
	@ExcelCellBinding(offset = 31)
	protected int jackpotOriValue;

	/** 彩金初始值 */
	@ExcelCellBinding(offset = 32)
	protected int jackpotOriValue1;

	/** 彩金初始值 */
	@ExcelCellBinding(offset = 33)
	protected int jackpotOriValue2;

	/** 彩金初始值 */
	@ExcelCellBinding(offset = 34)
	protected int jackpotOriValue3;

	/** 彩金初始值 */
	@ExcelCellBinding(offset = 35)
	protected int jackpotOriValue4;

	/** 彩金初始值 */
	@ExcelCellBinding(offset = 36)
	protected int jackpotOriValue5;

	/**  */
	@ExcelCellBinding(offset = 37)
	protected int jackpotPoolPer;

	/**  */
	@ExcelCellBinding(offset = 38)
	protected int jackpotAddPoolPer;

	/** 添加奖池比例 */
	@ExcelCellBinding(offset = 39)
	protected int raceReward;

	/** 第一名获得比例 */
	@ExcelCellBinding(offset = 40)
	protected int firstReward;

	/** 第二名获得比例 */
	@ExcelCellBinding(offset = 41)
	protected int secondReward;

	/** 第三名获得比例 */
	@ExcelCellBinding(offset = 42)
	protected int thirdReward;

	/** 连线类型 1：3*5老虎机 2： 4*5老虎机 */
	@ExcelCellBinding(offset = 43)
	protected int lineType;

	/** 初始坐标 */
	@ExcelCellBinding(offset = 44)
	protected int firstReel1;

	/** 初始坐标 */
	@ExcelCellBinding(offset = 45)
	protected int firstReel2;

	/** 初始坐标 */
	@ExcelCellBinding(offset = 46)
	protected int firstReel3;

	/** 初始坐标 */
	@ExcelCellBinding(offset = 47)
	protected int firstReel4;

	/** 初始坐标 */
	@ExcelCellBinding(offset = 48)
	protected int firstReel5;

	/** 游戏类型 1 普通scatter 2 表示 大转盘玩法 */
	@ExcelCellBinding(offset = 49)
	protected int gameType;

	/** 物品ID */
	@ExcelCellBinding(offset = 50)
	protected int ticketID;

	/** 出现的次数 */
	@ExcelCellBinding(offset = 51)
	protected int count1;

	/** 摇的次数 */
	@ExcelCellBinding(offset = 52)
	protected int count2;


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
	
	public int getPayLinesNum() {
		return this.payLinesNum;
	}


	public final static int getPayLinesNumMinLimit() {
		return 0;
	}

	public void setPayLinesNum(int payLinesNum) {
		if (payLinesNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[下限]payLinesNum的值不得小于0");
		}
		this.payLinesNum = payLinesNum;
	}
	
	public int getBet1() {
		return this.bet1;
	}


	public final static int getBet1MinLimit() {
		return 0;
	}

	public void setBet1(int bet1) {
		if (bet1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[下注1]bet1的值不得小于0");
		}
		this.bet1 = bet1;
	}
	
	public int getBet2() {
		return this.bet2;
	}


	public final static int getBet2MinLimit() {
		return 0;
	}

	public void setBet2(int bet2) {
		if (bet2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[下注2]bet2的值不得小于0");
		}
		this.bet2 = bet2;
	}
	
	public int getBet3() {
		return this.bet3;
	}


	public final static int getBet3MinLimit() {
		return 0;
	}

	public void setBet3(int bet3) {
		if (bet3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[下注3]bet3的值不得小于0");
		}
		this.bet3 = bet3;
	}
	
	public int getBet4() {
		return this.bet4;
	}


	public final static int getBet4MinLimit() {
		return 0;
	}

	public void setBet4(int bet4) {
		if (bet4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[下注4]bet4的值不得小于0");
		}
		this.bet4 = bet4;
	}
	
	public int getBet5() {
		return this.bet5;
	}


	public final static int getBet5MinLimit() {
		return 0;
	}

	public void setBet5(int bet5) {
		if (bet5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[下注5]bet5的值不得小于0");
		}
		this.bet5 = bet5;
	}
	
	public int getReel1Num() {
		return this.reel1Num;
	}


	public final static int getReel1NumMinLimit() {
		return 0;
	}

	public void setReel1Num(int reel1Num) {
		if (reel1Num < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[卷轴1随机数范围]reel1Num的值不得小于0");
		}
		this.reel1Num = reel1Num;
	}
	
	public int getReel2Num() {
		return this.reel2Num;
	}


	public final static int getReel2NumMinLimit() {
		return 0;
	}

	public void setReel2Num(int reel2Num) {
		if (reel2Num < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[卷轴2随机数范围]reel2Num的值不得小于0");
		}
		this.reel2Num = reel2Num;
	}
	
	public int getReel3Num() {
		return this.reel3Num;
	}


	public final static int getReel3NumMinLimit() {
		return 0;
	}

	public void setReel3Num(int reel3Num) {
		if (reel3Num < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[卷轴3随机数范围]reel3Num的值不得小于0");
		}
		this.reel3Num = reel3Num;
	}
	
	public int getReel4Num() {
		return this.reel4Num;
	}


	public final static int getReel4NumMinLimit() {
		return 0;
	}

	public void setReel4Num(int reel4Num) {
		if (reel4Num < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[卷轴4随机数范围]reel4Num的值不得小于0");
		}
		this.reel4Num = reel4Num;
	}
	
	public int getReel5Num() {
		return this.reel5Num;
	}


	public final static int getReel5NumMinLimit() {
		return 0;
	}

	public void setReel5Num(int reel5Num) {
		if (reel5Num < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[卷轴5随机数范围]reel5Num的值不得小于0");
		}
		this.reel5Num = reel5Num;
	}
	
	public int getRows() {
		return this.rows;
	}


	public final static int getRowsMinLimit() {
		return 0;
	}

	public void setRows(int rows) {
		if (rows < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[行数]rows的值不得小于0");
		}
		this.rows = rows;
	}
	
	public int getColumns() {
		return this.columns;
	}


	public final static int getColumnsMinLimit() {
		return 0;
	}

	public void setColumns(int columns) {
		if (columns < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[列数]columns的值不得小于0");
		}
		this.columns = columns;
	}
	
	public int getBigWinNum() {
		return this.bigWinNum;
	}


	public final static int getBigWinNumMinLimit() {
		return 0;
	}

	public void setBigWinNum(int bigWinNum) {
		if (bigWinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[列数]bigWinNum的值不得小于0");
		}
		this.bigWinNum = bigWinNum;
	}
	
	public int getMegaWinNum() {
		return this.megaWinNum;
	}


	public final static int getMegaWinNumMinLimit() {
		return 0;
	}

	public void setMegaWinNum(int megaWinNum) {
		if (megaWinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[列数]megaWinNum的值不得小于0");
		}
		this.megaWinNum = megaWinNum;
	}
	
	public int getSuperWinNum() {
		return this.superWinNum;
	}


	public final static int getSuperWinNumMinLimit() {
		return 0;
	}

	public void setSuperWinNum(int superWinNum) {
		if (superWinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[列数]superWinNum的值不得小于0");
		}
		this.superWinNum = superWinNum;
	}
	
	public int getEpicWinNum() {
		return this.epicWinNum;
	}


	public final static int getEpicWinNumMinLimit() {
		return 0;
	}

	public void setEpicWinNum(int epicWinNum) {
		if (epicWinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[列数]epicWinNum的值不得小于0");
		}
		this.epicWinNum = epicWinNum;
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
	
	public int getType() {
		return this.type;
	}


	public final static int getTypeMinLimit() {
		return 0;
	}

	public void setType(int type) {
		if (type < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[类型]type的值不得小于0");
		}
		this.type = type;
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
					25, "[显示类型]list的值不得小于0");
		}
		this.list = list;
	}
	
	public int getJackpotswitch() {
		return this.jackpotswitch;
	}


	public final static int getJackpotswitchMinLimit() {
		return 0;
	}

	public void setJackpotswitch(int jackpotswitch) {
		if (jackpotswitch < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[房间是否开jackpot 1.开 2.关]jackpotswitch的值不得小于0");
		}
		this.jackpotswitch = jackpotswitch;
	}
	
	public int getBet1Lv() {
		return this.bet1Lv;
	}


	public final static int getBet1LvMinLimit() {
		return 0;
	}

	public void setBet1Lv(int bet1Lv) {
		if (bet1Lv < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[压注最小等级]bet1Lv的值不得小于0");
		}
		this.bet1Lv = bet1Lv;
	}
	
	public int getBet2Lv() {
		return this.bet2Lv;
	}


	public final static int getBet2LvMinLimit() {
		return 0;
	}

	public void setBet2Lv(int bet2Lv) {
		if (bet2Lv < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[压注最小等级]bet2Lv的值不得小于0");
		}
		this.bet2Lv = bet2Lv;
	}
	
	public int getBet3Lv() {
		return this.bet3Lv;
	}


	public final static int getBet3LvMinLimit() {
		return 0;
	}

	public void setBet3Lv(int bet3Lv) {
		if (bet3Lv < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					29, "[压注最小等级]bet3Lv的值不得小于0");
		}
		this.bet3Lv = bet3Lv;
	}
	
	public int getBet4Lv() {
		return this.bet4Lv;
	}


	public final static int getBet4LvMinLimit() {
		return 0;
	}

	public void setBet4Lv(int bet4Lv) {
		if (bet4Lv < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					30, "[压注最小等级]bet4Lv的值不得小于0");
		}
		this.bet4Lv = bet4Lv;
	}
	
	public int getBet5Lv() {
		return this.bet5Lv;
	}


	public final static int getBet5LvMinLimit() {
		return 0;
	}

	public void setBet5Lv(int bet5Lv) {
		if (bet5Lv < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					31, "[压注最小等级]bet5Lv的值不得小于0");
		}
		this.bet5Lv = bet5Lv;
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
					32, "[彩金初始值]jackpotOriValue的值不得小于0");
		}
		this.jackpotOriValue = jackpotOriValue;
	}
	
	public int getJackpotOriValue1() {
		return this.jackpotOriValue1;
	}


	public final static int getJackpotOriValue1MinLimit() {
		return 0;
	}

	public void setJackpotOriValue1(int jackpotOriValue1) {
		if (jackpotOriValue1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					33, "[彩金初始值]jackpotOriValue1的值不得小于0");
		}
		this.jackpotOriValue1 = jackpotOriValue1;
	}
	
	public int getJackpotOriValue2() {
		return this.jackpotOriValue2;
	}


	public final static int getJackpotOriValue2MinLimit() {
		return 0;
	}

	public void setJackpotOriValue2(int jackpotOriValue2) {
		if (jackpotOriValue2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					34, "[彩金初始值]jackpotOriValue2的值不得小于0");
		}
		this.jackpotOriValue2 = jackpotOriValue2;
	}
	
	public int getJackpotOriValue3() {
		return this.jackpotOriValue3;
	}


	public final static int getJackpotOriValue3MinLimit() {
		return 0;
	}

	public void setJackpotOriValue3(int jackpotOriValue3) {
		if (jackpotOriValue3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					35, "[彩金初始值]jackpotOriValue3的值不得小于0");
		}
		this.jackpotOriValue3 = jackpotOriValue3;
	}
	
	public int getJackpotOriValue4() {
		return this.jackpotOriValue4;
	}


	public final static int getJackpotOriValue4MinLimit() {
		return 0;
	}

	public void setJackpotOriValue4(int jackpotOriValue4) {
		if (jackpotOriValue4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					36, "[彩金初始值]jackpotOriValue4的值不得小于0");
		}
		this.jackpotOriValue4 = jackpotOriValue4;
	}
	
	public int getJackpotOriValue5() {
		return this.jackpotOriValue5;
	}


	public final static int getJackpotOriValue5MinLimit() {
		return 0;
	}

	public void setJackpotOriValue5(int jackpotOriValue5) {
		if (jackpotOriValue5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[彩金初始值]jackpotOriValue5的值不得小于0");
		}
		this.jackpotOriValue5 = jackpotOriValue5;
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
					38, "[]jackpotPoolPer的值不得小于0");
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
					39, "[]jackpotAddPoolPer的值不得小于0");
		}
		this.jackpotAddPoolPer = jackpotAddPoolPer;
	}
	
	public int getRaceReward() {
		return this.raceReward;
	}


	public final static int getRaceRewardMinLimit() {
		return 0;
	}

	public void setRaceReward(int raceReward) {
		if (raceReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					40, "[添加奖池比例]raceReward的值不得小于0");
		}
		this.raceReward = raceReward;
	}
	
	public int getFirstReward() {
		return this.firstReward;
	}


	public final static int getFirstRewardMinLimit() {
		return 0;
	}

	public void setFirstReward(int firstReward) {
		if (firstReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					41, "[第一名获得比例]firstReward的值不得小于0");
		}
		this.firstReward = firstReward;
	}
	
	public int getSecondReward() {
		return this.secondReward;
	}


	public final static int getSecondRewardMinLimit() {
		return 0;
	}

	public void setSecondReward(int secondReward) {
		if (secondReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					42, "[第二名获得比例]secondReward的值不得小于0");
		}
		this.secondReward = secondReward;
	}
	
	public int getThirdReward() {
		return this.thirdReward;
	}


	public final static int getThirdRewardMinLimit() {
		return 0;
	}

	public void setThirdReward(int thirdReward) {
		if (thirdReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					43, "[第三名获得比例]thirdReward的值不得小于0");
		}
		this.thirdReward = thirdReward;
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
					44, "[连线类型 1：3*5老虎机 2： 4*5老虎机]lineType的值不得小于0");
		}
		this.lineType = lineType;
	}
	
	public int getFirstReel1() {
		return this.firstReel1;
	}


	public final static int getFirstReel1MinLimit() {
		return 0;
	}

	public void setFirstReel1(int firstReel1) {
		if (firstReel1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					45, "[初始坐标]firstReel1的值不得小于0");
		}
		this.firstReel1 = firstReel1;
	}
	
	public int getFirstReel2() {
		return this.firstReel2;
	}


	public final static int getFirstReel2MinLimit() {
		return 0;
	}

	public void setFirstReel2(int firstReel2) {
		if (firstReel2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					46, "[初始坐标]firstReel2的值不得小于0");
		}
		this.firstReel2 = firstReel2;
	}
	
	public int getFirstReel3() {
		return this.firstReel3;
	}


	public final static int getFirstReel3MinLimit() {
		return 0;
	}

	public void setFirstReel3(int firstReel3) {
		if (firstReel3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					47, "[初始坐标]firstReel3的值不得小于0");
		}
		this.firstReel3 = firstReel3;
	}
	
	public int getFirstReel4() {
		return this.firstReel4;
	}


	public final static int getFirstReel4MinLimit() {
		return 0;
	}

	public void setFirstReel4(int firstReel4) {
		if (firstReel4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					48, "[初始坐标]firstReel4的值不得小于0");
		}
		this.firstReel4 = firstReel4;
	}
	
	public int getFirstReel5() {
		return this.firstReel5;
	}


	public final static int getFirstReel5MinLimit() {
		return 0;
	}

	public void setFirstReel5(int firstReel5) {
		if (firstReel5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					49, "[初始坐标]firstReel5的值不得小于0");
		}
		this.firstReel5 = firstReel5;
	}
	
	public int getGameType() {
		return this.gameType;
	}


	public final static int getGameTypeMinLimit() {
		return 0;
	}

	public void setGameType(int gameType) {
		if (gameType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					50, "[游戏类型 1 普通scatter 2 表示 大转盘玩法]gameType的值不得小于0");
		}
		this.gameType = gameType;
	}
	
	public int getTicketID() {
		return this.ticketID;
	}


	public final static int getTicketIDMinLimit() {
		return 0;
	}

	public void setTicketID(int ticketID) {
		if (ticketID < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					51, "[物品ID]ticketID的值不得小于0");
		}
		this.ticketID = ticketID;
	}
	
	public int getCount1() {
		return this.count1;
	}


	public final static int getCount1MinLimit() {
		return 0;
	}

	public void setCount1(int count1) {
		if (count1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					52, "[出现的次数]count1的值不得小于0");
		}
		this.count1 = count1;
	}
	
	public int getCount2() {
		return this.count2;
	}


	public final static int getCount2MinLimit() {
		return 0;
	}

	public void setCount2(int count2) {
		if (count2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					53, "[摇的次数]count2的值不得小于0");
		}
		this.count2 = count2;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, SlotsListTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends SlotsListTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, SlotsListTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "SlotsListTemplateVO [  nameId=" + nameId + ", descrip=" + descrip + ", langDesc=" + langDesc + ", icon=" + icon + ", payLinesNum=" + payLinesNum + ", bet1=" + bet1 + ", bet2=" + bet2 + ", bet3=" + bet3 + ", bet4=" + bet4 + ", bet5=" + bet5 + ", reel1Num=" + reel1Num + ", reel2Num=" + reel2Num + ", reel3Num=" + reel3Num + ", reel4Num=" + reel4Num + ", reel5Num=" + reel5Num + ", rows=" + rows + ", columns=" + columns + ", bigWinNum=" + bigWinNum + ", megaWinNum=" + megaWinNum + ", superWinNum=" + superWinNum + ", epicWinNum=" + epicWinNum + ", openLv=" + openLv + ", type=" + type + ", list=" + list + ", jackpotswitch=" + jackpotswitch + ", bet1Lv=" + bet1Lv + ", bet2Lv=" + bet2Lv + ", bet3Lv=" + bet3Lv + ", bet4Lv=" + bet4Lv + ", bet5Lv=" + bet5Lv + ", jackpotOriValue=" + jackpotOriValue + ", jackpotOriValue1=" + jackpotOriValue1 + ", jackpotOriValue2=" + jackpotOriValue2 + ", jackpotOriValue3=" + jackpotOriValue3 + ", jackpotOriValue4=" + jackpotOriValue4 + ", jackpotOriValue5=" + jackpotOriValue5 + ", jackpotPoolPer=" + jackpotPoolPer + ", jackpotAddPoolPer=" + jackpotAddPoolPer + ", raceReward=" + raceReward + ", firstReward=" + firstReward + ", secondReward=" + secondReward + ", thirdReward=" + thirdReward + ", lineType=" + lineType + ", firstReel1=" + firstReel1 + ", firstReel2=" + firstReel2 + ", firstReel3=" + firstReel3 + ", firstReel4=" + firstReel4 + ", firstReel5=" + firstReel5 + ", gameType=" + gameType + ", ticketID=" + ticketID + ", count1=" + count1 + ", count2=" + count2 + ",]";
	}
}