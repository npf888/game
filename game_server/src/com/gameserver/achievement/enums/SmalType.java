package com.gameserver.achievement.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;

/**
 * 1、累计登陆(不是连续登陆)
2、达到等级
3、单次赢分排行榜
4、总赢分排行榜
5、vip等级
6、首次充值
7、累计赢得筹码
8、累计押注筹码
9、累计游戏次数
10、单次赢得筹码（次数）
11、单次赢得筹码（固定额度的次数，2个条件）****  数量：次数，多个逗号隔开
12、bigwin累计次数
13、megawin累计次数
14、superwin累计次数
15、epicwin累计次数
16、牌型             *****   牌型：次数，多个逗号隔开
17、竞赛（2个条件）
18、单发互动道具次数
19、群发互动道具次数
20、好友数量
21、赠送礼物
22、领取礼物
23、发送facebook邀请
 * @author 郭君伟
 *
 */
public enum SmalType implements IndexedEnum {
	/**累计登陆 **/
	SmalType1(1),
	/**达到等级 **/
	SmalType2(2),
	/**单次赢分排行榜 **/
	SmalType3(3),
	/**总赢分排行榜 **/
	SmalType4(4),
	/**vip等级 **/
	SmalType5(5),
	/**首次充值 **/
	SmalType6(6),
	/**累计赢得筹码 **/
	SmalType7(7),
	/**累计押注筹码 **/
	SmalType8(8),
	/**累计游戏次数 **/
	SmalType9(9),
	/** 单次赢得筹码（次数）**/
	SmalType10(10),
	/**单次赢得筹码（固定额度的次数，2个条件） **/
	SmalType11(11),
	/**bigwin累计次数 **/
	SmalType12(12),
	/**megawin累计次数 **/
	SmalType13(13),
	/** superwin累计次数**/
	SmalType14(14),
	/** epicwin累计次数**/
	SmalType15(15),
	/** 牌型  **/
	SmalType16(16),
	/**竞赛（2个条件） **/
	SmalType17(17),
	/**单发互动道具次数 **/
	SmalType18(18),
	/**群发互动道具次数 **/
	SmalType19(19),
	/**好友数量 **/
	SmalType20(20),
	/**赠送礼物 **/
	SmalType21(21),
	/**领取礼物 **/
	SmalType22(22),
	/**发送facebook邀请 **/
	SmalType23(23),
	;
	private int index;
	private SmalType(int index){
		this.index = index;
	}
	@Override
	public int getIndex() {
		return index;
	}
	
	private static final List<SmalType> values = IndexedEnumUtil.toIndexes(SmalType.values());

	public static SmalType valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}
}
