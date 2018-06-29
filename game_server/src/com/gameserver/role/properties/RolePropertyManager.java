package com.gameserver.role.properties;

import java.util.BitSet;

import com.core.util.Assert;
import com.core.util.KeyValuePair;
import com.gameserver.role.Role;


/**
 * 角色属性管理器
 * @author Thinker
 *
 * @param <T>
 * @param <V>
 */
public abstract class RolePropertyManager<T extends Role,V>
{
	/** 影响属性值的影响器标志 */
	/** ALL */
	public static final int PROP_FROM_MARK_ALL = 0xFFFFFFFF;
	
	/** 初始 */
	public static final int PROP_FROM_MARK_INIT = 0x0001;
	/** 等级 */
	public static final int PROP_FROM_MARK_LEVEL = 0x0002;	
	/** 装备 */
	public static final int PROP_FROM_MARK_EQUIP = 0x0004;
	/** 神器 */
	public static final int PROP_FROM_MARK_GODEQUIP = 0x0008;
	/** 武将附身 */
	public static final int PROP_FROM_MARK_PET_POSSESSED = 0x0010;
	/**珍宝阁 */
	public static final int PROP_FROM_MARK_JUMBO_COURT = 0x0020;
	/** 武将缘分 */
	public static final int PROP_FROM_MARK_PET_FATE= 0x0040;
	/** 被动技能 */
	public static final int PROP_FROM_MARK_PASSIVESKILL= 0x0080;
	/** 天书 */
	public static final int PROP_FROM_GOD_BOOK= 0x0100;
	/** 称号信息 */
	public static final int PROP_FROM_TITLE= 0x0110;
	

	/** 一级属性 */
	protected static final int CHANGE_INDEX_APROP = 0;
	/** 二级属性 */
	protected static final int CHANGE_INDEX_BPROP = 1;
	
	protected T owner;
	
	/** 一级、二级、抗性改变标志 */
	protected BitSet propChangeSet;
	
	public RolePropertyManager(T role,int bitSetSize)
	{
		Assert.notNull(role);
		owner = role;
		propChangeSet = new BitSet(bitSetSize);
	}
		
	/**
	 * 按指定的影响标识，更新一级属性
	 * 
	 * @param role
	 * @param effectMark
	 * @return
	 */
	abstract protected boolean updateAProperty(T role, int effectMask);

	/**
	 * 按指定的影响标识，更新二级属性
	 * 
	 * @param role
	 * @param effectMark
	 * @return
	 */
	abstract protected boolean updateBProperty(T role, int effectMask);
	
	/**
	 * 按标识更新属性
	 * @param effectMask
	 */
	abstract public void updateProperty(int effectMask);
	
	
	/**
	 * 获取所有改变
	 * @return
	 */
	abstract public KeyValuePair<Integer, V>[] getChanged();
	
	/**
	 * 一、二级或抗性是否有改变
	 * 
	 * @return
	 */
	public boolean isChanged()
	{
		return !propChangeSet.isEmpty();
	}

	/**
	 * 重置属性修改标识
	 */
	public void resetChanged()
	{
		propChangeSet.clear();
	}
}
