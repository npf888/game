package com.gameserver.worldboss.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.common.LogReasons;
import com.core.schedule.LLScheduleEnum;
import com.core.util.RandomUtil;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.BossEntity;
import com.db.model.HumanEntity;
import com.db.model.HumanVipNewEntity;
import com.gameserver.common.Globals;
import com.gameserver.worldboss.data.LastWinHumanData;
import com.gameserver.worldboss.schedule.ScheduleBossEnd;
import com.gameserver.worldboss.template.BossPropertyTemplate;
import com.gameserver.worldboss.template.BossTemplate;
import com.gameserver.worldboss.vo.AttackRankVO;

public class BossInit extends BossBase{

	
	public static final int running = 1;
	public static final int overdue = 0;
	public static final int future= 2;
	Boss lastBoss = null;
	LastWinHumanData lastWinHumanData = new LastWinHumanData();
	//boss 的技能  现在处于 哪种状态 0：开始进行中，1 结束了 
	private int skill = 1;
	//秒
	private long skillEnd = 0l;
	public void init(List<BossPropertyTemplate> bossPropertyTemplateList,List<BossTemplate> bossTemplateList){
		/**
		 * 
		 */
		List<BossEntity>  bossEntityList= Globals.getDaoService().getBossDao().getLastBoss();
		long now1 = Globals.getTimeService().now();
		//第一次  从来没有过 boss 初始化一个
		if(bossEntityList == null || bossEntityList.size() == 0){
			this.setStatus(Boss.future);//将来时
			int whichOne = RandomUtil.nextInt(0, 4);
			//当前的富豪
			BossPropertyTemplate bossPropertyTemplate= bossPropertyTemplateList.get(whichOne);
			
			int index=0;
			long nowLong = Globals.getTimeService().now();
			for(int i=0;i<bossTemplateList.size();i++){
					String now = TimeUtils.formatYMDTime(nowLong);
					long bossLong = TimeUtils.stringToTime(now+" "+bossTemplateList.get(i).getStarttime()+":00").getTime();
					if(nowLong < bossLong){
						index=i;
						break;
					}
			}
			//当前的时间段
			BossTemplate bossTemplate = bossTemplateList.get(index);
			this.setLastBossId(0);
			this.setNextBossId(0);
			this.setNameId(bossPropertyTemplate.getNameId());
			this.setBossId(1);
			
			this.setDbId(Globals.getUUIDService().getNextUUID(now1,UUIDType.WORLDBOSSID));
			this.active();
			this.setInDb(false);
			Date now = new Date();
			this.setCreateTime(now);
			this.setUpdateTime(now);
			
			String nowStr = TimeUtils.formatYMDTime(nowLong);
			Date startTime = TimeUtils.stringToTime(nowStr+" "+bossTemplateList.get(index).getStarttime()+":00");
			this.setStartTime(startTime);
			
			int continueTime = bossTemplate.getContinuetime();
			this.setContinueTime(bossTemplate.getContinuetime());
			long time = startTime.getTime()+continueTime*60*1000;
			this.setEndTime(new Date(time));
			
			this.setDescrip(bossPropertyTemplate.getDescrip());
			this.setType(bossPropertyTemplate.getType());
			//第一次创建  boss
			this.setBlood(bossPropertyTemplate.getBlood());
			this.setAirtime(bossPropertyTemplate.getAirtime());
			this.setAvoid(bossPropertyTemplate.getAvoid());
			this.setDamagereduce(bossPropertyTemplate.getDamagereduce());
			this.setRecover1(bossPropertyTemplate.getRecover1());
			this.setRewardNum1(bossPropertyTemplate.getRewardNum1());
			this.setRewardNum2(bossPropertyTemplate.getRewardNum2());
			this.setWildreduce(bossPropertyTemplate.getWildreduce());
			this.setBossTemplateId(bossTemplate.getId());
			this.setBlood(bossPropertyTemplate.getBlood());
			this.setChangingBlood(bossPropertyTemplate.getBlood());
			this.setIncreaseBlood(0l);
		}else{
			if(this.getLastBossId() >0){
				List<BossEntity>  lastBossEntityList= Globals.getDaoService().getBossDao().getBossByBossId(this.getLastBossId());
				lastBoss = new Boss();
				lastBoss.fromEntity(lastBossEntityList.get(0));
			}
			BossEntity bossEntity= bossEntityList.get(0);
			this.fromEntity(bossEntity);
			/**
			 * 如果被杀 就是 过期了
			 */
			if(this.getBeKilled() == 1){
				this.setStatus(Boss.overdue);
			}else{
				if(now1>this.getStartTime().getTime() && now1<this.getEndTime().getTime()){
					this.setStatus(Boss.running);
					Date endtime = this.getEndTime();
					//如果处在正在运行时 就设置 一个结束的 定时器 仅仅一次
					Globals.getScheduleService().scheduleOnce(new ScheduleBossEnd(), LLScheduleEnum.WORLD_BOSS_END, endtime);
					
				}else if(now1<this.getStartTime().getTime()){
					this.setStatus(Boss.future);
				}else if(now1>this.getEndTime().getTime()){
					this.setStatus(Boss.overdue);
				}
			}
			//初始化排行榜
			List<AttackRankVO> rAttackRankVOList = JSONArray.parseArray(this.getAttackRank(),AttackRankVO.class);
			if(rAttackRankVOList != null && rAttackRankVOList.size()>0){
				Globals.getWorldBossNewService().setAttackRankVOList(rAttackRankVOList);
			}
		}
		this.setModified();
		//初始化 排行榜
		List<AttackRankVO>  AttackRankVOList = Globals.getWorldBossNewService().getAttackRankVOList();
		if(AttackRankVOList != null && AttackRankVOList.size()>0){
			AttackRankVO AttackRankVO =AttackRankVOList.get(0);
			List<HumanEntity> humanEntity = Globals.getDaoService().getHumanDao().loadHumans(AttackRankVO.getUserId());
			HumanVipNewEntity  humanVipNewEntity  = Globals.getDaoService().getVipNewDao().getVipHumanById(AttackRankVO.getUserId());
			lastWinHumanData.getSelf(AttackRankVO.getAttackTotalBlood(),humanEntity,humanVipNewEntity);
		}
		
		
	}
	/**
	 * 创建 一个正在运行的boss
	 * @param bossPropertyTemplateList 
	 */
	public void BuildBoss(Boss theBoss,BossTemplate bossTemplate, List<BossPropertyTemplate> bossPropertyTemplateList){
		lastBoss=theBoss;
		//初始化 排行榜
		List<AttackRankVO>  AttackRankVOList = Globals.getWorldBossNewService().getAttackRankVOList();
		if(AttackRankVOList != null && AttackRankVOList.size()>0){
			AttackRankVO AttackRankVO =AttackRankVOList.get(0);
			List<HumanEntity> humanEntity = Globals.getDaoService().getHumanDao().loadHumans(AttackRankVO.getUserId());
			HumanVipNewEntity  humanVipNewEntity  = Globals.getDaoService().getVipNewDao().getVipHumanById(AttackRankVO.getUserId());
			lastWinHumanData.getSelf(AttackRankVO.getAttackTotalBlood(),humanEntity,humanVipNewEntity);
		}
		Globals.getWorldBossNewService().clearAttackRankVOList();
		this.setStatus(Boss.running);
		BossPropertyTemplate bossPropertyTemplate = null;
		if(theBoss.getNextBossId() > 0){
			for(BossPropertyTemplate BPTemplate:bossPropertyTemplateList){
				if(theBoss.getNextBossId() == BPTemplate.getId()){
					bossPropertyTemplate=BPTemplate;
				}
			}
		}else{
			BossPropertyTemplate lastBossPropertyTemplate = null;
			if(this.getLastBossId() > 0){
				for(BossPropertyTemplate BPTemplate:bossPropertyTemplateList){
					if(this.getLastBossId() == BPTemplate.getId()){
						lastBossPropertyTemplate=BPTemplate;
					}
				}
				bossPropertyTemplate=getBossPropertyTemplate(lastBossPropertyTemplate,bossPropertyTemplateList);
			}else{
				bossPropertyTemplate=getBossPropertyTemplate(null,bossPropertyTemplateList);
			}
		}
		this.setLastBossId(Long.valueOf(theBoss.getBossId()).intValue());
		this.setNextBossId(0);
		this.setNameId(bossPropertyTemplate.getNameId());
		long now1 = Globals.getTimeService().now();
		this.setDbId(Globals.getUUIDService().getNextUUID(now1,UUIDType.WORLDBOSSID));
		
		this.active();
		this.setInDb(false);
		this.setSkillEnd(now1/1000+bossTemplate.getSkillstart());
		Date now = new Date();
		this.setCreateTime(now);
		this.setUpdateTime(now);
		this.setStartTime(now);
		int continueTime = bossTemplate.getContinuetime();
		this.setContinueTime(bossTemplate.getContinuetime());
		long time = now.getTime()+continueTime*60*1000;
		this.setEndTime(new Date(time));
		this.setDescrip(bossPropertyTemplate.getDescrip());
		this.setType(bossPropertyTemplate.getType());
		//第一次创建  boss
		this.setBlood(bossPropertyTemplate.getBlood());
		this.setAirtime(bossPropertyTemplate.getAirtime());
		this.setAvoid(bossPropertyTemplate.getAvoid());
		this.setDamagereduce(bossPropertyTemplate.getDamagereduce());
		this.setRecover1(bossPropertyTemplate.getRecover1());
		this.setRewardNum1(bossPropertyTemplate.getRewardNum1());
		this.setRewardNum2(bossPropertyTemplate.getRewardNum2());
		this.setWildreduce(bossPropertyTemplate.getWildreduce());
		this.setBossTemplateId(bossTemplate.getId());
		
		//这边主要设置 基础血量  -- 如果以前玩过 每次的基础 血量 都会变化
		this.setBossId(theBoss.getBossId()+1);
		//获取上一次的基础血量
		long baseBlood = theBoss.getBlood();
		if(theBoss.getBeKilled() == 1){
			//击杀增长血量，百分比（除以100）
			int inc = bossTemplate.getIncrease();
			long curBlood = baseBlood+baseBlood*inc/100;
			this.setBlood(curBlood);
		}else if(theBoss.getBeKilled() == 0){
			//未击杀减少量，百分比（除以100）
			//最低减少到基础血量，不在减少
			int reduce = bossTemplate.getReduce();
			long curBlood = baseBlood-baseBlood*reduce/100;
			//基础血量 减少到  最最最 基本的配置 blood就不减了
			if(curBlood < bossPropertyTemplate.getBlood()){
				this.setBlood(bossPropertyTemplate.getBlood());
			}else{
				this.setBlood(curBlood);
			}
		}
		this.setChangingBlood(this.getBlood());
		
		/**
		 * boss日志
		 */
		Globals.getLogService().sendWorldBossLog(null, LogReasons.WorldBossLogReason.WorldBoss, "", 
				this.getType(), TimeUtils.dateToTimeString(this.getStartTime()), 1, -1, 0l,this.getBossId());
	}

	/**
	 * 随机  获取世界boss
	 */
	public BossPropertyTemplate getBossPropertyTemplate(BossPropertyTemplate lastBossPropertyTemplate,List<BossPropertyTemplate> bossPropertyTemplateList){
		if(lastBossPropertyTemplate == null){
			int whichOne = RandomUtil.nextInt(0, 4);
			return bossPropertyTemplateList.get(whichOne);
		}else{
			int whichOne = RandomUtil.nextInt(0, 3);
			List<BossPropertyTemplate> tempList = new ArrayList<BossPropertyTemplate>();
			tempList.addAll(bossPropertyTemplateList);
			tempList.remove(lastBossPropertyTemplate);
			return  tempList.get(whichOne);
			
		}
	}
	public BossPropertyTemplate getBossPropertyTemplate(int lastBossPropertyTemplateId,List<BossPropertyTemplate> bossPropertyTemplateList){
			int whichOne = RandomUtil.nextInt(0, 3);
			List<BossPropertyTemplate> tempList = new ArrayList<BossPropertyTemplate>();
			tempList.addAll(bossPropertyTemplateList);
			BossPropertyTemplate lastBossPropertyTemplate = null;
			for(BossPropertyTemplate bpt:tempList){
				if(bpt.getId() == lastBossPropertyTemplateId){
					lastBossPropertyTemplate=bpt;
				}
			}
			tempList.remove(lastBossPropertyTemplate);
			return  tempList.get(whichOne);
			
	}
	public Boss getLastBoss() {
		return lastBoss;
	}
	public void setLastBoss(Boss lastBoss) {
		this.lastBoss = lastBoss;
	}
	public LastWinHumanData getLastWinHumanData() {
		return lastWinHumanData;
	}
	public void setLastWinHumanData(LastWinHumanData lastWinHumanData) {
		this.lastWinHumanData = lastWinHumanData;
	}
	public int getSkill() {
		return skill;
	}
	public void setSkill(int skill) {
		this.skill = skill;
	}
	public long getSkillEnd() {
		return skillEnd;
	}
	public void setSkillEnd(long skillEnd) {
		this.skillEnd = skillEnd;
	}
	
}
