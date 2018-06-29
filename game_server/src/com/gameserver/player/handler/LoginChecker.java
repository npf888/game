package com.gameserver.player.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gameserver.common.Globals;

/**
 * 登录检查, 应该是只检查但不决策! 
 * <font color="#990000">{@link LoginChecker} 中只检查 IP 地址, 不检查用户名!</font>
 * 相同 IP 地址, 2 秒之内不能重复登录...
 * 
 * @author Thinker
 * 
 */
public final class LoginChecker
{
	/** 有效的同一 IP 的登陆间隔时间 */
	private static final long VALIDATE_DURATION = 0L;
	/** 删除 IP 的登陆间隔时间 */
	private static final long CLEAR_VALIDATE_DURATION = 2 * 1000L;
	/** 单一实体 */
	private static LoginChecker _theInstance = new LoginChecker();
	/** 登录 IP 字典 */
	private final Map<String, Long> _ipAddrMap = new ConcurrentHashMap<String, Long>();



	private LoginChecker() 
	{
		
	}

	/**
	 * 获取登录检查器实例
	 */
	public final static LoginChecker getInstance() 
	{
		return _theInstance;
	}

	
	
	/**
	 * 验证 IP 地址, IP 地址不能为空, 且同一 IP 地址登录时间间隔应该在 2 秒以上!
	 * @param ipAddr 
	 * 
	 */
	public boolean checkIPAddr(String ipAddr)
	{
		if (ipAddr == null || ipAddr.isEmpty()) return false;

		// 根据 IP 地址拿到上一次登录时间
		Long lastTime = this._ipAddrMap.get(ipAddr);

		if (lastTime == null)
		{
			lastTime = Globals.getTimeService().now();
			this._ipAddrMap.put(ipAddr, lastTime);
			return true;
		}

		long now = System.currentTimeMillis();
		
		if ((now - lastTime) > VALIDATE_DURATION)
		{
			this._ipAddrMap.put(ipAddr, now);
			return true;
		}

		for (String ipKey : this._ipAddrMap.keySet())
		{
			// 循环清除过期 IP 登陆校验信息, 让缓存数据变小
			if((this._ipAddrMap.get(ipKey) - now) >= CLEAR_VALIDATE_DURATION) 
			{
				 this._ipAddrMap.remove(ipKey);
			}
		}
		return false;
	}

	/**
	 * 服务器已经满员
	 * 
	 */
	public boolean serverIsFull()
	{
		return Globals.getOnlinePlayerService().isFull();
	}

	/**
	 * 移除 IP 地址
	 * 
	 * @param ipAddr  
	 * 
	 */
	public void removeIPAddr(String ipAddr) 
	{
		if (ipAddr == null || ipAddr.isEmpty()) return;
		
		this._ipAddrMap.remove(ipAddr);
	}
}
