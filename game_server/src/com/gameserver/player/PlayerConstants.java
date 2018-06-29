package com.gameserver.player;


/**
 * 角色常量定义
 * 
 *@author Thinker
 * 
 */
public class PlayerConstants
{

	/** 角色姓名最小允许英文字符数 */
	public static final int MIN_NAME_LENGTH_ENG = 4;
	/** 角色姓名最大允许英文字符数 */
	public static final int MAX_NAME_LENGTH_ENG = 14;


	/* 角色信息保存的Mask定义 ： 通过Mask保存相应的数据 */
	
	/** 什么也不保存 */
	public static final int CHARACTER_MASK_NO= 0x0;
	/** 角色基本信息 */
	public static final int CHARACTER_INFO_MASK_BASE = 0x00000001;

	

}
