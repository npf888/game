package com.gameserver.player.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

public enum ChannelTypeEnum  implements IndexedEnum{
	DEFAULT(0), // 默认渠道
	GOOGLEPLAY(1), // 谷歌
	IOS(2),// IOS 
	SBGOOGLEPLAY(3),// 越南 AC
	KOREAN_IOS(4),// 韩国 作废
	PAYMENTWALL(5),// 作废
	UNIPIN(7),//印尼 作废
	KOREAN_GOOGLEPALY(9),// 韩国 作废
	TUOMI_GOOGLEPLAY(11),//拓米 作废
	YOUMI_GOOGLEPLAY(13),//优米  金沙娱乐城
	GAME_VIEW(14),//GAME_VIEW
	AMAZON(15),//amazon
	TEQ(16),//土耳其
	
	GOOGLEPLAY17(17),// 天拖谷歌
	IOS18(18),// 天拖IOS
	BOQU19(19);// 第三方平台 博趣
	;
	
	private int index;
	private ChannelTypeEnum(int index){
		this.index = index;
	}
	
	private static final List<ChannelTypeEnum> indexes = IndexedEnumUtil.toIndexes(ChannelTypeEnum.values());

	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public static ChannelTypeEnum indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}
	
	public boolean isIOS(){
		return this ==IOS18 || this ==KOREAN_IOS;
	}

}
