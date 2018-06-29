package com.gameserver.role.properties;

import com.core.annotation.Comment;
import com.core.annotation.Type;

/**
 * 基础属性(人物角色，宠物公用)：Object类型，存放Long及非数值类型的属性
 * @author Thinker
 * 
 */
public class RoleBaseStrProperties extends PropertyObject
{
	/** 基础对象型属性索引开始值 */
	private static int _BEGIN = 0;
	
	/** 基础对象型属性索引结束值 */
	public static int _END = _BEGIN;


	@Comment(content = "创建时间")
	@Type(Long.class)
	public static final int CREATETIME = ++_END;
	
	
	/** 基础整型属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;

	public static final int TYPE = PropertyType.BASE_ROLE_PROPS_STR;

	public RoleBaseStrProperties() {
		super(_SIZE, TYPE);
	}

}
