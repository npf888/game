package com.gameserver.compensation;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.uuid.UUIDType;
import com.db.model.CompensationEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanCompensationManager;
import com.gameserver.mail.MailLogic;



/**
 * 补偿服务
 * @author wayne
 *
 */
public class CompensationService  implements InitializeRequired,DestroyRequired,AfterInitializeRequired{
	
	
	private Logger logger = Loggers.compensationLogger;
	
	private static final int COMPENSATION_VALID_DAY = 7;
	private List<Compensation> compensationList = new ArrayList<Compensation>();
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		loadCompensationsFromDB();
	}
	
	/**
	 * 加载补偿列表
	 */
	private void loadCompensationsFromDB() {
		// TODO Auto-generated method stub
		List<CompensationEntity> dbCompensationList=Globals.getDaoService().getCompensationDao().getAllCompensations();
		if(dbCompensationList==null) return;

		for(CompensationEntity compensationEntity:dbCompensationList) 
		{
		
			Compensation compensation = new Compensation();
			compensation.fromEntity(compensationEntity);
			compensationList.add(compensation);
		}
	}

	/**
	 * 添加补偿
	 * @param activity
	 */
	public void addCompensation(Compensation compensation){
		Compensation tempCompensation = getCompensationById(compensation.getDbId());
		if(tempCompensation!=null){
			logger.warn("add compensation["+compensation.getDbId()+"] repeat");
			return;
		}
		compensationList.add(compensation);
	}
	
	/**
	 * 移除补偿
	 * @param activity
	 */
	public void removeCompensation(Compensation compensation){
		Compensation tempCompensation = getCompensationById(compensation.getDbId());
		if(tempCompensation!=null){
			logger.warn("remove no exist compensation["+compensation.getDbId()+"]");
			return;
		}
		compensationList.remove(tempCompensation);
	}
	
	/**
	 * 补充用户补偿
	 */
	public void checkHumanCompensations(Human human){
		// TODO Auto-generated method stub
		HumanCompensationManager humanCompensationManager = human.getHumanCompensationManager();
		List<Compensation> compensationList =Globals.getCompensationService().getCompensationList();
		for(Compensation compensation :compensationList ){
			HumanCompensation humanCompensation = humanCompensationManager.getHumanCompensationByCompensationId(compensation.getDbId());
			//发了补偿
			if(humanCompensation != null){
				continue;
			}
			//新创建的号没有尝
			if(human.getCreateTime() > compensation.getCreateTime()){
				continue;
			}
			
			//添加用户补偿
			complementHumanCompensation(human,compensation);
		}
	}
	
	/**
	 * 补充用户补偿
	 */
	public void complementHumanCompensation(Human human,Compensation compensation){
		//添加补偿记录
		HumanCompensation humanCompensation = createHumanCompensation(human,compensation);
		humanCompensation.setModified();
		
		//发送邮件
		List<Long> recIds = new ArrayList<Long>();
		recIds.add(human.getPassportId());
		MailLogic.getInstance().systemSendMailCompensation(null,compensation.getTitle() ,compensation.getContent(), recIds, compensation.getItemList());
	}
	
	/**
	 * 获取所有补偿
	 */
	public List<Compensation> getCompensationList(){
		return compensationList;
	}
	
	/**
	 * 获取补偿
	 * @param cid
	 * @return
	 */
	public Compensation getCompensationById(long cid){
		for(Compensation compensation :compensationList){
			if (compensation.getDbId() == cid)
				return compensation;
		}
		return null;
	}
	
	
	/**
	 * 创建补偿id
	 * @param charId
	 * @param compensationId
	 * @return
	 */
	public HumanCompensation createHumanCompensation(Human human,Compensation compensation)
	{
		HumanCompensation humanCompensation=new HumanCompensation();
	
		long now = Globals.getTimeService().now();
		long tempId=Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANCOMPENSATIONID);
		humanCompensation.setDbId(tempId);
		humanCompensation.setCharId(human.getPassportId());
		humanCompensation.setCompensationId(compensation.getDbId());
		humanCompensation.setUpdateTime(now);
		humanCompensation.setCreateTime(now);
		humanCompensation.setInDb(false);
		humanCompensation.setOwner(human);
		humanCompensation.setInDb(false);
		humanCompensation.active();
		return humanCompensation;
	}
}
