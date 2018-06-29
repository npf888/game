package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 还钱返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSpinZhuanpanNofree extends GCMessage{
	
	/** 是否有免费转动 1 有免费转动 0 没有 */
	private int isFree;
	/** 转盘停留位置 */
	private int point;

	public GCSpinZhuanpanNofree (){
	}
	
	public GCSpinZhuanpanNofree (
			int isFree,
			int point ){
			this.isFree = isFree;
			this.point = point;
	}

	@Override
	protected boolean readImpl() {
		isFree = readInteger();
		point = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isFree);
		writeInteger(point);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SPIN_ZHUANPAN_NOFREE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SPIN_ZHUANPAN_NOFREE";
	}

	public int getIsFree(){
		return isFree;
	}
		
	public void setIsFree(int isFree){
		this.isFree = isFree;
	}

	public int getPoint(){
		return point;
	}
		
	public void setPoint(int point){
		this.point = point;
	}
}