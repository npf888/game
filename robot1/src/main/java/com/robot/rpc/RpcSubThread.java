package com.robot.rpc;

import com.robot.common.Globals;


@Deprecated
public class RpcSubThread extends Thread{
	public RpcSubThread()
	{
	}
	
	@Override
	public void run()
	{
		Globals.getRpcService().getTserver().serve();
	}
}
