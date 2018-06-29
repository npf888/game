package com.gameserver.human.manager;

import java.util.Date;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.HumanBazooRankEntity;
import com.gameserver.bazoorank.HumanBazooRank;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.enums.PlayerRoleEnum;

public class HumanBazooRankManager implements  InitializeRequired {
	private Human owner;
	private HumanBazooRank HumanBazooRank = null;
	public HumanBazooRankManager(Human owner) {
		this.owner = owner;
	}
	
	
	@Override
	public void init() {
		HumanBazooRankEntity HumanBazooRankEntity = Globals.getDaoService().getBazooRankDao().getBazooRankByPassportId(owner.getPassportId());
		HumanBazooRank = new HumanBazooRank(owner);
		if(HumanBazooRankEntity == null){
			HumanBazooRank.setInDb(false);
			HumanBazooRank.setDbId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.HUMANBAZOOSIGNIN));
			HumanBazooRank.setPassportId(owner.getPassportId());
			HumanBazooRank.setName(owner.getName());
			HumanBazooRank.setHeadImg(owner.getImg());
			HumanBazooRank.setDayProfit(0l);
			HumanBazooRank.setWeekProfit(0l);
			HumanBazooRank.setMonthProfit(0l);
			HumanBazooRank.setBazooAgentDisplay(0);
			if(owner.getPlayer().getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
				HumanBazooRank.setBazooRobotDisplay(1);
			}else{
				HumanBazooRank.setBazooRobotDisplay(0);
			}
			HumanBazooRank.active();
			HumanBazooRank.setModified();
		}else{
			HumanBazooRank.fromEntity(HumanBazooRankEntity);
		}
		
	}


	
	
	public HumanBazooRank getHumanBazooRank() {
		return HumanBazooRank;
	}
	public void setHumanBazooRank(HumanBazooRank humanBazooRank) {
		HumanBazooRank = humanBazooRank;
	}



	
	
	
}
