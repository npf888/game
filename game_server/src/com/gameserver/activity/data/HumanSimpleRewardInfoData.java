package com.gameserver.activity.data;

/**
 * 活动状态  前端 显示 go-clam的
 * @author 郭君伟
 *
 */
public class HumanSimpleRewardInfoData {

	/**活动id*/
	private long activityId;
	
	/**活动状态  列表  0不可以领取  1可以领取 2已经领取**/
	private int[] stateListist;
	

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public int[] getStateListist() {
		return stateListist;
	}

	public void setStateListist(int[] stateListist) {
		this.stateListist = stateListist;
	}

	

	
	

	

	

	

	
	
	
}
