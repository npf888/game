package com.core.helper;


/**
 * 内存辅助类
 * @author Thinker
 *
 */
public class MemHelper 
{
	/**
	 * 获取使用的内存大小
	 */
	public static long getUsedMemoryMB()
	{
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
	}
	/**
	 * 获取空闲的内存大小
	 */
	public static long getFreeMemoryMB()
	{
		return (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1048576;
	}
	/**
	 * 获取所有的内存大小
	 */
	public static long getTotalMemoryMB() 
	{
		return Runtime.getRuntime().maxMemory() / 1048576;
	}

	/**
	 * 构建内存信息
	 */
	public static String buildMemoryInfo()
	{
		long freeMem = MemHelper.getFreeMemoryMB();
		long totalMem = MemHelper.getTotalMemoryMB();
		return "Free memory " + freeMem + " Mb of " + totalMem + " Mb";
	}
}
