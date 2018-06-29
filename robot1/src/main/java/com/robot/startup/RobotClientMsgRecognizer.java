package com.robot.startup;

import java.util.HashMap;
import java.util.Map;

import com.core.msg.IMessage;
import com.core.msg.MessageParseException;
import com.core.msg.recognizer.BaseMessageRecognizer;


/**
 * 机器人消息注册
 * @author Thinker
 *
 */
public class RobotClientMsgRecognizer extends BaseMessageRecognizer
{
	
	private Map<Short, Class<?>> msgs = new HashMap<Short, Class<?>>();
	
	public RobotClientMsgRecognizer()
	{
		RobotMessageMappingProvider.fill(msgs);
	}

	@Override
	public IMessage createMessageImpl(short type) throws MessageParseException
	{
		Class<?> clazz = msgs.get(type);
		if (clazz == null)
		{
			throw new MessageParseException("Unknow msgType:" + type);
		} else
		{
			try 
			{
				IMessage msg = (IMessage) clazz.newInstance();
				return msg;
			} catch (Exception e)
			{
				throw new MessageParseException("Message Newinstance Failed，msgType:" + type, e);
			}
		}
	}
}
