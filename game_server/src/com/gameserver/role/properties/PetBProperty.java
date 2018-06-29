package com.gameserver.role.properties;

import com.core.annotation.Comment;

/**
 * 武将二级属性索引定义
 * @author Thinker
 * 
 */
@Comment(content="武将二级属性")
public final class PetBProperty extends FloatNumberPropertyObject
{
	/** 二级属性索引开始值 */
	public static int _BEGIN = 0;

	/** 二级属性索引结束值 */
	public static int _END = _BEGIN;
	
	/** 二级属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;
	
	public static final int TYPE = PropertyType.PET_PROP_B; 
	
	/**
	 * 是否是合法的索引
	 * 
	 * @param index
	 * @return
	 */
	public static final boolean isValidIndex(int index)
	{
		return index>=0&&index<PetBProperty._SIZE;
	}
	
	public PetBProperty() 
	{
		super(_SIZE,TYPE);
	}
}
