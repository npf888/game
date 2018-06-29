package com.gameserver.common.data;

import java.util.ArrayList;
import java.util.List;


/**
 * 通用奖励
 * @author wayne
 *
 */
public class RandRewardData {
	/**奖励id*/
	private int rewardId;
	/**奖励数量*/
	private int rewardCount;
	
	/**全服的赠送奖励 （只有全服的才根据 这个vippoint 给用户赠送奖励）*/
	private int vippoint;
	
	public int getRewardId() {
		return rewardId;
	}
	public void setRewardId(int rewardId) {
		this.rewardId = rewardId;
	}
	public int getRewardCount() {
		return rewardCount;
	}
	public void setRewardCount(int rewardCount) {
		this.rewardCount = rewardCount;
	}
	
	public static  List<RandRewardData> combine(List<RandRewardData> temp){
		List<RandRewardData> result = new ArrayList<RandRewardData>();
		for(RandRewardData randRewardData:temp){
			boolean found = false;
			for(RandRewardData randRewardData1:result){
				if(randRewardData1.getRewardId() == randRewardData.getRewardId()){
					randRewardData1.setRewardCount(randRewardData1.getRewardCount() +randRewardData.getRewardCount() );
					found = true;
					break;
				}
			}
			if(!found)
			{
				RandRewardData newRandRewardData = new RandRewardData();
				newRandRewardData.setRewardId(randRewardData.getRewardId());
				newRandRewardData.setRewardCount(randRewardData.getRewardCount());
				result.add(newRandRewardData);
			}
		}
		
		return result;
	}
	public int getVippoint() {
		return vippoint;
	}
	public void setVippoint(int vippoint) {
		this.vippoint = vippoint;
	}
	
	
	
}
