package com.gameserver.luckyspin.data;

import com.gameserver.luckyspin.HumanLuckySpin;

public class HumanLuckySpinData {
	private long lastSpinTime;

	/**
	 * @return the lastSpinTime
	 */
	public long getLastSpinTime() {
		return lastSpinTime;
	}

	/**
	 * @param lastSpinTime the lastSpinTime to set
	 */
	public void setLastSpinTime(long lastSpinTime) {
		this.lastSpinTime = lastSpinTime;
	}
	
	public static HumanLuckySpinData convertFrom(HumanLuckySpin humanLuckySpin){
		HumanLuckySpinData tempHumanLuckySpinData = new HumanLuckySpinData();
		tempHumanLuckySpinData.setLastSpinTime(humanLuckySpin.getLastSpinTime());
		return tempHumanLuckySpinData;
	}

}
