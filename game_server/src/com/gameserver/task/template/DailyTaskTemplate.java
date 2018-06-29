package com.gameserver.task.template;

import java.util.ArrayList;
import java.util.List;

import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.data.RandRewardData;

@ExcelRowBinding
public class DailyTaskTemplate extends DailyTaskTemplateVO{

	private List<RandRewardData> rewardList = new ArrayList<RandRewardData>();
	
	
	@Override
	public void check(){

		
	}
	
	@Override
	public void patchUp(){
		RandRewardData reward = new RandRewardData();
		reward.setRewardId(this.getItemId());
		reward.setRewardCount(this.getReward1Num());
		rewardList.add(reward);
		
	}

	public  List<RandRewardData> getRewardList() {
		return rewardList;
	}
	
	
}
