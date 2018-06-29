package com.gameserver.worldboss.pojo;

import com.gameserver.human.Human;
import com.gameserver.worldboss.template.BossTemplate;
import com.gameserver.worldboss.util.BossBloodReturningUtils;
import com.gameserver.worldboss.util.BossBroadcast;
import com.gameserver.worldboss.vo.AttackRankVO;



/**
 * 世界boss的大boss
 * @author JavaServer
 *
 */
public class Boss  extends BossInit {
	
	BossBloodReturningUtils bossBloodReturningUtils = new BossBloodReturningUtils();
	BossBroadcast bossBroadcast = new BossBroadcast();

	
	public BossBroadcast getBossBroadcast() {
		return bossBroadcast;
	}

	public void setBossBroadcast(BossBroadcast bossBroadcast) {
		this.bossBroadcast = bossBroadcast;
	}

	public BossBloodReturningUtils getBossBloodReturningUtils() {
		return bossBloodReturningUtils;
	}

	public void setBossBloodReturningUtils(
			BossBloodReturningUtils bossBloodReturningUtils) {
		this.bossBloodReturningUtils = bossBloodReturningUtils;
	}
	
	public  void setVIPAddition(Human human,BossTemplate bossTemplate,AttackRankVO ar){
		int vipLevel =human.getHumanVipNewManager().getVipLv();
		if(vipLevel == 0){
			ar.setAttackRate(bossTemplate.getVip0());
		}else if(vipLevel == 1){
			ar.setAttackRate(bossTemplate.getVip1());
		}else if(vipLevel == 2){
			ar.setAttackRate(bossTemplate.getVip2());
		}else if(vipLevel == 3){
			ar.setAttackRate(bossTemplate.getVip3());
		}else if(vipLevel == 4){
			ar.setAttackRate(bossTemplate.getVip4());
		}else if(vipLevel == 5){
			ar.setAttackRate(bossTemplate.getVip5());
		}
	}
	
	
	
	
	
}
