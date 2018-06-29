package com.common.constants;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 终端类型枚举
 * @author Thinker
 * 
 */
public enum TerminalTypeEnum implements IndexedEnum
{
	/** Web 版本 */
	WEB(1), 
	/** iPad 版本 */
	IPAD(2),
	/** iPhone 版本 */
	IPHONE(3),
	/** 安卓 版本 */
	ANDDROID(4),
	/** GOOLE PLAY版本*/
	AND_GPLAY(5),
	/** kindle版本*/
	AND_KINDLE(6),
;
	/** 枚举值列表 */
	private static final List<TerminalTypeEnum> values = IndexedEnumUtil.toIndexes(TerminalTypeEnum.values());
	
	/** 枚举索引 */
	private final int index;

	/**
	 * 枚举参数构造器
	 * 
	 * @param index
	 */
	private TerminalTypeEnum(int index)
	{
		this.index = index;
	}

	@Override
	public int getIndex()
	{
		return this.index;
	}

	/**
	 * 将 int 类型转换为枚举类型
	 * 
	 * @param index
	 * @return
	 */
	public static TerminalTypeEnum valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}
	
	public static int convertDeviceType(String deviceType)
	{
		int terminalType = 1;
		
		if(deviceType != null&& !"".equals(deviceType))
		{
			deviceType = deviceType.split("@@")[0];
			// 具体要判断一下客户端发过来的设备类型
			if("ipad".equalsIgnoreCase(deviceType))
			{
				terminalType = TerminalTypeEnum.IPAD.getIndex();
			}else if("iphone".equalsIgnoreCase(deviceType)|| "ipod touch".equalsIgnoreCase(deviceType))
			{
				terminalType = TerminalTypeEnum.IPHONE.getIndex();
			}else if("android".equalsIgnoreCase(deviceType))
			{
				terminalType = TerminalTypeEnum.ANDDROID.getIndex();
			}else
			{
				terminalType = TerminalTypeEnum.WEB.getIndex();
			}
		}
		return terminalType;
	}
	/**
	 * @category 
	 * 			根据设备类型返字符串
	 * @param currentType
	 * 				设备类型枚举
	 * @return
	 * 		"ipad","iphone","web"
	 */
	public static String toString(TerminalTypeEnum currentType)
	{
		if(currentType==null)
		{
			return "传入参数为空!";
		}
		if(currentType==TerminalTypeEnum.WEB)
		{
			return "WEB";
		}else if(currentType==TerminalTypeEnum.IPAD)
		{
			return "IPAD";
		}else if(currentType ==TerminalTypeEnum.IPHONE)
		{
			return "IPHONE";
		}else if(currentType ==TerminalTypeEnum.ANDDROID)
		{
			return "ANDDROID";
		}
		return "未知设备类型!";
	}
	
	public static boolean isAppleMoveType(TerminalTypeEnum currentType)
	{
		if(currentType==TerminalTypeEnum.IPAD)
		{
			return true;
		}else if(currentType ==TerminalTypeEnum.IPHONE)
		{
			return true;
		}
		return false;
	}
	public static boolean isMobileType(TerminalTypeEnum currentType)
	{
		if(currentType==TerminalTypeEnum.IPAD
			||currentType ==TerminalTypeEnum.IPHONE
			||currentType ==TerminalTypeEnum.ANDDROID)
		{
			return true;
		}
		
		return false;
	}
}
