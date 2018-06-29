package com.db.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;


/**
 * 
 * 客户端设备记录Entity
 * 
 * @author haijiang.jin
 *
 */
@Entity
@Table(name = "t_device")
public class DeviceEntity implements BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String deviceType;
	
	private String serverid;
	
	private long userid;
	
	private String username;
	
	
	private String deviceId;
	
	private String deviceMode;
	
	private String osVersion;
	
	private int channelType;
	
	private String clientVersion;
	
	private int resourceVersion;
	
	private long updatetime;
	
	private long createTime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		if(id != null){
			this.id = id;
		}
	}



	@Column
	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	@Column
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	@Column
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Column(nullable = false)
	public long getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(long updatetime) {
		this.updatetime = updatetime;
	}


	

	@Column
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Column
	public String getDeviceMode() {
		return deviceMode;
	}



	public void setDeviceMode(String deviceMode) {
		this.deviceMode = deviceMode;
	}
	
	@Column
	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	@Column
	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	@Column
	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	@Column
	public int getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(int resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	@Column
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	
}
