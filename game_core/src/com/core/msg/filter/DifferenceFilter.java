package com.core.msg.filter;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoFilter;
import org.apache.mina.common.IoFilterAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import com.core.codec.GameCodecFactory;
import com.core.codec.WebSocketCodecFactory;
import com.core.msg.recognizer.IMessageRecognizer;

/**
 * 区分是WebSocket  还是 原生的 协议
 * @author JavaServer
 *
 */
public class DifferenceFilter extends IoFilterAdapter{
	private IMessageRecognizer messageRecognizer;
	public DifferenceFilter(IMessageRecognizer messageRecognizer){
		this.messageRecognizer=messageRecognizer;
	}
	@Override
	public void messageReceived(NextFilter nextFilter, IoSession connection,
			Object message) throws Exception {
		ByteBuffer buffer = (ByteBuffer)message;
		ByteBuffer temp  = buffer.duplicate();
		String inString = getChars(temp);
		IoFilter ioFilter = connection.getFilterChain().get("web-codec");
		if(inString.contains("Upgrade") || ioFilter!=null){//webSocket
			if(inString.contains("Upgrade")){//握手
				connection.getFilterChain().addLast("web-codec", new ProtocolCodecFilter(new WebSocketCodecFactory(messageRecognizer)));
			}
			
		}else{//自定义协议
			IoFilter codecIoFilter = connection.getFilterChain().get("codec");
			if(codecIoFilter == null){
				connection.getFilterChain().addLast("codec", new ProtocolCodecFilter(new GameCodecFactory(messageRecognizer)));
			}
		}
		
		
		super.messageReceived(nextFilter,connection, message); 
	}
	
	
	public  String getChars(ByteBuffer buffer)
	  {
		try{
			ByteBuffer temp = buffer.duplicate();
			return new String(temp.array(),"utf8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	 } 
}
