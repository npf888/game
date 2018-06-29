package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.HumanBazooAchieveEntity;
import com.gameserver.bazoo.template.LiarsDiceRoomTaskTemplate;
import com.gameserver.bazooachieve.HumanBazooAchieve;
import com.gameserver.bazooachieve.data.BazooAchieveData;
import com.gameserver.bazootask.HumanBazooTask;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class HumanBazooAchieveManager implements  InitializeRequired {
	private Human owner;
	private HumanBazooAchieve HumanBazooAchieve = null;
	public HumanBazooAchieveManager(Human owner) {
		this.owner = owner;
	}
	
	
	@Override
	public void init() {
		/*HumanBazooAchieveEntity HumanBazooAchieveEntity = Globals.getDaoService().getHumanBazooAchieveDao().getHumanBazooAchieveByPassportId(owner.getPassportId());
		HumanBazooAchieve = new HumanBazooAchieve(owner);
		if(HumanBazooAchieveEntity == null){
			HumanBazooAchieve.setInDb(false);
			HumanBazooAchieve.setDbId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.HUMANBAZOOTASK));
			HumanBazooAchieve.setPassportId(owner.getPassportId());
			List<LiarsDiceRoomTaskTemplate>  taskTemplateList = Globals.getHumanBazooTaskService().getTaskList();
			List<BazooAchieveData> BazooAchieveDataList = new ArrayList<BazooAchieveData>();
			for(LiarsDiceRoomTaskTemplate template : taskTemplateList){
				BazooAchieveData BazooAchieveData = new BazooAchieveData();
				BazooAchieveData.setAchieveId(template.getId());
				BazooAchieveData.setFinished(0);
				BazooAchieveData.setFinishValues(0);
				BazooAchieveData.setGetNums(0);
				BazooAchieveDataList.add(BazooAchieveData);
			}
			HumanBazooAchieve.setAchieves(BazooAchieveDataList);
			HumanBazooAchieve.active();
			HumanBazooAchieve.setModified();
		}else{
			HumanBazooAchieve.fromEntity(HumanBazooAchieveEntity);
		}*/
		
	}


	public Human getOwner() {
		return owner;
	}
	public void setOwner(Human owner) {
		this.owner = owner;
	}


	public HumanBazooAchieve getHumanBazooAchieve() {
		return HumanBazooAchieve;
	}


	public void setHumanBazooAchieve(HumanBazooAchieve humanBazooAchieve) {
		HumanBazooAchieve = humanBazooAchieve;
	}
	


	
	




	
	
	
}
