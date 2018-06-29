package com.gameserver.luckyspin.template;

import java.util.ArrayList;
import java.util.List;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.item.template.ItemTemplate;


/**
 * luckyspin
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public  class TurntableTemplate extends TurntableTemplateVO {
	private List<Integer> pools = new ArrayList<Integer>();
	private List<RandRewardData> rewardList = new ArrayList<RandRewardData>();
	
	@Override
	public void check() throws TemplateConfigException
	{
		if(this.getItemId()<1000){
			Currency tempCurrency= Currency.valueOf(this.getItemId());
			if(tempCurrency==null){
				throw new TemplateConfigException(this.getSheetName(),this.getItemId(),"item id 不对");
			}
		}else{
			ItemTemplate tempItem=Globals.getItemService().getItemTemplWithId(this.getItemId());
			if(tempItem==null){
				throw new TemplateConfigException(this.getSheetName(),this.getItemId(),"item id 不对");
			}
		}
	}
	
	@Override
	public void patchUp() throws Exception 
	{
		pools.add(this.getValue1());
		pools.add(this.getValue2());
		pools.add(this.getValue3());
		pools.add(this.getValue4());
		pools.add(this.getValue5());
		pools.add(this.getValue6());
		pools.add(this.getValue7());
		pools.add(this.getValue8());
		pools.add(this.getValue9());
		pools.add(this.getValue10());
		RandRewardData temp = new RandRewardData();
		temp.setRewardId(this.getItemId());
		temp.setRewardCount(this.getItemNum());
		rewardList.add(temp);
	}
	
	public List<Integer> getPools(){
		return this.pools;
	}
	
	public List<RandRewardData> getRewardList(){
		return this.rewardList;
	}
}