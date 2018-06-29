package com.gameserver.rank.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 排行版类型
 * @author wayne
 *
 */
public enum RankTypeEnum implements IndexedEnum{
	//DIAMOND(1,"rank.diamond",-1),
	GOLD(2,"rank.gold",-1),
	CHARM(3,"rank.charm",-1),
	TEXAS_WEEK_WIN(4,"rank.texas_week_win",7),
	TEXAS_SNG_WEEK(5,"rank.texas_sng_week",7),
	;
	
	/** 枚举值列表 */
	private static final List<RankTypeEnum> values = IndexedEnumUtil.toIndexes(RankTypeEnum.values());
	
	private final int index;
	private final String key;
	private final int refreshDay;
	private RankTypeEnum(int index,String key,int refreshDay)
	{
		this.index = index;
		this.key = key;
		this.refreshDay = refreshDay;
	}
	
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public String getKey()
	{
		return this.key;
	}

	public int getRefreshDay() {
		return refreshDay;
	}


	/**
	 * 将 int 类型转换为枚举类型
	 * 
	 * @param index
	 * @return
	 */
	public static RankTypeEnum valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}
}
