package com.gameserver.role.properties;


/**
 * 定义属性对象的类型
 * @author Thinker
 */
public abstract class PropertyType
{
	public final static int DUMY = 1;
	
	/** 公用(角色，武将)的一级属性 */
	public final static int PET_PROP_A = 3;
	/** 公用(角色，武将)的二级属性 */
	public final static int PET_PROP_B = 4;
	
	/** 基础属性（角色，武将）： 数值类型  int */
	public final static int BASE_ROLE_PROPS_INT = 5;
	/** 基础属性（角色，武将）： 非数值类型  String */
	public final static int BASE_ROLE_PROPS_STR = 6;
	
	/** 角色属性：Object类型，存放在游戏过程中不改变，或者无需告知客户端的各种类型属性 */
	public final static int ROLE_PROPS_FINAL = 7;

	/**
	 * 产生属性的KEY值，用于服务器之间，服务器和客户端之间数据发送接受
	 * 
	 * @param index
	 *           属性在Property类中的索引
	 * @param propertyType
	 *           Property类的类型
	 * @return
	 */
	public static int genPropertyKey(int index , int propertyType)
	{
		return propertyType * 100 + index;
	}
	
	public static int indexPropertyKey(int genKey , int propertyType)
	{
		return genKey-propertyType * 100;
	}
	
	public static void assertPropertyType(int propType) 
	{
		if (propType != PetAProperty.TYPE && propType != PetBProperty.TYPE)
		{
			throw new IllegalArgumentException("Not a valid PropLevel key ["+ propType + "]");
		}
	}
}
