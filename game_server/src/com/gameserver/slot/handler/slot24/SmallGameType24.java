package com.gameserver.slot.handler.slot24;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum SmallGameType24 implements IndexedEnum {

	/**0 失败 **/
	Type0(0),
	/**1 成功 **/
	Type1(1),
	/**2开启小游戏 **/
	Type2(2);
	
	private int index;
	private SmallGameType24(int index){
		this.index = index;
	}
	
	private static final List<SmallGameType24> indexes = IndexedEnumUtil.toIndexes(SmallGameType24.values());

	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public static SmallGameType24 indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}

}
