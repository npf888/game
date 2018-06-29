package com.gameserver.human.manager;

import java.util.Date;
import java.util.List;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.HumanBazooWinsEntity;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoowins.HumanBazooWins;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class HumanBazooWinsManager implements  InitializeRequired {
	private Human owner;
	private HumanBazooWins maxHumanBazooWins = null;
	public HumanBazooWinsManager(Human owner) {
		this.owner = owner;
	}
	@Override
	public void init() {
		List<HumanBazooWinsEntity>  entityList = Globals.getDaoService().getHumanBazooWinsDao().getHumanBazooWinsByPassportId(owner.getPassportId());
		//首先删除 除了winTimes 最大 的数据 以外其他的数据
		if(entityList != null && entityList.size() > 0 ){
			int modeType = 1;
			for(int i=0;i<entityList.size();i++){
				if(i==0){
					continue;
				}
				HumanBazooWinsEntity entity  = entityList.get(i);
				if(entity.getModeType() != modeType){
					modeType=entity.getModeType();
					continue;
				}
				HumanBazooWins humanBazooWins=new HumanBazooWins(owner);
				humanBazooWins.fromEntity(entity);
				humanBazooWins.onDelete();
			}
			//然后 再取出 最大的 winTimes数据
			maxHumanBazooWins=new HumanBazooWins(owner);
			maxHumanBazooWins.fromEntity(entityList.get(0));
		}
		
	}
	
	
	
	/**
	 * 每一次连胜 结束的时候 保存数据
	 * @param modeType
	 * @param winTimes
	 */
	public void saveBazooWins(int modeType,int winTimes){
		HumanBazooWins humanBazooWins = new HumanBazooWins(owner);
		humanBazooWins.setId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.HUMANBAZOOWINS));
		humanBazooWins.setModeType(modeType);
		humanBazooWins.setPassportId(owner.getPassportId());
		humanBazooWins.setWinTimes(winTimes);
		humanBazooWins.setInDb(false);
		humanBazooWins.active();
		humanBazooWins.setModified();
		
		if(maxHumanBazooWins == null){
			maxHumanBazooWins=humanBazooWins;
		}else if(winTimes>maxHumanBazooWins.getWinTimes()){
			maxHumanBazooWins=humanBazooWins;
		}
	}
	
	
	
	
	public HumanBazooWins getMaxHumanBazooWins() {
		return maxHumanBazooWins;
	}
	public void setMaxHumanBazooWins(HumanBazooWins maxHumanBazooWins) {
		this.maxHumanBazooWins = maxHumanBazooWins;
	}

	
	
	
	
	
}
