package com.db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 用户信息
 * @author Thinker
 */
@Entity
@Table(name = "t_user_info")
public class UserInfo implements BaseEntity<Long>
{
	private static final long serialVersionUID = -6420558996304842663L;

	/** 登陆标识 */
	private long id;
	/** 用户名 */
	private String name;
	/**用户名**/
	private String username;
	/**密码 **/
	private String password;
	/**渠道(相同的渠道用户名不能重复，不同的渠道用户名可以重复) **/
	private String channel;
	
	/**博趣 平台的 token_type **/
	 private String tokenType;
	/**博趣 平台的 access_token（这个平台的唯一标识） **/
	private String accessToken;
	/**账号*/
	private String accountId;
	/**facebook id*/
	private String facebookId;
	/** img */
	private String img;
	/** join time*/
	private long joinTime;
	/** 上次登陆时间 */
	private long lastLoginTime;
	/** 权限 */
	private int role;
	/** 锁定状态 */
	private int lockStatus;
	/** 锁定时间 */
	private int muteTime;
	/** 锁定原因 */
	private String muteReason;
	/** 移动设备Mac */
	private String deviceMac;
	 /*需要同步facebook信息到gameserver*/
	 private boolean updateFbInfo;
	
	@Id
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	@Column
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}
	@Column
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	@Column
	public int getRole() {
		return role;
	}
	
	public void setRole(int role) {
		this.role = role;
	}
	@Column
	public int getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(int lockStatus) {
		this.lockStatus = lockStatus;
	}
	@Column
	public int getMuteTime() {
		return muteTime;
	}
	public void setMuteTime(int muteTime) {
		this.muteTime = muteTime;
	}
	@Column
	public String getMuteReason() {
		return muteReason;
	}
	@Column
	public void setMuteReason(String muteReason) {
		this.muteReason = muteReason;
	}

	@Column
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public boolean isUpdateFbInfo() {
		return updateFbInfo;
	}
	public void setUpdateFbInfo(boolean updateFbInfo) {
		this.updateFbInfo = updateFbInfo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	

}
