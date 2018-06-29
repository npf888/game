package com.robot.rpc;


import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.core.util.JsScriptHelper;
import com.game.webserver.exception.LocalException;
import com.robot.Robot;
import com.robot.RobotManager;
import com.robot.RobotServerConfig;
import com.robot.common.Globals;
import com.robot.rpc.impl.RPCImpl;
import com.robot.strategy.impl.TexasHallStrategy;
import com.rpc.RPC;



/**
 * rpc service
 * @author wayne
 *
 */
public class RpcService implements InitializeRequired,AfterInitializeRequired{
	
	//private Logger logger = Loggers.rpcLogg
	
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
//		RobotServerConfig robotServerConfig = Globals.getRobotServerConfig();
//		InetSocketAddress inetSocketAddress = new InetSocketAddress(robotServerConfig.getTelnetBindIp(),Integer.parseInt(robotServerConfig.getTelnetPort()));
//		try {
//			 //设置传输通道，普通通道  
//            TServerTransport serverTransport = new TServerSocket(inetSocketAddress);  
//     
//            //设置处理器HelloImpl  
//            TProcessor processor = new RPC.Processor<RPCImpl>(new RPCImpl());  
//            
//            TServer.Args args = new TServer.Args(serverTransport);
//            args.processor(processor);
//            //创建服务器  
//            tserver = new TSimpleServer(args);  
//             
//		} catch (TTransportException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	
		
//		   TNonblockingServerTransport serverTransport;
//	        try {
//	            serverTransport = new TNonblockingServerSocket(Integer.parseInt(robotServerConfig.getTelnetPort()));
//	            RPC.Processor<RPCImpl> processor = new RPC.Processor<RPCImpl>(
//	                    new RPCImpl());
//
//	            TServer server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
//	            System.out.println("Start server on port "+Integer.parseInt(robotServerConfig.getTelnetPort()));
//	            server.serve();
//	        } catch (TTransportException e) {
//	            e.printStackTrace();
//	        }
	}
	
	public void start()
	{
		//logger.info("begin start rpc sub thread");
		RobotServerConfig robotServerConfig = Globals.getRobotServerConfig();
		
        try {
            System.out.println(" robot server start ....");

            RPC.Processor<RPC.Iface> tprocessor = new RPC.Processor<RPC.Iface>(new RPCImpl());
 
            // 简单的单线程服务模型，一般用于测试
            TServerSocket serverTransport = new TServerSocket(Integer.parseInt(robotServerConfig.getTelnetPort()));
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
 
        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
	//	logger.info("end start rpc sub thread");
	
	}
	
	public BlockingQueue<FutureTask<?>> getRpcTaskList()
	{
		return rpcTaskList;
	}
	
	
	public void putRpcTask(FutureTask<?> task) throws InterruptedException
	{
		this.rpcTaskList.put(task);
	}
	
	public FutureTask<?> take() throws InterruptedException{
		return this.rpcTaskList.take();
	}
	
	
	public long robotJoin(String serverId,long roomId){
		
		Robot robot = RobotManager.getInstance().createRobot(serverId);
		if(robot == null)
			return 0;
		TexasHallStrategy st = new TexasHallStrategy(robot,roomId);
		robot.addRobotStrategy(st);
		robot.start();
		
		return Long.parseLong(robot.getPassportId());
		// 加载具体脚本
//		if(path != null)
//		{
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("robot", robot);
//			
//			try
//			{
//				JsScriptHelper.executeScriptFile(path, params);
//			} catch (Exception e)
//			{
//				
//			}
//		}


		

	}
	

}
