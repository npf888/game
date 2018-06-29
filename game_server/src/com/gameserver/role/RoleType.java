package com.gameserver.role;

/**
 * 角色类型定义:主要用来发送数据需求.属性相同对象不同的情况
 * @author Thinker
 * 
 */
public final class RoleType
{
	/** 玩家角色 */
	public static final short HUMAN = 1;
	/** 武将 */
	public static final short PET = 2;
	
	private RoleType()
	{
	}
}
