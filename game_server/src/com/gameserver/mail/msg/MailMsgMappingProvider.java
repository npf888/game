package com.gameserver.mail.msg;

import java.util.HashMap;
import java.util.Map;

import com.gameserver.common.msg.MessageType;
import com.common.MessageMappingProvider;

/**
 *  Generated by MessageCodeGenerator,don't modify please.
 *  Need to register in<code>GameMessageRecognizer#init</code>
 */
public class MailMsgMappingProvider implements MessageMappingProvider {

	@Override
	public Map<Short, Class<?>> getMessageMapping() {
		Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
		map.put(MessageType.CG_LOAD_MAIL_LIST, CGLoadMailList.class);
		map.put(MessageType.CG_SEND_MAIL, CGSendMail.class);
		map.put(MessageType.CG_READ_MAIL, CGReadMail.class);
		map.put(MessageType.CG_DEAL_WITH_REWARD, CGDealWithReward.class);
		map.put(MessageType.CG_DELETE_MAIL, CGDeleteMail.class);
		map.put(MessageType.CG_RECEIVE_ALL, CGReceiveAll.class);
		return map;
	}

}