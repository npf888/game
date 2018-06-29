package com.gameserver.robot.async;


import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.async.ILocalRelativeOperation;
import com.gameserver.common.Globals;
import com.rpc.RPC;
import com.rpc.RobotJoinRequest;

/**
 * 异步rpc
 * @author wayne
 *
 * @param <T>
 */
public class AsyncPRCRobotRequestOperation<T> implements ILocalRelativeOperation {

	private Logger logger = Loggers.rpcLogger;
	private final static int TIME_OUT=10000;
	
	private String serverId;
	private long roomId;
	private IAsyncPRCCallBack<T> callBack;
	private T result;
	
	public AsyncPRCRobotRequestOperation(String serverId,long roomId,IAsyncPRCCallBack<T> callBack){
		this.callBack = callBack;
		this.serverId = serverId;
		this.roomId = roomId;
	}
	
	@Override
	public int doStart() {
		// TODO Auto-generated method stub
		logger.info("start rpc robot request");
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		// TODO Auto-generated method stub
		TTransport transport = new TSocket(Globals.getServerConfig().getRobotServerIp(), Globals.getServerConfig().getRobotServerPort(), TIME_OUT);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		
		RPC.Client robotRpcClient = new RPC.Client(protocol);
	
		try {
			transport.open();
			long robotId = robotRpcClient.robotJoin(new RobotJoinRequest(serverId,roomId));
			result = (T)((Long)robotId);
		} catch (TException e) {
			// TODO Auto-generated catch block
			logger.error("rpc robot request exception["+e+"]");
		}
		finally{
			transport.close();
		}
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		// TODO Auto-generated method stub
		logger.info("end rpc  robot request");
		if(callBack!=null){
			callBack.onFinished(result);
		}
		
		return STAGE_STOP_DONE;
	}


}
