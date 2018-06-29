package com.gameserver.shop;

import java.util.ArrayList;
import java.util.List;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.gameserver.common.Globals;
import com.gameserver.shop.data.ShopInfoData;
import com.gameserver.shop.template.ShopTemplate;


/**
 * 商城服务
 * @author wayne
 *
 */
public class ShopService implements InitializeRequired,AfterInitializeRequired{


	/**模板服务*/
	private TemplateService templateService;

	/**商品信息数据*/
	private List<ShopInfoData> shopInfoDataList = new ArrayList<ShopInfoData>();
	/**商品列表*/
	private List<ShopTemplate> shopList = new ArrayList<ShopTemplate>(); 

	public ShopService(TemplateService templateService){
		this.templateService = templateService;
	}
	
	public List<ShopInfoData> getShopInfoDataList() {
		return shopInfoDataList;
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub

	}


	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		templateService = Globals.getTemplateService();
		
		for(ShopTemplate shopTemplate: templateService.getAll(ShopTemplate.class).values()){
			shopList.add(shopTemplate);
			getShopInfoDataList().add(ShopInfoData.convertFrom(shopTemplate));
		}
		
	}
	
}
