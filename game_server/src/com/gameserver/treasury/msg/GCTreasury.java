package com.gameserver.treasury.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回当前存钱罐的 对象
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTreasury extends GCMessage{
	
	/** 类型 总共 六种   0,1,2,3,4,5  */
	private long typeTreasury;
	/** 当前金币 */
	private long curGold;
	/** 作者:存储上限 */
	private long maxTreasury;
	/** 作者:VIP点数奖励 */
	private long vipPointTreasury;

	public GCTreasury (){
	}
	
	public GCTreasury (
			long typeTreasury,
			long curGold,
			long maxTreasury,
			long vipPointTreasury ){
			this.typeTreasury = typeTreasury;
			this.curGold = curGold;
			this.maxTreasury = maxTreasury;
			this.vipPointTreasury = vipPointTreasury;
	}

	@Override
	protected boolean readImpl() {
		typeTreasury = readLong();
		curGold = readLong();
		maxTreasury = readLong();
		vipPointTreasury = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(typeTreasury);
		writeLong(curGold);
		writeLong(maxTreasury);
		writeLong(vipPointTreasury);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TREASURY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TREASURY";
	}

	public long getTypeTreasury(){
		return typeTreasury;
	}
		
	public void setTypeTreasury(long typeTreasury){
		this.typeTreasury = typeTreasury;
	}

	public long getCurGold(){
		return curGold;
	}
		
	public void setCurGold(long curGold){
		this.curGold = curGold;
	}

	public long getMaxTreasury(){
		return maxTreasury;
	}
		
	public void setMaxTreasury(long maxTreasury){
		this.maxTreasury = maxTreasury;
	}

	public long getVipPointTreasury(){
		return vipPointTreasury;
	}
		
	public void setVipPointTreasury(long vipPointTreasury){
		this.vipPointTreasury = vipPointTreasury;
	}
}