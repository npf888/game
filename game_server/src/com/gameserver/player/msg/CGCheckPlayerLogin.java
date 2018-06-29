package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 用户校验登录
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGCheckPlayerLogin extends CGMessage{
	
	/** 玩家的账户Id */
	private long userId;
	/** 玩家的随机码  */
	private String userCode;
	/** 设备码  */
	private String deviceId;
	/** 设备的类型 */
	private String deviceType;
	/** 设备机型 */
	private String deviceModel;
	/** 操作系统版本号 */
	private String osVersion;
	/** 渠道类型  */
	private int channelType;
	/** 客户端版本 */
	private String clientVersion;
	/** 客户端资源版本 */
	private int resourceVersion;
	/** 国家 */
	private String countries;
	
	public CGCheckPlayerLogin (){
	}
	
	public CGCheckPlayerLogin (
			long userId,
			String userCode,
			String deviceId,
			String deviceType,
			String deviceModel,
			String osVersion,
			int channelType,
			String clientVersion,
			int resourceVersion,
			String countries ){
			this.userId = userId;
			this.userCode = userCode;
			this.deviceId = deviceId;
			this.deviceType = deviceType;
			this.deviceModel = deviceModel;
			this.osVersion = osVersion;
			this.channelType = channelType;
			this.clientVersion = clientVersion;
			this.resourceVersion = resourceVersion;
			this.countries = countries;
	}
	
	@Override
	protected boolean readImpl() {
		userId = readLong();
		userCode = readString();
		deviceId = readString();
		deviceType = readString();
		deviceModel = readString();
		osVersion = readString();
		channelType = readInteger();
		clientVersion = readString();
		resourceVersion = readInteger();
		countries = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(userId);
		writeString(userCode);
		writeString(deviceId);
		writeString(deviceType);
		writeString(deviceModel);
		writeString(osVersion);
		writeInteger(channelType);
		writeString(clientVersion);
		writeInteger(resourceVersion);
		writeString(countries);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CHECK_PLAYER_LOGIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHECK_PLAYER_LOGIN";
	}

	public long getUserId(){
		return userId;
	}
		
	public void setUserId(long userId){
		this.userId = userId;
	}

	public String getUserCode(){
		return userCode;
	}
		
	public void setUserCode(String userCode){
		this.userCode = userCode;
	}

	public String getDeviceId(){
		return deviceId;
	}
		
	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}

	public String getDeviceType(){
		return deviceType;
	}
		
	public void setDeviceType(String deviceType){
		this.deviceType = deviceType;
	}

	public String getDeviceModel(){
		return deviceModel;
	}
		
	public void setDeviceModel(String deviceModel){
		this.deviceModel = deviceModel;
	}

	public String getOsVersion(){
		return osVersion;
	}
		
	public void setOsVersion(String osVersion){
		this.osVersion = osVersion;
	}

	public int getChannelType(){
		return channelType;
	}
		
	public void setChannelType(int channelType){
		this.channelType = channelType;
	}

	public String getClientVersion(){
		return clientVersion;
	}
		
	public void setClientVersion(String clientVersion){
		this.clientVersion = clientVersion;
	}

	public int getResourceVersion(){
		return resourceVersion;
	}
		
	public void setResourceVersion(int resourceVersion){
		this.resourceVersion = resourceVersion;
	}

	public String getCountries(){
		return countries;
	}
		
	public void setCountries(String countries){
		this.countries = countries;
	}
	


	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handleCheckPlayerLogin(this.getSession().getPlayer(), this);
	}
}