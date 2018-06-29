package com.robot.rpc.impl;

import org.apache.log4j.Logger;

import com.robot.common.Globals;
import com.rpc.RPC.Iface;
import com.rpc.RobotExitRequest;
import com.rpc.RobotJoinRequest;




public class RPCImpl implements Iface{
	
	private Logger logger = Logger.getLogger(RPCImpl.class);
	
	@Override
	public long robotJoin(RobotJoinRequest joinRequest) {
	
		String serverId = joinRequest.getServerId();
		long rId = joinRequest.getRoomId();
		logger.info("request join robot from server["+serverId+"],room["+rId+"]");
		return Globals.getRpcService().robotJoin(serverId,rId);
		
	}

	@Override
	public long robotExit(RobotExitRequest exitRequest)  {
		
		String serverId = exitRequest.getServerId();
		long robotId = exitRequest.getRobotId();
		logger.info("request exit robot from server["+serverId+"],room["+robotId+"]");

		return 0;
	}



}

