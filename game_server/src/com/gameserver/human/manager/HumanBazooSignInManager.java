package com.gameserver.human.manager;

import java.util.Calendar;
import java.util.Date;

import com.common.InitializeRequired;
import com.core.uuid.UUIDType;
import com.db.model.HumanBazooSigninEntity;
import com.gameserver.bazoo.util.TimeComparisonUtils;
import com.gameserver.bazoosignin.HumanBazooSignIn;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

public class HumanBazooSignInManager implements  InitializeRequired {
	private Human owner;
	private HumanBazooSignIn HumanBazooSignIn = null;
	public HumanBazooSignInManager(Human owner) {
		this.owner = owner;
	}
	
	
	@Override
	public void init() {
		HumanBazooSigninEntity humanBazooSigninEntity = Globals.getDaoService().getHumanBazooSigninDao().getHumanBazooSigninByPassportId(owner.getPassportId());
		HumanBazooSignIn = new HumanBazooSignIn(owner);
		if(humanBazooSigninEntity == null){
			HumanBazooSignIn.setInDb(false);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -2);
			HumanBazooSignIn.setDbId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.HUMANBAZOOSIGNIN));
			HumanBazooSignIn.setPassportId(owner.getPassportId());
			HumanBazooSignIn.setSignInTime(calendar.getTime());
			HumanBazooSignIn.setTimes(0);
			HumanBazooSignIn.active();
			HumanBazooSignIn.setModified();
		}else{
			HumanBazooSignIn.fromEntity(humanBazooSigninEntity);
		}
		
	}


	/**
	 * 获取用户的状态
	 * @return
	 */
	public int getStatus(){
		int comResult = TimeComparisonUtils.compareNowWithPassTime(HumanBazooSignIn.getSignInTime());
		if(comResult == -1){
			return 0;//可以领取
		}
		return 1;//不可领取
		
	}
	
	
	
	public HumanBazooSignIn getHumanBazooSignIn() {
		return HumanBazooSignIn;
	}


	public void setHumanBazooSignIn(HumanBazooSignIn humanBazooSignIn) {
		HumanBazooSignIn = humanBazooSignIn;
	}

	
	
	
}
