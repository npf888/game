package com.gameserver.activity;



/**
 * 活动奖励数据
 * @author wayne
 *
 */

public  class HumanRewardActivityDetailData{
	/**允许领取次数*/
	private int allowDrawCount;
	/**已经领取次数*/
	private int hasDrawCount;
	
	public int getAllowDrawCount() {
		return allowDrawCount;
	}
	public void setAllowDrawCount(int allowDrawCount) {
		this.allowDrawCount = allowDrawCount;
	}
	public int getHasDrawCount() {
		return hasDrawCount;
	}
	public void setHasDrawCount(int hasDrawCount) {
		this.hasDrawCount = hasDrawCount;
	}
	
	
	public static HumanRewardActivityDetailData convertFromActivityRewardRule(ActivityRewardRule activityRewardRule){
		HumanRewardActivityDetailData humanRewardActivityDetailData = new HumanRewardActivityDetailData();
		humanRewardActivityDetailData.setAllowDrawCount(0);
		humanRewardActivityDetailData.setHasDrawCount(0);
		return humanRewardActivityDetailData;
	}
}


	

