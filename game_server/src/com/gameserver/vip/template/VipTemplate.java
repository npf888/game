package com.gameserver.vip.template;

import java.util.ArrayList;
import java.util.List;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;

@ExcelRowBinding
public class VipTemplate extends VipTemplateVO{
	//起始奖励
	private List<RandRewardData> initRewardList = new ArrayList<RandRewardData>();
	//每天奖励
	private List<RandRewardData> dailyRewardList = new ArrayList<RandRewardData>();
	
	@Override
	public void check() throws TemplateConfigException
	{
		// TODO Auto-generated method stub
		if(this.getVipRoom()!=0 && this.getVipRoom()!=1)
		{
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"vip room 应该是0或1");
		}
		
		if(this.getExpression()!=0 && this.getExpression()!=1)
		{
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"expression 应该是0或1");
		}
	}
	
	@Override
	public void patchUp() throws Exception 
	{
		RandRewardData goldRandRewardData = new RandRewardData();
		goldRandRewardData.setRewardId(Currency.GOLD.getIndex());
		goldRandRewardData.setRewardCount(this.getGiftChips());
		initRewardList.add(goldRandRewardData);
		
		RandRewardData expRandRewardData = new RandRewardData();
		expRandRewardData.setRewardId(Currency.EXP.getIndex());
		expRandRewardData.setRewardCount(this.getGiftExp());
		initRewardList.add(expRandRewardData);
		
		RandRewardData charmRandRewardData = new RandRewardData();
		charmRandRewardData.setRewardId(Currency.CHARM.getIndex());
		charmRandRewardData.setRewardCount(this.getCharmValue());
		initRewardList.add(charmRandRewardData);
		
		RandRewardData dailyRandRewardData = new RandRewardData();
		dailyRandRewardData.setRewardId(Currency.GOLD.getIndex());
		dailyRandRewardData.setRewardCount(this.getDailyReward());
		dailyRewardList.add(dailyRandRewardData);
	}
	
	public List<RandRewardData> getInitRewardList(){
		return initRewardList;
	}
	
	public List<RandRewardData> getDailyRewardList(){
		return dailyRewardList;
	}
}
