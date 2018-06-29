package com.gameserver.bazoorank;

import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.db.dao.HumanBazooRankDao;
import com.db.model.HumanBazooRankEntity;
import com.db.model.HumanEntity;
import com.gameserver.bazoorank.data.HumanRankInfo;
import com.gameserver.bazoorank.msg.GCBazooRankRequest;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;

public class HumanBankService implements InitializeRequired {
	private Logger logger = Loggers.BAZOO;
	@Override
	public void init() {
	}
	
	public HumanRankInfo[] getAllPlayer(){
		List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().getPlayerRankByGoldDesc();
		
		HumanRankInfo[] HumanRankInfoArr = new HumanRankInfo[humanEntityList.size()];
		for(int i=0;i<humanEntityList.size();i++){
			HumanEntity entity = humanEntityList.get(i);
			HumanRankInfo humanRankInfo = new HumanRankInfo();
			humanRankInfo.setName(entity.getName());
			humanRankInfo.setPassportId(entity.getPassportId());
			humanRankInfo.setHeadImg(entity.getImg());
			humanRankInfo.setGold(entity.getGold());
			HumanRankInfoArr[i]=humanRankInfo;
		}
		
		GCBazooRankRequest GCBazooRankRequest = new GCBazooRankRequest();
		GCBazooRankRequest.setHumanRankInfo(HumanRankInfoArr);
		
		return HumanRankInfoArr;
	}




	/**
	 * 盈利
	 */
	public HumanRankInfo[] getBazooRankBy(int dateType) {
		HumanBazooRankDao humanBazooRankDao = Globals.getDaoService().getBazooRankDao();
		
		List<HumanBazooRankEntity> humanBazooRankEntityList = humanBazooRankDao.getBazooRankAll(dateType);
		HumanRankInfo[] HumanRankInfoArr = new HumanRankInfo[humanBazooRankEntityList.size()];
		for(int i=0;i<humanBazooRankEntityList.size();i++){
			HumanBazooRankEntity entity = humanBazooRankEntityList.get(i);
			HumanRankInfo humanRankInfo = new HumanRankInfo();
			humanRankInfo.setName(entity.getName());
			humanRankInfo.setPassportId(entity.getPassportId());
			humanRankInfo.setHeadImg(entity.getHeadImg());
			if(dateType == 1){
				humanRankInfo.setGold(entity.getDayProfit());
				
			}else if(dateType == 2){
				humanRankInfo.setGold(entity.getWeekProfit());
				
			}else if(dateType == 3){
				humanRankInfo.setGold(entity.getMonthProfit());
				
			}
			
			HumanRankInfoArr[i]=humanRankInfo;
		}
		
		return HumanRankInfoArr;
		
	}
	
	
	
	
	/**
	 * 每日 每周 每月 清除数据
	 */
	
	public void cleanBazooRank(int dateType){
		List<HumanBazooRankEntity> HumanBazooRankEntityList = Globals.getDaoService().getBazooRankDao().getAll(HumanBazooRankEntity.class);
		if(HumanBazooRankEntityList == null){
			return;
		}
		if(dateType == 1){
			for(HumanBazooRankEntity entity :HumanBazooRankEntityList){
				entity.setDayProfit(0);
				Globals.getDaoService().getBazooRankDao().saveOrUpdate(entity);
			}
			
		}else if(dateType == 2){
			for(HumanBazooRankEntity entity :HumanBazooRankEntityList){
				entity.setWeekProfit(0);
				Globals.getDaoService().getBazooRankDao().saveOrUpdate(entity);
			}
			
		}else if(dateType == 3){
			for(HumanBazooRankEntity entity :HumanBazooRankEntityList){
				entity.setMonthProfit(0);
				Globals.getDaoService().getBazooRankDao().saveOrUpdate(entity);
			}
			
		}
	}
	
	
	
	/**
	 * 玩游戏  增加 金币（只算盈利的部分）
	 */
	
	public void addGold(Player player,long gold){
		Human human = player.getHuman();
		HumanBazooRank HumanBazooRank = human.getHumanBazooRankManager().getHumanBazooRank();
		HumanBazooRank.setDayProfit(HumanBazooRank.getDayProfit()+gold);
		HumanBazooRank.setWeekProfit(HumanBazooRank.getWeekProfit()+gold);
		HumanBazooRank.setMonthProfit(HumanBazooRank.getMonthProfit()+gold);
		HumanBazooRank.setModified();
	}

}
