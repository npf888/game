package com.gameserver.ranknew.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 选择类型
 * @author 郭君伟
 *
 */
public enum RankType implements IndexedEnum {
	/** 单次 **/
	SINGLERANK(1),
	/** 总**/
	TOTALRank(2)
	;

    private int index;
	
	private RankType(int index){
		this.index = index;
	}

  private static final List<RankType> indexes = IndexedEnumUtil.toIndexes(RankType.values());

	@Override
	public int getIndex() {
		return index;
	}
	
	public static RankType indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}


}
