package com.gameserver.mail.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;


public enum MailStatus implements IndexedEnum 
{
	/** 未读邮件 */
	UNREAD(0),
	/** 已读邮件 */
	READ(1),
	/**已领取*/
	GET(2)
	;
	
	private MailStatus(int index)
	{
		this.index = index;
	}

	public final int index;

	@Override
	public int getIndex()
	{
		return index;
	}

	private static final List<MailStatus> values = IndexedEnumUtil.toIndexes(MailStatus.values());

	public static MailStatus valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
