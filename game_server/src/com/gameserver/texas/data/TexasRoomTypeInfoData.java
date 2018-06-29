package com.gameserver.texas.data;

import com.gameserver.texas.template.TexasRoomTemplate;

public class TexasRoomTypeInfoData {
	
	/** 房间类型id*/
	private int typeId;
	/** 房间标签 */
	private int roomTag;
	/**开启*/
	private int openUp;
	/**小盲注*/
	private int smallBlind;
	/**最小携带量*/
	private int minCarry;
	/**最大携带量*/
	private int maxCarry;
	/**房间人数*/
	private int roomNum;
	/**总人数*/
	private int totalNum;
	/**进入等级 */
	private int openLv;
	/**显示类型 1 2 3 4*/
	private int list;
	
	/**彩金*/
	private long handsel;
	
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getRoomTag() {
		return roomTag;
	}
	public void setRoomTag(int roomTag) {
		this.roomTag = roomTag;
	}
	public int getOpenUp() {
		return openUp;
	}
	public void setOpenUp(int openUp) {
		this.openUp = openUp;
	}
	public int getSmallBlind() {
		return smallBlind;
	}
	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}
	public int getMinCarry() {
		return minCarry;
	}
	public void setMinCarry(int minCarry) {
		this.minCarry = minCarry;
	}
	public int getMaxCarry() {
		return maxCarry;
	}
	public void setMaxCarry(int maxCarry) {
		this.maxCarry = maxCarry;
	}
	
	
	public int getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	
	public int getOpenLv() {
		return openLv;
	}
	public void setOpenLv(int openLv) {
		this.openLv = openLv;
	}
	public int getList() {
		return list;
	}
	public void setList(int list) {
		this.list = list;
	}
	public long getHandsel() {
		return handsel;
	}
	public void setHandsel(long handsel) {
		this.handsel = handsel;
	}
	public static TexasRoomTypeInfoData convertFromTexasRoomTemplate(TexasRoomTemplate texasRoomTemplate)
	{
		TexasRoomTypeInfoData texasRoomTypeInfoData = new TexasRoomTypeInfoData();
		texasRoomTypeInfoData.setTypeId(texasRoomTemplate.getId());
		texasRoomTypeInfoData.setOpenUp(texasRoomTemplate.getOpenUp());
		texasRoomTypeInfoData.setRoomTag(texasRoomTemplate.getRoomType());
		texasRoomTypeInfoData.setSmallBlind(texasRoomTemplate.getSmallBlind());
		texasRoomTypeInfoData.setMinCarry(texasRoomTemplate.getMinCarry());
		texasRoomTypeInfoData.setMaxCarry(texasRoomTemplate.getMaxCarry());
		texasRoomTypeInfoData.setRoomNum(texasRoomTemplate.getRoomNum());
		texasRoomTypeInfoData.setOpenLv(texasRoomTemplate.getOpenLv());
		texasRoomTypeInfoData.setList(texasRoomTemplate.getList());
		texasRoomTypeInfoData.setHandsel(0);
		return texasRoomTypeInfoData;
	}
	
	@Override
	public String toString()
	{
		return String.format("房间类型id:%d,小盲注:%d,最小携带:%d,最大携带:%d 人数:%d 总人数:%d 进入等级：%d  显示类型：%d 彩金：%d",getTypeId(),
				getSmallBlind(),getMinCarry(),getMaxCarry(),getRoomNum(),getTotalNum(),getOpenLv(),getList(),getHandsel() );

	}
}
