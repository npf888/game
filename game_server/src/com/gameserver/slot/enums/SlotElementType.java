package com.gameserver.slot.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 
   0：普通、1：万能符，2、scatter，3、wild1:放大1倍4、wild2放大2倍，5、wild3，6、wild4 7、jackpot 8,BONUS GAME 9 大转盘玩法
 * @author 郭君伟
 *
 */
public enum SlotElementType  implements IndexedEnum{
	/**普通类型 **/
	NORMAL(0),
	/**wild  **/
	WILD(1),
	/** scatter **/
	SCATTER(2),
	/**赔率 1倍  **/
	WILD1(3),  
	/**赔率 2倍  **/
	WILD2(4),
	/**赔率 3倍  **/
	WILD3(5),
	/**赔率 4倍  **/
	WILD4(6),
	/**jackpot **/
	JACKPOT(7),
	/**BONUS GAME **/
	BONUS_GAME(8),
	/**大转盘玩法 **/
	BIGWHEEL(9),
	/**大号wild **/
	bigWILD(10),
	/**bonus13 玩法 **/
	BONUS13(11),
	/**神秘符号 **/
	MYSTERIOUS(12),
     /**老虎机14 玩法 **/
	BONUS14(13),
	/**狮身人面老虎机 **/
	SLOTTYPE15(14),
	/**金字塔老虎机 **/
	SLOTTYPE15_BONUS(15),
	/**阿兹特克文明拼图 **/
	SLOTTYPE16(16),
	/**狼老虎机 **/
	SLOTTYPE17(17),
	/**水晶魔法老虎机**/
	CRYSTAL18(18),
	/**泰国象 **/
	SLOTTYPE20WILD(19),
	/**泰国象 **/
	SLOTTYPE20WILD_BONUS(20),
	/**老虎老虎机 **/
	SLOTTYPE21_BONUS(21),
	/**西部牛仔老虎机 **/
	SLOTTYPE22(22),
	/**东方龙老虎机 **/
	SLOTTYPE23(23),
	/**忍者老虎机 **/
	SLOTTYPE25(24),
	/**女巫魔法老虎机 **/
	SLOTWILD26(25),
	/**犀牛老虎机**/
	BONUS27(26),
	/**海洋世界老虎机**/
	BONUS28(27),
	WILD27(28),
	/**西方龙老虎机**/
	BONUS29(29),
	/**福尔摩斯老虎机**/
	BONUS30(30),
	/**海盗特殊wild**/
	SLOTWILD31(31),
	/**海盗特殊bonus**/
	SLOTBONUS31(32),
	
	/**社交固定**/
	SLOT32WILD33(33),
	/**战车**/
	SLOT32CAR34(34),
	/**战士**/
	SLOT32SOLDIER35(35),
	/**大号wild**/
	SLOT32WILD35(36),
	/**bonus斯巴达**/
	SLOT32BONUS36(37),
	
	/**特殊scatter用于 龟兔赛跑海洋世界 老虎机**/
	SLOT28SPECILSCATTER(38),
	
	/**小红帽的bonus**/
	SLOT33BOUNS(39),
	/**万圣节 收集bonus**/
	SLOT38BOUNS(40)
	
	;
	
	private int index;
	private SlotElementType(int index){
		this.index = index;
	}
	
	private static final List<SlotElementType> indexes = IndexedEnumUtil.toIndexes(SlotElementType.values());

	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public static SlotElementType indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}

}
