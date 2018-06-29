package com.gameserver.activity.data;



/**
 * 活动领取状态
 * @author wayne
 *
 */
public class HumanSimpleRewardActivityData {
	/**活动id*/
	private long activityId;
	/**状态*/
	private int drawType;
	
	public long getActivityId() {
		return activityId;
	}
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}
	public int getDrawType() {
		return drawType;
	}
	public void setDrawType(int drawType) {
		this.drawType = drawType;
	}
	


}
