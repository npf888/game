package com.gameserver.shop.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.item.template.ItemTemplate;
import com.gameserver.shop.enums.ShopCatergoryEnum;

@ExcelRowBinding
public class ShopTemplate extends ShopTemplateVO{

	private Currency currency;
	private ShopCatergoryEnum shopCategoryEnum;
	
	@Override
	public void check() throws TemplateConfigException
	{
		setCurrency(Currency.valueOf(this.getCurrencyType()));
		if(getCurrency() == null || getCurrency()!=Currency.DIAMOND ){
			throw new TemplateConfigException(this.getSheetName(),this.getCurrencyType(),"货币种类不同");
		}
		
		setShopCategoryEnum(ShopCatergoryEnum.indexOf(this.getSmallCategory()));
		if(getShopCategoryEnum() == null ){
			throw new TemplateConfigException(this.getSheetName(),this.getCurrencyType(),"上平种类不同");
		}
		
		if(getShopCategoryEnum()!=ShopCatergoryEnum.COINS){
			ItemTemplate itemTemplate = Globals.getTemplateService().get(this.getItemId(), ItemTemplate.class);
			if(itemTemplate == null){
				throw new TemplateConfigException(this.getSheetName(),this.getItemId(),"道具id不存在");
			}
		}
		
	}
	
	@Override
	public void patchUp() throws Exception 
	{
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public ShopCatergoryEnum getShopCategoryEnum() {
		return shopCategoryEnum;
	}

	public void setShopCategoryEnum(ShopCatergoryEnum shopCategoryEnum) {
		this.shopCategoryEnum = shopCategoryEnum;
	}
}