package com.gameserver.role.properties;

import com.core.annotation.Comment;
import com.core.annotation.Type;

/**
 * 武将一级属性数据对象
 * 
 * @author Thinker
 */
@Comment(content = "武将一级属性")
public final class PetAProperty extends FloatNumberPropertyObject
{
	/** 一级属性索引开始值 */
	public static int _BEGIN = 0;
	
	/** 一级属性索引结束值 */
	public static int _END = _BEGIN;
	
	/** 血量上限 */
	@Comment(content = "血量上限")
	@Type(Integer.class)
	public static final int MAX_HP = ++_END;
	
	/** 攻击力 */
	@Comment(content = "攻击力")
	@Type(Integer.class)
	public static final int ATTACK = ++_END;
	
	/** 防御力 */
	@Comment(content = "防御力")
	@Type(Integer.class)
	public static final int DEFENCE = ++_END;
	
	/** 暴击 */
	@Comment(content = "暴击")
	@Type(Integer.class)
	public static final int CRIT = ++_END;
	
	/** 暴击抵抗 */
	@Comment(content = "暴击抵抗")
	@Type(Integer.class)
	public static final int CRIT_RESIST = ++_END;
	
	/** 闪避 */
	@Comment(content = "闪避")
	@Type(Integer.class)
	public static final int DODGE = ++_END;
	
	/** 命中 */
	@Comment(content = "命中")
	@Type(Integer.class)
	public static final int HIT = ++_END;
	
	/** 免伤：固定值 */
	@Comment(content = "免伤:固定值")
	@Type(Integer.class)
	public static final int AVOID_DAMAGE = ++_END;
	
	/** 暴击伤害 */
	@Comment(content = "暴击伤害")
	@Type(Integer.class)
	public static final int CRIT_DAMAGE = ++_END;
	
	/** 破甲伤害 */
	@Comment(content = "破甲伤害 ")
	@Type(Integer.class)
	public static final int WRECK_ARMOR_DAMAGE = ++_END;
	
	/** 伤害减免 */
	@Comment(content = "伤害减免 ")
	@Type(Integer.class)
	public static final int DAMAGE_DECREASE = ++_END;

	/** 一级属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;
	
	/**
	 * 是否是合法的索引
	 * 
	 * @param index
	 * @return
	 */
	public static final boolean isValidIndex(int index)
	{
		return index>=0&&index<PetAProperty._SIZE;
	}

	public static final int TYPE = PropertyType.PET_PROP_A;

	public PetAProperty() 
	{
		super(_SIZE, TYPE);
	}
}
