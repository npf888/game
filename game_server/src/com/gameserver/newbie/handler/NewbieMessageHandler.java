package com.gameserver.newbie.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.util.ArrayUtils;
import com.db.dao.NewbieDao;
import com.db.model.NewbieEntity;
import com.gameserver.common.Globals;
import com.gameserver.consts.MessageCode;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.newbie.NewbieService;
import com.gameserver.newbie.data.NewbieSlotConnectInfoData;
import com.gameserver.newbie.data.NewbieSpecialConnectInfoData;
import com.gameserver.newbie.msg.CGGetSavePoint;
import com.gameserver.newbie.msg.CGSavePoint;
import com.gameserver.newbie.msg.CGSlotNewbie;
import com.gameserver.newbie.msg.GCGetSavePoint;
import com.gameserver.newbie.msg.GCSavePoint;
import com.gameserver.newbie.msg.GCSlotNewbie;
import com.gameserver.newbie.template.NoviceSlotsTemplate;
import com.gameserver.player.Player;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.template.SlotsListTemplate;


public class NewbieMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(NewbieMessageHandler.class);

	public void handleGetSavePoint(Player player, CGGetSavePoint cgGetSavePoint) {
		NewbieEntity ne = Globals.getDaoService().getNewbieDao().querNewbieStepByCharid(player.getPassportId());
		GCGetSavePoint res = new GCGetSavePoint();
		res.setStep(ne==null ? 0:ne.getStepId());
		player.sendMessage(res);
	}

	public void handleSavePoint(Player player, CGSavePoint cgSavePoint) {
		NewbieEntity ne = new NewbieEntity();
		ne.setCharId(player.getPassportId());
		ne.setStepId(cgSavePoint.getStep());
		Globals.getDaoService().getNewbieDao().save(ne);
		GCSavePoint sp = new GCSavePoint();
		sp.setRet(MessageCode.SUCC);
		player.sendMessage(sp);
	}

	public void handleSlotNewbie(Player player, CGSlotNewbie cgSlotNewbie) {
		NewbieDao newbieDao = Globals.getDaoService().getNewbieDao();
		NewbieEntity ne = newbieDao.querNewbieStepByCharid(player.getPassportId());
		int curStep = 0;
		if(ne==null)
		{
			ne = new NewbieEntity();
			ne.setCharId(player.getPassportId());
		}
		else
		{
			curStep = ne.getStepId();
		}
		curStep++;
		ne.setStepId(curStep);
		NewbieService ns = Globals.getNewbieService();
		NoviceSlotsTemplate nt = ns.getNoviceSlotsTemplateByStep(curStep);
		if(nt==null)
		{
			logger.error("passportId: "+player.getPassportId()+" has no slot for newbie step!");
			return;
		}
		Slot slot = Globals.getSlotService().getSlotByTemplateId(nt.getSlotNum());
		if(slot ==null){
			logger.warn("新手["+player.getPassportId()+"]请求的老虎机不存在");
			return;
		}
		Globals.getSlotService().playerEnterSlot(player, slot);
		List<Integer> list = new ArrayList<>();
		list.add(nt.getFirstReel1()-1);
		list.add(nt.getFirstReel2()-1);
		list.add(nt.getFirstReel3()-1);
		list.add(nt.getFirstReel4()-1);
		list.add(nt.getFirstReel5()-1);
		Human human = player.getHuman();
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		humanSlotManager.slot(list);
		SlotService slotService = Globals.getSlotService();
		List<SlotConnectInfo> tempSlotConnectInfoList = slotService.getSlotConnectInfoListByHuman(human);
		SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
		SlotBase slotBase = SlotBase.getSlot(nt.getSlotNum());
		slotBase.setTempSlotConnectInfoList(tempSlotConnectInfoList);
		slotBase.setHumanSlotManager(humanSlotManager);
		slotBase.setPlayer(player);
		slotBase.setSlot(slot);
		slotBase.setHuman(human);
		slotBase.setSlotService(slotService);
		SlotsListTemplate tempSlotsListTemplate = slotService.getslotTemplateMap(nt.getSlotNum());
		slotBase.setTempSlotsListTemplate(tempSlotsListTemplate);
		int tempAllBets = nt.getPayLinesNum()*nt.getBet();
		SpecialConnectInfoData tempSpecialConnectInfoData = slotBase.specificSlot(1,nt.getBet(),tempAllBets,tempSlotConnectInfoDataArr);
		List<Integer> tempSlotElements = slotService.getSlotElementsBySlotPos(human);
		
		NewbieSlotConnectInfoData[] newbieTempSlotConnectInfoDataArr = new NewbieSlotConnectInfoData[tempSlotConnectInfoDataArr.length];
		for(int i=0; i<tempSlotConnectInfoDataArr.length; i++)
		{
			SlotConnectInfoData scd = tempSlotConnectInfoDataArr[i];
			NewbieSlotConnectInfoData nsd = new NewbieSlotConnectInfoData();
			nsd.setPayId(scd.getPayId());
			nsd.setPayLineId(scd.getPayLineId());
			nsd.setPosList(scd.getPosList());
			newbieTempSlotConnectInfoDataArr[i] = nsd;
		}
//		int type = Globals.getJackpotServer().getSlotType(tempSlotsListTemplate.getType());
//		Globals.getJackpotServer().add(human.getPassportId(), human.getImg(), human.getName(), 0, type);
		humanSlotManager.addHumanExp(tempAllBets);
		
		newbieDao.saveOrUpdate(ne);
		GCSlotNewbie gcSlotSlot = new GCSlotNewbie();
//		gcSlotSlot.setMsgId(player.getHuman().getHumanSlotManager().getMsgCache().getNum());
		gcSlotSlot.setSlotElementList(ArrayUtils.intList2Array(tempSlotElements));
		gcSlotSlot.setSlotConnectInfoDataList(newbieTempSlotConnectInfoDataArr);
//		gcSlotSlot.setFreeTimes(humanSlotManager.getFreeTimes()-humanSlotManager.getUseTimes());
		int rewardNum = nt.getBet()*nt.getWinNum();
		gcSlotSlot.setRewardNum(rewardNum);
		gcSlotSlot.setRewardSum(humanSlotManager.getCurrentRewardNum()+rewardNum);
		
		if(tempSpecialConnectInfoData!=null){
			NewbieSpecialConnectInfoData newbieSpecialConnectInfoData = new NewbieSpecialConnectInfoData();
			newbieSpecialConnectInfoData.setPosList(tempSpecialConnectInfoData.getPosList());
			gcSlotSlot.setSpecialConnectInfoDataList(new NewbieSpecialConnectInfoData[]{newbieSpecialConnectInfoData});
		}else{
			gcSlotSlot.setSpecialConnectInfoDataList(new NewbieSpecialConnectInfoData[]{});
		}
		player.sendMessage(gcSlotSlot);
		
		
	}
    
}