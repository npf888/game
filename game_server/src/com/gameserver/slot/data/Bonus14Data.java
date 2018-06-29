package com.gameserver.slot.data;
/**
 * 
 * 石器时代 采集苹果的小游戏
 * @author JavaServer
 *
 */
public class Bonus14Data {

	/**剩余次数**/
	private int times;
	/**每次获得的金币数**/
	private long singleGold;
	/**每次叠加的金币数**/
	private long overlyingGold;
	/**单次收集的数量**/
	private int singleCollectNum;
	/**累计收集的数量**/
	private int overlyingCollectNum;
	
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public long getSingleGold() {
		return singleGold;
	}
	public void setSingleGold(long singleGold) {
		this.singleGold = singleGold;
	}
	public long getOverlyingGold() {
		return overlyingGold;
	}
	public void setOverlyingGold(long overlyingGold) {
		this.overlyingGold = overlyingGold;
	}
	public int getSingleCollectNum() {
		return singleCollectNum;
	}
	public void setSingleCollectNum(int singleCollectNum) {
		this.singleCollectNum = singleCollectNum;
	}
	public int getOverlyingCollectNum() {
		return overlyingCollectNum;
	}
	public void setOverlyingCollectNum(int overlyingCollectNum) {
		this.overlyingCollectNum = overlyingCollectNum;
	}
	
	
	
	
}
