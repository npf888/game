package com.gameserver.luckyspin.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 请求大转盘数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBigZhuanpan extends GCMessage{
	
	/** 是否有免费转动 1 有免费转动 0 没有 */
	private int isFree;
	/** 登陆进度 */
	private int loginPoint;

	public GCBigZhuanpan (){
	}
	
	public GCBigZhuanpan (
			int isFree,
			int loginPoint ){
			this.isFree = isFree;
			this.loginPoint = loginPoint;
	}

	@Override
	protected boolean readImpl() {
		isFree = readInteger();
		loginPoint = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isFree);
		writeInteger(loginPoint);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BIG_ZHUANPAN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BIG_ZHUANPAN";
	}

	public int getIsFree(){
		return isFree;
	}
		
	public void setIsFree(int isFree){
		this.isFree = isFree;
	}

	public int getLoginPoint(){
		return loginPoint;
	}
		
	public void setLoginPoint(int loginPoint){
		this.loginPoint = loginPoint;
	}
}