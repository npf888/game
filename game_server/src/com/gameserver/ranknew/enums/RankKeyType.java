package com.gameserver.ranknew.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * rankType:1 昨天单次 2 昨天总 3今日单次 4 今日总 5总单次 6总总
 * @author 郭君伟
 *
 */
public enum RankKeyType implements IndexedEnum {
	/**昨天单次 **/
	RankKeyType1(1,"RankKeyType1",1),
	/**昨天总 **/
	RankKeyType2(2,"RankKeyType2",1),
	/**今日单次 **/
	RankKeyType3(3,"RankKeyType3",1),
	/**今日总 **/
	RankKeyType4(4,"RankKeyType4",1),
	/**总单次 **/
	RankKeyType5(5,"RankKeyType5",-1),
	/**总总 **/
	RankKeyType6(6,"RankKeyType6",-1)
	;

    private int index;
    
    private String key;
    
    private int refreshDay;
	
	private RankKeyType(int index,String key,int refreshDay){
		this.index = index;
		this.key = key;
		this.refreshDay = refreshDay;
	}

  private static final List<RankKeyType> indexes = IndexedEnumUtil.toIndexes(RankKeyType.values());

	@Override
	public int getIndex() {
		return index;
	}
	
	
	
	public String getKey() {
		return key;
	}



	public int getRefreshDay() {
		return refreshDay;
	}



	public static List<RankKeyType> getIndexes() {
		return indexes;
	}



	public static RankKeyType indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}

}
