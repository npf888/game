package com.gameserver.gift.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 优惠礼包类型
 * @author 郭君伟
 *
 */
public enum GiftTypeEnum implements IndexedEnum {
	/** 商店弹出 **/
	GiftType1(1),
	/** 新手礼包  **/
	GiftType2(2),
	/** 多种礼包  **/
	GiftType3(3)
	;

    private int index;
	
	private GiftTypeEnum(int index){
		this.index = index;
	}

  private static final List<GiftTypeEnum> indexes = IndexedEnumUtil.toIndexes(GiftTypeEnum.values());

	@Override
	public int getIndex() {
		return index;
	}
	
	public static GiftTypeEnum indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}


}
