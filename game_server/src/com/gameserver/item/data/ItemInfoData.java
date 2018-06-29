package com.gameserver.item.data;

import java.util.List;

import com.gameserver.item.Item;

/**
 * 物品数据
 * @author wayne
 *
 */
public class ItemInfoData {
	private String uuid;
	private int templateId;
	private int overlap;
	private long beginTime;
	private long duration;
	
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public int getOverlap() {
		return overlap;
	}
	public void setOverlap(int overlap) {
		this.overlap = overlap;
	}
	public long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public static ItemInfoData convertFromItem(Item item)
	{
		ItemInfoData itemInfoData = new ItemInfoData();
		itemInfoData.setUuid(String.valueOf(item.getDbId()));
		itemInfoData.setTemplateId(item.getTemplateId());
		itemInfoData.setBeginTime(item.getBeginTime());
		itemInfoData.setDuration(item.getDuration());
		itemInfoData.setOverlap(item.getOverlap());
		return itemInfoData;
	}
	
	public static ItemInfoData[] convertFromItemList(List<Item> itemList)
	{
		ItemInfoData []itemInfoDataList = new ItemInfoData[itemList.size()];
		for(int i=0;i<itemList.size();i++)
		{
			itemInfoDataList[i] = convertFromItem(itemList.get(i));
		}
	
		return itemInfoDataList;
	}
}
