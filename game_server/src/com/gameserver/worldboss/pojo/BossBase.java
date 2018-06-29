package com.gameserver.worldboss.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.RandomUtil;
import com.core.uuid.UUIDType;
import com.db.model.BossEntity;
import com.gameserver.common.Globals;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.worldboss.template.BossPropertyTemplate;
import com.gameserver.worldboss.template.BossTemplate;

public class BossBase implements PersistanceObject<Long, BossEntity>{
	private final LifeCycle lifeCycle;
	private boolean inDb;
	
	private long id;
	private long bossId;
	/** 1.（airtime）秒内，所有wild连线伤害降低百分之（） 2.恢复自身最大血量的百分之（） 3.（airtime）秒内，收到伤害降低百分之（） 4.（airtime）秒内，有（）概率免疫伤害。 */
	private int type;
	/** 技能开始后持续时间（秒） */
	private int airtime;
	/** wild中奖连线伤害降低百分数。（除以100） */
	private int wildreduce;
	/** 恢复最大血量百分数（除以100） */
	private int recover1;
	/** 伤害减免百分数 */
	private int damagereduce;
	/** 免伤概率，百分数（除以100） */
	private int avoid;
	/** 基础血量 */
	private long blood;
	/** 击杀成功后：奖励金币总数，按照伤害占比瓜分奖励百分比 */
	private int rewardNum1;
	/** 未击杀成功：奖励金币总数，按照伤害占比瓜分奖励百分比 */
	private int rewardNum2;
	/**是否 被击杀 1：被击杀- 0：没有被击杀**/
	private int beKilled;
	
	private int nameId;
	private int descrip;
	private int nextBossId;
	private int lastBossId;
	private int status;
	/**这个boss 开的是哪个时间段的bossTemplate 的ID**/
	private int bossTemplateId;
	
	/**伤害排行榜**/
	private String attackRank;
	/**
	 * 变化中的血
	 */
	private long changingBlood;
	/**
	 * 增长的血（每次增长的血 的 ，全部是增长的）
	 */
	private long increaseBlood;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 持续时间
	 */
	private long continueTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	private Date createTime;
	private Date updateTime;
	
	/** wild中奖连线伤害降低百分数。（除以100） */
	private boolean wildreduceOnOff=false;;

	/** 伤害减免百分数 */
	private boolean damagereduceOnOff=false;

	/** 免伤概率，百分数（除以100） */
	private boolean avoidOnOff=false;
	
	
	/** 技能时间开始时间 */
	private long skillLeftTime=0;
	
	
	
	
	
	
	
	public BossBase(){
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	
	
	
	
	
	
	
	/**
	 *   用户攻击减少 血量
	 * 
	 *  1.（airtime）秒内，所有wild连线伤害降低百分之（）
	 *	2.恢复自身最大血量的百分之（）
	 *	3.（airtime）秒内，收到伤害降低百分之（）
	 *	4.（airtime）秒内，有（）概率免疫伤害。
	 * @param list 
	 *
	 * 
	 */

	public boolean reduceBlood(long decreaseBlood,List<Long> curAttackBlood, List<SlotConnectInfo> list,List<Integer> elementList,SlotService slotService,int slotType){
		long dBlood = 0l; 
		if(type == 1){
			if(wildreduceOnOff){
				long attackBloods = 0l;
				//所有的线
				for(SlotConnectInfo sci :list){
					boolean exist = false;
					//每一条线
					List<Integer> linePosition = sci.getPosList();
					for(Integer position:linePosition){
						Integer element = elementList.get(position);
						int elementType = slotService.getTurnType(slotType, element);
						if(elementType == SlotElementType.WILD.getIndex()){
							exist=true;
							attackBloods+=sci.getPay()*wildreduce/100;
							break;
						}
					}
					if(!exist){
						attackBloods = sci.getPay();
					}
				}
				
				changingBlood-=attackBloods;
				dBlood=attackBloods;
			}else{
				changingBlood-=decreaseBlood;
				dBlood=decreaseBlood;
			}
		}else if(type == 2){
			changingBlood-=decreaseBlood;
			dBlood=decreaseBlood;
		}else if(type == 3){
			if(damagereduceOnOff){
				changingBlood-=(decreaseBlood*damagereduce/100);
				dBlood=decreaseBlood*damagereduce/100;
			}else{
				changingBlood-=decreaseBlood;
				dBlood=decreaseBlood;
			}
		}else if(type == 4){
			if(avoidOnOff){
				/**
				 * avoid% 这个概率免受伤害
				 */
				int x = RandomUtil.nextInt(0, 100);
				if(x>avoid){
					changingBlood-=decreaseBlood;
					dBlood=decreaseBlood;
				}
			}else{
				changingBlood-=decreaseBlood;
				dBlood=decreaseBlood;
			}
		}
		curAttackBlood.add(dBlood);
		if(changingBlood <= 0){
			changingBlood=0;
			return true;
		}
		return false;
	}
	


	public int getBossTemplateId() {
		return bossTemplateId;
	}

	public void setBossTemplateId(int bossTemplateId) {
		this.bossTemplateId = bossTemplateId;
	}

	public int getNameId() {
		return nameId;
	}

	public void setNameId(int nameId) {
		this.nameId = nameId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public int getDescrip() {
		return descrip;
	}

	public void setDescrip(int descrip) {
		this.descrip = descrip;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public long getContinueTime() {
		return continueTime;
	}



	public void setContinueTime(long continueTime) {
		this.continueTime = continueTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAirtime() {
		return airtime;
	}

	public void setAirtime(int airtime) {
		this.airtime = airtime;
	}

	public int getWildreduce() {
		return wildreduce;
	}

	public void setWildreduce(int wildreduce) {
		this.wildreduce = wildreduce;
	}

	public int getRecover1() {
		return recover1;
	}

	public void setRecover1(int recover1) {
		this.recover1 = recover1;
	}

	public int getDamagereduce() {
		return damagereduce;
	}

	public void setDamagereduce(int damagereduce) {
		this.damagereduce = damagereduce;
	}

	public int getAvoid() {
		return avoid;
	}

	public void setAvoid(int avoid) {
		this.avoid = avoid;
	}

	public long getBlood() {
		return blood;
	}

	public void setBlood(long blood) {
		this.blood = blood;
	}

	public int getRewardNum1() {
		return rewardNum1;
	}

	public void setRewardNum1(int rewardNum1) {
		this.rewardNum1 = rewardNum1;
	}

	public int getRewardNum2() {
		return rewardNum2;
	}

	public void setRewardNum2(int rewardNum2) {
		this.rewardNum2 = rewardNum2;
	}

	public long getChangingBlood() {
		return changingBlood;
	}

	public void setChangingBlood(long changingBlood) {
		this.changingBlood = changingBlood;
	}

	public long getIncreaseBlood() {
		return increaseBlood;
	}

	public void setIncreaseBlood(long increaseBlood) {
		this.increaseBlood = increaseBlood;
	}

	public boolean isWildreduceOnOff() {
		return wildreduceOnOff;
	}

	public void setWildreduceOnOff(boolean wildreduceOnOff) {
		this.wildreduceOnOff = wildreduceOnOff;
	}

	public boolean isDamagereduceOnOff() {
		return damagereduceOnOff;
	}

	public void setDamagereduceOnOff(boolean damagereduceOnOff) {
		this.damagereduceOnOff = damagereduceOnOff;
	}

	public boolean isAvoidOnOff() {
		return avoidOnOff;
	}

	public void setAvoidOnOff(boolean avoidOnOff) {
		this.avoidOnOff = avoidOnOff;
	}


	public int getBeKilled() {
		return beKilled;
	}

	public void setBeKilled(int beKilled) {
		this.beKilled = beKilled;
	}


	public String getAttackRank() {
		return attackRank;
	}

	public void setAttackRank(String attackRank) {
		this.attackRank = attackRank;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public long getBossId() {
		return bossId;
	}


	public void setBossId(long bossId) {
		this.bossId = bossId;
	}


	public int getNextBossId() {
		return nextBossId;
	}

	public void setNextBossId(int nextBossId) {
		this.nextBossId = nextBossId;
	}

	public long getSkillLeftTime() {
		return skillLeftTime;
	}

	public void setSkillLeftTime(long skillLeftTime) {
		this.skillLeftTime = skillLeftTime;
	}

	public int getLastBossId() {
		return lastBossId;
	}

	public void setLastBossId(int lastBossId) {
		this.lastBossId = lastBossId;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	@Override
	public void setDbId(Long id) {
		this.id=id;
	}


	@Override
	public Long getDbId() {
		return id;
	}


	@Override
	public String getGUID() {
		return "boss#"+this.id;
	}


	@Override
	public boolean isInDb() {
		return this.inDb;
	}


	@Override
	public void setInDb(boolean inDb) {
		this.inDb=inDb;
	}


	@Override
	public long getCharId() {
		return 0;
	}


	@Override
	public BossEntity toEntity() {
		BossEntity entity = new BossEntity();
		entity.setId(getDbId());
		entity.setBlood(getBlood());
		entity.setAirtime(getAirtime());
		entity.setAvoid(getAvoid());
		entity.setChangingBlood(getChangingBlood());
		entity.setDamagereduce(getDamagereduce());
		entity.setIncreaseBlood(getIncreaseBlood());
		entity.setRecover1(getRecover1());
		entity.setRewardNum1(getRewardNum1());
		entity.setRewardNum2(getRewardNum2());
		entity.setType(getType());
		entity.setWildreduce(getWildreduce());
		entity.setType(getType());
		entity.setBeKilled(getBeKilled());
		entity.setAttackRank(getAttackRank());
		entity.setEndTime(getEndTime());
		entity.setStartTime(getStartTime());
		entity.setContinueTime(getContinueTime());
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		entity.setBossId(getBossId());
		entity.setNameId(getNameId());
		entity.setDescrip(getDescrip());
		entity.setNextBossId(getNextBossId());
		entity.setBossTemplateId(getBossTemplateId());
		entity.setLastBossId(getLastBossId());
		entity.setStatus(getStatus());
		return entity;
	
	}


	@Override
	public void fromEntity(BossEntity entity) {
		this.setDbId(entity.getId());
		this.setType(entity.getType());
		this.setBlood(entity.getBlood());
		this.setAirtime(entity.getAirtime());
		this.setAvoid(entity.getAvoid());
		this.setChangingBlood(entity.getChangingBlood());
		this.setDamagereduce(entity.getDamagereduce());
		this.setIncreaseBlood(entity.getIncreaseBlood());
		this.setRecover1(entity.getRecover1());
		this.setRewardNum1(entity.getRewardNum1());
		this.setRewardNum2(entity.getRewardNum2());
		this.setWildreduce(entity.getWildreduce());
		this.setBeKilled(entity.getBeKilled());
		this.setAttackRank(entity.getAttackRank());
		this.setStartTime(entity.getStartTime());
		this.setContinueTime(entity.getContinueTime());
		this.setEndTime(entity.getEndTime());
		this.setCreateTime(entity.getCreateTime());
		this.setUpdateTime(entity.getUpdateTime());
		this.setBossId(entity.getBossId());
		this.setNameId(entity.getNameId());
		this.setDescrip(entity.getDescrip());
		this.setNextBossId(entity.getNextBossId());
		this.setBossTemplateId(entity.getBossTemplateId());
		this.setLastBossId(entity.getLastBossId());
		this.setStatus(entity.getStatus());
		this.setInDb(true);
		this.active();
	}


	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}


	@Override
	public void setModified() {
		onUpdate();
	}
	
	
	/**
	 * 激活此2关系
	 */
	public void active(){
		getLifeCycle().activate();
	}
	
	public void onUpdate()
	{

		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive())
		{
			//全球管理器的更新，不属于个人操作
 			Globals.getGlobalLogicRunner().getGlobalDataUpdater().addUpdate(lifeCycle);
		}
		
	}








	public BossPropertyTemplate buildBoss(Boss lastBoss,BossTemplate bossTemplate,
			List<BossPropertyTemplate> bossPropertyTemplateList,
			BossPropertyTemplate lastBossPropertyTemplate) {
		if(lastBoss != null && lastBoss.getNextBossId() > 0){
			for(BossPropertyTemplate BPTemplate:bossPropertyTemplateList){
				if(lastBoss.getNextBossId() == BPTemplate.getId()){
					lastBossPropertyTemplate=BPTemplate;
				}
			}
		}else{
			lastBossPropertyTemplate=getBossPropertyTemplate(lastBossPropertyTemplate,bossPropertyTemplateList);
		}
		this.setNextBossId(0);
		this.setNameId(lastBossPropertyTemplate.getNameId());
		List<BossEntity> bossEntityList= Globals.getDaoService().getBossDao().getLastBoss();
		if(bossEntityList != null && bossEntityList.size()>0){
			this.setBossId(bossEntityList.get(0).getBossId()+1);
		}else{
			this.setBossId(1);
		}
		long now1 = Globals.getTimeService().now();
		this.setDbId(Globals.getUUIDService().getNextUUID(now1,UUIDType.WORLDBOSSID));
		this.active();
		this.setInDb(false);
		Date now = new Date();
		this.setCreateTime(now);
		this.setUpdateTime(now);
		this.setStartTime(now);
		int continueTime = bossTemplate.getContinuetime();
		this.setContinueTime(bossTemplate.getContinuetime());
		long time = now.getTime()+continueTime*60*1000;
		this.setEndTime(new Date(time));
		this.setDescrip(lastBossPropertyTemplate.getDescrip());
		this.setType(lastBossPropertyTemplate.getType());
		//第一次创建  boss
		this.setBlood(lastBossPropertyTemplate.getBlood());
		this.setAirtime(lastBossPropertyTemplate.getAirtime());
		this.setAvoid(lastBossPropertyTemplate.getAvoid());
		this.setDamagereduce(lastBossPropertyTemplate.getDamagereduce());
		this.setRecover1(lastBossPropertyTemplate.getRecover1());
		this.setRewardNum1(lastBossPropertyTemplate.getRewardNum1());
		this.setRewardNum2(lastBossPropertyTemplate.getRewardNum2());
		this.setWildreduce(lastBossPropertyTemplate.getWildreduce());
		this.setBossTemplateId(bossTemplate.getId());
		//这边主要设置 基础血量  -- 如果以前玩过 每次的基础 血量 都会变化
		if(bossEntityList != null && bossEntityList.size() != 0){
			BossEntity bossEntity = bossEntityList.get(0);
			//获取上一次的基础血量
			long baseBlood = bossEntity.getBlood();
			if(bossEntity.getBeKilled() == 1){
				//击杀增长血量，百分比（除以100）
				int inc = bossTemplate.getIncrease();
				long curBlood = baseBlood+baseBlood*inc/100;
				this.setBlood(curBlood);
			}else if(bossEntity.getBeKilled() == 0){
				//未击杀减少量，百分比（除以100）
				//最低减少到基础血量，不在减少
				int reduce = bossTemplate.getReduce();
				long curBlood = baseBlood-baseBlood*reduce/100;
				//基础血量 减少到  最最最 基本的配置 blood就不减了
				if(curBlood < lastBossPropertyTemplate.getBlood()){
					this.setBlood(lastBossPropertyTemplate.getBlood());
				}else{
					this.setBlood(curBlood);
				}
			}
		}
		this.setChangingBlood(this.getBlood());
		this.setIncreaseBlood(0l);
		this.setModified();
		
		return lastBossPropertyTemplate;
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
	
	
}
