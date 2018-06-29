package com.gameserver.recharge.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 订单状态
 * @author wayne
 *
 */
public enum OrderStatus implements IndexedEnum{
	/**没有效果的 **/
	NON_VALIDATE(0),
	/**有效果的 **/
	VALIDATE(1),
	/**取消状态 **/
	CANCEL(2),
	/**未处理状态 **/
	PENDING(3),
	;

	private final int index;
	
	private OrderStatus(int index){
		this.index= index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}

	private static final List<OrderStatus> values = IndexedEnumUtil.toIndexes(OrderStatus.values());

	public static OrderStatus valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
