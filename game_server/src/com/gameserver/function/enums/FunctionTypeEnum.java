package com.gameserver.function.enums;

import com.gameserver.function.Function;
/**
 * 功能的 类型
 * @author JavaServer
 *
 */
public enum FunctionTypeEnum {
	/**大玩家 **/
	SendOneToOne(1);

	private final int index;
	private FunctionTypeEnum(int index){
		this.index= index;
	}
	
	public int getIndex() {
		return index;
	}
	public Class<? extends Function> getClazz() {
		return Function.class;
	}



}
