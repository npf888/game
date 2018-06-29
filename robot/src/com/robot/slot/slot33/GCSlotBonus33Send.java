package com.robot.slot.slot33;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.slot.data.GcRemoveSlotSlotList;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.msg.GCRemoveSlotSlot;
import com.robot.Robot;
import com.robot.slot.GCSlotBonusBase;

public class GCSlotBonus33Send  extends GCSlotBonusBase{
	protected Logger logger = Loggers.slotLogger;
	@Override
	public void execute(Robot robot, IMessage message, int slotId, int slotType) {
		if(message instanceof GCRemoveSlotSlot){
			GCRemoveSlotSlot gCRemoveSlotSlot = (GCRemoveSlotSlot)message;
			GcRemoveSlotSlotList[]  gcRemoveSlotSlotLists  = gCRemoveSlotSlot.getGcRemoveSlotSlotList();
			SlotConnectInfoData[] SlotConnectInfoDatas = gCRemoveSlotSlot.getSlotConnectInfoDataList();
			int i=0;
			i++;
			System.out.print(i);
			
		}
	}

}
