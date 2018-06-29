package com.gameserver.mail.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum MailHasAttachment implements IndexedEnum 
{
	/** 无要领取的礼品 */
	NOTHING(0),
	/** 有要领取的礼品 */
	ANYTHING (1),
	;
	
	private MailHasAttachment(int index)
	{
		this.index = index;
	}

	public final int index;

	@Override
	public int getIndex() 
	{
		return index;
	}

	private static final List<MailHasAttachment> values = IndexedEnumUtil.toIndexes(MailHasAttachment.values());

	public static MailHasAttachment valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
