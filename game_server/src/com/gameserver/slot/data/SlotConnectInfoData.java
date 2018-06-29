package com.gameserver.slot.data;

import java.util.Arrays;

import com.core.util.ArrayUtils;

public class SlotConnectInfoData {
	private int payLineId;
	private int[] posList;
	private int payId;
	/**
	 * @return the payLineId
	 */
	public int getPayLineId() {
		return payLineId;
	}
	/**
	 * @param payLineId the payLineId to set
	 */
	public void setPayLineId(int payLineId) {
		this.payLineId = payLineId;
	}
	/**
	 * @return the posList
	 */
	public int[] getPosList() {
		return posList;
	}
	/**
	 * @param posList the posList to set
	 */
	public void setPosList(int[] posList) {
		this.posList = posList;
	}
	
	public int getPayId() {
		return payId;
	}
	public void setPayId(int payId) {
		this.payId = payId;
	}
	public static SlotConnectInfoData convertFromSlotConnectInfo(SlotConnectInfo slotConnectInfo){
		SlotConnectInfoData tempSlotConnectInfoData = new SlotConnectInfoData();
		tempSlotConnectInfoData.setPayLineId(slotConnectInfo.getPaylinesTemplate().getId());
		tempSlotConnectInfoData.setPosList(ArrayUtils.intList2Array(slotConnectInfo.getPosList()));
		tempSlotConnectInfoData.setPayId(slotConnectInfo.getPayId());
		return tempSlotConnectInfoData;
		
	}
	@Override
	public String toString() {
		return "SlotConnectInfoData [payLineId=" + payLineId + ", posList=" + Arrays.toString(posList) + ", payId="
				+ payId + "]";
	}
	
	
}
