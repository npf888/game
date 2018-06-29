package com.gameserver.recharge.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 充值类型
 * @author 郭君伟
 *
 */
public enum CategoryEnum implements IndexedEnum {
	
	/** 1 筹码 **/
	CATEGORYENUM1(1),
	/** 2 物品 **/
	CATEGORYENUM2(2),
	/** 3 礼包 **/
	CATEGORYENUM3(3),
	/** 4 功能性付费**/
	CATEGORYENUM4(4)
	;

    private final int index;
	
	private CategoryEnum(int index){
		this.index= index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}

	private static final List<CategoryEnum> values = IndexedEnumUtil.toIndexes(CategoryEnum.values());

	public static CategoryEnum valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}

}
