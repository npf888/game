package com.gameserver.compensation.data;


import com.db.model.CompensationEntity;

/**
 * 补偿数据
 * @author wayne
 *
 */
public class CompensationData {
	private long id;
	private String title;
	private String content;
	private String itemPack;
	private long updateTime;
	private long createTime;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getItemPack() {
		return itemPack;
	}

	public void setItemPack(String itemPack) {
		this.itemPack = itemPack;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public static  CompensationEntity convertFromCompensationData(CompensationData compensationData){
		CompensationEntity compensationEntity = new CompensationEntity();
		compensationEntity.setId(compensationData.getId());
		compensationEntity.setTitle(compensationData.getTitle());
		compensationEntity.setContent(compensationData.getContent());
		compensationEntity.setItemPack(compensationData.getItemPack());
		compensationEntity.setUpdateTime(compensationData.getUpdateTime());
		compensationEntity.setCreateTime(compensationData.getCreateTime());
		return compensationEntity;
	}
}
