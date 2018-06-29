package com.gameserver.misc.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.data.RandRewardData;

@ExcelRowBinding
public class OnlineRewardTemplate extends OnlineRewardTemplateVO{
	
	private RandRewardData reward;
	
	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void patchUp()
	{
		reward =new RandRewardData();
		reward.setRewardId(this.getRewardId());
		reward.setRewardCount(this.getRewardNum());
	}

	public RandRewardData getReward() {
		return reward;
	}

	public void setReward(RandRewardData reward) {
		this.reward = reward;
	}
}
