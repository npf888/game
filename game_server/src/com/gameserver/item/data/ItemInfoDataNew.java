package com.gameserver.item.data;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.gameserver.common.Globals;
import com.gameserver.item.Item;
import com.gameserver.item.template.ItemNewTemplate;


/**
 * 物品数据
 * @author wayne
 *
 */
public class ItemInfoDataNew {
	//道具来源从哪来
	private long fromPlayerId;
	//道具ID
	private int itemId;
	//道具 类型
	private int itemType;
	//道具名
	private String itemName;
	//道具价格
	private long price;
	//道具图片
	private String img;
	//正在使用的色钟的itemID
	private int usingClockItemId;
	//是否已经存在 0：否，1：是
	private int isExist;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getUsingClockItemId() {
		return usingClockItemId;
	}
	public void setUsingClockItemId(int usingClockItemId) {
		this.usingClockItemId = usingClockItemId;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	
	public int getIsExist() {
		return isExist;
	}
	public void setIsExist(int isExist) {
		this.isExist = isExist;
	}
	public long getFromPlayerId() {
		return fromPlayerId;
	}
	public void setFromPlayerId(long fromPlayerId) {
		this.fromPlayerId = fromPlayerId;
	}
	public static ItemInfoDataNew convertFromItem(Item item,Item useringItem,ItemNewTemplate itemNewTemplate)
	{
		ItemInfoDataNew itemInfoData = new ItemInfoDataNew();
		itemInfoData.setImg("http://"+Globals.getServerConfig().getHttpDataAddress()+"/clock/"+itemNewTemplate.getIcon()+".png");
		itemInfoData.setItemId(item.getTemplateId());
		itemInfoData.setItemName(itemNewTemplate.getLangDesc());
		itemInfoData.setItemType(itemNewTemplate.getItemType());
		itemInfoData.setPrice(itemNewTemplate.getNum());
		if(useringItem != null){
			itemInfoData.setUsingClockItemId(useringItem.getTemplateId());
		}else{
			itemInfoData.setUsingClockItemId(0);
		}
		itemInfoData.setFromPlayerId(item.getFromPlayerId());
		itemInfoData.setIsExist(0);
		return itemInfoData;
	}
	public static ItemInfoDataNew convertListFromItem(ItemNewTemplate itemNewTemplate)
	{
		ItemInfoDataNew itemInfoData = new ItemInfoDataNew();
		itemInfoData.setImg("http://"+Globals.getServerConfig().getHttpDataAddress()+"/clock/"+itemNewTemplate.getIcon()+".png");
		itemInfoData.setItemId(itemNewTemplate.getId());
		itemInfoData.setItemName(itemNewTemplate.getLangDesc());
		itemInfoData.setItemType(itemNewTemplate.getItemType());
		itemInfoData.setPrice(itemNewTemplate.getNum());
		itemInfoData.setUsingClockItemId(0);
		itemInfoData.setFromPlayerId(0);
		itemInfoData.setIsExist(0);
		return itemInfoData;
	}
	
}
