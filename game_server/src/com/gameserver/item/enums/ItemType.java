package com.gameserver.item.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 小类：1、筹码2、周卡3、月卡、4、表情包5、头像6、币商道具、7、经验卡,8、门票9、改名卡 10、奖券 11、金币礼包 12、碎片 13、限时经验加成卡
 * @author 郭君伟
 *
 */
public enum ItemType implements IndexedEnum {
    /** **/
	ItemType1(1),
	 /** **/
	ItemType2(2),
	 /** **/
	ItemType3(3),
	 /** **/
	ItemType4(4),
	 /** **/
	ItemType5(5),
	 /** **/
	ItemType6(6),
	 /** **/
	ItemType7(7),
	 /** **/
	ItemType8(8),
	 /** **/
	ItemType9(9),
	 /**奖券 **/
	ItemType10(10),
	 /**金币礼包 **/
	ItemType11(11),
	 /**碎片 **/
	ItemType12(12),
	 /**限时经验加成卡 **/
	ItemType13(13)
       ;
	
	private int index;
	private ItemType(int index){
		this.index = index;
	}
	
	private static final List<ItemType> indexes = IndexedEnumUtil.toIndexes(ItemType.values());

	
	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return index;
	}
	
	public static ItemType indexOf(final int index)
	{
		return EnumUtil.valueOf(indexes, index);
	}
}
