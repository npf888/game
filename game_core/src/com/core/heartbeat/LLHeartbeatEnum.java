package com.core.heartbeat;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 心跳枚举类型
 * @author Thinker
 *
 */
public enum LLHeartbeatEnum implements IndexedEnum
{
	/** 时间同步 */
	TIMEASYNC(1,"时间同步"),
	/** 定时任务 */
	SCHEDULE(2,"定时任务"),
	;
	
	/** 枚举的索引 */
	public final int index;
	/** 名称的key */
	private final String nameKey;
	
	/** 按索引顺序存放的枚举数组 */
	private static final List<LLHeartbeatEnum> indexes = IndexedEnum.IndexedEnumUtil.toIndexes(LLHeartbeatEnum.values());

	
	private LLHeartbeatEnum(int index,String nameKey)
	{
		this.index = index;
		this.nameKey = nameKey;
	}
	
	/**
	 * 获取索引
	 */
	@Override
	public int getIndex()
	{
		return index;
	}
	
	/**
	 * 取得名称key
	 * 
	 * @return
	 */
	public String getNameKey()
	{
		return this.nameKey;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 *            枚举的索引
	 * @return
	 */
	public static LLHeartbeatEnum valueOf(final int index) 
	{
		return EnumUtil.valueOf(indexes, index);
	}
}
