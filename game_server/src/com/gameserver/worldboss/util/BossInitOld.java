package com.gameserver.worldboss.util;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.core.util.TimeUtils;
import com.db.model.BossEntity;
import com.db.model.HumanAttackBossEntity;
import com.db.model.HumanEntity;
import com.db.model.HumanVipNewEntity;
import com.gameserver.common.Globals;
import com.gameserver.worldboss.data.LastWinHumanData;
import com.gameserver.worldboss.pojo.Boss;
import com.gameserver.worldboss.template.BossPropertyTemplate;
import com.gameserver.worldboss.template.BossTemplate;
import com.gameserver.worldboss.vo.AttackRankVO;

public class BossInitOld {

	private boolean bossStillAlive=false;
	Boss rlastBoss = null;
	Boss rBoss = null;
	LastWinHumanData rLastWinHumanData=null;
	BossPropertyTemplate rLastBossPropertyTemplate = null;
	BossTemplate nbossTemplate=null;
	public boolean initBoss(Boss boss,Boss lastBoss,boolean cbossStillAlive,LastWinHumanData lastWinHumanData,
			List<BossPropertyTemplate> bossPropertyTemplateList,
			BossPropertyTemplate lastBossPropertyTemplate,
			List<AttackRankVO> attackRankVOList,
			List<BossTemplate> bossTemplateList){
		/**
		 * 加载上次的 最后的两个 boss
		 */
		bossStillAlive=cbossStillAlive;
		List<BossEntity>  bossEntityList= Globals.getDaoService().getBossDao().getLastTwoBoss();
		lastWinHumanData =new LastWinHumanData();
		if(bossEntityList != null && bossEntityList.size() ==2){
			//判断第一个boss 是否 运行完毕   (如果正在运行中 ，那么就是当前boss,如果不是正在运行中 那么，就是 他自己就是上一个boss)
			BossEntity bossEntity = bossEntityList.get(0);
			//有可能 是上一个boss,也有可能是 上上个boss
			BossEntity lastBossEntity = bossEntityList.get(1);
			//是否 被击杀 1：被击杀- 0：没有被击杀(没有被击杀 同时 还没有过期 ,那么就 运行这个 boss)
			long now = Globals.getTimeService().now();
			long endTime = bossEntity.getEndTime().getTime();
			if(bossEntity.getBeKilled() == 0 && now < endTime){
				bossStillAlive=true;
				boss = new Boss();
				boss.fromEntity(bossEntity);
				
				lastBoss=new Boss();
				lastBoss.fromEntity(lastBossEntity);
			}else{
				lastBoss=new Boss();
				lastBoss.fromEntity(bossEntity);
			}
			List<HumanAttackBossEntity> humanAttackBossEntityList = Globals.getDaoService().getHumanAttackBossDao().getWinnerByBossId(lastBoss.getBossId());
			if(humanAttackBossEntityList!= null && humanAttackBossEntityList.size()>0){
				HumanAttackBossEntity humanAttackBossEntity = humanAttackBossEntityList.get(0);
				long userId = humanAttackBossEntity.getUserId();
				List<HumanEntity> humanEntity = Globals.getDaoService().getHumanDao().loadHumans(userId);
				HumanVipNewEntity  humanVipNewEntity  = Globals.getDaoService().getVipNewDao().getVipHumanById(userId);
				lastWinHumanData.getSelf(humanAttackBossEntity,humanEntity,humanVipNewEntity);
			}
		}else if(bossEntityList != null && bossEntityList.size() ==1){
			//只有一个boss 如果不是当前 boss 就是前一个boss
			BossEntity bossEntity = bossEntityList.get(0);
			//是否 被击杀 1：被击杀- 0：没有被击杀(没有被击杀 同时 还没有过期 ,那么就 运行这个 boss)
			long now = Globals.getTimeService().now();
			long endTime = bossEntity.getEndTime().getTime();
			if(bossEntity.getBeKilled() == 0 && now < endTime){
				bossStillAlive=true;
				boss = new Boss();
				boss.fromEntity(bossEntity);
			}else{
				lastBoss=new Boss();
				lastBoss.fromEntity(bossEntity);
			}
		}
		//初始化lastBossPropertyTemplate
		for(BossPropertyTemplate bpt:bossPropertyTemplateList){
			if(boss != null){
				if(boss.getType() == bpt.getType()){
					lastBossPropertyTemplate=bpt;
				}
			}else if(lastBoss != null){
				if(lastBoss.getType() == bpt.getType()){
					lastBossPropertyTemplate=bpt;
					
				}
				
			}
		}
		
		//初始化 排行榜
		if(boss != null){
			List<AttackRankVO> rAttackRankVOList = JSONArray.parseArray(boss.getAttackRank(),AttackRankVO.class);
			if(rAttackRankVOList != null && rAttackRankVOList.size()>0){
				attackRankVOList.addAll(rAttackRankVOList);
			}
		}
		
		rBoss = boss;
		rlastBoss = lastBoss;
		rLastWinHumanData = lastWinHumanData;
		rLastBossPropertyTemplate = lastBossPropertyTemplate;
		for(BossTemplate BossTemplate:bossTemplateList){
			if(boss != null){
				if(boss.getBossTemplateId() == BossTemplate.getId())
				nbossTemplate = BossTemplate;
			}else if(lastBoss!=null){
				if(lastBoss.getBossTemplateId() == BossTemplate.getId())
					nbossTemplate = BossTemplate;
			}
		}
		if(nbossTemplate == null){
			int index=0;
			for(int i=0;i<bossTemplateList.size();i++){
				long nowLong = Globals.getTimeService().now();
				String now = TimeUtils.formatYMDTime(nowLong);
				long bossLong = TimeUtils.stringToTime(now+" "+bossTemplateList.get(i).getStarttime()+":00").getTime();
				if(nowLong < bossLong){
					index=i;
					break;
				}
			}
			nbossTemplate = bossTemplateList.get(index);
		}
		return bossStillAlive;
	}
	public Boss getRlastBoss() {
		return rlastBoss;
	}
	public void setRlastBoss(Boss rlastBoss) {
		this.rlastBoss = rlastBoss;
	}
	public Boss getrBoss() {
		return rBoss;
	}
	public void setrBoss(Boss rBoss) {
		this.rBoss = rBoss;
	}
	public LastWinHumanData getrLastWinHumanData() {
		return rLastWinHumanData;
	}
	public void setrLastWinHumanData(LastWinHumanData rLastWinHumanData) {
		this.rLastWinHumanData = rLastWinHumanData;
	}
	public BossPropertyTemplate getrLastBossPropertyTemplate() {
		return rLastBossPropertyTemplate;
	}
	public void setrLastBossPropertyTemplate(
			BossPropertyTemplate rLastBossPropertyTemplate) {
		this.rLastBossPropertyTemplate = rLastBossPropertyTemplate;
	}
	public BossTemplate getNbossTemplate() {
		return nbossTemplate;
	}
	public void setNbossTemplate(BossTemplate nbossTemplate) {
		this.nbossTemplate = nbossTemplate;
	}
	
	
}
