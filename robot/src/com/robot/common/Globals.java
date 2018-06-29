package com.robot.common;




import com.core.server.ShutdownHooker;
import com.game.webserver.Config.LocalConfig;
import com.game.webserver.exception.LocalException;
import com.game.webserver.login.ILoginServerHandler;
import com.game.webserver.login.LoginServerHandler;
import com.robot.HeartbeatThread;
import com.robot.RobotManager;
import com.robot.RobotServerConfig;
import com.robot.bazooRPC.BazooRobotService;
import com.robot.rpc.RpcService;




public final class Globals {
	
	private static RobotServerConfig robotServerConfig;
	/** 进程停止Hooker */
	private static ShutdownHooker shutdownHooker;
	
	private static ILoginServerHandler synLoginServerHandler;
	
	private static RpcService rpcService;
	
	private static HeartbeatThread heartbeatThread;
	
	private static BazooRobotService bazooRobotService;
	
	

	public static void init(RobotServerConfig config) throws Exception{
		// TODO Auto-generated method stub
		robotServerConfig = config;
		shutdownHooker = new ShutdownHooker();
		rpcService = new RpcService();
		rpcService.init();
		rpcService.afterInit();
		RobotManager.getInstance().init();
		heartbeatThread = new HeartbeatThread();
		bazooRobotService = new BazooRobotService();
	}

	public static void start() {
		heartbeatThread.start();
		// TODO Auto-generated method stub
		//Globals.getRpcService().start();
	}

	public static ShutdownHooker getShutdownHooker() {
		// TODO Auto-generated method stub
		return shutdownHooker;
	}
	
	public static RobotServerConfig getRobotServerConfig(){
		return robotServerConfig;
	}

	public static HeartbeatThread getHeartBeatThread(){
		return heartbeatThread;
	}
	public static RpcService getRpcService() {
		// TODO Auto-generated method stub
		return rpcService;
	}
	public static BazooRobotService getBazooRobotService() {
		return bazooRobotService;
	}

	public static ILoginServerHandler getSynLoginServerHandler() throws LocalException {
		// TODO Auto-generated method stub
		if(synLoginServerHandler == null)
		{
			
			synLoginServerHandler = new LoginServerHandler(LocalConfig.robotLocalConfig());
	
		}
		return synLoginServerHandler;
	}
}
