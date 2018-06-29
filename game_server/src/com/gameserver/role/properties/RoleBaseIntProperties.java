package com.gameserver.role.properties;

import java.util.BitSet;

import com.core.annotation.Comment;
import com.core.annotation.Type;

/**
 * 玩家角色整型属性:符号值定义
 * 
 * @author Thinker
 * 
 */
public class RoleBaseIntProperties extends GenericPropertyObject<Integer>
{
	/** 基础整型属性索引开始值 */
	public static int _BEGIN = 0;
	
	/** 基础整型属性索引结束值 */
	public static int _END = _BEGIN;
	

	/** VIP 等级 */
	@Comment(content = "VIP 等级")
	@Type(Integer.class)
	public static final int VIP_LEVEL = ++_END;

	/** 等级 */
	@Comment(content = "等级")
	@Type(Integer.class)
	public static final int LEVEL = ++_END;
	

	/** 钻石 */
	@Comment(content = "钻石")
	@Type(Integer.class)
	public static final int DIAMOND = ++_END;

	/** 金币 */
	@Comment(content = "金币")
	@Type(Integer.class)
	public static final int GOLD = ++_END;
	
	/** 点券 */
	@Comment(content = "点券")
	@Type(Integer.class)
	public static final int COUPON = ++_END;
	
	/** 魅力值 */
	@Comment(content = "魅力")
	@Type(Integer.class)
	public static final int CHARM = ++_END;
	
	/** 当前经验 */
	@Comment(content = "当前经验")
	@Type(Integer.class)
	public static final int CUR_EXP = ++_END;
	
	/** 经验值上限 */
	@Comment(content = "经验值上限")
	@Type(Integer.class)
	public static final int MAX_EXP = ++_END;



	

	/** 基础整型属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;

	public static final int TYPE = PropertyType.BASE_ROLE_PROPS_INT;

	/** 数值是否修改的副本标识 */
	private final BitSet shadowBitSet;

	public RoleBaseIntProperties() 
	{
		super(Integer.class, _SIZE, TYPE);
		this.shadowBitSet = new BitSet(this.size());
	}

	/**
	 * 重载{@link #resetChanged()},在重置前将props的修改记录下来
	 */
	@Override
	public void resetChanged()
	{
		this.props.fillChangedBit(this.shadowBitSet);
		super.resetChanged();
	}

	/**
	 * 是否有副本属性的修改
	 * 
	 * @return ture,有修改
	 */
	public boolean isShadowChanged()
	{
		return this.props.isChanged() || (!this.shadowBitSet.isEmpty());
	}

	/**
	 * 检查指定的副本属性索引是否有修改
	 * 
	 * @param index
	 * @return true,有修改;false,无修改
	 */
	public boolean isShadowChanged(final int index)
	{
		return this.props.isChanged(index) || this.shadowBitSet.get(index);
	}

	public void resetShadowChanged() 
	{
		this.shadowBitSet.clear();
	}

	/**
	 * 判定指定的属性索引是否有修改
	 * 
	 * @param index
	 * @return
	 */
	public boolean isChanged(int index) 
	{
		return this.props.isChanged(index);
	}

	/**
	 * 获取属性值
	 * 
	 * @param index
	 * @return
	 */
	@Override
	public Integer getPropertyValue(int index)
	{
		Integer value = props.get(index);
		if (value != null)
		{
			return value;
		} else
		{
			return 0;
		}
	}
}
