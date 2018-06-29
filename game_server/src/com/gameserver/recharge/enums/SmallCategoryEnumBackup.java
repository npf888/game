package com.gameserver.recharge.enums;

import java.util.List;

import com.core.enums.IndexedEnum;
import com.core.util.EnumUtil;
/**
 * 
 * 
 * 小类：1、筹码 2、周卡3、月卡4、门票5、改名卡 6、付费转盘,7、翻倍转盘8、奖券9、俱乐部礼包10、金库（小金猪）
 * @author 牛鹏飞
 *
 */
public enum SmallCategoryEnumBackup implements IndexedEnum {
	/** 1 筹码 **/
	SMALLCATEGORYENUM1(1),
	/** 2、周卡 **/
	SMALLCATEGORYENUM2(2),
	/** 3、月卡 **/
	SMALLCATEGORYENUM3(3),
	/** 4、门票**/
	SMALLCATEGORYENUM4(4),
	/** 5、改名卡**/
	SMALLCATEGORYENUM5(5),
	/** 6、付费转盘**/
	SMALLCATEGORYENUM6(6),
	/** 7、翻倍转盘**/
	SMALLCATEGORYENUM7(7),
	/** 8、奖券**/
	SMALLCATEGORYENUM8(8),
	/** 9、俱乐部礼包**/
	SMALLCATEGORYENUM9(9),
	/** 10、金库（小金猪）**/
	SMALLCATEGORYENUM10(10),
	/** 11、新手礼包**/
	SMALLCATEGORYENUM11(11),
	/**12、经验加成卡**/
	SMALLCATEGORYENUM12(12),
	/**13、俱乐部改名卡**/
	SMALLCATEGORYENUM13(13),
	/**14、周特惠礼包**/
	SMALLCATEGORYENUM14(14),
	/**15、月特惠礼包**/
	SMALLCATEGORYENUM15(15),
	/**16、大奖转盘**/
	SMALLCATEGORYENUM16(16)
	;

    private final int index;
	
	private SmallCategoryEnumBackup(int index){
		this.index= index;
	}
	
	@Override
	public int getIndex() {
		return index;
	}

	private static final List<SmallCategoryEnumBackup> values = IndexedEnumUtil.toIndexes(SmallCategoryEnumBackup.values());

	public static SmallCategoryEnumBackup valueOf(int index) 
	{
		return EnumUtil.valueOf(values, index);
	}

}
