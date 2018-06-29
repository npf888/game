package com.gameserver.slot.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 游戏类型
 * @author 郭君伟
 *
 */
public enum GameType implements IndexedEnum {
	GameType1(1),
	GameType2(2)
       ;
	
	private int index;
	private GameType(int index){
		this.index = index;
	}
	
	private static final List<GameType> indexes = IndexedEnumUtil.toIndexes(GameType.values());

	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public static GameType indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}
}
