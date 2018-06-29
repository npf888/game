package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.db.model.HumanCompensationEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.compensation.HumanCompensation;
import com.gameserver.human.Human;

/**
 * 用户补偿管理器
 * @author wayne
 *
 */
public class HumanCompensationManager implements RoleDataHolder, InitializeRequired{

	private Logger logger = Loggers.compensationLogger;
	
	private Human owner;
	private List<HumanCompensation> humanCompensationList = new ArrayList<HumanCompensation>();
	
	
	/**
	 * 构造器
	 * 
	 * @param owner
	 */
	public HumanCompensationManager(Human owner) {
		this.owner = owner;

	}
	
	public Human getOwner(){
		return this.owner;
	}
	
	public List<HumanCompensation> getHumanCompensationList(){
		return humanCompensationList;
	}
	
	/**
	 * 加载补偿数据  */
	public void load(){
		logger.debug("玩家["+owner.getPassportId()+"]加载补偿管理器");
		List<HumanCompensationEntity> humanCompensationEntityList = Globals.getDaoService().getHumanCompensationDao().getAllHumanCompensations(owner.getPassportId());
		
		if(humanCompensationEntityList!=null&&humanCompensationEntityList.size()>0){
			for(HumanCompensationEntity humanCompensationEntity:humanCompensationEntityList){
		
				HumanCompensation humanCompensation=new HumanCompensation();
				humanCompensation.setOwner(owner);
				humanCompensation.fromEntity(humanCompensationEntity);
				humanCompensationList.add(humanCompensation);
			}
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		logger.debug("玩家["+owner.getPassportId()+"]初始化补偿管理器");
		Globals.getCompensationService().checkHumanCompensations(owner);
	}



	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 用户补偿 根据id
	 * @param cid
	 * @return
	 */
	public HumanCompensation getHumanCompensationByCompensationId(long cid){
		for(HumanCompensation humanCompensation : humanCompensationList){
			if(humanCompensation.getCompensationId() == cid)
				return humanCompensation;
		}
		return null;
	}

}
