package com.gameserver.bazoo.enums;

import com.core.enums.IndexedEnum;

public enum RedisBazooKey implements IndexedEnum {
	/**倍率 **/
	bazooRoomBet(1,"bazooRoomBet_"),
	/**房间 号 **/
	bazooRoomNum(2,"bazooRoomNum_"),
	;

	
	private int index;
	private String key;
	
	@Override
	public int getIndex() {
		return index;
	}
	public String getKey() {
		return key;
	}
	
	private RedisBazooKey(int index,String key){
		this.index = index;
		this.key = key;
	}
	
	
	
	
	
	
}
