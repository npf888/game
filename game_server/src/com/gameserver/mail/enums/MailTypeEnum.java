package com.gameserver.mail.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 邮件种类
 * @author wayne
 *
 */
public enum MailTypeEnum implements IndexedEnum{
	/** 系统**/
	SYSTEM(0),
	/**人物 **/
	PLAYER(1),
	/**礼包 **/
	PLAYER_GIFT(2),
	/**补偿 **/
	COMPENSATION(3),
	;
	
	private final int index;
	private MailTypeEnum(int index)
	{
		this.index = index;
	}
	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	private static final List<MailTypeEnum> values = IndexedEnumUtil.toIndexes(MailTypeEnum.values());

	public static MailTypeEnum valueOf(int index)
	{
		return EnumUtil.valueOf(values, index);
	}
	
}
