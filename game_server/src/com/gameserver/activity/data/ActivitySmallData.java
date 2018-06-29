package com.gameserver.activity.data;

import com.gameserver.common.data.RandRewardData;

/**
 * 一个活动里面的一个小条件
 * 静态规则数据
 * @author 郭君伟
 *
 */
public class ActivitySmallData {

	/**条件 **/
	private ActivityCondition[] conditionList ;
	
	/**奖励列表*/
	private RandRewardData[] rewardList;

	public ActivityCondition[] getConditionList() {
		return conditionList;
	}

	public void setConditionList(ActivityCondition[] conditionList) {
		this.conditionList = conditionList;
	}

	public RandRewardData[] getRewardList() {
		return rewardList;
	}

	public void setRewardList(RandRewardData[] rewardList) {
		this.rewardList = rewardList;
	}

	
	

	
	
}
