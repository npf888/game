package com.gameserver.givealike.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.gameserver.common.Globals;
import com.gameserver.givealike.pojo.HumanGivealike;
import com.gameserver.human.Human;

public class HumanGivealikeService implements InitializeRequired {


	/**
	 * 根据 slotType bet 选出 当前老虎机当前bet场的 点赞清空
	 * @param human
	 * @param slotType
	 * @param slotBet
	 * @return
	 */
	public List<HumanGivealike> getHumanGiveLike(Human human,int slotType){
		List<HumanGivealike> selectHumanGivealikeList = new ArrayList<HumanGivealike>();
		List<HumanGivealike> humanGivealikeList = human.getHumanGivealikeManager().getHumanGivealikeList();
		for(HumanGivealike humanGivealike:humanGivealikeList){
			if(humanGivealike.getSlotType() == slotType){
				selectHumanGivealikeList.add(humanGivealike);
			}
		}
		return selectHumanGivealikeList;
	}
	
	
	/**
	 * 保存用户的评价
	 * 
	 */
	public void saveGiveLike(Human human,int slotType,int paintAssess,int playAssess,int totalAssess){
		List<HumanGivealike> humanGivealikeList = human.getHumanGivealikeManager().getHumanGivealikeList();
		for(HumanGivealike like:humanGivealikeList){
			if(like.getUserId() == human.getPassportId() && like.getSlotType() == slotType){
				return;
			}
		}
		HumanGivealike humanGivealike =new HumanGivealike(human);
		Date now = new Date();
		
		humanGivealike.setCreateTime(now);
		humanGivealike.setDbId(Globals.getUUIDService().getNextUUID(now.getTime(),UUIDType.HUMANGIVEALIKE));
		humanGivealike.setSlotType(slotType);
		humanGivealike.setUserId(human.getPassportId());
		humanGivealike.setContent("paintAssess:"+paintAssess+",playAssess:"+playAssess+",totalAssess"+totalAssess);
		humanGivealike.setInDb(false);
		humanGivealike.active();
		humanGivealike.setModified();
		human.getHumanGivealikeManager().addHumanGivealikeToList(humanGivealike);
	}


	public void init() {
		
	}
}
