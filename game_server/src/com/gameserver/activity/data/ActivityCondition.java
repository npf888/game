package com.gameserver.activity.data;

/**
 * 
 * @author 郭君伟
 *
 */
public class ActivityCondition {
	
	/**
	 * 小条件
	 */
	private int conditionType;
	
	/**
	 * 小条件需要达到的值
	 */
	private int valueDate;


	public int getConditionType() {
		return conditionType;
	}

	public void setConditionType(int conditionType) {
		this.conditionType = conditionType;
	}

	public int getValueDate() {
		return valueDate;
	}

	public void setValueDate(int valueDate) {
		this.valueDate = valueDate;
	}
	
	

}
