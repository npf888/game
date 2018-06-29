package com.gameserver.redis.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 老虎机缓存字段
 * @author 郭君伟
 *
 */
public enum RedisSlotKey implements IndexedEnum {
	
	/**彩金 **/
	jackpot(1,"jackpot"),
	/**累计彩金 **/
	cumuJackpot(2,"cumuJackpot"),
	/**总的押注量 **/
	stock(3,"stock"),
	/**老虎机总的旋转次数 **/
	spinTimes(4,"spinTimes"),
	
	//老虎机竞赛缓存key======================================
	
	/**每个具体的老虎机 排行榜 **/
	slotIdRank(5,"slotIdRank"),
	
	//老虎机房间缓存Key=====================================
	
	slotRoomID(6,"slotRoomID"),
	
	//这里边装的都是 未满的 房间
	slotRoomNoenoughID(7,"slotRoomNoenoughID"),
	
	slotRoomIdKey(8,"slotRoomIdkey"),
	
	//斯巴达 对应房间 的子弹 --bullet
	bulletSlotRoom(9,"bullet")
	;

	private int index;
	private String key;
	
	private RedisSlotKey(int index,String key){
		this.index = index;
		this.key = key;
	}
	
	 private static final List<RedisSlotKey> indexes = IndexedEnumUtil.toIndexes(RedisSlotKey.values());

		@Override
		public int getIndex() {
			return index;
		}
		
		
		
		public String getKey() {
			return key;
		}



		public static RedisSlotKey indexOf(final int index)
		{
			return EnumUtil.valueOf(indexes, index);
		}
}
