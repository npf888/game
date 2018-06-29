package com.gameserver.player.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 响应用户校验登录
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCheckPlayerLogin extends GCMessage{
	
	/** 玩家的登录id */
	private long loginId;
	/** facebookid */
	private String facebookId;
	/** 账号id */
	private String accountId;
	/** 玩家图片 */
	private String img;
	/** 间距时间 */
	private long utcOffset;
	/** 账户类型 */
	private int playerRole;

	public GCCheckPlayerLogin (){
	}
	
	public GCCheckPlayerLogin (
			long loginId,
			String facebookId,
			String accountId,
			String img,
			long utcOffset,
			int playerRole ){
			this.loginId = loginId;
			this.facebookId = facebookId;
			this.accountId = accountId;
			this.img = img;
			this.utcOffset = utcOffset;
			this.playerRole = playerRole;
	}

	@Override
	protected boolean readImpl() {
		loginId = readLong();
		facebookId = readString();
		accountId = readString();
		img = readString();
		utcOffset = readLong();
		playerRole = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(loginId);
		writeString(facebookId);
		writeString(accountId);
		writeString(img);
		writeLong(utcOffset);
		writeInteger(playerRole);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHECK_PLAYER_LOGIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHECK_PLAYER_LOGIN";
	}

	public long getLoginId(){
		return loginId;
	}
		
	public void setLoginId(long loginId){
		this.loginId = loginId;
	}

	public String getFacebookId(){
		return facebookId;
	}
		
	public void setFacebookId(String facebookId){
		this.facebookId = facebookId;
	}

	public String getAccountId(){
		return accountId;
	}
		
	public void setAccountId(String accountId){
		this.accountId = accountId;
	}

	public String getImg(){
		return img;
	}
		
	public void setImg(String img){
		this.img = img;
	}

	public long getUtcOffset(){
		return utcOffset;
	}
		
	public void setUtcOffset(long utcOffset){
		this.utcOffset = utcOffset;
	}

	public int getPlayerRole(){
		return playerRole;
	}
		
	public void setPlayerRole(int playerRole){
		this.playerRole = playerRole;
	}
}