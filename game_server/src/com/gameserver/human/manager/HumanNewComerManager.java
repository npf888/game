package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanNewComerEntity;
import com.gameserver.common.Globals;
import com.gameserver.gift.HumanNewComer;
import com.gameserver.gift.msg.GCNewComerGift;
import com.gameserver.gift.template.NewComerTemplate;
import com.gameserver.human.Human;
/**
 * 新手礼包 新
 * @author JavaServer
 *
 */
public class HumanNewComerManager implements InitializeRequired{

	private static Logger logger = Loggers.rechargeLogger;
	private Human owner;
	
	private List<HumanNewComer> HumanNewComerList = new ArrayList<HumanNewComer>();
	public HumanNewComerManager(Human owner){
		this.owner = owner;
	}
	
	
	
	
	@Override
	public void init() {
		//如果用户买了 就 不做处理
		if(owner.getNewGuyGift() == 1){
			return;
		}
		
		List<HumanNewComerEntity> humanNewComerEntityList = Globals.getDaoService().getHumanNewComerDao().getAllHumanNewComerEntity(owner.getPassportId());
		if(humanNewComerEntityList == null || humanNewComerEntityList.size() == 0){
			buildNewEntity(HumanNewComerList);
			return;
		}
		for(HumanNewComerEntity entity:humanNewComerEntityList){
			HumanNewComer HumanNewComer = new HumanNewComer(owner);
			HumanNewComer.fromEntity(entity);
			HumanNewComerList.add(HumanNewComer);
		}
		
		Date startTime = HumanNewComerList.get(0).getStartTime();
		String userStartTime = TimeUtils.formatYMDTime(startTime.getTime());
		String now = TimeUtils.formatYMDTime(new Date().getTime());
		//如果不是同一天
		if(!userStartTime.equals(now)){
			buildNewEntity(HumanNewComerList);
		}
		//发送数据
		sendTime();
	}

	private void buildNewEntity(List<HumanNewComer> HumanNewComerList){
		HumanNewComer HumanNewComer = new HumanNewComer(owner);
		long now = Globals.getTimeService().now();
		HumanNewComer.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANNEWCOMER));
		HumanNewComer.setPerStartTime(new Date());
		HumanNewComer.setUserId(owner.getPassportId());
		HumanNewComer.setStartTime(new Date());
		HumanNewComer.setInDb(false);
		HumanNewComer.active();
		HumanNewComer.setModified();
		HumanNewComerList.add(0,HumanNewComer);
	}
	
	public void  sendTime(){
		
		NewComerTemplate newComerTemplate = Globals.getGiftService().getNewComerTemplateByDay(HumanNewComerList.size());
		if(newComerTemplate == null){
			return;
		}
		//不限时
		GCNewComerGift gCNewComerGift  = new GCNewComerGift();
		if(newComerTemplate.getType()==1){
			gCNewComerGift.setOpenShut(0);//开启
			gCNewComerGift.setGiftType(1);// 不限时
			gCNewComerGift.setLeftTime(0);
			
		//限时
		}else{
			gCNewComerGift.setOpenShut(0);//开启
			gCNewComerGift.setGiftType(0);// 限时
			//剩余时间
			HumanNewComer humanNewComer = HumanNewComerList.get(0);
			long now = Globals.getTimeService().now();
			long nowTimeCha = now-humanNewComer.getPerStartTime().getTime();
			long timeLimit = newComerTemplate.getTimeLimit()*1000;
			
			long leftTime = timeLimit-nowTimeCha;
			gCNewComerGift.setLeftTime(leftTime);
			//时间到了
			if(leftTime<0){
				humanNewComer.setPerStartTime(new Date());
				humanNewComer.setModified();
				gCNewComerGift.setLeftTime(timeLimit);
			}
			
		}
		gCNewComerGift.setPid(newComerTemplate.getPid());
		logger.info("---["+owner.getPassportId()+"]-新手礼包-pid:"+newComerTemplate.getPid());
		owner.sendMessage(gCNewComerGift);
	}



	/**
	 * 如果结束 就发送接口
	 */

	public void isEnd() {
		if(HumanNewComerList == null || HumanNewComerList.size() == 0){
			return;
		}
		HumanNewComer humanNewComer = HumanNewComerList.get(0);
		NewComerTemplate newComerTemplate = Globals.getGiftService().getNewComerTemplateByDay(HumanNewComerList.size());
		long now = Globals.getTimeService().now();
		long nowTimeCha = now-humanNewComer.getPerStartTime().getTime();
		long timeLimit = newComerTemplate.getTimeLimit()*1000;
		
		long leftTime = timeLimit-nowTimeCha;
		//时间到了
		if(leftTime<0){
			GCNewComerGift gCNewComerGift  = new GCNewComerGift();
			gCNewComerGift.setOpenShut(1);//关闭
			gCNewComerGift.setGiftType(1);// 不限时
			gCNewComerGift.setLeftTime(0);
			gCNewComerGift.setPid(newComerTemplate.getPid());
			owner.sendMessage(gCNewComerGift);
		}
		
	}




	
}
