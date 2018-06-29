package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.HumanBazooPersonalEntity;
import com.gameserver.bazoopersonal.HumanBazooPersonal;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class HumanBazooPersonalManager implements  InitializeRequired {

	private Human owner;
	private List<HumanBazooPersonal> humanBazooPersonalList = new ArrayList<HumanBazooPersonal>();
	public HumanBazooPersonalManager(Human owner) {
		this.owner = owner;
	}
	
	@Override
	public void init() {
		List<HumanBazooPersonalEntity> humanBazooPersonalEntityList = Globals.getDaoService().getHumanBazooPersonalDao().getBazooPersonalByPassportId(owner.getPassportId());
		if(humanBazooPersonalEntityList == null || humanBazooPersonalEntityList.size() == 0){//如果为空 就创建三条
			for(int i=0;i<4;i++){
				humanBazooPersonalList.add(newHumanBazooPersonal(i));
			}
		}else{
			humanBazooPersonalList.clear();
			List<Integer> existModeTypeList = new ArrayList<Integer>();
			for(HumanBazooPersonalEntity entity:humanBazooPersonalEntityList){
				HumanBazooPersonal humanBazooPersonal = new HumanBazooPersonal(owner);
				humanBazooPersonal.fromEntity(entity);
				humanBazooPersonalList.add(humanBazooPersonal);
				existModeTypeList.add(entity.getModeType());
			}
			//动态添加上 后添加 的模式    例如：红黑模式
			for(int i=0;i<4;i++){
				if(i<humanBazooPersonalEntityList.size()){
					HumanBazooPersonalEntity entity = humanBazooPersonalEntityList.get(i);
					if(existModeTypeList.contains(Integer.valueOf(entity.getModeType()))){
						continue;
					}
				}
				humanBazooPersonalList.add(newHumanBazooPersonal(i));
			}
		}
		
	}

	public HumanBazooPersonal newHumanBazooPersonal(int i){
		HumanBazooPersonal humanBazooPersonal = new HumanBazooPersonal(owner);
		humanBazooPersonal.setInDb(false);
		humanBazooPersonal.setDbId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.HUMANBAZOOPERSONAL));
		humanBazooPersonal.setPassportId(owner.getPassportId());
		humanBazooPersonal.setModeType(i+1);
		
		humanBazooPersonal.setAWinningStreak(0);
		humanBazooPersonal.setBigPatterns("1,2,3,4,5-1");
		humanBazooPersonal.setNumberOfGame(0);
		humanBazooPersonal.setSingleTopGold(0);
		humanBazooPersonal.setRateOfWinning(0);
		humanBazooPersonal.setPassToKill(0);
		humanBazooPersonal.setPantherNumber(0);
		
		humanBazooPersonal.setDayProfit(0l);
		humanBazooPersonal.setWeekProfit(0l);
		humanBazooPersonal.setMonthProfit(0l);
		humanBazooPersonal.active();
		humanBazooPersonal.setModified();
		return humanBazooPersonal;
	}
	
	
	public List<HumanBazooPersonal> getHumanBazooPersonalList() {
		return humanBazooPersonalList;
	}

	public void setHumanBazooPersonalList(
			List<HumanBazooPersonal> humanBazooPersonalList) {
		this.humanBazooPersonalList = humanBazooPersonalList;
	}
	
	
	

}
