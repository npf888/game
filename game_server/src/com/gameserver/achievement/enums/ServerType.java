package com.gameserver.achievement.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 1 百家乐  2 德州 3 classic 4 四大美人 5水果 6 沙滩 7吸血鬼 8 埃及艳后 9美人鱼 10澳门女神 11白蛇传  50 一般 51 社交 52 全部
 * @author 郭君伟
 *
 */
public enum ServerType implements IndexedEnum {
	
	/**百家乐  **/
	ServerType1(1),
	/**德州 **/
	ServerType2(2),
	/** classic **/
	ServerType3(3),
	/**四大美人 **/
	ServerType4(4),
	/**水果 **/
	ServerType5(5),
	/** 沙滩**/
	ServerType6(6),
	/**吸血鬼 **/
	ServerType7(7),
	/**埃及艳后 **/
	ServerType8(8),
	/**美人鱼 **/
	ServerType9(9),
	/**澳门女神 **/
	ServerType10(10),
	/**1白蛇传 **/
	ServerType11(11),
	/** 一般**/
	ServerType50(50),
	/** 社交**/
	ServerType51(51),
	/**全部 **/
	ServerType52(52),
	;

	private int index;
	private ServerType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}
	
	private static final List<ServerType> values = IndexedEnumUtil.toIndexes(ServerType.values());

	public static ServerType valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
   
}
