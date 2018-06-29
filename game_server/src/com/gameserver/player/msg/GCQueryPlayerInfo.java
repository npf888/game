package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端请求用户信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCQueryPlayerInfo extends GCMessage{
	
	/** 玩家信息 */
	private com.gameserver.player.data.PlayerInfoData playerInfoData;
	/** 玩家信息 */
	private com.gameserver.bazoopersonal.data.BazooPersonalInfo[] bazooPersonalInfo;

	public GCQueryPlayerInfo (){
	}
	
	public GCQueryPlayerInfo (
			com.gameserver.player.data.PlayerInfoData playerInfoData,
			com.gameserver.bazoopersonal.data.BazooPersonalInfo[] bazooPersonalInfo ){
			this.playerInfoData = playerInfoData;
			this.bazooPersonalInfo = bazooPersonalInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		playerInfoData = new com.gameserver.player.data.PlayerInfoData();
					playerInfoData.setPlayerId(readLong());
							playerInfoData.setName(readString());
							playerInfoData.setImg(readString());
							playerInfoData.setGold(readLong());
							playerInfoData.setDiamond(readLong());
							playerInfoData.setCharm(readLong());
							playerInfoData.setLevel(readLong());
							playerInfoData.setSex(readInteger());
							playerInfoData.setViplevel(readInteger());
							playerInfoData.setCountries(readString());
							playerInfoData.setAge(readInteger());
							playerInfoData.setSlotRotate(readLong());
							playerInfoData.setSlotWin(readLong());
							playerInfoData.setSlotSingleWin(readLong());
							playerInfoData.setSlotWinNum(readLong());
							playerInfoData.setIntegral(readLong());
							playerInfoData.setIsRequest(readInteger());
							playerInfoData.setNewGuyGift(readInteger());
							playerInfoData.setClubId(readString());
							playerInfoData.setClubIco(readInteger());
							playerInfoData.setClubInvitedTimes(readInteger());
							playerInfoData.setAchieveRate(readString());
				count = readShort();
		count = count < 0 ? 0 : count;
		bazooPersonalInfo = new com.gameserver.bazoopersonal.data.BazooPersonalInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoopersonal.data.BazooPersonalInfo obj = new com.gameserver.bazoopersonal.data.BazooPersonalInfo();
			obj.setModeType(readInteger());
			obj.setNumberOfGame(readInteger());
			obj.setSingleTopGold(readLong());
			obj.setRateOfWinning(readInteger());
			obj.setAWinningStreak(readInteger());
			obj.setPassToKill(readInteger());
			obj.setBigPatterns(readString());
			obj.setPantherNumber(readInteger());
			obj.setThreeKill(readInteger());
			obj.setFourKill(readInteger());
			obj.setFiveKill(readInteger());
			obj.setDayProfit(readLong());
			obj.setWeekProfit(readLong());
			obj.setMonthProfit(readLong());
			bazooPersonalInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerInfoData.getPlayerId());
		writeString(playerInfoData.getName());
		writeString(playerInfoData.getImg());
		writeLong(playerInfoData.getGold());
		writeLong(playerInfoData.getDiamond());
		writeLong(playerInfoData.getCharm());
		writeLong(playerInfoData.getLevel());
		writeInteger(playerInfoData.getSex());
		writeInteger(playerInfoData.getViplevel());
		writeString(playerInfoData.getCountries());
		writeInteger(playerInfoData.getAge());
		writeLong(playerInfoData.getSlotRotate());
		writeLong(playerInfoData.getSlotWin());
		writeLong(playerInfoData.getSlotSingleWin());
		writeLong(playerInfoData.getSlotWinNum());
		writeLong(playerInfoData.getIntegral());
		writeInteger(playerInfoData.getIsRequest());
		writeInteger(playerInfoData.getNewGuyGift());
		writeString(playerInfoData.getClubId());
		writeInteger(playerInfoData.getClubIco());
		writeInteger(playerInfoData.getClubInvitedTimes());
		writeString(playerInfoData.getAchieveRate());
		writeShort(bazooPersonalInfo.length);
		for(int i=0; i<bazooPersonalInfo.length; i++){
			writeInteger(bazooPersonalInfo[i].getModeType());
			writeInteger(bazooPersonalInfo[i].getNumberOfGame());
			writeLong(bazooPersonalInfo[i].getSingleTopGold());
			writeInteger(bazooPersonalInfo[i].getRateOfWinning());
			writeInteger(bazooPersonalInfo[i].getAWinningStreak());
			writeInteger(bazooPersonalInfo[i].getPassToKill());
			writeString(bazooPersonalInfo[i].getBigPatterns());
			writeInteger(bazooPersonalInfo[i].getPantherNumber());
			writeInteger(bazooPersonalInfo[i].getThreeKill());
			writeInteger(bazooPersonalInfo[i].getFourKill());
			writeInteger(bazooPersonalInfo[i].getFiveKill());
			writeLong(bazooPersonalInfo[i].getDayProfit());
			writeLong(bazooPersonalInfo[i].getWeekProfit());
			writeLong(bazooPersonalInfo[i].getMonthProfit());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QUERY_PLAYER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUERY_PLAYER_INFO";
	}

	public com.gameserver.player.data.PlayerInfoData getPlayerInfoData(){
		return playerInfoData;
	}
		
	public void setPlayerInfoData(com.gameserver.player.data.PlayerInfoData playerInfoData){
		this.playerInfoData = playerInfoData;
	}

	public com.gameserver.bazoopersonal.data.BazooPersonalInfo[] getBazooPersonalInfo(){
		return bazooPersonalInfo;
	}

	public void setBazooPersonalInfo(com.gameserver.bazoopersonal.data.BazooPersonalInfo[] bazooPersonalInfo){
		this.bazooPersonalInfo = bazooPersonalInfo;
	}	
}