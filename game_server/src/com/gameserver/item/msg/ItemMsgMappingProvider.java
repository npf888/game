package com.gameserver.item.msg;

import java.util.HashMap;
import java.util.Map;

import com.gameserver.common.msg.MessageType;
import com.common.MessageMappingProvider;

/**
 *  Generated by MessageCodeGenerator,don't modify please.
 *  Need to register in<code>GameMessageRecognizer#init</code>
 */
public class ItemMsgMappingProvider implements MessageMappingProvider {

	@Override
	public Map<Short, Class<?>> getMessageMapping() {
		Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
		map.put(MessageType.CG_SEND_INTERACTIVE_ITEM, CGSendInteractiveItem.class);
		map.put(MessageType.CG_GROUP_SEND_INTERACTIVE_ITEM, CGGroupSendInteractiveItem.class);
		map.put(MessageType.CG_BAZOO_MALL_REQUEST, CGBazooMallRequest.class);
		map.put(MessageType.CG_BAZOO_ITEM_REQUEST, CGBazooItemRequest.class);
		map.put(MessageType.CG_BAZOO_ITEM_CLOCK_CHANGE, CGBazooItemClockChange.class);
		map.put(MessageType.CG_BAZOO_ITEM_BUY_BY_GOLD, CGBazooItemBuyByGold.class);
		return map;
	}

}
