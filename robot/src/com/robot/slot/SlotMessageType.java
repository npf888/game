package com.robot.slot;

import java.util.HashMap;
import java.util.Map;

import com.gameserver.slot.enums.SlotTypeEnum;
import com.robot.slot.slot1.GCFreeSlotRewardEnd;
import com.robot.slot.slot1.Slot1;
import com.robot.slot.slot10.GCFreeSlot10RewardEnd;
import com.robot.slot.slot10.Slot10;
import com.robot.slot.slot11.Slot11;
import com.robot.slot.slot12.GCSlotBonus12;
import com.robot.slot.slot12.Slot12;
import com.robot.slot.slot13.GCSlotBonus13;
import com.robot.slot.slot13.GCSlotBonus13Send;
import com.robot.slot.slot13.Slot13;
import com.robot.slot.slot14.GCSlotBonus14;
import com.robot.slot.slot14.GCSlotNewBonus14;
import com.robot.slot.slot14.Slot14;
import com.robot.slot.slot15.GCSlotBonus15Send;
import com.robot.slot.slot15.Slot15;
import com.robot.slot.slot16.GCSlotBonus16;
import com.robot.slot.slot16.Slot16;
import com.robot.slot.slot17.GCSlotBonus17;
import com.robot.slot.slot17.Slot17;
import com.robot.slot.slot18.Slot18;
import com.robot.slot.slot19.GCSlotBonus19Send;
import com.robot.slot.slot19.Slot19;
import com.robot.slot.slot20.GCSlotBonus20;
import com.robot.slot.slot20.GCSlotWild20;
import com.robot.slot.slot20.Slot20;
import com.robot.slot.slot21.GCSlotBonus21Send;
import com.robot.slot.slot21.Slot21;
import com.robot.slot.slot22.GCSlotBonus22Send;
import com.robot.slot.slot22.Slot22;
import com.robot.slot.slot23.GCSlotBonus23;
import com.robot.slot.slot23.Slot23;
import com.robot.slot.slot24.GCSlotBonus24;
import com.robot.slot.slot24.GCSlotBonus24New;
import com.robot.slot.slot24.GCSlotBonus24Send;
import com.robot.slot.slot24.Slot24;
import com.robot.slot.slot25.GCSlotBonus25;
import com.robot.slot.slot25.Slot25;
import com.robot.slot.slot26.GCSlotBonus26Send;
import com.robot.slot.slot26.Slot26;
import com.robot.slot.slot27.GCSlotBonus27;
import com.robot.slot.slot27.Slot27;
import com.robot.slot.slot28.GCSlotBonus28;
import com.robot.slot.slot28.GCSlotBonus28Send;
import com.robot.slot.slot28.GCSlotWild28;
import com.robot.slot.slot28.Slot28;
import com.robot.slot.slot29.GCSlotBonus29;
import com.robot.slot.slot29.GCSlotBonus29Send;
import com.robot.slot.slot29.Slot29;
import com.robot.slot.slot3.GCSlotBonus3Send;
import com.robot.slot.slot3.Slot3;
import com.robot.slot.slot30.GCSlotBonus30;
import com.robot.slot.slot30.GCSlotBonus30Send;
import com.robot.slot.slot30.Slot30;
import com.robot.slot.slot31.GCSlotBonus31;
import com.robot.slot.slot31.Slot31;
import com.robot.slot.slot32.GCSlotBonus32;
import com.robot.slot.slot32.GCSlotBonus32Send;
import com.robot.slot.slot32.Slot32;
import com.robot.slot.slot33.GCSlotBonus33;
import com.robot.slot.slot33.GCSlotBonus33Send;
import com.robot.slot.slot33.Slot33;
import com.robot.slot.slot38.GCSlotBonus38;
import com.robot.slot.slot38.GCSlotOther38;
import com.robot.slot.slot38.Slot38;

public class SlotMessageType {
	/**bonus的消息映射**/
	private Map<String,GCSlotBonusBase> bonusMap = new HashMap<String,GCSlotBonusBase>();
	public SlotMessageType(){
		
		
		
		
		
		/** 1-7,9,10  关于盒子的计算 **/
		bonusMap.put("GC_FREE_SLOT_REWARD", new GCFreeSlotRewardEnd());
		/*** 10 马来网红的结算***/
		bonusMap.put("GC_SLOT_TYPE10_SCATTER", new GCFreeSlot10RewardEnd());
		
		/** 12 维密 **/
		bonusMap.put("GC_SLOT_TYPE12", new GCSlotBonus12());
		bonusMap.put("GC_SLOT_TYPE12_CHOOSE", new GCSlotBonus12());
		bonusMap.put("GC_SLOT_TYPE12_FREE", new GCSlotBonus12());
		/** 13 宙斯 **/
		bonusMap.put("GC_SLOT_TYPE13_BOUNS", new GCSlotBonus13());//旧的
		bonusMap.put("GC_SLOT_TYPE13_SEND_BOUNS", new GCSlotBonus13Send());//新的
		/** 14 石器时代 **/
		bonusMap.put("GC_SLOT_TYPE14_BONUS", new GCSlotBonus14());
		/** 14 石器时代 new  **/
		bonusMap.put("GC_SLOT_TYPE14_APPLE_BONUS", new GCSlotNewBonus14());
		bonusMap.put("GC_SLOT_TYPE14_RUNE_BONUS", new GCSlotNewBonus14());
		bonusMap.put("GC_SLOT_TYPE14_PREY_BONUS", new GCSlotNewBonus14());
		/** 15 狮身人面 **/
		bonusMap.put("GC_SLOT_TYPE15_BOUNS_START", new GCSlotBonus15Send());
//		bonusMap.put("GC_SLOT_TYPE15_BOUNS", new GCSlotBonus15(false));
		/** 13 水果 **/
		bonusMap.put("GC_SLOT_TYPE3_BOUNS_START", new GCSlotBonus3Send());
		
		/** 16 阿兹特克 拼图 **/
		bonusMap.put("GC_SLOT_TYPE16", new GCSlotBonus16());
		/** 17 狼老虎 **/
		bonusMap.put("GC_SLOT_TYPE17", new GCSlotBonus17());
		/** 19  水晶魔法 **/
		bonusMap.put("GC_SLOT_TYPE19", new GCSlotBonus19Send());
		/** 20 泰国象 **/
		bonusMap.put("GC_SLOT_TYPE20_BOUNS", new GCSlotBonus20());
		bonusMap.put("GC_SLOT_TYPE20_BOUNS_NEW", new GCSlotBonus20());
		bonusMap.put("GC_SLOT_TYPE20", new GCSlotWild20());
		
		/** 21 老虎 **/
		bonusMap.put("GC_SLOT_TYPE21_BOUNS_INFO", new GCSlotBonus21Send());
//		bonusMap.put("GC_SLOT_TYPE21_BOUNS", new GCSlotBonus21(false));
		
		/** 22 西部牛仔 **/
		bonusMap.put("GC_SLOT_TYPE22", new GCSlotBonus22Send());
		/** 23 东方龙 **/
		bonusMap.put("GC_SLOT_TYPE23_BOUNS_INFO", new GCSlotBonus23());
		/** 24巴西风情 **/
		bonusMap.put("GC_SLOT_TYPE24_BOUNS", new GCSlotBonus24());
		bonusMap.put("GC_SLOT_TYPE24_SEND_BOUNS", new GCSlotBonus24Send());
		/** 24巴西风情 **/
		bonusMap.put("GC_SLOT_TYPE24_BOUNS_GAME_START", new GCSlotBonus24New());
		bonusMap.put("GC_SLOT_TYPE24_BOUNS_SAMBA", new GCSlotBonus24New());
		bonusMap.put("GC_SLOT_TYPE24_BOUNS_BAR", new GCSlotBonus24New());
		
		
		/** 25 **/
		bonusMap.put("GC_SLOT_TYPE25_BOUNS_INFO", new GCSlotBonus25(true));
		bonusMap.put("GC_SLOT_TYPE25_BOUNS", new GCSlotBonus25(false));
		/** 26 **/
		bonusMap.put("GC_SLOT_TYPE26_BOUNS_INFO", new GCSlotBonus26Send());
//		bonusMap.put("GC_SLOT_TYPE26_BOUNS", new GCSlotBonus26(false));
		/** 27 **/
		bonusMap.put("GC_SLOT_TYPE27_BOUNS_INFO", new GCSlotBonus27());
		/** 28 **/
		bonusMap.put("GC_SLOT_TYPE28_WILD_INFO", new GCSlotWild28());
		bonusMap.put("GC_SLOT_TYPE28_BOUNS_INFO", new GCSlotBonus28Send());
		bonusMap.put("GC_SLOT_TYPE28_BOUNS", new GCSlotBonus28(false));
		/** 29 **/
		bonusMap.put("GC_SLOT_TYPE29_BOUNS_INFO", new GCSlotBonus29Send());
		bonusMap.put("GC_SLOT_TYPE29_BOUNS", new GCSlotBonus29(false));
		/** 30 **/
		bonusMap.put("GC_SLOT_TYPE30_BOUNS_INFO", new GCSlotBonus30Send());
		bonusMap.put("GC_SLOT_TYPE30_BOUNS", new GCSlotBonus30(false));
		
		bonusMap.put("GC_SLOT_TYPE31_BONUS", new GCSlotBonus31(true));
		bonusMap.put("GC_SLOT_TYPE31_BONUS_ONE", new GCSlotBonus31(false));
		bonusMap.put("GC_SLOT_TYPE31_BONUS_TWO", new GCSlotBonus31(false));
		bonusMap.put("GC_SLOT_TYPE31_BONUS_THREE", new GCSlotBonus31(false));
		
		bonusMap.put("GC_SLOT_TYPE32_SOCIAL_CONTACT", new GCSlotBonus32Send());
		bonusMap.put("GC_SLOT_TYPE32_BONUS", new GCSlotBonus32());
		
		
		bonusMap.put("GC_SLOT_TYPE33_BONUS_LIST", new GCSlotBonus33());
		bonusMap.put("GC_REMOVE_SLOT_SLOT", new GCSlotBonus33Send());
		
		bonusMap.put("GC_SLOT_TYPE38_BONUS_TRIGGER", new GCSlotBonus38(true));
		bonusMap.put("GC_SLOT_TYPE38_BONUS", new GCSlotBonus38(false));
		bonusMap.put("GC_SLOT_TYPE38_JACKPOT", new GCSlotOther38());
		bonusMap.put("GC_SLOT_TYPE38_PUMPKIN", new GCSlotOther38());
		bonusMap.put("GC_SLOT_TYPE38_WILD", new GCSlotOther38());
		
	}
	//根据老虎机类型获取不同的老虎机
		public  GCSlotBase getSlot(int type){
			if(SlotTypeEnum.SlotType1.getIndex() == type || 
					SlotTypeEnum.SlotType2.getIndex() == type || 
				/*	SlotTypeEnum.SlotType3.getIndex() == type || 
					SlotTypeEnum.SlotType4.getIndex() == type || */
					SlotTypeEnum.SlotType5.getIndex() == type || 
					SlotTypeEnum.SlotType6.getIndex() == type || 
					SlotTypeEnum.SlotType7.getIndex() == type || 
							SlotTypeEnum.SlotType8.getIndex() == type ||
					SlotTypeEnum.SlotType9.getIndex() == type 
					
					){
				return new Slot1();
			}else if(SlotTypeEnum.SlotType3.getIndex() == type){
				return new Slot3();
			}else if(SlotTypeEnum.SlotType10.getIndex() == type){
				return new Slot10();
				
			}else if(SlotTypeEnum.SlotType11.getIndex() == type){
				return new Slot11();
				
			}else if(SlotTypeEnum.SlotType12.getIndex() == type){
				return new Slot12();
			}else if(SlotTypeEnum.SlotType4.getIndex() == type){//沙滩 和 维密一样了
				return new Slot12();
			}else if(SlotTypeEnum.SlotType13.getIndex() == type){
				return new Slot13();
			}else if(SlotTypeEnum.SlotType14.getIndex() == type){
				return new Slot14();
			}else if(SlotTypeEnum.SlotType15.getIndex() == type){
				return new Slot15();

			}else if(SlotTypeEnum.SlotType16.getIndex() == type){
				return new Slot16();
			}else if(SlotTypeEnum.SlotType17.getIndex() == type){
				return new Slot17();
			}else if(SlotTypeEnum.SlotType18.getIndex() == type){
				return new Slot18();
			}else if(SlotTypeEnum.SlotType19.getIndex() == type){
				return new Slot19();
			}else if(SlotTypeEnum.SlotType20.getIndex() == type){
				return new Slot20();
			}else if(SlotTypeEnum.SlotType21.getIndex() == type){
				return new Slot21();
			}else if(SlotTypeEnum.SlotType22.getIndex() == type){
				return new Slot22();
			}else if(SlotTypeEnum.SlotType23.getIndex() == type){
				return new Slot23();
			}else if(SlotTypeEnum.SlotType24.getIndex() == type){
				return new Slot24();
			}else if(SlotTypeEnum.SlotType25.getIndex() == type){
				return new Slot25();
			}else if(SlotTypeEnum.SlotType26.getIndex() == type){
				return new Slot26();
			}else if(SlotTypeEnum.SlotType27.getIndex() == type){
				return new Slot27();
			}else if(SlotTypeEnum.SlotType28.getIndex() == type){
				return new Slot28();
			}else if(SlotTypeEnum.SlotType29.getIndex() == type){
				return new Slot29();
			}else if(SlotTypeEnum.SlotType30.getIndex() == type){
				return new Slot30();
			}else if(SlotTypeEnum.SlotType31.getIndex() == type){
				return new Slot31();
			}else if(SlotTypeEnum.SlotType32.getIndex() == type){
				return new Slot32();
			}else if(SlotTypeEnum.SlotType33.getIndex() == type){
				return new Slot33();
			}else if(SlotTypeEnum.SlotType38.getIndex() == type){
				return new Slot38();
			}
			return null;
		}
		
		
		public GCSlotBonusBase getBonus(String typeName){
			return bonusMap.get(typeName);
		}
}
