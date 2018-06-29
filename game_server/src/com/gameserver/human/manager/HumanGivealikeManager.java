package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.List;

import com.common.InitializeRequired;
import com.db.model.HumanGivealikeEntity;
import com.gameserver.common.Globals;
import com.gameserver.givealike.pojo.HumanGivealike;
import com.gameserver.human.Human;

public class HumanGivealikeManager implements  InitializeRequired{

	
	private Human owner;
	
	private List<HumanGivealike> humanGivealikeList = new ArrayList<HumanGivealike>();
	public HumanGivealikeManager(Human human){
		this.owner=human;
	}
	
	@Override
	public void init() {
		List<HumanGivealikeEntity> humanGivealikeEntityList = Globals.getDaoService().getHumanGivealikeDao().getLikeByUserId(owner.getPassportId());
		if(humanGivealikeEntityList != null){
			for(HumanGivealikeEntity entity:humanGivealikeEntityList){
				HumanGivealike humanGivealike = new HumanGivealike(owner);
				humanGivealike.fromEntity(entity);
				humanGivealikeList.add(humanGivealike);
			}
		}
	}

	
	public void load(){
		
	}

	public List<HumanGivealike> getHumanGivealikeList() {
		return humanGivealikeList;
	}

	public void setHumanGivealikeList(List<HumanGivealike> humanGivealikeList) {
		this.humanGivealikeList = humanGivealikeList;
	}
	
	public void addHumanGivealikeToList(HumanGivealike humanGivealike){
		humanGivealikeList.add(humanGivealike);
	}
	
}
