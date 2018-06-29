package com.core.codec;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderException;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.core.msg.IMessage;
import com.core.msg.MessageParseException;

/**
 * 支持任意长度消息的编写，只要消息支持setBuffer和write方法
 * @author Thinker
 * 
 */
public class GameEncoder implements ProtocolEncoder
{

	public void dispose(IoSession arg0) throws Exception
	{
	}

	public void encode(IoSession session, Object msg, ProtocolEncoderOutput out)throws Exception
	{
		if (!(msg instanceof IMessage))
		{
			throw new ProtocolEncoderException("This encoder only encode mop's IMessage");
		}
		encodeCommonMessage(msg, out);
	}

	private void encodeCommonMessage(Object msg, ProtocolEncoderOutput out)throws MessageParseException
	{
		IMessage m = (IMessage) msg;
		ByteBuffer buf = ByteBuffer.allocate(m.getInitBufferLength());
		buf.setAutoExpand(true);
		m.setBuffer(buf);

		boolean success = false;
		try
		{
			if (m.write()) 
			{
				success = true;
			}
		} finally
		{
			buf.flip();
			if (success) 
			{
				// 如果成功，保留缓存区，写入out
				if (buf.hasRemaining())
				{
					// 统一处理，如果消息太长
					//byte[] p=new byte[buf.position()];
					// p=LLZlibHelper.compressBytes(buf.array());
					// buf.clear();
					// buf.put(p);
					// buf.flip();
					//LLXORHelper.NFX_EnCode(buf.array(),buf.remaining());
					out.write(buf);
					// 因为调用了ProtocolEncoderOutput，因此不需要release
				} else
				{
					// 这个消息太长了，放弃
					buf.release();
				}
			} else
			{
				// 写入失败，释放内存，应该记录下来这个问题
				buf.release();
			}
			m.setBuffer(null);
		}
	}
}
