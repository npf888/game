package com.gameserver.shop.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 小类：1、筹码2、周卡3、月卡4、门票5、改名卡 6、付费转盘,7、翻倍转盘8、奖券9、俱乐部礼包10、金库（小金猪）11、新手礼包12、经验加成卡
 * @author JavaServer
 *
 */
public enum ShopCatergoryEnum implements IndexedEnum{
	COINS(1),//筹码
	WEEK_CARD(2),//周卡
	MONTH_CARD(3),//月卡
	TICKET(4),//门票
	RENAME(5),//改名卡
	pay_dial(6),//付费转盘
	double_dial(7),//翻倍转盘
	COLLECT(8),//券
	club_bag(9),//俱乐部礼包
	treasury(10),//金库（小金猪
	new_guy_pag(11),//新手礼包
	expence(12),//经验加成卡
	club_change_name(13),//俱乐部改名卡
	;
	
	private final int index;
	private static final List<ShopCatergoryEnum> indexes = IndexedEnumUtil.toIndexes(ShopCatergoryEnum.values());

	private ShopCatergoryEnum(int index)
	{
		this.index = index;
	}
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static ShopCatergoryEnum indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}

}
