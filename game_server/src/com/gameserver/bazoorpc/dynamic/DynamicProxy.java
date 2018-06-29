package com.gameserver.bazoorpc.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.gameserver.bazoorpc.client.RpcClient;
import com.gameserver.bazoorpc.remoteData.TriggerRobotData;
import com.gameserver.bazoorpc.remoteObj.TriggerRobotService;
import com.gameserver.bazoorpc.remoteObj.TriggerRobotServiceImpl;

/**
 * 动态代理的handler
 * @author JavaServer
 *
 */
public class DynamicProxy implements InvocationHandler{


    //　这个就是我们要代理的真实对象
    private Object subject;
    
    //    构造方法，给我们要代理的真实对象赋初值
    public DynamicProxy(Object subject)
    {
        this.subject = subject;
    }
	
	@Override
	public Object invoke(Object object, Method method, Object[] args)
			throws Throwable {
		
		method.invoke(subject, args);
		
		/**
		 * 开启远程调用
		 */
		TriggerRobotData data = new TriggerRobotData();
		data.setRoomNumber((String)args[0]);
		data.setNumber((int)args[1]);
		RpcClient.startClient(data);
		
		
		return null;
	}

	/**
	 * 获取代理对象
	 * @return
	 */
	public static Object getProxy(TriggerRobotService triggerRobotService){
		InvocationHandler handler = new DynamicProxy(triggerRobotService);
		return Proxy.newProxyInstance(handler.getClass().getClassLoader(), triggerRobotService.getClass().getInterfaces(),handler);
	}
	
	public static void robotGoInRoom(String roomNumber,int number){
		if(number <=0){
			return;
		}
		TriggerRobotService triggerRobotService =(TriggerRobotService)DynamicProxy.getProxy(new TriggerRobotServiceImpl());
		triggerRobotService.startRobot(roomNumber, number);
	}
}
