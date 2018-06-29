package com.gameserver.treasury.data;

/**
 * 目前没有用
 * @author JavaServer
 *
 */
public class TreasuryCurData {

	/** 类型 总共 六种   0,1,2,3,4,5 */
	protected long typeTreasury;

	/** 当前金币 */
	protected long curGold;

	/** 作者:存储上限 */
	protected long maxTreasury;

	/** 作者:VIP点数奖励 */
	protected long vipPointTreasury;

	public long getTypeTreasury() {
		return typeTreasury;
	}

	public void setTypeTreasury(long typeTreasury) {
		this.typeTreasury = typeTreasury;
	}

	public long getCurGold() {
		return curGold;
	}

	public void setCurGold(long curGold) {
		this.curGold = curGold;
	}

	public long getMaxTreasury() {
		return maxTreasury;
	}

	public void setMaxTreasury(long maxTreasury) {
		this.maxTreasury = maxTreasury;
	}

	public long getVipPointTreasury() {
		return vipPointTreasury;
	}

	public void setVipPointTreasury(long vipPointTreasury) {
		this.vipPointTreasury = vipPointTreasury;
	}

	
	
	
}
