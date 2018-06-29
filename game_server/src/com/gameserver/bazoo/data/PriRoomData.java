package com.gameserver.bazoo.data;
/**
 * 私人房间的列表信息
 * @author JavaServer
 *
 */
public class PriRoomData {

	private String roomNumber;
	private String creater;
	private long createrPassportId;
	private int flag;
	private int vip;
	private int modeType;
	private int bet;
	private String img;
	private String numTotalNum;// 房间当前人数/房间总人数
	private int isNeedPassword;//是否需要密码：0需要， 1不需要
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public long getCreaterPassportId() {
		return createrPassportId;
	}
	public void setCreaterPassportId(long createrPassportId) {
		this.createrPassportId = createrPassportId;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getModeType() {
		return modeType;
	}
	public void setModeType(int modeType) {
		this.modeType = modeType;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getNumTotalNum() {
		return numTotalNum;
	}
	public void setNumTotalNum(String numTotalNum) {
		this.numTotalNum = numTotalNum;
	}
	public int getIsNeedPassword() {
		return isNeedPassword;
	}
	public void setIsNeedPassword(int isNeedPassword) {
		this.isNeedPassword = isNeedPassword;
	}
	public int getVip() {
		return vip;
	}
	public void setVip(int vip) {
		this.vip = vip;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	
	
}
