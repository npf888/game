package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.HumanBazooNewGuyEntity;
import com.gameserver.bazoonewguy.HumanBazooNewGuy;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class HumanBazooNewGuyManager implements  InitializeRequired {
	private Human owner;
	private List<HumanBazooNewGuy> humanBazooNewGuys = new ArrayList<HumanBazooNewGuy>();
	public HumanBazooNewGuyManager(Human owner) {
		this.owner = owner;
	}
	
	
	@Override
	public void init() {
		List<HumanBazooNewGuyEntity> humanBazooNewGuyEntityList = Globals.getDaoService().gethumanBazooNewGuyDao().getHumanBazooNewGuyByPassportId(owner.getPassportId());
		humanBazooNewGuys.clear();
		if(humanBazooNewGuyEntityList == null){
			for(int i=0;i<4;i++){//初始化
				HumanBazooNewGuy humanBazooNewGuy = new HumanBazooNewGuy(owner);
				humanBazooNewGuy.setInDb(false);
				humanBazooNewGuy.setDbId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.BAZOONEWGUY));
				humanBazooNewGuy.setUserId(owner.getPassportId());
				humanBazooNewGuy.setType(i+1);
				humanBazooNewGuy.setProcess(0);
				humanBazooNewGuy.setCreateTime(new Date());
				humanBazooNewGuy.setUpdateTime(new Date());
				humanBazooNewGuy.active();
				humanBazooNewGuy.setModified();
				humanBazooNewGuys.add(humanBazooNewGuy);
			}
		}else{
			for(HumanBazooNewGuyEntity entity:humanBazooNewGuyEntityList){
				HumanBazooNewGuy humanBazooNewGuy = new HumanBazooNewGuy(owner);
				humanBazooNewGuy.fromEntity(entity);
				humanBazooNewGuys.add(humanBazooNewGuy);
			}
		}
		
	}


	public Human getOwner() {
		return owner;
	}
	public void setOwner(Human owner) {
		this.owner = owner;
	}


	public List<HumanBazooNewGuy> getHumanBazooNewGuys() {
		return humanBazooNewGuys;
	}
	public void setHumanBazooNewGuys(List<HumanBazooNewGuy> humanBazooNewGuys) {
		this.humanBazooNewGuys = humanBazooNewGuys;
	}

	


	
	


	
	




	
	
	
}
