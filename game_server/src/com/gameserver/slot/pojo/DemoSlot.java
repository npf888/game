package com.gameserver.slot.pojo;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.slot.enums.SlotElementType;

public class DemoSlot {

	public static List<Integer> demoType1 = new ArrayList<Integer>();
	public static List<Integer> demoType2 = new ArrayList<Integer>();
	public static List<Integer> demoType3 = new ArrayList<Integer>();
	public static List<Integer> demoType4 = new ArrayList<Integer>();
	public static List<Integer> demoType5 = new ArrayList<Integer>();
	
	static{
		demoType1.add(SlotElementType.BONUS_GAME.getIndex());
		demoType1.add(SlotElementType.BONUS13.getIndex());
		demoType1.add(SlotElementType.BONUS14.getIndex());
		demoType1.add(SlotElementType.SLOTTYPE15_BONUS.getIndex());
		demoType1.add(SlotElementType.SLOTTYPE16.getIndex());
		demoType1.add(SlotElementType.SLOTTYPE17.getIndex());
		demoType1.add(SlotElementType.SLOTTYPE20WILD_BONUS.getIndex());
		demoType1.add(SlotElementType.SLOTTYPE21_BONUS.getIndex());
		demoType1.add(SlotElementType.SLOTTYPE25.getIndex());
		demoType1.add(SlotElementType.BONUS27.getIndex());
		demoType1.add(SlotElementType.BONUS28.getIndex());
		demoType1.add(SlotElementType.BONUS29.getIndex());
		demoType1.add(SlotElementType.BONUS30.getIndex());
		demoType1.add(SlotElementType.SLOTBONUS31.getIndex());
		demoType1.add(SlotElementType.SLOT32BONUS36.getIndex());
		demoType1.add(SlotElementType.SLOT33BOUNS.getIndex());
		
		demoType2.add(SlotElementType.SCATTER.getIndex());
		demoType2.add(SlotElementType.BIGWHEEL.getIndex());
		demoType2.add(SlotElementType.CRYSTAL18.getIndex());
		demoType2.add(SlotElementType.SLOTTYPE22.getIndex());
		demoType2.add(SlotElementType.SLOTWILD31.getIndex());
		
		
		
		demoType3.add(SlotElementType.bigWILD.getIndex());
		demoType3.add(SlotElementType.SLOTTYPE20WILD.getIndex());
		demoType3.add(SlotElementType.SLOTWILD26.getIndex());
		demoType3.add(SlotElementType.WILD27.getIndex());
		
		demoType4.add(SlotElementType.WILD.getIndex());
		demoType4.add(SlotElementType.SLOTTYPE15.getIndex());
		
		
		demoType5.add(SlotElementType.JACKPOT.getIndex());
		demoType5.add(SlotElementType.SLOTTYPE23.getIndex());
	}
	
	
	
}
