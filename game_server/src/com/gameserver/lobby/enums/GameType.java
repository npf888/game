package com.gameserver.lobby.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 百家乐 1 德州扑克 2 老虎机 classic：3  老虎机 四大美人：4 老虎机 水果：5
 * @author 郭君伟
 *  
 */
public enum GameType implements IndexedEnum {
	/** 百家乐**/
	BACCART(1),
	/** 德州扑克**/
	TEXAS(2),
	/** 老虎机 classic**/
	SLOT_CLASSIC(3),
	/**老虎机 四大美人 **/
	SLOT_BEAUTY(4),
	/**老虎机 水果 **/
	SLOT_FRUITS(5),
	/**老虎机 沙滩 **/
	SLOT_BEACH(6),
	/**老虎机 吸血鬼 **/
	SLOT_VAMPIRE(7),
	/**老虎机 埃及艳后 **/
	SLOT_CLEOPATRA(8),
	/**老虎机美人鱼 **/
	SLOT_MERMAID(9),
	/**澳门女神 **/
	SLOT_MACAU(10),
	/**白蛇传 **/
	SLOT_SNAKE(11),
	/**马来网红 **/
	SLOT_NETRED(12)
	;
	
	private int index;
	private GameType(int index){
		this.index = index;
	}

  private static final List<GameType> indexes = IndexedEnumUtil.toIndexes(GameType.values());

	
	@Override
	public int getIndex() {
		return index;
	}
	
	public static GameType indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}

}
