package com.gameserver.vip.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * vip数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanVipInfoData extends GCMessage{
	
	/** vip数据 */
	private com.gameserver.vip.data.HumanVipInfoData vipInfoData;

	public GCHumanVipInfoData (){
	}
	
	public GCHumanVipInfoData (
			com.gameserver.vip.data.HumanVipInfoData vipInfoData ){
			this.vipInfoData = vipInfoData;
	}

	@Override
	protected boolean readImpl() {
		vipInfoData = new com.gameserver.vip.data.HumanVipInfoData();
					vipInfoData.setLastGetTime(readLong());
							vipInfoData.setLevel(readInteger());
							vipInfoData.setBuyTime(readLong());
							vipInfoData.setDays(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(vipInfoData.getLastGetTime());
		writeInteger(vipInfoData.getLevel());
		writeLong(vipInfoData.getBuyTime());
		writeInteger(vipInfoData.getDays());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_VIP_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_VIP_INFO_DATA";
	}

	public com.gameserver.vip.data.HumanVipInfoData getVipInfoData(){
		return vipInfoData;
	}
		
	public void setVipInfoData(com.gameserver.vip.data.HumanVipInfoData vipInfoData){
		this.vipInfoData = vipInfoData;
	}
}