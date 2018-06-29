package com.gameserver.vipnew.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * vip数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCVipNewData extends GCMessage{
	
	/** vip数据 */
	private com.gameserver.vipnew.data.HumanVipNewInfoData humanVipNewInfoData;

	public GCVipNewData (){
	}
	
	public GCVipNewData (
			com.gameserver.vipnew.data.HumanVipNewInfoData humanVipNewInfoData ){
			this.humanVipNewInfoData = humanVipNewInfoData;
	}

	@Override
	protected boolean readImpl() {
		humanVipNewInfoData = new com.gameserver.vipnew.data.HumanVipNewInfoData();
					humanVipNewInfoData.setVipLevel(readInteger());
							humanVipNewInfoData.setVipPoint(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(humanVipNewInfoData.getVipLevel());
		writeInteger(humanVipNewInfoData.getVipPoint());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_VIP_NEW_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_VIP_NEW_DATA";
	}

	public com.gameserver.vipnew.data.HumanVipNewInfoData getHumanVipNewInfoData(){
		return humanVipNewInfoData;
	}
		
	public void setHumanVipNewInfoData(com.gameserver.vipnew.data.HumanVipNewInfoData humanVipNewInfoData){
		this.humanVipNewInfoData = humanVipNewInfoData;
	}
}