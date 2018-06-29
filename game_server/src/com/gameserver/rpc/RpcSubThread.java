package com.gameserver.rpc;

import com.gameserver.common.Globals;


/**
 * rpc 子线程
 * @author wayne
 *
 */
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