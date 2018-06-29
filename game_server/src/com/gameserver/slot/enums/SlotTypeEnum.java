package com.gameserver.slot.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 老虎机类型
 * @author 郭君伟
 *
 */
public enum SlotTypeEnum implements IndexedEnum {
	/** classic **/
	SlotType1(1),
	/** 四大美人 **/
	SlotType2(2),
	/** 水果 **/
	SlotType3(3),
	/**沙滩 **/
	SlotType4(4),
	/**吸血鬼**/
	SlotType5(5),
	/**埃及艳后**/
	SlotType6(6),
	/**美人鱼**/
	SlotType7(7),
	/**澳门女神**/
	SlotType8(8),
	/**白蛇传**/
	SlotType9(9),
	/**马来网红**/
	SlotType10(10),
	/**日月潭老虎机**/
	SlotType11(11),
	/**维密老虎机**/
	SlotType12(12),
	/**宙斯老虎机**/
	SlotType13(13),
	/**石器时代老虎机**/
	SlotType14(14),
	/**狮身人面像老虎机**/
	SlotType15(15),
    /**阿兹特克文明老虎机 **/
	SlotType16(16),
	/**狼老虎机**/
	SlotType17(17),
	/**动物猫**/
	SlotType18(18),
	/** 水晶魔法 老虎机**/
	SlotType19(19),
	/**  大象 老虎机**/
	SlotType20(20),
	/**  老虎 老虎机**/
	SlotType21(21),
	/** 西部牛仔 老虎机**/
	SlotType22(22),
	/** 东方龙 老虎机**/
	SlotType23(23),
	/**  巴西风情 老虎机**/
	SlotType24(24),
	/**  忍者 老虎机**/
	SlotType25(25),
	/**   女巫魔法 老虎机**/
	SlotType26(26),
	/**   犀牛 老虎机**/
	SlotType27(27),
	/**  海洋世界策 老虎机**/
	SlotType28(28),
	/**  西方龙 老虎机**/
	SlotType29(29),
	/** 福尔摩斯 老虎机**/
	SlotType30(30),
	/** 海盗 老虎机**/
	SlotType31(31),
	/** 斯巴达 老虎机**/
	SlotType32(32),
	/** 小红帽 老虎机**/
	SlotType33(33),
	/** 万圣节 老虎机**/
	SlotType38(38),
	;

	private int index;
	private SlotTypeEnum(int index){
		this.index = index;
	}
	
	private static final List<SlotTypeEnum> indexes = IndexedEnumUtil.toIndexes(SlotTypeEnum.values());

	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public static SlotTypeEnum indexOf(final int index){
		return EnumUtil.valueOf(indexes, index);
	}

}
