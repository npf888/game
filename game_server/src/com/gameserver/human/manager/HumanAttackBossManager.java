package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.core.uuid.UUIDType;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.template.SlotsListTemplate;
import com.gameserver.worldboss.pojo.Boss;
import com.gameserver.worldboss.pojo.HumanAttackBoss;
import com.gameserver.worldboss.template.BossTemplate;
import com.gameserver.worldboss.vo.AttackRankVO;

/**
 * 用户攻击
 * @author JavaServer
 *
 */
public class HumanAttackBossManager implements InitializeRequired{

	private Human ower;
	private HumanAttackBoss humanAttackBoss;
	private long totalAttackNum;//攻击每个boss的总量  （boss如果变了 ,这个值 从零开始）
	private int attackType = 0;
	private List<Long> curAttackBlood = new ArrayList<Long>();//每一次 攻击完 就返回去，然后清空
	public HumanAttackBossManager(Human ower){
		this.ower=ower;
	}
	
	@Override
	public void init(){
		Boss boss = Globals.getWorldBossNewService().getCurBoss();
		totalAttackNum=0;
		if(boss.getStatus() == Boss.running){
			List<AttackRankVO> AttackRankVOList = Globals.getWorldBossNewService().getAttackRankVOList();
			if(AttackRankVOList != null && AttackRankVOList.size() >0){
				for(AttackRankVO AttackRankVO:AttackRankVOList){
					if(AttackRankVO.getUserId() == ower.getPassportId()){
						totalAttackNum=AttackRankVO.getAttackTotalBlood();
					}
				}
			}
			
		}
	}
	
	public void load(){
		
	}

	
	public HumanAttackBoss getHumanAttackBoss(){
		if(humanAttackBoss == null){
			humanAttackBoss = new HumanAttackBoss(ower);
		}
		humanAttackBoss.setAttackAllTotalBlood(totalAttackNum);
		return humanAttackBoss;
	}

	public long setTotalAttackNum(long bossId,long gold){
		if(humanAttackBoss.getBossId() != bossId){
			totalAttackNum=0l;
		}
		totalAttackNum+=gold;
		return totalAttackNum;
	}
	

	public long getTotalAttackNum() {
		return totalAttackNum;
	}

	public void setTotalAttackNum(long totalAttackNum) {
		this.totalAttackNum = totalAttackNum;
	}

	public int getAttackType() {
		return attackType;
	}

	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}

	public boolean  attackBoss(Boss boss,long gold,int curBet,Slot slot,
			BossTemplate bossTemplate,
			List<AttackRankVO> attackRankVOList,
			List<SlotConnectInfo> list,List<Integer> elementList,SlotService slotService,int slotType, Human human){
		
		this.getHumanAttackBoss().setAttackBlood(gold);
		this.getHumanAttackBoss().setBossId(boss.getBossId());
		this.getHumanAttackBoss().setUserId(ower.getPassportId());
		this.getHumanAttackBoss().setCreateTime(new Date());
		long now1 = Globals.getTimeService().now();
		this.getHumanAttackBoss().setDbId(Globals.getUUIDService().getNextUUID(now1,UUIDType.HUMANATTACKBOSSID));
		this.getHumanAttackBoss().active();
		this.getHumanAttackBoss().setInDb(false);
		/**
		 * 根据 等级 或者 	bigwin megawin  superwin  epicwin 来计算 攻击值
		 */
		int curVipLv = ower.getHumanVipNewManager().getVipLv();
		if(curVipLv == 0){
			int vip0 = bossTemplate.getVip0();
			gold+=gold*vip0/100;
		}else if(curVipLv == 1){
			int vip1 = bossTemplate.getVip1();
			gold+=gold*vip1/100;
		}else if(curVipLv == 2){
			int vip2 = bossTemplate.getVip2();
			gold+=gold*vip2/100;
		}else if(curVipLv == 3){
			int vip3 = bossTemplate.getVip3();
			gold+=gold*vip3/100;
		}else if(curVipLv == 4){
			int vip4 = bossTemplate.getVip4();
			gold+=gold*vip4/100;
		}else if(curVipLv == 5){
			int vip5 = bossTemplate.getVip5();
			gold+=gold*vip5/100;
			
		}
		
		SlotsListTemplate slotsListTemplate = Globals.getSlotService().getslotTemplateMap(slot.getTempleId());
		int bigWin = slotsListTemplate.getBigWinNum();
		int MegaWinNum = slotsListTemplate.getMegaWinNum();
		int SuperWinNum = slotsListTemplate.getSuperWinNum();
		int EpicWinNum = slotsListTemplate.getEpicWinNum();
		long times = gold/curBet;
		//如果当前 获得的金币  除以 当前的倍数  大于 上边 的某个参数 ，就按其中的一个值 走 （上边的值 是一次增大的）
		if(times >= EpicWinNum){
			gold+=gold*EpicWinNum/100;
			attackType=1;
		}else if(times >= SuperWinNum){
			gold+=gold*SuperWinNum/100;
			attackType=2;
		}else if(times >= MegaWinNum){
			gold+=gold*MegaWinNum/100;
			attackType=3;
		}else if(times >= bigWin){
			gold+=gold*bigWin/100;
			attackType=4;
		}
		
		
		
		
		//减少血量，并判断 boss的血量是否 已经 没了
		boolean bossStatus = boss.reduceBlood(gold,curAttackBlood,list,elementList,slotService,slotType);
		//AttackTotalBlood 每一次变化后的钱
		this.getHumanAttackBoss().setAttackTotalBlood(gold);
		long totalGold = ower.getHumanAttackBossManager().setTotalAttackNum(boss.getBossId(), curAttackBlood.get(0));
		this.getHumanAttackBoss().setAttackAllTotalBlood(totalGold);
//		this.getHumanAttackBoss().setModified();
		
		/**
		 * 如果用户在排行榜里 就修改下数据，不在就放进去
		 */
		boolean inOrNot = false;
		for(int i=0;i<attackRankVOList.size();i++){
			AttackRankVO ar = attackRankVOList.get(i);
			if(ar.getUserId()==ower.getPassportId()){
				inOrNot=true;
				ar.setAttackTotalBlood(totalGold);
				boss.setVIPAddition(ower,bossTemplate,ar);
				ar.setVipAdditionRate(getAttackRate(boss,totalGold));
				break;
			}
		}
		if(!inOrNot){
			AttackRankVO newAttackRankVO = getAttackRankVO(boss,totalGold,boss.getBossId(),ower.getPassportId(),ower.getName());
			attackRankVOList.add(newAttackRankVO);
		}
		/**
		 * 然后排序
		 */
		Collections.sort(attackRankVOList, new Comparator<AttackRankVO>(){

			@Override
			public int compare(AttackRankVO o1, AttackRankVO o2) {
				if(o1.getAttackTotalBlood() > o2.getAttackTotalBlood()){
					return -1;
				}
				return 1;
			}
			
		});
		/**
		 * 再次寻找用户的位置
		 */
		for(int i=0;i<attackRankVOList.size();i++){
			AttackRankVO ar = attackRankVOList.get(i);
			ar.setCurIndex(i+1);
		}
		boss.setAttackRank(JSONArray.toJSONString(attackRankVOList));
		boss.setModified();
		
		/**
		 * boss日志
		 */
		Globals.getLogService().sendWorldBossLog(human, LogReasons.WorldBossLogReason.WorldBoss, "", 
				boss.getType(),null, 0, 0, curAttackBlood.get(0),boss.getBossId());
		
		
		return bossStatus;
	}
	
	private AttackRankVO getAttackRankVO(Boss boss,long totalBlood,long bossId,long userId,String name){
		
		AttackRankVO attackRankVO = new AttackRankVO();
		attackRankVO.setName(name);
		attackRankVO.setBossId(bossId);
		attackRankVO.setUserId(userId);
		attackRankVO.setAttackTotalBlood(totalBlood);
		attackRankVO.setAttackRate(getAttackRate(boss,totalBlood));
		return attackRankVO;
	}
	private int getAttackRate(Boss boss,long totalBlood){
		long bosstotalBlood = boss.getIncreaseBlood()+boss.getBlood();
		long humanShuldBlood = (totalBlood*100)/bosstotalBlood;
		return Long.valueOf(humanShuldBlood).intValue();
	}

	public List<Long> getCurAttackBlood() {
		return curAttackBlood;
	}

	public void setCurAttackBlood(List<Long> curAttackBlood) {
		this.curAttackBlood = curAttackBlood;
	}
	
	
}
