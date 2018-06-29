package com.gameserver.role;

import java.util.List;

import com.core.util.KeyValuePair;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.unit.DynamicUnit;
import com.gameserver.human.Human;
import com.gameserver.human.msg.GCRoleSymbolChangedLong;
import com.gameserver.role.properties.PropertyType;
import com.gameserver.role.properties.RoleBaseIntProperties;
import com.gameserver.role.properties.RoleBaseLongProperties;
import com.gameserver.role.properties.RoleBaseStrProperties;


/**
 * 抽象角色类
 * @author Thinker
 *
 */
public abstract class Role extends DynamicUnit
{
	/** 角色类型 */
	protected short roleType;
	/** 角色的属性定义：角色在游戏过程中对客户端不可见的属性 */
	protected final RoleFinalProps finalProps = new RoleFinalProps();
	/** 基础属性：整型 */
	protected final RoleBaseLongProperties baseIntProperties;
	/** 基础属性：对象型 */
	protected final RoleBaseStrProperties baseStrProperties;

	public Role(short roleType)
	{
		this.roleType = roleType;
		baseIntProperties = new RoleBaseLongProperties();
		baseStrProperties = new RoleBaseStrProperties();
	}
	
	public void setRoleType(short roleType)
	{
		this.roleType = roleType;
	}

	public short getRoleType() 
	{
		return roleType;
	}
	

	@Override
	public void heartBeat() 
	{
		
	}
	

	/**
	 * 重置所有属性的修改标识
	 * 
	 * @param reset
	 */
	public void resetChange()
	{
		this.finalProps.resetChanged();
		this.baseIntProperties.resetChanged();
		this.baseStrProperties.resetChanged();
	}

	public long getLevel() 
	{
		return this.baseIntProperties.getPropertyValue(RoleBaseIntProperties.LEVEL);
	}

	public void setLevel(long level)
	{
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.LEVEL,level);
		this.onModified();
	}

	/**
	 * 获取当前经验值
	 * 
	 * @return
	 */
	public long getCurExp()
	{
		return this.baseIntProperties.getPropertyValue(RoleBaseIntProperties.CUR_EXP);
	}

	/**
	 * 获取当前经验值
	 * 
	 * @param currExp
	 *            当前经验值
	 * @return
	 */
	public void setCurExp(long curExp)
	{
		this.baseIntProperties.setPropertyValue(RoleBaseIntProperties.CUR_EXP,curExp);
		this.onModified();
	}
	
	
	/**
	 * 获取升级所需经验值
	 * 
	 * @return
	 * 
	 */
	public long getMaxExp()
	{
		return baseIntProperties.getPropertyValue(RoleBaseIntProperties.MAX_EXP);
	}
	
	public RoleBaseLongProperties getBaseIntProperties()
	{
		return baseIntProperties;
	}

	public RoleBaseStrProperties getBaseStrProperties()
	{
		return baseStrProperties;
	}
	
	/**
	 * 将有改动的数值数据发送到客户端
	 * 
	 * @param reset
	 *            如果reset标识为true,则会在快照完成后重置更新状态
	 */
	
	public void snapChangedProperty(boolean reset)
	{
		// 如果 LevelA,LevelB,DynamicNumProp,DynamicOtherProp 均无变化，则返回NULL
		if ( !this.baseIntProperties.isChanged()
				&& !this.baseStrProperties.isChanged())
		{
			return;
		}
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Long>> _numChanged = changedNum();
		// 保存字符串类属性变化
		KeyValuePair<Integer, String>[] _strChanged = changedStr();
		
		KeyValuePair<Integer, Long>[] empty = KeyValuePair.newKeyValuePairArray(0);
		
		if (_numChanged != null && !_numChanged.isEmpty())
		{
			sendMessage(new GCRoleSymbolChangedLong(getRoleType(), this.getUUID(), _numChanged.toArray(empty)));
		}

		if (_strChanged != null && _strChanged.length > 0)
		{
			//sendMessage(new GCRoleSymbolChangedLong(getRoleType(), this.getUUID(), _strChanged));
		}

		if (reset)
		{
			resetChange();
		}
	}

	protected KeyValuePair<Integer, String>[] changedStr() 
	{
		// 保存字符串类属性变化
		KeyValuePair<Integer, String>[] _strChanged = null;
		// 处理 baseStrProps
		if (this.baseStrProperties.isChanged())
		{

			Object[] _dOtherPropsChangedValues = this.baseStrProperties
					.getChanged(); // Object
			int[] _indexs = (int[]) _dOtherPropsChangedValues[0];
			Object[] _values = (Object[]) _dOtherPropsChangedValues[1];
			_strChanged = KeyValuePair.newKeyValuePairArray(_indexs.length);
			for (int i = 0; i < _indexs.length; i++) {
				_strChanged[i] = new KeyValuePair<Integer, String>(PropertyType.genPropertyKey(
						_indexs[i],PropertyType.BASE_ROLE_PROPS_STR),
						_values[i].toString());
			}
		}
		return _strChanged;
	}
	
	abstract public long getUUID();
	
	/**
	 * 向前端发送消息
	 * 
	 * @see Human#sendMessage(GCMessage)
	 */
	abstract protected void sendMessage(GCMessage msg) ;
	

	/**
	 * 当属性被修改时调用
	 */
	abstract protected void onModified();
	
	abstract protected List<KeyValuePair<Integer, Long>> changedNum();
}
