package com.core.session;

import org.apache.mina.common.IoSession;

import com.core.msg.BaseMinaMessage;
import com.core.msg.IMessage;
import com.core.msg.ISliceMessage;

/**
 * mina会话，封装了mina原生的<code>IoSession</code>，以及其他一些 应用程序自定义的业务逻辑
 * 
 */
public abstract class MinaSession implements ISession {
	protected volatile IoSession session;

	public MinaSession(IoSession s) {
		session = s;
	}

	@Override
	public boolean isConnected() {
		if (session != null) {
			return session.isConnected();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(IMessage msg) {
		if (session != null) {
			if(msg instanceof ISliceMessage){
				final ISliceMessage<BaseMinaMessage> _slices = (ISliceMessage<BaseMinaMessage>) msg;
				for(final BaseMinaMessage _msg:_slices.getSliceMessages()){
					session.write(_msg);
				}
			}else{
				session.write(msg);
			}
		}
	}

	@Override
	public void close() {
		if (session != null) {
			session.close();
		}
	}
	
	public IoSession getIoSession(){
		return session;
	}

	public boolean closeOnException() {
		// TODO Auto-generated method stub
		return true;
	}

}
