package com.login;


/**
 * 
 * @author Thinker
 *
 */
public class DataServerLogin
{
	/**与APP通信的用户key */
	private String openId;
	/** session key */
	private String openkey;
	/** 应用的唯一ID */
	private String appid;
	/** 密钥 */
	private String sig;
	/** 应用的来源平台 */
	private String pf;
	/** 第3方平台ID */
	private long passpodId;
	/** 默认角色ID */
	private long roleId;
	/** 名称 */
	private String roleName;
	/** 人物默认头像地址 */
	private String headerURL;
	/** 防沉迷系统级别  >0 已经需要防沉迷*/
	private int wallow;
	
	/** 最近时间 */
	private long lasttime;
	
	/** 用户在线事件 */
	private long OnlineTime;
	
	/**错误码*/
	private int errorCode;
	
	/** 数据是否正常 */
	public boolean check(){
		if (openId == null || openId.length() < 1)
			return false;
		if (openkey == null || openkey.length() < 1)
			return false;
		if (appid == null || appid.length() < 1)
			return false;
		if (sig == null || sig.length() < 1)
			return false;
		if (pf == null || pf.length() < 1)
			return false;
		return true;
	}
	
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getOpenkey() {
		return openkey;
	}
	public void setOpenkey(String openkey) {
		this.openkey = openkey;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSig() {
		return sig;
	}
	public void setSig(String sig) {
		this.sig = sig;
	}
	public String getPf() {
		return pf;
	}
	public void setPf(String pf) {
		this.pf = pf;
	}
	public long getLasttime() {
		return lasttime;
	}
	public void setLasttime(long lasttime) {
		this.lasttime = lasttime;
	}


	public long getPasspodId() {
		return passpodId;
	}


	public void setPasspodId(long passpodId) {
		this.passpodId = passpodId;
	}


	public long getRoleId() {
		return roleId;
	}


	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getHeaderURL() {
		return headerURL;
	}


	public void setHeaderURL(String headerURL) {
		this.headerURL = headerURL;
	}


	public int getWallow() {
		return wallow;
	}


	public void setWallow(int wallow) {
		this.wallow = wallow;
	}


	public long getOnlineTime() {
		return OnlineTime;
	}


	public void setOnlineTime(long onlineTime) {
		OnlineTime = onlineTime;
	}


	public int getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	
	
}
