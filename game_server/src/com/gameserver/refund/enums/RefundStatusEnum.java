package com.gameserver.refund.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;



/**
 * 返还状态
 * @author wayne
 *
 */
public enum RefundStatusEnum implements IndexedEnum{
	NON_SEND(0),
	SEND(1)
	;

	private final int index;
	
	private RefundStatusEnum(int index){
		this.index= index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}

	private static final List<RefundStatusEnum> values = IndexedEnumUtil.toIndexes(RefundStatusEnum.values());

	public static RefundStatusEnum valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
