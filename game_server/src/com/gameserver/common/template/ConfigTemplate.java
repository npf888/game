package com.gameserver.common.template;

import java.util.ArrayList;
import java.util.List;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.item.template.ItemTemplate;

/**
 * 
 * @author wayne
 *
 */
@ExcelRowBinding
public class ConfigTemplate extends ConfigTemplateVO {
	
	private List<RandRewardData> giftRewardList = new ArrayList<RandRewardData>();
	private List<RandRewardData> fbRewardList = new ArrayList<RandRewardData>();
	
	@Override
	public void check() throws TemplateConfigException
	{
		if(this.getDefaultNum()!=6 && this.getDefaultNum()!=9)
		{
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"房间人数不是5人也不是9人");
		}
		
		if(this.getSupplyChips()!=0 && this.getSupplyChips()!=1)
		{
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"自动补充不是0也不是1");
		}
		
	}
	@Override
	public void patchUp()
	{
		ItemTemplate itemTemplate  = Globals.getTemplateService().get(this.getChangeNameId(),ItemTemplate.class);
		if(itemTemplate == null){
			throw new TemplateConfigException(this.getSheetName(),this.getId(),"不存在改名卡");
		}
		RandRewardData giftReward= new RandRewardData();
		giftReward.setRewardId(this.getGiftId());
		giftReward.setRewardCount(this.getGiftNum());
		giftRewardList.add(giftReward);
		
		RandRewardData fbReward= new RandRewardData();
		fbReward.setRewardId(Currency.GOLD.getIndex());
		fbReward.setRewardCount(this.getFbItemNum());
		fbRewardList.add(fbReward);
	}

	public List<RandRewardData> getGiftRewardList(){
		return giftRewardList;
	}
	
	public List<RandRewardData> getFbRewardList(){
		return fbRewardList;
	}
}
