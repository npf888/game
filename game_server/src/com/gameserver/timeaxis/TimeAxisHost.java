package com.gameserver.timeaxis;

/**
 * @author baoliang.shen
 * 时间轴的宿主，用于触发事件时向下传递
 */
public interface TimeAxisHost {

	static public enum Type{
		HUMAN,
		GLOBAL,
	}
	
	public Type	getTimeAxisHostType();
	
	public TimeAxis getTimeAxis();
}
