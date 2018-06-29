package com.gameserver.club;

public enum ClubZhiwu {
	NONE(0,ClubRight.NONE),
	ZHUXI(1,ClubRight.ZHUXI),
	FUZHUXI(2,ClubRight.FUZHUXI),
	ZHUGUAN(3,ClubRight.ZHUGUAN),//主管
	CHENGYUAN(4,ClubRight.CHENGYUAN);
	
	private int level;
	private ClubRight rights;
	
	private ClubZhiwu(int level, ClubRight rights) {
		this.level = level;
		this.rights = rights;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ClubRight getRights() {
		return rights;
	}

	public void setRights(ClubRight rights) {
		this.rights = rights;
	}
	
	/**
	 * 判断是否有权限
	 * @param clubRight
	 * @return
	 */
	public boolean haveRight(ClubRight clubRight) {
		return (this.rights.getRight() & clubRight.getRight()) != 0;
	}
	

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static ClubZhiwu from(int level) {
		for(ClubZhiwu type : ClubZhiwu.values()) {
			if(type.level == level) {
				return type;
			}
		}
		return NONE;
	}
	
}
