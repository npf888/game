package com.gameserver.player.data;

import com.db.model.HumanEntity;


/**
 * 角色信息
 * @author Thinker
 *
 */
public class RoleInfo
{
	/** 角色ID */
	private long roleUUID;
	/** 职业类型 */
	private int vocation;
	/** 名字 */
	private String name;
	/** 级别 */
	private long level;
	/** 装备品质 */
	private int equipquality;
	
	/** 账户id */
	private long passportId;

	
	/** 是否为首次登陆 */
	private int firstLogin;
	/** 删除标记*/
	private int deleted;
	
	public RoleInfo()
	{
		
	}
	
	public RoleInfo(long roleUUID, String name) {
		super();
		this.roleUUID = roleUUID;
		this.name = name;
	}
	
	public long getRoleUUID() {
		return roleUUID;
	}

	public void setRoleUUID(long roleUUID) {
		this.roleUUID = roleUUID;
	}

	
	public int getVocation() {
		return vocation;
	}

	public void setVocation(int vocation) {
		this.vocation = vocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public int getEquipquality() {
		return equipquality;
	}

	public void setEquipquality(int equipquality) {
		this.equipquality = equipquality;
	}

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}


	public int getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(int firstLogin) {
		this.firstLogin = firstLogin;
	}
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	/**
	 * 将角色信息转换为数据库实体
	 * 
	 */
	public HumanEntity toEntity()
	{
		HumanEntity entity = new HumanEntity();
		entity.setPassportId(this.passportId);
		entity.setId(this.roleUUID);

		entity.setName(this.name);
		entity.setLevel(this.level);
		return entity;
	}
}
