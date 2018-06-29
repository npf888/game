package com.gameserver.currency;


import java.util.List;

import com.common.constants.LangConstants;
import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
import com.gameserver.role.properties.RoleBaseIntProperties;
import com.gameserver.role.properties.RoleBaseLongProperties;

/**
 * 货币类型
 * 
 * @author Thinker
 * 
 */
public enum Currency implements IndexedEnum
{
	NULL(0, -1, 0),
	/** 钻石,  由充值所得*/
	DIAMOND(1,RoleBaseLongProperties.DIAMOND, LangConstants.CURRENCY_NAME_DIAMOND), 
	/** 点券, 由运营发放也是钻石 */
	COUPON(2,RoleBaseLongProperties.COUPON, LangConstants.CURRENCY_NAME_COUPON),
	/** 金币, 游戏内获得 */
	GOLD(3,RoleBaseLongProperties.GOLD, LangConstants.CURRENCY_NAME_GOLD),
	/**魅力值*/
	CHARM(4,RoleBaseLongProperties.CHARM,LangConstants.CURRENCY_NAME_CHARM),
	/**经验*/
	EXP(5,RoleBaseLongProperties.CUR_EXP,LangConstants.CURRENCY_NAME_EXP),
	
;

	/** 枚举的索引 */
	public final int index;
	
	/** 此货币类型在任务属性常量中的索引 @see {@link RoleBaseIntProperties} */
	private final int roleBaseIntPropIndex;
	
	/** 货币名称的key */
	private final Integer nameKey;
	
	/** 按索引顺序存放的枚举数组 */
	private static final List<Currency> indexes = IndexedEnum.IndexedEnumUtil.toIndexes(Currency.values());

	
	private Currency(int index, int roleBaseIntPropIndex, Integer nameKey) {
		this.index = index;
		this.roleBaseIntPropIndex = roleBaseIntPropIndex;
		this.nameKey = nameKey;
	}
	
	/**
	 * 获取货币索引
	 */
	@Override
	public int getIndex() {
		return index;
	}
	
	/**
	 * 取得货币的名称key
	 * 
	 * @return
	 */
	public Integer getNameKey() {
		return this.nameKey;
	}
	
	/**
	 * 获取货币的基本属性索引
	 * @return
	 */
	public int getRoleBaseIntPropIndex() {
		return roleBaseIntPropIndex;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 *            枚举的索引
	 * @return
	 */
	public static Currency valueOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}
