package com.gameserver.activity;

/**
 * 活动参数设置
 * @author wayne
 *
 */
public  class ActivityParam
{
	/**参数索引 参照 奖励条件参数*/
	private int paramIndex;
	/**参数值 */
	private int paramCount;
	
	public int getParamIndex() {
		return paramIndex;
	}
	public void setParamIndex(int paramIndex) {
		this.paramIndex = paramIndex;
	}
	public int getParamCount() {
		return paramCount;
	}
	public void setParamCount(int paramCount) {
		this.paramCount = paramCount;
	}


}