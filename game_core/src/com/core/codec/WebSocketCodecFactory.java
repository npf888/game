package com.core.codec;

import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.core.msg.recognizer.IMessageRecognizer;

public class WebSocketCodecFactory implements ProtocolCodecFactory
{
	/** 编码器 * */
	private static final GameEncoder ENCODER = new GameEncoder();

	/** 消息识别器 * */
	private final IMessageRecognizer recognizer;

	public WebSocketCodecFactory(IMessageRecognizer recog)
	{
		recognizer = recog;
	}

	public ProtocolDecoder getDecoder() throws Exception
	{
		return new WebSocketDecoder(recognizer);
	}

	public ProtocolEncoder getEncoder() throws Exception 
	{
		return new WebSocketEncoder();
	}
    @Deprecated
	public IMessageRecognizer getRecognizer()
	{
		return recognizer;
	}

}
