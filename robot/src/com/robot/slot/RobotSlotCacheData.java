package com.robot.slot;
/**
 * 转动老虎机的的过程中 缓存的数据
 * @author JavaServer
 *
 */
public class RobotSlotCacheData {
	/**
	 * 这些值 是用户转完 老虎机，最后统计的数值 
	 */
	private long rewardCount;
	private long rewardUsed;
	private long payCount;
	private long scatterNum;
	private long bonusNum;//所有老虎机都用这一个 bonusNum
	private long scatterTriggerCount;//付费   Scatter筹码数：中scatter游戏获得的筹码数量；
	private long scatterTriggerFreeCount;//免费 Scatter筹码数：中scatter游戏获得的筹码数量；
	private long bonusTriggerCount;
	private long bonusTriggerFreeCount;
	/*************************************/
	
	private long wildTriggerCount;
	private long wildTriggerFreeCount;
	private long wildNum;
	private boolean wildFree;
	private int wildFreeTimes;
	/**
	 * 临时数据
	 */
	private String slotName;
	private int slotId;
	private int slotType;
	private int bet;
	private int free;
	private int freeTimes;
	
	private int num;//第几个老虎机
	private int maxNum;//最多转几次
	private int bonusPubNum;//所有老虎机都用这一个 bonusNum
	//触摸次数
	private int touchPubNum;
	
	/**
	 * 控制 是否属于 免费 scatter 
	 * true 是，false 不是
	 */
	private boolean scatterFree;
	//如果scatter 和 bonus 同时出现 这个 值记录 付费 转动 获取的免费转动次数 scatter
	private int scatterTimes;
	/**
	 * 控制 是否属于 bonus 
	 * true 是，false 不是
	 */
	private boolean bonusFree;
	
	/**
	 * 马来网红的 boxId
	 */
	private int boxId;
	
	
	
	
	/**第12个老虎机   维密老虎机**/
	private long slot12FreeTImes;//每次转动获得的奖金数
	/**第24个老虎机   巴西世界**/
	private long slot24BounsWinFreeTimes;
	
	/**第29个老虎机   西方龙**/
	
	/**第30个老虎机   福尔摩斯**/
	
	
	
	
	public RobotSlotCacheData(){
		this.rewardCount=0;
		this.rewardUsed=0;
		this.payCount=0;
		this.scatterNum=0;
		this.bonusNum=0;
		this.scatterTriggerCount=0;
		this.scatterTriggerFreeCount=0;
		this.bonusTriggerCount=0;
		this.bonusTriggerFreeCount=0;
		this.bet=0;
		this.free=0;
		this.freeTimes=0;
		this.num=1;
		this.maxNum=10;
		this.slotId=0;
		this.slotType=0;
		this.touchPubNum=1;
		this.bonusPubNum=0;
		this.slot24BounsWinFreeTimes=0;
		this.slot12FreeTImes=0;
		this.scatterTimes=0;
		this.scatterFree=false;//默认是false
		this.bonusFree=false;//默认是false
		this.wildFree=false;//默认是false
		this.slotName="无";
		
		this.wildTriggerCount=0;
		this.wildTriggerFreeCount=0;
		this.wildNum=0;
		this.wildFreeTimes=0;
		this.boxId=0;
	}
	public long getRewardCount() {
		return rewardCount;
	}
	public void setRewardCount(long rewardCount) {
		this.rewardCount = rewardCount;
	}
	public long getRewardUsed() {
		return rewardUsed;
	}
	public void setRewardUsed(long rewardUsed) {
		this.rewardUsed = rewardUsed;
	}
	public long getPayCount() {
		return payCount;
	}
	public void setPayCount(long payCount) {
		this.payCount = payCount;
	}
	public long getScatterNum() {
		return scatterNum;
	}
	public void setScatterNum(long scatterNum) {
		this.scatterNum = scatterNum;
	}
	public long getBonusNum() {
		return bonusNum;
	}
	public void setBonusNum(long bonusNum) {
		this.bonusNum = bonusNum;
	}
	public long getScatterTriggerCount() {
		return scatterTriggerCount;
	}
	public void setScatterTriggerCount(long scatterTriggerCount) {
		this.scatterTriggerCount = scatterTriggerCount;
	}
	public long getBonusTriggerCount() {
		return bonusTriggerCount;
	}
	public void setBonusTriggerCount(long bonusTriggerCount) {
		this.bonusTriggerCount = bonusTriggerCount;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	public int getFree() {
		return free;
	}
	public void setFree(int free) {
		this.free = free;
	}
	public int getFreeTimes() {
		return freeTimes;
	}
	public void setFreeTimes(int freeTimes) {
		this.freeTimes = freeTimes;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public int getSlotId() {
		return slotId;
	}
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	
	public int getTouchPubNum() {
		return touchPubNum;
	}
	public void setTouchPubNum(int touchPubNum) {
		this.touchPubNum = touchPubNum;
	}
	public int getBonusPubNum() {
		return bonusPubNum;
	}
	public void setBonusPubNum(int bonusPubNum) {
		this.bonusPubNum = bonusPubNum;
	}
	public long getScatterTriggerFreeCount() {
		return scatterTriggerFreeCount;
	}
	public void setScatterTriggerFreeCount(long scatterTriggerFreeCount) {
		this.scatterTriggerFreeCount = scatterTriggerFreeCount;
	}
	public int getSlotType() {
		return slotType;
	}
	public void setSlotType(int slotType) {
		this.slotType = slotType;
	}
	public String getSlotName() {
		return slotName;
	}
	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}
	public long getSlot24BounsWinFreeTimes() {
		return slot24BounsWinFreeTimes;
	}
	public void setSlot24BounsWinFreeTimes(long slot24BounsWinFreeTimes) {
		this.slot24BounsWinFreeTimes = slot24BounsWinFreeTimes;
	}
	public boolean isScatterFree() {
		return scatterFree;
	}
	public void setScatterFree(boolean scatterFree) {
		this.scatterFree = scatterFree;
	}
	public boolean isBonusFree() {
		return bonusFree;
	}
	public void setBonusFree(boolean bonusFree) {
		this.bonusFree = bonusFree;
	}
	public long getBonusTriggerFreeCount() {
		return bonusTriggerFreeCount;
	}
	public void setBonusTriggerFreeCount(long bonusTriggerFreeCount) {
		this.bonusTriggerFreeCount = bonusTriggerFreeCount;
	}
	public int getScatterTimes() {
		return scatterTimes;
	}
	public void setScatterTimes(int scatterTimes) {
		this.scatterTimes = scatterTimes;
	}
	public long getSlot12FreeTImes() {
		return slot12FreeTImes;
	}
	public void setSlot12FreeTImes(long slot12FreeTImes) {
		this.slot12FreeTImes = slot12FreeTImes;
	}
	public long getWildTriggerCount() {
		return wildTriggerCount;
	}
	public void setWildTriggerCount(long wildTriggerCount) {
		this.wildTriggerCount = wildTriggerCount;
	}
	public long getWildTriggerFreeCount() {
		return wildTriggerFreeCount;
	}
	public void setWildTriggerFreeCount(long wildTriggerFreeCount) {
		this.wildTriggerFreeCount = wildTriggerFreeCount;
	}
	public long getWildNum() {
		return wildNum;
	}
	public void setWildNum(long wildNum) {
		this.wildNum = wildNum;
	}
	public boolean isWildFree() {
		return wildFree;
	}
	public void setWildFree(boolean wildFree) {
		this.wildFree = wildFree;
	}
	public int getWildFreeTimes() {
		return wildFreeTimes;
	}
	public void setWildFreeTimes(int wildFreeTimes) {
		this.wildFreeTimes = wildFreeTimes;
	}
	public int getBoxId() {
		return boxId;
	}
	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}
	
	
	
}
