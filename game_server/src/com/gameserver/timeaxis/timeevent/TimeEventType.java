package com.gameserver.timeaxis.timeevent;

import com.core.enums.IndexedEnum;

/**
 * 事件类型
 * @author baoliang.shen
 *
 */
public enum TimeEventType implements IndexedEnum{
	TOKEN_GIVE_SPECIAL(1),
	CAMPAIGN_START(2),
	CAMPAIGN_STOP(3),
	STRONGHOLD_FIGHT(4),
	STRONGHOLD_HOLDEN_END(5),
	STRONGHOLD_CD_END(6),
	STRONGHOLD_GIFT_SEND(7),
	UPDATE_TRAINING(8),
	GAMEOPERATOR_START(9),
	GAMEOPERATOR_STOP(10),
	MINEWAR_DOUBLE_STOP(11),
	/** 商城物品上架 */
	SHOPMALL_ITEM_ON(12),	
	/** 商城物品下架 */
	SHOPMALL_ITEM_OFF(13),	
	;

	private final int index;
	private TimeEventType(int index) {
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
}
