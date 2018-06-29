package com.common.model;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;



/**
 * 同步状态
 * @author Thinker
 *
 */
public class SynStatus
{
	private int serverID;
	private int synOper;
	private int status;
	public int getServerID() {
		return serverID;
	}
	public void setServerID(int serverID) {
		this.serverID = serverID;
	}

	public int getSynOper() {
		return synOper;
	}
	public void setSynOper(int synOper) {
		this.synOper = synOper;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public enum GameServerSynStatEnum  implements IndexedEnum
	{
		NEW(0),
		READY(1),
		SYN_SUCCESS(2),
		DELETE(3);
		;

		private final int index;
		/** 按索引顺序存放的枚举数组 */
		private static final List<GameServerSynStatEnum> values = IndexedEnumUtil.toIndexes(GameServerSynStatEnum.values());
		
		/**
		 * 
		 * @param index
		 * 枚举的索引,从0开始
		 */
		private GameServerSynStatEnum(int index)
		{
			this.index = index;
		}

		@Override
		public int getIndex() 
		{
			return this.index;
		}

		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static GameServerSynStatEnum valueOf(final int index)
		{
			return EnumUtil.valueOf(values, index);
		}
		
	}
	
	/**
	 * 同步操作定义
	 *@author 
	*/
	public enum SynOperation implements IndexedEnum 
	{
		NULL(0),
		ADD(1),
		DELETE(2),
		UPDATE(3);
		;

		private final int index;
		/** 按索引顺序存放的枚举数组 */
		private static final List<SynOperation> values = IndexedEnumUtil.toIndexes(SynOperation.values());
		
		/**
		 * 
		 * @param index
		 * 枚举的索引,从0开始
		 */
		private SynOperation(int index)
		{
			this.index = index;
		}

		@Override
		public int getIndex() 
		{
			return this.index;
		}

		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static SynOperation valueOf(final int index)
		{
			return EnumUtil.valueOf(values, index);
		}
	}
}
