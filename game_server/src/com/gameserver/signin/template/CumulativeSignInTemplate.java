package com.gameserver.signin.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.data.RandRewardData;

@ExcelRowBinding
public class CumulativeSignInTemplate extends CumulativeSignInTemplateVO{
	
	private RandRewardData randRewardData;
	@Override
	public void check() throws TemplateConfigException
	{
		//检测天数
		if(this.getDay()<=0 && this.getDay()>7)
		{
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"累计天数小于0或大于7");
		}
	}
	
	@Override
	public void patchUp() throws Exception 
	{
		randRewardData = new RandRewardData();
		randRewardData.setRewardCount(this.getItemNum());
		randRewardData.setRewardId(this.getItemId());
	}
	
	
	public RandRewardData getRandRewardData()
	{
		return randRewardData;
	}
}