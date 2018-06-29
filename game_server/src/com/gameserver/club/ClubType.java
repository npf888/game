package com.gameserver.club;

public enum ClubType {
	
	TYPE_NONE(0),
	TYPE_PUBLIC(1),
	TYPE_NEED_APPLY(2),
	TYPE_NOT_JOIN(3);
	
	
	private int value;

	public int getValue() {
		return value;
	}

	private ClubType(int value) {
		this.value = value;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static ClubType from(int value) {
		for(ClubType type : ClubType.values()) {
			if(type.value == value) {
				return type;
			}
		}
		return TYPE_NONE;
	}
	
	
}
