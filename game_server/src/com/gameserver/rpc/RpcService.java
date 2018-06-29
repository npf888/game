package com.gameserver.rpc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.thrift.server.TServer;
import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;


/**
 * rpc service
 * @author wayne
 *
 */
public class RpcService implements InitializeRequired,AfterInitializeRequired{
	private Logger logger = Loggers.rpcLogger;
	private RpcSubThread rpcSubThread;
	private TServer tserver;
	private BlockingQueue<FutureTask<?>> rpcTaskList= new LinkedBlockingQueue<FutureTask<?>>();
	
	public TServer getTserver() {
		return tserver;
	}

	public void setTserver(TServer tserver) {
		this.tserver = tserver;
	}

	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void init() {
//		rpcSubThread = new RpcSubThread();
//		// TODO Auto-generated method stub
//		GameServerConfig gameServerConfig = Globals.getServerConfig();
//		InetSocketAddress inetSocketAddress = new InetSocketAddress(gameServerConfig.getTelnetBindIp(),Integer.parseInt(gameServerConfig.getTelnetPort()));
//		try {
//			 //设置传输通道，普通通道  
//            TServerTransport serverTransport = new TServerSocket(inetSocketAddress);  
//     
//            //设置处理器HelloImpl  
//            TProcessor processor = new RPC.Processor(new RPCImpl());  
//            
//            TServer.Args args = new TServer.Args(serverTransport);
//            args.processor(processor);
//            //创建服务器  
//            tserver = new TSimpleServer(args);  
//             
//		} catch (TTransportException e) {
//			logger.error("", e);
//		}
	}
	
	public void start()
	{
		logger.info("begin start rpc sub thread");
		//rpcSubThread.start();
		logger.info("end start rpc sub thread");
	
	}
	
	public BlockingQueue<FutureTask<?>> getRpcTaskList()
	{
		return rpcTaskList;
	}
	
	
	
	
	public FutureTask<?> take() throws InterruptedException{
		return this.rpcTaskList.take();
	}
}
