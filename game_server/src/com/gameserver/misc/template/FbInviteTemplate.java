package com.gameserver.misc.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.data.RandRewardData;

@ExcelRowBinding
public class FbInviteTemplate extends FbInviteTemplateVO{
	
	private RandRewardData reward =new RandRewardData();
	
	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void patchUp()
	{
		
		reward.setRewardId(this.getItemId());
		reward.setRewardCount(this.getItemNum());
	}

	public RandRewardData getReward() {
		return reward;
	}

	public void setReward(RandRewardData reward) {
		this.reward = reward;
	}
}
