package com.gameserver.recharge.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 
 * 
 * 小类：1、筹码 2、周卡3、月卡4、门票5、改名卡 6、付费转盘,7、翻倍转盘8、奖券9、俱乐部礼包10、金库（小金猪）
 * @author 牛鹏飞
 *
 */
public enum SmallCategoryEnum implements IndexedEnum {
	/** 1 筹码 **/
	SMALLCATEGORYENUM1(1),
	/** 2、色钟 **/
	SMALLCATEGORYENUM2(2),
	/** 3、红包 **/
	SMALLCATEGORYENUM3(3),
	/** 4、礼物 **/
	SMALLCATEGORYENUM4(4),
	;

    private final int index;
	
	private SmallCategoryEnum(int index){
		this.index= index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}

	private static final List<SmallCategoryEnum> values = IndexedEnumUtil.toIndexes(SmallCategoryEnum.values());

	public static SmallCategoryEnum valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}

}
