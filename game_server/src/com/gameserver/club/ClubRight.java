package com.gameserver.club;

public enum ClubRight {
	
	NONE(1 << 0),
	SHENGZHI_JIANGZHI(1 << 1),
	PIZHUN(1 << 2),
	KICK(1 << 3),
	EDIT(1 << 4),
	CLEAN_NOTE(1 << 5),
	ZHUANRANG(1 << 6),
	TANHE(1 << 7),
	
	ZHUXI(ClubRight.SHENGZHI_JIANGZHI.getRight() 
			| ClubRight.PIZHUN.getRight() 
			| ClubRight.KICK.getRight() 
			| ClubRight.EDIT.getRight()
			| ClubRight.CLEAN_NOTE.getRight()
			| ClubRight.ZHUANRANG.getRight()
			| ClubRight.TANHE.getRight()
			),
	
	
	FUZHUXI(ClubRight.SHENGZHI_JIANGZHI.getRight() 
			| ClubRight.PIZHUN.getRight() 
			| ClubRight.KICK.getRight() 
			| ClubRight.EDIT.getRight()
			| ClubRight.CLEAN_NOTE.getRight()
			),
	
	ZHUGUAN(ClubRight.SHENGZHI_JIANGZHI.getRight() 
			| ClubRight.PIZHUN.getRight() 
			| ClubRight.CLEAN_NOTE.getRight()
			),
	
	CHENGYUAN(ClubRight.NONE.getRight());
	
	private int right;

	private ClubRight(int right) {
		this.right = right;
	}
	
	/**
	 * 判断是否有权限
	 * @param clubRight
	 * @return
	 */
	public boolean haveRight(ClubRight clubRight) {
		return (this.right & clubRight.right) != 0;
	}

	public int getRight() {
		return right;
	}
	
	
}
