package com.gameserver.role;

import java.sql.Timestamp;

import com.core.annotation.Type;
import com.gameserver.role.properties.PropertyObject;
import com.gameserver.role.properties.PropertyType;

/**
 * 角色在游戏过程中对客户端不可见的属性
 * @author Thinker
 * 
 */
public class RoleFinalProps extends PropertyObject
{
	private static final int BEGIN = 0;

	/** 账号ID */
	@Type(Long.class)
	public static final int PASSPORT_ID = BEGIN + 0;
	/** 最近一次登入，单位毫秒 */
	@Type(Timestamp.class)
	public static final int LAST_LOGIN = PASSPORT_ID + 1;
	/** 最近一次登出，单位毫秒 */
	@Type(Timestamp.class)
	public static final int LAST_LOGOUT = LAST_LOGIN + 1;
	/** 最近一次登录ip */
	@Type(String.class)
	public static final int LAST_IP = LAST_LOGOUT + 1;
	/** 角色创建时间 */
	@Type(Timestamp.class)
	public static final int CREATE_TIME = LAST_IP + 1;
	/** 角色总在线时长 */
	@Type(Integer.class)
	public static final int TOTAL_MINUTE = CREATE_TIME + 1;
	/** 当日充值数额 */
	@Type(Integer.class)
	public static final int TODAY_CHARGE = TOTAL_MINUTE + 1;
	/** 总充值数额 */
	@Type(Integer.class)
	public static final int TOTAL_CHARGE = TODAY_CHARGE + 1;
	/** 最后充值时间 */
	@Type(Timestamp.class)
	public static final int LAST_CHARGE_TIME = TOTAL_CHARGE + 1;	
	/** 最后成为某级别VIP级别时间 */
	@Type(Timestamp.class)
	public static final int LAST_VIP_TIME = LAST_CHARGE_TIME + 1;

	private static final int END = LAST_VIP_TIME + 1;

	public RoleFinalProps()
	{
		super(END, PropertyType.ROLE_PROPS_FINAL);
	}
}
