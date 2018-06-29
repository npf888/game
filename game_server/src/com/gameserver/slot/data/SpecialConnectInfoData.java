package com.gameserver.slot.data;

import java.util.Arrays;

import com.core.util.ArrayUtils;



public class SpecialConnectInfoData {
	private int[]posList;

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
	
	public static SpecialConnectInfoData convertFromScatterInfo(ScatterInfo scatterInfo){
		SpecialConnectInfoData tempSpecialConnectInfoData = new SpecialConnectInfoData();
		tempSpecialConnectInfoData.setPosList(ArrayUtils.intList2Array(scatterInfo.getPosList()));
		return tempSpecialConnectInfoData;
	}

	@Override
	public String toString() {
		return "SpecialConnectInfoData [posList=" + Arrays.toString(posList) + "]";
	}

	
	
	
}
