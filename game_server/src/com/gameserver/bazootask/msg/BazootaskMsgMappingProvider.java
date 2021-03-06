package com.gameserver.bazootask.msg;

import java.util.HashMap;
import java.util.Map;

import com.gameserver.common.msg.MessageType;
import com.common.MessageMappingProvider;

/**
 *  Generated by MessageCodeGenerator,don't modify please.
 *  Need to register in<code>GameMessageRecognizer#init</code>
 */
public class BazootaskMsgMappingProvider implements MessageMappingProvider {

	@Override
	public Map<Short, Class<?>> getMessageMapping() {
		Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
		map.put(MessageType.CG_BAZOO_TASK, CGBazooTask.class);
		map.put(MessageType.CG_BAZOO_ACHIEVE_TASK, CGBazooAchieveTask.class);
		map.put(MessageType.CG_BAZOO_GET_REWARD, CGBazooGetReward.class);
		return map;
	}

}
