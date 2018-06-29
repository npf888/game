package com.gameserver.slot.data;

import java.util.Arrays;
import java.util.List;

import com.core.util.ArrayUtils;

public class BounsConnectInfoData {
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
	public static BounsConnectInfoData convertFromScatterInfo(List<Integer> posList){
		BounsConnectInfoData tempBounsConnectInfoData = new BounsConnectInfoData();
		tempBounsConnectInfoData.setPosList(ArrayUtils.intList2Array(posList));
		return tempBounsConnectInfoData;
	}

	@Override
	public String toString() {
		return "BounsConnectInfoData [posList=" + Arrays.toString(posList) + "]";
	}
}
