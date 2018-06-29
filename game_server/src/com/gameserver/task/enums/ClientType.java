package com.gameserver.task.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 
 * @author 郭君伟
 *
 */
public enum ClientType implements IndexedEnum {
	TASK1(1),
	TASK2(2),
	TASK3(3),
	TASK4(4),
	TASK5(5),
	TASK6(6),
	TASK7(7),
	TASK8(8),
	TASK9(9),
	TASK10(10),
	TASK11(11),
	TASK12(12),
	TASK13(13),
	TASK14(14),
	TASK15(15),
	TASK16(16),
	TASK17(17),
	TASK18(18),
	TASK19(19),
	TASK20(20),
	TASK21(21),
	TASK22(22),
	TASK23(23),
	TASK24(24),
	TASK25(25),
	TASK26(26),
	TASK27(27),
	TASK28(28),
	TASK29(29),
	TASK30(30),
	
	TASK101(101),
	TASK102(102),
	TASK104(104),
	;
	
	private int index;
	private ClientType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	private static final List<ClientType> values = IndexedEnumUtil.toIndexes(ClientType.values());

	public static ClientType valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}


}
