package com.gameserver.misc.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 在线奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCMiscFbInfoData extends GCMessage{
	
	/** 内容 */
	private com.gameserver.misc.data.HumanMiscFBInfoData humanMiscFBInfoData;

	public GCMiscFbInfoData (){
	}
	
	public GCMiscFbInfoData (
			com.gameserver.misc.data.HumanMiscFBInfoData humanMiscFBInfoData ){
			this.humanMiscFBInfoData = humanMiscFBInfoData;
	}

	@Override
	protected boolean readImpl() {
		humanMiscFBInfoData = new com.gameserver.misc.data.HumanMiscFBInfoData();
					humanMiscFBInfoData.setFbReward(readInteger());
						{
			int subCount = readShort();
							String[] subList = new String[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readString();
									}
			humanMiscFBInfoData.setFbInviteList(subList);
		}
						{
			int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
			humanMiscFBInfoData.setFbInviteRewardList(subList);
		}
							humanMiscFBInfoData.setFbThumb(readInteger());
							humanMiscFBInfoData.setFbstartTime(readLong());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(humanMiscFBInfoData.getFbReward());
		String[] fbInviteList=humanMiscFBInfoData.getFbInviteList();
		writeShort(fbInviteList.length);
		for(int i=0; i<fbInviteList.length; i++){	
				writeString(fbInviteList[i]);
		}
		int[] fbInviteRewardList=humanMiscFBInfoData.getFbInviteRewardList();
		writeShort(fbInviteRewardList.length);
		for(int i=0; i<fbInviteRewardList.length; i++){	
				writeInteger(fbInviteRewardList[i]);
		}
		writeInteger(humanMiscFBInfoData.getFbThumb());
		writeLong(humanMiscFBInfoData.getFbstartTime());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MISC_FB_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MISC_FB_INFO_DATA";
	}

	public com.gameserver.misc.data.HumanMiscFBInfoData getHumanMiscFBInfoData(){
		return humanMiscFBInfoData;
	}
		
	public void setHumanMiscFBInfoData(com.gameserver.misc.data.HumanMiscFBInfoData humanMiscFBInfoData){
		this.humanMiscFBInfoData = humanMiscFBInfoData;
	}
}